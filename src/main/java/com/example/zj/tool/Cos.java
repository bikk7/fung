package com.example.zj.tool;

import com.example.zj.service.FileService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Cos {

   
    // 将本地文件上传到COS
    public static void SimpleUploadFileFromLocal(com.example.zj.pojo.File file, String lung_file,String path_lung_file,String spectrum_file,String path_spectrum_file,String ct_file,String path_ct_file,FileService fileService) {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        //上传肺音文件
        File localFile = new File(path_lung_file+lung_file);
        String key = file.getP_id()+"/"+"Lung_file"+"/"+localFile.getName();
        file.setLung_file(web+key);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);

        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard);
        try {
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();
            String crc64 = putObjectResult.getCrc64Ecma();
            System.out.println(etag);
            System.out.println(crc64);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }

        //上传频谱图
        File localFile1 = new File(path_spectrum_file+spectrum_file);
        String key1 = file.getP_id()+"/"+"Spectrum_file"+"/"+localFile1.getName();
        file.setSpectrum_image(web+key1);
        PutObjectRequest putObjectRequest1 = new PutObjectRequest(bucketName, key1, localFile1);

        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest1.setStorageClass(StorageClass.Standard);
        try {
            PutObjectResult putObjectResult1 = cosclient.putObject(putObjectRequest1);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult1.getETag();
            String crc64 = putObjectResult1.getCrc64Ecma();
            System.out.println();
            System.out.println(etag);
            System.out.println(crc64);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }

        //上传ct图
        File localFile2 = new File(path_ct_file+ct_file);
        String key2 = file.getP_id()+"/"+"Ct_file"+"/"+localFile2.getName();
        file.setCt_image(web+key2);
        PutObjectRequest putObjectRequest2 = new PutObjectRequest(bucketName, key2, localFile2);

        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest2.setStorageClass(StorageClass.Standard);
        try {
            PutObjectResult putObjectResult2 = cosclient.putObject(putObjectRequest2);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult2.getETag();
            String crc64 = putObjectResult2.getCrc64Ecma();
            System.out.println();
            System.out.println(etag);
            System.out.println(crc64);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }

        //插入文件信息到数据库
        fileService.insertFileMessage(file);
        // 关闭客户端
        cosclient.shutdown();
    }

    // 从输入流进行读取并上传到COS
    public static void SimpleUploadFileFromStream(com.example.zj.pojo.File file, String filename,String imagename,String pathlung,String pathimage,FileService fileService) {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String key = "aaa/bbb.mp3";


        InputStream input = new ByteArrayInputStream(new byte[10]);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(10);
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
        objectMetadata.setContentType("image/mp3");

        PutObjectRequest putObjectRequest =
                new PutObjectRequest("zj-1303075044", key, input, objectMetadata);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard);
        try {
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();
            String crc64 = putObjectResult.getCrc64Ecma();
            System.out.println();
            System.out.println(etag);
            System.out.println(crc64);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }

        // 关闭客户端
        cosclient.shutdown();
    }
    //查找存储桶
    public static void SerchBucket()
    {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        List<Bucket> buckets = cosclient.listBuckets();
        for (Bucket bucketElement : buckets) {
            String bucketName = bucketElement.getName();
            String bucketLocation = bucketElement.getLocation();
            System.out.println(bucketName);
            System.out.println(bucketLocation);
        }
    }
    //查找存储桶中的内容
    public static void SerchContext(String p_id, Map<String,String> map)
    {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
// 设置bucket名称
        listObjectsRequest.setBucketName(bucketName);
// prefix表示列出的object的key以prefix开始
        listObjectsRequest.setPrefix(p_id+"/");
// deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        listObjectsRequest.setDelimiter("/");
// 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        do {
            try {
                objectListing = cosclient.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return ;
            } catch (CosClientException e) {
                e.printStackTrace();
                return ;
            }
            // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
            List<String> commonPrefixs = objectListing.getCommonPrefixes();

            // object summary表示所有列出的object列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            if(cosObjectSummaries.size()==0)
                System.out.println("没有文件");
            else
            {
                System.out.println("一共有"+cosObjectSummaries.size()+"个文件");
                for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                    // 文件的路径key
                    String key = cosObjectSummary.getKey();
                    System.out.println("文件的路径:"+key);
                    map.put(key,web+key);
                    // 文件的etag
                    String etag = cosObjectSummary.getETag();
                    System.out.println("文件的etag:"+etag);
                    // 文件的长度
                    long fileSize = cosObjectSummary.getSize();
                    System.out.println("文件的长度:"+fileSize);
                    // 文件的存储类型
                    String storageClasses = cosObjectSummary.getStorageClass();
                    System.out.println("文件的存储类型:"+storageClasses);
                    System.out.println();
                }
            }


            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());
    }
   //下载文件到本地
    public static boolean DownFile(String[] filename,String path)
    {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        for(String s: filename) {
            String key = s.substring(s.indexOf(Cos.web));
            String outputFilePath = path+"\\" + s;
            File downFile = new File(outputFilePath);
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
            ObjectMetadata downObjectMeta = cosclient.getObject(getObjectRequest, downFile);
        }
        return true;
    }



}
