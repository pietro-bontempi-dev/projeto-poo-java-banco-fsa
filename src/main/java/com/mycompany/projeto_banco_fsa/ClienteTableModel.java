package com.mycompany.projeto_banco_fsa;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ClienteTableModel extends AbstractTableModel {

    private final List<Cliente> clientes;
    private final String[] colunas = {"ID", "Conta", "Nome", "Nascimento", "Profissão", "Tipo"};

    public ClienteTableModel(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = clientes.get(rowIndex);
        switch (columnIndex) {
            case 0: return cliente.getId();
            case 1: return cliente.getNumeroConta();
            case 2: return cliente.getNome();
            case 3: return cliente.getDataNascimento();
            case 4: return cliente.getProfissao();
            case 5: return cliente.getTipoConta();
            default: return null;
        }
    }
    
    // Método auxiliar para obter o objeto Cliente inteiro
    public Cliente getCliente(int rowIndex) {
        return clientes.get(rowIndex);
    }
}
