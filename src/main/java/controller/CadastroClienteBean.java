package controller;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
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

	@Produces
	@ClienteEdicao
	private Cliente cliente;

	private Endereco endereco;

	int linha;

	public int getLinha() {
		return this.linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

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
		limpaEndereco();
	}

	public void limpaEndereco() {
		this.endereco = new Endereco();
	}

	public void incluirEndereco() {
		this.endereco.setCliente(this.cliente);
		this.cliente.getEnderecos().add(this.endereco);
		limpaEndereco();
	}

	public void atualizaEndereco() {
		this.cliente.getEnderecos().set(this.linha, this.endereco);
		limpaEndereco();
	}

	public void removeEndereco() {
		this.cliente.getEnderecos().remove(this.endereco);
		limpaEndereco();
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

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public TipoPessoa[] getTiposPessoas() {
		return TipoPessoa.values();
	}

	public boolean isEditando() {
		return this.cliente.getId() != null;
	}

	public boolean isEditandoEndereco() {
		return this.endereco.getId() != null;
	}

}
