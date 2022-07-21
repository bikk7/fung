package com.example.zj.service.impl;

import com.example.zj.dao.DocterDao;
import com.example.zj.pojo.Doctor;
import com.example.zj.service.DocterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocterServiceImpl implements DocterService {
    @Autowired
    DocterDao docterDao=null;
    @Override
    public boolean enter(Doctor doctor) {
        return docterDao.enter(doctor);
    }

    @Override
    public List<Doctor> getPatientMessage(String d_id) {
        return docterDao.getPatientMessage(d_id);
    }
}
