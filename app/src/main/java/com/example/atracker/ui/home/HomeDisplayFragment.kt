package com.example.atracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atracker.R
import com.example.atracker.databinding.FragmentHomeDisplayBinding
import com.example.atracker.ui.MainActivity

class HomeDisplayFragment : Fragment(), HomeProgressOnclickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeDisplayBinding? = null
    private val parentActivity by lazy {
        activity as MainActivity
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val homeProgressAdapter by lazy {
        HomeProgressAdapter(this)
    }


    private lateinit var homeMyCurrentStateTotalCircleView : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeDisplayBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        homeMyCurrentStateTotalCircleView = binding.homeMyCurrentStateTotalCircleView

        binding.homeProgressRV.also{
            it.layoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)
            it.setHasFixedSize(false)
            it.adapter = homeProgressAdapter
        }

        binding.homeNotificationIV.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_navigation_community_to_navigation_notification_display)
        }

        binding.homeMyPageIV.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_navigation_community_to_navigation_my_page_display)
        }

        binding.homeAddProgressIV.setOnClickListener{view ->
            view.findNavController().navigate(R.id.action_navigation_community_to_navigation_home_add)

        }


        homeProgressAdapter.submitList(homeViewModel.homeProgressArrayList.value)


        homeMyCurrentStateTotalCircleView.max = 100

        setProgressTo(60)
        setSecondaryProgressTo(40)


        return root
    }


    fun setProgressTo(progress: Int){
        homeMyCurrentStateTotalCircleView.progress = progress
    }

    fun setSecondaryProgressTo(progress: Int){
        homeMyCurrentStateTotalCircleView.secondaryProgress = progress
    }


//    fun setSecondaryProgressTo(progress: Int){
//        homeMyCurrentStateTotalCircleView.secondaryProgress = progress
//    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickContainerView(view: View, position: Int, viewTag: String) {
        findNavController().navigate(R.id.action_navigation_community_to_navigation_home_detail)
    }
}