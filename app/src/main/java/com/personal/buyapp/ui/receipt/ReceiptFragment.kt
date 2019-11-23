package com.personal.buyapp.ui.receipt

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.personal.buyapp.R
import com.personal.buyapp.ifrastructure.Repository
import com.personal.buyapp.ifrastructure.UserType
import com.personal.buyapp.ifrastructure.infoAlert
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
        viewModel.receiptData = NavigationArgumentsHack.receiptData!!

        populateLIst()

        receipt_ready_btn.setOnClickListener {
            if (Repository.userType == UserType.SELLER) {
                infoAlert("The receipt is ready, approach the buyer device to send the nfc payment data")
                Repository.refreshNfc()

            } else {
                //TODO
                //confirm payment
            }
        }

    }

    private fun populateLIst() {
        receipt_product_list_view.removeAllViews()
        viewModel.receiptData.products.forEach {
            val productView = layoutInflater.inflate(R.layout.receipt_product_item_view, null)
            productView.product_name_lbl.text = it.id
            receipt_product_list_view.addView(productView)
        }


    }
}
