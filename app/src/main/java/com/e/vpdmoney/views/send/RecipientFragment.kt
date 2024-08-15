package com.e.vpdmoney.views.send

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.e.vpdmoney.MainActivity
import com.e.vpdmoney.R
import com.e.vpdmoney.databinding.FragmentRecipientBinding
import com.e.vpdmoney.model.request.CreateUserRequest
import com.e.vpdmoney.model.request.TransferRequest
import com.e.vpdmoney.model.response.*
import com.e.vpdmoney.util.GlobalUtil.readJsonFromRawResource
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class RecipientFragment : Fragment() {

    lateinit var binding:FragmentRecipientBinding
    private val args:RecipientFragmentArgs by navArgs()
    private var isValidated=false

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
        binding=FragmentRecipientBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNav()
        // Read JSON from the raw resource file
        binding.ctnBtn.isClickable=false
        val jsonString = readJsonFromRawResource(R.raw.banks, requireContext())

        val gson = Gson()
        val response = gson.fromJson(jsonString, BankResponse::class.java)


        setUpBankSpinner(response.data )

        binding.navBtn.setOnClickListener {

            findNavController().navigate(RecipientFragmentDirections.actionRecipientFragmentToNavSend())
            mainActivity.showBottomNav()

        }

        binding.accountNameEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length > 2){
                    if(binding.accountNumEdt.text.isNotEmpty() && binding.banks.isSelectionValid()){
                        enableButton(true)
                    }else{
                        enableButton(false)
                    }
                }else{
                    enableButton(false)
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.accountNumEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.notification.innerNotifWrapper.visibility=View.GONE
                if(p0.toString().length  ==10){
                    if(binding.accountNameEdt.text.isNotEmpty() && binding.banks.isSelectionValid()){
                        enableButton(true)
                    }else{
                        enableButton(false)
                    }
                }else{
                    enableButton(false)
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.ctnBtn.setOnClickListener {



            if (isValidated){
                if (binding.accountNumEdt.text.length !=10){
                    binding.notification.innerNotifWrapper.visibility = View.VISIBLE
                    binding.notification.messageTv.text = "Invalid account number ..."
                    return@setOnClickListener
                }

                val today: LocalDate = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formattedDate = today.format(formatter)

                val transferRequest= TransferRequest(
                 reference=UUID.randomUUID().toString(),
                 receiver =binding.accountNameEdt.text.toString(),
                 receiver_account_number =binding.accountNumEdt.text.toString(),
                 receiver_bank =binding.banks.getSelectedItemName(),
                 amount =0,
                    sender=args.accountDetail.account_name,
                    sender_account_number= args.accountDetail.account_number,
                    narration =  "",
                    payment_method = "Bank Transfer",
                    transaction_date = formattedDate,
                    transaction_status = "Processing"


                )
                findNavController().navigate(RecipientFragmentDirections.actionRecipientFragmentToAmountFragment(args.accountDetail,transferRequest))

            }
        }
    }

    private fun setUpBankSpinner(bankList:  List<BankDatum>) {

        val keyValueTasks = KeyValues(bankList.map {
            KeyValue(
                it.name,
                it.name.lowercase(Locale.getDefault()),
                it.code.toString(), it
            )
        })

        (binding.banks as AutoCompleteTextView)
            .setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.dropdown_menu_item_dark,
                    keyValueTasks.keyValues
                )
            )

        binding.banks.onItemClick ={
            val selectedItem = it as BankDatum
            if (selectedItem != null && bankList.contains(selectedItem)) {

                if (binding.accountNameEdt.text.isNotEmpty() && binding.accountNumEdt.text.isNotEmpty()){
                    enableButton(true)
                }else{
                    enableButton(false)
                }

            } else {
                enableButton(false)
            }

        }

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
            RecipientFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}