package controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Produto;
import repository.ProdutoRepository;
import repository.filter.ProdutoFilter;
import util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Produto> produtosFiltrados;

	private Produto produtoSelecionado;

	@Inject
	private ProdutoRepository produtos;

	private ProdutoFilter filter;

	public PesquisaProdutosBean() {
		this.filter = new ProdutoFilter();
	}

	public void excluir() {
		this.produtos.remover(this.produtoSelecionado);
		this.produtosFiltrados.remove(this.produtoSelecionado);

		FacesUtil.addInfoMessage("Produto " + this.produtoSelecionado.getSku() + " excluído com sucesso.");
	}

	public void pesquisar() {
		this.produtosFiltrados = this.produtos.filtrados(this.filter);
	}

	public List<Produto> getProdutosFiltrados() {
		return this.produtosFiltrados;
	}

	public ProdutoFilter getFilter() {
		return this.filter;
	}

	public void setFilter(ProdutoFilter filter) {
		this.filter = filter;
	}

	public Produto getProdutoSelecionado() {
		return this.produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

}