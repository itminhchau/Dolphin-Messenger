package com.minhchaudm.kotlinmessenger.model

class User(val uid: String, val userName: String, val profileImage: String){
    constructor(): this("","","")
}