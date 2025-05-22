package Caminhos;

import Simulador.Evento;
import Estações.EstacaoTransferencia;
import Util.TempoUtil;

/**
 * Representa um caminhão pequeno padrão utilizado na coleta urbana.
 * Este caminhão coleta lixo nas zonas da cidade e descarrega na estação de transferência associada.
 */
public class CaminhaoPequenoPadrao extends CaminhaoPequeno {
    private int ocupadoAte; // Minuto até o qual o caminhão está ocupado
    private EstacaoTransferencia estacaoDestino; // Estação de transferência associada
    private int totalColetado; // Total de lixo coletado em kg

    /**
     * Construtor do caminhão pequeno padrão.
     *
     * @param id               Identificador do caminhão.
     * @param capacidadeMaxima Capacidade máxima em toneladas.
     * @param maximoViagens    Número máximo de viagens permitidas.
     * @param zonaAtual        Zona urbana onde o caminhão está operando.
     * @param estacaoDestino   Estação de transferência associada.
     */
    public CaminhaoPequenoPadrao(String id, int capacidadeMaxima, int maximoViagens, String zonaAtual, EstacaoTransferencia estacaoDestino) {
        super(id, capacidadeMaxima, maximoViagens, zonaAtual);
        this.ocupadoAte = 0;
        this.estacaoDestino = estacaoDestino;
        this.totalColetado = 0;
    }

    /**
     * Coleta uma quantidade de lixo.
     * Incrementa também o total coletado.
     *
     * @param quantidade Quantidade de lixo em kg.
     * @return true se conseguiu coletar, false se excedeu a capacidade.
     */
    @Override
    public boolean coletarLixo(int quantidade) {
        boolean sucesso = super.coletarLixo(quantidade);
        if (sucesso) totalColetado += quantidade;
        return sucesso;
    }

    /**
     * Verifica se o caminhão está disponível no minuto atual.
     *
     * @param minutoAtual Minuto atual da simulação.
     * @return true se está disponível, false se ainda está ocupado.
     */
    public boolean estaDisponivel(int minutoAtual) {
        return minutoAtual >= ocupadoAte;
    }

    /**
     * Executa a coleta de lixo, considerando horário de pico, capacidade e descarregamento na estação.
     *
     * @param evento       Evento de coleta.
     * @param minutoAtual  Minuto atual da simulação.
     */
    public void coletar(Evento evento, int minutoAtual) {
        int coletaKg = 50 + (int)(Math.random() * 451); // Gera valor entre 50kg e 500kg

        // Verifica se está no horário de pico
        boolean horarioDePico = (minutoAtual >= 7 * 60 && minutoAtual <= 9 * 60) ||
                (minutoAtual >= 17 * 60 && minutoAtual <= 19 * 60);

        // Calcula capacidade restante em kg
        int capacidadeRestante = getCapacidadeMaxima() * 1000 - getCargaAtual();

        if (capacidadeRestante <= 0) {
            coletaKg = 0;
        } else {
            coletaKg = Math.min(coletaKg, capacidadeRestante);
        }

        // Realiza a coleta se houver capacidade
        if (coletaKg > 0) {
            coletarLixo(coletaKg);

            System.out.println(">>> " + getId() + " coletou " + coletaKg + "kg às " + minutoAtual + " min");
            System.out.printf("  - Caminhão Pequeno [%s] coletou %d kg em %s (%s)%n",
                    getId(),
                    coletaKg,
                    getZonaAtual(),
                    horarioDePico ? "Horário de Pico" : "Fora de Pico"
            );
        }

        // Atualiza o tempo que ficará ocupado
        ocupadoAte = minutoAtual + evento.getDuracao();

        // Se o caminhão encher, realiza viagem para a estação
        if (getCargaAtual() >= getCapacidadeMaxima() * 1000) {
            System.out.printf("    -> Caminhão Pequeno [%s] CHEIO com %.2f toneladas. Indo para a estação %s.%n",
                    getId(), getCargaAtual() / 1000.0, estacaoDestino.getNome());

            realizarViagemParaEstacao(estacaoDestino.getNome());
            estacaoDestino.receberCaminhao(this, minutoAtual);
            descarregar();
        }
    }

    /**
     * Gera um relatório do total coletado e quantidade de viagens realizadas.
     *
     * @return String com o resumo das operações.
     */
    public String gerarRelatorio() {
        return String.format("Caminhão %s - Total coletado: %d kg | Viagens: %d",
                getId(), totalColetado, getViagensRealizadas());
    }
}
