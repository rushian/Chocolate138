package br.com.iterasys;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AreasAulas {
    public static double calcularQuadrado(double lado){ return 0; }
    public static double calcularRetangulo(double largura, double comprimento){ return 0; }
    public static double calcularTriangulo(double largura, double comprimento){ return 0; }
    public static double calcularCirculo(double raio){ return 0; }

    @Test
    public void testarCalcularQuadrado(){
        // Configura
        double lado = 3;
        double resultadoEsperado = 9;

        // Executa
        double resultadoAtual = AreasAulas.calcularQuadrado(lado);

        // Valida
        Assert.assertEquals(resultadoAtual, resultadoEsperado);
    }
    @Test
    public void testarCalcularRetangulo() {
        // Configura
        double largura = 3;
        double comprimento = 4;
        double resultadoEsperado = 12;

        // Executa
        double resultadoAtual = AreasAulas.calcularRetangulo(largura, comprimento);

        // Valida
        Assert.assertEquals(resultadoAtual, resultadoEsperado);
    }

    @Test
    public void testarCalcularTriangulo() {
        // Configura
        double base = 3;
        double altura = 4;
        double resultadoEsperado = 6; // A fórmula da área do triângulo é (base*altura)/2

        // Executa
        double resultadoAtual = AreasAulas.calcularTriangulo(base, altura);

        // Valida
        Assert.assertEquals(resultadoAtual, resultadoEsperado);
    }

    @Test
    public void testarCalcularCirculo() {
        // Configura
        double raio = 3;
        double resultadoEsperado = Math.PI * Math.pow(raio, 2); // A fórmula da área do círculo é ?r²

        // Executa
        double resultadoAtual = AreasAulas.calcularCirculo(raio);

        // Valida
        Assert.assertEquals(resultadoAtual, resultadoEsperado);
    }
}
