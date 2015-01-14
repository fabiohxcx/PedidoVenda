package controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import model.Categoria;
import model.Produto;
import repository.CategoriaRepository;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;

	@NotNull
	private Categoria categoriaPai;

	private List<Categoria> categoriasRaizes;
	@Inject
	private CategoriaRepository categorias;

	public CadastroProdutoBean() {
		this.produto = new Produto();
	}

	public List<Categoria> getCategoriasRaizes() {
		return this.categoriasRaizes;
	}

	public void inicializar() {
		System.out.println("Inicializando");

		this.categoriasRaizes = this.categorias.raizes();
	}

	public void salvar() {
		System.out.println("Categoria pai selecionada: " + this.categoriaPai.getDescricao());
	}

	public Produto getProduto() {
		return this.produto;
	}

	public Categoria getCategoriaPai() {
		return this.categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

}
