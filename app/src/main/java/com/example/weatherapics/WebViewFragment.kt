package com.example.weatherapics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.core.content.ContextCompat.registerReceiver

class WebViewFragment : Fragment() {

    private lateinit var webView: WebView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_view, container, false)
        webView = view.findViewById(R.id.webView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews(){
        val result = arguments?.let { WebViewFragmentArgs.fromBundle(it).searchResult }
        result?.webUrl?.let { webView.loadUrl(it) }

        webView.webViewClient = WebViewManager.client
    }

    private fun setupListeners(){
        webView.settings.javaScriptEnabled= true
        WebViewManager.setOnKeyListener(webView)
    }
}