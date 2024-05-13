package com.example.pokegnomego

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.pokegnomego.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    public fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.whatever, fragment)
        transaction.commit()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //działa z poziomu aktywności
        loadFragment(FirstFragment())


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFirst.setOnClickListener {
            loadFragment(SecondFragment())
        }

        binding.buttonAchievements.setOnClickListener {
            loadFragment(ThirdFragment())
        }

        binding.buttonMap.setOnClickListener {
            val i = Intent(this@MainActivity, MapActivity::class.java)
            // on below line we are calling start activity method to start our activity.
            startActivity(i)
            // on below line we are calling finish to finish our main activity.
            finish()
        }

        binding.buttonProfile.setOnClickListener {
            val i = Intent(this@MainActivity, ProfileActivity::class.java)
            // on below line we are calling start activity method to start our activity.
            startActivity(i)
            // on below line we are calling finish to finish our main activity.
            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}