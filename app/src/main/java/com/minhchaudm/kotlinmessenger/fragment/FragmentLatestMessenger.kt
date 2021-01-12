package com.minhchaudm.kotlinmessenger.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.minhchaudm.kotlinmessenger.R
import com.minhchaudm.kotlinmessenger.utils.addFragment
import com.minhchaudm.kotlinmessenger.utils.replaceFragment

class FragmentLatestMessenger:Fragment() {
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_latest_messenger,container,false)

        return  rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        verifiUserIsLoginIn()
    }
    private fun verifiUserIsLoginIn(){
        val uid = FirebaseAuth.getInstance().uid
        if (uid== null){
            replaceFragment(FragmentRegister.newInstance(),R.id.Fr_layout)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item?.itemId){
            R.id.nav_Messenger -> {
                replaceFragment(FragmentLatestMessenger.newInstance(),R.id.Fr_layout)
            }
            R.id.nav_new_Messenger -> {
              addFragment(FragmentNewMessenger.newInstance(),R.id.fr_latestMessenger)
            }
            R.id.nav_signOut_Messenger ->{
                replaceFragment(FragmentRegister.newInstance(),R.id.Fr_layout)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    companion object{
        fun newInstance () = FragmentLatestMessenger ()
    }
}