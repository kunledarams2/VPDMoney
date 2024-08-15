package com.e.vpdmoney.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.vpdmoney.MainActivity
import com.e.vpdmoney.databinding.FragmentHomePageBinding
import com.e.vpdmoney.db.LoadingStatus
import com.e.vpdmoney.model.request.TransferRequest
import com.e.vpdmoney.util.Formatter
import com.e.vpdmoney.viewmodel.LocalViewModel
import com.e.vpdmoney.views.send.SuccessFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment(),
    TransactionItemAdapter.OnTransactionClickListener{

    lateinit var localViewModel: LocalViewModel
    lateinit var binding:FragmentHomePageBinding

//    protected val mainActivity: MainActivity
//        get() {
//            return activity as? MainActivity ?: throw IllegalStateException("Not attached!")
//        }


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
        binding = FragmentHomePageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        localViewModel.getTransferTransaction()

        localViewModel.loadingStatus.observe(viewLifecycleOwner){
            when(it){
                LoadingStatus.Success->{
                    if (!localViewModel.transferTransaction.value.isNullOrEmpty()){
                        val groupedItems = groupTransactionsByDate(localViewModel.transferTransaction.value!!)
                        val groupTransactionAdapter = GroupTransactionAdapter(groupedItems, this)

                        binding.apply {
                            emptyLayout.emptyWrapper.visibility=View.GONE
                            recyTransaction.apply {
                                adapter = groupTransactionAdapter
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(false)
                            }
                        }
                    }

                }
                is LoadingStatus.Loading->{

                }
                is LoadingStatus.Error->{

                }
            }
        }
    }

    private fun groupTransactionsByDate(transactions: List<TransferRequest>): Map<String, List<TransferRequest>> {
        return transactions.groupBy { Formatter.formatDate(it.transaction_date) }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomePageFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onTransactionClick(transaction: TransferRequest) {

        findNavController().navigate(HomePageFragmentDirections.actionNavHomeToTransactionReceiptFragment(transaction))
//        mainActivity.hideBottomNav()
    }
}