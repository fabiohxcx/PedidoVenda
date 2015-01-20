package service;

import java.io.Serializable;

import javax.inject.Inject;

import model.Produto;
import repository.ProdutoRepository;
import util.jpa.Transactional;

public class CadastroProdutoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ProdutoRepository produtos;

	@Transactional
	public Produto salvar(Produto produto) {
		Produto produtoExistente = this.produtos.porSku(produto.getSku());

		if (produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Já existe um produto com o SKU informado");
		}

		produto.setSku(produto.getSku().toUpperCase());

		return this.produtos.guardar(produto);
	}

}
