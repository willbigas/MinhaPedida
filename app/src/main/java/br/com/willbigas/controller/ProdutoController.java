package br.com.willbigas.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
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
            listProdutos = produtoDao.buscarTodos(true);
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

    private void atualizarListView() {
        try {
            adapterProdutos.clear();
            List<Produto> produtos = new ArrayList<>(produtoDao.buscarTodos(true));
            adapterProdutos.addAll(produtos);
            adapterProdutos.notifyDataSetChanged();
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
                alerta.setTitle(R.string.produto);
                alerta.setMessage(produto.toString());
                alerta.setNegativeButton(R.string.fechar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        produto = null;
                    }
                });
                alerta.setPositiveButton(R.string.editar, new DialogInterface.OnClickListener() {
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
        produto.setAtivo(true);
        try {
            int res = produtoDao.getDao().create(produto);
            adapterProdutos.add(produto);

            if (res > 0) {
                Toast.makeText(activity, R.string.cadastrado_com_sucesso, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, R.string.tente_novamente_em_breve, Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(activity, activity.getString(R.string.id_) + produto.getId(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(activity, R.string.sucesso, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, R.string.tente_mais_tarde, Toast.LENGTH_SHORT).show();
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
        alerta.setTitle(R.string.excluindo_produto);
        alerta.setMessage(activity.getString(R.string.deseja_realmente_excluir_o_produto) + p.getNome() + "?");
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                produto = null;
            }
        });
        alerta.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    p.setAtivo(false);
                    produtoDao.getDao().update(p);
                    atualizarListView();
                    Toast.makeText(activity, R.string.comanda_item_excluido_sucesso, Toast.LENGTH_SHORT).show();
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

        if (!ProdutoBO.validarMargemDePreco(activity.getEdtPrecoProduto().getText().toString())) {
            activity.getEdtPrecoProduto().setError(activity.getString(R.string.preco_produto_margem_permitida));
            foiValidado = false;
        }
        return foiValidado;
    }
}
