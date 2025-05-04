# Sistema de Cadastro de Funcionários e Usuários - Java Swing + PostgreSQL

Este projeto foi desenvolvido como parte de um **teste prático para vaga de programador**, utilizando Java com Swing para a interface gráfica e PostgreSQL para persistência de dados. O sistema permite o cadastro, login de usuários e a gestão de funcionários com funcionalidades de busca, filtro por status e exportação para CSV e PDF.

## 🛠 Tecnologias Utilizadas

- **Java 8+**
- **Java Swing** (interface gráfica)
- **JDBC** (acesso ao banco de dados)
- **PostgreSQL**
- **iText** (geração de PDF)
- **NetBeans IDE**
- **Criptografia SHA-256** (para senhas)
- **Estilização personalizada de componentes Swing** (RoundedTextField, RoundedPasswordField etc.)

## 📋 Funcionalidades

### Tela de Login e Cadastro de Usuários

- Cadastro de usuário com nome, e-mail e senha (criptografada com SHA-256)
- Validação de campos obrigatórios e e-mail válido
- Login com verificação de credenciais
- Redirecionamento para a tela principal ao logar

### Tela Principal

- Exibe mensagem personalizada de boas-vindas
- Acesso à tela de cadastro de funcionários

### Cadastro de Funcionários

- Cadastro com nome, data de admissão, salário e status (ativo/inativo)
- Edição, exclusão e listagem dos funcionários
- Filtro por status: Todos, Ativos, Inativos
- Busca por nome
- Exportação dos dados visíveis da tabela para:
  - **PDF**
  - **CSV**

### Componentes personalizados

- Campos com cantos arredondados e placeholder:
  - `RoundedTextField`, `RoundedPasswordField`, `RoundedDateField`
- Máscara automática de data `dd/MM/yyyy`
- Estilização visual moderna com `GradientPaint`

## 🧪 Testes

Foram incluídas duas classes de teste:

- `FuncionarioDAOTest`: testes de inserção, atualização, listagem e remoção de funcionários
- `UsuarioDAOTest`: testes de inserção, login, listagem e remoção de usuários

## 🗃 Banco de Dados

O banco de dados deve ser PostgreSQL, com o nome `teste_pratico`. Exemplo de criação de tabelas:

```sql
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(256) NOT NULL
);

CREATE TABLE funcionarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_admissao DATE NOT NULL,
    salario DOUBLE PRECISION NOT NULL,
    status BOOLEAN NOT NULL
);
```

## 🚀 Como Executar

1. Clone o projeto no NetBeans
2. Crie o banco de dados `teste_pratico` no PostgreSQL
3. Ajuste os dados de conexão em `Conexao.java` se necessário
4. Rode a classe principal `TestePraticoSAAM.java`

---

**Desenvolvido como solução completa para o desafio prático da vaga de programador.**
