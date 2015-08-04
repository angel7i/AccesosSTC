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

public class ConMicro
{
    private InetAddress ip;
    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    Scanner scanner;

    public ConMicro()
    {
        try
        {
            ip = InetAddress.getByName("11.83.10.50");
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

            info = new HashMap<>();
            info.put("DATE", new Date());
            String trama = data.toString().replace("@", "").trim();


            trama = trama.substring(trama.indexOf("#") + 2);
            info.put("T1N", trama.substring(trama.indexOf("N"), trama.indexOf("N") + 4));
            trama = trama.substring(trama.indexOf("B1"));
            info.put("B1", trama.substring(trama.indexOf("B1")+3, trama.indexOf("B1")+9));
            trama = trama.substring(trama.indexOf("T1"));
            info.put("T1", trama.substring(trama.indexOf("T1") + 3, trama.indexOf("T1") + 9));
            String es1 = trama.substring(trama.indexOf("F1")+ 3, trama.indexOf("F1") + 4) +
                    trama.substring(trama.indexOf("C1")+ 3, trama.indexOf("C1") + 4);
            trama = trama.substring(trama.indexOf("N"));
            info.put("ES1", estado(es1));
            info.put("T2N", trama.substring(trama.indexOf("N"), trama.indexOf("N") + 4));
            trama = trama.substring(trama.indexOf("B2"));
            info.put("B2", trama.substring(trama.indexOf("B2")+3, trama.indexOf("B2")+9));
            trama = trama.substring(trama.indexOf("T2"));
            info.put("T2", trama.substring(trama.indexOf("T2") + 3, trama.indexOf("T2") + 9));
            String es2 = trama.substring(trama.indexOf("F2")+ 3, trama.indexOf("F2") + 4) +
                    trama.substring(trama.indexOf("C2")+ 3, trama.indexOf("C2") + 4);
            trama = trama.substring(trama.indexOf("N"));
            info.put("ES2", estado(es2));
            info.put("T3N", trama.substring(trama.indexOf("N"), trama.indexOf("N") + 4));
            trama = trama.substring(trama.indexOf("B3"));
            info.put("B3", trama.substring(trama.indexOf("B3")+3, trama.indexOf("B3")+9));
            trama = trama.substring(trama.indexOf("T3"));
            info.put("T3", trama.substring(trama.indexOf("T3") + 3, trama.indexOf("T3") + 9));
            String es3 = trama.substring(trama.indexOf("F3")+ 3, trama.indexOf("F3") + 4) +
                    trama.substring(trama.indexOf("C3")+ 3, trama.indexOf("C3") + 4);
            trama = trama.substring(trama.indexOf("N"));
            info.put("ES3", estado(es3));
            info.put("T4N", trama.substring(trama.indexOf("N"), trama.indexOf("N") + 4));
            trama = trama.substring(trama.indexOf("B4"));
            info.put("B4", trama.substring(trama.indexOf("B4")+3, trama.indexOf("B4")+9));
            trama = trama.substring(trama.indexOf("T4"));
            info.put("T4", trama.substring(trama.indexOf("T4") + 3, trama.indexOf("T4") + 9));
            String es4 = trama.substring(trama.indexOf("F4")+ 3, trama.indexOf("F4") + 4) +
                    trama.substring(trama.indexOf("C4")+ 3, trama.indexOf("C4") + 4);
            info.put("ES4", estado(es4));

//            for (Map.Entry<String, Object> s : info.entrySet())
//            {
//                System.out.println(s.getKey() + ":" + s.getValue());
//            }
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
            case "01":     // No funciona boleto
                return 1;
            case "10":     // Boleto y Tarjeta funcionando
                return 2;
        }

        return 0;
    }

    public static void main(String[] args)
    {
//        ConMicro r = new ConMicro();
//        r.getTrama();
    }
}
