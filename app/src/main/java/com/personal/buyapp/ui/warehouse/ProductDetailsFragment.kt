package com.personal.buyapp.ui.warehouse

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.personal.buyapp.R
import com.personal.buyapp.utils.NavigationArgumentsHack
import com.personal.buyapp.utils.toastl
import kotlinx.android.synthetic.main.product_details_fragment.*

class ProductDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ProductDetailsFragment()
    }

    private lateinit var viewModel: ProductDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductDetailsViewModel::class.java)
        // TODO: Use the ViewModel
        product_barcode.setText( NavigationArgumentsHack.registerProductBarcode)
        viewModel.barcode = NavigationArgumentsHack.registerProductBarcode
        add_product.setOnClickListener {
            viewModel.addProduct(
                productPrice = product_price.text.toString().toDouble(),
                productName = product_name.text.toString(),
                productStock = product_stock.text.toString().toDouble()
            )
        }

        viewModel.mutableCallFinished.observe(viewLifecycleOwner, Observer {
            toastl("Request done")
            findNavController().popBackStack()
        })
    }

}
