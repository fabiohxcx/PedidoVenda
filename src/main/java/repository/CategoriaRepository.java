package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import model.Categoria;
import model.Produto;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import service.NegocioException;
import util.jpa.Transactional;

public class CategoriaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Categoria guardar(Categoria categoria) {
		return this.manager.merge(categoria);
	}

	public Categoria porId(Long id) {
		return this.manager.find(Categoria.class, id);
	}

	public List<Categoria> subcategoriasDe(Categoria categoriaPai) {
		return this.manager.createQuery("from Categoria where categoriaPai = :raiz", Categoria.class)
				.setParameter("raiz", categoriaPai).getResultList();
	}

	public List<Categoria> raizes() {
		return this.manager.createQuery("from Categoria where categoriaPai is null", Categoria.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Produto> filtrados(String nomeCategoria) {
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Categoria.class);

		if (StringUtils.isNotBlank(nomeCategoria)) {
			criteria.add(Restrictions.ilike("descricao", nomeCategoria, MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("categoriaPai")).list();
	}

	public Categoria porDescricao(String descricao) {
		try {
			return this.manager.createQuery("from Categoria where upper(descricao) = :descricao", Categoria.class)
					.setParameter("descricao", descricao.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public void remover(Categoria categoria) {
		try {
			categoria = porId(categoria.getId());
			this.manager.remove(categoria);
			this.manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Categoria não pode ser excluído.");
		}
	}
}
