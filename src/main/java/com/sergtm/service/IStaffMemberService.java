package com.sergtm.service;

import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.entities.StaffMember;
import com.sergtm.health.tracker.persistence.entity.User;

public interface IStaffMemberService extends IDeletableByPersonService {
    StaffMember add(int userId, int personId);
    StaffMember getByUser(User user);
    StaffMember addStaffMemberByUsernameAndPersonName(Person person, String username);
    StaffMember addPatientForStaffMember(Person person, String username);
}
