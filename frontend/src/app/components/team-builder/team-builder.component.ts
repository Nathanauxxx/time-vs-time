import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ChampionService } from '../../services/champion.service';
import { TeamCompositionService } from '../../services/team-composition.service';
import { RiotApiService } from '../../services/riot-api.service';
import { Champion } from '../../models/champion.model';
import { TeamComposition, TeamState } from '../../models/team-composition.model';

@Component({
  selector: 'app-team-builder',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './team-builder.component.html',
  styleUrls: ['./team-builder.component.scss']
})
export class TeamBuilderComponent implements OnInit {
  champions: Champion[] = [];
  currentTeams: TeamState = { blue: {}, red: {} };
  currentPosition: string | null = null;
  currentFilter: string = 'all';
  searchTerm: string = '';
  isModalOpen: boolean = false;
  filteredChampions: Champion[] = [];
  isSyncing: boolean = false;
  syncMessage: string = '';
  comparisonResult: any = null;

  constructor(
    private championService: ChampionService,
    private teamCompositionService: TeamCompositionService,
    private riotApiService: RiotApiService
  ) {}

  ngOnInit(): void {
    this.loadChampions();
  }

  loadChampions(): void {
    console.log('🔍 Iniciando carregamento de campeões...');
    console.log('📡 URL da API:', `${this.championService['apiUrl']}`);
    
    this.championService.getAllChampions().subscribe({
      next: (champions) => {
        this.champions = champions;
        this.filteredChampions = champions;
        console.log(`✅ ${champions.length} campeões carregados do PostgreSQL!`);
        console.log('📋 Primeiros 3 campeões:', champions.slice(0, 3));
      },
      error: (error) => {
        console.error('❌ Erro ao carregar campeões:', error);
        console.error('❌ Status:', error.status);
        console.error('❌ Mensagem:', error.message);
        alert('Erro ao carregar campeões. Verifique se o backend está rodando em http://localhost:8080');
      }
    });
  }

  openModal(position: string): void {
    this.currentPosition = position;
    this.isModalOpen = true;
    this.searchTerm = '';
    this.filterChampions();
  }

  closeModal(): void {
    this.isModalOpen = false;
    this.currentPosition = null;
  }

  selectChampion(champion: Champion): void {
    if (!this.currentPosition) return;

    const [lane, team] = this.currentPosition.split('-');
    
    // Verificar se o campeão já está no time
    const teamChampions = Object.values(this.currentTeams[team as 'blue' | 'red']);
    if (teamChampions.some((c: any) => c.name === champion.name)) {
      alert(`${champion.name} já está neste time!`);
      return;
    }

    this.currentTeams[team as 'blue' | 'red'][lane] = champion;
    this.closeModal();
  }

  removeChampion(position: string): void {
    const [lane, team] = position.split('-');
    delete this.currentTeams[team as 'blue' | 'red'][lane];
  }

  filterChampions(): void {
    this.filteredChampions = this.champions.filter(champion => {
      const matchesSearch = champion.name.toLowerCase().includes(this.searchTerm.toLowerCase());
      const matchesFilter = this.currentFilter === 'all' || 
                           champion.championClass?.toLowerCase() === this.currentFilter.toLowerCase();
      return matchesSearch && matchesFilter;
    });
  }

  setFilter(filter: string): void {
    this.currentFilter = filter;
    this.filterChampions();
  }

  onSearchChange(): void {
    this.filterChampions();
  }

  clearAll(): void {
    if (confirm('Deseja limpar todas as seleções?')) {
      this.currentTeams = { blue: {}, red: {} };
    }
  }

  saveComposition(): void {
    const blueTeam = Object.values(this.currentTeams.blue);
    const redTeam = Object.values(this.currentTeams.red);

    if (blueTeam.length === 0 && redTeam.length === 0) {
      alert('⚠️ Adicione pelo menos um campeão antes de salvar!');
      return;
    }

    const name = prompt('📝 Nome da composição:', 'Minha Composição');
    if (!name) return;

    const description = prompt('📄 Descrição (opcional):', 'Composição criada') || 'Composição criada';

    const composition: TeamComposition = {
      name: name,
      description: description,
      blueTop: this.currentTeams.blue['top']?.id || null,
      blueJungle: this.currentTeams.blue['jungle']?.id || null,
      blueMid: this.currentTeams.blue['mid']?.id || null,
      blueAdc: this.currentTeams.blue['adc']?.id || null,
      blueSupport: this.currentTeams.blue['support']?.id || null,
      redTop: this.currentTeams.red['top']?.id || null,
      redJungle: this.currentTeams.red['jungle']?.id || null,
      redMid: this.currentTeams.red['mid']?.id || null,
      redAdc: this.currentTeams.red['adc']?.id || null,
      redSupport: this.currentTeams.red['support']?.id || null
    };

    this.teamCompositionService.saveComposition(composition).subscribe({
      next: (result) => {
        alert(`✅ Composição "${name}" salva no PostgreSQL!\nID: ${result.id}`);
      },
      error: (error) => {
        console.error('❌ Erro ao salvar:', error);
        alert('❌ Erro ao salvar composição. Verifique se o backend está rodando.');
      }
    });
  }

