package Caminhos;

import Simulador.Evento;

/**
 * Caminhão grande padrão, usado para levar lixo ao aterro.
 * Controla quando estará ocupado durante a simulação.
 */
public class CaminhaoGrandePadrao extends CaminhaoGrande {
    private int ocupadoAte;

    public CaminhaoGrandePadrao(int tempoMaximoEspera) {
        super(tempoMaximoEspera);
        this.ocupadoAte = 0;
    }

    /**
     * Retorna a capacidade do caminhão (20 toneladas).
     */
    public int getCapacidade() {
        return 20;
    }

    /**
     * Verifica se o caminhão está disponível no minuto atual.
     */
    public boolean estaDisponivel(int minutoAtual) {
        return minutoAtual >= ocupadoAte && super.estaDisponivel();
    }

    /**
     * Marca o caminhão como ocupado após coletar um evento.
     *
     * @param evento evento que o caminhão irá realizar.
     * @param minutoAtual minuto atual da simulação.
     */
    public void coletar(Evento evento, int minutoAtual) {
        System.out.println("  - Caminhão Grande [" + getId() + "] coletou: " + evento.getDescricao());
        ocupadoAte = minutoAtual + evento.getDuracao();
    }
}
