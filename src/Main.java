/**
 * Main.java
 * Simulador de Coleta Urbana de Lixo - Teresina
 * Projeto desenvolvido por Aline Dias e Kaielly Sousa
 * Professor: Ricardo Sekeef
 *
 * Função: Configura zonas, estações, caminhões e executa a simulação
 */

import Simulador.Simulador;
import Zonas.ZonaUrbana;
import Estações.EstacaoTransferencia;
import Caminhos.CaminhaoPequenoPadrao;
import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Cabeçalho
        System.out.println("==============================================");
        System.out.println("      SIMULADOR DE COLETA URBANA - Aline e Kaielly");
        System.out.println("==============================================");

        // Entrada do usuário
        System.out.print("Digite a quantidade de dias da simulação: ");
        int dias = scanner.nextInt();
        int duracaoTotalMinutos = dias * 24 * 60;

        // Informando início da simulação
        System.out.println("\n===================================================");
        System.out.println("         Iniciando simulação por " + dias + (dias == 1 ? " dia" : " dias"));
        System.out.println("===================================================\n");

        // Criação das estações
        EstacaoTransferencia estacaoSul = new EstacaoTransferencia("Estação Sul");
        EstacaoTransferencia estacaoNorte = new EstacaoTransferencia("Estação Norte");

        // Criação do simulador
        Simulador simulador = new Simulador(duracaoTotalMinutos, estacaoSul, estacaoNorte);

        // Criação das zonas urbanas
        ZonaUrbana sul = new ZonaUrbana("Sul", estacaoSul);
        ZonaUrbana norte = new ZonaUrbana("Norte", estacaoNorte);
        ZonaUrbana centro = new ZonaUrbana("Centro", estacaoSul);
        ZonaUrbana leste = new ZonaUrbana("Leste", estacaoSul);
        ZonaUrbana sudeste = new ZonaUrbana("Sudeste", estacaoNorte);

        // Criação e inserção dos caminhões pequenos nas zonas
        sul.inserirCaminhaoPequeno(new CaminhaoPequenoPadrao("CP-Sul-2T", 2, 5, "Sul", estacaoSul));
        sul.inserirCaminhaoPequeno(new CaminhaoPequenoPadrao("CP-Sul-4T", 4, 5, "Sul", estacaoSul));

        norte.inserirCaminhaoPequeno(new CaminhaoPequenoPadrao("CP-Norte-2T", 2, 5, "Norte", estacaoNorte));
        norte.inserirCaminhaoPequeno(new CaminhaoPequenoPadrao("CP-Norte-4T", 4, 5, "Norte", estacaoNorte));

        centro.inserirCaminhaoPequeno(new CaminhaoPequenoPadrao("CP-Centro-8T", 8, 5, "Centro", estacaoSul));

        leste.inserirCaminhaoPequeno(new CaminhaoPequenoPadrao("CP-Leste-10T", 10, 5, "Leste", estacaoSul));

        sudeste.inserirCaminhaoPequeno(new CaminhaoPequenoPadrao("CP-Sudeste-2T", 2, 5, "Sudeste", estacaoNorte));

        // Adicionando zonas no simulador
        simulador.adicionarZona(sul);
        simulador.adicionarZona(norte);
        simulador.adicionarZona(centro);
        simulador.adicionarZona(leste);
        simulador.adicionarZona(sudeste);

        // Executando simulação
        simulador.executar();
        

    }


}








