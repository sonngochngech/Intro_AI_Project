package com.AI.core.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class ExcelHelper {

    public ArrayList<String> readExcelHelper(InputStream inputStream){
        ArrayList<String> list=new ArrayList<>();

        try{
            Workbook workbook=new XSSFWorkbook(inputStream);
            Sheet worksheet=workbook.getSheetAt(0);
            Iterator<Row> rowIterator=worksheet.iterator();
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Cell cell=row.getCell(0);
                if(cell!=null){
                    if(cell.getCellType()== CellType.STRING){
                        list.add(cell.getStringCellValue());
                    }else{
                        System.out.println("file is not right");
                    }
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }
}
