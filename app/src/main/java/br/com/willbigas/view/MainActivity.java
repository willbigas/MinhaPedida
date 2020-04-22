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
    private ListView lvProdutos;

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
        lvProdutos = findViewById(R.id.lvProdutos);
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
}
