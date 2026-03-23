package com.sergtm.service.impl;

import com.sergtm.dao.IStaffMemberDao;
import com.sergtm.entities.Person;
import com.sergtm.entities.StaffMember;
import com.sergtm.entities.User;
import com.sergtm.service.IStaffMemberService;
import com.sergtm.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StaffMemberServiceImpl implements IStaffMemberService {
    @Resource
    private IStaffMemberDao staffMemberDao;
    @Resource
    private IUserService userService;

    @Override
    public StaffMember add(int userId, int personId) {
        return null;
    }

    @Override
    public StaffMember getByUser(User user) {
        Optional<StaffMember> staffMember = staffMemberDao.getByUser(user);
        return staffMember.orElse(null);
    }

    @Override
    @Transactional
    public StaffMember addStaffMemberByUsernameAndPersonName(Person person, String username) {
        User user = userService.findUserByUsername(username);

        StaffMember staffMember = new StaffMember();
        staffMember.setPerson(person);
        staffMember.setUser(user);

        staffMemberDao.save(staffMember);
        return staffMember;
    }

    @Override
    public StaffMember addPatientForStaffMember(Person person, String username) {
        User user = userService.findUserByUsername(username);

        StaffMember staffMember = staffMemberDao.getByUser(user).get();
        staffMember.getPatients().add(person);

        return staffMember;
    }

    @Override
    public void deleteByPerson(Person person) {
        staffMemberDao.deleteByPerson(person);
    }
}
