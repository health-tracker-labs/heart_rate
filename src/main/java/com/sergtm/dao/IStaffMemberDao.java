package com.sergtm.dao;

import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.entities.StaffMember;
import com.sergtm.health.tracker.persistence.entity.User;

import java.util.Optional;

public interface IStaffMemberDao {
    Optional<StaffMember> getByUser(User user);
    void save(StaffMember staffMember);
    void deleteByPerson(Person person);
}
