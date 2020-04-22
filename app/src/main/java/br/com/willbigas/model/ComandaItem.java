package br.com.willbigas.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.willbigas.util.UtilNumberFormat;

@DatabaseTable
public class ComandaItem {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Produto produto;

    @DatabaseField(canBeNull = false)
    private Integer quantidade;

    @DatabaseField(canBeNull = false)
    private Double subtotal;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Comanda comanda;

    public ComandaItem() {
    }

    public ComandaItem(Produto produto, Integer quantidade, Double subtotal, Comanda comanda) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
        this.comanda = comanda;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    @Override
    public String toString() {
        return produto.getNome() + " - " + UtilNumberFormat.deDecimalParaStringR$(produto.getValor()) + " - " + quantidade + " - " + UtilNumberFormat.deDecimalParaStringR$(subtotal);
    }
}
