package com.example.doasans

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.*
import com.example.doasans.adapter.AdapterProduto
import com.example.doasans.model.Produto


private lateinit var backbutton: ImageButton
private lateinit var recyclerview: RecyclerView

class tela_recompensas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_recompensas)

        backbutton = findViewById(R.id.backbutton)
        backbutton.setOnClickListener({
            intent = Intent(this, tela_principal::class.java)
            startActivity(intent)
        })

        recyclerview = findViewById(R.id.recycler)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        // configurar adapter

        val lista_prod: MutableList<Produto> = mutableListOf()
        val adapter_produto = AdapterProduto(this, lista_prod)
        recyclerview.adapter = adapter_produto

        val produto1 = Produto(
            R.drawable.steam,
            "Giftcard Steam",
            "Vales-presente Steam e códigos da Carteira Steam funcionam como certificados de presente, podendo ser ativados no Steam para comprar jogos, softwares, hardwares e qualquer outro item à venda no Steam.",
            "1000P"
        )
        lista_prod.add(produto1)

        val produto2 = Produto(
            R.drawable.netflix,
            "Giftcard Netflix",
            "Você pode usar cartões-presente da Netflix para pagar pela Netflix ou para dar de presente.",
            "1000P"
        )
        lista_prod.add(produto2)

        val produto3 = Produto(
            R.drawable.xbox,
            "Giftcard Xbox",
            "Os cartões de oferta Xbox e Microsoft permitem escolher a partir de uma vasta seleção de jogos, filmes e programas de TV, dispositivos premium e muito mais. Sabia que qualquer um dos cartões de oferta poderá ser utilizado em qualquer Microsoft Store – online, no Windows ou na Xbox? Além disso, nunca terá de preocupar-se com taxas adicionais ou datas de expiração.",
            "1000P"
        )
        lista_prod.add(produto3)

        val produto4 = Produto(
            R.drawable.ifood,
            "Giftcard Ifood",
            "Ofereça Aos Seus Fornecedores e Bonifique Seus Funcionários Com o Melhor Presente: Comida. Dê iFood de presente.",
            "1000P"
        )
        lista_prod.add(produto4)
    }
}