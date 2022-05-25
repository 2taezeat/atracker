package com.example.atracker.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.atracker.databinding.FragmentHomeWriteBinding
import com.example.atracker.ui.MainActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.R

import com.google.android.material.tabs.TabLayout








// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeWriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentHomeWriteBinding? = null
    private val binding get() = _binding!!

    private val parentActivity by lazy {
        activity as MainActivity
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeWriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private lateinit var homeWriteTabLayout : TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeWriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeWriteTabLayout = binding.homeWriteTabLayout

        addTabItem()

        binding.homeWritePlusButton1.setOnClickListener{
            val secondlayoout = this.layoutInflater.inflate(com.example.atracker.R.layout.layout2, null) as ConstraintLayout // inflating view from xml
            var params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                500 // This will define text view height
            )
            params.setMargins(10,10,10,10)
            secondlayoout.layoutParams = params
            secondlayoout.id = View.generateViewId()
            Log.d("test123_2", "${secondlayoout.id}")

            binding.homeWriteLL.addView(secondlayoout)

        }

        binding.homeWritePlusButton2.setOnClickListener{
            val thridlayoout = this.layoutInflater.inflate(com.example.atracker.R.layout.layout3, null) as ConstraintLayout // inflating view from xml
            var params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                500 // This will define text view height
            )
            params.setMargins(10,10,10,10)
            thridlayoout.layoutParams = params
            thridlayoout.id = View.generateViewId()


            val name = thridlayoout.findViewById(com.example.atracker.R.id.homeWriteQuestionTV) as TextView



            Log.d("test123_3", "${thridlayoout.id}")
            name.id = View.generateViewId()
            Log.d("test123_4", "${name.id}")


            binding.homeWriteLL.addView(thridlayoout)
        }


        return root

    }

    fun addTabItem() {
        homeWriteTabLayout.addTab(homeWriteTabLayout.newTab().setText("TAB-3"))
    }





}