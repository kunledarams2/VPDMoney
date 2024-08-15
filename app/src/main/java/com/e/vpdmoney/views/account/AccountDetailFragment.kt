package com.e.vpdmoney.views.account

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.vpdmoney.MainActivity
import com.e.vpdmoney.R
import com.e.vpdmoney.databinding.FragmentAccountDetailBinding
import com.e.vpdmoney.databinding.ViewAccountDetailsLayoutBinding
import com.e.vpdmoney.db.LoadingStatus
import com.e.vpdmoney.model.request.TransferRequest
import com.e.vpdmoney.util.Formatter
import com.e.vpdmoney.util.GlobalUtil
import com.e.vpdmoney.viewmodel.LocalViewModel
import com.e.vpdmoney.views.auth.RegFragmentThreeArgs
import com.e.vpdmoney.views.home.GroupTransactionAdapter
import com.e.vpdmoney.views.home.TransactionItemAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountDetailFragment : Fragment(),  TransactionItemAdapter.OnTransactionClickListener {

   private val args:AccountDetailFragmentArgs  by navArgs()
    lateinit var binding: FragmentAccountDetailBinding
    private lateinit var localViewModel:LocalViewModel

    protected val mainActivity: MainActivity
        get() {
            return activity as? MainActivity ?: throw IllegalStateException("Not attached!")
        }

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
        binding = FragmentAccountDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNav()
        binding.accountStatus.text = args.accountDetail.account_status
        when(args.accountDetail.account_status){
            "Frozen"->{
                binding.accountStatus.setTextColor(ColorStateList.valueOf(Color.parseColor("#EDA005")))
                binding.statusIndicator.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FEC523"))
                binding.accountStatusWrapper.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFF9E8"))
            }

            "Closed"->{
                binding.accountStatus.setTextColor(ColorStateList.valueOf(Color.parseColor("#BA3C44")))
                binding.statusIndicator.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D93C48"))
                binding.accountStatusWrapper.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFECEE"))
            }
        }

        binding.accountBalance.text = GlobalUtil.currencyFormat(( args.accountDetail.account_balance.toDouble()))
        binding.accName.text = args.accountDetail.account_name
        binding.navBtn.setOnClickListener {

            findNavController().navigate(AccountDetailFragmentDirections.actionAccountDetailFragmentToNavAccount())
            mainActivity.showBottomNav()
        }

        binding.imageView2.setOnClickListener {
            findNavController().navigate(AccountDetailFragmentDirections.actionAccountDetailFragmentToRecipientFragment(args.accountDetail))
        }

        binding.textView10.setOnClickListener {
            openBottom()
        }


        localViewModel.getTransferTransactionBySubAccount(args.accountDetail.account_number)

        localViewModel.loadingStatus.observe(viewLifecycleOwner){
            when(it){
                LoadingStatus.Success->{
                    if (localViewModel.transferTransaction.value.isNullOrEmpty()){
                        binding. emptyLayout.emptyWrapper.visibility=View.VISIBLE
                    }else{
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

    private fun openBottom(){
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val view = ViewAccountDetailsLayoutBinding.inflate(LayoutInflater.from(requireContext()), null)


        view.accountBankAccount.text =args.accountDetail.account_number
        view.accountHolder.text = args.accountDetail.account_holder
        view.accountName.text = args.accountDetail.account_name
        view.accountBankName.text = args.accountDetail.bank_name

        view.navBtn.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(view.root)
        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.show()

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AccountDetailFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onTransactionClick(transaction: TransferRequest) {
        findNavController().navigate(AccountDetailFragmentDirections.actionAccountDetailFragmentToRecipientFragment(args.accountDetail))
    }
}