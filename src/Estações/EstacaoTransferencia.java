package Estações;

import Simulador.Evento;
import Caminhos.CaminhaoPequenoPadrao;
import Caminhos.CaminhaoGrandePadrao;
import Util.No;

/**
 * Classe que representa uma estação de transferência de lixo.
 * Recebe caminhões pequenos, acumula lixo e repassa para caminhões grandes.
 */
public class EstacaoTransferencia {
    private String nome;
    private No<Evento> eventos;
    private No<CaminhaoPequenoPadrao> caminhoesPequenos;
    private No<CaminhaoGrandePadrao> caminhoesGrandes;
    private int cargaAcumulada;

    /**
     * Cria uma estação de transferência com um nome.
     * @param nome Nome da estação.
     */
    public EstacaoTransferencia(String nome) {
        this.nome = nome;
        this.eventos = null;
        this.caminhoesPequenos = null;
        this.caminhoesGrandes = null;
        this.cargaAcumulada = 0;
    }

    /**
     * Adiciona um caminhão pequeno à estação.
     * @param caminhao Caminhão pequeno.
     */
    public void adicionarCaminhao(CaminhaoPequenoPadrao caminhao) {
        No<CaminhaoPequenoPadrao> novo = new No<>(caminhao);
        novo.setProximo(caminhoesPequenos);
        caminhoesPequenos = novo;
    }

    /**
     * Adiciona um caminhão grande à estação.
     * @param caminhao Caminhão grande.
     */
    public void adicionarCaminhao(CaminhaoGrandePadrao caminhao) {
        No<CaminhaoGrandePadrao> novo = new No<>(caminhao);
        novo.setProximo(caminhoesGrandes);
        caminhoesGrandes = novo;
    }

    /**
     * Recebe um evento de geração de lixo.
     * @param evento Evento a ser processado.
     */
    public void receberEvento(Evento evento) {
        No<Evento> novo = new No<>(evento);
        novo.setProximo(eventos);
        eventos = novo;
    }

    /**
     * Processa todos os eventos e faz a coleta.
     * Caminhões pequenos fazem a coleta dos eventos.
     * Quando há lixo acumulado, caminhões grandes levam ao aterro.
     * @param minutoAtual Minuto atual da simulação.
     */
    public void processarEventos(int minutoAtual) {
        No<Evento> atualEvento = eventos;
        while (atualEvento != null) {
            Evento evento = atualEvento.getElemento();
            if (!evento.foiProcessado()) {
                No<CaminhaoPequenoPadrao> atualCaminhao = caminhoesPequenos;
                while (atualCaminhao != null) {
                    CaminhaoPequenoPadrao caminhao = atualCaminhao.getElemento();
                    if (caminhao.estaDisponivel(minutoAtual)) {
                        caminhao.coletar(evento, minutoAtual);
                        cargaAcumulada += evento.getCarga();
                        evento.marcarComoProcessado();
                        break;
                    }
                    atualCaminhao = atualCaminhao.getProximo();
                }
            }
            atualEvento = atualEvento.getProximo();
        }

        // Verifica se há caminhão grande disponível para levar o lixo acumulado
        if (cargaAcumulada > 0) {
            No<CaminhaoGrandePadrao> atualGrande = caminhoesGrandes;
            while (atualGrande != null) {
                CaminhaoGrandePadrao grande = atualGrande.getElemento();
                if (grande.estaDisponivel(minutoAtual)) {
                    grande.receberCarga(cargaAcumulada, minutoAtual);
                    grande.partirParaAterro(minutoAtual);
                    cargaAcumulada = 0;
                    break;
                }
                atualGrande = atualGrande.getProximo();
            }
        }
    }

    /**
     * Recebe um caminhão pequeno que veio descarregar na estação.
     * @param caminhao Caminhão pequeno.
     * @param minutoAtual Minuto atual da simulação.
     */
    public void receberCaminhao(CaminhaoPequenoPadrao caminhao, int minutoAtual) {
        adicionarCaminhao(caminhao);
        int descarregado = caminhao.descarregar();
        cargaAcumulada += descarregado;

        System.out.printf("      Estação [%s] recebeu caminhão pequeno [%s] às %d min com %d kg.%n",
                nome, caminhao.getId(), minutoAtual, descarregado);
    }

    /**
     * Retorna o nome da estação.
     * @return Nome da estação.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Gera um relatório simples da estação.
     * @return Texto com informações da estação.
     */
    public String gerarRelatorio() {
        return String.format("Estação %s - Carga total armazenada: %d kg", nome, cargaAcumulada);
    }
}
