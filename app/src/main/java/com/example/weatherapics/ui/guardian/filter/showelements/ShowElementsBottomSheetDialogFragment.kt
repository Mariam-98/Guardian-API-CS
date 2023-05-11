package com.example.weatherapics.ui.guardian.filter.showelements

import com.example.weatherapics.model.OrderByEnum
import com.example.weatherapics.ui.base.BaseSimpleBottomSheetDialogFragment

class ShowElementsBottomSheetDialogFragment : BaseSimpleBottomSheetDialogFragment() {
    override val items: List<String>
        get() = OrderByEnum.values().toMutableList().map { it.key }
}