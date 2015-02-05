package controller;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Cliente;
import model.Endereco;
import model.TipoPessoa;
import service.CadastroClienteService;
import util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroClienteService cadastroClienteService;

	private Cliente cliente;
	private Endereco endereco;

	public CadastroClienteBean() {
		limpar();
	}

	public void inicializar() {

	}

	public void salvar() {
		this.cliente = this.cadastroClienteService.salvar(this.cliente);
		limpar();
		FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
	}

	private void limpar() {
		this.cliente = new Cliente();
		this.cliente.setTipo(TipoPessoa.FISICA);
		this.endereco = new Endereco();
	}

	public void incluirEndereco() {

	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return this.endereco;
	}

	public TipoPessoa[] getTiposPessoas() {
		return TipoPessoa.values();
	}

	public boolean isEditando() {
		return this.cliente.getId() != null;
	}

}
