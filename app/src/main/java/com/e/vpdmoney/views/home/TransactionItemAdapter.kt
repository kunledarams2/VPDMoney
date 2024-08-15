package com.e.vpdmoney.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.vpdmoney.R
import com.e.vpdmoney.databinding.TransactionHolderLayoutBinding
import com.e.vpdmoney.model.request.TransferRequest
import com.e.vpdmoney.util.GlobalUtil
import com.e.vpdmoney.util.callback.ClickListener

class TransactionItemAdapter(private val items:List<TransferRequest>, private val listener: OnTransactionClickListener) : RecyclerView.Adapter<TransactionItemAdapter.ItemViewHolder>() {


    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    var clickListener: ClickListener<TransferRequest>?=null

    interface OnTransactionClickListener {
        fun onTransactionClick(transaction: TransferRequest)
    }

    fun setOnClickListener(clickListener: ClickListener<TransferRequest>){
        this.clickListener = clickListener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(TransactionHolderLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false))


    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

           holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


    inner  class ItemViewHolder(private val binding: TransactionHolderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransferRequest) {

            binding.transTimeType.text = "${item.payment_method} • ${item.transaction_date} "
            binding.transAmount.text = GlobalUtil.currencyFormat((item.amount.toDouble()))
            binding.transName.text = item.receiver

            if (item.transaction_status =="failed"){
                binding.paymentStatusImage.setImageResource(R.drawable.failed_icon)
            }

            binding.transWrapper.setOnClickListener {
                listener.onTransactionClick(item)
            }


        }
    }
}