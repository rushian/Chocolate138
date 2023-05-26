/**
 * @author Luciano Santos
 */
package agenda;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.*;
import javax.json.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Agenda extends JFrame {

    /*Use os seguintes comandos no mySQL para criar o banco de dados
    create database agenda;
    use agenda;
    create table amigos (idAmigo int (7) AUTO_INCREMENT PRIMARY KEY, nome varchar(30), telefone varchar (15));
    desc amigos;
    */
    public String CONSULTA_PADRAO = "SELECT * FROM amigos ORDER BY nome";
    JTable tblAmigos;
    JScrollPane scrlAmigos;
    JTextField txtNome;
    JFormattedTextField ftxtFone;
    MaskFormatter mskTelefone;
    JButton btnAddAmigo, btnDelAmigo, btnCorrigir, btnSair;
    JLabel lblTitulo, lblNome, lblTelefone;
    //PreparedStatement pStmtAmigo;
  //  TabelaModelo mdlAmigo;
    ActionListener update = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent clic) {
            System.setProperty("file.encoding", "UTF-8");
            JTextField idAmigo = new JTextField();
            JTextField nome = new JTextField();
            JFormattedTextField fone = new JFormattedTextField(mskTelefone);

            final JComponent[] inputs = new JComponent[]{
                    new JLabel("<html>Digite os dados corretos:<br>id</html>"),
                    idAmigo,
                    new JLabel("Nome"),
                    nome,
                    new JLabel("Telefone"),
                    fone
            };

            JOptionPane.showMessageDialog(null, inputs, "Corrigir registro", JOptionPane.PLAIN_MESSAGE);

            try {
                File jsonFile = new File("src/main/resources/amigos.json");
                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> amigos = objectMapper.readValue(jsonFile, new TypeReference<>() {});

                if (!idAmigo.getText().equals("") && !nome.getText().equals("") && !fone.getText().equals("")) {
                    int amigoId = Integer.parseInt(idAmigo.getText());
                    boolean amigoEncontrado = false;

                    for (Map<String, Object> amigo : amigos) {
                        int id = (int) amigo.get("id");

                        if (id == amigoId) {
                            amigo.put("nome", nome.getText());
                            amigo.put("telefone", fone.getText());
                            amigoEncontrado = true;
                            break;
                        }
                    }

                    if (amigoEncontrado) {
                        objectMapper.writeValue(jsonFile, amigos);
                        System.out.println("Amigo atualizado - id " + amigoId);
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Amigo não encontrado", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Preencha todos os dados para correcao", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler ou escrever no arquivo JSON: " + e.getMessage());
            }

            buscaAmigos(); // Atualizando a tabela apresentada
            txtNome.requestFocus(); // Levando o cursor para um novo nome
        }
    };
    /*

    public String INSERCAO = "INSERT INTO amigos (id,nome,telefone) VALUES (?,?,?)";
    ActionListener Add = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent clic) {
            try {
                dbAmigo = new ConexaoDB();
                pStmtAmigo = dbAmigo.conexao.prepareStatement(INSERCAO);

                if (!txtNome.getText().equals("") && !ftxtFone.getText().equals("")) {// tratamento logico
                    pStmtAmigo.setString(1, txtNome.getText());
                    pStmtAmigo.setString(2, ftxtFone.getText());

                    int retorno = pStmtAmigo.executeUpdate();
                    System.out.println(retorno + " linha inserida - Nome: " + txtNome.getText());
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Insira um nome e um número de telefone", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
                pStmtAmigo.close();
                dbAmigo.conexao.close();
            } catch (SQLException erro2) {
                System.out.println("Erro ao inserir - " + erro2);
            }
            buscaAmigos();//atualizando a tabela apresentada
            ftxtFone.requestFocus();//levando o cursor para um novo telefone
        }
    };
    public String DELECAO = "DELETE FROM amigos WHERE idAmigo =  (?)";
    ActionListener Del = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent clic) {
            try {
                dbAmigo = new ConexaoDB();
                pStmtAmigo = dbAmigo.conexao.prepareStatement(DELECAO);
                String idAmigo = "0";
                if (!idAmigo.equals("")) {// tratamento logico
                    pStmtAmigo.setString(1, JOptionPane.showInputDialog("Qual o id do amigo a ser excluido?"));
                    int retorno = pStmtAmigo.executeUpdate();
                    System.out.println(retorno + " linha excluida - id " + idAmigo);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Insira um id válido", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
                pStmtAmigo.close();
                dbAmigo.conexao.close();
            } catch (SQLException erro2) {
                System.out.println("Erro ao excluir - " + erro2);
            }
            buscaAmigos();//atualizando a tabela apresentada
            ftxtFone.requestFocus();//levando o cursor para um novo telefone
        }
    };
    */
    ActionListener Sair = click -> System.exit(0);

    public Agenda() {
        setTitle("Agenda de telefones");
        setSize(400, 375);
        setLocationRelativeTo(null);

        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        relogio();
        botoes();
        caixas();
        rotulos();
        tabela();
        buscaAmigos();
    }

            private static String readFileAsString(String filePath) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    return stringBuilder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

    public static void main(String[] args) {
        Agenda amigos = new Agenda();
        amigos.setVisible(true);
    }

    public final void relogio() {
        Relogio hora = new Relogio();

        lblTitulo = hora.lblHorario;
        lblTitulo.setBounds(150, 0, 120, 24);
        add(lblTitulo);
    }

    public final void tabela() {
        tblAmigos = new JTable();
        tblAmigos.setBounds(20, 20, 350, 200);
        add(tblAmigos);

        scrlAmigos = new JScrollPane(tblAmigos);
        scrlAmigos.setBounds(20, 20, 350, 200);
        add(scrlAmigos);
    }

    public final void botoes() {
        btnAddAmigo = new JButton("Adicionar");
        btnAddAmigo.setBounds(220, 254, 150, 20);
        btnAddAmigo.addActionListener(insert);
        btnAddAmigo.setMnemonic(KeyEvent.VK_A);
        add(btnAddAmigo);

        btnSair = new JButton("Sair");
        btnSair.setBounds(120, 314, 150, 20);
        btnSair.addActionListener(Sair);
        btnSair.setMnemonic(KeyEvent.VK_S);
        add(btnSair);

        btnDelAmigo = new JButton("Deletar");
        btnDelAmigo.setBounds(200, 284, 170, 20);
        //btnDelAmigo.addActionListener(delete);
        add(btnDelAmigo);

        btnCorrigir = new JButton("Corrigir");
        btnCorrigir.setBounds(20, 284, 165, 20);
        btnCorrigir.addActionListener(update);
        btnCorrigir.setMnemonic(KeyEvent.VK_C);
        add(btnCorrigir);
    }

    public final void rotulos() {
        lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 227, 150, 20);
        add(lblNome);

        lblTelefone = new JLabel("Celular:");
        lblTelefone.setBounds(20, 254, 150, 20);
        add(lblTelefone);
    }

    public final void caixas() {
        txtNome = new JTextField(30);
        txtNome.setBounds(70, 227, 220, 20);
        txtNome.addActionListener(insert);
        add(txtNome);

        try {
            mskTelefone = new MaskFormatter("(##) #####-####");
        } catch (ParseException erro) {
            System.out.println("Erro - " + erro);
        }
        ftxtFone = new JFormattedTextField(mskTelefone);
        ftxtFone.setBounds(90, 254, 120, 20);
        ftxtFone.addActionListener(insert);
        add(ftxtFone);
    }

    public final void buscaAmigos() {
        // Ler o arquivo JSON
        String jsonFilePath = "src/main/resources/amigos.json";
        String jsonContent = readFileAsString(jsonFilePath);

        // Converter o JSON em um objeto Java
        JSONObject jsonObject = new JSONObject(jsonContent);
        JSONArray amigosArray = jsonObject.getJSONArray("amigos");

        // Criar um DefaultTableModel
        DefaultTableModel tableModel = new DefaultTableModel();

        // Adicionar as colunas ao modelo da tabela
        JSONObject firstRow = amigosArray.getJSONObject(0);
        for (int i = firstRow.length() - 1; i >= 0; i--) {
            String columnName = firstRow.names().getString(i);
            tableModel.addColumn(columnName);
        }

        // Adicionar as linhas ao modelo da tabela
        for (int i = 0; i < amigosArray.length(); i++) {
            JSONObject rowDataObject = amigosArray.getJSONObject(i);
            Vector<Object> rowDataVector = new Vector<>();
            for (int j = rowDataObject.length() - 1; j >= 0; j--) {
                rowDataVector.add(rowDataObject.get(rowDataObject.names().getString(j)));
            }
            tableModel.addRow(rowDataVector);
        }
        tblAmigos.setModel(tableModel);
    }
    public void adicionarAmigo(String nome, String fone) {
        if(nome.length() > 0 && fone.replace(" ","").length() > 3){
            try {
                String jsonFilePath = "src/main/resources/amigos.json";
                String jsonContent = readFileAsString(jsonFilePath);

                // Ler o conteúdo do arquivo JSON

                // Converter o conteúdo em um objeto JSON
                JSONObject jsonObject = new JSONObject(jsonContent);

                // Obter o array de amigos
                JSONArray amigosArray = jsonObject.getJSONArray("amigos");

                // Criar um novo objeto JSON para o novo amigo
                JSONObject novoAmigo = new JSONObject();
                novoAmigo.put("id", amigosArray.length() + 1);
                novoAmigo.put("nome", nome);
                novoAmigo.put("telefone", fone);

                // Adicionar o novo amigo ao array existente de amigos
                amigosArray.put(novoAmigo);

                // Atualizar o objeto JSON com o array de amigos modificado
                jsonObject.put("amigos", amigosArray);

                // Escrever o conteúdo atualizado de volta no arquivo JSON
                String jsonString = jsonObject.toString();
                Files.write(Paths.get(jsonFilePath), jsonString.getBytes(StandardCharsets.UTF_8));

            } catch (IOException e) {
                System.out.println("Erro ao adicionar amigo no arquivo JSON: " + e.getMessage());
            }

        }
        if(fone.replaceAll("[ ]", "").length() == 3 && nome.length() == 0)
            JOptionPane.showMessageDialog(rootPane, "Para adicionar preencha os campos Nome e Telefone", "Aviso", JOptionPane.WARNING_MESSAGE);
        else if(fone.replaceAll("[\s]", "").length() == 3)
            JOptionPane.showMessageDialog(rootPane, "Preencha um numero de celular valido com ddd", "Aviso", JOptionPane.WARNING_MESSAGE);
        else if(nome.length() == 0)
            JOptionPane.showMessageDialog(rootPane, "Preencha o nome", "Aviso", JOptionPane.WARNING_MESSAGE);

    }

    final ActionListener insert = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent clic) {
            String nome = txtNome.getText();
            String fone = ftxtFone.getText();
            adicionarAmigo(nome, fone);
            txtNome.setText(null);
            ftxtFone.setValue(null);
            txtNome.requestFocus();
            buscaAmigos();
        }
    };
}