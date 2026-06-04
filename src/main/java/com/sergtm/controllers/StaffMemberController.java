package com.sergtm.controllers;

import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.entities.StaffMember;
import com.sergtm.service.IPersonService;
import com.sergtm.service.IStaffMemberService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/staffMember")
public class StaffMemberController {
    @Resource
    private IStaffMemberService staffMemberService;
    @Resource
    private IPersonService personService;

    @RequestMapping(path = "/add", method = RequestMethod.GET, produces = "application/json")
    public StaffMember addStaffMemberByUsernameAndPersonName(String username, String firstName, String lastName) {
        Person person = personService.getByName(firstName, lastName);
        return staffMemberService.addStaffMemberByUsernameAndPersonName(person, username);
    }

    @RequestMapping(path = "/addPatient", method = RequestMethod.GET, produces = "application/json")
    public StaffMember addPatientForStaffMember(Authentication authentication, String firstName, String lastName) {
        Person person = personService.getByName(firstName, lastName);
        return staffMemberService.addPatientForStaffMember(person, authentication.getName());
    }
}
