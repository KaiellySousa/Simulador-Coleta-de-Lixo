package Util;

/**
 * Classe utilitária para controlar e formatar o tempo na simulação.
 */
public class TempoUtil {
    private static int tempoAtual = 0;

    /**
     * Avança o tempo em minutos.
     */
    public static void avancarTempo(int minutos) {
        tempoAtual += minutos;
    }

    /**
     * Define o tempo atual em minutos.
     */
    public static void definirTempo(int minutos) {
        tempoAtual = minutos;
    }

    /**
     * Retorna o tempo atual em minutos.
     */
    public static int getTempoEmMinutos() {
        return tempoAtual;
    }

    /**
     * Retorna o tempo atual no formato "HH:MM".
     */
    public static String getTempoAtual() {
        return formatarMinutos(tempoAtual);
    }

    /**
     * Formata minutos para "HH:MM".
     */
    public static String formatarMinutos(int minutosTotais) {
        if (minutosTotais >= 1440) return "24:00"; // Trata 24:00 como meia-noite
        int horas = minutosTotais / 60;
        int minutos = minutosTotais % 60;
        return String.format("%02d:%02d", horas, minutos);
    }

    /**
     * Verifica se é horário de pico.
     * Horário de pico: 7h às 9h e 17h às 19h.
     */
    public static boolean ehHorarioDePico(int minutoAtual) {
        int hora = minutoAtual / 60;
        return (hora >= 7 && hora < 9) || (hora >= 17 && hora < 19);
    }
}
