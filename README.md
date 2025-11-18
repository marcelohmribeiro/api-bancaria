# 🏦 API Bancária

API REST para gerenciamento de operações bancárias desenvolvida com Spring Boot.

## 📦 Instalação

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/api-bancaria.git

# Entre no diretório
cd api-java

# Execute o projeto
./mvnw spring-boot:run
```

## 🔧 Configuração

Configure o banco de dados em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/seubanco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

---

## 📚 Endpoints da API

> **Nota:** Todas as rotas protegidas requerem o token JWT no header:
>
> ```http
> Authorization: Bearer seu_token_jwt_aqui
> ```

### 🔐 Autenticação

#### Registrar Usuário

```http
POST /users/register
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "senha123"
}
```

**Resposta:**

```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com"
}
```

#### Login

```http
POST /login
Content-Type: application/json

{
  "email": "joao@email.com",
  "password": "senha123"
}
```

**Resposta:**

```json
{
  "user": {
    "id": 1,
    "name": "João Silva",
    "email": "joao@email.com"
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### 👤 Usuários

#### Listar Todos os Usuários

```http
GET /users/
Authorization: Bearer {token}
```

#### Buscar Usuário por ID

```http
GET /users/{id}
Authorization: Bearer {token}
```

**Resposta:**

```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com"
}
```

#### Deletar Usuário

```http
DELETE /users/{id}
Authorization: Bearer {token}
```

**Resposta:**

```json
"Usuário deletado com sucesso!"
```

---

### 💸 Transações e Transferências

#### Realizar Transferência

```http
POST /transactions
Authorization: Bearer {token}
Content-Type: application/json

{
  "fromAccountNumber": "123456",
  "toAccountNumber": "654321",
  "amount": 100.00,
  "description": "Pagamento"
}
```

**Resposta:**

```json
{
  "id": 1,
  "type": "TRANSFER",
  "amount": 100.0,
  "description": "Pagamento",
  "createdAt": "2025-11-18T10:30:00"
}
```

---

### 📊 Extrato

#### Visualizar Extrato da Conta

```http
GET /statement/{accountNumber}
Authorization: Bearer {token}
```

**Resposta:**

```json
[
  {
    "id": 1,
    "side": "CREDIT",
    "amount": 100.0,
    "description": "Depósito",
    "createdAt": "2025-11-17T10:30:00"
  },
  {
    "id": 2,
    "side": "DEBIT",
    "amount": 50.0,
    "description": "Transferência enviada",
    "createdAt": "2025-11-17T14:15:00"
  }
]
```

---

### 💳 Cartões

#### Criar Cartão

```http
POST /cards
Authorization: Bearer {token}
Content-Type: application/json

{
  "accountNumber": "123456",
  "cardType": "DEBIT"
}
```

**Resposta:**

```json
{
  "id": 1,
  "cardNumber": "1234567890123456",
  "cardType": "DEBIT",
  "status": "ACTIVE",
  "expirationDate": "2028-11-18",
  "accountNumber": "123456"
}
```

#### Listar Cartões do Usuário

```http
GET /cards/user/{userId}
Authorization: Bearer {token}
```

**Resposta:**

```json
[
  {
    "id": 1,
    "cardNumber": "1234567890123456",
    "cardType": "DEBIT",
    "status": "ACTIVE",
    "expirationDate": "2028-11-18",
    "accountNumber": "123456"
  }
]
```

---

### 🔑 Chaves PIX

#### Criar Chave PIX

```http
POST /pix-keys/user/{userId}
Authorization: Bearer {token}
Content-Type: application/json

{
  "type": "EMAIL",
  "valueKey": "joao@email.com"
}
```

**Resposta:**

```json
{
  "id": 1,
  "type": "EMAIL",
  "valueKey": "joao@email.com",
  "accountNumber": "123456",
  "userId": 1
}
```

#### Listar Chaves PIX do Usuário

```http
GET /pix-keys/user/{userId}
Authorization: Bearer {token}
```

**Resposta:**

```json
[
  {
    "id": 1,
    "type": "EMAIL",
    "valueKey": "joao@email.com",
    "accountNumber": "123456",
    "userId": 1
  }
]
```

#### Deletar Chave PIX

```http
DELETE /pix-keys/{keyId}
Authorization: Bearer {token}
```

---


### 💰 Investimentos

#### Criar Investimento

```http
POST /investments/user/{userId}
Authorization: Bearer {token}
Content-Type: application/json

{
  "amount": 1000.00,
  "monthlyRate": 0.5,
  "type": "CDB"
}
```

**Resposta:**

```json
{
  "id": 1,
  "type": "CDB",
  "amount": 1000.0,
  "currentAmount": 1000.0,
  "monthlyRate": 0.5,
  "status": "ACTIVE",
  "accountNumber": "123456",
  "userId": 1,
  "createdAt": "2025-11-18T10:30:00",
  "lastYieldDate": "2025-11-18T10:30:00"
}
```

#### Listar Investimentos do Usuário

```http
GET /investments/user/{userId}
Authorization: Bearer {token}
```

**Resposta:**

```json
[
  {
    "id": 1,
    "type": "CDB",
    "amount": 1000.0,
    "currentAmount": 1005.0,
    "monthlyRate": 0.5,
    "status": "ACTIVE",
    "accountNumber": "123456",
    "userId": 1,
    "createdAt": "2025-11-18T10:30:00",
    "lastYieldDate": "2025-11-18T10:30:00"
  }
]
```

#### Resgatar Investimento

```http
POST /investments/{investmentId}/redeem
Authorization: Bearer {token}
```

**Resposta:**

```json
{
  "id": 1,
  "type": "CDB",
  "amount": 1000.0,
  "currentAmount": 1005.0,
  "monthlyRate": 0.5,
  "status": "REDEEMED",
  "accountNumber": "123456",
  "userId": 1,
  "createdAt": "2025-11-18T10:30:00",
  "lastYieldDate": "2025-11-18T10:30:00"
}
```

---

### 📍 Endereços

#### Adicionar Endereço

```http
POST /address/user/{userId}
Authorization: Bearer {token}
Content-Type: application/json

{
  "rua": "Rua das Flores",
  "numero": "123",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01234-567"
}
```

**Resposta:**

```json
{
  "id": 1,
  "rua": "Rua das Flores",
  "numero": "123",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01234-567",
  "userId": 1
}
```

#### Listar Endereços do Usuário

```http
GET /address/user/{userId}
Authorization: Bearer {token}
```

**Resposta:**

```json
[
  {
    "id": 1,
    "rua": "Rua das Flores",
    "numero": "123",
    "bairro": "Centro",
    "cidade": "São Paulo",
    "estado": "SP",
    "cep": "01234-567",
    "userId": 1
  }
]
```

---

### 📱 Dispositivos

#### Listar Dispositivos do Usuário

```http
GET /devices/user/{userId}
Authorization: Bearer {token}
```

**Resposta:**

```json
[
  {
    "id": 1,
    "deviceName": "Mozilla/5.0...",
    "ip": "192.168.1.1",
    "userAgent": "Mozilla/5.0...",
    "lastAccess": "2025-11-18T10:30:00"
  }
]
```

---

### 🎫 Tickets de Suporte

#### Criar Ticket

```http
POST /tickets
Authorization: Bearer {token}
Content-Type: application/json

{
  "userId": 1,
  "description": "Problema com transferência",
  "category": "TECHNICAL"
}
```

**Resposta:**

```json
{
  "id": 1,
  "userId": 1,
  "description": "Problema com transferência",
  "category": "TECHNICAL",
  "status": "OPEN",
  "createdAt": "2025-11-18T10:30:00"
}
```

#### Atualizar Status do Ticket

```http
PATCH /tickets/{id}/status
Authorization: Bearer {token}
Content-Type: application/json

{
  "status": "RESOLVED"
}
```

**Resposta:**

```json
{
  "id": 1,
  "userId": 1,
  "description": "Problema com transferência",
  "category": "TECHNICAL",
  "status": "RESOLVED",
  "createdAt": "2025-11-18T10:30:00"
}
```

---

## 🗃️ Modelo de Dados

### Diagrama Entidade-Relacionamento (DER)

<image src="./images/der.png" />

### Modelo Entidade-Relacionamento (MER)

<image src="./images/mer.png"/>