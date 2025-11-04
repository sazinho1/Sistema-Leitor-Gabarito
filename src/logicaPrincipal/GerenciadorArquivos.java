package logicaPrincipal;

import java.util.List;

public class GerenciadorArquivos {

    //Adiciona um aluno ao arquivo da disciplina 
    public void salvarRespostasAluno(String nomeDisciplina, String respostas, String nomeAluno){

    }

    // Um método para ler linhas de um arquivo
    public List<String> lerArquivo(String caminhoArquivo){

    }

    //Gera o arquivo de saída em ordem alfabetica
    public void salvarResultadosPorNome(List<Aluno> alunos, String nomeArquivo) {
        //(Use Collections.sort() com um Comparator para o nome)
    }

    //Gera o arquivo de saída por notas de modo decrescente
    public void salvarResultadosPorNota(List<Aluno> alunos, double media, String nomeArquivo){
        //(Use Collections.sort() com um Comparator para a nota, em ordem reversa) 
    }
}