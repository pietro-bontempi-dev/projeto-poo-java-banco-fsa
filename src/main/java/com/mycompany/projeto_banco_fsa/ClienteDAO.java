package com.mycompany.projeto_banco_fsa;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // 1. CREATE (Criar novo cliente)
    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (numero_conta, nome, data_nascimento, profissao, tipo_conta) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNumeroConta());
            pstmt.setString(2, cliente.getNome());
            pstmt.setDate(3, cliente.getDataNascimento());
            pstmt.setString(4, cliente.getProfissao());
            pstmt.setString(5, cliente.getTipoConta());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir cliente: " + e.getMessage(), e);
        }
    }

    // 2. READ (Buscar todos os clientes)
    public List<Cliente> buscarTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("numero_conta"),
                    rs.getString("nome"),
                    rs.getDate("data_nascimento"),
                    rs.getString("profissao"),
                    rs.getString("tipo_conta")
                );
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    // 3. READ (Buscar por número da conta)
    public Cliente buscarPorConta(String numeroConta) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE numero_conta = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, numeroConta);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                        rs.getInt("id"),
                        rs.getString("numero_conta"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento"),
                        rs.getString("profissao"),
                        rs.getString("tipo_conta")
                    );
                }
            }
        }
        return null; // Retorna null se não encontrar
    }

    // 4. UPDATE (Atualizar dados de um cliente)
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, data_nascimento = ?, profissao = ?, tipo_conta = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setDate(2, cliente.getDataNascimento());
            pstmt.setString(3, cliente.getProfissao());
            pstmt.setString(4, cliente.getTipoConta());
            pstmt.setInt(5, cliente.getId()); // Atualiza usando o ID

            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }

    // 5. DELETE (Deletar cliente por ID ou Conta)
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao deletar cliente: " + e.getMessage(), e);
        }
    }
}