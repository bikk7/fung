package com.example.zj.service;

import com.example.zj.pojo.Patient;

import java.util.List;

public interface PatientService {
    public List<Patient> getPatient(String d_id);
}
