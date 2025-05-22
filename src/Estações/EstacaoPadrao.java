package Estações;

import Caminhos.CaminhaoPequeno;
import Caminhos.CaminhaoGrande;

/**
 * Representa uma estação de transferência padrão.
 * Recebe lixo de caminhões pequenos e repassa para caminhões grandes.
 */
public class EstacaoPadrao extends EstacaoTransferencia {

    private int lixoArmazenado; // Quantidade de lixo armazenado na estação (em kg)

    /**
     * Construtor da estação de transferência.
     *
     * @param nome Nome da estação.
     */
    public EstacaoPadrao(String nome) {
        super(nome); // Chama o construtor da superclasse EstacaoTransferencia
        this.lixoArmazenado = 0;
    }

    /**
     * Recebe o lixo de um caminhão pequeno.
     * O lixo é adicionado ao total armazenado na estação.
     *
     * @param caminhao Caminhão pequeno que descarrega na estação.
     */
    public void receberCaminhaoPequeno(CaminhaoPequeno caminhao) {
        int lixoColetado = caminhao.getCargaAtual();
        lixoArmazenado += lixoColetado;
        caminhao.descarregar();
        System.out.println("Estação " + getNome() + " recebeu " + lixoColetado + "kg de lixo do caminhão pequeno.");
    }

    /**
     * Transfere todo o lixo armazenado para um caminhão grande.
     * Após a transferência, o lixo armazenado na estação zera.
     *
     * @param caminhao     Caminhão grande que recebe a carga.
     * @param minutoAtual  Minuto atual da simulação, utilizado para controle do caminhão grande.
     */
    public void descarregarParaCaminhaoGrande(CaminhaoGrande caminhao, int minutoAtual) {
        caminhao.receberCarga(lixoArmazenado, minutoAtual);
        System.out.println("Estação " + getNome() + " carregou caminhão grande com " + lixoArmazenado + "kg.");
        lixoArmazenado = 0;
    }
}
