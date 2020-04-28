package br.com.willbigas.service;

import br.com.willbigas.dao.ComandaItemDao;
import br.com.willbigas.model.Comanda;
import br.com.willbigas.model.ComandaItem;
import br.com.willbigas.model.Produto;

public class ComandaItemService {

    private ComandaItemDao comandaItemDao;

    public ComandaItem criarItem(Comanda comanda, Produto produto, Integer quantidade) {
        return new ComandaItem(produto, quantidade, quantidade * produto.getValor(), comanda);
    }

    public ComandaItem adicionarMaisUm(ComandaItem comandaItem) {
        comandaItem.setQuantidade(comandaItem.getQuantidade() + 1);
        comandaItem.setSubtotal(comandaItem.getQuantidade() * comandaItem.getProduto().getValor());
        return comandaItem;

    }

    public ComandaItem subtrairMenosUm(ComandaItem comandaItem) {
        comandaItem.setQuantidade(comandaItem.getQuantidade() - 1);
        comandaItem.setSubtotal(comandaItem.getQuantidade() * comandaItem.getProduto().getValor());
        return comandaItem;
    }
}
