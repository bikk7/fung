package com.example.zj.controller;

import com.example.zj.pojo.Analysis;
import com.example.zj.pojo.Doctor;
import com.example.zj.pojo.Mel;
import com.example.zj.service.DocterService;
import com.example.zj.tool.Base64Utils;
import com.example.zj.tool.Cos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
@RestController
public class DoctorController {
    @Autowired
    private DocterService docterService = null;

    //登录
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/enter")
    public String enter(@RequestBody Doctor doctor) {
        String result1 = "false";
        if (docterService.enter(doctor))
            result1 = "true";
        return result1;
    }



    //获取医生病人信息
    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/getPatientMessage/{d_id}")
    public List<Map> getPatientMessage(@PathVariable("d_id") String d_id) {
        List<Doctor> list = docterService.getPatientMessage(d_id);
        List<Map> mapList=new LinkedList<Map>();
        for(Doctor doctor:list)
        {
            Map<String, String> map = new HashMap<String, String>();
            map.put("d_name",doctor.getD_name());
            map.put("d_institution",doctor.getD_institution());
            map.put("p_id",doctor.getPatientList().get(0).getP_id());
            map.put("p_name",doctor.getPatientList().get(0).getP_name());
            map.put("p_age",String.valueOf(doctor.getPatientList().get(0).getP_age()));
            map.put("p_sex",doctor.getPatientList().get(0).getP_sex());
            map.put("p_hospNum",doctor.getPatientList().get(0).getP_hospNum());
            mapList.add(map);
        }
        return mapList;
    }
}
