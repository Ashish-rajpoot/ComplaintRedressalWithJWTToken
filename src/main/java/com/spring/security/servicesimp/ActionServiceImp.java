package com.spring.security.servicesimp;

import com.spring.security.entity.Action;
import com.spring.security.repository.ActionRepository;
import com.spring.security.services.ActionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionServiceImp implements ActionService {
    final ActionRepository actionRepository;

    public ActionServiceImp(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }


    @Override
    public List<Action> getActionByComplaintId(int id) {
        List<Action> isComplaint = actionRepository.findByComplaintId(id);
        return isComplaint;
    }

    public Action addAction(Action action){
        return actionRepository.save(action);
    }
}
