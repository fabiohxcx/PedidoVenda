package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import model.Categoria;

public class CategoriaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Categoria porId(Long id) {
		return this.manager.find(Categoria.class, id);
	}

	public List<Categoria> raizes() {

		return this.manager.createQuery("from Categoria", Categoria.class).getResultList();

	}
}
