package com.example.feedback3

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity: AppCompatActivity(){
    private lateinit var progreso: ProgressBar
    private lateinit var imagen: ImageView
    private lateinit var texto: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imagen = findViewById(R.id.imageView)
        progreso = findViewById(R.id.progreso)
        texto = findViewById(R.id.textoSesion)

        val fadeOut = AlphaAnimation(1.0f, 0.0f).apply {
            duration = 2000
            fillAfter = true
        }
        imagen.startAnimation(fadeOut)

        Handler(Looper.getMainLooper()).postDelayed({

            progreso.visibility = View.GONE
            texto.text = "Sesión cerrada."

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3 segundos
    }
}