/**
 * Enumerações usadas na aplicação.
 */

/** Classes de campeões */
export enum ChampionClass {
  FIGHTER = 'fighter',
  TANK = 'tank',
  MAGE = 'mage',
  ASSASSIN = 'assassin',
  MARKSMAN = 'marksman',
  SUPPORT = 'support'
}

/** Labels traduzidos para classes */
export const ChampionClassLabels: Record<ChampionClass, string> = {
  [ChampionClass.FIGHTER]: 'Lutador',
  [ChampionClass.TANK]: 'Tanque',
  [ChampionClass.MAGE]: 'Mago',
  [ChampionClass.ASSASSIN]: 'Assassino',
  [ChampionClass.MARKSMAN]: 'Atirador',
  [ChampionClass.SUPPORT]: 'Suporte'
};

/** Rotas da aplicação */
export enum AppRoutes {
  HOME = '/',
  CHAMPIONS = '/champions',
  ADD_CHAMPION = '/add-champion',
  COMPOSITIONS = '/compositions',
  ANALYSIS = '/analysis'
}

/** Posições/Lanes do jogo */
export enum Lane {
  TOP = 'top',
  JUNGLE = 'jungle',
  MID = 'mid',
  ADC = 'adc',
  SUPPORT = 'support',
  ALL = 'all'
}

/** Labels traduzidos para lanes */
export const LaneLabels: Record<Lane, string> = {
  [Lane.TOP]: 'Topo',
  [Lane.JUNGLE]: 'Selva',
  [Lane.MID]: 'Meio',
  [Lane.ADC]: 'ADC',
  [Lane.SUPPORT]: 'Suporte',
  [Lane.ALL]: 'Todas'
};
