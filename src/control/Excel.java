package control;

import model.Bateriaentrada;
import model.Torniqueteentrada;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Excel
{
    public static String doExcel(List query)
    {
        String success = null;

        try
        {
            String pathFile = System.getProperty("user.home") +
                    File.separator + "Downloads" + File.separator +
                    "ReporteTorniquetes.xls";
            File fileXLS = new File(pathFile);

            if (fileXLS.exists())
                fileXLS.delete();

            HSSFWorkbook excel = new HSSFWorkbook();
            FileOutputStream file = new FileOutputStream(fileXLS);
            HSSFSheet page = excel.createSheet("Torniquetes");

            HSSFRow header = page.createRow(0);
            header.createCell(0).setCellValue("Torniquete");
            header.createCell(1).setCellValue("Boletos");
            header.createCell(2).setCellValue("Tarjeta");
            header.createCell(3).setCellValue("Total");
            header.createCell(4).setCellValue("Entradas");
            header.createCell(5).setCellValue("Fecha");

            HSSFCellStyle style = excel.createCellStyle();
            style.setBorderBottom(CellStyle.BORDER_MEDIUM);
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderLeft(CellStyle.BORDER_MEDIUM);
            style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderRight(CellStyle.BORDER_MEDIUM);
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderTop(CellStyle.BORDER_MEDIUM);

            for (Object o : query)
            {
                Bateriaentrada b = (Bateriaentrada) o;

                for (Torniqueteentrada t : b.getTorniqueteentradas())
                {
                    HSSFRow row = page.createRow(page.getLastRowNum() + 1);
                    row.createCell(0).setCellValue(t.getTorniquete());
                    row.createCell(1).setCellValue(t.getEntradaBoleto());
                    row.createCell(2).setCellValue(t.getEntradaTarjeta());
                    row.createCell(3).setCellValue(t.getEntradaBoleto() + t.getEntradaTarjeta());
                    row.createCell(4).setCellValue(estado(t.getEstado()));
                    row.createCell(5).setCellValue(fecha(b.getFecha()));
                }
            }

            for (Row r : page)
            {
                for (Cell c : r)
                {
                    c.setCellStyle(style);
                }
            }

            page.autoSizeColumn(4);
            page.autoSizeColumn(5);

            excel.write(file);
            file.close();
            AccesosDAO.close();

            success = fileXLS.getAbsolutePath();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return success;
    }

    private static String fecha(Date d)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy' 'hh:mm:ss a");
        return format.format(d);
    }

    private static String estado(int e)
    {
        switch (e)
        {
            case 0:
                return "Error";
            case 1:
                return "Boleto inhabilitado";
            case 2:
                return "Habilitado";
        }

        return null;
    }
}
