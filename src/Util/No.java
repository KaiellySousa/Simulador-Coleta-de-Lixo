package Util;

/**
 * Nó de uma estrutura encadeada.
 * Armazena um elemento e uma referência para o próximo nó.
 */
public class No<T> {
    private T elemento;          // Dado armazenado
    private No<T> proximo;       // Próximo nó

    /**
     * Cria um nó com o elemento informado.
     */
    public No(T elemento) {
        this.elemento = elemento;
        this.proximo = null;
    }

    /**
     * Retorna o elemento do nó.
     */
    public T getElemento() {
        return elemento;
    }

    /**
     * Retorna o próximo nó.
     */
    public No<T> getProximo() {
        return proximo;
    }

    /**
     * Define o próximo nó.
     */
    public void setProximo(No<T> proximo) {
        this.proximo = proximo;
    }
}
