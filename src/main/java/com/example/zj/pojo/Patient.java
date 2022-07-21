package com.example.zj.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Data
@Alias(value = "patient")
public class Patient {
    String d_id;//医生id
    String p_id;//病人id
    String p_Password;//病人登录密码
    String p_name;//病人姓名
    int p_age;//病人年龄
    String p_sex;//病人性别
    String p_diag;//诊断结果
    String p_hospNum;//住院号
    List<File> fileList;//文件列表
}
