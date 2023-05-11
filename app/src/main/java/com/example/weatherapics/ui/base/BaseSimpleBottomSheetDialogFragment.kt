package com.example.weatherapics.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapics.R
import com.example.weatherapics.SimpleItemAdapter
import com.example.weatherapics.ui.base.BaseSimpleBottomSheetDialogFragment.Companion.ITEM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseSimpleBottomSheetDialogFragment : BottomSheetDialogFragment() {

    abstract val items: List<String>

    private val adapter = SimpleItemAdapter {
        setFragmentResult(this.javaClass.simpleName, Bundle().apply { putString(ITEM, it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_dialog_filter, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        adapter.updateItems(items)
        return view
    }

    open fun onItemClicked(item: String) {
        dismiss()
    }

    companion object {
        const val ITEM = "base_bottom_sheet_dialog_fragment_item"
    }
}

fun Fragment.getSelectedSimpleItem(): String? = arguments?.getString(ITEM)