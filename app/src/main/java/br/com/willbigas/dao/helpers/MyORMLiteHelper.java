package br.com.willbigas.dao.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import br.com.willbigas.model.Comanda;
import br.com.willbigas.model.ComandaItem;
import br.com.willbigas.model.Produto;

public class MyORMLiteHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "minhaPedida.db";
    private static final int DATABASE_VERSION = 2;

    public MyORMLiteHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {

            TableUtils.createTableIfNotExists(connectionSource, Produto.class);
            TableUtils.createTableIfNotExists(connectionSource, Comanda.class);
            TableUtils.createTableIfNotExists(connectionSource, ComandaItem.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Produto.class, true);
            TableUtils.dropTable(connectionSource, Comanda.class, true);
            TableUtils.dropTable(connectionSource, ComandaItem.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
