package br.com.willbigas.service;

import java.util.Arrays;
import java.util.List;

import br.com.willbigas.model.Produto;

public class ProdutoService {


    public List<Produto> carregarProdutos() {

        Produto refrigerante = new Produto(1, "Refrigerante", 3.00);
        Produto cerveja = new Produto(2, "Cerveja", 5.00);
        Produto batataFrita = new Produto(3, "Batata Frita", 10.00);
        Produto agua = new Produto(4, "Água", 2.50);
        Produto pastel = new Produto(5, "Pastel", 3.50);
        Produto petiscos = new Produto(6, "Petiscos", 6.00);

        return Arrays.asList(refrigerante, cerveja, batataFrita, agua, pastel, petiscos);
    }

}
