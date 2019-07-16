package br.com.spartaseller.persistence.model;

public class Administrador extends AbstractEntity{
    private String nome;
    private String cpf;

    public Administrador() {
    }

    public Administrador(long id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
