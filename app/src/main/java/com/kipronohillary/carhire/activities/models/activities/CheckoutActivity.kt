package com.kipronohillary.carhire.activities.models.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.kipronohillary.carhire.R
import com.kipronohillary.carhire.activities.models.models.CartCar
import java.util.prefs.Preferences

class CheckoutActivity : AppCompatActivity() {
    private lateinit var mDatabase:FirebaseDatabase
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mReference: DatabaseReference
    private  var cartList:ArrayList<CartCar>?=null
    private val sharedPrefFile="kotlinsharedpreference"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        sharedPreferences=this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
        mDatabase=FirebaseDatabase.getInstance()
        mReference=mDatabase.getReference("cart_items")
        cartList= arrayListOf()

        mReference.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}