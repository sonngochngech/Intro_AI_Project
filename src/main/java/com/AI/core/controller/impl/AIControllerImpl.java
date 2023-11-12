package com.AI.core.controller.impl;


import com.AI.core.controller.AIController;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;


@Controller
public class AIControllerImpl implements AIController {


    @Override
    public String home(Model model) {

        String hello="ssss";
        return "index";
    }

    @Override
    public void mapReadExcelData(MultipartFile excelDataFile)   {

        String[]  addressList;
        int size=0;

        try{
            Workbook workbook=new XSSFWorkbook(excelDataFile.getInputStream());
            Sheet worksheet=workbook.getSheetAt(0);
            Iterator<Row> rowIterator=worksheet.iterator();
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Cell cell=row.getCell(0);
                if(cell!=null){
                    switch (cell.getCellType()){
                        case STRING:

                    }
                }


            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
