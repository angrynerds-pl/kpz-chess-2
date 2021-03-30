package com.example.kpz_chess_2


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import karballo.Config


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val config = Config()
    }
}