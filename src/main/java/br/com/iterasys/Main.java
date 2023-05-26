package br.com.iterasys;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Olá mundo");
        chamarEncomenda(35);
        chamarEncomenda(12);
        Calculadora.somarInteiros(5,7);
        Calculadora.subtrairInteiros(5,7);
        Calculadora.multiplicarInteiros(5,7);
        Calculadora.dividirInteiros(16,0);
        Calculadora.dividirInteiros(16,3);
    }
    public static void chamarEncomenda(int barras){
        Encomenda nova = new Encomenda();
        ///int barras = 35;
        nova.calcularBarrasDeChocolatePorCaixa(barras);
    }
}
