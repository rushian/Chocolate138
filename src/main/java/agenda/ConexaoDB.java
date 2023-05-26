/**
 * alterado por Luciano Santos
 * @site www.analisedesistemas.wordpress.com
 */
/*package agenda;

import java.sql.*;
import java.io.*;

public class ConexaoDB {

    Connection conexao;
    FileInputStream fi;
    BufferedReader buff;
    String line = "";
    String[] lines = new String[30];
    int i = 0;

    public ConexaoDB() {
        try {
            fi = new FileInputStream("/home/luciano/NetBeansProjects/Agenda/db.conf");
            /*
             * Num bloco de notas [windows] ou gedit [linux] digite as seguintes
             * informacoes e salve o arquivo na pasta do projeto com o nome db.conf
             * e altere o endereco acima para o endereco do arquivo.
             *
             * ex: c:\netBeansProjects\agenda\db.conf
             * ============================>>>
            #hostname ou Endereco IP
            localhost
            #Nome da base de dados
            agenda
            #Nome de usuário MySQL
            luciano
            #senha MySQL (deixe a proxima linha em branco se nao usar senha)
            entrar
            #fim
             * ==============================<<<
             * o arquivo deve conter somente as 9 linhas acima com as devidas
             * alteracoes de nome de usuario e senha.
             */
            /*buff = new BufferedReader(new InputStreamReader(fi));
            i = 0;
            while ((line = buff.readLine()) != null) {
                lines[i] = line;
                i++;
            }
            String host = lines[1].trim();
            String baseDeDados = lines[3].trim();
            String usuario = lines[5].trim();
            String senha = lines[7].trim();
            try{
                conexao = DriverManager.getConnection("jdbc:mysql://" + host + "/" + baseDeDados, usuario, senha);
                System.out.println("Conexao com banco de dados realizada com sucesso");
            }catch (SQLException erro1){
                System.out.println("Erro de conexao - " + erro1);
            }
        } catch (Exception erro2) {
            System.out.println("Erro de arquivo - " + erro2);
        }
    }
}*/