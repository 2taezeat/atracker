package com.example.atracker.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.atracker.R
import com.example.atracker.databinding.FragmentMyPageDisplayBinding
import com.example.atracker.ui.login.LoginActivity
import com.example.atracker.utils.AlertType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyPageDisplayFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private val lazyContext by lazy {
        requireContext()
    }


    companion object {
        @JvmStatic fun newInstance(param1: String, param2: String) =
            MyPageDisplayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding : FragmentMyPageDisplayBinding
    private lateinit var googleSignInClient : GoogleSignInClient


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentMyPageDisplayBinding>(inflater, R.layout.fragment_my_page_display, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(lazyContext, gso)


        binding.myPageLogoutTV.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            googleSignInClient.signOut().addOnCompleteListener {
                //activity!!.finish()
                //startLogin()

                showAlert(AlertType.TYPE10)
            }
        }


//        binding.myPageBackButton.setOnClickListener { view ->
//            val navController = view.findNavController()
//            navController.popBackStack()
//        }



        return binding.root
    }

    fun startLogin() {
        val intent = Intent(lazyContext, LoginActivity::class.java)
        ContextCompat.startActivity(lazyContext, intent, null)
        activity!!.finish()
    }


    private fun showAlert(alertType: AlertType){
        val alertDialogFragment = AlertDialogFragment.instance(
            object : AlertDialogListener {
                override fun onLeftClick() {
                }

                override fun onCenterClick() {

                }

                override fun onRightClick() {
                    when (alertType) {
                        AlertType.TYPE10 -> {
                            startLogin()
                        }
                    }
                }
            },
            alertType,
            null
        )

        alertDialogFragment.show(childFragmentManager, AlertDialogFragment.TAG)
    }

}