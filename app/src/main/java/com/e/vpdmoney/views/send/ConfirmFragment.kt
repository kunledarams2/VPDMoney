package com.e.vpdmoney.views.send

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.e.vpdmoney.R
import com.e.vpdmoney.databinding.FragmentConfirmBinding
import com.e.vpdmoney.model.request.TransferRequest
import com.e.vpdmoney.util.GlobalUtil
import java.util.*
import kotlin.collections.ArrayList


class ConfirmFragment : Fragment() {

    lateinit var binding:FragmentConfirmBinding
    private val args:ConfirmFragmentArgs by navArgs()
    private val editTextArray: ArrayList<EditText> = ArrayList(NUM_OF_DIGITS)
    private var numTemp=String()

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
        binding=FragmentConfirmBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.receiverName.text= args.transferRequest.receiver
        binding.amount.text = GlobalUtil.currencyFormat(args.transferRequest.amount.toDouble())
        binding.accountNumEdt.text= "${args.transferRequest.receiver_account_number} - ${args.transferRequest.receiver_bank}"



        // Initialize EditText views with tags and set up listeners
//        for (i in 1..4) {
//            val digitView = binding.root.findViewWithTag<EditText>("digit$i")
//            digitView?.let {
//                editTextArray.add(it)
//                it.addTextChangedListener(createTextWatcher(i))
//                it.setOnEditorActionListener { v, actionId, _ ->
//                    if (actionId == EditorInfo.IME_ACTION_DONE) {
//                        v.clearFocus()  // Hide keyboard on action done
//                    }
//                    false
//                }
//            }
//        }
        for (index in 0 until (binding.codeLayout.childCount)) {
            val view: View = binding.codeLayout.getChildAt(index)
            if (view is EditText) {
                editTextArray.add(index, view)
                editTextArray[index].addTextChangedListener(createTextWatcher(index))
                editTextArray[index].setOnEditorActionListener { v, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        v.clearFocus()  // Hide keyboard on action done
                    }
                    false
                }

            }

            // Handle deletion to move back focus
            editTextArray[index].setOnKeyListener { _, keyCode, event ->
                if (event.action == android.view.KeyEvent.ACTION_DOWN && keyCode == android.view.KeyEvent.KEYCODE_DEL) {
                    if (editTextArray[index].text.isEmpty() && index > 0) {
                        editTextArray[index - 1].requestFocus()
                    }
                }
                false
            }



    }

        // Set focus on the first EditText
        editTextArray[0].requestFocus()
    }

    private fun createTextWatcher(index: Int): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    // Move to the next EditText if two characters are entered
                    if (index < editTextArray.size) {
                        Log.d("Index", index.toString())


                        if(index <= 2){
                            editTextArray[index+1].requestFocus()
                        }else{
                            val transferRequest= TransferRequest(
                                reference= UUID.randomUUID().toString(),
                                receiver = args.transferRequest.receiver,
                                receiver_account_number =args.transferRequest.receiver_account_number,
                                receiver_bank =args.transferRequest.receiver_bank,
                                amount =args.transferRequest.amount,
                                sender=args.accountDetail.account_name,
                                sender_account_number= args.accountDetail.account_number,
                                narration = args.transferRequest.narration,
                                payment_method = "Bank Transfer",
                                transaction_date = args.transferRequest.transaction_date,
                                transaction_status = "Successful"


                            )
                            findNavController().navigate(ConfirmFragmentDirections.actionConfirmFragmentToSuccessFragment(transferRequest))
                        }

                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }



    companion object {
        const val NUM_OF_DIGITS = 4
        @JvmStatic
        fun newInstance() =
            ConfirmFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}