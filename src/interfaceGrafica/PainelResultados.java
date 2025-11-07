package interfaceGrafica;

import java.awt.*;
import javax.swing.*;

public class PainelResultados extends JPanel {


    private JButton botaoSelectDisciplina;
    private JButton botaoSelectGabarito;
    private JButton botaoProcessar;
    private JLabel labelCaminhoDisciplina; //Mostra o arquivo selecionado
    private JLabel labelCaminhoGabarito;
    private JTextArea areaResultados; //Onde os resultados aparecem
    private JScrollPane scrollResultados; //Barra de rolagem para a área de texto

    public PainelResultados() {
        //BorderLayout é pra dividir o painel em norte, sul, leste, oeste e centro
        setLayout(new BorderLayout(10, 10));

        // Painel pros botões (no topo)
        JPanel painelBotoes = new JPanel(new GridLayout(3, 2, 5, 5));

        //Inicializando e adicionando os botões (O JLabel é pra mostrar texto)
        //O Jbutton é intuitivo
        botaoSelectDisciplina = new JButton("Selecionar Arquivo da Disciplina...");
        labelCaminhoDisciplina = new JLabel("Nenhum arquivo selecionado.");

        botaoSelectGabarito = new JButton("Selecionar Arquivo de Gabarito...");
        labelCaminhoGabarito = new JLabel("Nenhum arquivo selecionado.");

        botaoProcessar = new JButton("PROCESSAR RESULTADOS");
        //Cor pro botão
        botaoProcessar.setBackground(Color.YELLOW); 

        painelBotoes.add(botaoSelectDisciplina);
        painelBotoes.add(labelCaminhoDisciplina);
        painelBotoes.add(botaoSelectGabarito);
        painelBotoes.add(labelCaminhoGabarito);
        painelBotoes.add(new JLabel()); // Célula vazia
        painelBotoes.add(botaoProcessar);

        //Área de texto (do meio pra baixo)
        areaResultados = new JTextArea();
        areaResultados.setEditable(false); //Usuário não pode digitar aqui
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 16)); //Fonte
        scrollResultados = new JScrollPane(areaResultados); //Barra de rolagem lateral e vertical pra ver os resultados

        //Botando os botoes no painel principal
        add(painelBotoes, BorderLayout.NORTH); // Pro painel de botões ficar no topo
        add(scrollResultados, BorderLayout.CENTER); // Pra área de texto ficar no centro
    }

    // Getters pro controlador
    public JButton getBotaoSelDisciplina() {
        return botaoSelectDisciplina;
    }

    public JButton getBotaoSelGabarito() {
        return botaoSelectGabarito;
    }

    public JButton getBotaoProcessar() {
        return botaoProcessar;
    }

    public JLabel getLabelCaminhoDisciplina() {
        return labelCaminhoDisciplina;
    }

    public JLabel getLabelCaminhoGabarito() {
        return labelCaminhoGabarito;
    }

    public JTextArea getAreaResultados() {
        return areaResultados;
    }
}
