package com.example.kpz_chess_2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kpz_chess_2.activities.game.GameActivity
import com.example.kpz_chess_2.activities.userSettings.UserSettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startGameActivity(view: View){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
        Log.d("START", "start Work")
    }

    fun continiueGameActivity(view: View){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun changeLevelSettingsActivity(view: View){
        val intent = Intent(this, UserSettingsActivity::class.java)
        startActivity(intent)
    }

    fun exit(view: View){
        finishAffinity()
    }
}