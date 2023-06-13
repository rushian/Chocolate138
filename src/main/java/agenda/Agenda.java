/**
 * @author Luciano Santos
 */
package agenda;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Agenda extends JFrame {
    JTable tblAmigos;
    JScrollPane scrlAmigos;
    JTextField txtNome;
    JFormattedTextField ftxtFone;
    MaskFormatter mskTelefone;
    JButton btnAddAmigo, btnDelAmigo, btnAlterar, btnSair;
    JLabel lblTitulo, lblNome, lblTelefone;

    public static void main(String[] args) {
        Agenda amigos = new Agenda();
        amigos.setVisible(true);
    }

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
        listaAmigos();
    }

    public final void relogio() {
        Relogio hora = new Relogio();

        lblTitulo = hora.lblHorario;
        lblTitulo.setBounds(330, 0, 120, 24);
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

        btnAlterar = new JButton("Alterar");
        btnAlterar.setBounds(20, 284, 165, 20);
        btnAlterar.addActionListener(update);
        btnAlterar.setMnemonic(KeyEvent.VK_C);
        add(btnAlterar);
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
        txtNome.setBounds(80, 227, 250, 20);
        txtNome.addActionListener(insert);
        add(txtNome);

        try {
            mskTelefone = new MaskFormatter("(##) #####-####");
        } catch (ParseException erro) {
            System.out.println("Erro - " + erro);
        }
        ftxtFone = new JFormattedTextField(mskTelefone);
        ftxtFone.setBounds(80, 254, 100, 20);
        ftxtFone.addActionListener(insert);
        add(ftxtFone);
    }

    public final void listaAmigos() {
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
    ActionListener insert = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent clic) {
            String nome = txtNome.getText();
            String fone = ftxtFone.getText();
            adicionarAmigo(nome, fone);
            txtNome.setText(null);
            ftxtFone.setValue(null);
            txtNome.requestFocus();
            listaAmigos();
        }
    };
    public void adicionarAmigo(String nome, String fone) {
        if(nome.length() > 0 && fone.replace(" ","").length() > 3){
            try {
                String jsonFilePath = "src/main/resources/amigos.json";
                // Ler o conteúdo do arquivo JSON
                String jsonContent = readFileAsString(jsonFilePath);

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


    ActionListener update = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent clic) {
            System.setProperty("file.encoding", "UTF-8");

            // Obter o índice da linha selecionada na tabela tblAmigos
            int selectedRow = tblAmigos.getSelectedRow();

            if (selectedRow != -1) { // Verificar se algum item está selecionado
                JTextField idAmigo = new JTextField();
                JTextField nome = new JTextField();
                JFormattedTextField fone = new JFormattedTextField(mskTelefone);

                // Obter os valores do item selecionado na tabela
                String amigoId = tblAmigos.getValueAt(selectedRow, 0).toString();
                String amigoNome = tblAmigos.getValueAt(selectedRow, 1).toString();
                String amigoTelefone = tblAmigos.getValueAt(selectedRow, 2).toString();

                // Definir os valores nos campos de texto
                idAmigo.setText(amigoId);
                idAmigo.setEnabled(false);
                nome.setText(amigoNome);
                fone.setText(amigoTelefone);

                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Id"),
                        idAmigo,
                        new JLabel("Nome"),
                        nome,
                        new JLabel("Telefone"),
                        fone
                };
                Object[] options = { "Salvar" };
                JOptionPane.showOptionDialog(null, inputs, "Alterar registro",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
                //JOptionPane.showMessageDialog(null, inputs, "Alterar registro", JOptionPane.PLAIN_MESSAGE);

                try {
                    File jsonFile = new File("src/main/resources/amigos.json");


                    // Ler o conteúdo do arquivo JSON
                    String jsonContent = readFileAsString(String.valueOf(jsonFile));

                    // Converter o conteúdo em um objeto JSON
                    JSONObject jsonObject = new JSONObject(jsonContent);

                    // Obter o array de amigos
                    JSONArray amigosArray = jsonObject.getJSONArray("amigos");
                    List<Map<String, Object>> amigos = new ArrayList<>();
                    for (int i = 0; i < amigosArray.length(); i++) {
                        JSONObject amigoObj = amigosArray.getJSONObject(i);
                        Map<String, Object> amigoMap = amigoObj.toMap();
                        amigos.add(amigoMap);
                    }

                  //  if (!idAmigo.getText().equals("") && !nome.getText().equals("") && !fone.getText().equals("")) {
                        int amigoIdNum = Integer.parseInt(idAmigo.getText());
                        boolean amigoEncontrado = false;

                        for (Map<String, Object> amigo : amigos) {
                            int id = (int) amigo.get("id");

                            if (id == amigoIdNum) {
                                amigo.put("nome", nome.getText());
                                amigo.put("telefone", fone.getText());
                                amigoEncontrado = true;
                                break;
                            }
                        }

                        if (amigoEncontrado) {
                            // Converter a lista de mapas de amigos de volta para um JSONArray
                            JSONArray novoAmigosArray = new JSONArray();
                            for (Map<String, Object> amigo : amigos) {
                                JSONObject amigoObj = new JSONObject(amigo);
                                novoAmigosArray.put(amigoObj);
                            }


                            // Atualizar o objeto JSON com o array de amigos modificado
                            jsonObject.put("amigos", amigosArray);

                            // Escrever o conteúdo atualizado de volta no arquivo JSON
                            String jsonString = jsonObject.toString();
                            Files.write(Paths.get(jsonFile.toURI()), jsonString.getBytes(StandardCharsets.UTF_8));
                            System.out.println("Amigo atualizado - id " + amigoId);
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Amigo não encontrado", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                   // }
                }catch (IOException e) {
                    System.out.println("Erro ao ler ou escrever no arquivo JSON: " + e.getMessage());
                }

                listaAmigos(); // Atualizando a tabela apresentada
                txtNome.requestFocus(); // Levando o cursor para um novo nome
            }
        }
    };

    ActionListener Sair = click -> System.exit(0);
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
}