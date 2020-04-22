package br.com.willbigas.dao;

import android.content.Context;

import br.com.willbigas.dao.helpers.DaoHelper;
import br.com.willbigas.model.Produto;

public class ProdutoDao extends DaoHelper<Produto> {

    public ProdutoDao(Context c) {
        super(c, Produto.class);
    }
}
