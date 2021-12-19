package com.example.passwordmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.databinding.FragmentPasswordBinding
import com.example.passwordmanager.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * User Profile Fragment Showing the email of user in the fragment and logout funvtionality.
 */
class ProfileFragment : Fragment() {

    private var _binding:FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.userEmailTv.setText(firebaseAuth.currentUser!!.email)

        binding.signoutButton.setOnClickListener {
            firebaseAuth.signOut()
            val action = ProfileFragmentDirections.actionProfileFragmentToSignInFragment()
            findNavController().navigate(action)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}