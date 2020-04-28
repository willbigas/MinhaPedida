package br.com.willbigas.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.willbigas.util.UtilNumberFormat;

@DatabaseTable
public class Produto {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String nome;

    @DatabaseField(canBeNull = false)
    private Double valor;

    @DatabaseField(canBeNull = false)
    private Boolean ativo;


    public Produto() {
    }

    public Produto(String nome, Double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public Produto(Integer id, String nome, Double valor, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " - " + UtilNumberFormat.deDecimalParaStringR$(valor);
    }
}
