package com.sergtm.dao;

import com.sergtm.entities.Help;

import java.util.Collection;
import java.util.List;

public interface IHelpDao {
    Collection<Help> findAll();
    List<Help> getHelpByName(String name);
    Collection<Help> getByTopic(String name, String topicName);
}
