package com.kipronohillary.carhire.activities.models.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.kipronohillary.carhire.R
import com.kipronohillary.carhire.activities.models.adapters.CartCarAdapter
import com.kipronohillary.carhire.activities.models.models.CartCar
import com.kipronohillary.carhire.activities.models.models.Utils
import com.kipronohillary.carhire.databinding.ActivityCheckoutBinding
import java.util.prefs.Preferences

class CheckoutActivity : AppCompatActivity(),ItemClickListener {
    private lateinit var binding:ActivityCheckoutBinding
    private lateinit var mDatabase:FirebaseDatabase
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mReference: DatabaseReference
    private lateinit var adapter:CartCarAdapter
    private  var cartList:ArrayList<CartCar>?=null
    private val sharedPrefFile="kotlinsharedpreference"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences=this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
        mDatabase=FirebaseDatabase.getInstance()
        mReference=mDatabase.getReference("cart_items")
        cartList= arrayListOf()

        mReference.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(i in snapshot.children){
                        val item =i.getValue(CartCar::class.java)
                        cartList!!.add(item!!)
                    }
                    var sum=0.0
                    var vat = 0.0
                    var total=0.0
                    for(itm:CartCar in cartList!!){

                        sum+=itm.carHirePrice.toDouble()*itm.counter
                        vat += itm.vat.toDouble()
                        total+=sum
                    }
                    binding.subtotalValue.text=sum.toString()

                    binding.totalValue.text = total.toString()

                    initialiseRecycler()
                }
                else{
                    binding.subtotalValue.text = "0.0"

                    binding.totalValue.text = "0.0"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
     override fun decreaseFromCart(item : CartCar, position : Int) {
        if (item.counter == 1){

           // binding.m.isClickable = false
           // binding.minus
        }
        item.counter -= 1
        val mQuery : Query = mReference.orderByChild("itemName").equalTo(item.carModel)
        mQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                for (mSnapshot in snapshot.children) {
                    mSnapshot.child("counter").ref.setValue(item.counter)
                    adapter.clear()
                    decrementCounter(1)
                }
            }
            override fun onCancelled(error : DatabaseError) {
            }
        })
    }
     override fun increaseToCart(item : CartCar, position : Int) {
        item.counter += 1
        val mQuery : Query = mReference.orderByChild("itemName").equalTo(item.carModel)
        mQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                for (mSnapshot in snapshot.children) {
                    mSnapshot.child("counter").ref.setValue(item.counter)
                    adapter.clear()
                    incrementCounter()
                }
            }
            override fun onCancelled(error : DatabaseError) {
            }
        })
    }

     override fun deleteFromCart(item : CartCar, position : Int) {
        val mQuery : Query = mReference.orderByChild("itemName").equalTo(item.carModel)
        mQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                if (snapshot.exists()) {
                    for (mSnapshot in snapshot.children) {
                        val itmCount = item.counter
                        mSnapshot.ref.removeValue()
                        removeItem(position)
                        adapter.clear()
                        decrementCounter(itmCount)
                    }
                }
            }
            override fun onCancelled(error : DatabaseError) {
            }
        })
    }

    private fun incrementCounter(){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(Utils.counter.toString(), sharedPreferences.getInt(Utils.counter.toString(), 0) + 1)
        editor.apply()
        editor.commit()
        val sharedIdValue = sharedPreferences.getInt(Utils.counter.toString(), 0)

       // items_num.text = sharedIdValue.toString()

    }

    private fun decrementCounter(num : Int){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(Utils.counter.toString(), sharedPreferences.getInt(Utils.counter.toString(), 0) - num)
        editor.apply()
        editor.commit()
        val sharedIdValue = sharedPreferences.getInt(Utils.counter.toString(), 0)
        //binding.m = sharedIdValue.toString()
        binding.subtotalValue.text=sharedIdValue.toString()
    }

    private fun removeItem(position : Int){
        cartList?.removeAt(position)
        adapter.notifyItemRemoved(position)
    }


    private fun initialiseRecycler() {
        mRecyclerView = findViewById(R.id.cart_recycler)
        mRecyclerView.hasFixedSize()
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        adapter = CartCarAdapter(applicationContext, cartList!!, this@CheckoutActivity)
        mRecyclerView.adapter = adapter
    }
}