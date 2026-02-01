# 📡 Documentação da API

## Base URL
```
http://localhost:8080/api
```

## Autenticação
Atualmente a API não requer autenticação. Em produção, implementar JWT ou OAuth2.

---

## 🎮 Champions

### Listar todos os campeões
```http
GET /api/champions
```

**Resposta de Sucesso (200)**
```json
[
  {
    "id": 1,
    "name": "Ahri",
    "championClass": "mage",
    "role": "mid",
    "icon": "ahri.png",
    "physicalDamage": 3,
    "magicDamage": 9,
    "tankiness": 4,
    "crowdControl": 7,
    "lanes": "mid"
  }
]
```

### Buscar campeão por ID
```http
GET /api/champions/{id}
```

**Parâmetros**
- `id` (path, required) - ID do campeão

**Resposta de Sucesso (200)**
```json
{
  "id": 1,
  "name": "Ahri",
  ...
}
```

**Erro (404)**
```json
{
  "timestamp": "2026-01-31T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Campeão não encontrado com ID: 999"
}
```

### Criar novo campeão
```http
POST /api/champions
```

**Body**
```json
{
  "name": "Yasuo",
  "championClass": "fighter",
  "role": "mid",
  "physicalDamage": 9,
  "magicDamage": 2,
  "tankiness": 5,
  "crowdControl": 6,
  "lanes": "mid,top"
}
```

**Resposta de Sucesso (201)**
```json
{
  "id": 51,
  "name": "Yasuo",
  ...
}
```

### Atualizar campeão
```http
PUT /api/champions/{id}
```

**Parâmetros**
- `id` (path, required) - ID do campeão

**Body** - Mesmo formato do POST

**Resposta de Sucesso (200)**

### Deletar campeão
```http
DELETE /api/champions/{id}
```

**Resposta de Sucesso (204)** - No Content

---

## 🏆 Team Compositions

### Listar composições
```http
GET /api/compositions
```

**Resposta de Sucesso (200)**
```json
[
  {
    "id": 1,
    "name": "Poke Comp",
    "championIds": [1, 5, 12, 23, 45]
  }
]
```

### Criar composição
```http
POST /api/compositions
```

**Body**
```json
{
  "name": "Team Fight Comp",
  "championIds": [10, 15, 20, 25, 30]
}
```

**Validações**
- `championIds` deve ter exatamente 5 elementos
- Não pode haver IDs duplicados
- Todos os IDs devem existir no banco

---

## 📊 Team Analysis

### Analisar composição
```http
POST /api/analysis/team
```

**Body**
```json
{
  "championIds": [1, 5, 10, 15, 20]
}
```

**Resposta de Sucesso (200)**
```json
{
  "totalPhysicalDamage": 35,
  "totalMagicDamage": 25,
  "totalTankiness": 30,
  "totalCrowdControl": 28,
  "balanceScore": 8.5,
  "strengths": ["High physical damage", "Good tankiness"],
  "weaknesses": ["Low magic damage"],
  "suggestions": ["Add a mage for better magic damage"]
}
```

---

## 💡 Recommendations

### Obter recomendações
```http
POST /api/recommendations/analyze
```

**Body**
```json
{
  "championIds": [1, 5, 10, 15],
  "lane": "adc"
}
```

**Parâmetros**
- `championIds` - Array com 0-4 campeões atuais
- `lane` - Lane para recomendar (top, jungle, mid, adc, support, all)

**Resposta de Sucesso (200)**
```json
{
  "currentTeamAnalysis": {
    "totalPhysicalDamage": 28,
    ...
  },
  "recommendations": [
    {
      "champion": {
        "id": 30,
        "name": "Jinx",
        ...
      },
      "synergyScore": 9.2,
      "reasoning": "Provides high physical damage and scales well"
    }
  ],
  "topRecommendations": [...]
}
```

---

## 🚨 Códigos de Status HTTP

| Código | Significado |
|--------|-------------|
| 200 | OK - Sucesso |
| 201 | Created - Recurso criado |
| 204 | No Content - Sucesso sem corpo de resposta |
| 400 | Bad Request - Dados inválidos |
| 404 | Not Found - Recurso não encontrado |
| 500 | Internal Server Error - Erro no servidor |

---

## 📝 Notas

### CORS
A API permite requisições de `http://localhost:4200` em desenvolvimento. 
Configurar adequadamente para produção.

### Validações
- Valores de stats devem estar entre 0-10
- Nomes devem ter no máximo 100 caracteres
- Classes válidas: fighter, tank, mage, assassin, marksman, support

### Rate Limiting
Não implementado. Considerar adicionar em produção.

### Versionamento
Considerar adicionar versionamento da API (v1, v2) para futuras mudanças.
