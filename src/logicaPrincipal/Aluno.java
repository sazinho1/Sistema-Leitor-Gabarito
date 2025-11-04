package logicaPrincipal;

public class Aluno {

    //Atributos básicos
    String nome;
    String respostas;
    int nota;

    //Getters, Setters e Construtor
    public void Aluno(String nome, String respostas, int nota){
        this.nome = nome;
        this.respostas =respostas;
        this.nota = nota;
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

}
