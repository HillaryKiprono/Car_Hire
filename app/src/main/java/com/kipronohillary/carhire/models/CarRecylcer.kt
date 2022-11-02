package com.kipronodeveloper.onlinecarhire.models

import android.os.Parcel
import android.os.Parcelable

class CarRecylcer(

    val carImageUrl: String?="",
    val car_name: String? ="",
    val car_hire_price: String?="",
    val car_model: String?="",
    val car_seat_capacity: String? =""
   ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(carImageUrl)
        parcel.writeString(car_name)
        parcel.writeString(car_hire_price)
        parcel.writeString(car_model)
        parcel.writeString(car_seat_capacity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CarRecylcer> {
        override fun createFromParcel(parcel: Parcel): CarRecylcer {
            return CarRecylcer(parcel)
        }

        override fun newArray(size: Int): Array<CarRecylcer?> {
            return arrayOfNulls(size)
        }
    }


}

