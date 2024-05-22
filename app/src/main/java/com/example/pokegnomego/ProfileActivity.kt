package com.example.pokegnomego

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.pokegnomego.databinding.ActivityMainBinding
import com.example.pokegnomego.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityProfileBinding

    public fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.whatever, fragment)
        transaction.commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val back = findViewById<Button>(R.id.button_back2)
        back.setOnClickListener {
            val i = Intent(this@ProfileActivity, MainActivity::class.java)

            startActivity(i)

            finish()
        }

        binding.buttonMyPhotos.setOnClickListener {
            loadFragment(MyPhotosFragment())
        }

        binding.buttonRank.setOnClickListener {
            loadFragment(ThirdFragment())
        }

        binding.buttonMyAchievements.setOnClickListener {
            loadFragment(ThirdFragment())
        }

    }

}