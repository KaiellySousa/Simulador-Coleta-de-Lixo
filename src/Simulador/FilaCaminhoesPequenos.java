package Simulador;

import Caminhos.CaminhaoPequeno;

/**
 * Classe que implementa uma fila específica para caminhões pequenos.
 * Funciona no modelo FIFO (First In, First Out).
 */
public class FilaCaminhoesPequenos {

    // Nó interno da fila
    private static class No {
        CaminhaoPequeno valor;
        No proximo;

        No(CaminhaoPequeno valor) {
            this.valor = valor;
            this.proximo = null;
        }
    }

    private No frente;
    private No tras;

    /**
     * Cria uma fila vazia de caminhões pequenos.
     */
    public FilaCaminhoesPequenos() {
        frente = null;
        tras = null;
    }

    /**
     * Adiciona um caminhão pequeno no final da fila.
     * @param caminhao Caminhão a ser adicionado.
     */
    public void enfileirar(CaminhaoPequeno caminhao) {
        No novo = new No(caminhao);
        if (vazia()) {
            frente = tras = novo;
        } else {
            tras.proximo = novo;
            tras = novo;
        }
    }

    /**
     * Remove e retorna o caminhão na frente da fila.
     * @return Caminhão removido ou null se a fila estiver vazia.
     */
    public CaminhaoPequeno desenfileirar() {
        if (vazia()) return null;
        CaminhaoPequeno valor = frente.valor;
        frente = frente.proximo;
        if (frente == null) tras = null;
        return valor;
    }

    /**
     * Retorna o caminhão na frente da fila sem remover.
     * @return Caminhão na frente ou null se vazia.
     */
    public CaminhaoPequeno espiar() {
        return vazia() ? null : frente.valor;
    }

    /**
     * Verifica se a fila está vazia.
     * @return true se vazia, false se tiver elementos.
     */
    public boolean vazia() {
        return frente == null;
    }

    /**
     * Retorna o nó da frente da fila.
     * @return Nó da frente.
     */
    public No getFrente() {
        return frente;
    }

    /**
     * Permite percorrer a fila usando for-each.
     * @return Iterable dos caminhões na ordem da fila.
     */
    public Iterable<CaminhaoPequeno> iterar() {
        return () -> new java.util.Iterator<CaminhaoPequeno>() {
            private No atual = frente;

            public boolean hasNext() {
                return atual != null;
            }

            public CaminhaoPequeno next() {
                CaminhaoPequeno valor = atual.valor;
                atual = atual.proximo;
                return valor;
            }
        };
    }
}
