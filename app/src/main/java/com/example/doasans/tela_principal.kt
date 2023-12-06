package com.example.doasans

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import io.ktor.utils.io.concurrent.shared


class tela_principal : AppCompatActivity() {

    //Pegando informações salvas nas sharedpreferences
    private fun get_info_from_sharedpreference(key: String): String {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("$key", "") ?: ""
    }

    private lateinit var logout_button : ImageButton
    private lateinit var recompensas_button : Button

    lateinit var switchMode: SwitchCompat
    var nightMode: Boolean = false
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_principal)

        val textViewUsername = findViewById<TextView>(R.id.nomeuser)
        val textViewPontos = findViewById<TextView>(R.id.pontosuser)
        val switchMode: SwitchCompat = findViewById(R.id.switch_mode)

        // troca de temas claro - escuro
        val sharedPreferences: SharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        val nightMode: Boolean = sharedPreferences.getBoolean("nightMode", false)
        switchMode.isChecked = nightMode

        switchMode.setOnClickListener {
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putBoolean("nightMode", false)
                editor.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putBoolean("nightMode", true)
                editor.apply()
            }
        }

        // resgatando informações necessarias
        val nome_user = get_info_from_sharedpreference("username")
        val pontos_user = get_info_from_sharedpreference("pontos")
        // mostrando na tela
        textViewUsername.text = "Usuário: $nome_user"
        textViewPontos.text = pontos_user


        // "Logout" voltando para a tela de login
        logout_button = findViewById(R.id.logout)
        logout_button.setOnClickListener({
            val intent = Intent(this, tela_login::class.java)
            startActivity(intent)
        })

        // Intent da tela de recompensa
        recompensas_button = findViewById(R.id.bt_recompensas)
        recompensas_button.setOnClickListener({
            val intent = Intent(this, tela_recompensas::class.java)
            startActivity(intent)
        })
    }
}