package com.qbitscience.cameraappqbitscience

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap


import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity :AppCompatActivity(){
    lateinit var button: Button
    lateinit var image:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button=findViewById(R.id.camera)
        image=findViewById(R.id.image)
        //asks user to allow camera permission
        checkPermission(Manifest.permission.CAMERA,1888)

        //fetch image from camera after clicking picture
        var results= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
          result->
            image.setImageBitmap(result.data?.extras?.get("data") as Bitmap?)
        }

        button.setOnClickListener {
            var intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            results.launch(intent)

        }
    }
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }
}