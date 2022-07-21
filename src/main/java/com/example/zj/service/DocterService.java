package com.example.zj.service;

import com.example.zj.pojo.Doctor;

import java.util.List;

public interface DocterService {
    public boolean enter(Doctor doctor);
    public List<Doctor> getPatientMessage(String d_id);
}
