package com.example.weatherapics.ui.guardian.filter.orderby

import com.example.weatherapics.model.OrderByEnum
import com.example.weatherapics.ui.base.BaseSimpleBottomSheetDialogFragment
import com.example.weatherapics.ui.viewModels.GuardianViewModel

class OrderByBottomSheetDialogFragment : BaseSimpleBottomSheetDialogFragment() {

    override val items = OrderByEnum.values().map { it.key }
    override fun setFilter(guardianViewModel: GuardianViewModel, filter: String) {
        guardianViewModel.setOrderByFilter(filter)
    }
}