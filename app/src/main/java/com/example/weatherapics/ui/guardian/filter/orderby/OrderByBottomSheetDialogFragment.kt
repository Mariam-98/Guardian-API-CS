package com.example.weatherapics.ui.guardian.filter.orderby

import com.example.weatherapics.model.OrderByEnum
import com.example.weatherapics.ui.base.BaseSimpleBottomSheetDialogFragment

class OrderByBottomSheetDialogFragment : BaseSimpleBottomSheetDialogFragment() {

    override val items = OrderByEnum.values().toMutableList().map { it.key }
}