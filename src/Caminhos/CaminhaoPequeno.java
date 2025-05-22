package Caminhos;

import Util.TempoUtil;

/**
 * Representa um caminhão pequeno usado na coleta de lixo nas zonas urbanas.
 * Controla capacidade, viagens e zona onde está operando.
 */
public class CaminhaoPequeno {
    private int capacidadeMaxima;
    private int cargaAtual;
    private int viagensRealizadas;
    private int maximoViagens;
    private String id;
    private String zonaAtual;

    /**
     * Cria um caminhão pequeno.
     *
     * @param id identificador do caminhão.
     * @param capacidadeMaxima capacidade em toneladas.
     * @param maximoViagens limite de viagens por dia.
     * @param zonaAtual zona onde o caminhão está operando.
     */
    public CaminhaoPequeno(String id, int capacidadeMaxima, int maximoViagens, String zonaAtual) {
        this.id = id;
        this.capacidadeMaxima = capacidadeMaxima;
        this.maximoViagens = maximoViagens;
        this.zonaAtual = zonaAtual;
        this.cargaAtual = 0;
        this.viagensRealizadas = 0;
    }

    /**
     * Realiza a coleta de lixo na zona atual.
     *
     * @param quantidade quantidade em kg.
     * @return true se conseguiu coletar, false se excede a capacidade.
     */
    public boolean coletarLixo(int quantidade) {
        int capacidadeKg = capacidadeMaxima * 1000;
        if (cargaAtual + quantidade <= capacidadeKg) {
            cargaAtual += quantidade;
            System.out.println("[" + TempoUtil.getTempoAtual() + "] Caminhão " + id + " coletou " + quantidade + "kg de lixo na zona " + zonaAtual);
            return true;
        }
        return false;
    }

    /**
     * Verifica se o caminhão atingiu sua capacidade máxima.
     */
    public boolean atingiuCapacidadeMaxima() {
        return cargaAtual >= capacidadeMaxima * 1000;
    }

    /**
     * Verifica se ainda pode realizar viagens no dia.
     */
    public boolean podeViajar() {
        return viagensRealizadas < maximoViagens;
    }

    /**
     * Realiza a viagem até a estação de transferência.
     *
     * @param estacaoId identificador da estação.
     */
    public void realizarViagemParaEstacao(String estacaoId) {
        System.out.println("[" + TempoUtil.getTempoAtual() + "] Caminhão " + id + " atingiu capacidade máxima e está indo para a estação " + estacaoId);
        viagensRealizadas++;
    }

    /**
     * Descarrega o caminhão na estação.
     *
     * @return quantidade descarregada em kg.
     */
    public int descarregar() {
        int descarregado = cargaAtual;
        cargaAtual = 0;
        System.out.println("[" + TempoUtil.getTempoAtual() + "] Caminhão " + id + " descarregou " + descarregado + "kg de lixo");
        return descarregado;
    }

    public String getId() {
        return id;
    }

    public int getCargaAtual() {
        return cargaAtual;
    }

    public String getZonaAtual() {
        return zonaAtual;
    }

    public void setZonaAtual(String zonaAtual) {
        this.zonaAtual = zonaAtual;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public int getMaximoViagens() {
        return maximoViagens;
    }

    public int getViagensRealizadas() {
        return viagensRealizadas;
    }
}
