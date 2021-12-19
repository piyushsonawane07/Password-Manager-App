package com.example.passwordmanager

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.databinding.FragmentSplashScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : Fragment() {

    private var _binding:FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        _binding = FragmentSplashScreenBinding.inflate(inflater,container,false)
        Handler().postDelayed(Runnable {
            if (firebaseAuth.currentUser!=null){
                val action = SplashScreenDirections.actionSplashScreenToPasswordFragment()
                findNavController().navigate(action)
            }else{
                val action = SplashScreenDirections.actionSplashScreenToSignInFragment()
                findNavController().navigate(action)
            }

        },1500)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}