package chatGPT;

public class TesteCalculadora {
    public static void main(String[] args) {
        double a = 35;
        double b = 0;

        System.out.println("Adição: " + CalculadoraGPT.adicao(a, b));
        System.out.println("Subtração: " + CalculadoraGPT.subtracao(a, b));
        System.out.println("Multiplicação: " + CalculadoraGPT.multiplicacao(a, b));
        System.out.println("Divisão: " + CalculadoraGPT.divisao(a, b));
    }
}
