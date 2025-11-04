package logicaPrincipal;

import java.util.ArrayList;

public class ProcessarResultados {
    
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

    // as notas no arquivo
    public ArrayList<Aluno> processarNotas (String arquivoDisciplina, String arquivoGabarito){

    }

    //Calcula a média das notas dos alunos da disciplina
    public double calcularMedia(ArrayList<Aluno> alunos){

    }
}
