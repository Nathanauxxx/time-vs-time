// Estado da aplicação
let currentTeams = {
    blue: {},
    red: {}
};

let currentPosition = null;
let currentFilter = 'all';

// Elementos DOM
const modal = document.getElementById('champion-modal');
const closeBtn = document.querySelector('.close');
const championGrid = document.getElementById('champion-grid');
const championSearch = document.getElementById('champion-search');
const filterButtons = document.querySelectorAll('.filter-btn');
const championSlots = document.querySelectorAll('.champion-slot');

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    initializeEventListeners();
    renderChampionGrid();
});

function initializeEventListeners() {
    // Slots de campeões
    championSlots.forEach(slot => {
        slot.addEventListener('click', (e) => {
            if (e.target.classList.contains('remove-btn')) {
                removeChampion(slot.dataset.position);
            } else {
                currentPosition = slot.dataset.position;
                openModal();
            }
        });
    });

    // Modal
    closeBtn.addEventListener('click', closeModal);
    window.addEventListener('click', (e) => {
        if (e.target === modal) closeModal();
    });

    // Busca de campeões
    championSearch.addEventListener('input', (e) => {
        renderChampionGrid(e.target.value);
    });

    // Filtros
    filterButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            filterButtons.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            currentFilter = btn.dataset.filter;
            renderChampionGrid(championSearch.value);
        });
    });

    // Botões de controle
    document.getElementById('clear-btn').addEventListener('click', clearAll);
    document.getElementById('random-btn').addEventListener('click', randomComposition);
    document.getElementById('save-btn').addEventListener('click', saveComposition);
}

function openModal() {
    modal.classList.add('active');
    championSearch.value = '';
    championSearch.focus();
}

function closeModal() {
    modal.classList.remove('active');
}

function renderChampionGrid(searchTerm = '') {
    championGrid.innerHTML = '';
    
    let filteredChampions = champions;
    
    // Filtrar por classe
    if (currentFilter !== 'all') {
        filteredChampions = filteredChampions.filter(c => c.class === currentFilter);
    }
    
    // Filtrar por busca
    if (searchTerm) {
        filteredChampions = filteredChampions.filter(c => 
            c.name.toLowerCase().includes(searchTerm.toLowerCase())
        );
    }
    
    filteredChampions.forEach(champion => {
        const card = document.createElement('div');
        card.className = 'champion-card';
        card.innerHTML = `
            <div class="champion-card-icon">${champion.icon}</div>
            <div class="champion-card-name">${champion.name}</div>
            <div class="champion-card-class">${champion.role}</div>
        `;
        card.addEventListener('click', () => selectChampion(champion));
        championGrid.appendChild(card);
    });
}

function selectChampion(champion) {
    if (!currentPosition) return;
    
    const [lane, team] = currentPosition.split('-');
    
    // Verificar se o campeão já está no time
    if (Object.values(currentTeams[team]).some(c => c.name === champion.name)) {
        alert(`${champion.name} já está neste time!`);
        return;
    }
    
    currentTeams[team][lane] = champion;
    updateChampionSlot(currentPosition, champion);
    updateTeamStats(team);
    updateAnalysis();
    closeModal();
}

function updateChampionSlot(position, champion) {
    const slot = document.querySelector(`[data-position="${position}"]`);
    slot.classList.add('filled');
    slot.innerHTML = `
        <div class="champion-info">
            <div class="champion-icon">${champion.icon}</div>
            <div class="champion-details">
                <div class="champion-name">${champion.name}</div>
                <div class="champion-role">${champion.role}</div>
            </div>
        </div>
        <button class="remove-btn">×</button>
    `;
}

function removeChampion(position) {
    const [lane, team] = position.split('-');
    delete currentTeams[team][lane];
    
    const slot = document.querySelector(`[data-position="${position}"]`);
    slot.classList.remove('filled');
    slot.innerHTML = '<span class="placeholder">Selecione</span>';
    
    updateTeamStats(team);
    updateAnalysis();
}

function updateTeamStats(team) {
    const teamChampions = Object.values(currentTeams[team]);
    if (teamChampions.length === 0) {
        resetStats(team);
        return;
    }
    
    const stats = {
        physical: 0,
        magic: 0,
        tank: 0,
        cc: 0
    };
    
    teamChampions.forEach(champion => {
        stats.physical += champion.damage.physical;
        stats.magic += champion.damage.magic;
        stats.tank += champion.tank;
        stats.cc += champion.cc;
    });
    
    // Normalizar para porcentagem
    const maxPhysical = teamChampions.length * 10;
    const maxMagic = teamChampions.length * 10;
    const maxTank = teamChampions.length * 10;
    const maxCC = teamChampions.length * 10;
    
    const statsContainer = document.getElementById(`${team}-stats`);
    statsContainer.querySelector('[data-stat="physical"]').style.width = 
        `${(stats.physical / maxPhysical) * 100}%`;
    statsContainer.querySelector('[data-stat="magic"]').style.width = 
        `${(stats.magic / maxMagic) * 100}%`;
    statsContainer.querySelector('[data-stat="tank"]').style.width = 
        `${(stats.tank / maxTank) * 100}%`;
    statsContainer.querySelector('[data-stat="cc"]').style.width = 
        `${(stats.cc / maxCC) * 100}%`;
}

function resetStats(team) {
    const statsContainer = document.getElementById(`${team}-stats`);
    statsContainer.querySelectorAll('.stat-fill').forEach(fill => {
        fill.style.width = '0%';
    });
}

