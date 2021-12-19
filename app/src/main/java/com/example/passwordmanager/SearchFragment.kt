package com.example.passwordmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.databinding.FragmentHomeBinding
import com.example.passwordmanager.databinding.FragmentSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class SearchFragment : Fragment(),ItemClicked {

    private var _binding: FragmentSearchBinding? = null //view binding
    private val binding get() = _binding!!
    private lateinit var matchedPassList: ArrayList<DataModel>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var passList: ArrayList<DataModel>
    private lateinit var adapter: PasswordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        val uid =
            firebaseAuth.currentUser!!.uid //getting the user id as we are storing the data respective to user

        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
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
        binding.recyclerViewSearch.adapter = adapter
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.notifyDataSetChanged()




        binding.editTextSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val query = query.toString().lowercase()
                filterQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.toString().lowercase()
                filterQuery(query)
                return true
            }
        })
    }

    private fun filterQuery(text: String) {
        //new array list that will hold the filtered data
        matchedPassList = arrayListOf()

        text?.let {
            passList.forEach { passlist ->
                if (passlist.title!!.contains(text,true)){
                    matchedPassList.add(passlist)
                }

            }
        }
        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        adapter.passList = matchedPassList
        adapter!!.notifyDataSetChanged()
    }
    override fun onItemClicked(item: DataModel) {
        //on click of the password item navigating to the view Password and we are sendeing the
        //id,username,title,password to that fragment with navigation arguments
        val action = SearchFragmentDirections.actionSearchFragmentToViewPassword(
            item.title!!,
            item.username!!,
            item.password!!,
            item.id!!
        )
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}