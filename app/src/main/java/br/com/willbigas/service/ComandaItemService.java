package br.com.willbigas.service;

import br.com.willbigas.model.Comanda;
import br.com.willbigas.model.ComandaItem;
import br.com.willbigas.model.Produto;

public class ComandaItemService {


    public ComandaItem criarItem(Comanda comanda, Produto produto, Integer quantidade) {
        return new ComandaItem(produto, quantidade, quantidade * produto.getValor(), comanda);
    }
}
