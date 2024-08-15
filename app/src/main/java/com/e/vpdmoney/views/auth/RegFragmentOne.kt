package com.e.vpdmoney.views.auth

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController


import com.e.vpdmoney.R

import com.e.vpdmoney.databinding.FragmentRegOneBinding
import com.e.vpdmoney.model.request.CreateUserRequest
import java.util.regex.Pattern


class RegFragmentOne : Fragment() {

    lateinit var binding: FragmentRegOneBinding
    lateinit var bundle :Bundle
    private lateinit var mutableLiveData: MutableLiveData<Boolean>
    private var errorMessage=String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bundle = it
            errorMessage= it.getString("errorMessage", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegOneBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (errorMessage.isNotEmpty()){
            binding.include.innerNotifWrapper.visibility = View.VISIBLE
            binding.include.messageTv.text = errorMessage
        }

        binding.headerWrapper.subHeaderTvSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_regFragmentOne_to_loginFragment, bundle)
        }
        binding.nextBtn.setOnClickListener {

            val createUserRequest= CreateUserRequest(
                full_name =  "${binding.firstNameEdt.text} ${binding.lastNameEdt.text}",
                organization_name = "",
                email = binding.emailEdt.text.toString(),
                phone_number =  binding.phoneEdt.text.toString(),
                password =  "",
                country = "",
                business_category = "",
                accepted_terms = binding.checkBox.isChecked
            )
            findNavController().navigate( RegFragmentOneDirections.actionRegFragmentOneToRegFragmentTwo(createUserRequest))

        }
        mutableLiveData = MutableLiveData()
        binding.nextBtn.isClickable=false
        viewObserver()
        setView()
    }



    private fun viewObserver() {
        mutableLiveData.observe(viewLifecycleOwner) { s: Boolean? ->
            /*  if(s!!.isNotEmpty()){
                  binding.cardInsertStatus.text = s

              }*/
        /*    if (!TextUtils.isEmpty(s)) {
                showResult(binding.cardInsertStatus, s)
                if (s==getString(R.string.wait_card)){
                    binding.confirmWrapper.visibility = View.GONE
                    binding.processPaymentWrapper.visibility = View.VISIBLE
                    binding.btnCtn.setBackgroundColor(Color.parseColor("#F2050505"))
                }
            }*/
        }
    }

    private  fun setView(){
        binding.firstNameEdt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length > 2){
                    if(binding.lastNameEdt.text.isNotEmpty() && Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), binding.emailEdt.text.toString()) && binding.phoneEdt.text.toString().length >= 11&& binding.checkBox.isChecked){
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

        binding.lastNameEdt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length > 2){
                    if(binding.firstNameEdt.text.isNotEmpty() &&  Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), binding.emailEdt.text.toString()) && binding.phoneEdt.text.toString().length >= 11&& binding.checkBox.isChecked){
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


        binding.emailEdt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), p0.toString())){
                    if(binding.firstNameEdt.text.isNotEmpty() && binding.lastNameEdt.text.isNotEmpty() && binding.phoneEdt.text.length>=11 && binding.checkBox.isChecked){
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

        binding.phoneEdt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length >=11 ){
                    if(binding.firstNameEdt.text.isNotEmpty() && Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), binding.emailEdt.text.toString())  && binding.checkBox.isChecked&& binding.lastNameEdt.text.isNotEmpty()){
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

        binding.checkBox.setOnClickListener {
            if (binding.checkBox.isChecked){
                if(binding.firstNameEdt.text.isNotEmpty() && binding.lastNameEdt.text.isNotEmpty() && Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), binding.emailEdt.text.toString()) && binding.phoneEdt.text.toString().length > 11){
                    enableButton(true)
                }else{
                    enableButton(false)
                }
            }else{
                enableButton(false)
            }
        }

    }

    private fun enableButton(isButtonEnable:Boolean){

        if (isButtonEnable){
            binding.nextBtn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FF000000"))
            binding.nextBtn.isClickable=true
        }else{
            binding.nextBtn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#BABEC2"))
            binding.nextBtn.isClickable=false

        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RegFragmentOne().apply {
                arguments = Bundle().apply {

                }
            }
    }
}