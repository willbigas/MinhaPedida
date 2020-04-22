package br.com.willbigas.model.bo;

public class ProdutoBO {

    public static boolean validarNome(String nome) {
        return nome != null && !nome.equals("");
    }

    public static boolean validarPreco(Double preco) {
        return preco != null && preco >= 0;
    }

    public static boolean validarPreco(String preco) {
        try {
            return Double.valueOf(preco) != null && Double.valueOf(preco) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }


    }
}
