package com.personal.buyapp.ui.receipt

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.personal.buyapp.R
import com.personal.buyapp.ifrastructure.*
import com.personal.buyapp.utils.NavigationArgumentsHack
import kotlinx.android.synthetic.main.receipt_fragment.*
import kotlinx.android.synthetic.main.receipt_product_item_view.view.*

class ReceiptFragment : Fragment() {

    companion object {
        fun newInstance() = ReceiptFragment()
    }

    private lateinit var viewModel: ReceiptViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.receipt_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ReceiptViewModel::class.java)
//        viewModel.receiptData = NavigationArgumentsHack.receiptData!!
        viewModel.receiptId = NavigationArgumentsHack.receiptId

        viewModel.getGeneratedReceipt()

        receipt_ready_btn.setOnClickListener {
            if (Repository.userType == UserType.SELLER) {
                infoAlert("The receipt is ready, approach the buyer device to send the nfc payment data")
                Repository.refreshNfc()

            } else {
                viewModel.confirmPayment()
            }
        }

        viewModel.generatedreceipt.observe(viewLifecycleOwner, Observer {
            populateData(it)
        })

    }

    private fun populateData(generatedReceipt: GeneratedReceipt) {
        receipt_total_value.text = "${generatedReceipt.total} RON"

        receipt_product_list_view.removeAllViews()

        generatedReceipt.products.forEach {newProduct ->
            val productView = layoutInflater.inflate(R.layout.receipt_product_item_view, null)
            productView.quantity_and_price.text = "${newProduct.quantity} x ${newProduct.price} RON"
            productView.product_name_lbl.text = newProduct.name
            productView.product_total_price.text = "${newProduct.price * newProduct.quantity} RON"

            receipt_product_list_view.addView(productView)
        }


    }
}
