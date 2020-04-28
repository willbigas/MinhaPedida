package br.com.willbigas.dao;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import br.com.willbigas.dao.helpers.DaoHelper;
import br.com.willbigas.model.Produto;

public class ProdutoDao extends DaoHelper<Produto> {

    public ProdutoDao(Context c) {
        super(c, Produto.class);
    }

    public List<Produto> buscarTodos(Boolean ativo) throws SQLException {
        return getDao().queryBuilder().where().eq("ativo", ativo).query();
    }
}
