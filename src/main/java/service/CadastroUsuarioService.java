package service;

import java.io.Serializable;

import javax.inject.Inject;

import model.Usuario;
import repository.UsuarioRepository;
import util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarios;

	@Transactional
	public Usuario salvar(Usuario usuario) {

		Usuario usuarioExistente = this.usuarios.porEmail(usuario.getEmail());

		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			throw new NegocioException("Já existe um usuário com o e-mail informado.");
		}

		return this.usuarios.guardar(usuario);
	}

}
