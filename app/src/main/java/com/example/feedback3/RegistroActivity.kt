package com.example.feedback3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class RegistroActivity: AppCompatActivity() {
    private lateinit var btnAlta2: Button
    private lateinit var btnCancelar2: Button
    private lateinit var editmail2: EditText
    private lateinit var editpassword2: EditText
    private val db: FirebaseFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        btnAlta2 = findViewById(R.id.btnAlta)
        btnCancelar2 = findViewById(R.id.btnCancelar2)
        editmail2 = findViewById(R.id.editMail2)
        editpassword2 = findViewById(R.id.editPassword2)

        btnAlta2.setOnClickListener {
            login()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnCancelar2.setOnClickListener {
            finish()
        }
    }

    private fun login() {
        val mail = editmail2.text.toString()
        val password = editpassword2.text.toString()
        val nuevoUsuario = Usuario(mail, password, false) //iniciamos el modo oscuro siempre a falso

        db.collection("dbUsuarios")
            .whereEqualTo("mail", mail)
            .get()
            .addOnSuccessListener { documentReference ->
                if (documentReference.isEmpty) {
                    Toast.makeText(this, "El mail: ${nuevoUsuario.mail}, ya esta registrado", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    db.collection("dbUsuarios")
                        .add(nuevoUsuario)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(this, "El usuario: ${nuevoUsuario.mail}, se ha registrado correctamente", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al comprobar el usuario", Toast.LENGTH_SHORT).show()
            }
    }
}