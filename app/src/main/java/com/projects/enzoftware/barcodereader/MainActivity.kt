package com.projects.enzoftware.barcodereader

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.projects.enzoftware.barcodereader.fragments.AboutFragment
import com.projects.enzoftware.barcodereader.fragments.ReaderFragment
import com.projects.enzoftware.barcodereader.fragments.ScannedFragment
import com.projects.enzoftware.barcodereader.fragments.WelcomeFragment
import com.projects.enzoftware.barcodereader.utils.requestPermission
import java.io.File

class MainActivity : AppCompatActivity() {
    private var toolbar : android.support.v7.app.ActionBar ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = supportActionBar
        toolbar!!.title = getString(R.string.app_name)
        startFragmentTransaction(WelcomeFragment())

        val navigator: BottomNavigationView = findViewById(R.id.navigator)
        navigator.setOnNavigationItemSelectedListener{
            item ->
            when(item.itemId){
                R.id.navigation_list -> {
                    toolbar!!.title = getString(R.string.list)
                    startFragmentTransaction(ScannedFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_barcode -> {
                    toolbar!!.title = getString(R.string.app_name)
                    startFragmentTransaction(ReaderFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_info -> {
                    toolbar!!.title = getString(R.string.about)
                    startFragmentTransaction(AboutFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
        requestPermission(this,android.Manifest.permission.CAMERA)
        requestPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }


    private fun startFragmentTransaction(fragment:android.support.v4.app.Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.container,fragment)
                .commit()
    }
}


/* btnCameraRequest.setOnClickListener {
     val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
     val imageFileName = timeStamp + ".jpg"
     val storageDir : File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
     pictureImagePath = storageDir.absolutePath + "/" + imageFileName
     val file = File(pictureImagePath)
     val outputFileUri: Uri = Uri.fromFile(file)
     val takePictureIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
     takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,outputFileUri)
     startActivityForResult(takePictureIntent,captureCode)

 }*/
