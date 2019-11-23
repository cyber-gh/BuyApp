package com.personal.buyapp.ui.warehouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback

import com.personal.buyapp.R
import com.personal.buyapp.utils.toastl
import kotlinx.android.synthetic.main.fragment_register_product.*


class RegisterProductFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        codeScanner = CodeScanner(context!!, scanner_view)

        codeScanner.decodeCallback = DecodeCallback {
            activity!!.runOnUiThread {
                toastl("Code received " + it.text)
            }
        }

        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }
    }

}
