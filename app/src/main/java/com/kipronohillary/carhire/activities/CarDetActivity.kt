package com.kipronodeveloper.onlinecarhire.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.kipronodeveloper.onlinecarhire.R
import com.kipronodeveloper.onlinecarhire.models.CarRecylcer

class CarDetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_det)


        val food=intent.getParcelableExtra<CarRecylcer>("car")
        if(food!=null) {
            val carName: TextView = findViewById(R.id.car_name_cart)
            carName.text = food.car_name

            val carModel: TextView = findViewById(R.id.car_model_cart)
            carModel.text = food.car_model

            val carHirePrice: TextView = findViewById(R.id.car_hire_price_cart)
            carHirePrice.text = food.car_hire_price

            val carSeatingCapacity: TextView = findViewById(R.id.car_seat_capacity_cart)
            carSeatingCapacity.text = food.car_seat_capacity

        }
    }
}
