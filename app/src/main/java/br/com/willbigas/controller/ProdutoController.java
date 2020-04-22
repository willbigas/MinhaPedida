package br.com.willbigas.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.com.willbigas.R;
import br.com.willbigas.dao.ProdutoDao;
import br.com.willbigas.model.Produto;
import br.com.willbigas.model.bo.ProdutoBO;
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
        inicializarListView();
        addClickListeners();
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

    private void addClickListeners() {
        addLongClickLvProdutos();
        addShortClickLvProdutos();
    }

    private void addShortClickLvProdutos() {
        activity.getLvProdutos().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                produto = adapterProdutos.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Produto");
                alerta.setMessage(produto.toString());
                alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        produto = null;
                    }
                });
                alerta.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        popularFormAction(produto);
                    }
                });
                alerta.show();
            }
        });
    }

    private void addLongClickLvProdutos() {
        activity.getLvProdutos().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                produto = adapterProdutos.getItem(position);
                confirmarExclusaoAction(produto);
                return true;
            }
        });
    }


    private void cadastrar() {
        Produto produto = getProdutoForm();
        try {
            int res = produtoDao.getDao().create(produto);
            adapterProdutos.add(produto);

            if (res > 0) {
                Toast.makeText(activity, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Tente novamente em breve", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(activity, "Id:" + produto.getId(), Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editar(Produto produto) {
        this.produto.setNome(produto.getNome());
        this.produto.setValor(produto.getValor());
        try {
            adapterProdutos.notifyDataSetChanged();
            int res = produtoDao.getDao().update(this.produto);
            if (res > 0) {
                Toast.makeText(activity, "Sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Tente mais tarde", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void salvarAction() {
        if (validarDadosDaView()) {

            if (produto == null) {
                cadastrar();
            } else {
                editar(getProdutoForm());
            }
            produto = null;
            limparForm();
        }


    }

    private Produto getProdutoForm() {
        return new Produto(activity.getEdtNomeProduto().getText().toString(), Double.valueOf(activity.getEdtPrecoProduto().getText().toString()));
    }


    private void popularFormAction(Produto p) {
        activity.getEdtNomeProduto().setText(p.getNome());
        activity.getEdtPrecoProduto().setText(String.valueOf(p.getValor()));
    }

    private void limparForm() {
        activity.getEdtNomeProduto().setText("");
        activity.getEdtPrecoProduto().setText("");
    }

    private void confirmarExclusaoAction(final Produto p) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluindo Produto");
        alerta.setMessage("Deseja realmente excluir o produto " + p.getNome() + "?");
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                produto = null;
            }
        });
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    produtoDao.getDao().delete(p);
                    adapterProdutos.remove(p);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                produto = null;
            }
        });
        alerta.show();
    }


    private boolean validarDadosDaView() {
        boolean foiValidado = true;

        if (!ProdutoBO.validarNome(activity.getEdtNomeProduto().getText().toString())) {
            activity.getEdtNomeProduto().setError(activity.getString(R.string.nome_produto_obrigatorio));
            foiValidado = false;
        }

        if (!ProdutoBO.validarPreco(activity.getEdtPrecoProduto().getText().toString())) {
            activity.getEdtPrecoProduto().setError(activity.getString(R.string.preco_produto_obrigatorio));
            foiValidado = false;
        }
        return foiValidado;
    }
}
