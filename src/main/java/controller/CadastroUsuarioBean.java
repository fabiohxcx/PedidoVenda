package controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Grupo;
import model.Usuario;
import repository.GrupoRepository;
import service.CadastroUsuarioService;
import service.NegocioException;
import util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GrupoRepository grupos;

	private Usuario usuario;
	private List<Grupo> gruposTela;
	private Grupo grupoAdicionado;

	@Inject
	private CadastroUsuarioService cadastroUsuarioService;

	public void inicializar() {
		this.gruposTela = this.grupos.todos();
	}

	public CadastroUsuarioBean() {
		limpar();

	}

	public void salvar() {
		this.usuario = this.cadastroUsuarioService.salvar(this.usuario);
		limpar();
		FacesUtil.addInfoMessage("Produto salvo com sucesso!");
	}

	public void excluirGrupo() {
		this.usuario.getGrupos().remove(this.grupoAdicionado);
	}

	private void limpar() {
		this.usuario = new Usuario();
	}

	public void adicionarGrupo() {
		List<Grupo> gruposDoUsuario = this.usuario.getGrupos();

		if (gruposDoUsuario != null) {
			for (int i = 0; i < gruposDoUsuario.size(); i++) {
				if (gruposDoUsuario.get(i).getNome().equals(this.grupoAdicionado.getNome())) {
					throw new NegocioException("Grupo já está adicionado");
				}
			}
		}
		this.usuario.getGrupos().add(this.grupoAdicionado);
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public List<Grupo> getGruposTela() {
		return this.gruposTela;
	}

	public Grupo getGrupoAdicionado() {
		return this.grupoAdicionado;
	}

	public void setGrupoAdicionado(Grupo grupoAdicionado) {
		this.grupoAdicionado = grupoAdicionado;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

}
