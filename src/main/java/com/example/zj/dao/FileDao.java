package com.example.zj.dao;

import com.example.zj.pojo.File;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDao {
public List<File> getFile(String p_id);
public List<File> getFileAll();
public void insertFileMessage(File file);
}
