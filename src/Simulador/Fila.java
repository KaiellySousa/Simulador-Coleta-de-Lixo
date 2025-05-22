package Simulador;

/**
 * Classe que implementa uma fila genérica usando nós encadeados.
 * Funciona no modelo FIFO (First In, First Out).
 */
public class Fila<T> {
    private No<T> inicio;
    private No<T> fim;
    private int tamanho;

    /**
     * Cria uma fila vazia.
     */
    public Fila() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    /**
     * Adiciona um elemento no final da fila.
     * @param elemento Elemento a ser adicionado.
     */
    public void enfileirar(T elemento) {
        No<T> novo = new No<>(elemento);
        if (fim != null) {
            fim.setProximo(novo);
        } else {
            inicio = novo;
        }
        fim = novo;
        tamanho++;
    }

    /**
     * Remove e retorna o primeiro elemento da fila.
     * @return Elemento removido ou null se a fila estiver vazia.
     */
    public T desenfileirar() {
        if (inicio == null) return null;
        T elemento = inicio.getElemento();
        inicio = inicio.getProximo();
        if (inicio == null) fim = null;
        tamanho--;
        return elemento;
    }

    /**
     * Retorna o tamanho atual da fila.
     * @return Número de elementos na fila.
     */
    public int tamanho() {
        return tamanho;
    }

    /**
     * Verifica se a fila está vazia.
     * @return true se vazia, false se tiver elementos.
     */
    public boolean estaVazia() {
        return tamanho == 0;
    }

    /**
     * Permite percorrer a fila usando um for-each.
     * @return Iterable dos elementos na ordem da fila.
     */
    public Iterable<T> iterar() {
        return () -> new java.util.Iterator<T>() {
            private No<T> atual = inicio;

            public boolean hasNext() {
                return atual != null;
            }

            public T next() {
                T valor = atual.getElemento();
                atual = atual.getProximo();
                return valor;
            }
        };
    }
}
