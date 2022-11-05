package com.kipronohillary.carhire.activities.models.models

import android.os.Parcel
import android.os.Parcelable

class CarDetails(

    val car_model: String?="",
    val car_hire_price: String?="",
    val car_seat_capacity: String?="",
    val car_description: String?="",
    val car_name: String?="",
    val carImageUrl: String?=""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(car_model)
        parcel.writeString(car_hire_price)
        parcel.writeString(car_seat_capacity)
        parcel.writeString(car_description)
        parcel.writeString(car_name)
        parcel.writeString(carImageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CarDetails> {
        override fun createFromParcel(parcel: Parcel): CarDetails {
            return CarDetails(parcel)
        }

        override fun newArray(size: Int): Array<CarDetails?> {
            return arrayOfNulls(size)
        }
    }

}