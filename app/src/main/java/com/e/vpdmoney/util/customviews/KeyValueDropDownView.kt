package com.e.vpdmoney.util.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.e.vpdmoney.model.response.KeyValue


class KeyValueDropDownView<T>(
    context: Context,
    attrs: AttributeSet?
) : AppCompatAutoCompleteTextView(context, attrs) {

    private var selectedPosition = -1

    var onItemClick: ((`object`: T) -> Unit)? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            if (isSelectionValid()) {
                onItemClick?.invoke(getItemObject())
                error = null
            }
        }
    }


    var selectedItem: KeyValue<T>
        get() = adapter.getItem(selectedPosition) as KeyValue<T>
        set(value) {
            val position = (adapter as ArrayAdapter<KeyValue<T>>).getPosition(value)
            Log.d("selecteditem", ": $position")
            if (position >= 0) {
                selectedPosition = position
                this.setText(value.toString(), false)
            }
        }

    fun getCheckableSpinnerSelectedItems (): MutableSet<KeyValue<T>> {
        if (this.adapter is CheckableAdapter<*>)
            return (adapter as CheckableAdapter<T>).selectedItems
        throw Exception("Adapter not instance of CheckableAdapter<*>")
    }

    fun getItemObject() = selectedItem.`object`

    fun getSelectedItemKey() = selectedItem.key

    fun getSelectedItemId() = selectedItem.id
    fun getSelectedItemName() = selectedItem.name

    fun isSelectionValid() = selectedPosition >= 0
}