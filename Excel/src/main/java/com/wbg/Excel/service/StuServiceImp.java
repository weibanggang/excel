package com.wbg.Excel.service;

import com.wbg.Excel.dao.StuMapper;
import com.wbg.Excel.entity.Stu;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StuServiceImp implements StuService{

    @Autowired
    private StuMapper stuMapper;
    @Override
    public List<Stu> listAll() {
        return stuMapper.selectAll();
    }

    @Override
    public byte[] exportToExcel(List<Stu> list) throws IOException {
        HSSFWorkbook hssfWorkbook=new HSSFWorkbook();
        HSSFSheet hfw=hssfWorkbook.createSheet("table");
        CellStyle cellStyle=hssfWorkbook.createCellStyle();
        cellStyle.setDataFormat(hssfWorkbook.getCreationHelper().createDataFormat().getFormat("yyyy-MM-dd"));
        HSSFRow th=hfw.createRow(0);
        String [] ths={"编号","姓名","性别","学历","月薪" };
        for (int i = 0; i <ths.length ; i++) {
            th.createCell(i).setCellValue(ths[i]);
        }
        for (int i = 0; i <list.size(); i++) {
            //行
            HSSFRow row=hfw.createRow(i+1);
            row.createCell(0).setCellValue(list.get(i).getsId());
            row.createCell(1).setCellValue(list.get(i).getsName());
            row.createCell(2).setCellValue(list.get(i).getsSex());
            row.createCell(3).setCellValue(list.get(i).getsEducation());
            row.createCell(4).setCellValue(list.get(i).getsSalary().doubleValue());
        }

        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        hssfWorkbook.write(bos);
        return bos.toByteArray();
    }

    @Override
    public int insert(List<Stu> list) {

        return 0;
    }

    @Override
    public List<Stu> inputToExcel(InputStream inputStream) throws IOException {
        List<Stu> list = new ArrayList<>();
        boolean flag = false;
        try {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
            for (Sheet rows : hssfWorkbook) {
                for (Row row : rows) {
                    //去掉表头
                    if (!flag) {
                        flag = true;
                        continue;
                    }
                    Stu stu = new Stu();
                    stu.setsId(row.getCell(0).toString());
                    stu.setsName(row.getCell(1).toString());
                    stu.setsSex(row.getCell(2).toString());
                    stu.setsEducation(row.getCell(3).toString());
                    stu.setsSalary(new BigDecimal(row.getCell(4).toString()));
                    list.add(stu);
                }
            }
        } catch (IOException e) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            for (Sheet rows : xssfWorkbook) {
                for (Row row : rows) {
                    //去掉表头
                    if (!flag) {
                        flag = true;
                        continue;
                    }
                    Stu stu = new Stu();
                    stu.setsId(row.getCell(0).toString());
                    stu.setsName(row.getCell(1).toString());
                    stu.setsSex(row.getCell(2).toString());
                    stu.setsEducation(row.getCell(3).toString());
                    stu.setsSalary(new BigDecimal(row.getCell(4).toString()));
                    list.add(stu);
                }
            }
        }
        return list;
    }
}
