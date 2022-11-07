package com.kipronohillary.carhire.activities.models.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kipronohillary.carhire.R
import com.kipronohillary.carhire.activities.models.activities.CheckoutActivity
import com.kipronohillary.carhire.activities.models.models.CartCar

class CartCarAdapter(private val context: Context, private val carArrayList: ArrayList<CartCar>, private val itemClickListener: CheckoutActivity)
    :RecyclerView.Adapter<CartCarAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_items_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itm=carArrayList[position]
        Glide.with(context)
            .load(itm.carImage)
            .into(holder.car_Image)

        holder.car_Model.text=itm.carModel
        holder.car_Price.text="Ksh. ${itm.carHirePrice}"

        holder.itemNumber.text=itm.counter.toString()

        holder.deleteItem.setOnClickListener{
            itemClickListener.decreaseFromCart(carArrayList[position],position)
        }
        holder.addItem.setOnClickListener{
            itemClickListener.increaseToCart(carArrayList[position],position)
        }
        holder.minusItem.setOnClickListener {
            itemClickListener.decreaseFromCart(carArrayList[position],position)
        }
    }

    override fun getItemCount(): Int {
        return carArrayList.size
    }
    fun clear(){
        val size:Int=carArrayList.size
        carArrayList.clear()
        notifyItemChanged(0,size)
    }



    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val car_Image: ImageView = view.findViewById(R.id.cart_item_image)
        val car_Model: TextView = view.findViewById(R.id.cart_item_model)
        val car_Price: TextView = view.findViewById(R.id.cart_item_price)
        val deleteItem: ImageView = view.findViewById(R.id.delete_cart_item)
        val addItem: ImageView = view.findViewById(R.id.add_cart_car)
        val minusItem: ImageView = view.findViewById(R.id.minus_cart_item)
        val itemNumber: TextView = view.findViewById(R.id.items_num)

    }
}