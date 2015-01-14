package controller;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import model.Cliente;
import model.Endereco;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private Endereco endereco;

	public CadastroClienteBean() {
		this.cliente = new Cliente();
		this.endereco = new Endereco();
	}

	public void salvar() {

	}

	public void incluirEndereco() {

	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public Endereco getEndereco() {
		return this.endereco;
	}

}
