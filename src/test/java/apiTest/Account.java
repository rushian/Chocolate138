// 1. pacote
package apiTest;

// 2. imports
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import entities.AccountEntity;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

// 3. Classe
public class Account {
    // 3.1 Atributos
        //3.1.1 instaciar atributos
        String userId;
        //3.1.2 Instanciar Classes
        Gson gson = new Gson(); // instancia o objeto

    //3.2 métodos e funções
    @Test
    public void testCreateUser(){
        //Arrange
        AccountEntity account = new AccountEntity();
        account.userName = "luke0002";
        account.password = "@Aa123456";

        String jsonBody = gson.toJson(account);
        System.out.println(jsonBody);

        //Act
        Response resposta = (Response) given() // dado que
            .contentType("application/json")
            .log().all().body(jsonBody).
        when() //quando
            .post("https://bookstore.toolsqa.com/Account/v1/User").
        then()
            .log().all()
            .statusCode(201) // valida a comunicacao
            .body("username", is(account.userName))
            .extract()
        ;

        // extrair userID
        userId = resposta.jsonPath().getString("userID");
        System.out.println("UserID extraido: " + userId);

        if(resposta.getStatusCode() == 201) {
            // Especifique o caminho do arquivo
            String filePath = "src/test/resources/respostaUsuario.json";
            LocalDateTime now = LocalDateTime.now();
            // Converter a data e hora para uma representação formatada
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String dataAtual = now.format(formatter);
            // Crie um FileWriter para escrever no arquivo
            FileWriter writer = null;
            try {
                // Escreva o JSON no arquivo

                JsonObject jsonExistente = gson.fromJson(resposta.asPrettyString(), JsonObject.class);
                jsonExistente.addProperty("Data criação", dataAtual);
                Gson gsonComData = new GsonBuilder().setPrettyPrinting().create();
                gsonComData.toJson(jsonExistente);
                File arquivo = new File(filePath);
                if(arquivo.exists()){
                    // Lê o conteúdo do arquivo

                    writer = new FileWriter(filePath, true);

                    writer.write("," + gsonComData.toJson(jsonExistente));
                    writer.write(System.lineSeparator());
                    String conteudo = lerArquivoParaString(filePath);
                    System.out.println(conteudo);
                    //String conteudo = Files.readAllLines(Path.of(filePath)).toString();

                    // Verifica se o conteúdo não contém '[' na primeira linha
                    if (!conteudo.startsWith("[")) {
                        // Adiciona '[' na primeira linha
                        conteudo = "[" + System.lineSeparator() + conteudo;
                    }

                    // Verifica se o conteúdo não contém ']' na última linha
                    if (!conteudo.endsWith("]")) {
                        // Adiciona ']' na última linha
                        conteudo = conteudo + System.lineSeparator() + "]";
                    }
                    writer.write(conteudo);
                }else{
                    writer = new FileWriter(filePath);
                    writer.write(gsonComData.toJson(jsonExistente));
                    writer.write(System.lineSeparator());
                }
                // Feche o FileWriter
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void testGenerateToken(){

    }// fim do metodo de geracao de token de identificacao do usuario
    public static String lerArquivoParaString(String nomeArquivo) throws IOException {
        byte[] bytesArquivo = Files.readAllBytes(Paths.get(nomeArquivo));
        return new String(bytesArquivo);
    }
}
