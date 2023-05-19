package com.example.weatherapics.ui.guardian.filter.showelements

import com.example.weatherapics.model.ShowElementsEnum
import com.example.weatherapics.ui.base.BaseSimpleBottomSheetDialogFragment
import com.example.weatherapics.ui.viewModels.GuardianViewModel

class ShowElementsBottomSheetDialogFragment : BaseSimpleBottomSheetDialogFragment() {
    override val items: List<String>
        get() = ShowElementsEnum.values().map { it.key }

    override fun setFilter(guardianViewModel: GuardianViewModel, filter: String) {
        guardianViewModel.setShowElementsFilter(filter)
    }
}