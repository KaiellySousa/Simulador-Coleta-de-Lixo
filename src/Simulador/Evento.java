package Simulador;

import java.util.Random;

/**
 * Classe que representa um evento de geração de lixo.
 * Cada evento tem um horário (minuto), uma descrição, uma duração
 * e uma quantidade de carga (lixo) gerada.
 */
public class Evento {
    private int minuto;
    private String descricao;
    private boolean processado;
    private int duracao;
    private int carga;

    /**
     * Cria um evento de geração de lixo.
     * A carga é gerada aleatoriamente entre 50 e 500 kg.
     * @param minuto Minuto em que o evento ocorre.
     * @param descricao Descrição do evento.
     * @param duracao Duração em minutos do evento.
     */
    public Evento(int minuto, String descricao, int duracao) {
        this.minuto = minuto;
        this.descricao = descricao;
        this.duracao = duracao;
        this.processado = false;
        this.carga = new Random().nextInt(451) + 50; // Entre 50 e 500 kg
    }

    /**
     * Retorna a quantidade de lixo gerada.
     * @return Carga em kg.
     */
    public int getCarga() {
        return carga;
    }

    /**
     * Retorna o minuto em que o evento ocorre.
     * @return Minuto do evento.
     */
    public int getMinuto() {
        return minuto;
    }

    /**
     * Retorna a descrição do evento.
     * @return Descrição.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna a duração do evento em minutos.
     * @return Duração.
     */
    public int getDuracao() {
        return duracao;
    }

    /**
     * Verifica se o evento já foi processado.
     * @return true se foi processado, false caso contrário.
     */
    public boolean foiProcessado() {
        return processado;
    }

    /**
     * Marca o evento como processado.
     */
    public void marcarComoProcessado() {
        this.processado = true;
    }
}
