package com.kipronohillary.carhire.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.kipronohillary.carhire.R
import com.kipronohillary.carhire.activities.models.models.CarDetails


class CarRecyclerAdapter(

    private  var context: Context,
    private var carArrayList: ArrayList<CarDetails>
):
    RecyclerView.Adapter<CarRecyclerAdapter.ViewHolder>() {

    var onItemClick:((CarDetails)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val arrayList=LayoutInflater.from(parent.context).inflate(R.layout.view_car_item,parent,false)
        return  ViewHolder(arrayList)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentcar=carArrayList[position]

        holder.cName.text=currentcar.car_name
        holder.cHirePrice.text=currentcar.car_hire_price
        holder.cModel.text=currentcar.car_model
        holder.carnumSeat.text=currentcar.car_seat_capacity
        holder.cDescription.text=currentcar.car_description


        Glide.with(context).load(carArrayList[position].carImageUrl)
            .into(holder.cImage).toString()

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(currentcar)
        }



    }


    override fun getItemCount(): Int {
     return  carArrayList.size
    }


    class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){

        var cImage:ImageView=itemView.findViewById(R.id.car_image_view)
        var cName:TextView=itemView.findViewById(R.id.car_name_view)
        var cModel:TextView=itemView.findViewById(R.id.car_model_view)
        var carnumSeat:TextView=itemView.findViewById(R.id.seatCapacity_view)
        var cHirePrice:TextView=itemView.findViewById(R.id.carPrice_view)
        var cDescription:TextView=itemView.findViewById(R.id.car_description_view)

    }


}


