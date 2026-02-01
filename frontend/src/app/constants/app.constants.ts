/**
 * Constantes globais da aplicação Angular.
 * Centraliza valores mágicos e configurações.
 */
export class AppConstants {
  
  /** URL base da API */
  static readonly API_BASE_URL = 'http://localhost:8080/api';
  
  /** Endpoints da API */
  static readonly ENDPOINTS = {
    CHAMPIONS: '/champions',
    COMPOSITIONS: '/compositions',
    ANALYSIS: '/analysis',
    RECOMMENDATIONS: '/recommendations'
  };
  
  /** Limites de valores */
  static readonly LIMITS = {
    MIN_STAT: 0,
    MAX_STAT: 10,
    TEAM_SIZE: 5,
    MAX_NAME_LENGTH: 100
  };
  
  /** Configurações de UI */
  static readonly UI = {
    DEFAULT_ITEMS_PER_PAGE: 10,
    DEBOUNCE_TIME: 300,
    TOAST_DURATION: 3000
  };
  
  /** Mensagens de erro */
  static readonly ERROR_MESSAGES = {
    GENERIC: 'Ocorreu um erro inesperado',
    NETWORK: 'Erro de conexão com o servidor',
    NOT_FOUND: 'Recurso não encontrado',
    INVALID_DATA: 'Dados inválidos'
  };
  
  /** Mensagens de sucesso */
  static readonly SUCCESS_MESSAGES = {
    CHAMPION_CREATED: 'Campeão criado com sucesso!',
    CHAMPION_UPDATED: 'Campeão atualizado com sucesso!',
    CHAMPION_DELETED: 'Campeão removido com sucesso!',
    COMPOSITION_SAVED: 'Composição salva com sucesso!'
  };
}
