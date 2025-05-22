package Simulador;

import Estações.EstacaoTransferencia;
import Util.TempoUtil;
import Zonas.ZonaUrbana;
import Caminhos.CaminhaoPequenoPadrao;

/**
 * Classe responsável por executar a simulação da coleta de lixo.
 * Controla os ciclos de dias, horário de operação, geração de coleta,
 * e geração de relatórios finais.
 */
public class Simulador {
    private int duracaoMinutos; // Duração total da simulação em minutos
    private Fila<ZonaUrbana> zonas; // Fila de zonas urbanas participantes da simulação
    private EstacaoTransferencia estacaoSul; // Estação que atende Sul, Centro e Leste
    private EstacaoTransferencia estacaoNorte; // Estação que atende Norte e Sudeste

    /**
     * Construtor do simulador.
     *
     * @param duracaoMinutos Duração total da simulação em minutos
     * @param estacaoSul     Instância da estação Sul
     * @param estacaoNorte   Instância da estação Norte
     */
    public Simulador(int duracaoMinutos, EstacaoTransferencia estacaoSul, EstacaoTransferencia estacaoNorte) {
        this.duracaoMinutos = duracaoMinutos;
        this.zonas = new Fila<ZonaUrbana>();
        this.estacaoNorte = estacaoNorte;
        this.estacaoSul = estacaoSul;
    }

    /**
     * Adiciona uma zona urbana à simulação.
     *
     * @param zona Zona urbana a ser adicionada
     */
    public void adicionarZona(ZonaUrbana zona) {
        zonas.enfileirar(zona);
    }

    /**
     * Executa a simulação de acordo com os parâmetros fornecidos.
     */
    public void executar() {
        int minutosPorDia = 24 * 60; // Total de minutos em um dia
        int totalDias = duracaoMinutos / minutosPorDia; // Quantidade total de dias da simulação

        boolean foraHorarioAtivo = false; // Controla se está fora do horário de operação
        int inicioForaDoHorario = -1; // Armazena o início do período fora do horário

        // Loop principal da simulação
        for (int minutoAtual = 0; minutoAtual < duracaoMinutos; minutoAtual++) {
            int diaAtual = minutoAtual / minutosPorDia + 1;
            int minutoNoDia = minutoAtual % minutosPorDia;

            // Início do dia
            if (minutoNoDia == 0) {
                System.out.println("\n===================================================");
                System.out.println("           Iniciando simulação do dia " + diaAtual);
                System.out.println("===================================================\n");
            }

            // Verifica se está dentro do horário operacional (06:00 às 18:00)
            boolean dentroHorario = minutoNoDia >= 360 && minutoNoDia < 1080;

            if (dentroHorario) {
                // Se estava fora do horário, informa o período que ficou parado
                if (foraHorarioAtivo) {
                    String inicioString = TempoUtil.formatarMinutos(inicioForaDoHorario);
                    String fimString = TempoUtil.formatarMinutos(minutoNoDia);
                    System.out.println("[" + inicioString + "] às [" + fimString + "] Fora do horário de operação dos caminhões.");
                    foraHorarioAtivo = false;
                }

                System.out.println("[" + TempoUtil.formatarMinutos(minutoNoDia) + "]");

                // Processa todas as zonas
                int tamanho = zonas.tamanho();
                for (int i = 0; i < tamanho; i++) {
                    ZonaUrbana zona = zonas.desenfileirar();

                    // Gera a coleta de lixo da zona no minuto atual
                    zona.gerarColeta(minutoAtual);

                    zonas.enfileirar(zona);
                }

                System.out.println();
            } else {
                // Marca início do período fora do horário de operação
                if (!foraHorarioAtivo) {
                    foraHorarioAtivo = true;
                    inicioForaDoHorario = minutoNoDia;
                }
            }

            // Fim do dia
            if (minutoNoDia == 1439) {
                // Se ficou fora do horário das 18:00 até 23:59, exibe isso
                if (foraHorarioAtivo && inicioForaDoHorario == 1080) {
                    String inicioString = TempoUtil.formatarMinutos(inicioForaDoHorario);
                    String fimString = TempoUtil.formatarMinutos(360); // 06:00 do dia seguinte
                    System.out.println("[" + inicioString + "] às [" + fimString + "] Fora do horário de operação dos caminhões.");
                    foraHorarioAtivo = false;
                }

                System.out.println("===================================================");
                System.out.println("             Fim da simulação do dia " + diaAtual);
                System.out.println("===================================================\n");
            }
        }

        // RELATÓRIO FINAL
        System.out.println("\n============== RELATÓRIO FINAL ==============");

        // Relatório por zona
        System.out.println("▶ Zonas:");
        for (ZonaUrbana zona : zonas.iterar()) {
            System.out.println("  - " + zona.gerarRelatorio());
        }

        // Relatório por caminhões
        System.out.println("\n▶ Caminhões:");
        for (ZonaUrbana zona : zonas.iterar()) {
            for (CaminhaoPequenoPadrao cam : zona.iterarCaminhoes()) {
                System.out.println("  - " + cam.gerarRelatorio());
            }
        }
    }
}
