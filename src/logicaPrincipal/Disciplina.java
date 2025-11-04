package logicaPrincipal;

import java.util.ArrayList;

public class Disciplina {

    //Atributos básicos
    String nome;
    ArrayList<Aluno> alunos;
    
    //Getters, Setters e Construtor
    public Disciplina(String nome, ArrayList<Aluno> alunos) {
        this.nome = nome;
        this.alunos = alunos;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }
    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }

    //Métodos necessários
    public void AdicionarAluno(ArrayList<Aluno> alunos,Aluno a){
        alunos.add(a);
    }
}
