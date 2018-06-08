package com.sergtm.dao.impl;

import com.sergtm.dao.IPressureDao;
import com.sergtm.entities.Pressure;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Collection;

@Repository
@Transactional(readOnly = true)
public class PressureDaoImpl implements IPressureDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void deletePressure(Pressure pressure) {
        sessionFactory.getCurrentSession().delete(pressure);
    }

    @Override
    @Transactional
    public void addPressure(Pressure pressure) {
        sessionFactory.getCurrentSession().save(pressure);
    }

    @Override
    public Pressure getByDate(LocalDate date) {
        String sql = "FROM Pressure p WHERE p.date = :date";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("date", date);
        if (query.getResultList().size() != 0)
            return (Pressure) query.getResultList().get(0);
        return null;
    }

    @Override
    public Collection<Pressure> getAll() {
        String sql = "FROM Pressure";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return query.getResultList();
    }
}