package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConMicroSalida
{
    private InetAddress ip;
    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    Scanner scanner;

    public ConMicroSalida()
    {
        try
        {
            ip = InetAddress.getByName("15.59.252.26");

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getTrama()
    {
        Map<String, Object> info = null;
        StringBuilder data = new StringBuilder();

        try
        {
            socket = new Socket(ip, 5000);
            socket.setSoTimeout(1000);

            if (!socket.isConnected())
            {
                System.out.println("No hay conexion");
                return info;
            }

            do
            {
                data.setLength(0);
                pw = new PrintWriter(socket.getOutputStream());
                pw.print(1);
                pw.flush();
                Thread.sleep(1000);
                scanner = new Scanner(socket.getInputStream());

                while (scanner.hasNext())
                {
                    data.append(scanner.next() + " ");
                }

                System.out.println(data.toString());
            }
            while (!data.toString().startsWith("@"));

            String trama = data.toString().replace("@", "").replace("*","");

            info = new HashMap<>();
            info.put("DATE", new Date());

            trama = trama.substring(trama.indexOf("#") + 3);
            info.put("T1N", "S015");
            info.put("FASE1", "");
            info.put("S1", trama.substring(trama.indexOf("S1") + 2, trama.indexOf("S1") + 8));
            trama = trama.substring(trama.indexOf("N2"));

            info.put("T2N", "S016");
            info.put("FASE2", "");
            info.put("S2", trama.substring(trama.indexOf("S2") + 2, trama.indexOf("S2") + 8));
            trama = trama.substring(trama.indexOf("N3"));

            info.put("T3N", "S017");
            info.put("FASE3", "");
            info.put("S3", trama.substring(trama.indexOf("S3") + 2, trama.indexOf("S3") + 8));
            trama = trama.substring(trama.indexOf("N4"));

            info.put("T4N", "S018");
            info.put("FASE4", "");
            info.put("S4", trama.substring(trama.indexOf("S4") + 2, trama.indexOf("S4") + 8));
            trama = trama.substring(trama.indexOf("N5"));

            info.put("T5N", "S019");
            info.put("FASE5", "");
            info.put("S5", trama.substring(trama.indexOf("S5") + 2, trama.indexOf("S5") + 8));
            trama = trama.substring(trama.indexOf("N6"));

            info.put("T6N", "S020");
            info.put("FASE6", "");
            info.put("S6", trama.substring(trama.indexOf("S6") + 2, trama.indexOf("S6") + 8));
            trama = trama.substring(trama.indexOf("N7"));

            info.put("T7N", "S021");
            info.put("FASE7", "");
            info.put("S7", trama.substring(trama.indexOf("S7") + 2, trama.indexOf("S7") + 8));

            for (Map.Entry<String, Object> s : info.entrySet())
            {
                System.out.println(s.getKey() + ":" + s.getValue());
            }
        }
        catch (SocketTimeoutException e)
        {
            System.out.println("Tiempo de respueta terminado");
        }
        catch (ConnectException e)
        {
            System.out.println("No hay conexion con el micro");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (pw != null && scanner != null)
                {
                    pw.close();
                    scanner.close();
                    socket.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return info;
    }

    private int estado(String s)
    {
        switch (s)
        {
            case "00":     // Error
            case "11":
                return 0;
            case "01":     // Boleto inhabilitado
                return 1;
            case "10":     // Boleto y Tarjeta habilitado
                return 2;
        }

        return 0;
    }

    public static void main(String[] args)
    {
        new ConMicroSalida().getTrama();
    }
}
