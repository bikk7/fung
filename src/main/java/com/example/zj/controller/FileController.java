package com.example.zj.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.example.zj.pojo.File;
import com.example.zj.service.FileService;
import com.example.zj.tool.Cos;
import com.example.zj.tool.DealFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**** imports ****/
@RestController
public class FileController {


    @Autowired
    private FileService fileService = null;
    /**
     * 打开文件上传请求页面
     * @return 指向JSP的字符串
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/upload/page")
    public ModelAndView doMenuEditUI(){
        ModelAndView  modelAndView= new ModelAndView("/file/upload");
        return modelAndView;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/insert")
    public ModelAndView doMenuEditU(){
        ModelAndView  modelAndView= new ModelAndView("insert");
        return modelAndView;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/upload")
    public Map<String, Object> uploadPart(@RequestParam("file") MultipartFile[] files,
                                          @RequestParam("p_id")String p_id,
                                          @RequestParam("f_id")String f_id,
                                          @RequestParam("collect_d_id")String collect_d_id,
                                          @RequestParam("state")int state,
                                          @RequestParam("collect_time")String collect_time,
                                          @RequestParam("collect_site")String collect_site,
                                          @RequestParam("lung_description")String lung_description,
                                          @RequestParam("ct_description")String ct_description) {
        //安卓上传的文件信息
        File file1=new File();
        file1.setP_id(p_id);
        file1.setF_id(f_id);
        file1.setCollect_d_id(collect_d_id);
        file1.setState(state);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String dateStr = collect_time;
        try {
            Date date = simpleDateFormat.parse(dateStr);
            file1.setCollect_time(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        file1.setCollect_site(collect_site);
        file1.setLung_description(lung_description);
        file1.setCt_description(ct_description);



        //肺音类型
        String lung_type="";
        //频谱图
        String spectrum_file="";
        //频谱图路径
//        String path_spectrum_file="D:\\music\\spectrum_file\\";
        String path_spectrum_file="/data/spectrum_file/";
        //肺音文件
        String lung_file="";
        //肺音文件路径
//        String path_lung_file="D:\\music\\lung_file\\";
        String path_lung_file="/data/lung_file/";
        //ct文件
        String ct_file="";
//        String path_ct_file="D:\\music\\ct_file\\";
        String path_ct_file="/data/ct_file/";

        try {
            //开始接收安卓上传的文件
            for (MultipartFile file : files) {
                //获取文件名
                String originalFilename = file.getOriginalFilename();
                //获取后缀
                String contentType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                try {
                    //保存文件
                    if (contentType.equals("wav")) {
                        //接收肺音文件
                        file.transferTo(new java.io.File(path_lung_file + originalFilename));
                        lung_file = originalFilename;
                        DealFile dealFile = new DealFile();
                        //获取肺音类型
                        lung_type = dealFile.getAnalysisResult(originalFilename, path_lung_file).getLung_type();
                        file1.setLung_type(lung_type);
                        //获取频谱图
                        spectrum_file = dealFile.getMel(originalFilename, path_lung_file, path_spectrum_file);
                    } else {
                        //接收ct图片
                        file.transferTo(new java.io.File(path_ct_file + originalFilename));
                        ct_file = originalFilename;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            //开始上传文件到cos，并更新数据库
            Cos.SimpleUploadFileFromLocal(file1,lung_file,path_lung_file,spectrum_file,path_spectrum_file,ct_file,path_ct_file,fileService);
        }catch (Exception e)
        {
            e.printStackTrace();
            return dealResultMap(false, "上传失败");
        }
        return dealResultMap(true, "上传成功");

//        // 获取提交文件名称
//        String fileName = file.getSubmittedFileName();
//
//        try {
//            // 写入文件
//            file.write(fileName);
//
//            DealFile dealFile=new DealFile();
//            //获取肺音类型
//            String lung_type=dealFile.getAnalysisResult(fileName).getLung_type();
//            file1.setLung_type(lung_type);
//            //获取频谱图
//           String imagename=dealFile.getMel(fileName);
//           Cos.SimpleUploadFileFromLocal(file1,fileName,imagename,"/data/tmp/","/data/lung_image/",fileService);
//
////            Cos.SimpleUploadFileFromLocal(file1,fileName,imagename,"D:\\music\\","D:\\music\\",fileService);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return dealResultMap(false, "上传失败");
//        }

    }



    //查看cos上有哪些文件
    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/getCosFile/{p_id}")
    public Map<String,String> getCosFile(@PathVariable("p_id") String p_id)
    {
        Map<String,String>map=new HashMap<String,String>();
        Cos.SerchContext(p_id,map);
        return map;
    }

    //下载文件
    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("download")
    public Map<String,Object> getCosFile(@PathVariable("url") String[] url,@PathVariable("path") String path)
    {
        try {
            Cos.DownFile(url,path);
        }catch (Exception e)
        {
            e.printStackTrace();
            return dealResultMap(false, "下载失败");
        }
        return dealResultMap(true, "下载成功");
    }

    // 处理上传文件结果
    private Map<String, Object> dealResultMap(boolean success, String msg) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", success);
        result.put("msg", msg);
        return result;
    }



    //获取指定就诊记录
    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/getFile/{p_id}")
    public List<com.example.zj.pojo.File> getFile(@PathVariable("p_id") String p_id) {
        return fileService.getFile(p_id);
    }

    //获取所有就诊记录
    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/getFileAll")
    public List<com.example.zj.pojo.File> getFileAll() {
        return fileService.getFileAll();
    }


    //插入就诊记录
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/insertFileMessage")
    public void insertFileMessage(@RequestBody com.example.zj.pojo.File file)
    {
        fileService.insertFileMessage(file);
    }


}
