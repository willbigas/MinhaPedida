package br.com.willbigas.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;
import java.util.List;

@DatabaseTable
public class Comanda {

    @DatabaseField(allowGeneratedIdInsert = true , generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private Double valorTotal;

    @ForeignCollectionField(eager = true)
    private Collection<ComandaItem> items;


    public Comanda() {
    }

    public Comanda(Double valorTotal, List<ComandaItem> items) {
        this.valorTotal = valorTotal;
        this.items = items;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Collection<ComandaItem> getItems() {
        return items;
    }

    public void setItems(Collection<ComandaItem> items) {
        this.items = items;
    }
}
