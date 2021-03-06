package com.jayneel.socialmedia

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.jayneel.socialmedia.Model.userModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Add_PostViewModel:ViewModel() {
    val database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("Post")
    var data = MutableLiveData<userModel>()
    fun savedata(uid: String,dis:String,img:String,postid:String,username:String){
//        "${n.monthValue}${n.dayOfMonth}${n.year}".toLong()*-1
        var n=LocalDateTime.now()
        var s=""
        if(n.second>10) {
            s=n.second.toString()
        }
        else{
            s="0"+n.second.toString()
        }
        val Post=HashMap<String,Any>()
        Post["uid"]=uid
        Post["disc"]=dis
        Post["dateTime"]="${n.format(DateTimeFormatter.BASIC_ISO_DATE)}${n.hour}${n.minute}${s}".toLong()*-1
        Post["username"]=username
        Post["img"]=img
        Post["postid"]=postid
        myRef.child(postid).setValue(Post)

    }
    fun getdata(uid: String): MutableLiveData<userModel>? {
        var myRef= database.getReference("user")
        val userprofile=object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(userModel::class.java)!!
                data.value=value
                Log.d("TAG", "Value is: $value")
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        }
        myRef.child(uid).addListenerForSingleValueEvent(userprofile)
        return data
    }

}