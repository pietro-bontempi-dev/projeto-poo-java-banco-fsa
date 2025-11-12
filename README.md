# 🏦 Sistema de Cadastro de Clientes - Banco FSA

Este é um projeto simples desenvolvido em Java para simular um sistema de cadastro e gerenciamento básico de clientes de um banco. A aplicação utiliza interfaces gráficas do Java Swing (`JOptionPane`) e persistência de dados através do JDBC (Java Database Connectivity) com o MySQL.

## ✨ Funcionalidades Principais

O sistema implementa as quatro operações fundamentais do CRUD (Create, Read, Update, Delete):

1.  **C**reate (Cadastro): Insere novos clientes no banco de dados.
2.  **R**ead (Consulta): Lista todos os clientes em uma tabela e permite a busca.
3.  **U**pdate (Atualização): Permite editar os dados (nome, profissão, etc.) de um cliente existente.
4.  **D**elete (Deleção): Remove um cliente do cadastro.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (JDK 8+ recomendado)
* **Interface Gráfica:** Java Swing (`JOptionPane`, `JTable`, etc.)
* **Persistência:** MySQL
* **Conector:** MySQL Connector/J (JDBC Driver)
* **Gerenciador de Dependências:** Apache Maven (`pom.xml`)

## ⚙️ Pré-requisitos

Para rodar este projeto, você precisará ter instalado e configurado:

1.  **Java Development Kit (JDK):** Versão 8 ou superior.
2.  **MySQL Server:** Servidor de banco de dados rodando (geralmente na porta **3306**).
3.  **IDE (Opcional):** IntelliJ, Eclipse ou VS Code com suporte a Java/Maven.

## 💾 Configuração do Banco de Dados

### 1. Criação do Esquema

Execute o seguinte script SQL no seu gerenciador de banco de dados para criar o esquema (`banco_fsa`) e a tabela `clientes`.

```sql
/* Cria o banco de dados se ele não existir */
CREATE DATABASE IF NOT EXISTS banco_fsa;

/* Seleciona o banco para usar */
USE banco_fsa;

/* Cria a tabela de clientes com ID e UNIQUE na conta */
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_conta VARCHAR(7) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE,
    profissao VARCHAR(100),
    tipo_conta ENUM('conta-corrente', 'conta-poupança') NOT NULL
);
