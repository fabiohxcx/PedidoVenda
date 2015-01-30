package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import model.Usuario;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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

}
