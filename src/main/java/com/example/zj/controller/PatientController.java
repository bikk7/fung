package com.example.zj.controller;

import com.example.zj.pojo.Patient;
import com.example.zj.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class PatientController {
    @Autowired
    private PatientService patientService = null;
    //获取指定病人信息
    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/getPatient/{p_id}")
    public List<Patient> getPatient(@PathVariable("p_id") String p_id) {
        return patientService.getPatient(p_id);
    }
}
