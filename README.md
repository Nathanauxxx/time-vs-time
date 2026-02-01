# 🎮 League of Legends Team Builder

Uma aplicação full-stack profissional para análise e montagem de composições de equipes de League of Legends.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Execução](#execução)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [API Endpoints](#api-endpoints)
- [Funcionalidades](#funcionalidades)
- [Contribuindo](#contribuindo)

## 📖 Sobre o Projeto

Sistema inteligente que permite criar, gerenciar e analisar composições de equipes de League of Legends, fornecendo insights sobre balanceamento de dano, tankiness e controle de grupo (CC).

### Principais Características

- ✅ Gerenciamento completo de campeões (CRUD)
- ✅ Sistema de busca e filtros avançados
- ✅ Análise inteligente de composições
- ✅ Recomendações baseadas em sinergia
- ✅ Interface responsiva e moderna
- ✅ API RESTful documentada

## 🚀 Tecnologias

### Backend
- **Java 21** - Linguagem principal
- **Spring Boot 3.2.2** - Framework
- **PostgreSQL 18** - Banco de dados
- **JPA/Hibernate** - ORM
- **Maven** - Gerenciamento de dependências
- **Lombok** - Redução de boilerplate

### Frontend
- **Angular 17** - Framework
- **TypeScript 5.2** - Linguagem
- **Bootstrap 5.3** - UI Framework
- **RxJS 7.8** - Programação reativa
- **SCSS** - Estilização

## 📦 Pré-requisitos

- Java 21 ou superior
- Node.js 18+ e npm
- PostgreSQL 18+
- Maven 3.8+

## 🔧 Instalação

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/lol-team-builder.git
cd lol-team-builder
```

### 2. Configure o banco de dados
```bash
# Crie o banco de dados
createdb -U postgres league_of_legends

# Execute o script de migração (se necessário)
psql -U postgres -d league_of_legends -f scripts/fix-database.sql
```

### 3. Configure as variáveis de ambiente
Edite `src/main/resources/application.properties` e ajuste as credenciais do PostgreSQL.

### 4. Instale as dependências

**Backend:**
```bash
mvn clean install
```

**Frontend:**
```bash
cd frontend
npm install
```

## ▶️ Execução

### Modo Desenvolvimento

**Backend** (porta 8080):
```bash
mvn spring-boot:run
```

**Frontend** (porta 4200):
```bash
cd frontend
npm start
```

Acesse: `http://localhost:4200`

### Modo Produção

**Build do backend:**
```bash
mvn clean package -DskipTests
java -jar target/team-builder-1.0.0.jar
```

**Build do frontend:**
```bash
cd frontend
npm run build
```

## 📁 Estrutura do Projeto

```
lol-team-builder/
├── docs/                          # Documentação
│   └── GUIA_EXECUCAO.md          # Guia detalhado
├── scripts/                       # Scripts SQL e automação
│   └── fix-database.sql          # Migrations
├── src/
│   └── main/
│       ├── java/com/lol/teambuilder/
│       │   ├── config/           # Configurações
│       │   ├── controller/       # Controllers REST
│       │   ├── dto/              # Data Transfer Objects
│       │   ├── model/            # Entidades JPA
│       │   ├── repository/       # Repositórios
│       │   └── service/          # Lógica de negócio
│       └── resources/
│           └── application.properties
├── frontend/
│   └── src/
│       ├── app/
│       │   ├── components/       # Componentes Angular
│       │   ├── models/           # Interfaces TypeScript
│       │   └── services/         # Serviços HTTP
│       ├── assets/               # Assets estáticos
│       └── environments/         # Configurações de ambiente
├── pom.xml                        # Maven config
└── README.md                      # Este arquivo
```

## 🌐 API Endpoints

### Champions

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/champions` | Lista todos os campeões |
| GET | `/api/champions/{id}` | Busca campeão por ID |
| POST | `/api/champions` | Cria novo campeão |
| PUT | `/api/champions/{id}` | Atualiza campeão |
| DELETE | `/api/champions/{id}` | Remove campeão |

### Team Compositions

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/compositions` | Lista composições |
| GET | `/api/compositions/{id}` | Busca composição |
| POST | `/api/compositions` | Cria composição |
| DELETE | `/api/compositions/{id}` | Remove composição |

### Analysis & Recommendations

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/analysis/team` | Analisa composição |
| POST | `/api/recommendations/analyze` | Recomenda campeões |

## ✨ Funcionalidades

### Gerenciamento de Campeões
- Busca e filtro em tempo real por nome/classe
- Visualização completa de estatísticas
- CRUD completo

### Análise de Times
- Cálculo de dano físico/mágico total
- Avaliação de tankiness
- Medição de controle de grupo (CC)
- Score de balanceamento

### Sistema de Recomendações
- Análise de lacunas na composição
- Sugestões baseadas em sinergia
- Top 5 campeões recomendados por posição

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👤 Autor

**Seu Nome**
- GitHub: [@seu-usuario](https://github.com/seu-usuario)

## 🙏 Agradecimentos

- Riot Games pelo League of Legends
- Comunidade Spring Boot
- Comunidade Angular

---

⭐ Se este projeto foi útil, considere dar uma estrela!
