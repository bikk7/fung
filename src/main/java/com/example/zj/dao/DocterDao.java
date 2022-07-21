package com.example.zj.dao;

import com.example.zj.pojo.Doctor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocterDao {
    public boolean enter(Doctor doctor);
    public List<Doctor> getPatientMessage(String d_id);
}
