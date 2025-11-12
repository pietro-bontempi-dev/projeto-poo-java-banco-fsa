package com.mycompany.projeto_banco_fsa;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.SQLException;

public class Projeto_banco_fsa {

    private static final ClienteDAO dao = new ClienteDAO();

    public static void main(String[] args) {
        // Loop principal: Fica rodando até o usuário fechar
        while (true) {
            int action = mostrarTelaBoasVindas();

            if (action == 0) {
                // Opção 1: Cadastrar Cliente
                mostrarTelaCadastro(null);
            } else if (action == 1) {
                // Opção 2: Gerenciar Clientes (Consulta/Deleção)
                mostrarTelaConsulta();
            } else {
                // Opção 3: Fechar (ou fechar a janela)
                break;
            }
        }
        System.out.println("Sistema encerrado.");
    }

    /**
     * Mostra a Tela 1: Boas Vindas.
     * @return 0 para Cadastrar, 1 para Consultar, -1 ou 2 para Fechar.
     */
    private static int mostrarTelaBoasVindas() {
        String[] options = {"Cadastrar Cliente", "Consultar/Deletar", "Fechar o painel"};
        
        int result = JOptionPane.showOptionDialog(
                null, 
                "Bem vindo ao Sistema de Cadastro do Banco FSA", 
                "Banco FSA - Menu Principal", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null, 
                options, 
                options[0] 
        );

        return result;
    }

    /**
     * Mostra a Tela 2: Formulário de Cadastro (para Criação ou Edição).
     * @param clienteToEdit Objeto Cliente para pré-preencher e atualizar (null para novo).
     */
    private static void mostrarTelaCadastro(Cliente clienteToEdit) {
        // --- Criar os componentes do formulário ---
        // O campo conta só é editável se for um novo cadastro
        boolean isNew = (clienteToEdit == null); 
        
        JTextField nomeField = new JTextField(isNew ? "" : clienteToEdit.getNome(), 20);
        JTextField profissaoField = new JTextField(isNew ? "" : clienteToEdit.getProfissao(), 20);

        JFormattedTextField contaField = criarCampoConta(isNew ? null : clienteToEdit.getNumeroConta());
        contaField.setEditable(isNew); // Bloqueia a edição da conta na atualização
        
        JFormattedTextField dataField = criarCampoData(isNew ? null : clienteToEdit.getDataNascimento());

        String[] tiposConta = {"Conta-Corrente", "Conta-Poupança", "Conta-Salário"};
        JComboBox<String> tipoContaBox = new JComboBox<>(tiposConta);
        if (!isNew) {
             tipoContaBox.setSelectedItem(clienteToEdit.getTipoConta());
        }

        // --- Criar o Painel ---
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Número da Conta (XXXXX-X):"));
        panel.add(contaField);
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Data de Nascimento (DD/MM/AAAA):"));
        panel.add(dataField);
        panel.add(new JLabel("Profissão:"));
        panel.add(profissaoField);
        panel.add(new JLabel("Tipo de Conta:"));
        panel.add(tipoContaBox);

        String title = isNew ? "Cadastro de Novo Cliente" : "Editar Cliente ID: " + clienteToEdit.getId();
        String[] options = {isNew ? "Cadastrar" : "Atualizar", "Cancelar"};
        
        int result = JOptionPane.showOptionDialog(null, panel, title, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        // --- Processar os dados ---
        if (result == JOptionPane.YES_OPTION) {
            try {
                // 1. Coleta e Validação
                String numeroConta = contaField.getText().trim();
                String nome = nomeField.getText().trim();
                String dataNascStr = dataField.getText().trim();
                String profissao = profissaoField.getText().trim();
                String tipoConta = (String) tipoContaBox.getSelectedItem();

                if (nome.isEmpty() || numeroConta.contains("_") || dataNascStr.contains("_")) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 2. Conversão de Data
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date utilDate = formatter.parse(dataNascStr);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                // 3. Execução do DAO
                if (isNew) {
                    Cliente novoCliente = new Cliente(numeroConta, nome, sqlDate, profissao, tipoConta);
                    dao.inserir(novoCliente);
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Preenche o objeto existente com os novos dados
                    clienteToEdit.setNome(nome);
                    clienteToEdit.setDataNascimento(sqlDate);
                    clienteToEdit.setProfissao(profissao);
                    clienteToEdit.setTipoConta(tipoConta);
                    dao.atualizar(clienteToEdit);
                    JOptionPane.showMessageDialog(null, "Cliente ID " + clienteToEdit.getId() + " atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (SQLException e) {
                // Trata erro de conta duplicada (CONSTRAINT UNIQUE) ou outros erros de banco
                String msg = e.getMessage().contains("Duplicate entry") ? "Erro: Número da conta já existe no banco de dados." : "Erro de Banco de Dados:\n" + e.getMessage();
                JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro:\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Mostra a Tela 3: Consulta e Gerenciamento (Read e Delete).
     */
    private static void mostrarTelaConsulta() {
        try {
            java.util.List<Cliente> listaClientes = dao.buscarTodos();
            if (listaClientes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum cliente cadastrado ainda.", "Consulta", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Criar JTable
            ClienteTableModel tableModel = new ClienteTableModel(listaClientes);
            JTable table = new JTable(tableModel);
            
            // Colocar tabela em um ScrollPane para rolar
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(650, 300));

            // Painel para botões
            JPanel buttonPanel = new JPanel();
            JButton btnEditar = new JButton("Editar Cliente Selecionado");
            JButton btnDeletar = new JButton("Deletar Cliente Selecionado");
            buttonPanel.add(btnEditar);
            buttonPanel.add(btnDeletar);

            // Ação do botão Deletar
            btnDeletar.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Cliente cliente = tableModel.getCliente(selectedRow);
                    int confirm = JOptionPane.showConfirmDialog(null, 
                            "Tem certeza que deseja DELETAR o cliente " + cliente.getNome() + " (Conta: " + cliente.getNumeroConta() + ")?", 
                            "Confirmação de Deleção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            dao.deletar(cliente.getId());
                            JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            // Recarrega a tela de consulta após a deleção
                            mostrarTelaConsulta(); 
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Erro ao deletar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um cliente na tabela para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            });
            
            // Ação do botão Editar
            btnEditar.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Cliente cliente = tableModel.getCliente(selectedRow);
                    // Passa o cliente para a tela de cadastro para edição
                    mostrarTelaCadastro(cliente); 
                    // Recarrega a tela de consulta após a edição
                    mostrarTelaConsulta();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um cliente na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            });
            
            // Painel principal para mostrar a tabela e os botões
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            JOptionPane.showMessageDialog(null, mainPanel, "Lista de Clientes Cadastrados", JOptionPane.PLAIN_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar clientes: " + e.getMessage(), "Erro de Banco", JOptionPane.ERROR_MESSAGE);
        }
    }


    // --- Métodos Auxiliares para a Máscara ---
    private static JFormattedTextField criarCampoConta(String initialValue) {
        try {
            MaskFormatter contaMask = new MaskFormatter("#####-#");
            contaMask.setPlaceholderCharacter('_');
            JFormattedTextField field = new JFormattedTextField(contaMask);
            if (initialValue != null) field.setText(initialValue);
            return field;
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

    private static JFormattedTextField criarCampoData(java.sql.Date initialDate) {
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            JFormattedTextField field = new JFormattedTextField(dateMask);
            if (initialDate != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                field.setText(formatter.format(initialDate));
            }
            return field;
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }
}