  getChampionIcon(champion: any): string {
    return champion.iconUrl || champion.name.charAt(0);
  }

  hasIconUrl(champion: any): boolean {
    return !!champion.iconUrl;
  }

  getTeamChampion(team: string, lane: string): any {
    return this.currentTeams[team as 'blue' | 'red'][lane];
  }

  getTeamStats(team: 'blue' | 'red'): any {
    const teamChampions = Object.values(this.currentTeams[team]);
    if (teamChampions.length === 0) {
      return { physical: 0, magic: 0, tank: 0, cc: 0 };
    }

    const stats = {
      physical: 0,
      magic: 0,
      tank: 0,
      cc: 0
    };

    teamChampions.forEach((champion: any) => {
      stats.physical += champion.physicalDamage || 0;
      stats.magic += champion.magicDamage || 0;
      stats.tank += champion.tankiness || 0;
      stats.cc += champion.crowdControl || 0;
    });

    const maxValue = teamChampions.length * 10;
    return {
      physical: (stats.physical / maxValue) * 100,
      magic: (stats.magic / maxValue) * 100,
      tank: (stats.tank / maxValue) * 100,
      cc: (stats.cc / maxValue) * 100
    };
  }

  /**
   * Sincroniza campeões com a API da Riot Games
   */
  syncWithRiotApi(): void {
    if (this.isSyncing) {
      return;
    }

    const confirmed = confirm('🎮 Deseja sincronizar os campeões com a API oficial da Riot Games?\n\n' +
      'Isso irá:\n' +
      '✅ Buscar TODOS os campeões\n' +
      '✅ Atualizar com dados REAIS de habilidades\n' +
      '✅ Calcular dano físico/mágico/CC baseado em Q/W/E/R\n\n' +
      'Pode levar alguns minutos...');

    if (!confirmed) {
      return;
    }

    this.isSyncing = true;
    this.syncMessage = '🔄 Sincronizando com a API da Riot Games...';
    console.log('🎮 Iniciando sincronização com API da Riot...');

    this.riotApiService.syncChampions().subscribe({
      next: (response) => {
        this.isSyncing = false;
        this.syncMessage = `✅ ${response.message} (${response.total} campeões)`;
        console.log('✅ Sincronização concluída:', response);
        
        // Recarrega os campeões
        this.loadChampions();
        
        // Limpa mensagem após 5 segundos
        setTimeout(() => {
          this.syncMessage = '';
        }, 5000);
      },
      error: (error) => {
        this.isSyncing = false;
        this.syncMessage = '❌ Erro ao sincronizar. Verifique se o backend está rodando.';
        console.error('❌ Erro na sincronização:', error);
        
        setTimeout(() => {
          this.syncMessage = '';
        }, 5000);
      }
    });
  }

  /**
   * Verifica se ambos os times têm pelo menos 1 campeão para comparar
   */
  canCompareTeams(): boolean {
    const blueCount = Object.keys(this.currentTeams.blue).length;
    const redCount = Object.keys(this.currentTeams.red).length;
    return blueCount > 0 && redCount > 0;
  }

