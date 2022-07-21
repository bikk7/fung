package com.example.zj.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias(value ="doctor")
@Data
public class Doctor {
     String d_id;//医生id
     String d_Password;//登录密码
     String d_name;//医生姓名
     String d_institution;//医生机构
     List<Patient> patientList;//该医生的病人列表
}
