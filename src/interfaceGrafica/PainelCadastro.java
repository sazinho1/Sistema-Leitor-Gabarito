package interfaceGrafica;

import java.awt.*; //todos os componentes Swing
import javax.swing.*; //classes de layout

//JPanel é a classe pra puxar uma janela criada mas sem nada dentro (pique uma moldura sem quadro)
public class PainelCadastro extends JPanel {

    // Declarando os componentes da interface
    private JTextField campoDisciplina;
    private JTextField campoNomeAluno;
    private JTextField campoRespostas; 
    private JButton botaoSalvar;

    public PainelCadastro() {
        //GridLayout cria a grade de linhas e colunas
        // (4, 2) = 4 linhas, 2 colunas. O 5, 5 é o espaçamento
        setLayout(new GridLayout(4, 2, 5, 5)); 

        //Inicializando os componentes
        JLabel labelDisciplina = new JLabel("Nome da Disciplina:");
        campoDisciplina = new JTextField();// nao precisa especificar o tamanho, ja que é um layout por grade

        JLabel labelNomeAluno = new JLabel("Nome do Aluno:");
        campoNomeAluno = new JTextField();

        JLabel labelRespostas = new JLabel("Respostas (10 V ou F):");
        campoRespostas = new JTextField();

        botaoSalvar = new JButton("Salvar Resposta");

        // Colocando os componentes ao painel (na ordem)
        add(labelDisciplina);
        add(campoDisciplina);
        add(labelNomeAluno);
        add(campoNomeAluno);
        add(labelRespostas);
        add(campoRespostas);
        add(new JLabel()); //Pro botao de salvar ficar do lado direito da tela
        add(botaoSalvar);
    }

    //O controlador pega o conteúdo desses campos e o botão para perceber o clique
    
    public JTextField getCampoDisciplina() {
        return campoDisciplina;
    }

    public JTextField getCampoNomeAluno() {
        return campoNomeAluno;
    }

    public JTextField getCampoRespostas() {
        return campoRespostas;
    }

    public JButton getBotaoSalvar() {
        return botaoSalvar;
    }
}