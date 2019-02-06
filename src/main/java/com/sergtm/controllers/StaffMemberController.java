package com.sergtm.controllers;

import com.sergtm.entities.StaffMember;
import com.sergtm.service.IStaffMemberService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("/staffMember")
public class StaffMemberController {
    @Resource
    private IStaffMemberService staffMemberService;

    @RequestMapping(path = "/add", method = RequestMethod.GET, produces = "application/json")
    public StaffMember addStaffMemberByUsernameAndPersonName(String username, String firstName, String lastName) {
        return staffMemberService.addStaffMemberByUsernameAndPersonName(username, firstName, lastName);
    }

    @RequestMapping(path = "/addPatient", method = RequestMethod.GET, produces = "application/json")
    public StaffMember addPatientForStaffMember(Authentication authentication, String firstName, String lastName) {
        return staffMemberService.addPatientForStaffMember(authentication.getName(), firstName, lastName);
    }
}
