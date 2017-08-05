package com.sergtm.dao;

import java.util.Collection;
import java.util.List;

public interface IHelpDao {
    Collection findAll();
    List getHelpByName(String name);
    Collection getByTopic(String name, String topicName);
}
