/**
 * @author Luciano Santos
 * @site http://analisedesistemas.wordpress.com
 */
package agenda;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.text.DecimalFormat;
import java.util.Calendar;

public class Relogio extends JFrame {
    JLabel lblHorario;//declarando componente

    //declarando variáveis
    int hh, mm, ss;
    Calendar hora;
    DecimalFormat formato;

    public Relogio() {
        setTitle("Relogio");//definindo o titulo da janela

        setSize(160, 50); //definindo tamanho
        setLayout(new FlowLayout());//definindo o layout da janela
        setLocationRelativeTo(null);//centralizando a janela

        lblHorario = new JLabel("");//configurando o label inicial
        add(lblHorario);//adicionando o label configurado a janela
        inicio(); //invocando o metodo inicio
    }
    public final void inicio() { //criando o metodo inicio
        Timer time = new Timer(0, ativar);
        time.start();
    }
    private String formatar(int num) {
        formato = new DecimalFormat("00");//configurando para que o valor usado apresente 2 digitos
        return formato.format(num);
    }
    public void horas() {//criando o metodo horas
        hora = Calendar.getInstance();
        hh = hora.get(Calendar.HOUR_OF_DAY);//registrando a hora
        mm = hora.get(Calendar.MINUTE);//registrando os minutos
        ss = hora.get(Calendar.SECOND);//registrando os segundos
        lblHorario.setText(formatar(hh) + ":" + formatar(mm) + ":" + formatar(ss));
    }
    ActionListener ativar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            horas();//invocando o metodo horas
        }
    };
}