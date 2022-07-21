package com.example.zj.tool;

import java.io.File;

public class TestBase64Utils {
    public static void main(String[] args) {
        //文件转base64
        Base64Utils utils = Base64Utils.getInstance();
        String str = utils.file2Base64(new File("F:\\音乐\\水手.mp3"));
        System.out.println(str);
        //base64转文件
        utils.base64ToFile(str, new File("F:\\音乐\\海贼王.mp3"));
    }
}
