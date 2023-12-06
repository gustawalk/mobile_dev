package com.example.doasans

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.*

import io.ktor.*
import io.ktor.client.*

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class tela_login : AppCompatActivity() {

    val pasta = "testeandroid";
    val url = "192.168.56.1";
    val client = HttpClient()


    // salvas informações para pegar na proxima tela
    private fun save_to_sharedpreferences(key: String, value: String) {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("$key", value)
        editor.apply()
    }

    // tentar realizar o login
    private suspend fun try_login(user: String, pass: String): String{
        val response: HttpResponse = client.request("http://$url/$pasta/login.php?user=$user&pass=$pass")
        val responseBody: String = response.body()

        return responseBody
    }

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.editTextUsername)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)
        registerButton = findViewById(R.id.buttonRegister)

        loginButton.setOnClickListener(View.OnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = try_login(usernameEditText.text.toString(), passwordEditText.text.toString())
                    val dataObject = Json.decodeFromString<user_response>(response)

                    // logs para debug
                    Log.d("Infos", dataObject.auth.toString())
                    Log.d("Infos", dataObject.username)

                    withContext(Dispatchers.Main) {
                        if (dataObject.auth){ //verifica se o auth é verdadeiro

                            val user = Usuario()
                            user.nome_user = dataObject.username
                            user.score_user = dataObject.pontos

                            save_to_sharedpreferences("username", user.nome_user)
                            save_to_sharedpreferences("pontos", user.score_user.toString())

                            Toast.makeText(this@tela_login, "Login feito", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@tela_login, tela_principal::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@tela_login, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: ConnectTimeoutException) {
                    Log.w("Error", "Tempo de conexão expirado: ${e.message}")
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@tela_login, "Tempo de conexão expirado", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.w("Error", "Erro desconhecido: ${e.toString()}")
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@tela_login, "Erro desconhecido", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        // botao para ir ate a tela de registro
        registerButton.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, tela_registro::class.java)
            startActivity(intent)
        })

    }
}