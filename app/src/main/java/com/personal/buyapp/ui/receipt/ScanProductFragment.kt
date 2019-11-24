package com.personal.buyapp.ui.receipt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback

import com.personal.buyapp.R
import com.personal.buyapp.ifrastructure.ProductWrapper
import com.personal.buyapp.ifrastructure.getTotalPrice
import com.personal.buyapp.utils.NavigationArgumentsHack
import com.personal.buyapp.utils.toastl
import kotlinx.android.synthetic.main.fragment_scan_product.*
import kotlinx.android.synthetic.main.receipt_product_item_view.view.*

class ScanProductFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var scanProductViewModel: ScanProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scanProductViewModel = ViewModelProviders.of(this).get(ScanProductViewModel::class.java)
        codeScanner = CodeScanner(context!!, scanner_view)

        codeScanner.decodeCallback = DecodeCallback {
            activity!!.runOnUiThread {
                scanProductViewModel.getProductById(it.text)
            }
        }

        scanProductViewModel.warehouseProductMutable.observe(viewLifecycleOwner, Observer {
            toastl(" Product found: " + it.name)
            val newProduct = ProductWrapper(it)

            scanProductViewModel.addProduct(newProduct)

            codeScanner.stopPreview()
        })

        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }


        populateList()

        generate_receipt.setOnClickListener {
            scanProductViewModel.getReceipt()
        }

        scanProductViewModel.shouldGoBack.observe(viewLifecycleOwner, Observer {
            if (it) findNavController().popBackStack()
        })

        scanProductViewModel.receiptDataLive.observe(viewLifecycleOwner, Observer {
            NavigationArgumentsHack.receiptId = it.id
            findNavController().navigate(R.id.action_scanProductFragment_to_receiptFragment)
        })

    }

    private fun populateList() {

        scanProductViewModel.productListMutableLiveData.observe(viewLifecycleOwner, Observer {
            product_list_view.removeAllViews()
            it.forEach { newProduct ->
                val productView = layoutInflater.inflate(R.layout.receipt_product_item_view, null)
                productView.quantity_and_price.text = "${newProduct.quantity} x ${newProduct.warehouseProduct.price} RON"
                productView.product_name_lbl.text = newProduct.warehouseProduct.name
                productView.product_total_price.text = "${newProduct.getTotalPrice()} RON"

                product_list_view.addView(productView)
            }
        })

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}
