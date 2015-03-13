package service;

import java.io.Serializable;

import javax.inject.Inject;

import model.Categoria;
import repository.CategoriaRepository;
import util.jpa.Transactional;

public class CadastroCategoriaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	CategoriaRepository categorias;

	@Transactional
	public Categoria salvar(Categoria categoria) {
		Categoria categoriaExistente = this.categorias.porDescricao(categoria.getDescricao());

		if (categoriaExistente != null && !categoriaExistente.equals(categoria)) {
			throw new NegocioException("Já existe uma categoria com a descrição informado");
		}

		return this.categorias.guardar(categoria);
	}

}
