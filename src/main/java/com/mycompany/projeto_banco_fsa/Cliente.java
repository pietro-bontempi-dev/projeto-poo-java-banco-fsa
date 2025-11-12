package com.mycompany.projeto_banco_fsa;

import java.sql.Date;

public class Cliente {

    private int id; // Novo campo
    private String numeroConta;
    private String nome;
    private java.sql.Date dataNascimento;
    private String profissao;
    private String tipoConta;

    // Construtor para NOVO cadastro (sem ID)
    public Cliente(String numeroConta, String nome, Date dataNascimento, String profissao, String tipoConta) {
        this.numeroConta = numeroConta;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.profissao = profissao;
        this.tipoConta = tipoConta;
    }

    // Construtor para LEITURA/EDIÇÃO (com ID)
    public Cliente(int id, String numeroConta, String nome, Date dataNascimento, String profissao, String tipoConta) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.profissao = profissao;
        this.tipoConta = tipoConta;
    }

    // Getters
    public int getId() { return id; }
    public String getNumeroConta() { return numeroConta; }
    public String getNome() { return nome; }
    public Date getDataNascimento() { return dataNascimento; }
    public String getProfissao() { return profissao; }
    public String getTipoConta() { return tipoConta; }
    
    // Setters (necessários para edição na tela)
    public void setNome(String nome) { this.nome = nome; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
    public void setProfissao(String profissao) { this.profissao = profissao; }
    public void setTipoConta(String tipoConta) { this.tipoConta = tipoConta; }
    // Não criamos setter para ID nem Conta, pois são chaves.
}