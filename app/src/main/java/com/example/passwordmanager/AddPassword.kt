package com.example.passwordmanager

import android.graphics.Color
import android.icu.text.CaseMap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.databinding.FragmentAddPasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddPassword : Fragment() {

    companion object {
        const val passKey=""
    }

    private var _binding:FragmentAddPasswordBinding?= null
    private val binding get() = _binding!!

    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.title = "Add Password"

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        val uid = firebaseAuth.currentUser!!.uid

        binding.saveBtn.setOnClickListener {
            val aes = AES()

            //getting all the values from the edittext
            val title = binding.titleEt.text.toString()
            val username = binding.usernameEt.text.toString()
            val pass = binding.passEt.text.toString().trim()

            val encPass = aes.encrypt(pass, passKey) //encrypting the password that user entered

//            Log.d(TEST, "title : $title")
//            Log.d(TEST, "username : $username")
//            Log.d(TEST, "encPass : $encPass")

            if (title.isEmpty() || title==" " || pass.isEmpty()){
                Toast.makeText(requireContext(), "Please check all fields !", Toast.LENGTH_SHORT).show()
            }
            else{

                //Users -> our db name on firebase
                // |_user id
                //    |_data
                //        |_id (here under this node we are going to store/add the data),(this we are taking as key)
                //           |_ id (the above id we have stored),(key)
                //           |_ name (for this we have given the default value that is User)
                //           |_ password
                //           |_ title
                //           |_ username

                databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                val key = databaseReference.child("Users").child(uid).child("data").push().key.toString()
                //getting the key that the firebase creates while storing the data

               // Log.d(TEST,"key $key")
                val data = DataModel("User", title, username, encPass,key)
                databaseReference.child(firebaseAuth.currentUser!!.uid).child("data").child(key).setValue(data) //storing the data under our push key generated

                showSnackBar(view)
                val action = AddPasswordDirections.actionAddPasswordToHomeFragment()
                findNavController().navigate(action)
            }
        }

    }

    //custom snackBar
    private fun showSnackBar(view: View){
        val snackBar:Snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        val customSnack:View = layoutInflater.inflate(R.layout.custom_snackbar,null)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout:Snackbar.SnackbarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setPadding(30,0,30,20)
        val title:TextView = customSnack.findViewById(R.id.text)
        title.text = "Added"
        val textView:TextView = customSnack.findViewById(R.id.action_text)
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