package testepraticosaam.dao;

import testepraticosaam.conexao.Conexao;
import testepraticosaam.model.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public boolean salvar(Funcionario f) {
        String sql = "INSERT INTO funcionarios (nome, data_admissao, salario, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, f.getNome());
            stmt.setDate(2, new java.sql.Date(f.getDataAdmissao().getTime()));
            stmt.setDouble(3, f.getSalario());
            stmt.setBoolean(4, f.isStatus());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar funcionário: " + e.getMessage());
            return false;
        }
    }

    public List<Funcionario> listar() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";

        try (Connection conn = Conexao.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setDataAdmissao(rs.getDate("data_admissao"));
                f.setSalario(rs.getDouble("salario"));
                f.setStatus(rs.getBoolean("status"));
                lista.add(f);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Funcionario f) {
        String sql = "UPDATE funcionarios SET nome = ?, data_admissao = ?, salario = ?, status = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, f.getNome());
            stmt.setDate(2, new java.sql.Date(f.getDataAdmissao().getTime()));
            stmt.setDouble(3, f.getSalario());
            stmt.setBoolean(4, f.isStatus());
            stmt.setInt(5, f.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM funcionarios WHERE id = ?";

        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir funcionário: " + e.getMessage());
            return false;
        }
    }
}