package com.example.zj.service;

import com.example.zj.pojo.File;

import java.util.List;

public interface FileService {
    public List<File> getFile(String p_id);
    public List<File> getFileAll();
    public void insertFileMessage(File file);
}
