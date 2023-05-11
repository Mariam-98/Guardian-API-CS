package com.example.weatherapics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapics.data.SearchResultDto
import com.example.weatherapics.model.FilterEnum
import com.example.weatherapics.model.OrderByEnum
import com.example.weatherapics.net.GuardianApiCallback
import com.example.weatherapics.net.GuardianRepositoryImpl
import com.example.weatherapics.net.RetrofitClient.retrofit
import com.example.weatherapics.ui.base.BaseSimpleBottomSheetDialogFragment
import com.example.weatherapics.ui.guardian.filter.orderby.OrderByBottomSheetDialogFragment

class GuardianFragment : Fragment(), GuardianApiCallback<List<SearchResultDto>> {

    private lateinit var guardianResultRecyclerView: RecyclerView
    private lateinit var filterRecyclerView: RecyclerView

    private val queryMap: MutableMap<String, String> = mutableMapOf()
    private val guardianRepo: GuardianRepositoryImpl by lazy { GuardianRepositoryImpl(retrofit) }

    private val guardianResultAdapter = GuardianResultAdapter {
        navigateToDetailsFragment(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        guardianRepo.search(queryMap, "weather", OrderByEnum.NEWEST, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_guardian, container, false)
        guardianResultRecyclerView = view.findViewById(R.id.guardianResultRecyclerView)
        filterRecyclerView = view.findViewById(R.id.guardianFilterRecyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        guardianResultRecyclerView.adapter = guardianResultAdapter
    }

    private fun setupListeners(){
        setFragmentResultListener(OrderByBottomSheetDialogFragment::javaClass.javaClass.simpleName) { _, bundle ->
            val selectedItem = bundle.getString(BaseSimpleBottomSheetDialogFragment.ITEM) ?: ""
//            val selectedItem = getSelectedSimpleItem()

            queryMap[FilterEnum.ORDER_BY.value] = selectedItem
            request(queryMap)
        }
    }

    private fun navigateToDetailsFragment(result: SearchResultDto) {
        findNavController().navigate(GuardianMainPageFragmentDirections.actionToGuardianDetailsFragment(result))
    }

    private fun navigateToOrderByDialog() {
        findNavController().navigate(GuardianFragmentDirections.actionToOrderByBottomSheetDialog())
    }

    private fun request(m: Map<String, String>) {
        guardianRepo.search(m, "weather", OrderByEnum.NEWEST, this)
    }

    override fun onSuccess(t: List<SearchResultDto>) {
        guardianResultAdapter.setWeatherList(t)
    }

    override fun onError(msg: String) {
        // TODO: show message dialog
    }

    override fun onFailure(e: Throwable) {

    }
}