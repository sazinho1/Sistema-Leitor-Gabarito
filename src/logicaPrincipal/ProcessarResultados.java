package logicaPrincipal;

import java.util.ArrayList;

public class ProcessarResultados {

    private final String gabarito;


    public ProcessarResultados(String gabarito){
        this.gabarito = gabarito;
    }

    //Calcula a nota de um aluno individual (considerando a condição de todos V ou todos F)
    @SuppressWarnings("StringEquality") //so pra não mostrar o aviso q ta comparando strings com "=="
    public int calcularNota(String respostasAluno, String gabarito){
        int notafinal = 0;
        int tamanhoGabarito = gabarito.length();

        //Se ele responder tudo igual
        if(respostasAluno == "VVVVVVVVVV" || respostasAluno == "FFFFFFFFFF"){return 0;}
        
        //Caso ele tenha feito a prova direito, ele compara caractere por caractere e adiciona 1 ponto por cada resposta certa
        for(int i = 0; i < tamanhoGabarito; i++){
            if (respostasAluno.toUpperCase().charAt(i) == gabarito.toUpperCase().charAt(i)) {
                notafinal ++;
            }
        }
        return notafinal;
    }

    //Calcula a nota de cada aluno e atualiza ela no arraylist de alunos
    public void processarNotas (ArrayList<Aluno> alunos){
         for (Aluno a : alunos) {
            int nota = calcularNota(a.getRespostas(), gabarito);
            a.setNota(nota);
         }


    }

    //Calcula a média das notas dos alunos da disciplina
    public double calcularMedia(ArrayList<Aluno> alunos){
        int mediabruta = 0;
        for (Aluno a : alunos) {
            mediabruta =+ a.getNota();
        }
        return (mediabruta/alunos.size());
    }
}
