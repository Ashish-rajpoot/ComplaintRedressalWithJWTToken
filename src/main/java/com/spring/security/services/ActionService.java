package com.spring.security.services;


import com.spring.security.entity.Action;

import java.util.List;

public interface ActionService  {

     List<Action> getActionByComplaintId(int id);

}
