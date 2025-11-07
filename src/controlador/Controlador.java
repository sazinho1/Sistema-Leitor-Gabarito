package controlador;

import interfaceGrafica.PainelCadastro;
import interfaceGrafica.PainelResultados;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
            
        } catch (Exception execao) {
            // pop up de erro generico
            JOptionPane.showMessageDialog(painelResultados, 
                "Ocorreu um erro: " + execao.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // metodos utilizados na interface (por meio dos botoes apertados)

    private void salvarResposta() throws Exception {
        // pega os dados do usuario
        String disciplina = painelCadastro.getCampoDisciplina().getText();
        String nome = painelCadastro.getCampoNomeAluno().getText();
        String respostas = painelCadastro.getCampoRespostas().getText().toUpperCase();

        // pra ver se o cara digitou alguma coisa
        if (disciplina.isEmpty() || nome.isEmpty() || respostas.length() != 10) {
            JOptionPane.showMessageDialog(painelCadastro, 
                "Preencha todos os campos! As respostas devem ter 10 caracteres.", 
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
        // função pra abrir o seletor de arquivo
        JFileChooser seletor = new JFileChooser("."); // "." = pasta atual
        int resultado = seletor.showOpenDialog(painelResultados);

        if (resultado == JFileChooser.APPROVE_OPTION) {// se der 0 é pq o arquivo ta ok
            File arquivo = seletor.getSelectedFile();
            caminhoDisciplina = arquivo.getAbsolutePath();
            painelResultados.getLabelCaminhoDisciplina().setText(arquivo.getName());// mostra o arquivo selecionado
        }
    }
    
    private void selecionarArquivoGabarito() {
        // mesma coisa do outro
        JFileChooser seletor = new JFileChooser(".");
        int resultado = seletor.showOpenDialog(painelResultados);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = seletor.getSelectedFile();
            caminhoGabarito = arquivo.getAbsolutePath();
            painelResultados.getLabelCaminhoGabarito().setText(arquivo.getName());
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