package control;

import model.Bateriaentrada;
import model.Bateriasalida;
import model.Estacion;
import model.Torniqueteentrada;
import model.Torniquetesalida;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TorniquetesDAO
{
    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction tr;

    private static SessionFactory buildSessionFactory()
    {
        try
        {
            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
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

    public static Map<String, Object> insertEntradas(Map<String, Object> t)
    {
        session = null;
        tr = null;
//        ConMicroEntrada micro = null;
        Map<String, Object> trama = null;

        try
        {
            session = getSessionFactory().openSession();
            ManagedSessionContext.bind(session);
            tr = session.getTransaction();
//            micro = new ConMicroEntrada();
            trama = t == null ? new ConMicroEntrada().getTrama() : t;

            if (trama ==  null) return null;

            tr.begin();
            Estacion e = new Estacion();
            Bateriaentrada b = new Bateriaentrada();
            Torniqueteentrada t1 = new Torniqueteentrada();
            Torniqueteentrada t2 = new Torniqueteentrada();
            Torniqueteentrada t3 = new Torniqueteentrada();
            Torniqueteentrada t4 = new Torniqueteentrada();
            e.setIdEstacion(2);
            e.setNombre("Zaragoza");
            b.setAcceso("Nororiente");
            b.setFecha((Date) trama.get("DATE"));
            b.setEstacion(e);
            t1.setTorniquete(trama.get("T1N").toString());
            t1.setFase(trama.get("FASE1").toString());
            t1.setEntradaBoleto(Integer.parseInt(trama.get("T1").toString()));
            t1.setEntradaTarjeta(Integer.parseInt(trama.get("B1").toString()));
            t1.setNoAutorizado(Integer.parseInt(trama.get("P1").toString()));
            t1.setEstado(Integer.parseInt(trama.get("ES1").toString()));
            t1.setBateriaentrada(b);
            t2.setTorniquete(trama.get("T2N").toString());
            t2.setFase(trama.get("FASE2").toString());
            t2.setEntradaBoleto(Integer.parseInt(trama.get("T2").toString()));
            t2.setEntradaTarjeta(Integer.parseInt(trama.get("B2").toString()));
            t2.setNoAutorizado(Integer.parseInt(trama.get("P2").toString()));
            t2.setEstado(Integer.parseInt(trama.get("ES2").toString()));
            t2.setBateriaentrada(b);
            t3.setTorniquete(trama.get("T3N").toString());
            t3.setFase(trama.get("FASE3").toString());
            t3.setEntradaBoleto(Integer.parseInt(trama.get("T3").toString()));
            t3.setEntradaTarjeta(Integer.parseInt(trama.get("B3").toString()));
            t3.setNoAutorizado(Integer.parseInt(trama.get("P3").toString()));
            t3.setEstado(Integer.parseInt(trama.get("ES3").toString()));
            t3.setBateriaentrada(b);
            t4.setTorniquete(trama.get("T4N").toString());
            t4.setFase(trama.get("FASE4").toString());
            t4.setEntradaBoleto(Integer.parseInt(trama.get("B4").toString()));
            t4.setEntradaTarjeta(Integer.parseInt(trama.get("T4").toString()));
            t4.setNoAutorizado(Integer.parseInt(trama.get("P4").toString()));
            t4.setEstado(Integer.parseInt(trama.get("ES4").toString()));
            t4.setBateriaentrada(b);

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

            Criteria cr = session.createCriteria(Bateriaentrada.class);
            cr.setFetchMode("Estacion", FetchMode.JOIN);
            cr.setFetchMode("Linea", FetchMode.JOIN);
            cr.setFetchMode("Torniqueteentrada", FetchMode.JOIN);
            cr.add(Restrictions.between("fecha", from, to));
//            cr.addOrder(Order.asc("idTorniquete"));
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

    public static Map<String, Object> insertSalidas(Map<String, Object> t)
    {
        session = null;
        tr = null;
//        ConMicroSalida micro = null;
        Map<String, Object> trama = null;

        try
        {
            session = getSessionFactory().openSession();
            ManagedSessionContext.bind(session);
            tr = session.getTransaction();
//            micro = new ConMicroSalida();
            trama = t == null ? new ConMicroSalida().getTrama() : t;

            if (trama ==  null) return null;

            tr.begin();
            Estacion e = new Estacion();
            Bateriasalida b = new Bateriasalida();
            Torniquetesalida t1 = new Torniquetesalida();
            Torniquetesalida t2 = new Torniquetesalida();
            Torniquetesalida t3 = new Torniquetesalida();
            Torniquetesalida t4 = new Torniquetesalida();
            Torniquetesalida t5 = new Torniquetesalida();
            Torniquetesalida t6 = new Torniquetesalida();
            Torniquetesalida t7 = new Torniquetesalida();
            e.setIdEstacion(2);
            e.setNombre("Zaragoza");
            b.setAcceso("Nororiente");
            b.setFecha((Date) trama.get("DATE"));
            b.setEstacion(e);
            t1.setTorniquete(trama.get("T1N").toString());
            t1.setFase(trama.get("FASE1").toString());
            t1.setSalida(Integer.parseInt(trama.get("S1").toString()));
            t1.setBateriasalida(b);
            t2.setTorniquete(trama.get("T2N").toString());
            t2.setFase(trama.get("FASE2").toString());
            t2.setSalida(Integer.parseInt(trama.get("S2").toString()));
            t2.setBateriasalida(b);
            t3.setTorniquete(trama.get("T3N").toString());
            t3.setFase(trama.get("FASE3").toString());
            t3.setSalida(Integer.parseInt(trama.get("S3").toString()));
            t3.setBateriasalida(b);
            t4.setTorniquete(trama.get("T4N").toString());
            t4.setFase(trama.get("FASE4").toString());
            t4.setSalida(Integer.parseInt(trama.get("S4").toString()));
            t4.setBateriasalida(b);
            t5.setTorniquete(trama.get("T4N").toString());
            t5.setFase(trama.get("FASE5").toString());
            t5.setSalida(Integer.parseInt(trama.get("S5").toString()));
            t5.setBateriasalida(b);
            t6.setTorniquete(trama.get("T6N").toString());
            t6.setFase(trama.get("FASE6").toString());
            t6.setSalida(Integer.parseInt(trama.get("S6").toString()));
            t6.setBateriasalida(b);
            t7.setTorniquete(trama.get("T7N").toString());
            t7.setFase(trama.get("FASE7").toString());
            t7.setSalida(Integer.parseInt(trama.get("S7").toString()));
            t7.setBateriasalida(b);

            session.persist(b);
            session.persist(t1);
            session.persist(t2);
            session.persist(t3);
            session.persist(t4);
            session.persist(t5);
            session.persist(t6);
            session.persist(t7);

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

    public static List getSalidas(Date from, Date to)
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

            Criteria cr = session.createCriteria(Bateriasalida.class);
            cr.setFetchMode("Estacion", FetchMode.JOIN);
            cr.setFetchMode("Linea", FetchMode.JOIN);
            cr.setFetchMode("Torniquetesalida", FetchMode.JOIN);
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
//        f = format.parse("2015/09/11 13:00");
//        t = format.parse("2015/07/15 12:00");
//
//        System.out.println(f);
//        System.out.println(t);
//
//        getEntradas(f, t);
    }
}
