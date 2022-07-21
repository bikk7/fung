package com.example.zj.tool;

import com.example.zj.pojo.Analysis;
import com.example.zj.pojo.Mel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealFile {
    //获取分析结果
    public Analysis getAnalysisResult(String filename,String path_lung_file)
    {
        //转化为base64
        Base64Utils utils = Base64Utils.getInstance();
        String raw_audio=utils.file2Base64(new File(path_lung_file+filename));
//        String raw_audio=utils.file2Base64(new File("D:\\music\\"+filename));

        HttpHeaders headers = new HttpHeaders();
        // 请求类型
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //RestTemplate带参传的时候要用HttpEntity<?>对象传递
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("raw_audio", raw_audio);

        // 绑定请求体和头
        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(map, headers);
        RestTemplate restTmpl = new RestTemplate();
        // 请求服务器
        ResponseEntity<Analysis> userEntity = restTmpl.postForEntity(
                "http://129.204.188.223:8888/api/analysis", request, Analysis.class);
        // 获取响应体
        Analysis analysis = userEntity.getBody();
        // 获取响应头
        HttpHeaders respHeaders = userEntity.getHeaders();
        // 获取响应属性
        List<String> success = respHeaders.get("Date");
        // 响应的HTTP状态码
        int status = userEntity.getStatusCodeValue();
        return analysis;
    }
    //获取频谱图
    public String getMel(String filename,String path_lung_file,String path_spectrum_image)
    {
        //转化为base64
        Base64Utils utils = Base64Utils.getInstance();
//        String raw_audio=utils.file2Base64(new File("/data/tmp/"+filename));
        String raw_audio=utils.file2Base64(new File(path_lung_file+filename));
        HttpHeaders headers = new HttpHeaders();
        // 请求类型
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //RestTemplate带参传的时候要用HttpEntity<?>对象传递
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("raw_audio", raw_audio);

        // 绑定请求体和头
        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(map, headers);
        RestTemplate restTmpl = new RestTemplate();
        // 请求服务器
        ResponseEntity<Mel> userEntity = restTmpl.postForEntity(
                "http://129.204.188.223:8888/api/mel", request, Mel.class);
        // 获取响应体
        Mel mel = userEntity.getBody();
        // 获取响应头
        HttpHeaders respHeaders = userEntity.getHeaders();
        // 获取响应属性
        List<String> success = respHeaders.get("Date");
        // 响应的HTTP状态码
        int status = userEntity.getStatusCodeValue();
        String a[]=filename.split("\\.");
        String imagename=a[0]+".jpg";
//        utils.base64ToFile(mel.getImgage(), new File("/data/lung_image/"+imagename));
        utils.base64ToFile(mel.getImgage(), new File(path_spectrum_image+imagename));
        return imagename;
    }
}
