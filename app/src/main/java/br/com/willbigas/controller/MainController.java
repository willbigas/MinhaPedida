package br.com.willbigas.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.willbigas.R;
import br.com.willbigas.dao.ComandaDao;
import br.com.willbigas.dao.ComandaItemDao;
import br.com.willbigas.model.Comanda;
import br.com.willbigas.model.ComandaItem;
import br.com.willbigas.service.ComandaItemService;
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
    private ComandaItemService comandaItemService;

    public static Comanda comanda;

    private ComandaItem comandaItem;


    //Para o ListView
    private ArrayAdapter<ComandaItem> adapterComandaItems;
    private List<ComandaItem> listComandaItems;

    public MainController(MainActivity mainActivity) {
        this.activity = mainActivity;
        comandaDao = new ComandaDao(activity);
        comandaItemDao = new ComandaItemDao(activity);
        comandaItemService = new ComandaItemService();
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

        addShortClickListener();
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

    private void addShortClickListener() {
        activity.getLvComandaItems().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                comandaItem = adapterComandaItems.getItem(position);
                editarComandaItemDialog(comandaItem);
            }
        });

    }

    public void confirmarExclusaoAction(final ComandaItem item) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle(R.string.excluindo_item_da_comanda);
        alerta.setMessage(activity.getString(R.string.deseja_realmente_excluir_item) + item.getProduto().getNome() + activity.getString(R.string.da_comanda));
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                comandaItem = null;
            }
        });
        alerta.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    comandaItemDao.getDao().delete(item);
                    adapterComandaItems.remove(item);
                    atualizarValorTotalDaComanda();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                comandaItem = null;
            }
        });
        alerta.show();
    }

    public void editarComandaItemDialog(final ComandaItem item) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle(activity.getString(R.string.editar_quantidade_do_item) + item.getProduto().getNome());
        alerta.setIcon(android.R.drawable.ic_menu_edit);
        alerta.setPositiveButton(R.string.maisUm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adicionarMaisUmaQuantidade(item);
                atualizarEPersistirValoresDaComanda();
                Toast.makeText(activity, R.string.somado_mais_um_a_quantidade_do_item_selecionado, Toast.LENGTH_SHORT).show();
            }
        });
        alerta.setNegativeButton(R.string.menosUm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                subtrairMenosUmaQuantidade(item);
                atualizarEPersistirValoresDaComanda();
                Toast.makeText(activity, R.string.subtraido_menos_um_a_quantidade_do_item_selecionado, Toast.LENGTH_SHORT).show();

            }
        });
        alerta.setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

    public void atualizarEPersistirValoresDaComanda() {
        try {
            comanda = comandaService.recalcularTotal(comanda);
            comandaDao.getDao().update(comanda);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarMaisUmaQuantidade(ComandaItem item) {
        ComandaItem comandaItem = comandaItemService.adicionarMaisUm(item);
        try {
            comandaItemDao.getDao().update(comandaItem);
            atualizarItemsDaComanda();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void subtrairMenosUmaQuantidade(ComandaItem item) {
        verificaSeQuantidadeEhIgualAZeroEDeletaDaLista(item);

        ComandaItem comandaItem = comandaItemService.subtrairMenosUm(item);
        try {
            comandaItemDao.getDao().update(comandaItem);
            atualizarItemsDaComanda();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void verificaSeQuantidadeEhIgualAZeroEDeletaDaLista(ComandaItem item) {
        if (item.getQuantidade() == 0) {
            try {
                comandaItemDao.getDao().delete(item);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        atualizarEPersistirValoresDaComanda();
    }

    public void atualizarItemsDaComanda() {

        try {
            verificaSeAlistaContemUmItemComQuantidadeZeradaERemoveOItem(comandaDao.getDao().queryForId(comanda.getId()));
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

    public void verificaSeAlistaContemUmItemComQuantidadeZeradaERemoveOItem(Comanda comanda) {
        for (ComandaItem item : comanda.getItems()) {
            if (item.getQuantidade() == 0 || item.getQuantidade() < 0) {
                verificaSeQuantidadeEhIgualAZeroEDeletaDaLista(item);
            }
        }
    }
}
