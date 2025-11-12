package com.mycompany.projeto_banco_fsa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // A URL aponta para o host/porta da imagem e o banco de dados que criamos (banco_fsa)
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/banco_fsa";
    
    // O usuário é 'root', como na imagem
    private static final String USER = "root"; 
    
    // 👇 Aqui você deve colocar a SENHA que você usa para acessar o MySQL
    private static final String PASSWORD = "25644669Pe#!"; 

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }
}