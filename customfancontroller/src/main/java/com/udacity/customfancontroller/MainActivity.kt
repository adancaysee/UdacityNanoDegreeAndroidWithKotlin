package com.udacity.customfancontroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<DialView>(R.id.dialView).setOnClickListener {
            Toast.makeText(this, getString((it as DialView).fanSpeed.label), Toast.LENGTH_LONG)
                .show()
        }

    }
}