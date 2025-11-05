package logicaPrincipal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;



public class GerenciadorArquivos{

    //
    public void salvarRespostaAluno(String Disciplina, String nomeAluno, String respostas) throws IOException{
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Disciplina + ".txt", true))) {
            String linhaParaSalvar = respostas + "\t" + nomeAluno;
            bw.write(linhaParaSalvar); //Escreve a linha
            bw.newLine(); //Pula pra proxima linha
        }
    }

    public ArrayList<Aluno> lerRespostasDisciplina(String caminhoArquivo) throws IOException{
        ArrayList<Aluno> alunos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))){
            String linha;
            while ( (linha = br.readLine()) != null) {
                String[] partes = linha.split("\t"); //Quebra a linha no /t (tab)
                if (partes.length == 2) { //Vê se o tamanho da linha ta certo (respostas e nome do aluno)
                    String respostas = partes[0];
                    String nome = partes[1];
                    Aluno a = new Aluno(nome, respostas);
                    alunos.add(a);
                }
            }
        }
        return alunos;
    }


    // Um método para ler linhas de um arquivo
    public String lerGabarito(String caminhoArquivo) throws IOException{

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo)) ) {
        return br.readLine(); //Lê e retorna a primeira linha(que é a unica linha q tem o gabarito)
        }
    }

    //Gera o arquivo de saída por notas de modo decrescente ou por ordem alfabetica
    public void salvarResultadosPorNota(ArrayList<Aluno> alunos, double media, String nomeDisciplina) throws IOException {
        // Arquivo por Nome 
        Collections.sort(alunos); // Ordena por nome (usando o compareTo)

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeDisciplina + "_por_nome.txt"))){
            for (Aluno a : alunos) {
                bw.write("Aluno: " + a.getNome() + ", Acertos: " + a.getNota());
                bw.newLine();
            }
        }

        // Arquivo por Nota Decrescente
        //função lambda pra comparar os alunos (a1 e a2) na ordem inversa (compare(a2,a1) )
        Collections.sort(alunos, (a1, a2) -> Integer.compare(a2.getNota(), a1.getNota()));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeDisciplina + "_por_nota.txt"))) {
            for (Aluno aluno : alunos) {
                bw.write(aluno.getNome() + "\t" + aluno.getNota() + " acertos");
                bw.newLine();
            }
            bw.newLine(); // Linha em branco
            // String.format é ótimo para formatar números
            bw.write(String.format("Média da turma: %.2f", media));
        }
    }
}