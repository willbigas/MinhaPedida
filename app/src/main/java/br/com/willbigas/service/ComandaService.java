package br.com.willbigas.service;

import java.util.ArrayList;

import br.com.willbigas.model.Comanda;
import br.com.willbigas.model.ComandaItem;
import br.com.willbigas.model.Produto;

public class ComandaService {

    private ProdutoService produtoService;
    private ComandaItemService comandaItemService;

    public ComandaService() {
        produtoService = new ProdutoService();
        comandaItemService = new ComandaItemService();

    }

    public Comanda iniciarComanda() {
        return new Comanda(0.0, new ArrayList<>());
    }

    public Comanda adicionarItemNaComanda(Comanda comanda, Produto produto, Integer quantidade) {
        ComandaItem comandaItem = comandaItemService.criarItem(comanda, produto, quantidade);
        comanda.getItems().add(comandaItem);
        comanda = recalcularTotal(comanda);
        return comanda;
    }




    private Comanda recalcularTotal(Comanda comanda) {
        Double valorTotal = 0.0;

        for (ComandaItem item : comanda.getItems()) {
            valorTotal += item.getSubtotal();
        }

        comanda.setValorTotal(valorTotal);
        return comanda;
    }
}
