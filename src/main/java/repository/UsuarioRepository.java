package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import model.Usuario;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import service.NegocioException;
import util.jpa.Transactional;

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuario guardar(Usuario usuario) {
		return this.manager.merge(usuario);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(String nome) {

		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);

		if (StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
			// criteria troca td pra minusculo
		}

		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Usuario porEmail(String email) {
		try {
			return this.manager.createQuery("from Usuario where upper(email) = :email", Usuario.class)
					.setParameter("email", email.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuario porId(Long id) {
		return this.manager.find(Usuario.class, id);
	}

	@Transactional
	public void remover(Usuario usuario) {
		try {
			usuario = porId(usuario.getId());
			this.manager.remove(usuario);
			this.manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Usuario não pode ser excluído.");
		}
	}

}
