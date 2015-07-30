package control;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "Estado", urlPatterns = "/estado")
public class Estado extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            Map<String, Object> trama = AccesoDAO.insertEntradas();

            if (trama != null)
            {
                resp.getWriter().print(json(trama));
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

    private JsonArray json(Map<String, Object> trama)
    {
        JsonArray array = null;

        JsonObject t1 = Json.createObjectBuilder().
            add("torniq", trama.get("T1N").toString()).
            add("boleto", Integer.parseInt(trama.get("B1").toString())).
            add("tarjeta", Integer.parseInt(trama.get("T1").toString())).
            add("total", Integer.parseInt(trama.get("B1").toString())
            + Integer.parseInt(trama.get("T1").toString())).
            add("estado", estado(Integer.parseInt(trama.get("ES1").toString()))).build();
        JsonObject t2 = Json.createObjectBuilder().
                add("torniq", trama.get("T2N").toString()).
                add("boleto", Integer.parseInt(trama.get("B2").toString())).
                add("tarjeta", Integer.parseInt(trama.get("T2").toString())).
                add("total", Integer.parseInt(trama.get("B2").toString())
                        + Integer.parseInt(trama.get("T2").toString())).
                add("estado", estado(Integer.parseInt(trama.get("ES2").toString()))).build();
        JsonObject t3 = Json.createObjectBuilder().
                add("torniq", trama.get("T3N").toString()).
                add("boleto", Integer.parseInt(trama.get("B3").toString())).
                add("tarjeta", Integer.parseInt(trama.get("T3").toString())).
                add("total", Integer.parseInt(trama.get("B3").toString())
                        + Integer.parseInt(trama.get("T3").toString())).
                add("estado", estado(Integer.parseInt(trama.get("ES3").toString()))).build();
        JsonObject t4 = Json.createObjectBuilder().
                add("torniq", trama.get("T4N").toString()).
                add("boleto", Integer.parseInt(trama.get("B4").toString())).
                add("tarjeta", Integer.parseInt(trama.get("T4").toString())).
                add("total", Integer.parseInt(trama.get("B4").toString())
                        + Integer.parseInt(trama.get("T4").toString())).
                add("estado", estado(Integer.parseInt(trama.get("ES4").toString()))).build();

        array =  Json.createArrayBuilder().add(t1).add(t2).add(t3).add(t4).build();

        return array;
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
                return "No funciona boleto";
            case 2:
                return "Funcionando";
        }

        return null;
    }
}
