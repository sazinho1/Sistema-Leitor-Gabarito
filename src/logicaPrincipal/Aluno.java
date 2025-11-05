package logicaPrincipal;

public class Aluno implements Comparable<Aluno>{

    //Atributos básicos
    private String nome;
    private String respostas;
    private int nota;

    //Getters, Setters e Construtor
    public Aluno(String nome, String respostas, int nota){
        this.nome = nome;
        this.respostas =respostas;
        this.nota = nota;
    }

    public Aluno(String nome, String respostas){
        this.nome = nome;
        this.respostas = respostas;
        this.nota = 0;
    }

    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getRespostas() {
        return respostas;
    }
    public void setRespostas(String repostas) {
        this.respostas = repostas;
    }
    public int getNota() {
        return nota;
    }
    public void setNota(int nota) {
        this.nota = nota;
    }

    //Para a ordenação do item 2 do trabalho
    @Override
    public int compareTo(Aluno outro){
        return this.nome.compareTo(outro.getNome());
    }
}
