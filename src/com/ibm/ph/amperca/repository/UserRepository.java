package com.ibm.ph.amperca.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.ibm.ph.amperca.model.User;

@Stateless
public class UserRepository {

	// Represent first level cache

	@PersistenceContext(unitName = "user-api")
	EntityManager em;

	public UserRepository() {
	}

	public String getName() {
		return "Charles Amper";
	}

	public Long countUser(String searchField) {
		Query query = null;
		if (searchField == null || searchField.isEmpty()) {
			query = em
					.createQuery("SELECT COUNT(f.id) FROM User f", User.class);
		} else {
			query = em.createQuery(
							"SELECT COUNT(f.id) FROM User f WHERE f.firstName LIKE :searchField OR f.lastName LIKE :searchField",
							User.class);
			query.setParameter("searchField", "%" + searchField + "%");
		}

		return (Long) query.getSingleResult();
	}

	public User add(User user) {
		em.persist(user);
		return user;
	}

	public User findById(Long id) {
		if (id == null) {
			return null;
		}
		return em.find(User.class, id);
	}

	public User update(Long id, User user) {
		user.setId(id);
		User s = em.find(User.class, id);
		if (s == null) {
			return null;
		}
		em.merge(user);

		return s;
	}

	public List<User> getUsers(int startPosition, int maxResults,
			String sortFields, String sortDirections, String searchField) {

		TypedQuery<User> query = null;
		/*
		 * Note:
		 * Need to refactor the sorting fields to follow best practices 
		 * 
		 * 
		 * */
		if (searchField == null || searchField.isEmpty()) {
			query = em.createQuery("SELECT f FROM User f ORDER BY f."
					+ sortFields + " " + sortDirections, User.class);
		} else {
			query = em.createQuery("SELECT f FROM User f WHERE f.firstName LIKE :searchField OR f.lastName LIKE :searchField ORDER BY f."
									+ sortFields + " " + sortDirections,
							User.class);
			query.setParameter("searchField", "%" + searchField + "%");
		}

		query.setFirstResult(startPosition);
		query.setMaxResults(maxResults);
		List<User> results = query.getResultList();
		return results;
	}

	public void deleteUser(User user) {
		em.remove(user);
	}
}
