#  Sistema de Cadastro de Clientes - Banco FSA

Este é um projeto simples desenvolvido em Java para simular um sistema de cadastro e gerenciamento básico de clientes de um banco. A aplicação utiliza interfaces gráficas do Java Swing (`JOptionPane`) e persistência de dados através do JDBC (Java Database Connectivity) com o MySQL.

##  Funcionalidades Principais

O sistema implementa as quatro operações fundamentais do CRUD (Create, Read, Update, Delete):

1.  **C**reate (Cadastro): Insere novos clientes no banco de dados.
2.  **R**ead (Consulta): Lista todos os clientes em uma tabela e permite a busca.
3.  **U**pdate (Atualização): Permite editar os dados (nome, profissão, etc.) de um cliente existente.
4.  **D**elete (Deleção): Remove um cliente do cadastro.

##  Tecnologias Utilizadas

* **Linguagem:** Java (JDK 8+ recomendado)
* **Interface Gráfica:** Java Swing (`JOptionPane`, `JTable`, etc.)
* **Persistência:** MySQL
* **Conector:** MySQL Connector/J (JDBC Driver)
* **Gerenciador de Dependências:** Apache Maven (`pom.xml`)

## ⚙️ Pré-requisitos

Para rodar este projeto, você precisará ter instalado e configurado:

1.  **Java Development Kit (JDK):** Versão 8 ou superior.
2.  **MySQL Server:** Servidor de banco de dados rodando (Porta **3306**).
3.  **IDE (Opcional):** Apache NetBeans, Eclipse ou VS Code com suporte a Java/Maven.

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


2. Configuração de Conexão
Localize o arquivo DatabaseConnection.java e atualize as credenciais do banco de dados com suas informações:

Java

// DatabaseConnection.java
private static final String URL = "jdbc:mysql://127.0.0.1:3306/banco_fsa"; // Porta padrão
private static final String USER = "root"; // Seu usuário do MySQL
private static final String PASSWORD = "SUA_SENHA_AQUI"; // Sua senha de acesso
📦 Configuração do Projeto (Maven)
O projeto utiliza o Maven para gerenciar a dependência do driver JDBC.

1. Adicionar Dependência
Certifique-se de que a dependência do MySQL Connector/J esteja presente no seu arquivo pom.xml:

XML

<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version> </dependency>
</dependencies>
2. Compilar e Rodar
Após adicionar a dependência, execute o build do Maven para baixar o driver.

Bash

# Baixa dependências e compila
mvn clean install

# Executa o projeto (Se configurado no pom.xml com o plugin Exec)
mvn exec:java
Ou simplesmente execute o arquivo Main.java a partir da sua IDE.

🧭 Como Usar o Sistema
Ao iniciar a aplicação, a tela principal aparecerá:

1. Menu Principal (Tela de Boas Vindas)
Você terá duas opções principais de navegação:

Cadastrar Cliente: Abre o formulário para inserção de um novo cliente.

Consultar/Deletar: Abre uma tabela listando todos os clientes.

2. Cadastro de Cliente
Preencha todos os campos, atentando-se ao formato da Número da Conta (XXXXX-X) e Data de Nascimento (DD/MM/AAAA).

O sistema verifica se o número da conta já existe antes de inserir no banco.

3. Consulta e Gerenciamento
A tabela exibe todos os dados dos clientes.

Deletar Cliente Selecionado: Remove a linha selecionada do banco de dados após uma confirmação de segurança.

Editar Cliente Selecionado: Abre o formulário de cadastro, pré-preenchido com os dados do cliente selecionado, permitindo a Atualização das informações. O número da conta não pode ser alterado nesta etapa.
