package logicaPrincipal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;



public class GerenciadorArquivos {

    // Método para salvar a resposta de um aluno em um arquivo da disciplina
    public void salvarRespostaAluno(String Disciplina, String nomeAluno, String respostas) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Disciplina + ".txt", true))) {
            String linhaParaSalvar = respostas + "\t" + nomeAluno;
            bw.write(linhaParaSalvar); //Escreve a linha
            bw.newLine(); //Pula pra proxima linha
        }
    }

    // metodo pra salvar as respostas de uma disciplina e separá-las por alunos
    public ArrayList<Aluno> lerRespostasDisciplina(String caminhoArquivo) throws IOException {
        ArrayList<Aluno> alunos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split("\t"); //Quebra a linha no /t (tab)
                if (partes.length == 2) { //Vê se o tamanho da linha ta certo (respostas e nome do aluno)
                    String respostas = partes[0].toUpperCase(); //Pega as respostas e transforma em maiusculo
                    String nome = partes[1].trim(); //Pega o nome do aluno e tira espaços em branco desnecessarios
                    
                    if (respostas.matches("^[VFvf]{10}$") && !nome.isEmpty()) { 
                    // Se as respostas forem válidas (10 V/F) e o nome não estiver vazio
                    
                    String respostasMaiusculas = respostas.toUpperCase();
                    // converte para maiúsculas (necessário para o cálculo correto da nota)
                    
                    // Cria e adiciona o aluno
                    alunos.add(new Aluno(nome, respostasMaiusculas));
                    } 
                // else: Linha inválida (formato de resposta errado ou nome vazio), o programa IGNORA e segue para a próxima linha
                }
            // else: Linha inválida (ex: menos ou mais de 1 tab), o programa IGNORA
            }
        }
        // Se nada foi lido, algo deu errado, então lança uma exceção
        if (alunos.isEmpty()) {
        throw new IOException("O arquivo da disciplina não contém respostas válidas.");
        }

        return alunos;
    }


    //Método para ler linhas do gabarito
    public String lerGabarito(String caminhoArquivo) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) { 
        return br.readLine().toUpperCase(); //Lê e retorna a primeira linha(que é a unica linha q tem o gabarito)
        }
    }

    //Metodo que gera o arquivo de saída com notas de modo decrescente ou por ordem alfabetica
    public void salvarResultadosPorNota(ArrayList<Aluno> alunos, double media, String nomeDisciplina) throws IOException {
        // Arquivo por Nome 
        Collections.sort(alunos); //Ordena por nome (usando o compareTo la da classe Aluno que puxa a interface Comparable)

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeDisciplina + "_por_nome.txt"))){
            for (Aluno a : alunos) {
                bw.write("Aluno: " + a.getNome() + ", Acertos: " + a.getNota());
                bw.newLine();
            }
        }

        // Arquivo por Nota Decrescente
        //função lambda pra comparar os alunos (a1 e a2) na ordem inversa (compare(a2,a1))
        Collections.sort(alunos, (a1, a2) -> Integer.compare(a2.getNota(), a1.getNota()));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeDisciplina + "_por_nota.txt"))) {
            for (Aluno aluno : alunos) {
                bw.write(aluno.getNome() + "\t" + aluno.getNota() + " acertos");
                bw.newLine();
            }
            bw.newLine(); //Linha em branco
            //Formata a media em uma string
            bw.write(String.format("Media da turma: %.2f", media));
        }
    }
}