  /**
   * Analisa a composição estratégica de um time (sinergia e papéis)
   */
  private analyzeTeamComposition(team: 'blue' | 'red'): any {
    const lanes = this.currentTeams[team];
    const analysis = {
      hasTank: false,
      hasSupport: false,
      hasADC: false,
      hasJungleGank: false,
      hasMidCarry: false,
      hasEngageTool: false,
      hasProtectionSupport: false,
      damageBalance: { physical: 0, magic: 0 },
      synergyScore: 0,
      strategy: '',
      missingRoles: [] as string[]
    };

    // Analisa cada lane
    const top = lanes['top'];
    const jungle = lanes['jungle'];
    const mid = lanes['mid'];
    const adc = lanes['adc'];
    const support = lanes['support'];

    // TOP: Tank ou Fighter
    if (top) {
      if (top.championClass === 'Tank') {
        analysis.hasTank = true;
        analysis.hasEngageTool = true;
      } else if (top.championClass === 'Fighter' || top.championClass === 'Assassin') {
        analysis.damageBalance.physical += 25;
      }
    } else {
      analysis.missingRoles.push('Top');
    }

    // JUNGLE: Capacidade de gank e engajamento
    if (jungle) {
      if (jungle.championClass === 'Tank' || jungle.championClass === 'Fighter') {
        analysis.hasJungleGank = true;
        analysis.hasEngageTool = true;
      } else if (jungle.championClass === 'Assassin') {
        analysis.hasJungleGank = true;
        analysis.damageBalance.physical += 20;
      }
      if (jungle.championClass === 'Tank') {
        analysis.hasTank = true;
      }
    } else {
      analysis.missingRoles.push('Jungle');
    }

    // MID: Carry (Mago, Assassino ou Lutador)
    if (mid) {
      analysis.hasMidCarry = true;
      if (mid.championClass === 'Mage') {
        analysis.damageBalance.magic += 30;
      } else if (mid.championClass === 'Assassin') {
        analysis.damageBalance.physical += 25;
      } else if (mid.championClass === 'Fighter') {
        analysis.damageBalance.physical += 20;
      }
    } else {
      analysis.missingRoles.push('Mid');
    }

    // ADC: Dano físico sustentado
    if (adc) {
      analysis.hasADC = true;
      analysis.damageBalance.physical += 30;
    } else {
      analysis.missingRoles.push('ADC');
    }

    // SUPPORT: Proteção e utilitário
    if (support) {
      analysis.hasSupport = true;
      if (support.championClass === 'Support') {
        analysis.hasProtectionSupport = true;
        // Suportes como Lulu, Janna, Nami protegem o ADC
        if (adc) {
          analysis.synergyScore += 25; // Sinergia Support + ADC
        }
      } else if (support.championClass === 'Tank') {
        analysis.hasTank = true;
        analysis.hasEngageTool = true;
      }
    } else {
      analysis.missingRoles.push('Support');
    }

    // Verifica sinergia adicional
    // Se tem tank e ADC, +15 pontos (frontline protege carry)
    if (analysis.hasTank && analysis.hasADC) {
      analysis.synergyScore += 15;
    }

    // Se tem engage (tank/fighter jungle ou tank top) e mid carry, +10 pontos
    if (analysis.hasEngageTool && analysis.hasMidCarry) {
      analysis.synergyScore += 10;
    }

    // Se tem jungle gank e mid carry, +10 pontos (snowball mid lane)
    if (analysis.hasJungleGank && analysis.hasMidCarry) {
      analysis.synergyScore += 10;
    }

    // Define estratégia do time
    if (analysis.damageBalance.physical > 60) {
      analysis.strategy = 'Composição de Dano Físico (AD Heavy)';
    } else if (analysis.damageBalance.magic > 50) {
      analysis.strategy = 'Composição de Dano Mágico (AP Heavy)';
    } else if (analysis.hasTank && analysis.hasEngageTool) {
      analysis.strategy = 'Composição de Engage/Team Fight';
    } else if (analysis.hasProtectionSupport && analysis.hasADC) {
      analysis.strategy = 'Composição Protect the ADC';
    } else {
      analysis.strategy = 'Composição Balanceada';
    }

    return analysis;
  }

