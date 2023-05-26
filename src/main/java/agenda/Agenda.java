/**
 * @author Luciano Santos
 */
package agenda;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class Agenda extends JFrame {

    JTable tblAmigos;
    JScrollPane scrlAmigos;
    JTextField txtNome;
    JFormattedTextField ftxtFone;
    MaskFormatter mskTelefone;
    JButton btnAddAmigo, btnDelAmigo, btnCorrigir, btnSair;
    JLabel lblTitulo, lblNome, lblTelefone;
    PreparedStatement pStmtAmigo;

    TabelaModelo mdlAmigo;

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
        //btnAddAmigo.addActionListener(insert);
        btnAddAmigo.setMnemonic(KeyEvent.VK_A);
        add(btnAddAmigo);

        btnSair = new JButton("Sair");
        btnSair.setBounds(120, 314, 150, 20);
        btnSair.addActionListener(Sair);
        btnSair.setMnemonic(KeyEvent.VK_S);
        add(btnSair);

        btnDelAmigo = new JButton("Deletar");
        btnDelAmigo.setBounds(200, 284, 170, 20);
        btnDelAmigo.addActionListener(delete);
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

        lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(20, 254, 150, 20);
        add(lblTelefone);
    }

    public final void caixas() {
        txtNome = new JTextField(30);
        txtNome.setBounds(70, 227, 220, 20);
        add(txtNome);
        try {
            mskTelefone = new MaskFormatter("(##) ####-####");
        } catch (ParseException erro) {
            System.out.println("Erro - " + erro);
        }
        ftxtFone = new JFormattedTextField(mskTelefone);
        ftxtFone.setBounds(90, 254, 120, 20);
        add(ftxtFone);
    }
    /*Use os seguintes comandos no mySQL para criar o banco de dados
    create database agenda;
    use agenda;
    create table amigos (idAmigo int (7) AUTO_INCREMENT PRIMARY KEY, nome varchar(30), telefone varchar (15));
    desc amigos;
    */
    public String CONSULTA_PADRAO = "SELECT * FROM amigos ORDER BY nome";
    public final void buscaAmigos() {
        // Ler o arquivo JSON e mapear para um objeto Java
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("src/main/resources/amigos.json");
        //AmigosDataObject amigosDataObject = objectMapper.readValue(jsonFile, AmigosDataObject.class);

        // Usar os dados do objeto Java como fonte para a tabela
        mdlAmigo = new TabelaModelo(jsonFile);
        tblAmigos.setModel(mdlAmigo);
    }
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
            ftxtFone.requestFocus(); // Levando o cursor para um novo telefone
        }
    };

        ActionListener delete = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent clic) {
                try {
                    // Carrega os dados existentes do arquivo JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    File jsonFile = new File("src/main/resources/amigos.json");
                    List<Amigo> amigos = objectMapper.readValue(jsonFile, new TypeReference<List<Amigo>>() {});

                    // Solicita o ID do amigo a ser excluído
                    String idAmigo = JOptionPane.showInputDialog("Qual o ID do amigo a ser excluído?");
                    if (idAmigo != null && !idAmigo.isEmpty()) {
                        // Percorre a lista e remove o amigo com o ID correspondente
                        Iterator<Amigo> iterator = amigos.iterator();
                        while (iterator.hasNext()) {
                            Amigo amigo = iterator.next();
                            if (amigo.getIdAmigo().equals(idAmigo)) {
                                iterator.remove();
                                System.out.println("Registro excluído - ID: " + idAmigo);
                                break;
                            }
                        }

                        // Salva a lista atualizada no arquivo JSON
                        objectMapper.writeValue(jsonFile, amigos);
                        System.out.println("Lista de amigos atualizada no arquivo JSON: " + jsonFile.getAbsolutePath());
                    } else {
                        JOptionPane.showMessageDialog(null, "Insira um ID válido", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir registro do arquivo JSON: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        };





    /*

    public String INSERCAO = "INSERT INTO amigos (nome,telefone) VALUES (?,?)";
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
    ActionListener Sair = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent click) {
            System.exit(0);
        }
    };
    public static class AmigosDataObject {
        private List<Amigo> amigos;

        public List<Amigo> getAmigos() {
            return amigos;
        }

        public void setAmigos(List<Amigo> amigos) {
            this.amigos = amigos;
        }
    }
    public static class Amigo {
        private String nome;
        private String telefone;

        private int ID;

        public Amigo(String nome, String telefone) {
        }

        public String getNome() {
            return nome;
        }
        public void setNome(String nome) {
            this.nome = nome;
        }
        public String getTelefone() {
            return telefone;
        }
        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public Object getIdAmigo() {
            return ID;
        }
    }
    public static void main(String[] args) {
        Agenda amigos = new Agenda();
        amigos.setVisible(true);
    }
}