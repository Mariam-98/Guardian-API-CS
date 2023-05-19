package com.example.weatherapics

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapics.data.SearchResultDto
import com.example.weatherapics.model.FilterEnum
import com.example.weatherapics.net.GuardianApiCallback
import com.example.weatherapics.net.GuardianFakeDataRepositoryImpl
import com.example.weatherapics.net.GuardianRealDataRepositoryImpl
import com.example.weatherapics.net.RetrofitClient.retrofit
import com.example.weatherapics.ui.viewModels.GuardianDemoViewModel
import com.example.weatherapics.ui.viewModels.GuardianViewModel

class GuardianFragment : Fragment() {

    private val guardianViewModel = GuardianViewModel()
    private lateinit var guardianResultRecyclerView: RecyclerView
    private lateinit var filterRecyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView
    private lateinit var simpleTextView: TextView
    private val queryMap: MutableMap<String, String> = mutableMapOf()

    //mvvm
    private val guardianDemoViewModel = GuardianDemoViewModel(GuardianFakeDataRepositoryImpl())

    private val guardianResultAdapter = GuardianResultAdapter {
        navigateToDetailsFragment(it)
    }

    private val filterAdapter = FilterAdapter {
        when (it) {
            FilterEnum.ORDER_BY -> navigateToOrderByDialog()
            FilterEnum.SHOW_ELEMENTS -> navigateToShowElementsDialog()
            else -> return@FilterAdapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        guardianViewModel.setGuardianData("weather", queryMap)


        guardianDemoViewModel.search("", queryMap)

    }

    override fun onStart() {
        super.onStart()
        val connectivityManager = context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkChangeCallBack = NetworkChangeCallBack(requireContext())
        connectivityManager.registerDefaultNetworkCallback(networkChangeCallBack)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_guardian, container, false)
        guardianResultRecyclerView = view.findViewById(R.id.resultRecyclerView)
        filterRecyclerView = view.findViewById(R.id.filterRecyclerView)
        toolbar = view.findViewById(R.id.toolbar)
        searchView = view.findViewById(R.id.searchView)
        simpleTextView = view.findViewById(R.id.simpleTextView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
        observeLiveData()
    }

    override fun onStop() {
        super.onStop()
//        connectivityManager.unregisterNetworkCallback(networkChangeCallBack)
    }

    private fun setupViews() {
        guardianResultRecyclerView.adapter = guardianResultAdapter
        filterRecyclerView.adapter = filterAdapter

        simpleTextView.text = guardianDemoViewModel.someText
    }

    private fun setupListeners() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                guardianDemoViewModel.someText = query ?: "EMPTY"
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    guardianViewModel.setGuardianData(newText, queryMap)
                }
                return true
            }
        })

    }

    private fun observeLiveData() {
        guardianViewModel.guardianLiveData.observe(requireActivity()) {
            guardianResultAdapter.setResultList(it)
        }

        guardianViewModel.orderByFilter.observe(requireActivity()) {
            Toast.makeText(requireContext(), "in observer", Toast.LENGTH_SHORT).show()
            queryMap[FilterEnum.ORDER_BY.value] = it
            guardianViewModel.setGuardianData("weather", queryMap)
        }

        guardianViewModel.showElementsFilter.observe(requireActivity()) {
            queryMap[FilterEnum.ORDER_BY.value] = it
            guardianViewModel.setGuardianData("weather", queryMap)
        }


        guardianDemoViewModel.searchResultListLiveData.observe(viewLifecycleOwner) {
            guardianResultAdapter.setResultList(it)
        }
    }

    private fun checkInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    private fun navigateToDetailsFragment(result: SearchResultDto) {
        findNavController().navigate(GuardianFragmentDirections.actionToGuardianDetailsFragment(result))
    }

    private fun navigateToOrderByDialog() {
        findNavController().navigate(GuardianFragmentDirections.actionToOrderByBottomSheetDialog())
    }

    private fun navigateToShowElementsDialog() {
        findNavController().navigate(GuardianFragmentDirections.actionToShowElementsBottomSheetDialog())
    }
}
