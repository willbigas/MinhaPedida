package br.com.willbigas.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import br.com.willbigas.R;
import br.com.willbigas.controller.MainController;

public class MainActivity extends AppCompatActivity {

    private MainController mainController;

    private Button btnGerenciarProdutos;
    private Button btnAdicionarProduto;
    private Button btnLimparLista;
    private TextView tvTotal;
    private ListView lvComandaItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        initializeClickListeners();
        mainController = new MainController(this);
    }

    private void initializeComponents() {
        btnGerenciarProdutos = findViewById(R.id.btnGerenciarProdutos);
        btnAdicionarProduto = findViewById(R.id.btnAdicionarProduto);
        btnLimparLista = findViewById(R.id.btnLimparLista);
        tvTotal = findViewById(R.id.tvTotal);
        lvComandaItems = findViewById(R.id.lvComandaItems);
    }

    private void initializeClickListeners(){
        btnAdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.redirecionarParaContextoDeAdicionarProduto();
            }
        });

        btnLimparLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.limparLista();
            }
        });

        btnGerenciarProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.redirecionarParaContextoDeGerenciarProdutos();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainController.atualizarItemsDaComanda();
    }

    public Button getBtnGerenciarProdutos() {
        return btnGerenciarProdutos;
    }

    public void setBtnGerenciarProdutos(Button btnGerenciarProdutos) {
        this.btnGerenciarProdutos = btnGerenciarProdutos;
    }

    public Button getBtnAdicionarProduto() {
        return btnAdicionarProduto;
    }

    public void setBtnAdicionarProduto(Button btnAdicionarProduto) {
        this.btnAdicionarProduto = btnAdicionarProduto;
    }

    public Button getBtnLimparLista() {
        return btnLimparLista;
    }

    public void setBtnLimparLista(Button btnLimparLista) {
        this.btnLimparLista = btnLimparLista;
    }

    public TextView getTvTotal() {
        return tvTotal;
    }

    public void setTvTotal(TextView tvTotal) {
        this.tvTotal = tvTotal;
    }

    public ListView getLvComandaItems() {
        return lvComandaItems;
    }

    public void setLvComandaItems(ListView lvComandaItems) {
        this.lvComandaItems = lvComandaItems;
    }
}
