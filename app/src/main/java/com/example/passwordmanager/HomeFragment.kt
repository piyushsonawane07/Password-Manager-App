package com.example.passwordmanager

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(), ItemClicked, PopupMenu.OnMenuItemClickListener {

    private var _binding: FragmentHomeBinding? = null //view binding
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private lateinit var passList: ArrayList<DataModel>
    private lateinit var adapter: PasswordsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.hide()


        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        val uid =
            firebaseAuth.currentUser!!.uid //getting the user id as we are storing the data respective to user

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        passList = arrayListOf()

        //using this below query we are accessing the place where we are going to take the data
        //Users -> our db name on firebase
        // |_user id
        //    |_data
        //        |_id (generated while pushing the data)
        //         * |_ id (the above id we have stored)  from here we are accessing the four properties marked with *
        //           |_ name
        //         * |_ password
        //         * |_ title
        //         * |_ username

        databaseReference.child("Users").child(uid).child("data").addChildEventListener(object :
            ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //getting the data from their positions
                val title =
                    snapshot.child("title").value.toString() //we need to convert the data we got to string
                val username = snapshot.child("username").value.toString()
                val pass = snapshot.child("password").value.toString()
                val id = snapshot.child("id").value.toString()
                val data = DataModel("", title, username, pass, id)

//                Log.d("Test", "Title : $title")
//                Log.d("Test", "Username : $username")
//                Log.d("Test", "Pass : $pass")
//                Log.d("Test", "Key : $id")

                passList.add(data).also {
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                adapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        adapter = PasswordsAdapter(this, passList)
        binding.recyclerView.adapter = adapter
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.notifyDataSetChanged()


        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddPassword()
            findNavController().navigate(action)
        }

        //here created the custom popupmenu
        binding.optiosMenu.setOnClickListener {
            val popup = PopupMenu(requireContext(), it)
            popup.setOnMenuItemClickListener(this)
            popup.inflate(R.menu.menu)
            popup.show()
        }

        binding.searchIcon.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            findNavController().navigate(action)
        }

    }


    override fun onItemClicked(item: DataModel) {
        //on click of the password item navigating to the view Password and we are sendeing the
        //id,username,title,password to that fragment with navigation arguments
        val action = HomeFragmentDirections.actionHomeFragmentToViewPassword(
            item.title!!,
            item.username!!,
            item.password!!,
            item.id!!
        )
        findNavController().navigate(action)
    }


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.signout -> {
                firebaseAuth.signOut() //shortcut for the signOut
                val action = HomeFragmentDirections.actionHomeFragmentToSignInFragment()
                findNavController().navigate(action)
            }
            R.id.profile -> { // navigating to profile
                val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
                findNavController().navigate(action)
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}