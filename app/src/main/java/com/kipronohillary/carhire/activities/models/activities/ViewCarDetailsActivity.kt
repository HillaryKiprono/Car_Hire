package com.kipronohillary.carhire.activities.models.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.kipronohillary.carhire.R
import com.kipronohillary.carhire.activities.models.models.CartCar
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
        checkCounter()
        val image=intent.getStringExtra("CAR_IMAGE")
        val carModel=intent.getStringExtra("CAR_MODEL")
        val carPrice=intent.getStringExtra("CAR_PRICE")
        val seatingCap=intent.getStringExtra("CAR_CAPACITY")
        val carDescription=intent.getStringExtra("CAR_DESCRIPTION")

        Glide.with(applicationContext)
            .load(image)
            .into(binding.carImagecart)

        binding.carmodelcart.text=carModel
        binding.carpricecart.text=carPrice
        binding.carcapacitycart.text=seatingCap
        binding.cardescriptioncart.text=carDescription

        binding.carrentbtn.setOnClickListener {
            //val cartItems = CartCar(image.toString(), carPrice.toString(), seatingCap.toString(),1)
            val cartItems=CartCar(image.toString(),carPrice.toString(),carModel.toString(),seatingCap.toString(),0.0,0)
            myRef.child("cart_items").push().setValue(cartItems)
            incrementCounter()
            finish()
        }
    }

    private fun incrementCounter() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(Utils.counter.toString(), sharedPreferences.getInt(Utils.counter.toString(),0)+1)
        editor.apply()
        editor.commit()
        val sharedIdValue = sharedPreferences.getInt(Utils.counter.toString(),0)
        binding.cartBadge.text = sharedIdValue.toString()
        binding.cartBadge.visibility = View.VISIBLE
    }
    private fun checkCounter(){
        val sharedIdValue = sharedPreferences.getInt(Utils.counter.toString(),0)
        if(sharedIdValue == 0){
            binding.cartBadge.visibility = View.INVISIBLE
        }
        else if (sharedIdValue >= 1){
            binding.cartBadge.text = sharedIdValue.toString()
            binding.cartBadge.visibility = View.VISIBLE
        }
    }

}