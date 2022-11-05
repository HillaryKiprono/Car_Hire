package com.kipronohillary.carhire.activities.models.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kipronohillary.carhire.R
import com.kipronohillary.carhire.activities.models.activities.ItemClickListener
import com.kipronohillary.carhire.activities.models.models.CartCar

class CartCarAdapter(private val context: Context,private  val carArrayList: ArrayList<CartCar>,private val itemClickListener: ItemClickListener)
    :RecyclerView.Adapter<CartCarAdapter.ViewHolder>(){

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
//        val car_Image: ImageView = view.findViewById(R.id.cart_item_image)
//        val car_Model: TextView = view.findViewById(R.id.cart_item_name)
//        val car_Price: TextView = view.findViewById(R.id.cart_item_price)
//        val deleteItem: ImageView = view.findViewById(R.id.delete_cart_item)
//        val addItem: ImageView = view.findViewById(R.id.add_cart_item)
//        val minusItem: ImageView = view.findViewById(R.id.minus_cart_item)
//        val itemNumber: TextView = view.findViewById(R.id.items_num)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}