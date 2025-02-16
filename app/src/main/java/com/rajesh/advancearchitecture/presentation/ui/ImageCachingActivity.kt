package com.rajesh.advancearchitecture.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.rajesh.advancearchitecture.R
import com.rajesh.imagecache.cache.ImageCaching

class ImageCachingActivity : AppCompatActivity() {

    private lateinit var imageLoader: ImageCaching
    val URL1 = "https://i.pinimg.com/originals/93/09/77/930977991c52b48e664c059990dea125.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_caching)

        val imageView = findViewById<ImageView>(R.id.image1)
        val listBtn = findViewById<Button>(R.id.listBtn)
        val clearBtn = findViewById<Button>(R.id.clearBtn)
        imageLoader = ImageCaching.getInstance(this, 8089)
        imageLoader.displayImage(URL1, imageView, R.drawable.ic_launcher_background)

        listBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        clearBtn.setOnClickListener {
            imageLoader.clearCache()
        }
    }

}