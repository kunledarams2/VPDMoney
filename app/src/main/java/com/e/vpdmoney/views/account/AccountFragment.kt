package com.e.vpdmoney.views.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.vpdmoney.R
import com.e.vpdmoney.databinding.FragmentAccountBinding
import com.e.vpdmoney.model.response.AccountDatum
import com.e.vpdmoney.model.response.AccountResponse
import com.e.vpdmoney.util.GlobalUtil
import com.e.vpdmoney.util.GlobalUtil.readJsonFromRawResource
import com.e.vpdmoney.util.callback.ClickListener
import com.google.gson.Gson


class AccountFragment : Fragment() {

    lateinit var binding: FragmentAccountBinding
    lateinit var accountAdapter: AccountAdapter

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
        binding = FragmentAccountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jsonString = readJsonFromRawResource(R.raw.subaccounts, requireContext())
        val gson = Gson()
        val response = gson.fromJson(jsonString, AccountResponse::class.java)



        binding.allAccountBalance.text = GlobalUtil.currencyFormat(response.data.sumOf { it.account_balance.toInt() }.toDouble())

        accountAdapter = AccountAdapter()
        binding.accReyc.apply {
            layoutManager =LinearLayoutManager(requireContext())
            adapter = accountAdapter
            setHasFixedSize(false)
        }

        accountAdapter.setData(response.data)

        accountAdapter.setViewAccountListener(object :ClickListener<AccountDatum>{
            override fun onClick(model: AccountDatum) {
                findNavController().navigate(AccountFragmentDirections.actionNavAccountToAccountDetailFragment(model))
            }
        })


    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AccountFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}