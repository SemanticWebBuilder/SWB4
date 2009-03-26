package org.semanticwb;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.*;
import org.semanticwb.platform.SessionUser;
import org.semanticwb.portal.SWBMonitor;
import org.semanticwb.portal.SWBResourceMgr;
import org.semanticwb.model.SWBRuleMgr;
import org.semanticwb.portal.SWBServiceMgr;
import org.semanticwb.portal.SWBTemplateMgr;
import org.semanticwb.portal.SWBUserMgr;
import org.semanticwb.portal.db.SWBDBAdmLog;
import org.semanticwb.util.JarFile;
import org.semanticwb.util.db.GenericDB;

public class SWBPortal {

    private static Logger log = SWBUtils.getLogger(SWBPortal.class);
    private static SWBPortal instance = null;
    private static HashMap hAnchors = null;
    private static HashMap admFiles = new HashMap();
    private static SWBUserMgr usrMgr;
    private static SWBMonitor monitor = null;
    private static SWBResourceMgr resmgr = null;
    private static SWBTemplateMgr templatemgr = null;
    private static SWBServiceMgr servicemgr = null;
    private static SWBDBAdmLog admlog=null;
    private static HashMap<String, SessionUser> m_sessions;

    static public synchronized SWBPortal createInstance() {
        //System.out.println("Entra a createInstance");
        if (instance == null) {
            instance = new SWBPortal();
        }
        return instance;
    }

    private SWBPortal() {
        log.event("Initializing SemanticWebBuilder Portal...");
        init();
    }
    //Initialize context

    private void init() {
        //Check for GlobalWebSite
        WebSite site = SWBContext.getGlobalWebSite();
        if (site == null) {
            log.event("Creating Global WebSite...");
            site = SWBContext.createWebSite(SWBContext.WEBSITE_GLOBAL, "http://org.semanticwb.globalws#");
            site.setTitle("Global");
            site.setDescription("Global WebSite");
            site.setActive(true);
            //Crear lenguajes por defecto
            Language lang = site.createLanguage("es");
            lang.setTitle("Español", "es");
            lang.setTitle("Spanish", "en");
            lang = site.createLanguage("en");
            lang.setTitle("Ingles", "es");
            lang.setTitle("English", "en");
            //Create HomePage
            WebPage home = site.createWebPage("home");
            site.setHomePage(home);
            home.setActive(true);
            //Create DNS
//            Dns dns = site.createDns("localhost");
//            dns.setTitle("localhost");
//            dns.setDescription("DNS por default", "es");
//            dns.setDescription("Default DNS", "en");
//            dns.setDefault(true);
//            dns.setWebPage(home);
        }
        //Check for GlobalWebSite
        UserRepository urep = SWBContext.getDefaultRepository();
        if (urep == null) {
            log.event("Creating Default User Repository...");
            urep = SWBContext.createUserRepository(SWBContext.USERREPOSITORY_DEFAULT, "http://org.semanticwb.userrep#");
            urep.setTitle("Default UserRepository");
            urep.setDescription("Default UserRpository");
            urep.setProperty(UserRepository.SWBUR_AuthMethod, "FORM"); //BASIC
            urep.setProperty(UserRepository.SWBUR_LoginContext, "swb4TripleStoreModule");
            urep.setProperty(UserRepository.SWBUR_CallBackHandlerClassName, "org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");
            site.setUserRepository(urep);
            //Create User
            User user = urep.createUser();
            user.setUsrLogin("admin");
            user.setUsrPassword("webbuilder");
            user.setUsrEmail("admin@semanticwebbuilder.org");
            user.setUsrFirstName("Admin");
            user.setLanguage("es");
            user.setActive(true);
        }

        site = SWBContext.getAdminWebSite();
        if (site == null)
        {
            log.event("Creating Admin WebSite...");
            SWBPlatform.getSemanticMgr().createModel(SWBContext.WEBSITE_ADMIN, "http://www.semanticwb.org/SWBAdmin#");
        }

        //Crear tablas LOGS
        try {
            Connection con = SWBUtils.DB.getDefaultConnection();
            Statement st = con.createStatement();
            try {
                ResultSet rs = st.executeQuery("select count(*) from swb_admlog");
                if (rs.next()) {
                    int x = rs.getInt(1);
                }
                rs.close();
            } catch (SQLException ne) {
                //ne.printStackTrace();
                log.event("Creating Logs Tables...");
                GenericDB db = new GenericDB();
                String xml = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath() + "/WEB-INF/xml/swb_logs.xml");
                //System.out.println("xml:"+xml);
                db.executeSQLScript(xml, SWBUtils.DB.getDatabaseName(), null);
            }
            st.close();
            con.close();
        } catch (SQLException e) {
            log.error(e);
        }

