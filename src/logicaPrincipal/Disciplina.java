package logicaPrincipal;

import java.util.ArrayList;

public class Disciplina {

    //Atributos básicos
    private String nome;
    private ArrayList<Aluno> alunos;
    
    //Getters, Setters e Construtor
    public Disciplina(String nome){
        this.nome = nome;
        this.alunos = new ArrayList<>();
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
    public void AdicionarAluno(Aluno a){
        this.alunos.add(a);
    }
}
