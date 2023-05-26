// 1. Pacote
package br.com.iterasys;
// 2. Importacao de bibliotecas

//3. classe
public class Encomenda {
    public int calcularBarrasDeChocolatePorCaixa(int barras){
        short quantidadePorCaixa = 12;

        int caixas = barras/quantidadePorCaixa;
        int unidades = barras%quantidadePorCaixa;

        if (unidades > 1) {
            System.out.println("Qtd de caixas: " + caixas + " e " + unidades + " unidades");
        } else if (unidades == 1) {
            System.out.println("Qtd de caixas: " + caixas + " e " + unidades + " unidade");
        } else {
            System.out.println("Qtd de caixas: " + caixas);
        }

        return barras;
    }
}
