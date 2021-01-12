package com.minhchaudm.kotlinmessenger.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.minhchaudm.kotlinmessenger.R
import kotlinx.android.synthetic.main.fragment_login.view.*

class FragmentLogin:Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var rootView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_login,container,false)
        mAuth = Firebase.auth
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rootView.bt_login.setOnClickListener {
            val mail = rootView.tv_email_login.text.toString()
            val pass = rootView.tv_password_login.text.toString()
            mAuth.signInWithEmailAndPassword(mail,pass)
        }
    }
    companion object{
        fun newInstance() = FragmentLogin()
    }
}