package controlador;

import interfaceGrafica.PainelCadastro;
import interfaceGrafica.PainelResultados;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*; 
import logicaPrincipal.Aluno;
import logicaPrincipal.GerenciadorArquivos;
import logicaPrincipal.ProcessarResultados;


public class Controlador implements ActionListener {

    private final PainelCadastro painelCadastro;
    private final PainelResultados painelResultados;
    private final GerenciadorArquivos gerenciador;
    private String caminhoDisciplina = null;
    private String caminhoGabarito = null;

    // construtor do controlador
    public Controlador(PainelCadastro painelCadastro, PainelResultados painelResultados) {
        this.painelCadastro = painelCadastro;
        this.painelResultados = painelResultados;
        this.gerenciador = new GerenciadorArquivos();

        // pra conectar o controlador (e sua interface) ao action listener
        this.painelCadastro.getBotaoSalvar().addActionListener(this); 
        /*conecta o botao salvar ao action listener (o "this" no final é pra referir que,
         toda vez que esse botao for clicado, ele chama o action listener (no try catch ali embaixo)
         e puxa a função relacionada)*/
        this.painelResultados.getBotaoSelDisciplina().addActionListener(this);
        this.painelResultados.getBotaoSelGabarito().addActionListener(this);
        this.painelResultados.getBotaoProcessar().addActionListener(this);
    }

    // metodo para toda vez que o botão for clicado
    @Override //Override pq é um metodo proprio da interface do action listener
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();// pra ver qual o botao clicado

