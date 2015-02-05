package service;

import java.io.Serializable;

import javax.inject.Inject;

import model.Cliente;
import repository.ClienteRepository;
import util.jpa.Transactional;

public class CadastroClienteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteRepository clientes;

	@Transactional
	public Cliente salvar(Cliente cliente) {

		Cliente clienteExistente = this.clientes.porDocReceitaFederal(cliente.getDocumentoReceitaFederal());

		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente com o Documento informado.");
		}

		return this.clientes.guardar(cliente);
	}

}
