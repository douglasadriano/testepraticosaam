package daoTest;

import testepraticosaam.dao.FuncionarioDAO;
import testepraticosaam.model.Funcionario;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FuncionarioDAOTest {
    public static void main(String[] args) {
        FuncionarioDAO dao = new FuncionarioDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // 1. Criar novo funcionário
            Funcionario funcionario = new Funcionario();
            funcionario.setNome("Maria Oliveira");
            funcionario.setDataAdmissao(sdf.parse("01/01/2023"));
            funcionario.setSalario(4500.00);
            funcionario.setStatus(true);

            if (dao.salvar(funcionario)) {
                System.out.println("Funcionário salvo com sucesso!");
            }

            // 2. Listar todos os funcionários
            List<Funcionario> funcionarios = dao.listar();
            System.out.println("Funcionários cadastrados:");
            for (Funcionario f : funcionarios) {
                System.out.println("ID: " + f.getId() +
                        " | Nome: " + f.getNome() +
                        " | Admissão: " + sdf.format(f.getDataAdmissao()) +
                        " | Salário: " + f.getSalario() +
                        " | Ativo: " + (f.isStatus() ? "Sim" : "Não"));
            }

            // 3. Atualizar funcionário
            if (!funcionarios.isEmpty()) {
                Funcionario f = funcionarios.get(0);
                f.setNome("Maria Atualizada");
                f.setSalario(5000.00);
                f.setStatus(false);

                if (dao.atualizar(f)) {
                    System.out.println("Funcionário atualizado com sucesso!");
                }
            }

            // 4. Deletar funcionário
            if (!funcionarios.isEmpty()) {
                int idParaExcluir = funcionarios.get(0).getId();
                if (dao.deletar(idParaExcluir)) {
                    System.out.println("Funcionário com ID " + idParaExcluir + " excluído com sucesso.");
                }
            }

        } catch (Exception e) {
            System.err.println("Erro nos testes: " + e.getMessage());
        }
    }
}