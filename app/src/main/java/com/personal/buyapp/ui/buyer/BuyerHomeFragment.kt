package com.personal.buyapp.ui.buyer

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ReportFragment
import androidx.navigation.fragment.findNavController

import com.personal.buyapp.R
import com.personal.buyapp.ifrastructure.Repository
import com.personal.buyapp.ifrastructure.UserType
import com.personal.buyapp.utils.CompleteActionDialog
import kotlinx.android.synthetic.main.buyer_home_fragment.*
import kotlinx.android.synthetic.main.buyer_home_fragment.avatar_image
import kotlinx.android.synthetic.main.buyer_home_fragment.halfview
import kotlinx.android.synthetic.main.buyer_home_fragment.root_view
import kotlinx.android.synthetic.main.fragment_home.*

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
            if (Repository.followRedirect) {
                findNavController().navigate(R.id.action_buyerHomeFragment_to_receiptFragment)

            }
        }

        Repository.sellDaataready.observe(viewLifecycleOwner, Observer {
            if (Repository.followRedirect) {
                findNavController().navigate(R.id.action_buyerHomeFragment_to_receiptFragment)
            }
        })

        avatar_image.setOnClickListener {
            if (Repository.userType == UserType.SELLER) {
                viewModel.swapState()
                halfview.setBackgroundColor(resources.getColor(viewModel.halfViewColor))
                root_view.setBackgroundColor(resources.getColor(viewModel.rootColor))


            }
        }
        Repository.currentBalance.observe(viewLifecycleOwner, Observer {
            account_balance_lbl.text = it.toString()
        })

        username_lbl_1.text = Repository.userName

        CompleteActionDialog.show(activity!!.supportFragmentManager)
    }

}
