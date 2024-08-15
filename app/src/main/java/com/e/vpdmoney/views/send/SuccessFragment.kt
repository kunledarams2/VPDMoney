package com.e.vpdmoney.views.send

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.e.vpdmoney.R
import com.e.vpdmoney.db.LoadingStatus
import com.e.vpdmoney.viewmodel.LocalViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SuccessFragment : Fragment() {

    private lateinit var localViewModel: LocalViewModel
    private val args:SuccessFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            localViewModel.storeTransferTransaction(args.transferRequest)

            localViewModel.loadingStatus.observe(viewLifecycleOwner){
                when(it){
                    LoadingStatus.Success->{
                        findNavController().navigate(SuccessFragmentDirections.actionSuccessFragmentToTransactionReceiptFragment(args.transferRequest))

                    }
                    is LoadingStatus.Loading->{

                    }
                    is LoadingStatus.Error->{

                    }
                }
            }

        }, 3000)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SuccessFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}