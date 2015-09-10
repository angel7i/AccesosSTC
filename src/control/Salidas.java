package control;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "Salidas", urlPatterns = "/salidas")
public class Salidas extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        try
        {
            Map<String, Object> trama = AccesosDAO.insertSalidas();

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
                add("salida", Integer.parseInt(trama.get("S1").toString())).build();
        JsonObject t2 = Json.createObjectBuilder().
                add("torniq", trama.get("T2N").toString()).
                add("salida", Integer.parseInt(trama.get("S2").toString())).build();
        JsonObject t3 = Json.createObjectBuilder().
                add("torniq", trama.get("T3N").toString()).
                add("salida", Integer.parseInt(trama.get("S3").toString())).build();
        JsonObject t4 = Json.createObjectBuilder().
                add("torniq", trama.get("T4N").toString()).
                add("salida", Integer.parseInt(trama.get("S4").toString())).build();
        JsonObject t5 = Json.createObjectBuilder().
                add("torniq", trama.get("T5N").toString()).
                add("salida", Integer.parseInt(trama.get("S5").toString())).build();
        JsonObject t6 = Json.createObjectBuilder().
                add("torniq", trama.get("T6N").toString()).
                add("salida", Integer.parseInt(trama.get("S6").toString())).build();
        JsonObject t7 = Json.createObjectBuilder().
                add("torniq", trama.get("T7N").toString()).
                add("salida", Integer.parseInt(trama.get("S7").toString())).build();

        array =  Json.createArrayBuilder().add(t1).add(t2).add(t3).add(t4).
                add(t5).add(t6).add(t7).build();

        return array;
    }
}
