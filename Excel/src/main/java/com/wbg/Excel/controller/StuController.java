package com.wbg.Excel.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.wbg.Excel.dao.StuMapper;
import com.wbg.Excel.entity.Stu;
import com.wbg.Excel.service.StuServiceImp;
import com.wbg.Excel.util.R;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class StuController {
    @Autowired
    private StuMapper stuMapper;

    static StuServiceImp stuServiceImp=new StuServiceImp();
    @RequestMapping("/index")
    public String index(Model model) {
        List<Stu> list = stuMapper.selectAll();
        model.addAttribute("list", list);
        return "index";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insert(@RequestBody List<Stu> list) {
        int sum = 0;
        int no = 0;
        R r = new R();
        try {
            for (Stu stu1 : list) {
                if (stuMapper.insert(stu1) > 0) {
                    sum++;
                } else {
                    no++;
                }
            }
            r.setMsg("成功导入数据:" + String.valueOf(sum) + "条，导入失败：" + no + "条");
            r.setCode(200);
        } catch (Exception e) {
            r.setMsg("数据导入有误,成功导入数据:" + String.valueOf(sum) + "条，导入失败：" + no + "条");
            return r.toJson();
        }
        return r.toJson();
    }

    @GetMapping(value = "/ss")
    ResponseEntity<String> sss() {

        HttpHeaders headers = null;
            return ResponseEntity.ok().headers(headers).body("");
    }


    @GetMapping(value = "/excel_download")
    ResponseEntity<byte[]> downloadFile() {

        HttpHeaders headers = null;
        byte[] bytes = null;
        try {
            bytes = stuServiceImp.exportToExcel(stuMapper.selectAll());
            headers = new HttpHeaders();
            headers.setCacheControl("no-cache, no-store, must-revalidate");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(bytes.length);
            headers.setContentDispositionFormData("attachment", "xxx_" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String uploadFile(@RequestPart("xxx") MultipartFile multipartFile, Model model, HttpServletRequest request) {

        R r = new R();
        int sum = 0;
        int no = 0;
        try {
            List<Stu> list=stuServiceImp.inputToExcel(multipartFile.getInputStream());

            r.setMsg("成功导入数据:" + String.valueOf(sum) + "条，导入失败：" + no + "条");
            r.setCode(200);
        } catch (Exception e) {
            r.setMsg("数据导入有误,成功导入数据:" + String.valueOf(sum) + "条，导入失败：" + no + "条");
            return r.toJson();
        }
        return r.toJson();
    }

}
