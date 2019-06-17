/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yourorghere;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
 
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
/**
 *
 * @author ����
 */
public class PriceCounter {
    
    
    public int GetPrice(String SearchName, int SheetNumb)
    {
        int Price=0;
        try
        {
            FileInputStream inputStream = new FileInputStream(new File("Price.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

            HSSFSheet sheet = workbook.getSheetAt(SheetNumb);
            Row NameRow = sheet.getRow(0);
            Row PriceRow = sheet.getRow(2);
            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                Cell cell = row.getCell(0);
                if (SearchName.equals(cell.getStringCellValue())) 
                { 
                    Cell cell2 = row.getCell(2);
                    Price = (int)cell2.getNumericCellValue();
                }
            }

        }
        catch (IOException ex) 
        {
                
        }
        return Price;

    }
    public String GetNameWithoutDirectory(String ImputName)
    {
        String Name = ImputName;
        String Char[];
        Char = Name.split("/");
        Name = Char[Char.length-1];
        Name = Name.replace(".stl", "");
        return Name;        
    }
    
    public String GetPriceInfo(Turnstile t, Reader r, Reader r2)
    {
        String TurnstilePrice = Integer.toString(GetPrice(GetNameWithoutDirectory(t.TurnstileToRead),0));
        String ReaderPrice = Integer.toString(GetPrice(GetNameWithoutDirectory(r.ReaderToRead),2));
        String ScannerPrice = Integer.toString(GetPrice("������ " + GetNameWithoutDirectory(r.Scanner),3));
        String TopLightPrice = Integer.toString(GetPrice("������� ��������",3));
        String BottomLightPrice = Integer.toString(GetPrice("������ ��������",3));
        String SupportPrice = Integer.toString(GetPrice(GetNameWithoutDirectory(t.SupportToRead),1));
        String CameraPrice = Integer.toString(GetPrice(GetNameWithoutDirectory(r.CameraToRead),3));

        String ReaderPrice2 = Integer.toString(GetPrice(GetNameWithoutDirectory(r2.ReaderToRead),2));
        String ScannerPrice2 = Integer.toString(GetPrice("������ " + GetNameWithoutDirectory(r2.Scanner),3));
        String CameraPrice2 = Integer.toString(GetPrice(GetNameWithoutDirectory(r2.CameraToRead),3));
        int TotalPrice = GetPrice(GetNameWithoutDirectory(t.TurnstileToRead),0)+GetPrice(GetNameWithoutDirectory(r.ReaderToRead),2)*2+GetPrice(GetNameWithoutDirectory(t.SupportToRead),1)*2;

        String OutputText;
        OutputText = GetNameWithoutDirectory(t.TurnstileToRead) + ": " + TurnstilePrice + " ������" + "\n";
        OutputText += GetNameWithoutDirectory(r.ReaderToRead) + ": " + ReaderPrice + " ������" + "\n";
        OutputText += GetNameWithoutDirectory(t.SupportToRead) + ": " + SupportPrice + " ������" + "\n";
        OutputText += GetNameWithoutDirectory(r2.ReaderToRead) + ": " + ReaderPrice2 + " ������" + "\n";
        OutputText += GetNameWithoutDirectory(t.SupportToRead) + ": " + SupportPrice + " ������" + "\n";
        if (r.Camera)
        {
            OutputText += GetNameWithoutDirectory(r.CameraToRead) + ": " + CameraPrice + " ������" + "\n";
            TotalPrice +=GetPrice(GetNameWithoutDirectory(r.CameraToRead),3);
        }
        if (r.BottomLight)
        {
            OutputText +="������ ��������: " + BottomLightPrice + " ������" + "\n";
            TotalPrice +=GetPrice("������ ��������",3);
        }
        if (r.TopLight)
        {
            OutputText +="������� ��������: " + TopLightPrice + " ������" + "\n";
            TotalPrice +=GetPrice("������� ��������",3);
        }
        if (r.WithScanner)
        {
            OutputText += "������ " + GetNameWithoutDirectory(r.Scanner) + ": " + ScannerPrice + " ������" + "\n";
            TotalPrice +=GetPrice("������ " + GetNameWithoutDirectory(r.Scanner),3);
        }
        if (r2.Camera)
        {
            OutputText += GetNameWithoutDirectory(r2.CameraToRead) + ": " + CameraPrice2 + " ������" + "\n";
            TotalPrice +=GetPrice(GetNameWithoutDirectory(r2.CameraToRead),3);
        }
        if (r2.BottomLight)
        {
            OutputText +="������ ��������: " + BottomLightPrice + " ������" + "\n";
            TotalPrice +=GetPrice("������ ��������",3);
        }
        if (r2.TopLight)
        {
            OutputText +="������� ��������: " + TopLightPrice + " ������" + "\n";
            TotalPrice +=GetPrice("������� ��������",3);
        }
        if (r2.WithScanner)
        {
            OutputText += "������ " + GetNameWithoutDirectory(r2.Scanner) + ": " + ScannerPrice2 + " ������" + "\n";
            TotalPrice +=GetPrice("������ " + GetNameWithoutDirectory(r2.Scanner),3);
        }
        OutputText += "�������� ����: " + TotalPrice;
        return OutputText;
    }
    public void MakeDocument()
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.xls","*.*");
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION )
        {
        }
    }
    
}
