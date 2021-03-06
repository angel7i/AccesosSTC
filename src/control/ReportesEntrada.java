package control;

import model.Bateriaentrada;
import model.Torniqueteentrada;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ReportesEntrada", urlPatterns = "/reporteEntrada")
public class ReportesEntrada extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            Date from = toDate(req.getParameter("from"));
            Date to = toDate(req.getParameter("to"));
            List list = TorniquetesDAO.getEntradas(from, to);

            if (list != null)
            {
                String range = req.getParameter("from").split(" ")[0].replace("/", "") + "-" + req.getParameter("to").split(" ")[0].replace("/", "");
                Excel.doExcelEntradas(list, getServletContext().getRealPath("/"), range);
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
                Bateriaentrada b = (Bateriaentrada) o;

                for (Torniqueteentrada t : b.getTorniqueteentradas())
                {
                    JsonObject object = Json.createObjectBuilder().
                            add("torniq", t.getTorniquete()).
                            add("fase", t.getFase()).
                            add("boleto", t.getEntradaBoleto()).
                            add("tarjeta", t.getEntradaTarjeta()).
                            add("noautorizado", t.getNoAutorizado()).
                            add("total", t.getEntradaBoleto() + t.getEntradaTarjeta() + t.getNoAutorizado()).
                            add("estado", estado(t.getEstado())).
                            add("fecha", fecha(b.getFecha())).build();
                    array.add(object);
                }
            }

            TorniquetesDAO.close();
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
