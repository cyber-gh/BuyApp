package com.personal.buyapp.ui.buyer

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ReportFragment
import androidx.navigation.fragment.findNavController

import com.personal.buyapp.R
import com.personal.buyapp.ifrastructure.Repository

class BuyerHomeFragment : Fragment() {

    companion object {
        fun newInstance() = BuyerHomeFragment()
    }

    private lateinit var viewModel: BuyerHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.buyer_home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BuyerHomeViewModel::class.java)
        // TODO: Use the ViewModel



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Repository.sellerUserName != "") {
            findNavController().navigate(R.id.action_buyerHomeFragment_to_receiptFragment)
        }

        Repository.sellDaataready.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_buyerHomeFragment_to_receiptFragment)
        })
    }

}
