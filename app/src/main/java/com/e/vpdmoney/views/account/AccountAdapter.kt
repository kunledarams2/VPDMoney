package com.e.vpdmoney.views.account

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.vpdmoney.databinding.AccountItemLayoutBinding
import com.e.vpdmoney.model.response.AccountDatum
import com.e.vpdmoney.util.GlobalUtil
import com.e.vpdmoney.util.callback.ClickListener

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    private var accountList:List<AccountDatum>? =null
    private var viewAccountListener: ClickListener<AccountDatum>? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountAdapter.AccountViewHolder{
        return  AccountViewHolder(AccountItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AccountAdapter.AccountViewHolder, position: Int) {
        holder.bindView(accountList!![position])
    }

    override fun getItemCount(): Int {
        return accountList!!.size
    }

    fun setData(operators:List<AccountDatum>){
        this.accountList = operators
        notifyDataSetChanged()
    }

    fun setViewAccountListener(clickListener: ClickListener<AccountDatum>){
        this.viewAccountListener = clickListener

    }



    inner class AccountViewHolder(private val binding: AccountItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(accountDatum: AccountDatum){
            binding.accountName.text = accountDatum.account_name
            binding.accountBalance.text = GlobalUtil.currencyFormat((accountDatum.account_balance.toDouble()))
            binding.accountStatus.text = accountDatum.account_status

            when(accountDatum.account_status){
                "Frozen"->{
                    binding.accountStatus.setTextColor(ColorStateList.valueOf(Color.parseColor("#EDA005")))
                    binding.statusIndicator.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FEC523"))
                    binding.accountStatusWrapper.backgroundTintList =ColorStateList.valueOf(Color.parseColor("#FFF9E8"))
                }

                "Closed"->{
                    binding.accountStatus.setTextColor(ColorStateList.valueOf(Color.parseColor("#BA3C44")))
                    binding.statusIndicator.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D93C48"))
                    binding.accountStatusWrapper.backgroundTintList =ColorStateList.valueOf(Color.parseColor("#FFECEE"))
                }
            }

            binding.cardView.setOnClickListener {
                viewAccountListener!!.onClick(accountDatum)
            }


        }
    }


}