package com.example.zj.service.impl;

import com.example.zj.dao.PatientDao;
import com.example.zj.pojo.Patient;
import com.example.zj.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientDao patientDao=null;
    @Override
    public List<Patient> getPatient(String d_id) {
        return patientDao.getPatient(d_id);
    }
}
