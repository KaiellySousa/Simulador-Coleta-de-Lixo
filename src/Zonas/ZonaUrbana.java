package Zonas;

import Caminhos.CaminhaoPequenoPadrao;
import Estações.EstacaoTransferencia;
import Simulador.Evento;
import Util.No;
import Util.TempoUtil;

import java.util.Random;

/**
 * Representa uma zona urbana onde ocorre geração e coleta de lixo.
 */
public class ZonaUrbana {
    private String nome;
    private EstacaoTransferencia estacao;
    private Random random = new Random();
    private No<CaminhaoPequenoPadrao> caminhoesPequenos = null;
    private int lixoTotalGerado;
    private int lixoTotalColetado;

    /**
     * Cria uma zona urbana com nome e estação de transferência associada.
     */
    public ZonaUrbana(String nome, EstacaoTransferencia estacao) {
        this.nome = nome;
        this.estacao = estacao;
    }

    /**
     * Retorna o nome da zona.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Adiciona um caminhão pequeno à zona.
     */
    public void inserirCaminhaoPequeno(CaminhaoPequenoPadrao caminhao) {
        No<CaminhaoPequenoPadrao> novo = new No<>(caminhao);
        novo.setProximo(caminhoesPequenos);
        caminhoesPequenos = novo;
    }

    /**
     * Define o tempo de coleta, variando conforme o horário (pico ou não).
     */
    public int tempoColetaPorHorario(int minutoAtual) {
        boolean pico = TempoUtil.ehHorarioDePico(minutoAtual);

        if (nome.equals("Centro")) {
            return pico ? 20 : 12;
        } else if (nome.equals("Bairro Sul")) {
            return pico ? 15 : 8;
        } else {
            return pico ? 18 : 10;
        }
    }

    /**
     * Gera uma coleta na zona, se houver caminhão disponível.
     */
    public void gerarColeta(int minutoAtual) {
        System.out.println("Gerando coleta " + nome + " no minuto " + minutoAtual);

        if (random.nextInt(10) < 3) { // Aproximadamente 30% de chance
            int duracao = tempoColetaPorHorario(minutoAtual);
            Evento evento = new Evento(minutoAtual, "Coleta de lixo - " + nome, duracao);

            adicionarLixoGerado(evento.getCarga());

            No<CaminhaoPequenoPadrao> atual = caminhoesPequenos;
            while (atual != null) {
                CaminhaoPequenoPadrao caminhao = atual.getElemento();
                if (caminhao.estaDisponivel(minutoAtual)) {
                    caminhao.coletar(evento, minutoAtual);
                    adicionarLixoColetado(evento.getCarga());
                    break;
                }
                atual = atual.getProximo();
            }
        }
    }

    /**
     * Processa os eventos da fila da estação.
     */
    public void processarFila(int minutoAtual) {
        estacao.processarEventos(minutoAtual);
    }

    /**
     * Soma lixo gerado.
     */
    public void adicionarLixoGerado(int kg) {
        lixoTotalGerado += kg;
    }

    /**
     * Soma lixo coletado.
     */
    public void adicionarLixoColetado(int kg) {
        lixoTotalColetado += kg;
    }

    /**
     * Gera um relatório simples da zona.
     */
    public String gerarRelatorio() {
        return String.format("Zona %s - Gerado: %d kg | Coletado: %d kg", nome, lixoTotalGerado, lixoTotalColetado);
    }

    /**
     * Conta a quantidade de caminhões na zona.
     */
    public int contarCaminhoes() {
        int cont = 0;
        No<CaminhaoPequenoPadrao> atual = caminhoesPequenos;
        while (atual != null) {
            cont++;
            atual = atual.getProximo();
        }
        return cont;
    }

    /**
     * Permite iterar sobre os caminhões da zona.
     */
    public Iterable<CaminhaoPequenoPadrao> iterarCaminhoes() {
        return () -> new java.util.Iterator<CaminhaoPequenoPadrao>() {
            private No<CaminhaoPequenoPadrao> atual = caminhoesPequenos;

            public boolean hasNext() {
                return atual != null;
            }

            public CaminhaoPequenoPadrao next() {
                CaminhaoPequenoPadrao valor = atual.getElemento();
                atual = atual.getProximo();
                return valor;
            }
        };
    }
}
