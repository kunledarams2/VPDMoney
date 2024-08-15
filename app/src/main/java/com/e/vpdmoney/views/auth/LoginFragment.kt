package com.e.vpdmoney.views.auth

import android.content.Intent
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
import com.e.vpdmoney.MainActivity
import com.e.vpdmoney.R
import com.e.vpdmoney.databinding.FragmentLoginBinding
import java.util.regex.Pattern


class LoginFragment : Fragment() {

    lateinit var binding:FragmentLoginBinding
    private var isValidated= false
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
        binding = FragmentLoginBinding.inflate(inflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signButn.setOnClickListener {
            if (isValidated){
                if (!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), binding.phEmailEdt.text.toString())){
                    binding.notification.innerNotifWrapper.visibility = View.VISIBLE
                    binding.notification.messageTv.text = "Invalided email address ...."
                    return@setOnClickListener
                }
                if (observeEntry()){

                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }


            }

        }

        binding.headerWrapper.navBtn.visibility = View.GONE
        binding.headerWrapper.headerTv.text = "Welcome Back"
        binding.headerWrapper.subHeaderTv.text = "Don’t have an account? "
        binding.headerWrapper.subHeaderTvSignIn.text = "Create one here."

        binding.phEmailEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.notification.innerNotifWrapper.visibility = View.GONE
                if(p0.toString().length > 2){
                    if(binding.pinPasswordEdt.text.isNotEmpty() && Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), binding.phEmailEdt.text.toString()) ){
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

        binding.pinPasswordEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length > 6){
                    if(  Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), binding.phEmailEdt.text.toString()) ){
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

    }

    private fun enableButton(isButtonEnable:Boolean){

        if (isButtonEnable){
            binding.signButn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0076B5"))
            binding.signButn.isClickable=true
            isValidated = true
        }else{
            binding.signButn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#BABEC2"))
            binding.signButn.isClickable=false
            isValidated = false

        }
    }

    private val special = Pattern.compile("[!@£¬#$'/%&*()_.,:;^`\"+=|<>?{}\\[\\]~-]")
    private val digit = Pattern.compile("[0-9]")
    private val uppercase = Pattern.compile("[A-Z]")
    private val lowercase = Pattern.compile("[a-z]")

    private fun observeEntry(): Boolean {
        val password = binding.pinPasswordEdt.text.toString()
        //check
        if(password.length < 6){
            binding.notification.innerNotifWrapper.visibility = View.VISIBLE
            binding.notification.messageTv.text = "Password must be 6 character long "
            return false

        }

        if(!uppercase.matcher(password).find()){
            binding.notification.innerNotifWrapper.visibility = View.VISIBLE
            binding.notification.messageTv.text = "Password must contain uppercase character  "
            return false

        }


        if(!lowercase.matcher(password).find()){
            binding.notification.innerNotifWrapper.visibility = View.VISIBLE
            binding.notification.messageTv.text = "Password must contain lowercase character  "
            return false

        }

        if(!digit.matcher(password).find()){
            binding.notification.innerNotifWrapper.visibility = View.VISIBLE
            binding.notification.messageTv.text = "Password must contain digit  "
            return false

        }
        return true
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}