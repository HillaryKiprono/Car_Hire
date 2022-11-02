package com.kipronodeveloper.onlinecarhire.activities

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
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.kipronodeveloper.onlinecarhire.databinding.ActivityAddCarBinding
import com.kipronodeveloper.onlinecarhire.models.CarDetails
import java.util.*

class AddCarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCarBinding
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //config progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Dear Admin please wait as we Upload New Car")
        progressDialog.setMessage("Uploading Car Details...")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.selectCarImage.setOnClickListener{
            launchGallery()
        }

        binding.addNewCar.setOnClickListener{
            PerformDataValidation()
        }
    }



    private fun launchGallery() {
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,0)

    }

    var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK &&data!=null){
            Log.d(TAG, "onActivityResult: Photo was selected")
            selectedPhotoUri = data.data
            var bitmap=MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)
            binding.selectCarImage.setImageBitmap(bitmap)
        }
    }
    private lateinit var c_name: String
    private lateinit var c_model: String
    private lateinit var c_hire_price: String
    private lateinit var c_seat_capacity: String
    private fun PerformDataValidation() {
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

        if (selectedPhotoUri==null)return

        val filename=UUID.randomUUID().toString()
        val ref=FirebaseStorage.getInstance().getReference("/CarImages/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener{
                Log.d(TAG, "uploadImageToFirebaseStorage: Sucessfully uploaded to firebase")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d(TAG, "uploadImageToFirebaseStorage: File location:$it")

                    saveCarDetailsToFirebase(it.toString())

                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
            }


    }

    private fun saveCarDetailsToFirebase(carImageUrl: String) {
        progressDialog.show()
        val uid=FirebaseAuth.getInstance().uid?:""
       var databaseRef=FirebaseDatabase.getInstance().getReference("/car_Items/$uid").push()
        var carItem= CarDetails(uid,c_name,c_model,c_hire_price,c_seat_capacity,carImageUrl)
        databaseRef.setValue(carItem).addOnSuccessListener {
            Log.d(TAG, "Finally we saved the user to Firebase Database")
            progressDialog.dismiss()
            Toast.makeText(this, "New Car item Saved Successfully", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener{
                Log.d(TAG, "saveCarDetailsToFirebase: ${it.message}")
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to Save CAr Items to firebase", Toast.LENGTH_SHORT).show()
            }


    }


}


