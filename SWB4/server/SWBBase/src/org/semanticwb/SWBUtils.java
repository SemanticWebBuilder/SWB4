
package org.semanticwb;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;
import org.semanticwb.base.db.DBConnectionManager;
import org.semanticwb.base.util.imp.Logger4jImpl;

/**
 *
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */

public class SWBUtils
{
    static private SWBUtils instance;
    private static String applicationPath = "";
    
    private static Logger log=getLogger(SWBUtils.class);
    
    private static int bufferSize=8192;    
    
    private static boolean initLogger=false;
    
    /** Creates new utils */
    private SWBUtils()
    {
        log.event("SemanticWebBuilder Base Starting...");
        log.event("AppicationPath: "+applicationPath);
        init();
    }
    
    /** Get Instance.
     * @return  */
    static public SWBUtils getInstance()
    {
        return instance;
    }  
    
    /** Get Instance.
     * @param applicationPath 
     * @return  */
    static public synchronized SWBUtils createInstance(String applicationPath)
    {
        SWBUtils.applicationPath = IO.normalizePath(applicationPath);
        if (instance == null)
        {
            initFileLogger();
            instance = new SWBUtils();
        }
        return instance;
    }      
    
    /*
     * Inicializa SWBUtils
     */
    private void init()
    {
    }

    /*
     * Regresa ruta fisica de la Aplicacion WEB
     * ejemplo: /tomcat/webapps/swb
     */
    /**
     * 
     * @return
     */
    public static String getApplicationPath() {
        return applicationPath;
    }

    //default Logger
    private static void initLogger()
    {
        String log_conf="log4j.rootLogger=debug, stdout" + "\n" +
                "log4j.appender.stdout=org.apache.log4j.ConsoleAppender" + "\n" +
                "log4j.appender.stdout.layout=org.apache.log4j.PatternLayout" + "\n" +
                "log4j.appender.stdout.layout.ConversionPattern=%d %p - %m%n" + "\n" +
                "log4j.logger.org.semanticwb=INFO";
        try
        {
            Properties proper=new Properties();
            proper.load(IO.getStreamFromString(log_conf));
            PropertyConfigurator.configure(proper);
        }catch(Exception e)
        {
            System.out.println("Error: To configure Logger...");
            e.printStackTrace();
        }
        //org.apache.log4j.Logger.getLogger("org.semanticwb").setLevel(Level.TRACE);
        initLogger=true;
    }
    
    private static void initFileLogger()
    {
        try
        {
            FileInputStream in=new FileInputStream(applicationPath+"/WEB-INF/classes/logging.properties");
            String log_conf=IO.readInputStream(in);
            log_conf=SWBUtils.TEXT.replaceAll(log_conf,"{apppath}",applicationPath);
            Properties proper=new Properties();
            proper.load(IO.getStreamFromString(log_conf));
            PropertyConfigurator.configure(proper);
        }catch(Exception e)
        {
            log.error("Error: logging.properties not found...",e);
        }
        //org.apache.log4j.Logger.getLogger("org.semanticwb").setLevel(Level.TRACE);
        initLogger=true;
    }    
    
    /*
     * Regresa el Logger de acuerdo a el nombre de la clase
     */
    /**
     * 
     * @param cls
     * @return
     */
    public static Logger getLogger(Class cls)
    {
        if(!initLogger)initLogger();
        return new Logger4jImpl(org.apache.log4j.Logger.getLogger(cls));
    }
    
    /**
     * 
     */
    public static class TEXT
    {
        //version 1.3
        /**
         * Remplaza en una cadena (str) las coincidencias encontradas (match) con otra cadena (replace).
         * @param str 
         * @param match
         * @param replace
         * @return 
         */
        public static String replaceAll(String str, String match,String replace)
        {
            if(match==null || match.length()==0)return str;
            if(replace==null)replace="";
            int i=str.indexOf(match);
            int y=0;
            while(i>=0)
            {
                str=str.substring(0,i)+replace+str.substring(i+match.length());
                y=i+replace.length();
                i=str.indexOf(match,y);
            }
            return str;
        }       
    }
    
    /**
     * 
     */
    public static class IO
    {
        /*
         * Obtiene un objeto InputStream dado un objeto String.
         */
        /**
         * 
         * @param str
         * @return
         */
        public static InputStream getStreamFromString(String str)
        {
            if (str == null) return null;
            return new ByteArrayInputStream(str.getBytes());
        }       
        
        /**
         *  Copia el InputStream al OutputStream y al final cierra los streams
         *
         * @param in 
         * @param out 
         * @throws IOException
         */
        public static void copyStream(InputStream in, OutputStream out) throws IOException
        {
             copyStream(in, out, bufferSize);
        }  

        /**
         *  Copia el InputStream al OutputStream y al final cierra los streams
         * @param in 
         * @param out
         * @param bufferSize 
         * @throws IOException
         */
        public static void copyStream(InputStream in, OutputStream out, int bufferSize) throws IOException
        {
            if (in==null)throw new IOException("Input Stream null");
            if (out==null)throw new IOException("Ouput Stream null");

            byte[] bfile = new byte[bufferSize];
            int x;
            while ((x = in.read(bfile, 0, bufferSize)) > -1)
            {
                out.write(bfile, 0, x);
            }
            in.close();
            out.flush();
            out.close();
        }       
        
