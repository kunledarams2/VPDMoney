package com.e.vpdmoney.util.customviews

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.e.vpdmoney.model.response.KeyValue


class CheckableAdapter<T>(
    ctx: Context,
    var resource: Int,
    var items: MutableList<KeyValue<T>>,
    val selectedItems: MutableSet<KeyValue<T>>,
    private val onItemCheckedListener:
        (item: KeyValue<T>, isChecked: Boolean, selectedItems: MutableSet<KeyValue<T>>) -> Unit
) : ArrayAdapter<KeyValue<T>>(ctx, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view as TextView
        val item = items[position]
        val isSelected = selectedItems.contains(item)
        textView.text = item.name
        /*if (isSelected)
            textView.drawableStart = view.drawable(R.drawable.ic_selected)
        else textView.drawableStart = view.drawable(R.drawable.ic_unselected)
        textView.setOnClickListener {
            if (selectedItems.contains(item)) {
                selectedItems.remove(item)
                onItemCheckedListener(item, false, selectedItems)
                textView.drawableStart = view.drawable(R.drawable.ic_unselected)
            }
            else {
                selectedItems.add(item)
                onItemCheckedListener(item, true, selectedItems)
                textView.drawableStart = view.drawable(R.drawable.ic_selected)
            }
        }*/
        return view
    }

}