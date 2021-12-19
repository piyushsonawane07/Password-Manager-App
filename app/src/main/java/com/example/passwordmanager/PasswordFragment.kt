package com.example.passwordmanager

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.passwordmanager.databinding.FragmentPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * In this class we are checking the password of user after signing in.
 */
class PasswordFragment : Fragment() {

   companion object {
       const val passKey=""
   }

    private var _binding:FragmentPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        binding.nextButton.setOnClickListener {
            val pass = binding.masterPassEt.text.toString().trim()
            if (pass.isEmpty() || pass==" "){
                Toast.makeText(requireContext(),"Please Enter the Passwords !",Toast.LENGTH_SHORT).show()
            }else{
                checkPass(pass)
            }
        }

    }

    /**
     * To check the master password
     */
    private fun checkPass(pass:String){
        val aes = AES()
        val uid = firebaseAuth.currentUser!!.uid
        var masterPass: String
        // Log.d(SetupFragment.TEST, "UID : $uid")

        //to check password which user has set while signing in
        databaseReference.child("Users").child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    //getting the masterPassword
                    masterPass = snapshot.child("masterPass").value.toString()

//                    Log.d(SetupFragment.TEST, "mp : $masterPass")

                    val dec = aes.decrypt(masterPass,passKey) //decrypting the key to check the master password

//                    Log.d(SetupFragment.TEST, "dec : $dec")

                    if (pass == dec) {
                        //if master password is correct then navigating to home fragment
                        val action = PasswordFragmentDirections.actionPasswordFragmentToHomeFragment()
                        findNavController().navigate(action)
                    }else{
                        Toast.makeText(requireContext(),"Please Check Pass",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.d("Test",error.message);
                }
            })

    }

}