package com.example.agenda.dominio.entidades;

import java.io.Serializable;

public class Contato implements Serializable {
    private Long id;
    private String nome;
    private String telefone;
    private String apelido;

    public Contato(){
        id= Long.valueOf(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    @Override
    public String toString(){
        return nome;

    }
}
