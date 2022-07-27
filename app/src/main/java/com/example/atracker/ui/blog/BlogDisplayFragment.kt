package com.example.atracker.ui.blog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.atracker.BuildConfig
import com.example.atracker.databinding.FragmentBlogDisplayBinding

class BlogDisplayFragment : Fragment() {

    private lateinit var blogViewModel: BlogViewModel
    private var _binding: FragmentBlogDisplayBinding? = null
    private val binding get() = _binding!!
    private val blogWebURL = BuildConfig.BLOG_WEB_URL


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBlogDisplayBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.blogDisplayWebView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
        }

        binding.blogDisplayWebView.loadUrl(blogWebURL)





        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}