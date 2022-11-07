package com.kipronohillary.carhire.activities.models.activities

import com.kipronohillary.carhire.activities.models.models.CartCar

interface ItemClickListener {
       fun increaseToCart(item: CartCar, position: Int)
       fun decreaseFromCart(item : CartCar, position : Int)
       fun deleteFromCart(item : CartCar, position : Int)
}
