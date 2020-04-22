package br.com.willbigas.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import br.com.willbigas.R;
import br.com.willbigas.controller.ComandaItemController;

public class ComandaItemActivity extends AppCompatActivity {

    private ComandaItemController controller;

    private Spinner spinnerProduto;
    private NumberPicker npQuantidade;
    private Button btnEnviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_produto);
        initialize();
        controller = new ComandaItemController(this);

    }

    private void initialize() {
        initializeComponents();
        initializeClickListeners();
    }

    private void initializeComponents() {
        spinnerProduto = findViewById(R.id.spinnerProduto);
        btnEnviar = findViewById(R.id.btnEnviar);

        npQuantidade = findViewById(R.id.npQuantidade);
        npQuantidade.setMinValue(1);
        npQuantidade.setMaxValue(99);
        npQuantidade.setWrapSelectorWheel(true);
    }

    private void initializeClickListeners() {
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.adicionar();
            }
        });
    }

    public Spinner getSpinnerProduto() {
        return spinnerProduto;
    }

    public void setSpinnerProduto(Spinner spinnerProduto) {
        this.spinnerProduto = spinnerProduto;
    }

    public NumberPicker getNpQuantidade() {
        return npQuantidade;
    }

    public void setNpQuantidade(NumberPicker npQuantidade) {
        this.npQuantidade = npQuantidade;
    }

    public Button getBtnEnviar() {
        return btnEnviar;
    }

    public void setBtnEnviar(Button btnEnviar) {
        this.btnEnviar = btnEnviar;
    }
}
