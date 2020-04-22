package br.com.willbigas.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import br.com.willbigas.R;
import br.com.willbigas.controller.ProdutoController;

public class ProdutoActivity extends AppCompatActivity {

    private ProdutoController control;

    private EditText edtNomeProduto;
    private EditText edtPrecoProduto;

    private ListView lvProdutos;

    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        initialize();
        control = new ProdutoController(this);
    }

    private void initialize() {
        initializeComponents();
        initializeClickListeners();
    }

    private void initializeComponents() {
        edtNomeProduto = findViewById(R.id.edtNomeProduto);
        edtPrecoProduto = findViewById(R.id.edtPrecoProduto);
        lvProdutos = findViewById(R.id.lvProdutos);
        btnSalvar = findViewById(R.id.btnSalvar);
    }

    private void initializeClickListeners() {
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // faz algo
            }
        });
    }

    public ProdutoController getControl() {
        return control;
    }

    public void setControl(ProdutoController control) {
        this.control = control;
    }

    public EditText getEdtNomeProduto() {
        return edtNomeProduto;
    }

    public void setEdtNomeProduto(EditText edtNomeProduto) {
        this.edtNomeProduto = edtNomeProduto;
    }

    public EditText getEdtPrecoProduto() {
        return edtPrecoProduto;
    }

    public void setEdtPrecoProduto(EditText edtPrecoProduto) {
        this.edtPrecoProduto = edtPrecoProduto;
    }

    public ListView getLvProdutos() {
        return lvProdutos;
    }

    public void setLvProdutos(ListView lvProdutos) {
        this.lvProdutos = lvProdutos;
    }

    public Button getBtnSalvar() {
        return btnSalvar;
    }

    public void setBtnSalvar(Button btnSalvar) {
        this.btnSalvar = btnSalvar;
    }
}
