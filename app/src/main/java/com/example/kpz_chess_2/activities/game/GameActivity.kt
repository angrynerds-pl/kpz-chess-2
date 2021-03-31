package com.example.kpz_chess_2.activities.game

//import android.widget.Toolbar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.kpz_chess_2.MainActivity
import com.example.kpz_chess_2.R
import com.google.android.material.navigation.NavigationView
import kotlin.system.exitProcess


class GameActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        saveGameActivity()


        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        /*val toggle = ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()*/
        navigationView.setNavigationItemSelectedListener {
            Log.d("LOG", "logggg")
            when(it.itemId){
                R.id.nav_save_btn -> {
                    Log.d("SAVE2", "sav Work")
                    Toast.makeText(this, "GAME SAVED!", Toast.LENGTH_LONG).show()
                }
                R.id.nav_menu_btn -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_exit_game_btn -> {
                    finish()
                    exitProcess(0)
                }
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun saveGameActivity(){

        val saveGameBtn = findViewById<Button>(R.id.btn_save_game) as Button
        saveGameBtn.setOnClickListener{
            Log.d("SAVE", "Work")
            Toast.makeText(this, "GAME SAVED!", Toast.LENGTH_LONG).show()
            val activityChangeIntent = Intent(this@GameActivity, MainActivity::class.java)

            this@GameActivity.startActivity(activityChangeIntent)
        }

        /*findViewById<Button>(R.id.btn_save_game).setOnClickListener(View.OnClickListener {
            Log.d("SAVE", "Work")
            Toast.makeText(this, "GAME SAVED!", Toast.LENGTH_LONG).show()
        })*/

        //.makeText(this, "GAME SAVED!", Toast.LENGTH_LONG).show()
        //Log.d("SAVE", "Work")
        //val intent = Intent(this, UserSettingsActivity::class.java)
        //startActivity(intent)

    }

}


