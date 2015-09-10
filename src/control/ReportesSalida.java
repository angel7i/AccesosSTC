package control;

import model.Bateriaentrada;
import model.Bateriasalida;
import model.Torniqueteentrada;
import model.Torniquetesalida;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ReportesSalida", urlPatterns = "/reporteSalida")
public class ReportesSalida extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            Date from = toDate(req.getParameter("from"));
            Date to = toDate(req.getParameter("to"));
            List list = AccesosDAO.getSalidas(from, to);

            if (list != null)
            {
//                Excel.doExcel(list);
                resp.getWriter().print(json(list));
            }
            else
            {
                JsonObject e = Json.createObjectBuilder().
                        add("error", "Error").build();
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

    private JsonArray json(List l)
    {
        JsonArrayBuilder array = null;

        try
        {
            array = Json.createArrayBuilder();

            for (Object o : l)
            {
                Bateriasalida b = (Bateriasalida) o;

                for (Torniquetesalida t : b.getTorniquetesalidas())
                {
                    JsonObject object = Json.createObjectBuilder().
                            add("torniq", t.getTorniquete()).
                            add("fase", t.getFase()).
                            add("salida", t.getSalida()).
                            add("fecha", fecha(b.getFecha())).build();
                    array.add(object);
                }
            }

            AccesosDAO.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return array.build();
    }

    private String fecha(Date d)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy" + "\n" +  "hh:mm:ss a");
        return format.format(d);
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
}
