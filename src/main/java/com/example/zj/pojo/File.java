package com.example.zj.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.util.Date;
@Data
@Alias(value = "file")
public class File {
    String p_id;//病人id
    String f_id;//文件id
    String collect_d_id;//采集的医生编号
    int state;//上传状态
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH-mm-ss")
    Date collect_time;//采集时间
    String collect_site;//采集部位
    String lung_file;//肺音听诊文件
    String lung_description;//肺音描述
    String lung_type;//肺音类型
    String spectrum_image;//肺音频谱图
    String ct_image;//ct照片
    String ct_description;//ct描述
}
