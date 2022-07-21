package com.example.zj.dao;

import com.example.zj.pojo.Patient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientDao {
    public List<Patient> getPatient(String d_id);
}
