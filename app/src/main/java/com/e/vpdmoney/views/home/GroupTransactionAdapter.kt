package com.e.vpdmoney.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.e.vpdmoney.databinding.GroupTransactionItemBinding
import com.e.vpdmoney.model.request.TransferRequest

class GroupTransactionAdapter(private val groupedTransactions: Map<String, List<TransferRequest>>,  private val listener: TransactionItemAdapter.OnTransactionClickListener) : RecyclerView.Adapter<GroupTransactionAdapter.GroupViewHolder>() {

    private val dates = groupedTransactions.keys.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {

        return GroupViewHolder(GroupTransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(dates[position], groupedTransactions[dates[position]]!!)
    }

    override fun getItemCount(): Int = dates.size

  inner  class GroupViewHolder(private var binding: GroupTransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(date: String, transactions: List<TransferRequest>) {
            binding.headerTextView.text = date
            val nestedAdapter = TransactionItemAdapter(transactions, listener)
            binding.nestedRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
            binding.nestedRecyclerView.adapter = nestedAdapter


        }
    }
}