package com.kipronohillary.carhire.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.kipronohillary.carhire.activities.models.CarDetails
import com.kipronohillary.carhire.databinding.ActivityInsertNewCarBinding
import java.util.UUID

class InsertNewCarActivity : AppCompatActivity() {
    private lateinit var binding:ActivityInsertNewCarBinding
    private  lateinit var progressDialog:ProgressDialog

    private lateinit var c_name: String
    private lateinit var c_model: String
    private lateinit var c_hire_price: String
    private lateinit var c_seat_capacity: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityInsertNewCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Dear Admin Please Wait As we upload New Car")
        progressDialog.setMessage("Saving New Car Details Activity")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.selectCarImage.setOnClickListener{
            launchGallery()
        }

        binding.addNewCar.setOnClickListener {
            performDataValidation()
        }


    }

    private fun performDataValidation() {
        c_name=binding.carName.text.toString()
        c_model=binding.carModel.text.toString()
        c_hire_price=binding.carPrice.text.toString()
        c_seat_capacity=binding.carSeatingCapacity.text.toString()


        if(TextUtils.isEmpty(c_name)){
            Toast.makeText(this, "Car name required", Toast.LENGTH_SHORT).show()
        }
        else if(TextUtils.isEmpty(c_model)){
            Toast.makeText(this, "car Model required", Toast.LENGTH_SHORT).show()
        }

        else if(TextUtils.isEmpty(c_hire_price)){
            Toast.makeText(this, "car Hire Price required", Toast.LENGTH_SHORT).show()
        }
        else if(TextUtils.isEmpty(c_seat_capacity)){
            Toast.makeText(this, "car Seating Capacity required", Toast.LENGTH_SHORT).show()
        }
        else{

            uploadImageToFirebaseStorage()

        }
    }

    private fun uploadImageToFirebaseStorage() {
        progressDialog.show()
        if(selectedPhotoUri==null)return
        val filename=UUID.randomUUID().toString()
        val ref=FirebaseStorage.getInstance().getReference("/CarImages/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnCompleteListener{
                ref.downloadUrl.addOnSuccessListener {
                    saveCarDetailsToRealtime(it.toString())
                }
            }
    }

    private fun saveCarDetailsToRealtime(carImageUrl: String) {
       progressDialog.show()
        val uid=FirebaseAuth.getInstance().uid?:""
        var databaseReference=FirebaseDatabase.getInstance().getReference("/Car_Items/$uid").push()
        var carItem= CarDetails(uid,c_name,c_model,c_hire_price,c_seat_capacity,carImageUrl)
        databaseReference.setValue(carItem).addOnSuccessListener { 
            progressDialog.dismiss()
            Toast.makeText(this, "New Car item Saved Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { 
            progressDialog.dismiss()
            Toast.makeText(this, "Failed to Save Car Items to firebase", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launchGallery() {
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,0)
    }

    var selectedPhotoUri: Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK  && data!=null)
        {
            Log.d(TAG, "onActivityResult: Photo was selected")
            selectedPhotoUri=data.data

            var bitmap=MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)
            binding.selectCarImage.setImageBitmap(bitmap)
        }
    }
}