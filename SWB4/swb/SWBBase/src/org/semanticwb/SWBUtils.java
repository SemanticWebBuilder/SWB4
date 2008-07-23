package org.semanticwb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.log4j.PropertyConfigurator;
import org.semanticwb.base.db.DBConnectionManager;
import org.semanticwb.base.db.DBConnectionPool;
import org.semanticwb.base.util.imp.Logger4jImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 *
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */
public class SWBUtils {

    private static Logger log = getLogger(SWBUtils.class);
    static private SWBUtils instance;
    private static String applicationPath = "";
    private static int bufferSize = 8192;
    private static boolean initLogger = false;
    
    /** Creates new utils */
    private SWBUtils() {
        log.event("SemanticWebBuilder Base Starting...");
        log.event("AppicationPath: " + applicationPath);
        init();
    }

    /** Get Instance.
     * @return  */
    static public SWBUtils getInstance() {
        return instance;
    }

    /** Get Instance.
     * @param applicationPath 
     * @return  */
    static public synchronized SWBUtils createInstance(String applicationPath) {
        SWBUtils.applicationPath = IO.normalizePath(applicationPath);
        if (instance == null) {
            initFileLogger();
            instance = new SWBUtils();
        }
        return instance;
    }

    /*
     * Inicializa SWBUtils
     */
    private void init() {
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
    private static void initLogger() {
        String log_conf = "log4j.rootLogger=debug, stdout" + "\n" +
                "log4j.appender.stdout=org.apache.log4j.ConsoleAppender" + "\n" +
                "log4j.appender.stdout.layout=org.apache.log4j.PatternLayout" + "\n" +
                "log4j.appender.stdout.layout.ConversionPattern=%d %p - %m%n" + "\n" +
                "log4j.logger.org.semanticwb=INFO";
        try {
            Properties proper = new Properties();
            proper.load(IO.getStreamFromString(log_conf));
            PropertyConfigurator.configure(proper);
        } catch (Exception e) {
            System.out.println("Error: To configure Logger...");
            e.printStackTrace();
        }
        //org.apache.log4j.Logger.getLogger("org.semanticwb").setLevel(Level.TRACE);
        initLogger = true;
    }

    private static void initFileLogger() {
        try {
            FileInputStream in = new FileInputStream(applicationPath + "/WEB-INF/classes/logging.properties");
            String log_conf = IO.readInputStream(in);
            log_conf = SWBUtils.TEXT.replaceAll(log_conf, "{apppath}", applicationPath);
            Properties proper = new Properties();
            proper.load(IO.getStreamFromString(log_conf));
            PropertyConfigurator.configure(proper);
        } catch (Exception e) {
            log.error("Error: logging.properties not found...", e);
        }
        //org.apache.log4j.Logger.getLogger("org.semanticwb").setLevel(Level.TRACE);
        initLogger = true;
    }

    /*
     * Regresa el Logger de acuerdo a el nombre de la clase
     */
    /**
     * 
     * @param cls
     * @return
     */
    public static Logger getLogger(Class cls) {
        if (!initLogger) {
            initLogger();
        }
        return new Logger4jImpl(org.apache.log4j.Logger.getLogger(cls));
    }

    /**
     * 
     */
    public static class TEXT {

        private static SimpleDateFormat iso8601dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS");
        //version 1.3
        /**
         * Remplaza en una cadena (str) las coincidencias encontradas (match) con otra cadena (replace).
         * @param str 
         * @param match
         * @param replace
         * @return 
         */
        public static String replaceAll(String str, String match, String replace) {
            if (match == null || match.length() == 0) {
                return str;
            }
            if (replace == null) {
                replace = "";
            }
            int i = str.indexOf(match);
            int y = 0;
            while (i >= 0) {
                str = str.substring(0, i) + replace + str.substring(i + match.length());
                y = i + replace.length();
                i = str.indexOf(match, y);
            }
            return str;
        }

        public static String iso8601DateFormat(Date date) {
            return iso8601dateFormat.format(date);
        }

        public static Date iso8601DateParse(String date) throws ParseException {
            return iso8601dateFormat.parse(date);
        }
    }

    /**
     * 
     */
    public static class IO {
        /*
         * Obtiene un objeto InputStream dado un objeto String.
         */

        /**
         * 
         * @param str
         * @return
         */
        public static InputStream getStreamFromString(String str) {
            if (str == null) {
                return null;
            }
            return new ByteArrayInputStream(str.getBytes());
        }

        /**
         *  Copia el InputStream al OutputStream y al final cierra los streams
         *
         * @param in 
         * @param out 
         * @throws IOException
         */
        public static void copyStream(InputStream in, OutputStream out) throws IOException {
            copyStream(in, out, bufferSize);
        }

        /**
         *  Copia el InputStream al OutputStream y al final cierra los streams
         * @param in 
         * @param out
         * @param bufferSize 
         * @throws IOException
         */
        public static void copyStream(InputStream in, OutputStream out, int bufferSize) throws IOException {
            if (in == null) {
                throw new IOException("Input Stream null");
            }
            if (out == null) {
                throw new IOException("Ouput Stream null");
            }
            byte[] bfile = new byte[bufferSize];
            int x;
            while ((x = in.read(bfile, 0, bufferSize)) > -1) {
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
        public static String readInputStream(InputStream in) throws IOException {
            if (in == null) {
                throw new IOException("Input Stream null");
            }
            StringBuffer buf = new StringBuffer();
            byte[] bfile = new byte[bufferSize];
            int x;
            while ((x = in.read(bfile, 0, bufferSize)) > -1) {
                String aux = new String(bfile, 0, x);
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
        public static String readInputStream(InputStream inp, String enc) throws java.io.UnsupportedEncodingException, java.io.IOException {
            if (inp == null) {
                throw new IOException("Input Stream null");
            }
            if (enc == null) {
                throw new UnsupportedEncodingException("Encoding null");
            }
            InputStreamReader in = new InputStreamReader(inp, enc);

            StringBuffer ret = new StringBuffer();

            char[] bfile = new char[bufferSize];
            int x;
            while ((x = in.read(bfile, 0, bufferSize)) > -1) {
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
        public static String normalizePath(String path) {
            if (path == null) {
                return null;
            }
            String normalized = path;
            if (normalized.equals("/.")) {
                return "/";
            }
            if (normalized.indexOf('\\') >= 0) {
                normalized = normalized.replace('\\', '/');
            }
            if (!normalized.startsWith("/") && normalized.indexOf(':') < 0) {
                normalized = "/" + normalized;
            }
            do {
                int index = normalized.indexOf("//");
                if (index < 0) {
                    break;
                }
                normalized = normalized.substring(0, index) + normalized.substring(index + 1);
            } while (true);
            do {
                int index = normalized.indexOf("/./");
                if (index < 0) {
                    break;
                }
                normalized = normalized.substring(0, index) + normalized.substring(index + 2);
            } while (true);
            do {
                int index = normalized.indexOf("/../");
                if (index >= 0) {
                    if (index == 0) {
                        return null;
                    }
                    int index2 = normalized.lastIndexOf('/', index - 1);
                    normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
                } else {
                    return normalized;
                }
            } while (true);
        }
    }

    /**
     * 
     */
    public static class XML {

        private static XML m_xml = null;
        private DocumentBuilderFactory m_dbf = null;
        private TransformerFactory m_tFactory = null;

        private static XML getInstance() {
            if (m_xml == null) {
                m_xml = new XML();
            }
            return m_xml;
        }

        public static DocumentBuilderFactory getDocumentBuilderFactory() {
            XML xml = getInstance();
            return xml.m_dbf;
        }

        public static TransformerFactory getTransformerFactory() {
            XML xml = getInstance();
            return xml.m_tFactory;
        }

        private XML() {
            try {
                m_dbf = DocumentBuilderFactory.newInstance();
                m_dbf.setNamespaceAware(true);
                m_dbf.setIgnoringElementContentWhitespace(true);
            //db=dbf.newDocumentBuilder();
            } catch (Exception e) {
                log.error("Error getting DocumentBuilderFactory...", e);
            }

            try {
                m_tFactory = TransformerFactory.newInstance();
            } catch (Exception e) {
                log.error("Error getting TransformerFactory...", e);
            }
        }

        /**
         *Crea un objeto String a partir de un objeto Document con cierta codificación especificada y 
         * teniendo la posibilidad de identar la salida, la identación que se tiene especificada en el método es 2.
         * @param dom
         * @param encode
         * @param ident
         * @return  */
        public static String domToXml(Document dom, String encode, boolean ident) {
            ByteArrayOutputStream sw = new java.io.ByteArrayOutputStream();
            OutputStreamWriter osw = null;
            try {
                osw = new java.io.OutputStreamWriter(sw, encode);
                StreamResult streamResult = new StreamResult(osw);
                TransformerFactory tFactory = getTransformerFactory();
                Transformer transformer = null;
                synchronized (tFactory) {
                    transformer = tFactory.newTransformer();
                }
                transformer.setOutputProperty(OutputKeys.ENCODING, encode);
                if (ident) {
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    try {
                        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                    } catch (Exception noe) {/*No soportado en algunos xerses*/

                    }
                }
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.transform(new DOMSource(dom), streamResult);
            } catch (Exception e) {
                log.error(e);
            }
            return sw.toString();
        }

        /**
         * Crea un objeto String a partir de un objeto Document con codificación UTF-8 y sin identación.
         * @param dom
         * @return  */
        public static String domToXml(Document dom) {
            return domToXml(dom, "UTF-8", false);
        }

        /**
         * Crea un objeto String a partir de un objeto Document con codificación UTF-8 y teniendo la posibilidad de
         * identar la salida, la identación que se tiene especificada en el método es 2.
         * @param dom
         * @param ident
         * @return  */
        public static String domToXml(Document dom, boolean ident) {
            return domToXml(dom, "UTF-8", ident);
        }

        /**
         * Crea una copia exacta de un objeto Document
         * Creates an exactly copy of Document object
         * @param dom
         * @throws org.w3c.dom.DOMException
         * @return  */
        public static Document copyDom(Document dom) throws SWBException {
            Document n = getNewDocument();
            if (dom != null && dom.hasChildNodes()) {
                Node node = n.importNode(dom.getFirstChild(), true);
                n.appendChild(node);
            }
            return n;
        }

        /**
         * Crea un objeto Document a partir de un objeto String.
         * Creates a document object in base of String object
         * @param xml
         * @return  */
        public static Document xmlToDom(String xml) {
            if (xml == null || xml.length() == 0) {
                return null;
            }
            Document dom = null;
            try {
                ByteArrayInputStream sr = new java.io.ByteArrayInputStream(xml.getBytes());
                dom = xmlToDom(sr);
            } catch (Exception e) {
                log.error(e);
            }
            return dom;
        }

        /**
         * Crea un objeto Document a partir de un objeto InputStream.
         * Creates a document object in base of InputStream object
         * @param xml
         * @return  */
        public static Document xmlToDom(InputStream xml) {
            Document dom = null;
            try {
                dom = xmlToDom(new InputSource(xml));
            //xml.close();
            } catch (Exception e) {
                log.error(e);
            }
            return dom;
        }

        /**
         * Crea un objeto Document a partir de un objeto InputSource.
         * Creates a document object in base of InputSource object
         * @param xml
         * @return  */
        public static Document xmlToDom(InputSource xml) {
            DocumentBuilderFactory dbf = null;
            DocumentBuilder db = null;
            Document dom = null;
            try {
                dbf = getDocumentBuilderFactory();
                synchronized (dbf) {
                    db = dbf.newDocumentBuilder();
                }
                if (xml != null) {
                    dom = db.parse(xml);
                    try {
                        dom = copyDom(dom);
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
            return dom;
        }

        /**
         * Crea un nuevo objeto Document.
         * Creates a new object document
         * @throws com.infotec.appfw.exception.AFException
         * @return  */
        public static Document getNewDocument() throws SWBException {
            DocumentBuilderFactory dbf = getDocumentBuilderFactory();
            DocumentBuilder db = null;
            Document dom = null;
            try {
                synchronized (dbf) {
                    db = dbf.newDocumentBuilder();
                }
                dom = db.newDocument();
            } catch (Exception e) {
                log.error(e);
                throw new SWBException("Error getting new XML Document", e);
            }
            return dom;
        }

        /**
         * Carga un objeto InputStream de un xslt para ser utilizado como plantilla.
         * @param stream
         * @throws javax.xml.transform.TransformerConfigurationException
         * @return  */
        public static Templates loadTemplateXSLT(InputStream stream) throws TransformerConfigurationException {
            TransformerFactory transFact = getTransformerFactory();
            return transFact.newTemplates(new StreamSource(stream));
        }

        /**
         * Transforma un objeto Document con un Template (xslt) especificado, 
         * regresando un objeto String con dicha transformación y listo para ser desplegado.
         * @param tpl
         * @param doc
         * @throws javax.xml.transform.TransformerException
         * @return  */
        public static String transformDom(Templates tpl, Document doc) throws TransformerException {
            ByteArrayOutputStream sw = new java.io.ByteArrayOutputStream();
            Transformer trans = tpl.newTransformer();
            trans.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        }

        public boolean xmlVerifier(org.xml.sax.InputSource schema, org.xml.sax.InputSource xml) {
            return xmlVerifier(null, schema, null, xml);
        }

        public boolean xmlVerifier(String idschema, org.xml.sax.InputSource schema, String idxml, org.xml.sax.InputSource xml) {
            boolean bOk = false;
            if (schema == null || xml == null) {
                if (schema == null) {
                    log.error("Error WBAdmResourceUtils.XMLVerifier(): Schema source is null.");
                } else {
                   log.event("Error WBAdmResourceUtils.XMLVerifier(): The input document source is null.");
                }
                return bOk;
            }

            if (idschema != null && !idschema.trim().equals("")) {
                schema.setSystemId(idschema);
            }
            if (idxml != null && !idxml.trim().equals("")) {
                xml.setSystemId(idxml);
            }
            bOk = xmlVerifierImpl(schema.getSystemId(), schema, xml);
            return bOk;
        }

        public boolean xmlVerifier(java.io.InputStream schema, java.io.InputStream xml) {
            return xmlVerifier(null, schema, xml);
        }

        public boolean xmlVerifier(String idschema, java.io.InputStream schema, java.io.InputStream xml) {
            boolean bOk = false;
            if (schema == null || xml == null) {
                if (schema == null) {
                    log.error("Error WBAdmResourceUtils.XMLVerifier(): Schema stream is null.");
                } else {
                    log.error("Error WBAdmResourceUtils.XMLVerifier(): The input document stream is null.");
                }
                return bOk;
            }
            org.xml.sax.InputSource inxml = new org.xml.sax.InputSource(xml);
            bOk = xmlVerifierImpl(idschema, schema, inxml);
            return bOk;
        }

        private boolean xmlVerifierImpl(String sysid, Object objschema, Object objxml) {
            boolean bOk = false;
            if (objschema == null || objxml == null) {
                if (objschema == null) {
                    log.error("Error WBAdmResourceUtils.XMLVerifier(): Schema is null.");
                } else {
                    log.error("Error WBAdmResourceUtils.XMLVerifier(): The input document is null.");
                }
                return bOk;
            }
            org.iso_relax.verifier.VerifierFactory factory = new com.sun.msv.verifier.jarv.TheFactoryImpl();            
            org.iso_relax.verifier.Schema schema = null;
            try {
                if (objschema instanceof java.io.File) {
                    schema = factory.compileSchema((java.io.File) objschema);
                } else if (objschema instanceof org.xml.sax.InputSource) {
                    schema = factory.compileSchema((org.xml.sax.InputSource) objschema);
                } else if (objschema instanceof java.io.InputStream) {
                    if (sysid != null && !sysid.trim().equals("")) {
                        schema = factory.compileSchema((java.io.InputStream) objschema, sysid);
                    } else {
                        schema = factory.compileSchema((java.io.InputStream) objschema);
                    }
                } else if (objschema instanceof java.lang.String) {
                    schema = factory.compileSchema((java.lang.String) objschema);
                }
                try {
                    org.iso_relax.verifier.Verifier verifier = schema.newVerifier();
                    verifier.setErrorHandler(silentErrorHandler);

                    if (objxml instanceof java.io.File) {
                        bOk = verifier.verify((java.io.File) objxml);
                    } else if (objxml instanceof org.xml.sax.InputSource) {
                        bOk = verifier.verify((org.xml.sax.InputSource) objxml);
                    } else if (objxml instanceof org.w3c.dom.Node) {
                        bOk = verifier.verify((org.w3c.dom.Node) objxml);
                    } else if (objxml instanceof java.lang.String) {
                        bOk = verifier.verify((java.lang.String) objxml);
                    }
                } catch (org.iso_relax.verifier.VerifierConfigurationException e) {
                    log.error("Error WBAdmResourceUtils.XMLVerifier(): Unable to create a new verifier.",e);
                } catch (org.xml.sax.SAXException e) {
                    log.event("Error WBAdmResourceUtils.XMLVerifier(): The input document is not wellformed.",e);
                }
            } catch (Exception e) {
                log.event("Error WBAdmResourceUtils.XMLVerifier(): Unable to parse the schema file.", e);
            }
            return bOk;
        }

        public boolean xmlVerifierByURL(String schema, String xml) {
            return xmlVerifierByURL(null, schema, xml);
        }

        public boolean xmlVerifierByURL(String sysid, String schema, String xml) {
            return xmlVerifierImpl(sysid, schema, xml);
        }
        
        /**
         * An error handler implementation that doesn't report any error.
         */
        private static final org.xml.sax.ErrorHandler silentErrorHandler = new org.xml.sax.ErrorHandler() {
                public void fatalError( org.xml.sax.SAXParseException e ) {}
                public void error( org.xml.sax.SAXParseException e ) {}
                public void warning( org.xml.sax.SAXParseException e ) {}
        };
 
    }

    /**
     * 
     */
    public static class DB {

        private static DBConnectionManager manager = null;
        private static String defaultPoolName = "swb";

        private static DBConnectionManager getConnectionManager() {
            if (manager == null) {
                manager = new DBConnectionManager();
            }
            return manager;
        }

        /** Return a enumeration of DBConnectionPool
         * @return Return a enumeration of DBConnectionPool
         */
        public static Enumeration<DBConnectionPool> getPools() {
            return getConnectionManager().getPools().elements();
        }

        /**
         * 
         * @param poolName
         */
        public static void setDefaultPool(String poolName) {
            defaultPoolName = poolName;
        }

        /** Getter for Connection form DBPool.
         * @return Connection from DBPool.
         * @param poolName 
         */
        public static Connection getNoPoolConnection(String poolName) {
            return getConnectionManager().getNoPoolConnection(poolName);
        }

        /** Getter for Connection form DBPool.
         * @param description 
         * @return Connection from DBPool.
         */
        public static Connection getDefaultConnection(String description) {
            return getConnection(defaultPoolName, description);
        }

        /** Getter for Connection form DBPool.
         * @return Connection from DBPool.
         */
        public static Connection getDefaultConnection() {
            return getConnection(defaultPoolName);
        }

        /** Getter for Connection form DBPool.
         * @return Connection from DBPool.
         * @param poolName 
         * @param description 
         */
        public static Connection getConnection(String poolName, String description) {
            return getConnectionManager().getConnection(poolName, description);
        //return dbPool.getNoPoolConnection(name);
        }

        /** Getter for Connection form DBPool.
         * @return Connection from DBPool.
         * @param name  */
        public static Connection getConnection(String name) {
            return getConnectionManager().getConnection(name);
        }

        /** Nombre de base de datos.
         * DataBase name
         *  @return String nombre de la base de datos.
         */
        public static String getDatabaseName() {
            return getDatabaseName(defaultPoolName);
        }

        /** Nombre de base de datos.
         * DataBase name
         *  @param poolName 
         * @return String nombre de la base de datos.
         */
        public static String getDatabaseName(String poolName) {
            String ret = null;
            try {
                Connection con = getConnectionManager().getConnection(poolName);
                if (con != null) {
                    java.sql.DatabaseMetaData md = con.getMetaData();
                    ret = md.getDatabaseProductName();
                    con.close();
                }
            } catch (Exception e) {
                log.error("Not Database Found...", e);
            }
            return ret;
        }

        public static int getConnections(String poolName) {
            return getConnectionManager().getConnections(poolName);
        }

        public static int getFreeConnections(String poolName) {
            return getConnectionManager().getFreeConnections(poolName);
        }
    }
}


