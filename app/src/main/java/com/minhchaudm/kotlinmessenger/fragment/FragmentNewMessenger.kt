package com.minhchaudm.kotlinmessenger.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

import com.minhchaudm.kotlinmessenger.R
import com.minhchaudm.kotlinmessenger.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.fragment_newmessenger.view.*
import kotlinx.android.synthetic.main.item_user_recyclerview.view.*


class FragmentNewMessenger: Fragment() {
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_newmessenger,container,false)

        return rootView
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val adapter = GroupAdapter<GroupieViewHolder>()
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//        adapter.add(UserItem())
       fetchUser()
//        rootView.recycle_new_Messenger.adapter = adapter
    }
    private fun fetchUser(){
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                snapshot.children.forEach {
                    Log.d("FragmentNewMessenger",it.toString())
                    val use = it.getValue(User::class.java)
                    if (use!= null){
                        adapter.add(UserItem(use,rootView.context))
                    }
                    rootView.recycle_new_Messenger.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    class  UserItem(val user: User,val context: Context): Item<GroupieViewHolder>(){
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.rootView.item_txt_user.text = user.userName
            Picasso.with(context).load(user.profileImage).into(viewHolder.itemView.item_circleImageView)
        }

        override fun getLayout(): Int {
            return R.layout.item_user_recyclerview
        }

    }
    companion object{
        fun newInstance() = FragmentNewMessenger()
    }
}