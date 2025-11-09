package logicaPrincipal;

import java.util.ArrayList;

public class ProcessarResultados {

    private final String gabarito;


    public ProcessarResultados(String gabarito){
        if (gabarito == null || !gabarito.matches("^[VFvf]{10}$")) {
        // Se o gabarito não estiver no formato correto, lançamos uma exceção
        throw new IllegalArgumentException("O Gabarito selecionado é inválido. Deve conter exatamente 10 caracteres, sendo apenas V ou F.");
        }   
        this.gabarito = gabarito.toUpperCase();
    }

    //Calcula a nota de um aluno individual (considerando a condição de todos V ou todos F)
    public int calcularNota(String respostasAluno, String gabarito){
        int notafinal = 0;
        int tamanhoGabarito = gabarito.length();

        //Se ele responder tudo igual
        if(respostasAluno.toUpperCase().equals("VVVVVVVVVV") || respostasAluno.toUpperCase().equals("FFFFFFFFFF")) {
            return 0;
        }
        
        //Caso ele tenha feito a prova direito, ele compara caractere por caractere e adiciona 1 ponto por cada resposta certa
        for(int i = 0; i < tamanhoGabarito; i++) {
            if (respostasAluno.toUpperCase().charAt(i) == gabarito.toUpperCase().charAt(i)) {
                notafinal ++;
            }
        }
        return notafinal;
    }

    //Calcula a nota de cada aluno e atualiza ela no arraylist de alunos
    public void processarNotas (ArrayList<Aluno> alunos){
         for (Aluno a : alunos) {
            int nota = calcularNota(a.getRespostas().toUpperCase(), gabarito);
            a.setNota(nota);
        }
    }

    //Calcula a média das notas dos alunos da disciplina
    public double calcularMedia(ArrayList<Aluno> alunos){
        int mediabruta = 0;
        for (Aluno a : alunos) {
            mediabruta += a.getNota();
        }
        
        //Fazendo um cast dos numeros pra que o resultado da divisão no final dê um double
        double mediadouble = mediabruta;
        double qtdAlunos = alunos.size();

        return (mediadouble/qtdAlunos);
    }
}
