package com.example.vsuet.mainActivity

import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.vsuet.MainNavDirections
import com.example.vsuet.R
import com.example.vsuet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)
            goHomeButton.alpha = 0f
            goHomeButton.setOnClickListener {
                findNavController(R.id.nav_host_fragment).navigate(MainNavDirections.goHomeAction())
                it.isClickable = false
            }

            personalAccountButton.setOnClickListener {
                findNavController(R.id.nav_host_fragment).navigate(MainNavDirections.toPersonalAccount())
                it.isClickable = false
            }
        }
        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if(nfcAdapter == null){
            Toast.makeText(this, "GIGASADGE", Toast.LENGTH_SHORT).show()
        }
    }

}