package com.kipronohillary.carhire.activities.models.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.*
import com.kipronohillary.carhire.R
import com.kipronohillary.carhire.activities.models.models.CarDetails
import com.kipronohillary.carhire.adapters.CarRecyclerAdapter
import com.kipronohillary.carhire.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var  mAdapter: CarRecyclerAdapter
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var carArrayList : ArrayList<CarDetails>
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //config progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait and ensure you have Strong internet connection")
        progressDialog.setMessage("loading car details...")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.recyclerview.hasFixedSize()
        binding.recyclerview.layoutManager = GridLayoutManager(this, 2)
        carArrayList = arrayListOf<CarDetails>()



        fetchCarData()

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


//                    mAdapter.onItemClick={
//                        val intent= Intent(this@DashboardActivity,ViewCarDetailsActivity::class.java)
//                       // intent.putExtra("car",it)
//                        startActivity(intent)
//                    }




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