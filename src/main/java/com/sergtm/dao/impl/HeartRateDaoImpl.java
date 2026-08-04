package com.sergtm.dao.impl;

import com.sergtm.dao.IHeartRateDao;
import com.sergtm.entities.HeartRate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Sergey on 18.07.2017.
 */
@Repository
public class HeartRateDaoImpl implements IHeartRateDao {
	private static final String FIND_BY_DATE_RANGE_AND_PERSON_ID = "FROM HeartRate h where h.date between :from and :to and h.person.id = :id order by h.date";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void addHeartRate(HeartRate heartRate) {
		entityManager.persist(heartRate);
	}

	@Override
	public HeartRate findById(Long id) {
		return entityManager.find(HeartRate.class, id);
	}

	@Override
	@Transactional
	public void deleteHeartRate(HeartRate heartRate) {
		entityManager.remove(heartRate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<HeartRate> getByPage(int firstResult, int maxResult) {
		final String sql = "FROM HeartRate h order by h.date DESC";
		Query query = entityManager.createQuery(sql);

		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<HeartRate> findHeartRatesByDateRangeAndPerson(Date from,
			Date to, Long personId) {
		Query query = entityManager.createQuery(FIND_BY_DATE_RANGE_AND_PERSON_ID);

		query.setParameter("from", from, TemporalType.TIMESTAMP);
		query.setParameter("to", to, TemporalType.TIMESTAMP);
		query.setParameter("id", personId);

		return query.getResultList();
	}
}