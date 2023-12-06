package com.example.doasans;

public class Usuario {
    private int id_user;
    private String nome_user;
    private int score_user;
    private String email_user;

    public int getId_user() {
        return id_user;
    }
    public String getNome_user() {
        return nome_user;
    }
    public String getEmail_user() {
        return email_user;
    }
    public int getScore_user() {
        return score_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setNome_user(String nome_user) {
        this.nome_user = nome_user;
    }

    public void setScore_user(int score_user) {
        this.score_user = score_user;
    }
}
