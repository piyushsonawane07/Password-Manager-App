package com.example.passwordmanager

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.databinding.FragmentSetupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * Setting up master password of user if user is using app for the first time.
 */
class SetupFragment : Fragment() {

    companion object {
        const val TEST = "Test"
        const val passKey=""
    }

    private lateinit var databaseReference: DatabaseReference
    private var _binding: FragmentSetupBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference


        val aes = AES()

        binding.createButton.setOnClickListener {
            val pass = binding.masterPass.text.toString().trim()
            if (pass.isEmpty() || pass==" "){
                Toast.makeText(requireContext(),"Please Enter the Passwords !",Toast.LENGTH_SHORT).show()
            }else{
//                Log.d(TEST, pass)
                val encrypted = aes.encrypt(pass, passKey)  //encrypting the password and storing it on firebase
                databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                val data = DataModel("User",encrypted)
                databaseReference.child(firebaseAuth.currentUser!!.uid).setValue(data)
//                Log.d(TEST, encrypted)
                //navigating to home fragment
                val action = SetupFragmentDirections.actionSetupFragmentToHomeFragment()
                findNavController().navigate(action)
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}