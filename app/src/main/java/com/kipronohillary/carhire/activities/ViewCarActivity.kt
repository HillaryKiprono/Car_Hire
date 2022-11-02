package com.kipronodeveloper.onlinecarhire.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.kipronodeveloper.onlinecarhire.R
import com.kipronodeveloper.onlinecarhire.models.CarRecylcer

class ViewCarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_car)


        val food = intent.getParcelableExtra<CarRecylcer>("car")
        if (food != null) {
//            val carName: TextView = findViewById(R.id.ca)
//            carName.text = food.car_name

            val carModel: TextView = findViewById(R.id.carModelCart)
            carModel.text = food.car_model

            val carHirePrice: TextView = findViewById(R.id.carPriceCart)
            carHirePrice.text = food.car_hire_price

            val carSeatingCapacity: TextView = findViewById(R.id.carCapacityCart)
            carSeatingCapacity.text = food.car_seat_capacity
        }
    }
}