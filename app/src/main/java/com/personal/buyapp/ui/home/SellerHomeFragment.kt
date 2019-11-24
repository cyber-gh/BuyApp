package com.personal.buyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.personal.buyapp.R
import com.personal.buyapp.ifrastructure.Repository
import com.personal.buyapp.ifrastructure.UserType
import kotlinx.android.synthetic.main.buyer_home_fragment.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.avatar_image
import kotlinx.android.synthetic.main.fragment_home.balance_currency_lbl
import kotlinx.android.synthetic.main.fragment_home.halfview
import kotlinx.android.synthetic.main.fragment_home.root_view

class SellerHomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        account_balance_container.setBackgroundResource(R.drawable.circle_backgrond)
        view.findViewById<View>(R.id.create_receipt).setOnClickListener {
            findNavController().navigate(R.id.action_seller_home_fragment_to_scanProductFragment)
        }

        add_product.setOnClickListener {
            findNavController().navigate(R.id.action_seller_home_fragment_to_registerProductFragment)
        }

        avatar_image.setOnClickListener {
            if (Repository.userType == UserType.SELLER) {
                homeViewModel.swapState()
                halfview.setBackgroundColor(resources.getColor(homeViewModel.halfViewColor))
                root_view.setBackgroundColor(resources.getColor(homeViewModel.rootColor))

                add_product.isVisible = true
                avatar_image.isVisible = false

                balance_value_lbl.isVisible = false
                balance_currency_lbl.isVisible = false
                create_receipt.isVisible = true

                account_balance_container.setBackgroundResource(R.drawable.cirlce_background_filled_border)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.resetState()
    }
}
