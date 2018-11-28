package com.wbg.Excel.service;

import com.wbg.Excel.dao.StuMapper;
import com.wbg.Excel.entity.Stu;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface StuService {
    List<Stu> listAll();

    byte[] exportToExcel(List<Stu> list) throws IOException;
    int insert(List<Stu> list);
    List<Stu> inputToExcel(InputStream inputStream) throws IOException;
}
