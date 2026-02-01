import { environment } from './environment';

/**
 * Utilitários helper para a aplicação.
 */
export class AppUtils {
  
  /**
   * Constrói a URL completa da API.
   */
  static getApiUrl(endpoint: string): string {
    return `${environment.apiUrl}${endpoint}`;
  }
  
  /**
   * Formata um número como porcentagem.
   */
  static formatPercentage(value: number): string {
    return `${(value * 100).toFixed(1)}%`;
  }
  
  /**
   * Valida se um valor está dentro do range permitido.
   */
  static isValidStatValue(value: number, min: number = 0, max: number = 10): boolean {
    return value >= min && value <= max;
  }
  
  /**
   * Gera uma cor baseada em um score (0-10).
   */
  static getScoreColor(score: number): string {
    if (score >= 9) return 'success';
    if (score >= 7) return 'info';
    if (score >= 5) return 'warning';
    return 'danger';
  }
  
  /**
   * Debounce function para otimizar buscas.
   */
  static debounce<T extends (...args: any[]) => any>(
    func: T,
    wait: number
  ): (...args: Parameters<T>) => void {
    let timeout: NodeJS.Timeout | null = null;
    
    return function executedFunction(...args: Parameters<T>) {
      const later = () => {
        timeout = null;
        func(...args);
      };
      
      if (timeout) clearTimeout(timeout);
      timeout = setTimeout(later, wait);
    };
  }
  
  /**
   * Sanitiza uma string para busca.
   */
  static sanitizeSearchTerm(term: string): string {
    return term.toLowerCase().trim().normalize('NFD').replace(/[\u0300-\u036f]/g, '');
  }
}
