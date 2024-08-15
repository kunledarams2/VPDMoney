package com.e.vpdmoney.views.auth

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
import com.e.vpdmoney.databinding.FragmentRegTwoBinding
import com.e.vpdmoney.model.request.CreateUserRequest

import java.util.regex.Pattern



class RegFragmentTwo : Fragment() {

    lateinit var binding: FragmentRegTwoBinding
    lateinit var bundle:Bundle

    private var containLowerCase = false
    private var containUpperCase = false
    private var containNumber = false
    private var lengthSix = false
    private val args: RegFragmentTwoArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bundle = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegTwoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.headerWrapper.headerTv.text = "Set your password"
        binding.headerWrapper.subHeaderTv.text = "The best passwords are usually the ones you’ve never used anywhere else."
        binding.headerWrapper.subHeaderTvSignIn.visibility = View.GONE

        binding.nextBtn.setOnClickListener {
            val createUserRequest= CreateUserRequest(
                full_name =  args.createUserRequest.full_name,
                organization_name = "",
                email =  args.createUserRequest.email,
                phone_number =  args.createUserRequest.phone_number,
                password =  binding.edtPassword.text.toString(),
                country = "",
                business_category = "",
                accepted_terms = args.createUserRequest.accepted_terms,
            )
            findNavController().navigate(RegFragmentTwoDirections.actionRegFragmentTwoToRegFragmentThree(createUserRequest))
        }
        binding.headerWrapper.navBtn.setOnClickListener {
            findNavController().navigate(R.id.action_regFragmentTwo_to_regFragmentOne, bundle)

        }

        binding.edtPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                observeEntry()
                val password = p0.toString()
                if(lengthSix &&containLowerCase && containUpperCase && containNumber){
                    enableButton(true)
                }else{
                    enableButton(false)
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.nextBtn.isClickable=false
    }

    private val special = Pattern.compile("[!@£¬#$'/%&*()_.,:;^`\"+=|<>?{}\\[\\]~-]")
    private val digit = Pattern.compile("[0-9]")
    private val uppercase = Pattern.compile("[A-Z]")
    private val lowercase = Pattern.compile("[a-z]")

    private fun observeEntry(){
        val password = binding.edtPassword.text.toString()
        //check
        if(password.length > 6){
            lengthSix =true
            binding.sixLenImage.setImageResource(R.drawable.oval_blue)

        }else{
            lengthSix = false
            binding.sixLenImage.setImageResource(R.drawable.oval_gray)
        }

        if(uppercase.matcher(password).find()){
            containUpperCase =true
            binding.uppercaseImage.setImageResource(R.drawable.oval_blue)

        }else{
            containUpperCase = false
            binding.uppercaseImage.setImageResource(R.drawable.oval_gray)
        }


        if(lowercase.matcher(password).find()){
            containLowerCase =true
            binding.lowercaseImage.setImageResource(R.drawable.oval_blue)

        }else{
            containLowerCase = false
            binding.lowercaseImage.setImageResource(R.drawable.oval_gray)
        }

        if(digit.matcher(password).find()){
            containNumber =true
            binding.atNumberImage.setImageResource(R.drawable.oval_blue)

        }else{
            containNumber = false
            binding.atNumberImage.setImageResource(R.drawable.oval_gray)
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
            RegFragmentTwo().apply {
                arguments = Bundle().apply {

                }
            }
    }
}