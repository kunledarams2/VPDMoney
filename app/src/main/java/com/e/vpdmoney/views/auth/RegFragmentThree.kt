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
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs


import com.e.vpdmoney.R
import com.e.vpdmoney.databinding.FragmentRegThreeBinding
import com.e.vpdmoney.model.request.CreateUserRequest
import com.e.vpdmoney.model.response.KeyValue
import com.e.vpdmoney.model.response.KeyValues


import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*




class RegFragmentThree : Fragment() {

    lateinit var binding: FragmentRegThreeBinding
    lateinit var bundle: Bundle
    private var selectedBizCategory=String()
    private val args: RegFragmentThreeArgs by navArgs()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bundle=it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentRegThreeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.headerWrapper.headerTv.text = "Tell us about your \nbusiness"
        binding.headerWrapper.subHeaderTv.text= "We need basic information about your business to finish setting up your Bloc account."
        binding.headerWrapper.subHeaderTvSignIn.visibility=View.GONE

        binding.createBtn.setOnClickListener {
            createUser()
        }
        binding.headerWrapper.navBtn.setOnClickListener {
            findNavController().navigate(R.id.action_regFragmentThree_to_regFragmentTwo, bundle)
        }

        binding.createBtn.isClickable = false

        setView()

    }

    private  fun setView(){

        // Read JSON from the raw resource file
        val jsonString = readJsonFromRawResource(R.raw.business_categories)
        val jsonArray = JSONArray(jsonString)

        // Convert JSONArray to a List
        val itemsList = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            itemsList.add(JSONObject(jsonArray.getString(i)).getString("label"))
        }

        setUpBusinessSpinner(itemsList)

        // Create an ArrayAdapter using the string list
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemsList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapter to the spinner
//        binding.buzzCategory.adapter = adapter
       /* binding.buzzCategory.onItemSelectedListener = object :OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                selectedBizCategory = p0!!.getItemAtPosition(position) as String
                if (!selectedBizCategory.contains("Select a category") ){
                    if (binding.buzNameEdt.text.isNotEmpty()){
                        enableButton(true)
                    }else{
                        enableButton(false)
                    }
                }else{
                    enableButton(false)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }*/

        binding.buzNameEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length > 2){
                    if(selectedBizCategory.isNotEmpty() && !selectedBizCategory.contains("Select a category")){
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
        binding.countryEdt.setText("Nigeria")



    }

    private fun enableButton(isButtonEnable:Boolean){

        if (isButtonEnable){
            binding.createBtn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FF000000"))
            binding.createBtn.isClickable=true
        }else{
            binding.createBtn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#BABEC2"))
            binding.createBtn.isClickable=false

        }
    }

    private fun setUpBusinessSpinner(category: MutableList<String>) {

        val keyValueTasks = KeyValues(category.map {
            KeyValue(
                it,
                it.lowercase(Locale.getDefault()),
                it.toString(), it
            )
        })

        (binding.buzzCategory as AutoCompleteTextView)
            .setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.dropdown_menu_item_dark,
                    keyValueTasks.keyValues
                )
            )



        binding.buzzCategory.onItemClick ={
            val selectedItem = it as String
            if (selectedItem != null && category.contains(selectedItem)) {

                if (!selectedItem.contains("Select a category") ){
                    if (binding.buzNameEdt.text.isNotEmpty()){
                        enableButton(true)
                    }else{
                        enableButton(false)
                    }
                }else{
                    enableButton(false)
                }

            } else {
                enableButton(false)
            }

        }






    }

    private fun readJsonFromRawResource(resourceId: Int): String {
        val inputStream = resources.openRawResource(resourceId)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        return bufferedReader.use { it.readText() }
    }

    private fun createUser(){
        val createUserRequest= CreateUserRequest(
            full_name =  args.createUserRequest.full_name,
            organization_name = binding.buzNameEdt.text.toString(),
            email =  args.createUserRequest.email,
            phone_number =  args.createUserRequest.phone_number,
            password = args.createUserRequest.password,
            country = "Nigeria",
            business_category = binding.buzzCategory.getSelectedItemName(),
            accepted_terms = args.createUserRequest.accepted_terms
        )

      /*  if(isNetworkAvailable(requireActivity())){
            binding.createBtn.showProgress {
                buttonTextRes = R.string.wait
                progressColor = Color.WHITE  }
            CoroutineScope(Dispatchers.IO).launch {
                val response = blocApiService.createUser(createUserRequest)

                withContext(Dispatchers.Main){
                    binding.createBtn.hideProgress("Create my account")
                    if (response.isSuccessful){
                        if (response.body()!!.success){
                            findNavController().navigate(R.id.action_regFragmentThree_to_blankFragment, bundle)
                        }else {
                            bundle.putString("errorMessage", response.body()!!.message)
                            findNavController().navigate(R.id.action_regFragmentThree_to_regFragmentOne, bundle)
                        }
                    }
                }
            }
        }else{
            Toast.makeText(requireContext(), "No internet connection...", Toast.LENGTH_LONG).show()
        }*/





    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RegFragmentThree().apply {
                arguments = Bundle().apply {

                }
            }
    }
}