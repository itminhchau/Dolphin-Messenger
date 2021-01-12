package com.minhchaudm.kotlinmessenger.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.minhchaudm.kotlinmessenger.R
import com.minhchaudm.kotlinmessenger.fragment.FragmentSignUp
import com.minhchaudm.kotlinmessenger.utils.replaceFragment
import com.xwray.groupie.GroupAdapter
import kotlinx.android.synthetic.main.fragment_newmessenger.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(FragmentSignUp.newInstance(),
            R.id.Fr_layout
        )
    }
}