        /**
         * Regresa un objeto String resultante de un objeto InputStream
         * @param in 
         * @return
         * @throws IOException 
         */
        public static String readInputStream(InputStream in) throws IOException
        {
            if (in==null)throw new IOException("Input Stream null");
            StringBuffer buf = new StringBuffer();
            byte[] bfile = new byte[bufferSize];
            int x;
            while ((x = in.read(bfile, 0, bufferSize)) > -1)
            {
                String aux=new String(bfile, 0, x);
                buf.append(aux);
            }
            in.close();
            return buf.toString();
        }

        /**
         * Regresa un objeto String codificado resultante de un objeto InputStream
         * y un tipo de codificación.
         * @param inp 
         * @param enc 
         * @return
         * @throws java.io.UnsupportedEncodingException
         * @throws java.io.IOException 
         */
        public static String readInputStream(InputStream inp, String enc) throws java.io.UnsupportedEncodingException, java.io.IOException
        {
            if (inp==null)throw new IOException("Input Stream null");
            if (enc==null)throw new UnsupportedEncodingException("Encoding null");

            InputStreamReader in = new InputStreamReader(inp, enc);

            StringBuffer ret = new StringBuffer();

            char[] bfile = new char[bufferSize];
            int x;
            while ((x = in.read(bfile, 0, bufferSize)) > -1)
            {
                ret.append(new String(bfile, 0, x));
            }
            in.close();
            return ret.toString();
        }         
        
        /**
         * Normaliza rutas, sustituyendo  el carácter “\” por el carácter “/” y eliminando rutas relativas.
         * @param path
         * @return 
         */
       public static String normalizePath(String path)
        {
            if(path == null)return null;
            String normalized = path;
            if(normalized.equals("/."))
                return "/";
            if(normalized.indexOf('\\') >= 0)
                normalized = normalized.replace('\\', '/');
            if(!normalized.startsWith("/") && normalized.indexOf(':')<0)
                normalized = "/" + normalized;
            do
            {
                int index = normalized.indexOf("//");
                if(index < 0)
                    break;
                normalized = normalized.substring(0, index) + normalized.substring(index + 1);
            } while(true);
            do
            {
                int index = normalized.indexOf("/./");
                if(index < 0)
                    break;
                normalized = normalized.substring(0, index) + normalized.substring(index + 2);
            } while(true);
            do
            {
                int index = normalized.indexOf("/../");
                if(index >= 0)
                {
                    if(index == 0)
                        return null;
                    int index2 = normalized.lastIndexOf('/', index - 1);
                    normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
                } else
                {
                    return normalized;
                }
            } while(true);
        }                  
    }
    
    /**
     * 
     */
    public static class XML
    {
        
    }
    
    /**
     * 
     */
    public static class DB
    {
        private static DBConnectionManager manager=null;
        private static String defaultPoolName = "swb";
        
        private static DBConnectionManager getConnectionManager()
        {
            if(manager==null)
            {
                manager=new DBConnectionManager();
            }
            return manager;
        }
        /** Return a enumeration of DBConnectionPool
         * @return Return a enumeration of DBConnectionPool
        */    
        public static Enumeration getPools()
        {
            return getConnectionManager().getPools().elements();
        }
        
        /**
         * 
         * @param poolName
         */
        public static void setDefaultPool(String poolName)
        {
            defaultPoolName=poolName;
        }


        /** Getter for Connection form DBPool.
         * @return Connection from DBPool.
         * @param poolName 
         */
        public static Connection getNoPoolConnection(String poolName)
        {
            return getConnectionManager().getNoPoolConnection(poolName);
        }
        
        /** Getter for Connection form DBPool.
         * @param description 
         * @return Connection from DBPool.
         */
        public static Connection getDefaultConnection(String description)
        {
            return getConnection(defaultPoolName, description);
        }     
        
        /** Getter for Connection form DBPool.
         * @return Connection from DBPool.
         */
        public static Connection getDefaultConnection()
        {
            return getConnection(defaultPoolName);
        }         

        /** Getter for Connection form DBPool.
         * @return Connection from DBPool.
         * @param poolName 
         * @param description 
         */
        public static Connection getConnection(String poolName, String description)
        {
            return getConnectionManager().getConnection(poolName, description);
            //return dbPool.getNoPoolConnection(name);
        }

        /** Getter for Connection form DBPool.
         * @return Connection from DBPool.
         * @param name  */
        public static Connection getConnection(String name)
        {
            return getConnectionManager().getConnection(name);
        }
        
        /** Nombre de base de datos.
         * DataBase name
         *  @return String nombre de la base de datos.
         */
        public static String getDatabaseName()
        {
            return getDatabaseName(defaultPoolName);
        }        

        /** Nombre de base de datos.
         * DataBase name
         *  @param poolName 
         * @return String nombre de la base de datos.
         */
        public static String getDatabaseName(String poolName)
        {
            String ret = null;
            try
            {
                Connection con = getConnectionManager().getConnection(poolName);
                if(con!=null)
                {
                    java.sql.DatabaseMetaData md = con.getMetaData();
                    ret = md.getDatabaseProductName();
                    con.close();
                }
            } catch (Exception e)
            {
                log.error("Not Database Found...", e);
            }
            return ret;
        }        
    }
   
}


