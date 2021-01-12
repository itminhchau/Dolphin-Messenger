package com.minhchaudm.kotlinmessenger.fragment

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.minhchaudm.kotlinmessenger.R
import com.minhchaudm.kotlinmessenger.model.User
import com.minhchaudm.kotlinmessenger.utils.replaceFragment
import kotlinx.android.synthetic.main.fragment_register.view.*
import java.util.*


class FragmentRegister:Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var rootView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_register,container,false)
        mAuth = FirebaseAuth.getInstance();
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rootView.bt_Register.setOnClickListener {
           performRegister()
        }
        rootView.bt_select_photo.setOnClickListener {
            Log.d("FragmentRegister","choose photo")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)

        }
    }
    var selectPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            selectPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(rootView.context.contentResolver, selectPhotoUri)
            rootView.circleImageView_select_photo.setImageBitmap(bitmap)
            rootView.bt_select_photo.alpha = 0f
//            val bitmapDrawable = BitmapDrawable(bitmap)
//            rootView.bt_select_photo.setBackgroundDrawable(bitmapDrawable)
        }
    }
    private  fun performRegister(){
        val email = rootView.edit_Email.text.toString()
        val pass = rootView.edit_Password.text.toString()
        if (email.isEmpty() || pass.isEmpty()){
            Toast.makeText(this.context,"Please enter email and pass",Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("FragmentRegister","Email: ${email}")
        Log.d("FragmentRegister","Pass: ${pass}")
        // auth firebase
        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                // else successful
                Log.d("FragmentRegister","successful completer create with uid:${it.result?.user?.uid}")
            }
            .addOnFailureListener {
                Log.d("FragmentRegister","create fail ${it.message}")
            }
        upLoadImageOnFirebase()
    }
    private fun upLoadImageOnFirebase(){
        if(selectPhotoUri == null) return
        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$fileName")
        ref.putFile(selectPhotoUri!!)
            .addOnSuccessListener {
                Log.d("FragmentRegister","upload image success ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("FragmentRegister","locationUrl:$it")
                    upLoadUserToFirebase(it.toString())
                }
                    .addOnFailureListener {  }
            }

    }
    private fun upLoadUserToFirebase(profileImage: String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val use = User(uid,rootView.edit_User.text.toString(),profileImage)
        ref.setValue(use).addOnSuccessListener {
            Log.d("FragmentRegister","set value success")
            replaceFragment(FragmentLatestMessenger.newInstance(),R.id.Fr_layout)
        }
    }
    companion object{
        fun newInstance() = FragmentRegister()
    }
//    class User(val uid: String, val userName: String, val profileImage: String){
//
//    }
}