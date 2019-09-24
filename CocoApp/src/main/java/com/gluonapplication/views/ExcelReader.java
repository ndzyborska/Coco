package com.gluonapplication.views;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class ExcelReader{

    private Workbook workbook;

    ExcelReader(String myData) throws IOException,InvalidFormatException{
        workbook = WorkbookFactory.create(new File(myData));

    }

    String getImage(String id, int sheetNo){


        DataFormatter dataFormatter = new DataFormatter();
        Sheet sheet=workbook.getSheetAt(sheetNo);
        for(Row row:sheet){
            if(dataFormatter.formatCellValue(row.getCell(0)).equals(id)){
                return dataFormatter.formatCellValue(row.getCell(2));
            }
        }
        return "Image load Error";
    }

    String getName(String id, int sheetNo){
        DataFormatter dataFormatter=new DataFormatter();
        Sheet sheet=workbook.getSheetAt(sheetNo);
        for(Row row:sheet){
            if(dataFormatter.formatCellValue(row.getCell(0)).equals(id)){
                return dataFormatter.formatCellValue(row.getCell(1));
            }
        }

        return "Name load Error";
    }
    int getAmount(int sheetNo) {
        return workbook.getSheetAt(sheetNo).getLastRowNum();

    }

    ArrayList<Ingredients> getFood(String buttonId) {

        System.out.println("EXCEL READER: " + buttonId);

        ArrayList<Ingredients> food = new ArrayList<>();
        DataFormatter d = new DataFormatter();
        Sheet sheet;
        sheet = workbook.getSheet(buttonId);

        for (Row row : sheet) {
            try {
                food.add(new Ingredients(d.formatCellValue(row.getCell(0)),
                        d.formatCellValue(row.getCell(1)),
                        d.formatCellValue(row.getCell(2))));

            } catch (NullPointerException e) {
                System.out.print("Sheet not filled!") ;
            }

        }
        return food;

    }


    void close()throws IOException {
        workbook.close();

    }

}

