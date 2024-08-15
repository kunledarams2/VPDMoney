package com.e.vpdmoney.views.send

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.e.vpdmoney.MainActivity
import com.e.vpdmoney.R
import com.e.vpdmoney.databinding.FragmentTransactionReceiptBinding
import com.e.vpdmoney.util.GlobalUtil


class TransactionReceiptFragment : Fragment() {

    lateinit var binding:FragmentTransactionReceiptBinding
    private val args:TransactionReceiptFragmentArgs by navArgs()

    protected val mainActivity: MainActivity
        get() {
            return activity as? MainActivity ?: throw IllegalStateException("Not attached!")
        }

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
        binding=FragmentTransactionReceiptBinding.inflate(inflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNav()
        binding.backBtn.setOnClickListener {

            findNavController().navigate(TransactionReceiptFragmentDirections.actionTransactionReceiptFragmentToNavHome())
            mainActivity.showBottomNav()
        }

        binding.apply {
            amount.text = GlobalUtil.currencyFormat(args.transferData.amount.toDouble())
            refTv.text = args.transferData.reference
            rcAccNumber.text = args.transferData.receiver_account_number
            receiverName.text =args.transferData.receiver
            recBank.text = args.transferData.receiver_bank
            senderTv.text = args.transferData.sender
            dateRecie.text = args.transferData.transaction_date
            paymentType.text =args.transferData.payment_method
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TransactionReceiptFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}