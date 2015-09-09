package control;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import tables.Bateria;
import tables.Estacion;
import tables.Torniquete;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AccesoDAO
{
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    private  static Session session;
    private static Transaction tr;

    private static SessionFactory buildSessionFactory()
    {
        try
        {
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    public static SessionFactory getSessionFactory()
    {
        try
        {
            if (sessionFactory == null)
            {
                sessionFactory = buildSessionFactory();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return sessionFactory;
    }

    public static Map<String, Object> insertEntradas()
    {
        session = null;
        tr = null;
        ConMicro micro = null;
        Map<String, Object> trama = null;

        try
        {
            session = getSessionFactory().openSession();
            ManagedSessionContext.bind(session);
            tr = session.getTransaction();
            micro = new ConMicro();
            trama = micro.getTrama();

            if (trama ==  null) return null;

            tr.begin();
            Estacion e = new Estacion();
            Bateria b = new Bateria();
            Torniquete t1 = new Torniquete();
            Torniquete t2 = new Torniquete();
            Torniquete t3 = new Torniquete();
            Torniquete t4 = new Torniquete();
            e.setIdEstacion(2);
            e.setNombre("Zaragoza");
            b.setAcceso("Nororiente");
            b.setFecha((Date) trama.get("DATE"));
            b.setEstacion(e);
            t1.setTorniquete(trama.get("T1N").toString());
            t1.setFase(trama.get("FASE").toString());
            t1.setEntradaBoleto(Integer.parseInt(trama.get("T1").toString()));
            t1.setEntradaTarjeta(Integer.parseInt(trama.get("B1").toString()));
            t1.setNoAutorizado(Integer.parseInt(trama.get("P1").toString()));
            t1.setEstado(Integer.parseInt(trama.get("ES1").toString()));
            t1.setBateria(b);
            t2.setTorniquete(trama.get("T2N").toString());
            t2.setFase(trama.get("FASE").toString());
            t2.setEntradaBoleto(Integer.parseInt(trama.get("T2").toString()));
            t2.setEntradaTarjeta(Integer.parseInt(trama.get("B2").toString()));
            t2.setNoAutorizado(Integer.parseInt(trama.get("P2").toString()));            t2.setEstado(Integer.parseInt(trama.get("ES2").toString()));
            t2.setBateria(b);
            t3.setTorniquete(trama.get("T3N").toString());
            t3.setFase(trama.get("FASE").toString());
            t3.setEntradaBoleto(Integer.parseInt(trama.get("T3").toString()));
            t3.setEntradaTarjeta(Integer.parseInt(trama.get("B3").toString()));
            t3.setNoAutorizado(Integer.parseInt(trama.get("P3").toString()));            t3.setEstado(Integer.parseInt(trama.get("ES3").toString()));
            t3.setBateria(b);
            t4.setTorniquete(trama.get("T4N").toString());
            t4.setFase(trama.get("FASE").toString());
            t4.setEntradaBoleto(Integer.parseInt(trama.get("B4").toString()));
            t4.setEntradaTarjeta(Integer.parseInt(trama.get("T4").toString()));
            t4.setNoAutorizado(Integer.parseInt(trama.get("P4").toString()));            t4.setEstado(Integer.parseInt(trama.get("ES4").toString()));
            t4.setBateria(b);

            session.persist(b);
            session.persist(t1);
            session.persist(t2);
            session.persist(t3);
            session.persist(t4);

            if (!tr.wasCommitted())
                tr.commit();
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().toString());
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (session != null && (session.isConnected() || session.isOpen()))
            {
                if (tr != null && tr.isActive())
                {
                    tr.rollback();
                }

                session.close();
            }

            ManagedSessionContext.unbind(sessionFactory);
        }

        return trama;
    }

    public static List getEntradas(Date from, Date to)
    {
        session = null;
        tr = null;
        List list = null;

        try
        {
            session = getSessionFactory().openSession();
            ManagedSessionContext.bind(session);
            tr = session.getTransaction();
            tr.begin();

            Criteria cr = session.createCriteria(Bateria.class);
            cr.setFetchMode("Estacion", FetchMode.JOIN);
            cr.setFetchMode("Linea", FetchMode.JOIN);
            cr.setFetchMode("Torniquete", FetchMode.JOIN);
            cr.add(Restrictions.between("fecha", from, to));
            list = cr.list();

            if (!tr.wasCommitted())
                tr.commit();
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().toString());
            e.printStackTrace();
        }

        return list;
    }

    public static void close()
    {
        if (session != null && (session.isConnected() || session.isOpen()))
        {
            if (tr != null && tr.isActive())
            {
                tr.rollback();
            }

            session.close();
        }

        ManagedSessionContext.unbind(sessionFactory);
    }

    public static void main(String[] args) throws ParseException
    {
//        Date f = new Date();
//        Date t = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
//        f = format.parse("2015/07/01 13:00");
//        t = format.parse("2015/07/20 12:00");
//
//        System.out.println(f);
//        System.out.println(t);
//
//        getEntradas(f, t);
    }
}
