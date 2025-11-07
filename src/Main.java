
import interfaceGrafica.JanelaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) throws Exception {
            SwingUtilities.invokeLater(()->{
                //Instancia a janela e torna ela visivel
                JanelaPrincipal janela = new JanelaPrincipal();
                janela.setVisible(true);
            });
    }
}
