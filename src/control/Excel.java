package control;

import model.Bateriaentrada;
import model.Bateriasalida;
import model.Torniqueteentrada;
import model.Torniquetesalida;
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
    public static String doExcelEntradas(List query, String path, String range)
    {
        String success = null;

        try
        {
            String pathFile = path + File.separator + "TorniquetesEntrada" + range + ".xls";
            File fileXLS = new File(pathFile);

            if (fileXLS.exists())
                fileXLS.delete();

            System.out.println(fileXLS.getAbsolutePath());

            HSSFWorkbook excel = new HSSFWorkbook();
            FileOutputStream file = new FileOutputStream(fileXLS);
            HSSFSheet page = excel.createSheet("Torniquetes");

            HSSFRow header = page.createRow(0);
            header.createCell(0).setCellValue("Torniquete");
            header.createCell(1).setCellValue("Boletos");
            header.createCell(2).setCellValue("Tarjeta");
            header.createCell(3).setCellValue("No Autorizado");
            header.createCell(4).setCellValue("Total");
            header.createCell(5).setCellValue("Estado");
            header.createCell(6).setCellValue("Fecha");

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
                    row.createCell(3).setCellValue(t.getNoAutorizado());
                    row.createCell(4).setCellValue(t.getEntradaBoleto() + t.getEntradaTarjeta() + t.getNoAutorizado());
                    row.createCell(5).setCellValue(estado(t.getEstado()));
                    row.createCell(6).setCellValue(fecha(b.getFecha()));
                }
            }

            for (Row r : page)
            {
                for (Cell c : r)
                {
                    c.setCellStyle(style);
                }
            }

            page.autoSizeColumn(5);
            page.autoSizeColumn(6);

            excel.write(file);
            file.close();
            TorniquetesDAO.close();

            success = fileXLS.getAbsolutePath();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return success;
    }

    public static String doExcelSalidas(List query, String path, String range)
    {
        String success = null;

        try
        {
            String pathFile = path + File.separator + "TorniquetesSalida" + range + ".xls";

            File fileXLS = new File(pathFile);

            if (fileXLS.exists())
                fileXLS.delete();

            System.out.println(fileXLS.getAbsolutePath());

            HSSFWorkbook excel = new HSSFWorkbook();
            FileOutputStream file = new FileOutputStream(fileXLS);
            HSSFSheet page = excel.createSheet("Torniquetes");

            HSSFRow header = page.createRow(0);
            header.createCell(0).setCellValue("Torniquete");
            header.createCell(1).setCellValue("Total");
            header.createCell(2).setCellValue("Fecha");

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
                Bateriasalida b = (Bateriasalida) o;

                for (Torniquetesalida t : b.getTorniquetesalidas())
                {
                    HSSFRow row = page.createRow(page.getLastRowNum() + 1);
                    row.createCell(0).setCellValue(t.getTorniquete());
                    row.createCell(1).setCellValue(t.getSalida());
                    row.createCell(2).setCellValue(fecha(b.getFecha()));
                }
            }

            for (Row r : page)
            {
                for (Cell c : r)
                {
                    c.setCellStyle(style);
                }
            }

            page.autoSizeColumn(2);

            excel.write(file);
            file.close();
            TorniquetesDAO.close();

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
