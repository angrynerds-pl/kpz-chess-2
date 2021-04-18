package com.example.kpz_chess_2.activities.game

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.kpz_chess_2.R
import com.google.android.material.navigation.NavigationView
import org.jetbrains.annotations.NotNull


class GameActivity : AppCompatActivity(){
    private lateinit var toolbar: Toolbar
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        toolbar = findViewById(R.id.app_Bar)
        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        //saveGameActivity()

        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_white)
        actionBar.setDisplayShowTitleEnabled(false)
        toolbar.title = ""

        navigationView.setNavigationItemSelectedListener { menuItem ->
            Log.d("NAV ITEM", "nav items")
            val id = menuItem.itemId
            if (id == R.id.nav_save_btn) {
                Log.d("SAVE2", "sav Work")
                Toast.makeText(applicationContext, "SAVE BTN CLICKED", Toast.LENGTH_LONG).show()
            }
            NavigationUI.onNavDestinationSelected(menuItem, navController)
            drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(@NotNull item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                Log.d("BASELINE", "baseline")
                drawer.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    /*private fun saveGameActivity(){

        /*val saveGameBtn = findViewById<Button>(R.id.btn_save_game) as Button
        saveGameBtn.setOnClickListener{
            Log.d("SAVE", "Work")
            Toast.makeText(this, "GAME SAVED!", Toast.LENGTH_LONG).show()
            val activityChangeIntent = Intent(this@GameActivity, MainActivity::class.java)

            this@GameActivity.startActivity(activityChangeIntent)
        }*/

        /*findViewById<Button>(R.id.btn_save_game).setOnClickListener(View.OnClickListener {
            Log.d("SAVE", "Work")
            Toast.makeText(this, "GAME SAVED!", Toast.LENGTH_LONG).show()
        })*/

        //.makeText(this, "GAME SAVED!", Toast.LENGTH_LONG).show()
        //Log.d("SAVE", "Work")
        //val intent = Intent(this, UserSettingsActivity::class.java)
        //startActivity(intent)

    }*/

}


