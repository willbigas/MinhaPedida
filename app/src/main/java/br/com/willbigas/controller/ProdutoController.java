package br.com.willbigas.controller;

import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.List;

import br.com.willbigas.dao.ProdutoDao;
import br.com.willbigas.model.Produto;
import br.com.willbigas.service.ProdutoService;
import br.com.willbigas.view.ProdutoActivity;

public class ProdutoController {

    private ProdutoActivity activity;

    private ArrayAdapter<Produto> adapterProdutos;
    private List<Produto> listProdutos;

    private ProdutoDao produtoDao;
    private ProdutoService produtoService;

    private Produto produto;

    public ProdutoController(ProdutoActivity produtoActivity) {
        this.activity = produtoActivity;
        initialize();
    }

    private void initialize() {
        produtoDao = new ProdutoDao(this.activity);
        produtoService = new ProdutoService();
        loadDefaultProducts();
    }

    private void loadDefaultProducts() {
        try {
            List<Produto> defaultProducts = produtoService.carregarProdutos();
            for (Produto product : defaultProducts) {
                produtoDao.getDao().createIfNotExists(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void inicializarListView() {
        try {
            listProdutos = produtoDao.getDao().queryForAll();
            adapterProdutos = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_list_item_1,
                    listProdutos
            );
            activity.getLvProdutos().setAdapter(adapterProdutos);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
