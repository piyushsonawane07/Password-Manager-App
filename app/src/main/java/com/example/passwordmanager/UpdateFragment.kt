package com.example.passwordmanager

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.passwordmanager.databinding.FragmentUpdateBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Update the data of user
 */
class UpdateFragment : Fragment() {

    companion object {
        const val passKey=""
    }

    private var _binding:FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val navArgs:UpdateFragmentArgs by navArgs()

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        val aes = AES()

        //get the arguments from the ViewPassword Fragment we passed
        val uid = firebaseAuth.currentUser!!.uid
        val id = navArgs.id
        val title:String = navArgs.title
        val username:String = navArgs.username
        val pass = navArgs.password


        binding.titleEtUpdate.setText(title)
        binding.usernameEtUpdate.setText(username)
        binding.passEtUpdate.setText(pass)

        binding.updateBtn.setOnClickListener {
            val titleUp = binding.titleEtUpdate.text.toString()
            val usernameUp = binding.usernameEtUpdate.text.toString()
            val passUp = binding.passEtUpdate.text.toString().trim()
            val encPass = aes.encrypt(passUp, passKey)

            if (passUp.isEmpty() || titleUp.isEmpty()){
                Toast.makeText(requireContext(),"Please Check All Fields !", Toast.LENGTH_SHORT).show()
            }else{
                val dataMap = mapOf<String,String>("title" to titleUp,"username" to usernameUp,"password" to encPass)
                databaseReference.child("Users").child(uid).child("data").child(id).updateChildren(dataMap)
//                Log.d(TEST,"title : $titleUp")
//                Log.d(TEST,"username : $usernameUp")
//                Log.d(TEST,"pass : $encPass")
//                Log.d(TEST,"id : $id")

                // here we used map for updating the data.

                showSnackBar(view)

                val action = UpdateFragmentDirections.actionUpdateFragmentToHomeFragment()
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
        val title: TextView = customSnack.findViewById(R.id.text)
        title.text = "Updated"
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