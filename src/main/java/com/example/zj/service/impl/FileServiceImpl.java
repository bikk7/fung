package com.example.zj.service.impl;

import com.example.zj.dao.FileDao;
import com.example.zj.pojo.File;
import com.example.zj.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FileDao fileDao =null;
    @Override
    public List<File> getFile(String p_id) {
        return fileDao.getFile(p_id);
    }


    @Override
    public List<File> getFileAll() {
        return fileDao.getFileAll();
    }

    @Override
    public void insertFileMessage(File file) {
        fileDao.insertFileMessage(file);
    }


}
