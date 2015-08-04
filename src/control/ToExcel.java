package control;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import tables.Bateria;
import tables.Torniquete;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ToExcel", urlPatterns = "/toExcel")
public class ToExcel extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            Date from = toDate(req.getParameter("from"));
            Date to = toDate(req.getParameter("to"));

            List list = AccesoDAO.getEntradas(from, to);

            if (list != null)
            {
                if (doExcel(list, ""))
                {
                    System.out.println("OKExcel");
                    JsonObject e = Json.createObjectBuilder().
                            add("response", "OK").build();
                    resp.getWriter().print(e);
                }
                else
                {
                    System.out.println("ErrorExcel");
                    JsonObject e = Json.createObjectBuilder().
                            add("response", "Error").build();
                    resp.getWriter().print(e);
                }
            }
            else
            {
                System.out.println("NoQuery");
                JsonObject e = Json.createObjectBuilder().
                        add("response", "Error").build();
                resp.getWriter().print(e);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        doPost(req, resp);
    }

    private Date toDate(String s)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Date d = null;

        try
        {
            d = format.parse(s);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return  d;
    }

    private boolean doExcel(List query, String fileName)
    {
        boolean success = false;

        try
        {
            String pathFile = "ReporteTorniquetes.xls";
            File fileXLS = new File(pathFile);

            if(fileXLS.exists())
                fileXLS.delete();

            HSSFWorkbook excel = new HSSFWorkbook();
            FileOutputStream file = new FileOutputStream(fileXLS);
            HSSFSheet page = excel.createSheet("Torniquetes");

            HSSFRow header = page.createRow(0);
            header.createCell(0).setCellValue("Torniquete");
            header.createCell(1).setCellValue("Boletos");
            header.createCell(2).setCellValue("Tarjeta");
            header.createCell(3).setCellValue("Total");
            header.createCell(4).setCellValue("Estado");
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
                Bateria b = (Bateria) o;

                for (Torniquete t : b.getTorniquetes())
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
            AccesoDAO.close();

            success = true;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return success;
    }

    private String fecha(Date d)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy' 'hh:mm:ss a");
        return format.format(d);
    }

    private String estado(int e)
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
