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
import java.security.NoSuchAlgorithmException;
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.EmailAttachment;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.base.util.SFBase64;
import org.semanticwb.base.util.SWBMailSender;
import org.semanticwb.base.util.SWBMail;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import sun.misc.BASE64Encoder;

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
    private static Locale locale = Locale.ENGLISH;
    public static String LOCALE_SERVICES = null;

    /** Creates new utils */
    private SWBUtils() {
        log.event("Initializing SemanticWebBuilder Base...");
        log.event("-->AppicationPath: " + applicationPath);
        init();
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
        LOCALE_SERVICES = "locale_services";
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
        String log_conf = "log4j.rootLogger=info, stdout" + "\n" +
                "log4j.appender.stdout=org.apache.log4j.ConsoleAppender" + "\n" +
                "log4j.appender.stdout.layout=org.apache.log4j.PatternLayout" + "\n" +
                "log4j.appender.stdout.layout.ConversionPattern=%d %p - %m%n" + "\n" +
                "log4j.logger.org.semanticwb=trace";
        try {
            Properties proper = new Properties();
            proper.load(IO.getStreamFromString(log_conf));
            PropertyConfigurator.configure(proper);
        } catch (Exception e) {
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
         * Raplace match words in a String object
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

        /**
         * Convierte un String a entero, si el estring es nulo o esta mal formado regresa el valor de defecto.
         */
        public static int getInt(String val, int defa) {
            if (val == null) {
                return defa;
            }
            try {
                return Integer.parseInt(val);
            } catch (Exception e) {
                return defa;
            }
        }

        /**
         * Le pone a un objeto String el tipo de codificación especificado por parámetro.
         * Encodes a String object
         * @param data
         * @param enc
         * @throws java.io.UnsupportedEncodingException
         * @throws java.io.IOException
         * @return  */
        public static String encode(String data, String enc) throws java.io.UnsupportedEncodingException, java.io.IOException {
            ByteArrayOutputStream sw = new java.io.ByteArrayOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(sw, enc);
            out.write(data);
            out.flush();
            return new String(sw.toByteArray());
        }

        /**
         * Decodifica un objeto String poniéndole cierta codificación.
         * Decodes a string object
         * @param data
         * @param enc
         * @throws java.io.UnsupportedEncodingException
         * @throws java.io.IOException
         * @return  */
        public static String decode(String data, String enc) throws java.io.UnsupportedEncodingException, java.io.IOException {
            ByteArrayInputStream sw = new ByteArrayInputStream(data.getBytes());
            InputStreamReader in = new InputStreamReader(sw, enc);

            StringBuffer ret = new StringBuffer(data.length());

            char[] bfile = new char[bufferSize];
            int x;
            while ((x = in.read(bfile, 0, bufferSize)) > -1) {
                ret.append(new String(bfile, 0, x));
            }
            in.close();
            return ret.toString();
        }

        public static String encodeBase64(String txt) {
            return SFBase64.encodeString(txt);
        }

        public static String decodeBase64(String txt) {
            return SFBase64.decodeToString(txt);
        }

        /**
         * Convierte un string a una cadena de caracteres en donde la primera letra despues de cada separacion
         * con cualquiera de los caracteres ' ',.,- y _ , sera convertida a mayuscula
         *
         * Converts to UpperCase the firts letter in a word 
         * 
         * @param str String a convertir
         * @return
         */
        public static String toUpperCaseFL(String str) {
            boolean b = true;
            String ret = "";
            for (int x = 0; x < str.length(); x++) {
                char c = str.charAt(x);
                if (b) {
                    ret += Character.toUpperCase(c);
                    b = false;
                } else {
                    ret += c;
                }
                if (c == ' ' || c == '.' || c == '-' || c == '_') {
                    b = true;
                }
            }
            return ret;
        }

        /**
         *  Reemplaza caracteres acentuados y espacios en blanco.
         *  caracteres adicionales son eliminados.
         *  Replace special characters and blank spaces
         *
         * @param txt String a remplazar
         * @param replaceSpaces Si se desea que se remplacen caracteres acentuados
         * @return
         */
        public static String replaceSpecialCharacters(String txt, boolean replaceSpaces) {
            StringBuffer ret = new StringBuffer();
            String aux = txt;
            //aux = aux.toLowerCase();
            aux = aux.replace('Á', 'A');
            aux = aux.replace('Ä', 'A');
            aux = aux.replace('Å', 'A');
            aux = aux.replace('Â', 'A');
            aux = aux.replace('À', 'A');
            aux = aux.replace('Ã', 'A');

            aux = aux.replace('É', 'E');
            aux = aux.replace('Ê', 'E');
            aux = aux.replace('È', 'E');
            aux = aux.replace('Ë', 'E');

            aux = aux.replace('Í', 'I');
            aux = aux.replace('Î', 'I');
            aux = aux.replace('Ï', 'I');
            aux = aux.replace('Ì', 'I');

            aux = aux.replace('Ó', 'O');
            aux = aux.replace('Ö', 'O');
            aux = aux.replace('Ô', 'O');
            aux = aux.replace('Ò', 'O');
            aux = aux.replace('Õ', 'O');

            aux = aux.replace('Ú', 'U');
            aux = aux.replace('Ü', 'U');
            aux = aux.replace('Û', 'U');
            aux = aux.replace('Ù', 'U');

            aux = aux.replace('Ñ', 'N');


            aux = aux.replace('Ç', 'C');
            aux = aux.replace('Ý', 'Y');

            aux = aux.replace('á', 'a');
            aux = aux.replace('à', 'a');
            aux = aux.replace('ã', 'a');
            aux = aux.replace('â', 'a');
            aux = aux.replace('ä', 'a');
            aux = aux.replace('å', 'a');

            aux = aux.replace('é', 'e');
            aux = aux.replace('è', 'e');
            aux = aux.replace('ê', 'e');
            aux = aux.replace('ë', 'e');

            aux = aux.replace('í', 'i');
            aux = aux.replace('ì', 'i');
            aux = aux.replace('î', 'i');
            aux = aux.replace('ï', 'i');

            aux = aux.replace('ó', 'o');
            aux = aux.replace('ò', 'o');
            aux = aux.replace('ô', 'o');
            aux = aux.replace('ö', 'o');
            aux = aux.replace('õ', 'o');

            aux = aux.replace('ú', 'u');
            aux = aux.replace('ù', 'u');
            aux = aux.replace('ü', 'u');
            aux = aux.replace('û', 'u');

            aux = aux.replace('ñ', 'n');

            aux = aux.replace('ç', 'c');
            aux = aux.replace('ÿ', 'y');
            aux = aux.replace('ý', 'y');

            if (replaceSpaces) {
                aux = aux.replace(' ', '_');
            }
            int l = aux.length();
            for (int x = 0; x < l; x++) {
                char ch = aux.charAt(x);
                if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_') {
                    ret.append(ch);
                }
            }
            aux = ret.toString();
            return aux;
        }

        public static String getLocaleString(String Bundle, String key) {
            return getLocaleString(Bundle, key, locale);
        }

        public static String getLocaleString(String Bundle, String key, Locale locale) {
            return getLocaleString(Bundle, key, locale, null);
        }

        public static String getLocaleString(String Bundle, String key, Locale locale, ClassLoader loader) {
            String cad = "";
            try {
                if (loader == null) {
                    cad = java.util.ResourceBundle.getBundle(Bundle, locale).getString(key);
                } else {
                    cad = java.util.ResourceBundle.getBundle(Bundle, locale, loader).getString(key);
                }
            //System.out.println("cad:" + cad);
            } catch (Exception e) {
                log.error("Error while looking for properties key", e);
                return "";
            }
            return cad;
        }

        /**
         * Regresa el lenguaje con el que se este trabajando en la clase (SWBUtils).
         */
        public static Locale getLocale() {
            return locale;
        }

        /**
         * Regresa un arraylist de strings que fueron delimitados por un delimitador (regexp)
         */
        //version 1.4
        public static ArrayList regExpSplit(String txt, String regexp) {
            int index = 0;
            ArrayList matchList = new ArrayList();
            java.util.regex.Matcher m = java.util.regex.Pattern.compile(regexp).matcher(txt);

            while (m.find()) {
                String match = txt.substring(index, m.start());
                if (match.length() > 0) {
                    matchList.add(match);
                }
                match = txt.substring(m.start(), m.end());
                if (match.length() > 0) {
                    matchList.add(match);
                }
                index = m.end();
            }

            String match = txt.substring(index, txt.length());
            if (match.length() > 0) {
                matchList.add(match);
            }
            return matchList;
        }

        /**
         *   Regresa iterador con los string que se encuentren estre las cadenas pre y pos
         *   Ejemplo:
         *   String str="Tag uno:{request.getParameter(\"param1\") tag dos:{request.getParameter(\"param2\")}";
         *   
         *   Iterator it=AFUtils.findInterStr(str, "{request.getParameter(\"","\")");
         *   while(it.hasNext())
         *   {
         *       System.out.println("param:("+it.next()+")");
         *   }
         * 
         */
        public static Iterator findInterStr(String str, String pre, String pos) {
            ArrayList ret = new ArrayList();
            int y = 0;
            do {
                y = findInterStr(str, pre, pos, y, ret);
            } while (y > -1);
            return ret.iterator();
        }

        /**
         * Regresa iterador con los string que se encuentren estre las cadenas pre y pos.
         */
        private static int findInterStr(String str, String pre, String pos, int index, ArrayList arr) {
            int i = str.indexOf(pre, index);
            if (i > -1) {
                i = i + pre.length();
                int j = str.indexOf(pos, i);
                if (j > -1) {
                    arr.add(str.substring(i, j));
                    return j + pos.length();
                }
            }
            return -1;
        }

        /*
         *Regresa un objeto String conteniendo el nombre del número de día en el lenguaje pasado por parámetro., 
        el número de de día se envía por parámetro y comienza con 0 (Domingo).
         */
        public static String getStrDay(int day, String lang) {
            return getLocaleString("locale_date", "day_" + day, new Locale(lang));
        }

        /**
         * Regresa un objeto String conteniendo el nombre del número de mes en el lenguaje pasado por parámetro., 
         * el número de de mes se envía por parámetro y comienza con 0 (Enero).
         */
        public static String getStrMonth(int month, String lang) {
            return getLocaleString("locale_date", "month_" + month, new Locale(lang));
        }

        /**
         * Da formato a una fecha y la regresa en el lenguaje deseado.
         */
        public static String getStrDate(Date date, String lang) {
            return getStrDate(date, lang, null);
        }

        /**
         * Da formato a una fecha y la regresa en el lenguaje deseado.
         */
        public static String getStrDate(Date date, String lang, String format) {
            String ret = "";
            if (format != null) {
                ret = getStrFormat(date, format, lang);
            } else if (lang != null) {
                if (lang.equalsIgnoreCase("es")) {
                    ret = getStrDay(date.getDay(), lang) + " " + date.getDate() + " de " + getStrMonth(date.getMonth(), lang).toLowerCase() + " de " + (date.getYear() + 1900);
                } else if (lang.equalsIgnoreCase("en")) {
                    ret = getStrDay(date.getDay(), lang) + ", " + getStrMonth(date.getMonth(), lang) + " " + date.getDate() + ", " + (date.getYear() + 1900);
                } else {
                    ret = getStrDay(date.getDay(), lang) + ", " + getStrMonth(date.getMonth(), lang) + " " + date.getDate() + ", " + (date.getYear() + 1900);
                }
            } else {
                ret = date.toLocaleString();
            }
            if (ret == null || ret.length() == 0) {
                ret = date.toLocaleString();
            }
            return ret;
        }

        /**
         * Use:
         *   Day:     DAY, Day, day, dd
         *   Month:   MONTH, Month, month, mm
         *   Year:    yyyy, yy
         *   Hours:   hh
         *   Minutes: %m
         *   Seconds: ss
         */
        private static String getStrFormat(Date date, String format, String lang) {
            String ret = format;
            ret = replaceAll(ret, "Day", getStrDay(date.getDay(), lang));
            ret = replaceAll(ret, "DAY", getStrDay(date.getDay(), lang).toUpperCase());
            ret = replaceAll(ret, "day", getStrDay(date.getDay(), lang).toLowerCase());
            ret = replaceAll(ret, "dd", dateCeroComp(date.getDate()));
            ret = replaceAll(ret, "Month", getStrMonth(date.getMonth(), lang));
            ret = replaceAll(ret, "MONTH", getStrMonth(date.getMonth(), lang).toUpperCase());
            ret = replaceAll(ret, "month", getStrMonth(date.getMonth(), lang).toLowerCase());
            ret = replaceAll(ret, "mm", dateCeroComp(date.getMonth() + 1));
            ret = replaceAll(ret, "yy", dateCeroComp(date.getYear() - 100));
            ret = replaceAll(ret, "yyyy", dateCeroComp(date.getYear() + 1900));
            ret = replaceAll(ret, "hh", dateCeroComp(date.getHours()));
            ret = replaceAll(ret, "%m", dateCeroComp(date.getMinutes()));
            ret = replaceAll(ret, "ss", dateCeroComp(date.getSeconds()));
            return ret;
        }

        private static String dateCeroComp(int num) {
            String ret = "" + num;
            if (ret.length() == 1) {
                ret = "0" + ret;
            }
            return ret;
        }
        
        /**
     * Optiene parametros de un url
     * Regresa Map con parametros 
     *  Keys: Strings
     *  Vals: Strings [] 
     */
    public static Map parseQueryParams(String path)
    {
        Map map = new java.util.HashMap();
        if(path==null){
            return map;
        }
        int idx = path.indexOf("?");
        String parms = path.substring(idx+1);
        StringTokenizer st = new StringTokenizer(parms, "&");
        while(st.hasMoreTokens())
        {
            String pair = st.nextToken();
            if(pair.indexOf("=")>0)
            {
                String key = pair.substring(0,pair.indexOf("="));
                String val = pair.substring(pair.indexOf("=")+1);
                map.put(key, new String[]
                {val});
            }
        }
        return map;
    }    
        
    }

    /**
     * 
     */
    public static class IO {
        /*
         * Obtiene un objeto InputStream dado un objeto String.
         */

        public static int getBufferSize() {
            return bufferSize;
        }

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
         *  Copy a InputStream to OutputStream
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
         *  Copy a InputStream to OutputStream
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
         * Returns a String object as a result of InputStream Object
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
         * 
         * Normalice path, replace the "\" character by “/” and remove relative paths
         * 
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

        /**
         * Elimina directorios completos
         * Remove complete directories
         */
        public static boolean removeDirectory(String path) {
            try {
                File dir = new File(path);
                if (dir != null && dir.exists()) {
                    File[] listado = dir.listFiles();
                    for (int i = 0; i < listado.length; i++) {
                        if (listado[i].isFile()) {
                            listado[i].delete();
                        }
                        if (listado[i].isDirectory()) {
                            path = listado[i].getPath();
                            boolean flag = removeDirectory(path);
                            if (flag) {
                                listado[i].delete();
                            }
                        }
                    }
                }
                if (dir.delete()) {
                    return true;
                }
            } catch (Exception e) {
            }
            return false;
        }

        /**
         * Return a file from a path
         * @param path
         * @return
         */
        public static String getFileFromPath(String path) {
            StringBuffer ret = new StringBuffer(8192);
            try {
                InputStream file = null;
                file = new FileInputStream(path);
                byte[] bfile = new byte[8192];
                int x;
                while ((x = file.read(bfile, 0, 8192)) > -1) {
                    ret.append(new String(bfile, 0, x));
                }
                file.close();
            } catch (Exception e) {
            }
            return ret.toString();
        }

        /**
         * Crea un directorio con el nombre de ruta especificada
         * Creates a directory with specified path name
         * @param path
         * @return
         */
        public static boolean createDirectory(String path) {
            try {
                File f = new File(path);
                if (!f.exists()) {
                    f.mkdirs();
                }
                return true;
            } catch (Exception e) {
                log.error(e);
            }
            return false;
        }

        /**
         * Copia una estructura de directorios completa
         * Copy a complete fyle system path
         * @param source path to copy
         * @param target path where the fyle system in the source parameter will be copied
         * @return if the source was copied
         */
        public static boolean copyStructure(String source, String target) {
            try {
                copyStructure(source, target, false, "", "");
                return true;
            } catch (Exception e) {
                log.error(e);
            }
            return false;
        }

        /**
         * Copia una estructura de directorios completa
         * Copy a complete fyle system path
         * @param source path to copy
         * @param target path where the fyle system in the source parameter will be copied
         * @param ChangePath indicates if the target files will be parsed
         * @param sourcePath Indicates a path string in the source files to be changed (parsed)
         * @param targetPath Indicates the path string to be included in the target files in place of source path
         * @return if the source directory was copied succefully
         */
        public static boolean copyStructure(String source, String target, boolean ChangePath, String sourcePath, String targetPath) {
            try {
                File ftarget = new File(target);
                if (!ftarget.exists()) {
                    ftarget.mkdirs();
                }
                File dir = new File(source);
                if (dir != null && dir.exists() && dir.isDirectory()) {
                    File[] listado = dir.listFiles();
                    for (int i = 0; i < listado.length; i++) {
                        try {
                            if (listado[i].isFile()) {
                                File targetFile = new File(target + listado[i].getName());
                                if (targetFile.length() == 0) {
                                    copy(source + listado[i].getName(), target + listado[i].getName(), ChangePath, sourcePath, targetPath);
                                }
                            }
                            if (listado[i].isDirectory()) {
                                String newpath = listado[i].getPath();
                                File f = new File(target + listado[i].getName());
                                f.mkdirs();
                                boolean flag = copyStructure(newpath + "/", target + listado[i].getName() + "/", ChangePath, sourcePath, targetPath);
                                if (flag) {
                                    listado[i].delete();
                                }
                            }
                        } catch (Exception e) {
                            log.error(e);
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e);
                return false;
            }
            return true;
        }

        /**
         * Copia un archivo a otro directorio
         * Copy a fyle to an other directory
         * @param source_name File to copy
         * @param dest_name path where the fyle will be copied
         * @param ChangePath indicates if the target files will be parsed
         * @param sourcePath Indicates a path string in the source files to be changed (parsed)
         * @param targetPath Indicates the path string to be included in the target files in place of source path
         */
        public static void copy(String source_name, String dest_name, boolean ChangePath, String sourcePath, String targetPath) throws IOException {
            File source_file = new File(source_name);
            File destination_file = new File(dest_name);
            FileInputStream source = null;
            java.io.FileOutputStream destination = null;
            try {
                source = new FileInputStream(source_file);
                destination = new FileOutputStream(destination_file);
                if (ChangePath && (source_file.getName().endsWith(".htm") || source_file.getName().endsWith(".html") || source_file.getName().endsWith(".html.orig") || source_file.getName().endsWith(".htm.orig"))) {
                    String content = readInputStream(source);
                    content = content.replaceAll(sourcePath, targetPath);
                    copyStream(getStreamFromString(content), destination);
                } else {
                    copyStream(source, destination);
                }
            } catch (Exception e) {
                log.error(e);
            } finally {
                if (source != null) {
                    try {
                        source.close();
                    } catch (IOException e) {
                    }
                }
                if (destination != null) {
                    try {
                        destination.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        /**
         *  Deserializa y decodifica una objeto (de String Hexadecimal a objeto)
         */
        public static Object decodeObject(String txt) throws IOException, ClassNotFoundException {
            byte arr[] = new byte[txt.length() / 2];
            for (int x = 0; x < txt.length(); x += 2) {
                String val = txt.substring(x, x + 2);
                int v = Integer.decode("0x" + val).intValue();
                if (v > 127) {
                    v = v - 256;
                }
                arr[x / 2] = (byte) (v);
            }
            java.io.ObjectInputStream s = new java.io.ObjectInputStream(new ByteArrayInputStream(arr));
            Object obj = s.readObject();
            return obj;
        }

        /**
         *  Serializa y codifica una objeto a Hexadecimal
         */
        public static String encodeObject(Object obj) throws IOException {
            ByteArrayOutputStream f = new ByteArrayOutputStream();
            ObjectOutput s = new ObjectOutputStream(f);
            s.writeObject(obj);
            s.flush();
            s.close();
            byte arr[] = f.toByteArray();
            String hex = "";
            for (int x = 0; x < arr.length; x++) {
                int v = arr[x];
                if (v < 0) {
                    v = 256 + v;
                }
                String val = Integer.toHexString(v);
                if (val.length() == 1) {
                    val = "0" + val;
                }
                hex += val;
            }
            return hex;
        }

        /**
         * 
         * @param file file to read
         * @return array
         * @throws java.io.FileNotFoundException
         * @throws java.io.IOException
         */
        public static byte[] readFile(File file) throws FileNotFoundException, IOException {
            if (!file.exists()) {
                throw new FileNotFoundException("File Not Found...");
            }
            FileInputStream in = new FileInputStream(file);
            if (in == null) {
                throw new FileNotFoundException("File Not Found...");
            }

            int len = (int) file.length();

            byte[] bfile = new byte[len];
            int x = 0;
            int y = 0;
            while ((x = in.read(bfile, y, len - y)) > -1) {
                y += x;
                if (y == len) {
                    break;
                }
            }
            in.close();
            return bfile;
        }

        public static Iterator<FileItem> fileUpload(javax.servlet.http.HttpServletRequest request, String path2Save) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fu = new ServletFileUpload(factory);
            java.util.List items = null;
            try {
                items = fu.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            if (items != null && path2Save == null) {
                Iterator<FileItem> iter = items.iterator();
                return iter;
            } else if (items != null && path2Save != null) {
                Iterator<FileItem> iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (!item.isFormField()) { //Si No es un campo de forma comun, es un campo tipo file, grabarlo
                        File fichero = new File(path2Save + item.getName());
                        try {
                            item.write(fichero);
                        } catch (Exception e) {
                            log.error(e);
                        }
                    }
                }
                return iter;
            }
            return null;
        }
    }

    /**
     * specialiced email class
     * Jorge Jiménez
     */
    public static class EMAIL {

        private static String smtpserver = null;

        /**
         * Setter for property smtpserver
         * @param smtpserver 
         */
        public static void setSMTPServer(String smtpserver) {
            EMAIL.smtpserver = smtpserver;
        }

        /**
         * Getter for property smtpserver
         * @param smtpserver 
         */
        public static String getSMTPServer() {
            return smtpserver;
        }

        /**
         * 
         * Send an email
         * 
         * @param fromEmail Address which send the email
         * @param fromName  Name who send the email
         * @param address   Collection of addresses to send the email
         * @param ccEmail   Collection of addresses to send the email as copy
         * @param bccEmail  Collection of addresses to send the email as bgCopy
         * @param subject   Subject of email
         * @param data      Email Body Text Data
         * @param contentType HTML sends the body in html format, else it will be send in text format
         * @param login     Login for SMTP server authentication
         * @param password  password for SMTP server authentication
         * @return
         */
        public static String sendMail(String fromEmail, String fromName, Collection address, Collection ccEmail, Collection bccEmail,
                String subject, String contentType, String data, String login, String password, ArrayList<EmailAttachment> attachments) {
            try {
                HtmlEmail email = new HtmlEmail();

                Iterator itAttaches = attachments.iterator();
                while (itAttaches.hasNext()) {
                    EmailAttachment attchment = (EmailAttachment) itAttaches.next();
                    email.attach(attchment);
                }

                email.setHostName(smtpserver);
                email.setFrom(fromEmail, fromName);
                email.setTo(address);
                if (ccEmail != null) {
                    email.setCc(ccEmail);
                }
                if (bccEmail != null) {
                    email.setBcc(bccEmail);
                }
                email.setSubject(subject);

                if (contentType.equalsIgnoreCase("HTML")) {
                    email.setHtmlMsg(data); // set the html message

                } else {
                    email.setMsg(data);
                }

                if (login != null && password != null) {
                    email.setAuthentication(login, password);
                }
                return email.send();
            } catch (Exception e) {
                log.error(e);
            }
            return null;
        }

        /**
         * 
         * Send an email
         * 
         * @param fromEmail Address which send the email
         * @param fromName  Name who send the email
         * @param address   Collection of addresses to send the email
         * @param ccEmail   Collection of addresses to send the email as copy
         * @param bccEmail  Collection of addresses to send the email as bgCopy
         * @param subject   Subject of email
         * @param data      Email Body Text Data
         * @param contentType HTML sends the body in html format, else it will be send in text format
         * @param login     Login for SMTP server authentication
         * @param password  password for SMTP server authentication
         * @return
         */
        public static String sendMail(SWBMail message) throws java.net.SocketException {
            try {
                HtmlEmail email = new HtmlEmail();

                Iterator itAttaches = message.getAttachments().iterator();
                while (itAttaches.hasNext()) {
                    EmailAttachment attchment = (EmailAttachment) itAttaches.next();
                    email.attach(attchment);
                }

                email.setHostName(smtpserver);
                email.setFrom(message.getFromEmail(), message.getFromName());
                email.setTo(message.getAddresses());
                if (message.getCcEmail() != null) {
                    email.setCc(message.getCcEmail());
                }
                if (message.getBccEmail() != null) {
                    email.setBcc(message.getBccEmail());
                }
                email.setSubject(message.getSubject());

                if (message.getContentType().equalsIgnoreCase("HTML")) {
                    email.setHtmlMsg(message.getData()); // set the html message
                } else {
                    email.setMsg(message.getData());
                }

                if (message.getLogin() != null && message.getPassword() != null) {
                    email.setAuthentication(message.getLogin(), message.getPassword());
                }
                return email.send();
            } catch (Exception e) {
                log.error(e);
            }
            return null;
        }

        /**
         * Sends an email in background
         * @param message class
         * @throws java.net.SocketException
         */
        public static void sendBGEmail(String fromEmail, String fromName, Collection address, Collection ccEmail, Collection bccEmail,
                String subject, String contentType, String data, String login, String password, ArrayList<EmailAttachment> attachments) throws java.net.SocketException {
            SWBMail message = new SWBMail();
            message.setFromEmail(fromEmail);
            message.setFromName(fromName);
            message.setAddress((ArrayList) address);
            message.setCcEmail(ccEmail);
            message.setBccEmail(bccEmail);
            message.setSubject(subject);
            message.setContentType(contentType);
            message.setData(data);
            message.setLogin(login);
            message.setPassword(password);
            message.setAttachments(attachments);

            SWBMailSender swbMailSender = new SWBMailSender();
            swbMailSender.addEMail(message);
            swbMailSender.run();
        }

        /**
         * Sends an email in background
         * @param message class
         * @throws java.net.SocketException
         */
        public static void sendBGEmail(SWBMail message) throws java.net.SocketException {
            SWBMailSender swbMailSender = new SWBMailSender();
            swbMailSender.addEMail(message);
            swbMailSender.run();
        }
    }

    /**
     * 
     */
    public static class XML {

        private static XML m_xml = null;
        private DocumentBuilderFactory m_dbf = null;
        private TransformerFactory m_tFactory = null;        // 1. Instantiate an XPathFactory.
        private XPathFactory xpath_factory = null;
        private XPath xpathObj = null;

        public static XPathFactory getXPathFactory() {
            XML xml = getInstance();
            return xml.xpath_factory;
        }

        public static XPath getXPathObject() {
            XML xml = getInstance();
            return xml.xpathObj;
        }

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
                //xpath
                xpath_factory = javax.xml.xpath.XPathFactory.newInstance();
                xpathObj = xpath_factory.newXPath();

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
         * @return  a String object ready to be displayed
         */
        public static String transformDom(Templates tpl, Document doc) throws TransformerException {
            ByteArrayOutputStream sw = new java.io.ByteArrayOutputStream();
            Transformer trans = tpl.newTransformer();
            trans.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        }

        public static boolean xmlVerifier(org.xml.sax.InputSource schema, org.xml.sax.InputSource xml) {
            return xmlVerifier(null, schema, null, xml);
        }

        public static boolean xmlVerifier(String idschema, org.xml.sax.InputSource schema, String idxml, org.xml.sax.InputSource xml) {
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

        public static boolean xmlVerifier(java.io.InputStream schema, java.io.InputStream xml) {
            return xmlVerifier(null, schema, xml);
        }

        public static boolean xmlVerifier(String idschema, java.io.InputStream schema, java.io.InputStream xml) {
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

        /**
         * Transforma un objeto Document con un Template (xslt) especificado, 
         * regresando un objeto String con dicha transformación y listo para ser desplegado.
         * 
         * Transforms a Document object with specified template (xslt)
         * @param sysid
         * @param objschema
         * @param objxml
         * @return a String object ready to be displayed
         */
        private static boolean xmlVerifierImpl(String sysid, Object objschema, Object objxml) {
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
                    log.error("Error WBAdmResourceUtils.XMLVerifier(): Unable to create a new verifier.", e);
                } catch (org.xml.sax.SAXException e) {
                    log.event("Error WBAdmResourceUtils.XMLVerifier(): The input document is not wellformed.", e);
                }
            } catch (Exception e) {
                log.event("Error WBAdmResourceUtils.XMLVerifier(): Unable to parse the schema file.", e);
            }
            return bOk;
        }

        public static boolean xmlVerifier(String schema, String xml) {
            return xmlVerifierByURL(null, schema, xml);
        }

        public static boolean xmlVerifierByURL(String sysid, String schema, String xml) {
            return xmlVerifierImpl(sysid, schema, xml);
        }

        /**
         * Comvierte un Node a Document
         * Converts Node to Document object
         * Todo: Meter en AFUtils
         */
        public static Document node2Document(Node node) throws SWBException {
            // ensure xerces dom
            if (node instanceof org.apache.xerces.dom.DocumentImpl) {
                return (Document) node;
            }
            Document document = getNewDocument();
            if (node instanceof Document) {
                node = ((Document) node).getDocumentElement();
            }
            document.appendChild(document.importNode(node, true));
            return document;
        }

        /**
         * Obtiene el contenido del objeto Document como xml y 
         * lo envía a un archivo especificado (serialización) con codificación UTF-8 e identación de 2.
         * @param dom
         * @param file  */
        public static void DomtoFile(Document dom, String file) {
            domtoFile(dom, file, "UTF-8");
        }

        /**
         * Obtiene el contenido del objeto Document como xml y 
         * lo envía a un archivo especificado (serialización) bajo cierta codificación que se especifique e identación de 2.
         * 
         * Serialize a document object
         * 
         * @param dom
         * @param file
         * @param encode  
         */
        public static void domtoFile(Document dom, String file, String encode) {
            java.io.FileOutputStream osw = null;
            try {
                osw = new FileOutputStream(new java.io.File(file));
                StreamResult streamResult = new StreamResult(osw);

                Transformer transformer = null;
                TransformerFactory tFactory = getTransformerFactory();
                synchronized (tFactory) {
                    transformer = tFactory.newTransformer();
                }
                transformer.setOutputProperty(OutputKeys.ENCODING, encode);
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(new DOMSource(dom), streamResult);
                osw.flush();
                osw.close();
            } catch (Exception e) {
                log.error(e);
            }
        }
        /**
         * An error handler implementation that doesn't report any error.
         */
        private static final org.xml.sax.ErrorHandler silentErrorHandler = new org.xml.sax.ErrorHandler() {

            public void fatalError(org.xml.sax.SAXParseException e) {
            }

            public void error(org.xml.sax.SAXParseException e) {
            }

            public void warning(org.xml.sax.SAXParseException e) {
            }
        };

        /**
         * Replace special characters in xml String
         * @param str Remplaza caracteres especiales en un xml
         * @return
         */
        static public String replaceXMLChars(String str) {
            if (str == null) {
                return null;
            }
            StringBuffer ret = new StringBuffer();

            // split tokens
            StringTokenizer tokenizer = new StringTokenizer(str, " \t@%^&()-+=|\\{}[].;\"<>", true);
            while (tokenizer.hasMoreTokens()) {
                // next token
                String token = tokenizer.nextToken();

                // replace '\t' by the content of "tabulation"
                if (token.startsWith("\t")) {
                    ret.append("    ");
                    continue;
                }

                // replace '<' by '&lt;'
                if (token.startsWith("<")) {
                    ret.append("&lt;");
                    continue;
                }

                // replace '>' by '&gt;'
                if (token.startsWith(">")) {
                    ret.append("&gt;");
                    continue;
                }

                // replace '&' by '&amp;'
                if (token.startsWith("&")) {
                    ret.append("&amp;");
                    continue;
                }
                ret.append(token);
            }
            return ret.toString();

        }

        /**
         * Creates a node as child of other one
         * @param ele Node pather
         * @param name Node name to create
         * @return a new Element (Node)
         */
        static public Element appendChild(Element ele, String name) {
            Document doc = ele.getOwnerDocument();
            Element e = doc.createElement(name);
            ele.appendChild(e);
            return e;
        }

        /**
         * Creates a node as child of other one and assign a value to it
         * @param ele Node pather
         * @param name Node name to create
         * @param value Node Value to create
         * @return a new Element (Node)
         */
        static public Element appendChild(Element ele, String name, String value) {
            Document doc = ele.getOwnerDocument();
            Element e = doc.createElement(name);
            e.appendChild(doc.createTextNode(value));
            ele.appendChild(e);
            return e;
        }

        /**
         * Eval a xpath expression in an input source
         * @param expression xpath xpression
         * @param input input to eval
         * @param resultType Object type to return
         * @return a specified object QName according to XPathConstants object, ej. XPathConstants.NODE)
         * @throws javax.xml.xpath.XPathExpressionException
         */
        public static Object getXpathEval(String expression, InputSource input, QName resultType) throws javax.xml.xpath.XPathExpressionException {
            XPath xpathObj = getXPathObject();
            javax.xml.xpath.XPathExpression xpathExpression = xpathObj.compile(expression);
            return xpathExpression.evaluate(input, resultType);
        }

        /** Asigna un atributo al DOM del recurso.
         * Si no existe el atributo, lo crea y si existe lo modifica
         * @param name String nombre del atributo
         * @param value String valor del atributo
         */
        public static void setAttribute(Document dom, String name, String value) {
            NodeList data = dom.getElementsByTagName(name);
            if (data.getLength() > 0) {
                Node txt = data.item(0).getFirstChild();
                if (txt != null) {
                    if (value != null) {
                        txt.setNodeValue(value);
                    } else {
                        data.item(0).removeChild(txt);
                    }
                } else {
                    if (value != null) {
                        data.item(0).appendChild(dom.createTextNode(value));
                    }
                }
            } else {
                Element res = (Element) dom.getFirstChild();
                Element ele = dom.createElement(name);
                if (value != null) {
                    ele.appendChild(dom.createTextNode(value));
                }
                res.appendChild(ele);
            }
        }

        /** Lee un atributo del DOM del Recurso
         * Si el atributo no esta declarado regresa el valor por defecto defvalue.
         */
        public static String getAttribute(Document dom, String name, String defvalue) {
            String ret = getAttribute(dom, name);
            if (ret == null) {
                ret = defvalue;
            }
            return ret;
        }

        /** Lee un atributo del DOM del Recurso
         * Si el atributo no esta declarado regresa null.
         */
        public static String getAttribute(Document dom, String name) {
            String ret = null;
            NodeList data = dom.getElementsByTagName(name);
            if (data.getLength() > 0) {
                Node txt = data.item(0).getFirstChild();
                if (txt != null) {
                    ret = txt.getNodeValue();
                }
            }
            return ret;
        }
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

    public static class CryptoWrapper {

        public static String passwordDigest(String toEncode) throws NoSuchAlgorithmException {
            if (toEncode.startsWith("{SHA-512}") ||
                    toEncode.startsWith("{SHA}") ||
                    toEncode.startsWith("{SSHA}") ||
                    toEncode.startsWith("{CRYPT}") ||
                    toEncode.startsWith("{SMD5}") ||
                    toEncode.startsWith("{MD5}")) {
                return toEncode;
            }
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            return "{SHA-512}" + new BASE64Encoder().encode(messageDigest.digest(toEncode.getBytes()));
        }

        public static String comparablePassword(String toEncode) throws NoSuchAlgorithmException {
            return comparablePassword(toEncode, "SHA-512");
        }

        public static String comparablePassword(String toEncode, String digestAlgorithm) throws NoSuchAlgorithmException {
            MessageDigest messageDigest = MessageDigest.getInstance(digestAlgorithm);
            return "{" + digestAlgorithm + "}" + new BASE64Encoder().encode(messageDigest.digest(toEncode.getBytes()));
        }

        public static byte[] PBEAES128Cipher(String passPhrese, byte[] data) throws GeneralSecurityException {
            byte[] key = new byte[16];
            byte[] tmp = passPhrese.getBytes();
            int pos = 0;
            while (pos < 16) {
                System.arraycopy(tmp, 0, key, pos, Math.min(16 - pos, tmp.length));
                pos += tmp.length;
            }
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        }

        public static byte[] PBEAES128Decipher(String passPhrese, byte[] data) throws GeneralSecurityException {
            byte[] key = new byte[16];
            byte[] tmp = passPhrese.getBytes();
            int pos = 0;
            while (pos < 16) {
                System.arraycopy(tmp, 0, key, pos, Math.min(16 - pos, tmp.length));
                pos += tmp.length;
            }
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        }
    }
}
