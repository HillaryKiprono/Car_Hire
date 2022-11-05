package com.kipronohillary.carhire.activities.models.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.kipronohillary.carhire.R
import com.kipronohillary.carhire.activities.models.models.Utils
import com.kipronohillary.carhire.databinding.ActivityViewCarDetailsBinding

class ViewCarDetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityViewCarDetailsBinding
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityViewCarDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Get instance of the database and the reference
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference

        sharedPreferences=this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        //checkCounter()

        val image=intent.getStringExtra("CAR_IMAGE")
        val carPrice=intent.getStringExtra("CAR_PRICE")
        val seatingCap=intent.getStringExtra("CAR_CAPACITY")
        val carDescription=intent.getStringExtra("CAR_DESCRIPTION")

        Glide.with(applicationContext)
            .load(image)
            .into(binding.carImagecart)

        binding.carpricecart.text=carPrice
        binding.carcapacitycart.text=seatingCap
        binding.cardescriptioncart.text=carDescription
    }

}