package Caminhos;

import Util.TempoUtil;

/**
 * Representa um caminhão grande usado para levar lixo da estação de transferência até o aterro.
 * Tem capacidade de 20 toneladas e controla o tempo que pode ficar esperando na estação.
 */
public class CaminhaoGrande {
    private static int contador = 1;
    private String id;
    private int capacidade = 20;
    private int cargaAtual;
    private int tempoEspera;
    private int tempoMaximoEspera;
    private boolean emViagem;

    /**
     * Cria um caminhão grande com o tempo máximo de espera definido.
     *
     * @param tempoMaximoEspera tempo máximo (em minutos) que pode ficar esperando na estação.
     */
    public CaminhaoGrande(int tempoMaximoEspera) {
        this.id = "CG" + contador++;
        this.cargaAtual = 0;
        this.tempoEspera = 0;
        this.tempoMaximoEspera = tempoMaximoEspera;
        this.emViagem = false;
    }

    /**
     * Retorna o ID do caminhão.
     *
     * @return identificador.
     */
    public String getId() {
        return id;
    }

    /**
     * Verifica se o caminhão consegue receber a carga informada.
     *
     * @param carga quantidade de lixo (em toneladas).
     * @return true se cabe, false se exceder.
     */
    public boolean podeReceberCarga(int carga) {
        return (cargaAtual + carga <= capacidade);
    }

    /**
     * Recebe carga, se houver espaço. Mostra no console o que aconteceu.
     *
     * @param carga        quantidade de lixo.
     * @param minutoAtual  horário atual da simulação.
     */
    public void receberCarga(int carga, int minutoAtual) {
        if (podeReceberCarga(carga)) {
            cargaAtual += carga;
            System.out.println("[" + TempoUtil.formatarMinutos(minutoAtual) + "] " + id + " recebeu " + carga + "t de lixo. Carga atual: " + cargaAtual + "t.");
        } else {
            System.out.println("[" + TempoUtil.formatarMinutos(minutoAtual) + "] " + id + " não pode receber mais carga. Carga atual: " + cargaAtual + "t. Tentativa de adicionar: " + carga + "t.");
        }
    }

    /**
     * Soma um minuto no tempo de espera do caminhão na estação.
     * Se passar do tempo máximo, verifica se deve partir.
     *
     * @param minutoAtual horário atual da simulação.
     */
    public void incrementarEspera(int minutoAtual) {
        tempoEspera++;
        if (tempoEspera > tempoMaximoEspera && cargaAtual > 0) {
            partirParaAterro(minutoAtual);
        } else if (tempoEspera > tempoMaximoEspera) {
            System.out.println("[" + TempoUtil.formatarMinutos(minutoAtual) + "] " + id + " está vazio, mas atingiu o tempo máximo de espera. Aguarda nova carga.");
        }
    }

    /**
     * Envia o caminhão para o aterro e zera carga e tempo de espera.
     *
     * @param minutoAtual horário atual da simulação.
     */
    public void partirParaAterro(int minutoAtual) {
        System.out.println("[" + TempoUtil.formatarMinutos(minutoAtual) + "] " + id + " partiu para o aterro com " + cargaAtual + "t de lixo!");
        cargaAtual = 0;
        tempoEspera = 0;
        emViagem = true;
    }

    /**
     * Marca que o caminhão voltou do aterro e está disponível.
     *
     * @param minutoAtual horário atual da simulação.
     */
    public void retornarDoAterro(int minutoAtual) {
        emViagem = false;
        System.out.println("[" + TempoUtil.formatarMinutos(minutoAtual) + "] " + id + " retornou do aterro.");
    }

    /**
     * Verifica se o caminhão está disponível (não está em viagem).
     *
     * @return true se está na estação, false se está em viagem.
     */
    public boolean estaDisponivel() {
        return !emViagem;
    }

    /**
     * Verifica se o caminhão tem alguma carga no momento.
     *
     * @return true se tem carga, false se está vazio.
     */
    public boolean estaCarregado() {
        return cargaAtual > 0;
    }
}