function updateAnalysis() {
    const analysisContent = document.getElementById('analysis-content');
    const blueTeam = Object.values(currentTeams.blue);
    const redTeam = Object.values(currentTeams.red);
    
    if (blueTeam.length === 0 && redTeam.length === 0) {
        analysisContent.innerHTML = '<p>Selecione campeões para ver a análise da composição</p>';
        return;
    }
    
    let analysis = '';
    
    // Analisar time azul
    if (blueTeam.length > 0) {
        analysis += '<h4 style="color: #0bc6e3; margin-bottom: 10px;">🔵 Time Azul</h4>';
        analysis += analyzeTeam(blueTeam);
    }
    
    // Analisar time vermelho
    if (redTeam.length > 0) {
        analysis += '<h4 style="color: #e3420b; margin-top: 20px; margin-bottom: 10px;">🔴 Time Vermelho</h4>';
        analysis += analyzeTeam(redTeam);
    }
    
    analysisContent.innerHTML = analysis;
}

function analyzeTeam(team) {
    let analysis = '';
    
    const stats = {
        physical: team.reduce((sum, c) => sum + c.damage.physical, 0),
        magic: team.reduce((sum, c) => sum + c.damage.magic, 0),
        tank: team.reduce((sum, c) => sum + c.tank, 0),
        cc: team.reduce((sum, c) => sum + c.cc, 0)
    };
    
    // Balanceamento de dano
    const totalDamage = stats.physical + stats.magic;
    const physicalPercent = (stats.physical / totalDamage) * 100;
    const magicPercent = (stats.magic / totalDamage) * 100;
    
    if (physicalPercent > 75) {
        analysis += '<div class="analysis-item analysis-warning">⚠️ Composição muito focada em dano físico - inimigos podem itemizar armadura</div>';
    } else if (magicPercent > 75) {
        analysis += '<div class="analysis-item analysis-warning">⚠️ Composição muito focada em dano mágico - inimigos podem itemizar resistência mágica</div>';
    } else {
        analysis += '<div class="analysis-item analysis-positive">✅ Bom balanceamento entre dano físico e mágico</div>';
    }
    
    // Análise de frontline
    const avgTank = stats.tank / team.length;
    if (avgTank < 4) {
        analysis += '<div class="analysis-item analysis-negative">❌ Time muito frágil - falta frontline</div>';
    } else if (avgTank > 7) {
        analysis += '<div class="analysis-item analysis-positive">✅ Boa frontline para proteger aliados</div>';
    } else {
        analysis += '<div class="analysis-item analysis-positive">✅ Frontline adequada</div>';
    }
    
    // Análise de CC
    const avgCC = stats.cc / team.length;
    if (avgCC < 4) {
        analysis += '<div class="analysis-item analysis-warning">⚠️ Pouco controle de grupo - dificuldade em iniciar teamfights</div>';
    } else if (avgCC > 7) {
        analysis += '<div class="analysis-item analysis-positive">✅ Excelente controle de grupo para iniciar e controlar lutas</div>';
    } else {
        analysis += '<div class="analysis-item analysis-positive">✅ Controle de grupo suficiente</div>';
    }
    
    // Análise de composição
    const classes = team.map(c => c.class);
    const hasAssassin = classes.includes('assassin');
    const hasTank = classes.includes('tank');
    const hasMarksman = classes.includes('marksman');
    
    if (hasAssassin && !hasTank) {
        analysis += '<div class="analysis-item analysis-warning">⚠️ Time com assassinos mas sem tanques - precisam jogar pick-off</div>';
    }
    
    if (!hasMarksman && team.length >= 4) {
        analysis += '<div class="analysis-item analysis-warning">⚠️ Sem atirador - pode ter dificuldade em derrubar tanques no late game</div>';
    }
    
    return analysis;
}

function clearAll() {
    if (confirm('Deseja limpar todas as seleções?')) {
        currentTeams = { blue: {}, red: {} };
        championSlots.forEach(slot => {
            slot.classList.remove('filled');
            slot.innerHTML = '<span class="placeholder">Selecione</span>';
        });
        resetStats('blue');
        resetStats('red');
        updateAnalysis();
    }
}

function randomComposition() {
    clearAll();
    
    const lanes = ['top', 'jungle', 'mid', 'adc', 'support'];
    
    ['blue', 'red'].forEach(team => {
        lanes.forEach(lane => {
            const availableChampions = champions.filter(c => 
                c.lanes.includes(lane) && 
                !Object.values(currentTeams[team]).some(selected => selected.name === c.name)
            );
            
            if (availableChampions.length > 0) {
                const randomChampion = availableChampions[Math.floor(Math.random() * availableChampions.length)];
                currentTeams[team][lane] = randomChampion;
                updateChampionSlot(`${lane}-${team}`, randomChampion);
            }
        });
        updateTeamStats(team);
    });
    
    updateAnalysis();
}

function saveComposition() {
    const composition = {
        blue: currentTeams.blue,
        red: currentTeams.red,
        timestamp: new Date().toISOString()
    };
    
    const json = JSON.stringify(composition, null, 2);
    const blob = new Blob([json], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    
    const a = document.createElement('a');
    a.href = url;
    a.download = `composicao-${Date.now()}.json`;
    a.click();
    
    URL.revokeObjectURL(url);
    alert('Composição salva com sucesso!');
}
