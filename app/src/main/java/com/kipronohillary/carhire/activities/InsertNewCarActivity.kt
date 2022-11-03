package com.kipronohillary.carhire.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.kipronohillary.carhire.R
import com.kipronohillary.carhire.databinding.ActivityInsertNewCarBinding

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
        TODO("Not yet implemented")
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