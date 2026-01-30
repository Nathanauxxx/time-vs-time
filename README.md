# 🎮 League of Legends - Team Builder

Aplicação completa com **Spring Boot** (backend) e **HTML/CSS/JavaScript** (frontend) para construir e analisar composições de times de League of Legends.

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.2.2**
- **Spring Data JPA**
- **H2 Database** (banco em memória)
- **Lombok**
- **Maven**

### Frontend
- **HTML5**
- **CSS3**
- **JavaScript (Vanilla)**

## 📋 Funcionalidades

### Backend (API REST)
- ✅ CRUD completo de campeões
- ✅ Busca e filtros de campeões (por classe, lane, nome)
- ✅ Sistema de análise de composição de times
- ✅ Salvamento e carregamento de composições
- ✅ Banco de dados H2 em memória
- ✅ Console H2 para visualização de dados

### Frontend
- ✅ Interface visual temática do LoL
- ✅ Seleção de campeões por lane (Top, Jungle, Mid, ADC, Support)
- ✅ Filtros por classe de campeão
- ✅ Sistema de busca
- ✅ Análise em tempo real de composição
- ✅ Estatísticas visuais dos times
- ✅ Composição aleatória
- ✅ Salvar/carregar composições

## 🏗️ Estrutura do Projeto

```
legue-of-legends/
├── src/
│   └── main/
│       ├── java/com/lol/teambuilder/
│       │   ├── TeamBuilderApplication.java
│       │   ├── config/
│       │   │   └── DataInitializer.java
│       │   ├── controller/
│       │   │   ├── ChampionController.java
│       │   │   ├── TeamAnalysisController.java
│       │   │   └── TeamCompositionController.java
│       │   ├── dto/
│       │   │   └── TeamAnalysisDTO.java
│       │   ├── model/
│       │   │   ├── Champion.java
│       │   │   └── TeamComposition.java
│       │   ├── repository/
│       │   │   ├── ChampionRepository.java
│       │   │   └── TeamCompositionRepository.java
│       │   └── service/
│       │       ├── ChampionService.java
│       │       ├── TeamAnalysisService.java
│       │       └── TeamCompositionService.java
│       └── resources/
│           └── application.properties
├── index.html
├── style.css
├── script.js
├── champions.js
├── pom.xml
└── README.md
```

## 🔧 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- Navegador web moderno

### Passo 1: Compilar e Executar o Backend

```bash
# Na raiz do projeto
mvn clean install
mvn spring-boot:run
```

O servidor iniciará em: `http://localhost:8080`

### Passo 2: Abrir o Frontend

Abra o arquivo `index.html` no seu navegador ou use um servidor local:

```bash
# Opção 1: Abrir diretamente
# Clique duas vezes em index.html

# Opção 2: Usar Python para servidor local
python -m http.server 3000
# Acesse: http://localhost:3000
```

## 📡 Endpoints da API

### Campeões

```http
GET    /api/champions              # Lista todos os campeões
GET    /api/champions/{id}         # Busca campeão por ID
GET    /api/champions/class/{class} # Busca por classe
GET    /api/champions/lane/{lane}  # Busca por lane
GET    /api/champions/search?name= # Busca por nome
POST   /api/champions              # Cria novo campeão
PUT    /api/champions/{id}         # Atualiza campeão
DELETE /api/champions/{id}         # Remove campeão
```

### Análise de Times

```http
POST   /api/analysis/team          # Analisa composição
Body: [championId1, championId2, ...]
```

### Composições

```http
GET    /api/compositions           # Lista composições salvas
GET    /api/compositions/{id}      # Busca composição por ID
GET    /api/compositions/search?name= # Busca por nome
POST   /api/compositions           # Salva nova composição
DELETE /api/compositions/{id}      # Remove composição
```

## 🗄️ Console H2

Para acessar o console do banco de dados H2:

1. Acesse: `http://localhost:8080/h2-console`
2. Use as configurações:
   - **JDBC URL**: `jdbc:h2:mem:loldb`
   - **Username**: `sa`
   - **Password**: (deixe em branco)

## 🎯 Próximas Melhorias

- [ ] Integrar frontend com backend (substituir dados locais por chamadas API)
- [ ] Adicionar autenticação de usuários
- [ ] Persistência em banco PostgreSQL/MySQL
- [ ] Sistema de ranking e estatísticas de winrate
- [ ] Integração com API oficial da Riot
- [ ] Sistema de bans
- [ ] Recomendação automática de picks
- [ ] Histórico de partidas

## 📝 Como Integrar Frontend com Backend

Modifique o `script.js` para fazer chamadas à API:

```javascript
// Exemplo: Carregar campeões da API
async function loadChampions() {
    const response = await fetch('http://localhost:8080/api/champions');
    const champions = await response.json();
    return champions;
}

// Exemplo: Analisar time
async function analyzeTeam(championIds) {
    const response = await fetch('http://localhost:8080/api/analysis/team', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(championIds)
    });
    return await response.json();
}
```

## 📄 Licença

Este projeto é para fins educacionais e de estudo.

## 👨‍💻 Autor

Desenvolvido para aprendizado de Spring Boot e desenvolvimento full-stack.