        try {
            //botao de salvar a resposta do aluno
            if (source == painelCadastro.getBotaoSalvar()) {
                salvarResposta();
            }
            //botao de selecionar a disciplina
            else if (source == painelResultados.getBotaoSelDisciplina()) {
                selecionarArquivoDisciplina();
            }
            //botao de selecionar o gabarito
            else if (source == painelResultados.getBotaoSelGabarito()) {
                selecionarArquivoGabarito();
            }
            //botao pra processar os resultados e criar os arquivos
            else if (source == painelResultados.getBotaoProcessar()) {
                processarResultados();
            } 
            
        } catch (IllegalArgumentException eGabarito) { 
        // erro na validação do gabarito (formato inválido)
        JOptionPane.showMessageDialog(painelResultados, 
            "Erro de Formato no Gabarito: " + eGabarito.getMessage(), 
            "ERRO DE VALIDAÇÃO", 
            JOptionPane.ERROR_MESSAGE);
            
        } catch (IOException eArquivo) { 
        // erro de IO
        JOptionPane.showMessageDialog(painelResultados, 
            "Erro ao manipular arquivo: " + eArquivo.getMessage() + 
            "\nVerifique se os arquivos existem e se o nome da Disciplina está correto.",
            "ERRO DE ARQUIVO (I/O)", 
            JOptionPane.ERROR_MESSAGE);
            
        } catch (Exception eGenerica) { 
        // erro genérico
        eGenerica.printStackTrace(); // imprime o stack trace do erro no console para ajudar na depuração
        JOptionPane.showMessageDialog(painelResultados, 
            "Ocorreu um erro inesperado e grave: " + eGenerica.getMessage() + 
            "\nO programa será encerrado. Consulte o console para detalhes.", 
            "ERRO CRÍTICO", 
            JOptionPane.ERROR_MESSAGE);
            
        // fecha a aplicação
        System.exit(1);
        }
    }
    
    // metodos utilizados na interface (por meio dos botoes apertados)

    private void salvarResposta() throws Exception {
        // pega os dados do usuario
        String disciplina = painelCadastro.getCampoDisciplina().getText();
        String nome = painelCadastro.getCampoNomeAluno().getText();
        String respostas = painelCadastro.getCampoRespostas().getText().toUpperCase();

        // pra ver se o cara digitou alguma coisa
        if (disciplina.isEmpty() || nome.isEmpty()) {
            JOptionPane.showMessageDialog(painelCadastro, 
                "Preencha todos os campos! As respostas devem ter 10 caracteres.", 
                "Erro de Validação", 
                JOptionPane.WARNING_MESSAGE);
            return;//fecha a janela de erro
        }
        
        // faz a validação das respostas (deve ter 10 caracteres, apenas V ou F)
        if (!respostas.matches("^[VFvf]{10}$")) {
            JOptionPane.showMessageDialog(painelCadastro, 
                "As respostas devem conter exatamente 10 caracteres, apenas V ou F.", 
                "Erro de Validação", 
                JOptionPane.WARNING_MESSAGE);
            return;//fecha a janela de erro
        }
        
        // chama a logica
        gerenciador.salvarRespostaAluno(disciplina, nome, respostas);

        // pop up pra mostrar que deu certo
        JOptionPane.showMessageDialog(painelCadastro, 
            "Resposta salva no arquivo " + disciplina + ".txt!");

        // limpa os blocos de escrita la
        painelCadastro.getCampoNomeAluno().setText("");
        painelCadastro.getCampoRespostas().setText("");
    }
    
    private void selecionarArquivoDisciplina() {
        // chama a função da view pra selecionar o arquivo
        // recebe a string do caminho do arquivo selecionado
        String novoCaminho = painelResultados.selecionarArquivo(
            painelResultados.getLabelCaminhoDisciplina() // passa o JLabel para ser atualizado lá na View
        );
    
        // o Controller armazena o caminho
        if (novoCaminho != null) {
            this.caminhoDisciplina = novoCaminho;
        }
    }
    
    private void selecionarArquivoGabarito() {
        // repete a mesma lógica
        String novoCaminho = painelResultados.selecionarArquivo(
            painelResultados.getLabelCaminhoGabarito() // passa o JLabel para ser atualizado lá na View
        );

        if (novoCaminho != null) {
            this.caminhoGabarito = novoCaminho;
        }
    }
    
    private void processarResultados() throws Exception {
        if (caminhoDisciplina == null || caminhoGabarito == null) {
            // pop up pra mostrar que falta arquivos
            JOptionPane.showMessageDialog(painelResultados, 
                "Você precisa selecionar o arquivo da disciplina E o arquivo de gabarito.", 
                "Arquivos Faltando", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // pega o nome da disciplina, salva no nome base ("blabla.txt") e dps tira o txt ("blabla")
        String nomeBase = new File(caminhoDisciplina).getName();
        String nomeDisciplina = nomeBase.replace(".txt", "");
        
        // puxando as funcoes da logica principal pra aba de resultados
        
        String gabarito = gerenciador.lerGabarito(caminhoGabarito); // lê e armazena na string o gabarito a partir da leitura do file gabarito
        ArrayList<Aluno> alunos = gerenciador.lerRespostasDisciplina(caminhoDisciplina); // salva a resposta dos alunos pela leitura do arquivo da disciplina
        ProcessarResultados processador = new ProcessarResultados(gabarito); // salva o gabarito
        processador.processarNotas(alunos); // calcula as notas
        double media = processador.calcularMedia(alunos); // calcula a media
        gerenciador.salvarResultadosPorNota(alunos, media, nomeDisciplina); //cria os arquivos de saída

        // exibe na tela
        StringBuilder textoResultado = new StringBuilder(); //cria uma "string composta" pra ser exibida no painel de resultados
        textoResultado.append("--- RESULTADOS DA DISCIPLINA: ").append(nomeDisciplina).append(" ---\n");// o append é pra ir adicionando
        textoResultado.append("Gabarito: ").append(gabarito).append("\n");
        textoResultado.append("Média da Turma: ").append(String.format("%.2f", media)).append("\n"); //%.2f pro numero aproximar 2 casas
        textoResultado.append("------------------------------------------\n\n");
        
        // reordena as notas como na função lambda do "salvarResultadosPorNota" no gerenciador de arquivos
        alunos.sort((a1, a2) -> Integer.compare(a2.getNota(), a1.getNota()));
        
        for (Aluno a : alunos) {
            textoResultado.append(String.format("%-30s | Nota: %d\n", a.getNome(), a.getNota())); 
            // o "%-30s"é pra alinhar uma string (s) em (30) caracteres ("-" pra alinhamento a esquerda). O % é pq é uma variavel (nome)
        }
        painelResultados.getAreaResultados().setText(textoResultado.toString()); 
        // bota o texto na area do painel de resultados (o "toString" é pra ele imprimir tudo como string e nao dar erro imprimindo os numeros)

    }
}