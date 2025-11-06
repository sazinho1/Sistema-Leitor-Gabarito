
import java.util.ArrayList;
import logicaPrincipal.Aluno;
import logicaPrincipal.GerenciadorArquivos;
import logicaPrincipal.ProcessarResultados;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            GerenciadorArquivos g = new GerenciadorArquivos();
            String gabarito = g.lerGabarito("gabarito_teste.txt");
            ProcessarResultados p = new ProcessarResultados(gabarito);

            g.salvarRespostaAluno("POO", "Cico Doido", "VFVVFVFVVF");
            g.salvarRespostaAluno("POO", "Zumbi dos palmares", "vvvvvvvvvv");
            g.salvarRespostaAluno("POO", "Davi Brito", "VVfVfvfvvf");
            g.salvarRespostaAluno("POO", "Julio Cesar", "ffFFFfffFF");
            g.salvarRespostaAluno("POO", "Luiz inacio", "FFFFFFFFFF");

            
            ArrayList<Aluno> alunos = g.lerRespostasDisciplina("POO.txt");
            p.processarNotas(alunos);
            double media = p.calcularMedia(alunos);
            g.salvarResultadosPorNota(alunos, media, "POO");

            
        } catch (Exception e) {
            
        }
    }
}
