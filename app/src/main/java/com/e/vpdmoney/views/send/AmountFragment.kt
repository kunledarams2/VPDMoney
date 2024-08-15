package com.e.vpdmoney.views.send

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.e.vpdmoney.R
import com.e.vpdmoney.databinding.FragmentAmountBinding
import com.e.vpdmoney.databinding.FragmentRecipientBinding
import com.e.vpdmoney.model.request.TransferRequest
import java.util.*


class AmountFragment : Fragment() {

    private var isValidated = false
    lateinit var binding: FragmentAmountBinding
    private val args:AmountFragmentArgs by navArgs()


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
        binding = FragmentAmountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.amountEdt.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.notification.innerNotifWrapper.visibility=View.GONE
                if(p0.toString().length > 2){
                    enableButton(true)
                }else{
                    enableButton(false)
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.navBtn.setOnClickListener {
            findNavController().navigate(AmountFragmentDirections.actionAmountFragmentToRecipientFragment(args.accountDetail))
        }

        binding.ctnBtn.setOnClickListener {
            if (isValidated){
                if (args.accountDetail.account_balance.toInt() > binding.amountEdt.text.toString().toInt()){

                    val transferRequest= TransferRequest(
                        reference= UUID.randomUUID().toString(),
                        receiver = args.transferRequest.receiver,
                        receiver_account_number =args.transferRequest.receiver_account_number,
                        receiver_bank =args.transferRequest.receiver_bank,
                        amount =binding.amountEdt.text.toString().toInt(),
                        sender=args.accountDetail.account_name,
                        sender_account_number= args.accountDetail.account_number,
                        narration =  binding.narrationEdt.text.toString(),
                        payment_method = "Bank Transfer",
                        transaction_date = args.transferRequest.transaction_date,
                        transaction_status = "Pending"


                    )

                    findNavController().navigate(AmountFragmentDirections.actionAmountFragmentToConfirmFragment(args.accountDetail, transferRequest))
                }
                else{
                    binding.notification.innerNotifWrapper.visibility = View.VISIBLE
                    binding.notification.messageTv.text = "Insufficient fund"
                    binding.notification.alertIcon.background = resources.getDrawable(R.drawable.error_sign_icon)
                    binding.notification.innerNotifWrapper.background= resources.getDrawable(R.drawable.oval_red_stroke)
                }
            }
        }
        binding.feeEdt.isEnabled=false
    }

    private fun enableButton(isButtonEnable:Boolean){

        if (isButtonEnable){
            binding.ctnBtn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0076B5"))
            binding.ctnBtn.isClickable=true
            isValidated = true
        }else{
            binding.ctnBtn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#BABEC2"))
            binding.ctnBtn.isClickable=false
            isValidated = false

        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AmountFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}