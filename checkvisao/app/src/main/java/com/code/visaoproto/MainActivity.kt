package com.code.visaoproto

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.code.visaoproto.barcode.BarcodeActivity
import com.code.visaoproto.facedetect.FaceDetectActivity
import com.code.visaoproto.imagelabeler.ImageLabelingActivity
import com.code.visaoproto.textrecog.TextRecognitionActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        val PHOTO_REQ_CODE = 234
        @JvmStatic
        val EXTRA_DATA = "data"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTakeExtPhoto.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePhotoIntent, PHOTO_REQ_CODE)
                }
                else -> {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CAMERA),
                        CameraActivity.CAMERA_PERM_CODE
                    )
                }
            }
        }

        btnCameraActivity.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }

        btnBarcodeActivity.setOnClickListener {
            startActivity(Intent(this, BarcodeActivity::class.java))
        }

        btnFaceDetectActivity.setOnClickListener {
            startActivity(Intent(this, FaceDetectActivity::class.java))
        }

        btnLabelerActivity.setOnClickListener {
            startActivity(Intent(this, ImageLabelingActivity::class.java))
        }

        btnTextRecogActivity.setOnClickListener {
            startActivity(Intent(this, TextRecognitionActivity::class.java))
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == PHOTO_REQ_CODE) {
            if (data != null) {
                val bitmap = data?.extras?.get(EXTRA_DATA) as Bitmap
                imgThumb.setImageBitmap(bitmap)
                return
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CameraActivity.CAMERA_PERM_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePhotoIntent, PHOTO_REQ_CODE)
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Permission Error")
                    .setMessage("Camera Permission not provided")
                    .setPositiveButton("OK") { _, _ -> finish() }
                    .setCancelable(false)
                    .show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}