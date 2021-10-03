package com.sergtm.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergtm.dao.IHelpDao;
import com.sergtm.entities.Help;
import com.sergtm.entities.IEntity;
import com.sergtm.service.IHeartRateService;

@RestController
@RequestMapping("/help")
public class HelpController {
    @Autowired
    IHelpDao helpDao;
    @Autowired
    IHeartRateService heartRateService;

    @RequestMapping(method = RequestMethod.GET, path = "getAll.json", produces = "application/json")
    public Collection<Help> getAllJSon(){
        return helpDao.findAll();
    }

    @RequestMapping(method =  RequestMethod.GET, path = "getByName.json", produces = "application/json")
    public List<Help> getHelpBybName(@RequestParam String query){
        if(query.contains("^")){
            query = query.replace('^', '%');
        }
        List<Help> help = helpDao.getHelpByName(query);
        return help;
    }

    @RequestMapping(method =  RequestMethod.GET, path = "getByTopic.json", produces = "application/json")
    public Collection<? extends IEntity> getHelpByTopic(@RequestParam String query, @RequestParam String topic){
        if(query.contains("^")){
            query = query.replace('^', '%');
        }
        Collection<? extends IEntity> helps = heartRateService.getHelp(query, topic);
        return helps;
    }
}
