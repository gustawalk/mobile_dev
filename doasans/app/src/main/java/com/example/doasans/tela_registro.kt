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
import io.ktor.util.Identity.decode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString


class tela_registro : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    private lateinit var editEmail: EditText
    private lateinit var editUsername: EditText
    private lateinit var editPassword: EditText

    val pasta = "testeandroid";
    val url = "192.168.56.1";
    val client = HttpClient()

    private fun save_to_sharedpreferences(key: String, value: String) {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("$key", value)
        editor.apply()
    }

    private suspend fun try_regis(user: String, pass :String, email :String): String{
        val response: HttpResponse = client.request("http://$url/$pasta/register.php?user=$user&pass=$pass&email=$email")

        val responseBody: String = response.body()

        return responseBody // or return some other processed result
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_registro)

        loginButton = findViewById(R.id.buttonBackLogin)
        registerButton = findViewById(R.id.buttonConfirmRegister)

        editEmail = findViewById(R.id.editEmail)
        editUsername = findViewById(R.id.editTextUsername)
        editPassword = findViewById(R.id.editTextPassword)

        loginButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, tela_login::class.java)
            startActivity(intent)
        })

        registerButton.setOnClickListener(View.OnClickListener {
            val email = editEmail.text.toString();
            val user = editUsername.text.toString();
            val pass = editPassword.text.toString();

            CoroutineScope(Dispatchers.IO).launch{
                try {
                    val response = try_regis(user, pass, email)
                    val dataObject = Json.decodeFromString<user_response>(response)

                    Log.d("Infos", dataObject.auth.toString())

                    withContext(Dispatchers.Main){
                        if(dataObject.auth){

                            val user = Usuario()
                            user.nome_user = dataObject.username;
                            user.score_user = dataObject.pontos;

                            save_to_sharedpreferences("username", user.nome_user)
                            save_to_sharedpreferences("pontos", user.score_user.toString())

                            Toast.makeText(this@tela_registro, "Registro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@tela_registro, tela_principal::class.java)
                            startActivity(intent)
                        }else {
                            Toast.makeText(this@tela_registro, "Informações incorretas", Toast.LENGTH_SHORT).show()
                        }
                    }
                }catch (e: ConnectTimeoutException) {
                    Log.w("Error", "Timeout de conexão expirado: ${e.message}")
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@tela_registro, "Timeout de conexão expirado", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.w("Error", "Erro desconhecido: ${e.toString()}")
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@tela_registro, "Erro desconhecido", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}