package com.e.vpdmoney.views.send

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.vpdmoney.R
import com.e.vpdmoney.databinding.FragmentSendBinding
import com.e.vpdmoney.model.response.AccountDatum
import com.e.vpdmoney.model.response.AccountResponse
import com.e.vpdmoney.util.GlobalUtil
import com.e.vpdmoney.util.callback.ClickListener
import com.e.vpdmoney.views.account.AccountAdapter
import com.e.vpdmoney.views.account.AccountFragmentDirections
import com.google.gson.Gson


class SendFragment : Fragment() {

    lateinit var binding:FragmentSendBinding
    private lateinit var accountAdapter:AccountAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSendBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jsonString = GlobalUtil.readJsonFromRawResource(R.raw.subaccounts, requireContext())
        val gson = Gson()
        val response = gson.fromJson(jsonString, AccountResponse::class.java)
        accountAdapter = AccountAdapter()
        binding.accReyc.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = accountAdapter
            setHasFixedSize(false)
        }

        accountAdapter.setData(response.data)

        accountAdapter.setViewAccountListener(object : ClickListener<AccountDatum> {
            override fun onClick(model: AccountDatum) {
                when (model.account_status){

                    "Frozen"->{
                       binding.notification.innerNotifWrapper.visibility = View.VISIBLE
                        binding.notification.messageTv.text =resources.getString(R.string.frozen_account_alert)
                        binding.notification.alertIcon.background = resources.getDrawable(R.drawable.alert__1_)
                        binding.notification.innerNotifWrapper.background= resources.getDrawable(R.drawable.oval_orange_stroke)
                    }

                    "Closed"->{
                        binding.notification.innerNotifWrapper.visibility = View.VISIBLE

                        binding.notification.alertIcon.background = resources.getDrawable(R.drawable.error_sign_icon)
                        binding.notification.innerNotifWrapper.background= resources.getDrawable(R.drawable.oval_red_stroke)
                        binding.notification.messageTv.text =resources.getString(R.string.close_account_alert)
                    }
                    "Active"->{
                        findNavController().navigate(SendFragmentDirections.actionNavSendToRecipientFragment(model))

                    }

                }
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SendFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}