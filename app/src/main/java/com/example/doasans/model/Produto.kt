package com.example.doasans.model

data class Produto(

    val foto: Int, //Caso passe para o backend, tem que ser string para puxar o diretório
    val nome: String,
    val desc: String,
    val preco: String
)