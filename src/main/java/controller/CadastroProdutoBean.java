package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import model.Categoria;
import model.Produto;
import repository.CategoriaRepository;
import service.CadastroProdutoService;
import util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;

	@NotNull
	private Categoria categoriaPai;

	@Inject
	CadastroProdutoService cadastroProdutoService;

	private List<Categoria> categoriasRaizes;
	private List<Categoria> subCategorias;

	@Inject
	private CategoriaRepository categorias;

	public CadastroProdutoBean() {
		limpar();
	}

	public List<Categoria> getCategoriasRaizes() {
		return this.categoriasRaizes;
	}

	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			this.categoriasRaizes = this.categorias.raizes();

			if (this.categoriaPai != null) {
				carregarSubcategorias();
			}
		}
	}

	private void limpar() {
		this.produto = new Produto();
		this.categoriaPai = null;
		this.subCategorias = new ArrayList<Categoria>();
	}

	public void salvar() {
		this.cadastroProdutoService.salvar(this.produto);
		limpar();
		FacesUtil.addInfoMessage("Produto salvo com sucesso!");
	}

	public void carregarSubcategorias() {
		this.subCategorias = this.categorias.subcategoriasDe(this.categoriaPai);
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
		if (this.produto != null) {
			this.categoriaPai = this.produto.getCategoria().getCategoriaPai();
		}
	}

	public Categoria getCategoriaPai() {
		return this.categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public List<Categoria> getSubCategorias() {
		return this.subCategorias;
	}

	public boolean isEditando() {
		return this.produto.getId() != null;
	}

}
