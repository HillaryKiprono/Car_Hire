package com.kipronohillary.carhire.activities.models.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.*
import com.kipronohillary.carhire.activities.models.models.CarDetails
import com.kipronohillary.carhire.activities.models.models.Utils
import com.kipronohillary.carhire.adapters.CarRecyclerAdapter
import com.kipronohillary.carhire.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var  mAdapter: CarRecyclerAdapter
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var carArrayList : ArrayList<CarDetails>
    private lateinit var progressDialog: ProgressDialog

    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPreferences: SharedPreferences
    private var sharedIdValue : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


       sharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        sharedIdValue = sharedPreferences.getInt(Utils.counter.toString(), 0)
        checkCounter()

        binding.shoppingCart.setOnClickListener{

            startActivity(Intent(applicationContext, CheckoutActivity::class.java))

        }


        //config progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait and ensure you have Strong internet connection")
        progressDialog.setMessage("loading car details...")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.recyclerview.hasFixedSize()
        binding.recyclerview.layoutManager = GridLayoutManager(this, 2)
        carArrayList = arrayListOf<CarDetails>()


        binding.searchCarByName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
            }
        })

        binding.searchCarByNumOfSeats.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                filterbyCapacity(editable.toString())
            }
        })

        binding.searchCarByHirePrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                filterbyPrice(editable.toString())
            }
        })



        fetchCarData()

    }



    private fun checkCounter() {
        val sharedIdValue = sharedPreferences.getInt(Utils.counter.toString(), 0)
        if(sharedIdValue == 0){
            binding.cartBadge.visibility=View.INVISIBLE

        }
        else if (sharedIdValue >= 1){
            binding.cartBadge.text=sharedIdValue.toString()
            binding.cartBadge.visibility=View.VISIBLE
        }
    }

    fun filter(e: String) {
        val filteredList = ArrayList<CarDetails>()
        for (item in carArrayList!!) {
            if (item.car_name?.toLowerCase()?.contains(e.toLowerCase())!!) {
                filteredList.add(item)
            }
        }
        mAdapter= CarRecyclerAdapter(applicationContext, filteredList!!)
        binding.recyclerview.adapter= mAdapter
    }

    private fun filterbyCapacity(capacity: String) {
        val filteredCapacityList=ArrayList<CarDetails>()
        for(item in carArrayList!!){
            if(item.car_seat_capacity?.toLowerCase()?.contains(capacity.toLowerCase())!!){
                filteredCapacityList.add(item)
            }
        }
        mAdapter= CarRecyclerAdapter(applicationContext, filteredCapacityList!!)
        binding.recyclerview.adapter= mAdapter

    }

    private fun filterbyPrice(capacity: String) {
        val filteredPriceList=ArrayList<CarDetails>()
        for(item in carArrayList!!){
            if(item.car_hire_price?.toLowerCase()?.contains(capacity.toLowerCase())!!){
                filteredPriceList.add(item)
            }
        }
        mAdapter= CarRecyclerAdapter(applicationContext, filteredPriceList!!)
        binding.recyclerview.adapter= mAdapter

    }



    private fun fetchCarData() {
        progressDialog.show()

        dbRef = FirebaseDatabase.getInstance().getReference("car_Items")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    progressDialog.dismiss()
                    for (cars in snapshot.children) {
                        val carData = cars.getValue(CarDetails::class.java)
                        carArrayList.add(carData!!)

                        progressDialog.dismiss()

                    }

                    mAdapter = CarRecyclerAdapter(applicationContext,carArrayList)
                    binding.recyclerview.adapter = mAdapter

                }
                else{
                    progressDialog.dismiss()
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}