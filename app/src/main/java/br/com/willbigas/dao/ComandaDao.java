package br.com.willbigas.dao;

import android.content.Context;

import br.com.willbigas.dao.helpers.DaoHelper;
import br.com.willbigas.model.Comanda;
import br.com.willbigas.model.ComandaItem;

public class ComandaDao extends DaoHelper<Comanda> {

    public ComandaDao(Context c) {
        super(c, Comanda.class);
    }
}
