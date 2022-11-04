package com.kipronohillary.carhire.activities.models.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.kipronohillary.carhire.R
import com.kipronohillary.carhire.activities.models.models.CarDetails

class CarDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        val food = intent.getParcelableExtra<CarDetails>("car")
        if (food != null) {
//            val carName: TextView = findViewById(R.id.ca)
//            carName.text = food.car_name

            val carModel: TextView = findViewById(R.id.carmodelcart)
            carModel.text = food.car_model

            val carHirePrice: TextView = findViewById(R.id.carpricecart)
            carHirePrice.text = food.car_hire_price

            val carSeatingCapacity: TextView = findViewById(R.id.carcapacitycart)
            carSeatingCapacity.text = food.car_seat_capacity
        }
    }
}