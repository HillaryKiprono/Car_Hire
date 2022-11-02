package com.kipronodeveloper.onlinecarhire.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.*
import com.kipronohillary.carhire.adapters.CarRecyclerAdapter
import com.kipronodeveloper.onlinecarhire.databinding.ActivityDisplayCarsBinding
import com.kipronodeveloper.onlinecarhire.models.CarRecylcer


class DisplayCarsActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
     var context: Context? = null
    private lateinit var  mAdapter: CarRecyclerAdapter
    private lateinit var binding: ActivityDisplayCarsBinding
    private lateinit var carArrayList : ArrayList<CarRecylcer>
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayCarsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //config progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait and ensure you have Strong internet connection")
        progressDialog.setMessage("loading car details...")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.recyclerview.hasFixedSize()
        binding.recyclerview.layoutManager = GridLayoutManager(this, 2)
        carArrayList = arrayListOf<CarRecylcer>()



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
                        val carData = cars.getValue(CarRecylcer::class.java)
                        carArrayList.add(carData!!)

                        progressDialog.dismiss()

                    }

                     mAdapter = CarRecyclerAdapter(applicationContext,carArrayList)
                    binding.recyclerview.adapter = mAdapter


                    mAdapter.onItemClick={
                        val intent=Intent(this@DisplayCarsActivity,ViewCarActivity::class.java)
                        intent.putExtra("car",it)
                        startActivity(intent)
                    }




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

