package br.com.willbigas.controller;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.sql.SQLException;

import br.com.willbigas.R;
import br.com.willbigas.dao.ComandaDao;
import br.com.willbigas.dao.ProdutoDao;
import br.com.willbigas.model.Produto;
import br.com.willbigas.service.ComandaService;
import br.com.willbigas.view.ComandaItemActivity;

public class ComandaItemController {

    private ComandaItemActivity activity;

    private ArrayAdapter<Produto> adapterProdutos;

    private ProdutoDao produtoDao;
    private ComandaDao comandaDao;

    private ComandaService comandaService;


    public ComandaItemController(ComandaItemActivity activity) {
        this.activity = activity;
        initializeInstances();
        configSpinner();
    }

    private void initializeInstances() {
        produtoDao = new ProdutoDao(this.activity);
        comandaDao = new ComandaDao(this.activity);
        comandaService = new ComandaService();
    }


    private void configSpinner() {
        try {
            adapterProdutos = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_spinner_item,
                    produtoDao.buscarTodos(true)
            );
            activity.getSpinnerProduto().setAdapter(adapterProdutos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionar() {
        try {
            MainController.comanda = comandaService.adicionarItemNaComanda(MainController.comanda, (Produto) activity.getSpinnerProduto().getSelectedItem(), activity.getNpQuantidade().getValue());
            comandaDao.getDao().update(MainController.comanda);
            Toast.makeText(activity, R.string.comanda_item_adicionado_sucesso, Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