        m_sessions = new HashMap();

        usrMgr = new SWBUserMgr();
        usrMgr.init();

        monitor = new SWBMonitor();
        monitor.init();

        resmgr = new SWBResourceMgr();
        resmgr.init();

        templatemgr = new SWBTemplateMgr();
        templatemgr.init();

        servicemgr = new SWBServiceMgr();
        servicemgr.init();

        admlog = new SWBDBAdmLog();
        admlog.init();

        //Inicializa el RuleMgr
        Rule.getRuleMgr();

        try {
            log.debug("Loading admin Files from: /WEB-INF/lib/SWBAdmin.jar");
            String zipPath = SWBUtils.getApplicationPath() + "/WEB-INF/lib/SWBAdmin.jar";
            ZipFile zf = new ZipFile(zipPath);
            Enumeration e = zf.entries();
            while (e.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) e.nextElement();
                log.debug("/" + ze.getName() + ", " + ze.getSize() + ", " + ze.getTime());
                admFiles.put("/" + ze.getName(), new JarFile(ze, zipPath));
            }
            zf.close();
        //log.event("-->Admin Files in Memory:\t" + admFiles.size());
        } catch (Exception e) {
            log.warn("Error loading files for Webbuilder Administration:" + SWBUtils.getApplicationPath() + "/WEB-INF/lib/SWBAdmin.jar", e);
        }

        try {
            log.debug("Loading admin Files from: /WEB-INF/lib/dojo.jar");
            String zipPath = SWBUtils.getApplicationPath() + "/WEB-INF/lib/dojo.jar";
            ZipFile zf = new ZipFile(zipPath);
            Enumeration e = zf.entries();
            while (e.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) e.nextElement();
                log.debug("/" + ze.getName() + ", " + ze.getSize() + ", " + ze.getTime());
                admFiles.put("/" + ze.getName(), new JarFile(ze, zipPath));
            }
            zf.close();
            log.event("-->Admin Files in Memory:\t" + admFiles.size());
        } catch (Exception e) {
            log.warn("Error loading files for Webbuilder Administration:" + SWBUtils.getApplicationPath() + "/WEB-INF/lib/dojo.zip", e);
        }
    }

    private void createAdminSite()
    {
        WebSite site = SWBContext.getAdminWebSite();
        if (site == null)
        {
            log.event("Creating Admin WebSite...");
            site = SWBContext.createWebSite(SWBContext.WEBSITE_ADMIN, "http://www.semanticwb.org/SWBAdmin#");
            site.setTitle("Admin");
            site.setDescription("Admin WebSite");
            site.setActive(true);
            //Crear lenguajes por defecto
            Language lang = site.createLanguage("es");
            lang.setTitle("Español", "es");
            lang.setTitle("Spanish", "en");
            lang = site.createLanguage("en");
            lang.setTitle("Ingles", "es");
            lang.setTitle("English", "en");
            //Create HomePage
            WebPage home = site.createWebPage("home");
            site.setHomePage(home);
            home.setActive(true);
            //Create DNS
            Dns dns = site.createDns();
            dns.setDns("localhost");
            //dns.setDescription("DNS por default", "es");
            //dns.setDescription("Default DNS", "en");
            dns.setDefault(true);
            dns.setWebPage(home);
        }
    }

    public static String getDistributorPath() {
        return SWBPlatform.getContextPath() + "/" + SWBPlatform.getEnv("swb/distributor", "swb");
    }

    public static SWBUserMgr getUserMgr() {
        return usrMgr;
    }

    public static SWBMonitor getMonitor() {
        return monitor;
    }

    public static SWBResourceMgr getResourceMgr() {
        return resmgr;
    }

    public static SWBTemplateMgr getTemplateMgr() {
        return templatemgr;
    }

    public static SWBServiceMgr getServiceMgr() {
        return servicemgr;
    }

    public static SWBDBAdmLog getDBAdmLog()
    {
        return admlog;
    }

    public static JarFile getAdminFile(String path) {
        JarFile f = new JarFile(path);
        if (!f.exists()) {
            JarFile aux = (JarFile) admFiles.get(path);
            if (aux != null) {
                f = aux;
            }
        }
//        JarFile f = (JarFile) admFiles.get(path);
//        if (f == null) {
//            f = new JarFile(path);
//        }
        return f;
    }

    public static InputStream getAdminFileStream(String path) {
        JarFile f = (JarFile) admFiles.get(path);
        if (f == null) {
            f = new JarFile(path);
        }
        if (!f.exists()) {
            return null;
        }
        return f.getInputStream();
    }

    public static ArrayList getAppLanguages() {
        ArrayList languages = new ArrayList();
        Iterator<WebSite> it = SWBContext.listWebSites();
        while (it.hasNext()) {
            WebSite site = it.next();
            Iterator<Language> itLang = site.listLanguages();
            while (itLang.hasNext()) {
                Language lang = itLang.next();
                if (!languages.contains(lang.getId())) {
                    languages.add(lang.getId());
                }
            }
        }
        return languages;
    }

    public static void setSessionUser(User user) {
        //if (user != null) {
            SessionUser sess = m_sessions.get(Thread.currentThread().getName());
            if (sess == null) {
                m_sessions.put(Thread.currentThread().getName(), new SessionUser(user));
            } else {
                sess.setUser(user);
            }
        //}
    }

    public static User getSessionUser() {
        Principal user = null;
        SessionUser sess = m_sessions.get(Thread.currentThread().getName());
        if (sess != null) {
            user = sess.getUser();
        }
        return (User) user;
    }

    public static long getSessionUserID() {
        long ret = 0;
        Principal user = null;
        SessionUser sess = m_sessions.get(Thread.currentThread().getName());
        if (sess != null) {
            ret = sess.geRequestID();
        }
        return ret;
    }

    public static class UTIL {

        public static String parseHTML(String datos, String ruta) {
            return parseHTML(datos, ruta, 0);
        }

        /**
         * @param datos
         * @param ruta
         * @return  */
        public static String parseHTML(String datos, String ruta, int pages) {
            hAnchors = new HashMap();
            //detección de si el contenido es de word
            boolean iswordcontent = false;
            int posword = -1;
            posword = datos.toLowerCase().indexOf("name=generator content=\"microsoft word");
            if (posword > -1) {
                iswordcontent = true;
            }
            //termina detección de si es contenido de word

            HtmlTag tag = new HtmlTag();
            int pos = -1;
            int pos1 = -1;
            StringBuffer ret = new StringBuffer();
            try {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
                    int ttype = tok.getTokenType();
                    //if (ttype==HtmlStreamTokenizer.TT_COMMENT) continue;
                    if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) {
                        if (ttype == HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->")) {
                            continue;
                        }
                        tok.parseTag(tok.getStringValue(), tag);

                        if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                            continue;
                        }
                        if ((tag.getTagString().toLowerCase().equals("img") || tag.getTagString().toLowerCase().equals("applet") || tag.getTagString().toLowerCase().equals("script") || tag.getTagString().toLowerCase().equals("tr") || tag.getTagString().toLowerCase().equals("td") || tag.getTagString().toLowerCase().equals("table") || tag.getTagString().toLowerCase().equals("body") || tag.getTagString().toLowerCase().equals("input") || tag.getTagString().toLowerCase().equals("a") || tag.getTagString().toLowerCase().equals("form") || tag.getTagString().toLowerCase().equals("area") || tag.getTagString().toLowerCase().equals("meta") || tag.getTagString().toLowerCase().equals("bca") || tag.getTagString().toLowerCase().equals("link") || tag.getTagString().toLowerCase().equals("param") || tag.getTagString().toLowerCase().equals("embed") || tag.getTagString().toLowerCase().equals("iframe") || tag.getTagString().toLowerCase().equals("frame")) && !tok.getRawString().startsWith("<!--")) {
                            if (!tag.isEndTag()) {
                                ret.append("<");
                                ret.append(tag.getTagString());
                                ret.append(" ");
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                String actionval = "";
                                while (en.hasMoreElements()) {
                                    boolean bwritestyle = true;
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);

                                    //revisar si no tiene repercuciones
                                    value = value.replace('\\', '/');
                                    value = SWBUtils.TEXT.replaceAll(value, "%3f", "?");
                                    value = SWBUtils.TEXT.replaceAll(value, "%3F", "?");

                                    String sruta = null;
                                    if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.startsWith("https://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("rtsp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && !value.startsWith("../") && !value.startsWith("{")) {
                                        if (!tag.getTagString().toLowerCase().equals("input") && !value.toLowerCase().equals("true") && !value.toLowerCase().equals("false") && value.indexOf(".") > -1) {
                                            sruta = ruta;
                                        }
                                        //poner solo archivo
                                        if (((pos = value.indexOf("/")) > -1) || (pos = value.indexOf("\\")) > -1) {
                                            value = findFileName(value);
                                        }
                                    } else if (name.toLowerCase().equals("href") && value.startsWith("../")) {
                                        value = "/" + takeOffString(value, "../");
                                    } else if (name.toLowerCase().equals("href") && value.startsWith("#_") && pages > 1) { //Es un ancla
                                        int page = findAnchorInContent(datos, value, pages);
                                        if (page > 0) {
                                            value = "?page=" + page + "&" + value;
                                        }
                                    } else if (name.toLowerCase().equals("onmouseover") || name.toLowerCase().equals("onload") || name.toLowerCase().equals("onmouseout") || name.toLowerCase().equals("onclick")) {
                                        String out = findImagesInScript(value, ".gif'", ruta);
                                        out = findImagesInScript(out, ".jpg'", ruta);
                                        //ret.append(ruta);
                                        if (!out.equals("")) {
                                            value = out;
                                        }
                                    } else if (tag.getTagString().toLowerCase().equals("body") && iswordcontent && (name.equals("link") || name.equals("vlink"))) { //elimina los liks
                                        bwritestyle = false;
                                    }
                                    if (bwritestyle) {
                                        ret.append(name);
                                        ret.append("=\"");
                                        if (sruta != null) {
                                            ret.append(sruta);
                                        }
                                        ret.append(value);
                                        ret.append("\" ");
                                    }
                                }
                                if (tag.isEmpty()) {
                                    ret.append("/");
                                }
                                ret.append(">");
                                if (tag.getTagString().toLowerCase().equals("form")) {
                                    ret.append(actionval);
                                }
                            } else {
                                ret.append(tok.getRawString());
                            }
                        } else {
                            ret.append(tok.getRawString());
                        }
                    } else if (ttype == HtmlStreamTokenizer.TT_TEXT) {
                        ret.append(tok.getRawString());
                    }
                }
            } catch (NumberFormatException f) {
                log.error(f);
            } catch (Exception e) {
                log.error(e);
            }
            return ret.toString();
        }

        private static String findFileName(String value) {
            //System.out.println("value:"+value);
            String out = "";
            if (value.startsWith("../")) {
                out = takeOffString(value, "../");
                if (!out.equals("")) {
                    value = out;
                }
            }
            int x = value.lastIndexOf(".");
            if (x > -1) {
                int y = value.lastIndexOf("\\", x);
                if (y > -1) {
                    //System.out.println("entra a y:"+x);
                    value = value.substring(y + 1);
                }
                y = value.lastIndexOf("/", x);
                if (y > -1) {
                    //System.out.println("entra a y :"+y);
                    value = value.substring(y + 1);
                }
            }
            //System.out.println("regresa:"+value);
            return value;
        }

        public static String takeOffString(String value, String takeOff) {
            int pos = -1;
            do {
                pos = value.indexOf(takeOff);
                if (pos != -1) {
                    value = value.substring(pos + takeOff.length());
                }
            } while (pos != -1);
            return value;
        }

        private static int findAnchorInContent(String content, String ancla, int pages) {
            ancla = ancla.substring(1);
            Integer page = (Integer) hAnchors.get(ancla);
            if (page != null) { //existe en hash de anclas
                return page.intValue();
            } else {
                for (int i = 0; i <= pages; i++) {
                    if (findAnchorInContentPage(content, ancla, i, pages)) {
                        return i;
                    }
                }
            }
            return 0;
        }

        /**
         * @param datos
         * @param ruta
         */
        public static boolean findAnchorInContentPage(String datos, String ancla, int page, int itpages) {
            HtmlTag tag = new HtmlTag();
            boolean flag = false;
            boolean flag1 = false;
            boolean flag2 = false;
            try {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new java.io.ByteArrayInputStream(datos.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
                    int ttype = tok.getTokenType();
                    if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) {
                        tok.parseTag(tok.getStringValue(), tag);
                        if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                            continue;
                        }
                        if (tag.getTagString().toLowerCase().equals("div")) {
                            flag1 = true;
                            if (!tag.isEndTag()) {
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                while (en.hasMoreElements()) {
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);
                                    if (name.toLowerCase().equals("class")) {
                                        if (value.toLowerCase().equals("section" + page)) {
                                            flag = true;
                                        }
                                    } else if (flag) {
                                        flag2 = true;
                                    }
                                }
                            } else {
                                if (flag && !flag2) {
                                    //entra a este if y se rompe el ciclo solo si la página actual es menos al total de páginas encontradas en el documento,
                                    //si es igual, entonces no lo rompe y se termina hasta que se acaba el html, para que funcionen los pie de página si existen
                                    //al final del dicumento
                                    if (page < itpages) {
                                        break;
                                    }
                                } else if (flag && flag2) {
                                    flag2 = false;
                                }
                            }
                        } else if (flag1 && flag) {
                            if (tag.getTagString().toLowerCase().equals("a")) {
                                if (!tag.isEndTag()) {
                                    Enumeration en = tag.getParamNames();
                                    String name = "";
                                    String value = "";
                                    String actionval = "";
                                    while (en.hasMoreElements()) {
                                        name = (String) en.nextElement();
                                        value = tag.getParam(name);
                                        if (name.toLowerCase().equals("name") && value.equals(ancla)) { //emcontrado
                                            hAnchors.put(value, new Integer(page));
                                            return true;
                                        } else if (name.toLowerCase().equals("name") && value.startsWith("_")) { //es una ancla, guardarla en hash de anclas
                                            if (hAnchors.get(value) == null) {
                                                hAnchors.put(value, new Integer(page));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
            return false;
        }

        private static String findImagesInScript(String value, String ext, String ruta) {
            StringBuffer aux = new StringBuffer(value.length());
            int off = 0;
            int f = 0;
            int i = 0;
            int j = 0;
            do {
                f = value.indexOf(ext, off);
                i = value.lastIndexOf("'", f);
                j = value.lastIndexOf("/", f);
                if (f > 0 && i >= 0) {
                    i++;
                    if (value.startsWith("/", i) || value.startsWith("http://", i)) {
                        aux.append(value.substring(off, f + ext.length()));
                    } else if (j > -1) {
                        aux.append(value.substring(off, i) + ruta + value.substring(j + 1, f + ext.length()));
                    } else {
                        aux.append(value.substring(off, i) + ruta + value.substring(i, f + ext.length()));
                    }
                    off = f + ext.length();
                }
            } while (f > 0 && i > 0);
            aux.append(value.substring(off));
            return aux.toString();
        }

        /**
         * @param datos
         * @param ruta
         * @return  */
        public static String parseXsl(String datos, String ruta) {
            HtmlTag tag = new HtmlTag();
            int pos = -1;
            int pos1 = -1;
            StringBuffer ret = new StringBuffer();
            try {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
                    int ttype = tok.getTokenType();
                    //if (ttype==HtmlStreamTokenizer.TT_COMMENT) continue;
                    if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) {
                        if (ttype == HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->")) {
                            continue;
                        }
                        tok.parseTag(tok.getStringValue(), tag);

                        if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                            continue;
                        }
                        //if (tag.getTagString().toLowerCase().startsWith("o:")){System.out.println("o:Salto");   continue;}
                        if (tag.getTagString().toLowerCase().equals("img") || tag.getTagString().toLowerCase().equals("applet") || tag.getTagString().toLowerCase().equals("script") || tag.getTagString().toLowerCase().equals("td") || tag.getTagString().toLowerCase().equals("table") || tag.getTagString().toLowerCase().equals("body") || tag.getTagString().toLowerCase().equals("input") || tag.getTagString().toLowerCase().equals("a") || tag.getTagString().toLowerCase().equals("form") || tag.getTagString().toLowerCase().equals("area") || tag.getTagString().toLowerCase().equals("meta") || tag.getTagString().toLowerCase().equals("bca") || tag.getTagString().toLowerCase().equals("link") || tag.getTagString().toLowerCase().equals("param") || tag.getTagString().toLowerCase().equals("embed") || tag.getTagString().toLowerCase().equals("iframe") || tag.getTagString().toLowerCase().equals("frame")) {

                            if (!tag.isEndTag()) {
                                ret.append("<");
                                ret.append(tag.getTagString());
                                ret.append(" ");
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                String actionval = "";
                                while (en.hasMoreElements()) {
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);
                                    ret.append(name);
                                    ret.append("=\"");
                                    if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("rtsp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && !value.startsWith("{")) {
                                        if (!tag.getTagString().toLowerCase().equals("input") && !value.toLowerCase().equals("true") && !value.toLowerCase().equals("false") && value.indexOf(".") > -1) {
                                            ret.append(ruta);
                                        }
                                        //poner solo archivo
                                        if (((pos = value.indexOf("/")) > -1) || (pos = value.indexOf("\\")) > -1) {
                                            value = findFileName(value);
                                        }
                                    }
                                    if (name.toLowerCase().equals("onmouseover") || name.toLowerCase().equals("onload") || name.toLowerCase().equals("onmouseout") || name.toLowerCase().equals("onclick")) {
                                        String out = findImagesInScript(value, ".gif'", ruta);
                                        out = findImagesInScript(out, ".jpg'", ruta);
                                        //ret.append(ruta);
                                        if (!out.equals("")) {
                                            value = out;
                                        }
                                    //System.out.println("out:"+out);
                                    }
                                    ret.append(value);
                                    ret.append("\" ");
                                }
                                //if(tag.getTagString().toLowerCase().equals("img") && tok.getStringValue().toString().endsWith("/")) {
                                if (tag.isEmpty()) {
                                    ret.append("/");
                                }

                                ret.append(">");

                                if (tag.getTagString().toLowerCase().equals("form")) {
                                    ret.append(actionval);
                                }
                            } else {
                                ret.append(tok.getRawString());
                            }
                        } else {
                            ret.append(tok.getRawString());
                        }
                    } else if (ttype == HtmlStreamTokenizer.TT_TEXT) {
                        ret.append(tok.getRawString());
                    }
                }
            } catch (NumberFormatException f) {
                log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBUtils_decodifica"), f);
            } catch (Exception e) {
                log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBUtils_IOHTML"), e);
            }
            return ret.toString();
        }

        /**
         * @param datos
         * @param ruta
         */
        public static String FindAttaches(String datos) {
            HtmlTag tag = new HtmlTag();
            StringBuffer ret = new StringBuffer();
            Vector vvector = new Vector();
            try {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {

                    int ttype = tok.getTokenType();
                    if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) {
                        tok.parseTag(tok.getStringValue(), tag);

                        if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                            continue;
                        }
                        //if (tag.getTagString().toLowerCase().startsWith("o:")){System.out.println("o:Salto");   continue;}
                        if (tag.getTagString().toLowerCase().equals("img") || tag.getTagString().toLowerCase().equals("applet") || tag.getTagString().toLowerCase().equals("script") || tag.getTagString().toLowerCase().equals("td") || tag.getTagString().toLowerCase().equals("table") || tag.getTagString().toLowerCase().equals("body") || tag.getTagString().toLowerCase().equals("input") || tag.getTagString().toLowerCase().equals("a") || tag.getTagString().toLowerCase().equals("area") || tag.getTagString().toLowerCase().equals("link") || tag.getTagString().toLowerCase().equals("param") || tag.getTagString().toLowerCase().equals("embed")) {
                            if (!tag.isEndTag()) {
                                //ret.append("<");
                                //ret.append(tag.getTagString());
                                //ret.append(" ");
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                String actionval = "";
                                while (en.hasMoreElements()) {
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);
                                    String out = null;
                                    if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && !value.startsWith("{")) {
                                        String stype = "";
                                        if (tag.getTagString().toLowerCase().equals("input")) {
                                            stype = tag.getParam("type").toLowerCase();
                                        }
                                        if (!value.startsWith("http://") && !value.startsWith("https://") && (!tag.getTagString().toLowerCase().equals("input") || (tag.getTagString().toLowerCase().equals("input") && stype.equals("image")))) {
                                            if (value.toLowerCase().endsWith(".gif") || value.toLowerCase().endsWith(".jpg") || value.toLowerCase().endsWith(".jpeg") || value.toLowerCase().endsWith(".bmp") ||
                                                    value.toLowerCase().endsWith(".doc") || value.toLowerCase().endsWith(".htm") || value.toLowerCase().endsWith(".html") || value.toLowerCase().endsWith(".zip") ||
                                                    value.toLowerCase().endsWith(".txt") || value.toLowerCase().endsWith(".pdf") || value.toLowerCase().endsWith(".xls") || value.toLowerCase().endsWith(".ppt") ||
                                                    value.toLowerCase().endsWith(".xsl") || value.toLowerCase().endsWith(".xslt") || value.toLowerCase().endsWith(".bin") || value.toLowerCase().endsWith(".tar")) {
                                                out = value;
                                            }
                                        }
                                    } else if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && value.startsWith("{")) {
                                        int pos = -1;
                                        pos = value.indexOf("}");
                                        if (pos != -1) {
                                            out = value.substring(pos + 1);
                                        }
                                    } else if (name.toLowerCase().equals("href") && value.startsWith("/")) {
                                        out = value;
                                    } else if (name.toLowerCase().equals("onmouseover")) {
                                        //if(!value.startsWith("http://") && !value.startsWith("https://"))
                                        int pos = -1, pos1 = -1;
                                        pos = value.indexOf("http://");
                                        pos1 = value.indexOf("https://");
                                        if (pos < 0 && pos1 < 0) {
                                            out = findImageInScript1(value, ".gif'", "");
                                            out = findImageInScript1(out, ".jpg'", "");
                                        }
                                    }
                                    if (out != null) {
                                        boolean flag = false;
                                        for (int i = 0; i < vvector.size(); i++) {
                                            if (out.equals((String) vvector.elementAt(i))) {
                                                flag = true;
                                            }
                                        }
                                        if (!flag) {
                                            vvector.addElement(out);
                                        }
                                    }

                                //ret.append("\" ");
                                }
                            //ret.append(">");
                            //if(tag.getTagString().toLowerCase().equals("form")) ret.append(actionval);
                            } else {
                                //ret.append(tok.getRawString());
                            }
                        } else {
                            //ret.append(tok.getRawString());
                        }
                    } else if (ttype == HtmlStreamTokenizer.TT_TEXT) {
                        //ret.append(tok.getRawString());
                    }
                }
                for (int i = 0; i < vvector.size(); i++) {
                    ret.append((String) vvector.elementAt(i) + ";");
                }
            } catch (NumberFormatException f) {
                log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBUtils_decodifica"), f);
            } catch (Exception e) {
                log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBUtils_IOHTML"), e);
            }
            //System.out.println("entra a FindAttaches regresando:"+ret.toString());
            return ret.toString();
        }

        private static String findImageInScript1(String value, String ext, String ruta) {
            int f = value.indexOf(ext);
            int i = value.lastIndexOf("'", f);
            int j = value.lastIndexOf("'");
            if (f > 0 && i >= 0) {
                i++;
                if (value.startsWith("/", i) || value.startsWith("http://", i)) {
                    return value;
                } else {
                    return value.substring(i, j);
                }
            }
            return value;
        }

        private final String ALPHABETH = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        private String getRandString(int size) {
            StringBuilder sb = new StringBuilder(size);
            for (int i = 0; i < size; i++) {
                sb.append(ALPHABETH.charAt((int) (Math.random() * ALPHABETH.length())));
            }
            return sb.toString();
        }

        public void sendValidateImage(HttpServletRequest request, HttpServletResponse response, int size, String cad) throws ServletException, IOException {
            String cadena = null;
            if (null==cad) cadena = getRandString(size);
            else cadena = cad;
            request.getSession(true).setAttribute("swb_valCad", cadena);
            BufferedImage buffer = new BufferedImage(150, 40, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = buffer.createGraphics();
            g.setBackground(new Color(255, 255, 255));
            g.clearRect(0, 0, 150, 40);
            g.setColor(new Color(0, 0, 0));
            Font f = new Font("Serif",Font.BOLD,25);
            //g.setFont(g.getFont().deriveFont(Font.ROMAN_BASELINE, 25.0f));
            g.setFont(f);
            g.drawString(cadena, 15, 30);

            for (int i = 0; i < 150; i += 10) {
                g.drawLine(i, 0, i, 39);
            }
            for (int i = 0; i < 40; i += 10) {
                g.drawLine(0, i, 149, i);
            }

            try {
                org.semanticwb.base.util.GIFEncoder encoder = new org.semanticwb.base.util.GIFEncoder(buffer);
               // AFUtils.log("Cadena: "+cadena);
                response.setContentType("image/gif");
                encoder.Write(response.getOutputStream());
            } catch (AWTException e) {
                log.error(e);
            }
        }
    }
}