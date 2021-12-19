package com.example.passwordmanager

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.passwordmanager.databinding.FragmentViewPasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewPassword : Fragment() {


    companion object {
       const val passKey=""
    }

    private var _binding:FragmentViewPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private val args:ViewPasswordArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aes = AES()
        val logo = Logo() //class for setting the logo in viewPassword Fragment and with the passwordItem in HomeFragment

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        val uid = firebaseAuth.currentUser!!.uid

        //get the arguments from the HomeFragment we passed
        val id = args.id
        val title = args.title
        val username = args.username
        val pass = args.pass

        val decPass = aes.decrypt(pass, passKey) //decrypting the pass we got to show the user

        logo.setViewLogo(binding.logoView,title) //using this method of the Logo class we set the logo in the ViewPassword Fragment

        //setting data to their positions
        binding.titleView.setText(title)
        binding.usernameView.setText(username)
        binding.passView.setText(decPass)

        //to delete the stored password
        binding.deleteBtn.setOnClickListener {

            //going to the id where data is stored and deleting it from firebase and navigating back to HomeFragment
            databaseReference.child("Users").child(uid).child("data").child(args.id).removeValue()
            showSnackBar(view)
            val action = ViewPasswordDirections.actionViewPasswordToHomeFragment()
            findNavController().navigate(action)
        }

        binding.updateButton.setOnClickListener {
            //navigating to update fragment for updating values
            val action = ViewPasswordDirections.actionViewPasswordToUpdateFragment(id,title,username,decPass)
            findNavController().navigate(action)
        }

    }

    //custom snackBar
    private fun showSnackBar(view: View){
        val snackBar:Snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        val customSnack:View = layoutInflater.inflate(R.layout.custom_snackbar,null)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout:Snackbar.SnackbarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setPadding(30,0,30,20)
        val title: TextView = customSnack.findViewById(R.id.text)
        title.text = "Delete"
        val textView: TextView = customSnack.findViewById(R.id.action_text)
        textView.setOnClickListener {
            snackBar.dismiss()
        }
        snackBarLayout.addView(customSnack,0)
        snackBar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }



}