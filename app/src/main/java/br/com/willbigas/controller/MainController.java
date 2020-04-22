package br.com.willbigas.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.willbigas.dao.ComandaDao;
import br.com.willbigas.dao.ComandaItemDao;
import br.com.willbigas.model.Comanda;
import br.com.willbigas.model.ComandaItem;
import br.com.willbigas.service.ComandaService;
import br.com.willbigas.util.UtilNumberFormat;
import br.com.willbigas.view.ComandaItemActivity;
import br.com.willbigas.view.MainActivity;
import br.com.willbigas.view.ProdutoActivity;

public class MainController {

    private MainActivity activity;

    private ComandaDao comandaDao;
    private ComandaItemDao comandaItemDao;
    private ComandaService comandaService;

    public static Comanda comanda;

    private ComandaItem comandaItem;


    //Para o ListView
    private ArrayAdapter<ComandaItem> adapterComandaItems;
    private List<ComandaItem> listComandaItems;

    public MainController(MainActivity mainActivity) {
        this.activity = mainActivity;
        comandaDao = new ComandaDao(activity);
        comandaItemDao = new ComandaItemDao(activity);
        inicializarComanda();
        configListViewComandaItems();
    }

    private void inicializarComanda() {
        try {
            comandaService = new ComandaService();
            comanda = comandaService.iniciarComanda();
            comanda = comandaDao.getDao().createIfNotExists(comanda);
            comanda = comandaDao.getDao().queryForId(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void redirecionarParaContextoDeAdicionarProduto() {
        Intent intent = new Intent(activity, ComandaItemActivity.class);
        activity.startActivity(intent);
    }

    public void redirecionarParaContextoDeGerenciarProdutos() {
        Intent intent = new Intent(activity, ProdutoActivity.class);
        activity.startActivity(intent);
    }

    private void configListViewComandaItems() {
        try {
            listComandaItems = new ArrayList<ComandaItem>(comandaDao.getDao().queryForId(1).getItems());
            adapterComandaItems = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_list_item_1,
                    listComandaItems
            );
            activity.getLvComandaItems().setAdapter(adapterComandaItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addLongClickListener();
    }

    private void addLongClickListener() {
        activity.getLvComandaItems().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                comandaItem = adapterComandaItems.getItem(position);
                confirmarExclusaoAction(comandaItem);
                return true;
            }
        });

    }

    public void confirmarExclusaoAction(final ComandaItem item) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluindo item da comanda");
        alerta.setMessage("Deseja realmente excluir o item " + item.getProduto().getNome() + " da comanda ?");
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                comandaItem = null;
            }
        });
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    comandaItemDao.getDao().delete(item);
                    adapterComandaItems.remove(item);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                comandaItem = null;
            }
        });
        alerta.show();
    }

    public void limparLista() {
        try {
            for (ComandaItem item : comanda.getItems()) {
                comandaItemDao.getDao().delete(item);
            }
            adapterComandaItems.clear();
            List<ComandaItem> items = new ArrayList<>(comandaDao.getDao().queryForId(comanda.getId()).getItems());
            adapterComandaItems.addAll(items);
            adapterComandaItems.notifyDataSetChanged();
            atualizarValorTotalDaComanda();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void atualizarItemsDaComanda() {
        try {
            adapterComandaItems.clear();
            List<ComandaItem> items = new ArrayList<>(comandaDao.getDao().queryForId(comanda.getId()).getItems());
            adapterComandaItems.addAll(items);
            adapterComandaItems.notifyDataSetChanged();
            atualizarValorTotalDaComanda();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarValorTotalDaComanda() throws SQLException {
        comanda = comandaDao.getDao().queryForId(comanda.getId());
        comanda = comandaService.recalcularTotal(comanda);
        comandaDao.getDao().update(comanda);
        activity.getTvTotal().setText(UtilNumberFormat.deDecimalParaStringR$(comanda.getValorTotal()));
    }
}
