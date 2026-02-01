package com.lol.teambuilder.constants;

/**
 * Constantes globais da aplicação.
 * Centraliza valores mágicos e configurações estáticas.
 * 
 * @author Team Builder
 * @version 1.0.0
 */
public final class AppConstants {
    
    // Previne instanciação
    private AppConstants() {
        throw new UnsupportedOperationException("Classe de constantes não pode ser instanciada");
    }
    
    /**
     * Constantes relacionadas aos limites de valores.
     */
    public static final class Limits {
        public static final int MIN_STAT_VALUE = 0;
        public static final int MAX_STAT_VALUE = 10;
        public static final int TEAM_SIZE = 5;
        public static final int MAX_NAME_LENGTH = 100;
        
        private Limits() {}
    }
    
    /**
     * Constantes para análise de composições.
     */
    public static final class Analysis {
        public static final double BALANCED_SCORE_THRESHOLD = 7.0;
        public static final double GOOD_SCORE_THRESHOLD = 8.0;
        public static final double EXCELLENT_SCORE_THRESHOLD = 9.0;
        
        public static final int LOW_DAMAGE_THRESHOLD = 25;
        public static final int LOW_TANKINESS_THRESHOLD = 20;
        public static final int LOW_CC_THRESHOLD = 15;
        
        private Analysis() {}
    }
    
    /**
     * Constantes para recomendações.
     */
    public static final class Recommendation {
        public static final int TOP_RECOMMENDATIONS = 5;
        public static final double MIN_SYNERGY_SCORE = 0.0;
        public static final double MAX_SYNERGY_SCORE = 10.0;
        
        private Recommendation() {}
    }
    
    /**
     * Mensagens de erro padronizadas.
     */
    public static final class ErrorMessages {
        public static final String CHAMPION_NOT_FOUND = "Campeão não encontrado com ID: ";
        public static final String COMPOSITION_NOT_FOUND = "Composição não encontrada com ID: ";
        public static final String INVALID_STAT_VALUE = "Valor de estatística deve estar entre 0 e 10";
        public static final String INVALID_TEAM_SIZE = "Time deve ter exatamente 5 campeões";
        public static final String DUPLICATE_CHAMPION = "Campeão duplicado na composição";
        
        private ErrorMessages() {}
    }
    
    /**
     * Mensagens de sucesso padronizadas.
     */
    public static final class SuccessMessages {
        public static final String CHAMPION_CREATED = "Campeão criado com sucesso";
        public static final String CHAMPION_UPDATED = "Campeão atualizado com sucesso";
        public static final String CHAMPION_DELETED = "Campeão deletado com sucesso";
        public static final String COMPOSITION_SAVED = "Composição salva com sucesso";
        
        private SuccessMessages() {}
    }
    
    /**
     * Endpoints da API.
     */
    public static final class Endpoints {
        public static final String API_BASE = "/api";
        public static final String CHAMPIONS = API_BASE + "/champions";
        public static final String COMPOSITIONS = API_BASE + "/compositions";
        public static final String ANALYSIS = API_BASE + "/analysis";
        public static final String RECOMMENDATIONS = API_BASE + "/recommendations";
        
        private Endpoints() {}
    }
}
