package com.example.poker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.poker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var playerFragment = PlayerFragment()
    private var gameFragment = GameFragment()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.playerFragmentContainerView, playerFragment).commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.gameFragmentContainerView, gameFragment).commit()
    }
}