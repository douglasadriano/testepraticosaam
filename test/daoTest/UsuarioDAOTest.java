package daoTest;

import testepraticosaam.dao.UsuarioDAO;
import testepraticosaam.model.Usuario;
import testepraticosaam.util.CriptografiaUtil;
import java.util.List;

public class UsuarioDAOTest {
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();

        // 1. Criar novo usuário
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("João da Silva");
        novoUsuario.setEmail("joao@email.com");
        novoUsuario.setSenha(CriptografiaUtil.sha256("123456"));

        if (dao.salvar(novoUsuario)) {
            System.out.println("Usuário salvo com sucesso!");
        }

        // 2. Testar login
        Usuario usuarioLogado = dao.login("joao@email.com", CriptografiaUtil.sha256("123456"));
        if (usuarioLogado != null) {
            System.out.println("Login realizado! Bem-vindo, " + usuarioLogado.getNome());
        } else {
            System.out.println("Login falhou.");
        }

        // 3. Listar todos os usuários
        List<Usuario> usuarios = dao.listarTodos();
        System.out.println("Lista de usuários:");
        for (Usuario u : usuarios) {
            System.out.println("ID: " + u.getId() + " - Nome: " + u.getNome() + " - Email: " + u.getEmail());
        }

        // 4. Atualizar usuário
        if (!usuarios.isEmpty()) {
            Usuario user = usuarios.get(0);
            user.setNome("João Atualizado");
            user.setSenha(CriptografiaUtil.sha256("novaSenha123"));

            if (dao.atualizar(user)) {
                System.out.println("Usuário atualizado com sucesso.");
            }
        }

        // 5. Deletar usuário
        if (!usuarios.isEmpty()) {
            int idParaDeletar = usuarios.get(0).getId();
            if (dao.deletar(idParaDeletar)) {
                System.out.println("Usuário com ID " + idParaDeletar + " deletado com sucesso.");
            }
        }
    }
}