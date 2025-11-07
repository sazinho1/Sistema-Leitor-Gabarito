package interfaceGrafica;

// Importar o controlador
import controlador.*;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

//JFrame é a classe pra fazer a janela do sistema
public class JanelaPrincipal extends JFrame  {

    public JanelaPrincipal() {
        // Configurações básicas da janela
        setTitle("Leitor de Gabaritos");
        setSize(700, 500); //Largura e altura
        // Para o programa quando clica no "X"
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        // Abre a janela no centro da tela
        setLocationRelativeTo(null); 
        //Botando a logo do mangoo no canto 
        try {
            //Detalhe: só funcionou com o nome do pacote que tem o png sendo "resources"
            Image mangoo = ImageIO.read(getClass().getResource("/resources/Mangoo.png"));
            setIconImage(mangoo);
        } catch (IOException e) {
            System.out.println("Erro ao carregar a imagem.");
        }
        // Criando os painéis das abas
        PainelCadastro painelCadastro = new PainelCadastro();
        PainelResultados painelResultados = new PainelResultados();

        // Passamos os painéis para ele, para que ele possa "ouvir" os botões
        Controlador controller = new Controlador(painelCadastro, painelResultados);

        // Instancia da classe que cria as abas
        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Cadastrar Respostas (Item 1)", painelCadastro);
        abas.addTab("Gerar Resultados (Item 2)", painelResultados);

        // Adiciona o sistema de abas na janela principal
        add(abas);
    }
}