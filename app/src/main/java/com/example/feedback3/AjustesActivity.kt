package com.example.feedback3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AjustesActivity:AppCompatActivity() {
    private lateinit var btnMain: Button
    private lateinit var btnCerrar: Button
    private lateinit var textoInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        btnMain = findViewById(R.id.btnCerrarSesion)
        btnCerrar = findViewById(R.id.btnMain)
        textoInfo = findViewById(R.id.textMailInfo)

        btnMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnCerrar.setOnClickListener {
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}