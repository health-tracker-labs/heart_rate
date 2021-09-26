package com.sergtm.dao.impl;

import com.sergtm.dao.IStaffMemberDao;
import com.sergtm.entities.StaffMember;
import com.sergtm.entities.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class StaffMemberDaoImpl implements IStaffMemberDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<StaffMember> getByUser(User user) {
        String sql = "FROM StaffMember s WHERE s.user.id = :user_id";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("user_id", user.getId());

        return query.getResultList().stream().findFirst();
    }

    @Override
    @Transactional
    public void save(StaffMember staffMember) {
        sessionFactory.getCurrentSession().save(staffMember);
    }
}
