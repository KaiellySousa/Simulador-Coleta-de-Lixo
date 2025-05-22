package Simulador;

/**
 * Classe que representa um nó genérico para estruturas encadeadas,
 * como listas, pilhas e filas.
 *
 * @param <T> Tipo de dado armazenado no nó.
 */
public class No<T> {
    private T elemento;
    private No<T> proximo;

    /**
     * Cria um nó com o elemento fornecido.
     * @param elemento Elemento a ser armazenado.
     */
    public No(T elemento) {
        this.elemento = elemento;
        this.proximo = null;
    }

    /**
     * Retorna o elemento armazenado no nó.
     * @return Elemento do nó.
     */
    public T getElemento() {
        return elemento;
    }

    /**
     * Retorna a referência para o próximo nó.
     * @return Próximo nó ou null se não houver.
     */
    public No<T> getProximo() {
        return proximo;
    }

    /**
     * Define o próximo nó na sequência.
     * @param proximo Nó a ser vinculado como próximo.
     */
    public void setProximo(No<T> proximo) {
        this.proximo = proximo;
    }
}