  /**
   * Compara os dois times ESTRATEGICAMENTE
   */
  compareTeams(): void {
    const blueAnalysis = this.analyzeTeamComposition('blue');
    const redAnalysis = this.analyzeTeamComposition('red');
    
    // Score baseado em sinergia e composição (0-100)
    let blueScore = 50; // Base
    let redScore = 50;  // Base

    // Sinergia é o mais importante
    blueScore += blueAnalysis.synergyScore;
    redScore += redAnalysis.synergyScore;

    // Penalidade por papéis faltando
    blueScore -= blueAnalysis.missingRoles.length * 10;
    redScore -= redAnalysis.missingRoles.length * 10;

    // Bônus por ter todos os papéis essenciais
    if (blueAnalysis.hasTank && blueAnalysis.hasADC && blueAnalysis.hasSupport) {
      blueScore += 20;
    }
    if (redAnalysis.hasTank && redAnalysis.hasADC && redAnalysis.hasSupport) {
      redScore += 20;
    }

    // Bônus por balanceamento de dano
    const blueDamageTotal = blueAnalysis.damageBalance.physical + blueAnalysis.damageBalance.magic;
    const redDamageTotal = redAnalysis.damageBalance.physical + redAnalysis.damageBalance.magic;
    
    if (blueDamageTotal >= 60 && blueDamageTotal <= 90) blueScore += 15;
    if (redDamageTotal >= 60 && redDamageTotal <= 90) redScore += 15;

    // Determina vencedor
    const winner = blueScore > redScore ? 'blue' : redScore > blueScore ? 'red' : 'tie';
    
    // Gera análise detalhada
    const advantages: string[] = [];
    const weaknesses: string[] = [];
    const recommendations: string[] = [];

    const winningAnalysis = winner === 'blue' ? blueAnalysis : winner === 'red' ? redAnalysis : blueAnalysis;
    const losingAnalysis = winner === 'blue' ? redAnalysis : winner === 'red' ? blueAnalysis : redAnalysis;

    // Vantagens
    if (winningAnalysis.synergyScore > losingAnalysis.synergyScore + 15) {
      advantages.push(`✨ Sinergia superior entre os campeões (${winningAnalysis.synergyScore} vs ${losingAnalysis.synergyScore} pontos)`);
    }
    
    if (winningAnalysis.hasProtectionSupport && winningAnalysis.hasADC) {
      advantages.push('🛡️ Suporte protegendo ADC - excelente sinergia bot lane');
    }
    
    if (winningAnalysis.hasEngageTool && !losingAnalysis.hasEngageTool) {
      advantages.push('⚔️ Tem ferramentas de engage - pode iniciar teamfights');
    }
    
    if (winningAnalysis.hasJungleGank && winningAnalysis.hasMidCarry) {
      advantages.push('🎯 Jungle com gank + Mid carry - pode snowballar');
    }

    advantages.push(`📋 Estratégia: ${winningAnalysis.strategy}`);

    // Fraquezas
    if (winningAnalysis.missingRoles.length > 0) {
      weaknesses.push(`⚠️ Faltam posições: ${winningAnalysis.missingRoles.join(', ')}`);
    }
    
    if (!winningAnalysis.hasTank) {
      weaknesses.push('⚠️ Sem tank - time muito frágil em teamfights');
    }
    
    if (!winningAnalysis.hasSupport) {
      weaknesses.push('⚠️ Sem suporte - falta utilitário e proteção');
    }
    
    if (winningAnalysis.damageBalance.physical > 70 && winningAnalysis.damageBalance.magic < 20) {
      weaknesses.push('⚠️ Muito dano físico - inimigo pode stackar armadura');
    }
    
    if (winningAnalysis.damageBalance.magic > 60 && winningAnalysis.damageBalance.physical < 20) {
      weaknesses.push('⚠️ Muito dano mágico - inimigo pode stackar resistência');
    }

    // Recomendações estratégicas
    if (Math.abs(blueScore - redScore) < 10) {
      recommendations.push('⚖️ Times equilibrados! Skill e macro decidirão o jogo');
    } else if (Math.abs(blueScore - redScore) < 25) {
      recommendations.push('📊 Pequena vantagem. Foque em objetivos e evite throws');
    } else {
      recommendations.push('💪 Grande vantagem de composição!');
    }

    if (winningAnalysis.hasEngageTool && losingAnalysis.hasTank) {
      recommendations.push('🎮 Use o engage para iniciar lutas quando o inimigo estiver separado');
    }

    if (winningAnalysis.hasProtectionSupport && winningAnalysis.hasADC) {
      recommendations.push('🏹 Jogue em volta do ADC - deixe ele dar o dano enquanto o suporte protege');
    }

    if (!winningAnalysis.hasTank) {
      recommendations.push('🏃 Evite teamfights prolongadas - foque em pokes e picks');
    }

    if (winningAnalysis.hasJungleGank) {
      recommendations.push('🌲 Jungle deve focar em ganks nas lanes para dar vantagem');
    }

    if (losingAnalysis.damageBalance.physical > 70) {
      recommendations.push('🛡️ Time perdedor está AD heavy - compre armadura');
    }

    if (losingAnalysis.damageBalance.magic > 60) {
      recommendations.push('🧙 Time perdedor está AP heavy - compre resistência mágica');
    }

    this.comparisonResult = {
      winner,
      blueScore: Math.round(blueScore),
      redScore: Math.round(redScore),
      advantages: advantages.length > 0 ? advantages : ['Composição padrão'],
      weaknesses: weaknesses.length > 0 ? weaknesses : ['Composição bem formada'],
      recommendations
    };
    
    console.log('📊 Análise Estratégica:', this.comparisonResult);
    console.log('🔵 Time Azul:', blueAnalysis);
    console.log('🔴 Time Vermelho:', redAnalysis);
  }
}
