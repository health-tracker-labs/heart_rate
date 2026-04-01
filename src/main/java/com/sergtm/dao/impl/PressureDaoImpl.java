package com.sergtm.dao.impl;

import com.sergtm.dao.IPressureDao;
import com.sergtm.entities.Pressure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Collection;

@Repository
@Transactional(readOnly = true)
public class PressureDaoImpl implements IPressureDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void deletePressure(Pressure pressure) {
        entityManager.remove(pressure);
    }

    @Override
    @Transactional
    public void addPressure(Pressure pressure) {
        entityManager.persist(pressure);
    }

    @Override
    public Pressure getByDate(LocalDate date) {
        String sql = "FROM Pressure p WHERE p.date = :date";
        Query query = entityManager.createQuery(sql);
        query.setParameter("date", date);
        if (query.getResultList().size() != 0)
            return (Pressure) query.getResultList().get(0);
        return null;
    }

    @Override
    public Collection<Pressure> getAll() {
        String sql = "FROM Pressure";
        Query query = entityManager.createQuery(sql);
        return query.getResultList();
    }
}