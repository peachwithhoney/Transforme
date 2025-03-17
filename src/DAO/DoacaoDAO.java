package DAO;

import classes.Doacao;
import java.util.ArrayList;
import java.util.List;

public class DoacaoDAO {

    private static final List<Doacao> doacoes = new ArrayList<>();

    public static boolean registrarDoacao(Doacao doacao) {
        return doacoes.add(doacao);
    }

    public static List<Doacao> listarDoacoes() {
        return new ArrayList<>(doacoes);
    }

    public static List<Doacao> listarDoacoesPorProjeto(int idProjeto) {
        List<Doacao> doacoesProjeto = new ArrayList<>();
        for (Doacao d : doacoes) {
            if (d.getIdProjeto() == idProjeto) {
                doacoesProjeto.add(d);
            }
        }
        return doacoesProjeto;
    }

    public static double getTotalArrecadado(int idProjeto) {
        return doacoes.stream()
                      .filter(d -> d.getIdProjeto() == idProjeto)
                      .mapToDouble(Doacao::getValor)
                      .sum();
    }
}
