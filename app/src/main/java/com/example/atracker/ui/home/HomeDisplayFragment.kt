package com.example.atracker.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atracker.R
import com.example.atracker.databinding.FragmentHomeDisplayBinding
import com.example.atracker.ui.AlertDialogFragment
import com.example.atracker.ui.AlertDialogListener
import com.example.atracker.ui.MainActivity
import com.example.atracker.ui.MyPageViewModel
import com.example.atracker.utils.AlertApiObject
import com.example.atracker.utils.AlertType

class HomeDisplayFragment : Fragment(), HomeProgressOnclickListener {

//    private lateinit var homeViewModel: HomeViewModel
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

    private val lazyContext by lazy {
        requireContext()
    }




    private lateinit var homeMyCurrentStateTotalCircleView : com.example.atracker.utils.MyProgress

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val myPageViewModel: MyPageViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
//        homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeDisplayBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })



        homeMyCurrentStateTotalCircleView = binding.homeMyCurrentStateTotalCircleView

        binding.homeDisplayNestedSV.setOnScrollChangeListener{ v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val bottomDetector: Int = view!!.bottom - (binding.homeDisplayNestedSV.height + binding.homeDisplayNestedSV.scrollY)
            Log.d("homeDisplayNestedSV", "${bottomDetector}, ${binding.homeDisplayNestedSV.getHeight()}")
            if (bottomDetector == 0) {
                binding.homeDisplayScrollTopButton.visibility = View.INVISIBLE
            } else {
                binding.homeDisplayScrollTopButton.visibility = View.VISIBLE
            }
        }



        binding.homeProgressRV.also{
            it.layoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)
            it.setHasFixedSize(false)
            it.adapter = homeProgressAdapter
        }

//        binding.homeNotificationIV.setOnClickListener { view ->
//            view.findNavController().navigate(R.id.action_navigation_home_to_navigation_notification_display)
//        }
//
//        binding.homeMyPageIV.setOnClickListener { view ->
//            view.findNavController().navigate(R.id.action_navigation_home_to_navigation_my_page_display)
//        }

        binding.homeAddProgressFAB.setOnClickListener{view ->
            parentActivity.mainBottomNavigationDisappear()
            view.findNavController().navigate(R.id.action_navigation_home_to_navigation_home_add)
        }


        binding.homeDisplayScrollTopButton.setOnClickListener {
            binding.homeDisplayNestedSV.smoothScrollTo(0,0)
        }


        homeViewModel.homeDisplayArrayList.observe(viewLifecycleOwner, Observer {
            if (it.size == 0) {
                binding.homeDisplayPleaseWriteTV.visibility = View.VISIBLE
                binding.homeProgressCL.visibility = View.INVISIBLE
                binding.homeProgressHiddenCL.visibility = View.VISIBLE
            } else {
                binding.homeDisplayPleaseWriteTV.visibility = View.INVISIBLE
                binding.homeProgressCL.visibility = View.VISIBLE
                binding.homeProgressHiddenCL.visibility = View.INVISIBLE
            }

            homeProgressAdapter.submitList(it.toMutableList())
        })





        myPageViewModel.userExperienceType.observe(viewLifecycleOwner, Observer {
            binding.homeExperienceTypeTV.text = myPageViewModel.userExperienceType.value
            binding.homeMyTitleTV.text = myPageViewModel.userJobPosition.value
            binding.homeMyNickNameTV.text = myPageViewModel.userNickName.value
        })



        homeViewModel.portfolioNum1.observe(viewLifecycleOwner, Observer {
            binding.homeMyCurrentStatePercentTV1.text = it.toString()
        })

        homeViewModel.portfolioNum2.observe(viewLifecycleOwner, Observer {
            binding.homeMyCurrentStatePercentTV2.text = it.toString()

        })

        homeViewModel.portfolioNum3.observe(viewLifecycleOwner, Observer {
            binding.homeMyCurrentStatePercentTV3.text = it.toString()
        })

        homeViewModel.portfolioNumTotal.observe(viewLifecycleOwner, Observer {
            Log.d("portfolioNumTotal", "${it}")
            binding.homeMyCurrentStateTotalPassRateTV.text = it.toString()
            setProgress(homeViewModel.portfolioNum1.value!!, homeViewModel.portfolioNum2.value!!, homeViewModel.portfolioNum3.value!!)
        })



        //setProgress(homeViewModel.portfolioNum1.value!!, homeViewModel.portfolioNum2.value!!, homeViewModel.portfolioNum3.value!!)




        return root
    }


    fun setProgress(progress1: Int, progress2: Int, progress3: Int){
        homeMyCurrentStateTotalCircleView.progress = progress1
        homeMyCurrentStateTotalCircleView.secondaryProgress = progress1 + progress2
        homeMyCurrentStateTotalCircleView.thirdProgress = progress1 + progress2 + progress3
    }


//    fun ScrollView.smoothScrollToView(view: View) {
//        val y = computeDistanceToView(view)
//        ObjectAnimator.ofInt(this, "scrollY", y).apply {
//            duration = 1000L // 스크롤이 지속되는 시간을 설정한다. (1000 밀리초 == 1초)
//        }.start()
//    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickContainerView(view: View, position: Int, viewTag: String) {
        val applyId = homeViewModel.homeDisplayArrayList.value!![position].applyId

        homeViewModel.getApplyDetail(applyIds = arrayOf(applyId), includeContent = true)

        homeViewModel.homeApplyIdContent.observe(viewLifecycleOwner, Observer {

            homeViewModel.getApplyDetailFail.observe(viewLifecycleOwner, Observer {
                Log.d("getApplyDetailFail", "${it}")
                it.getContentIfNotHandled()?.let { boolean ->
                    if( boolean) {
                        parentActivity.showAlertInstance(AlertApiObject.alertDialogFragment)
                    } else {
                        val action = HomeDisplayFragmentDirections.actionNavigationHomeToNavigationHomeDetail(
                            progressIndex = applyId,
                            displayListPosition = position)
                        findNavController().navigate(action)

                        //parentActivity.mainBottomNavigationDisappear() // 없어도 navController 리스너가 해줌.
                    }
                }
            })


        })




    }

}