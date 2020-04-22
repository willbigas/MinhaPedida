package br.com.willbigas.dao;

import android.content.Context;

import br.com.willbigas.dao.helpers.DaoHelper;
import br.com.willbigas.model.ComandaItem;
import br.com.willbigas.model.Produto;

public class ComandaItemDao extends DaoHelper<ComandaItem> {

    public ComandaItemDao(Context c) {
        super(c, ComandaItem.class);
    }
}
