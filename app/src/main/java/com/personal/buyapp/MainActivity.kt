package com.personal.buyapp

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.personal.buyapp.ifrastructure.Repository
import com.personal.buyapp.ifrastructure.Router
import com.personal.buyapp.ifrastructure.UserType
import com.personal.buyapp.ifrastructure.infoAlert
import com.personal.buyapp.utils.log

class MainActivity : AppCompatActivity(), NfcAdapter.CreateNdefMessageCallback {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var nfcAdapter: NfcAdapter? = null

    private fun initialize() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this).apply {
            setNdefPushMessageCallback( null, this@MainActivity)
        }


        // OMG
        Repository.userTypeLiveData.observe(this, Observer {
            if (it == UserType.BUYER) {
                nfcAdapter = NfcAdapter.getDefaultAdapter(this).apply {
                    setNdefPushMessageCallback( null, this@MainActivity)
                }
            } else {
                if (Repository.currentReceipt != null) {
                    nfcAdapter = NfcAdapter.getDefaultAdapter(this).apply {
                        setNdefPushMessageCallback(this@MainActivity, this@MainActivity)
                    }
                }
            }
        })
        // OMG


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Router.acitivity = this
        initialize()
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.seller_home_fragment, R.id.nav_gallery, R.id.nav_slideshow, R.id.buyerHomeFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun createNdefMessage(p0: NfcEvent?): NdefMessage {
        val packageName = applicationContext.packageName
        val payload = "isendthis"
        val mimeType = "application/$packageName.payload"
        log(" creating ndef message")

        return NdefMessage(
            arrayOf<NdefRecord>(
                NdefRecord(
                    NdefRecord.TNF_MIME_MEDIA,
                    mimeType.toByteArray(Charsets.UTF_8),
                    ByteArray(0),
                    payload.toByteArray(Charsets.UTF_8)
                ),
                NdefRecord.createApplicationRecord(packageName)
            )
        )
    }

    override fun onNewIntent(intent: Intent?) {
        //setIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        when (intent.action) {
            Intent.ACTION_SEND -> {
                val str = intent.getStringExtra(Intent.EXTRA_TEXT)
                infoAlert("Sending for fuck sake --  " + str)
            }
            NfcAdapter.ACTION_NDEF_DISCOVERED -> {
                val messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                val payload = (messages[0] as NdefMessage).records[0].payload
                infoAlert("Receiving for fuck sake -- " + String(payload))
            }
        }
    }
}
