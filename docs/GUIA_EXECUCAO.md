# 🚀 GUIA DE EXECUÇÃO COMPLETO

## League of Legends Team Builder - Spring Boot + Angular 17

## ✅ Pré-requisitos

1. **PostgreSQL instalado e rodando**
   - Banco: `league_of_legends`
   - Usuário: `postgres`
   - Senha: `menswear1`

2. **Java 21** instalado
3. **Maven** instalado

## 📦 Passos para Executar

### 1️⃣ Iniciar o Backend (API)

Abra um terminal PowerShell nesta pasta e execute:

```powershell
mvn spring-boot:run
```

**Aguarde até ver:**
```
Started TeamBuilderApplication in X seconds
```

O servidor estará rodando em: **http://localhost:8080**

### 2️⃣ Abrir o Frontend

**Opção 1:** Clique duas vezes no arquivo `index.html`

**Opção 2:** Abra o arquivo `teste-api.html` primeiro para testar a conexão

### 3️⃣ Verificar Funcionamento

1. Abra o navegador em: `teste-api.html`
2. Clique em "Testar Conexão"
3. Se aparecer ✅ verde, está funcionando!
4. Clique em "Carregar Campeões" para ver todos os 50 campeões

## 🎮 Usando a Aplicação Principal

Abra `index.html` e:

1. **Selecionar Campeões:**
   - Clique em um slot vazio (Top, Jungle, Mid, ADC, Support)
   - Escolha o campeão no modal
   - Veja as estatísticas sendo atualizadas em tempo real

2. **Filtrar Campeões:**
   - Use os botões: Tank, Lutador, Mago, Assassino, Atirador, Suporte
   - Ou use a busca por nome

3. **Salvar Composição:**
   - Monte seu time
   - Clique em "💾 Salvar Composição"
   - A composição será salva no PostgreSQL

4. **Composição Aleatória:**
   - Clique em "🎲 Composição Aleatória"
   - Um time completo será gerado automaticamente

## 🔧 Endpoints da API

- `GET /api/champions` - Lista todos os campeões
- `GET /api/champions/{id}` - Busca campeão por ID
- `GET /api/champions/class/{class}` - Busca por classe
- `GET /api/champions/lane/{lane}` - Busca por lane
- `POST /api/champions` - Cria novo campeão
- `PUT /api/champions/{id}` - Atualiza campeão
- `DELETE /api/champions/{id}` - Deleta campeão
- `POST /api/teams` - Salva composição de time
- `GET /api/teams` - Lista composições salvas

## ❌ Problemas Comuns

### Erro: "Erro ao carregar campeões"
- Verifique se o backend está rodando
- Abra http://localhost:8080/api/champions no navegador
- Deve retornar JSON com os campeões

### Erro: "Connection refused"
- PostgreSQL não está rodando
- Verifique o banco: `league_of_legends`
- Verifique usuário e senha em `application.properties`

### Porta 8080 em uso
- Altere a porta em `application.properties`:
```properties
server.port=8081
```
- E em `api.js`:
```javascript
const API_URL = 'http://localhost:8081/api';
```

## 📊 Estrutura dos Dados

Cada campeão tem:
- `name` - Nome do campeão
- `role` - Papel (Lutador, Tank, Mago, etc.)
- `championClass` - Classe específica
- `physicalDamage` - Dano físico (0-10)
- `magicDamage` - Dano mágico (0-10)
- `tankiness` - Resistência (0-10)
- `crowdControl` - Controle de grupo (0-10)
- `lanes` - Lanes recomendadas

## 🎯 Recursos Implementados

✅ CRUD completo de campeões
✅ Sistema de busca e filtros
✅ Análise de composição em tempo real
✅ Estatísticas visuais (barras animadas)
✅ Salvamento de composições
✅ Inicialização automática de 50 campeões
✅ Interface temática do LoL
✅ Integração PostgreSQL
✅ API REST completa

## 🐛 Debug

Para ver logs detalhados, adicione em `application.properties`:
```properties
logging.level.com.lol.teambuilder=DEBUG
```

---

**Desenvolvido com Spring Boot 3.2.2 + Java 21 + PostgreSQL + HTML/CSS/JavaScript**
