package br.com.iterasys;

public class Calculadora {
    public static void somarInteiros(int num1, int num2){
        int soma = num1 + num2;
        System.out.println("Soma: " + num1 + " + " + num2 +" = " + soma);
    }
    public static void subtrairInteiros(int num1, int num2){
        int subtracao = num1 - num2;
        System.out.println("Subtrair: " + num1 + " - " + num2 +" = " + subtracao);
    }
    public static void multiplicarInteiros(int num1, int num2){
        int multiplicacao = num1 * num2;
        System.out.println("Multiplicar: " + num1 + " * " + num2 +" = " + multiplicacao);
    }
    public static String dividirInteiros(int num1, int num2){
        String resultado;
        if(num2 != 0) {
            float divisao = (float) num1 / num2;
            resultado = "Dividir: " + num1 + " / " + num2 + " = " + divisao;

        }else {
            resultado = "Nao e possivel dividir valores por 0";
        }
        System.out.println(resultado);
        return resultado;
    }
}
