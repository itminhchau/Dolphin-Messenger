package com.minhchaudm.kotlinmessenger.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.minhchaudm.kotlinmessenger.R
import com.minhchaudm.kotlinmessenger.utils.replaceFragment
import kotlinx.android.synthetic.main.fragment_signup.view.*

class FragmentSignUp : Fragment(){
    private lateinit var rootView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_signup,container,false)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rootView.btLogin.setOnClickListener {
            replaceFragment(FragmentLogin.newInstance(),R.id.Fr_layout)
        }
        rootView.bt_SignUP.setOnClickListener {
            replaceFragment(FragmentRegister.newInstance(),R.id.Fr_layout)
        }
        rootView.bt_facebook.setOnClickListener {
            replaceFragment(FragmentLatestMessenger.newInstance(),R.id.Fr_layout)
        }
    }

    companion object{
        fun newInstance() = FragmentSignUp()
    }
}