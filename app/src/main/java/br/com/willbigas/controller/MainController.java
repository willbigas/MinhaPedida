package br.com.willbigas.controller;

import android.content.Intent;

import br.com.willbigas.model.Comanda;
import br.com.willbigas.service.ComandaService;
import br.com.willbigas.view.AdicionarProdutoActivity;
import br.com.willbigas.view.MainActivity;
import br.com.willbigas.view.ProdutoActivity;

public class MainController {

    private MainActivity mainActivity;

    private ComandaService comandaService;

    private Comanda comanda;

    public MainController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        inicializarComanda();
    }

    private void inicializarComanda() {
        comandaService = new ComandaService();
        comanda = comandaService.iniciarComanda();
    }

    public void redirecionarParaContextoDeAdicionarProduto() {
        Intent intent = new Intent(mainActivity, AdicionarProdutoActivity.class);
        mainActivity.startActivity(intent);
    }

    public void redirecionarParaContextoDeGerenciarProdutos() {
        Intent intent = new Intent(mainActivity, ProdutoActivity.class);
        mainActivity.startActivity(intent);
    }

    public void limparLista() {

    }
}
