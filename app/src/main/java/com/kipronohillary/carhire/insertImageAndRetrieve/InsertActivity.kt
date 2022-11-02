package com.kipronodeveloper.onlinecarhire.insertImageAndRetrieve

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.kipronodeveloper.onlinecarhire.R
import com.kipronodeveloper.onlinecarhire.models.ImageTest
import java.util.UUID

class InsertActivity : AppCompatActivity() {

    private lateinit var image:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        image=findViewById(R.id.selectImageTest)
        image.setOnClickListener{

            loadGallery()
        }

    }

    private fun loadGallery() {
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,0)
    }
   var selectedPhotoUri:Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK &&data!=null) {
            selectedPhotoUri = data?.data
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            image.setImageBitmap(bitmap)

            uploadImage()

        }
    }

    private fun uploadImage() {
        if(selectedPhotoUri==null)return
        var imageFile=UUID.randomUUID().toString()
        var firestore=FirebaseStorage.getInstance().getReference("/ImagesTest2/$imageFile")
        firestore.putFile(selectedPhotoUri!!).addOnSuccessListener{

            firestore.downloadUrl.addOnSuccessListener {
                saveToRealTime(it.toString())
            }
        }.addOnFailureListener{

        }


    }

    private fun saveToRealTime(carUrl: String) {
        val uid= FirebaseAuth.getInstance().uid?:""
        var databaseRef=FirebaseDatabase.getInstance().getReference("/cImages/$uid").push()
        var carItem= ImageTest(uid,carUrl)
        databaseRef.setValue(carItem).addOnSuccessListener {
            Log.d(ContentValues.TAG, "Finally we saved the user to Firebase Database")

            Toast.makeText(this, "New Car item Saved Successfully", Toast.LENGTH_SHORT).show()
    }
}
}