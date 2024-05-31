package com.example.pokegnomego

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class PhotoGalleryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        val back = findViewById<Button>(R.id.button_back3)
        back.setOnClickListener {
            val i = Intent(this@PhotoGalleryActivity, ProfileActivity::class.java)
            startActivity(i)
            finish()
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        val photoFiles = getPhotosFromStorage()
        adapter = PhotoAdapter(photoFiles)
        recyclerView.adapter = adapter
    }

    private fun getPhotosFromStorage(): List<File> {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return storageDir?.listFiles()?.toList() ?: emptyList()
    }
}