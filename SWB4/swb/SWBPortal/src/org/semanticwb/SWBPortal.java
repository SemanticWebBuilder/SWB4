package org.semanticwb;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.semanticwb.model.*;
import org.semanticwb.portal.SWBDBAdmLog;
import org.semanticwb.portal.SWBMonitor;
import org.semanticwb.portal.SWBResourceMgr;
import org.semanticwb.portal.SWBTemplateMgr;
import org.semanticwb.portal.SWBUserMgr;
import org.semanticwb.portal.services.CalendarSrv;
import org.semanticwb.portal.services.CampSrv;
import org.semanticwb.portal.services.DeviceSrv;
import org.semanticwb.portal.services.DnsSrv;
import org.semanticwb.portal.services.GroupSrv;
import org.semanticwb.portal.services.IPFilterSrv;
import org.semanticwb.portal.services.LanguageSrv;
import org.semanticwb.portal.services.PFlowSrv;
import org.semanticwb.portal.services.ResourceSrv;
import org.semanticwb.portal.services.RoleSrv;
import org.semanticwb.portal.services.RuleSrv;
import org.semanticwb.portal.services.SWBServices;
import org.semanticwb.portal.services.TemplateSrv;
import org.semanticwb.portal.services.WebPageSrv;
import org.semanticwb.portal.services.WebSiteSrv;
import org.semanticwb.util.JarFile;

public class SWBPortal {

    private static Logger log = SWBUtils.getLogger(SWBPortal.class);
    private static SWBPortal instance = null;
    private HashMap hAnchors = null;
    private HashMap admFiles = new HashMap();
    private static SWBUserMgr usrMgr;
    private static SWBMonitor monitor = null;
    private static SWBResourceMgr resmgr = null;
    private static SWBTemplateMgr templatemgr = null;

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
            site = SWBContext.createWebSite(SWBContext.WEBSITE_GLOBAL, "http://org.semanticwb.globalws");
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
            Dns dns = site.createDns("localhost");
            dns.setTitle("localhost");
            dns.setDescription("DNS por default", "es");
            dns.setDescription("Default DNS", "en");
            dns.setDefault(true);
            dns.setWebPage(home);
        }
        //Check for GlobalWebSite
        UserRepository urep = SWBContext.getDefaultRepository();
        if (urep == null) {
            log.event("Creating Default User Repository...");
            urep = SWBContext.createUserRepository(SWBContext.USERREPOSITORY_DEFAULT, "http://org.semanticwb.userrep");
            urep.setTitle("Default UserRepository");
            urep.setDescription("Default UserRpository");
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

        usrMgr = new SWBUserMgr();
        usrMgr.init();

        monitor = new SWBMonitor();
        monitor.init();

        resmgr = new SWBResourceMgr();
        resmgr.init();

        templatemgr = new SWBTemplateMgr();
        templatemgr.init();

        try {
            log.debug("Loading admin Files from: /WEB-INF/lib/SWBAdmin.jar");
            String zipPath = SWBUtils.getApplicationPath() + "/WEB-INF/lib/WBAdmin.jar";
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
            log.warn("Error loading files for Webbuilder Administration:" + SWBUtils.getApplicationPath() + "/WEB-INF/lib/WBAdmin.jar");
        }
    }

    public static String getDistributorPath() {
        return SWBPlatform.getContextPath() + "/" + SWBPlatform.getEnv("swb/distributor", "swb");
    }

    public static SWBServices getSWBServices() {
        SWBServices swbServices = new SWBServices();
        return swbServices;
    }

    public static DnsSrv getDnsSrv() {
        DnsSrv dnsSrv = new DnsSrv();
        return dnsSrv;
    }

    public static DeviceSrv getDeviceSrv() {
        DeviceSrv deviceSrv = new DeviceSrv();
        return deviceSrv;
    }

    public static CalendarSrv getCalendarSrv() {
        CalendarSrv calendarSrv = new CalendarSrv();
        return calendarSrv;
    }

    public static GroupSrv getGrouprv() {
        GroupSrv groupSrv = new GroupSrv();
        return groupSrv;
    }

    public static ResourceSrv getResourcerv() {
        ResourceSrv resSrv = new ResourceSrv();
        return resSrv;
    }

    public static IPFilterSrv getIPFilterSrv() {
        IPFilterSrv iPFilterSrv = new IPFilterSrv();
        return iPFilterSrv;
    }

    public static RoleSrv getRoleSrv() {
        RoleSrv roleSrv = new RoleSrv();
        return roleSrv;
    }

    public static RuleSrv getRuleSrv() {
        RuleSrv ruleSrv = new RuleSrv();
        return ruleSrv;
    }

    public static TemplateSrv getTemplateSrv() {
        TemplateSrv templateSrv = new TemplateSrv();
        return templateSrv;
    }

    public static WebPageSrv getWebPageSrv() {
        WebPageSrv webPageSrv = new WebPageSrv();
        return webPageSrv;
    }

    public static WebSiteSrv getWebSiteSrv() {
        WebSiteSrv webSiteSrv = new WebSiteSrv();
        return webSiteSrv;
    }

    public static LanguageSrv getLanguageSrv() {
        LanguageSrv langSrv = new LanguageSrv();
        return langSrv;
    }

    public static CampSrv getCampSrv() {
        CampSrv campSrv = new CampSrv();
        return campSrv;
    }

    public static PFlowSrv getPFlowSrv() {
        PFlowSrv pFlowSrv = new PFlowSrv();
        return pFlowSrv;
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

    /**
     * Logeo de acciones
     * @param userID
     * @param action
     * @param uri
     * @param object
     * @param description
     * @param date
     */
    public static void log(String userID, String action, String uri, String object, String description, Timestamp date) {
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(userID, action, uri, object, description, date);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            log.error(e);
        }
    }

    public static JarFile getAdminFile(String path) {
        JarFile f = (JarFile) instance.admFiles.get(path);
        if (f == null) {
            f = new JarFile(path);
        }
        return f;
    }

    public static InputStream getAdminFileStream(String path) {
        JarFile f = (JarFile) instance.admFiles.get(path);
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
    
    public String parseHTML(String datos, String ruta)
    {
        return parseHTML(datos,ruta,0);
    }

    /**
     * @param datos
     * @param ruta
     * @return  */
    public String parseHTML(String datos, String ruta, int pages) {
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

     private String findFileName(String value)
    {
        //System.out.println("value:"+value);
        String out="";
        if(value.startsWith("../"))
        {
            out=takeOffString(value,"../");
            if(!out.equals(""))value=out;
        }
        int x=value.lastIndexOf(".");
        if(x>-1)
        {
            int y=value.lastIndexOf("\\",x);
            if(y>-1)
            {
                //System.out.println("entra a y:"+x);
                value=value.substring(y+1);
            }
            y=value.lastIndexOf("/",x);
            if(y>-1)
            {
                //System.out.println("entra a y :"+y);
                value=value.substring(y+1);
            }
        }
        //System.out.println("regresa:"+value);
        return value;
    }
    
    public String takeOffString(String value, String takeOff) {
        int pos = -1;
        do {
            pos = value.indexOf(takeOff);
            if (pos != -1) {
                value = value.substring(pos + takeOff.length());
            }
        } while (pos != -1);
        return value;
    }
    
    private int findAnchorInContent(String content,String ancla,int pages)
    {
        ancla=ancla.substring(1);
        Integer page=(Integer)hAnchors.get(ancla);
        if(page!=null)
        { //existe en hash de anclas
            return page.intValue();
        }
        else
        {
            for(int i=0;i<=pages;i++)
            {
                if(findAnchorInContentPage(content,ancla,i,pages)) return i;
            }
        }
        return 0;
    }
    
    /**
     * @param datos
     * @param ruta
     */
    public boolean findAnchorInContentPage(String datos,String ancla,int page,int itpages)
    {
        HtmlTag tag = new HtmlTag();
        boolean flag=false;
        boolean flag1=false;
        boolean flag2=false;
        try
        {
            HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new java.io.ByteArrayInputStream(datos.getBytes()));
            while(tok.nextToken()!=HtmlStreamTokenizer.TT_EOF)
            {
                int ttype = tok.getTokenType();
                if (ttype == HtmlStreamTokenizer.TT_TAG || ttype==HtmlStreamTokenizer.TT_COMMENT)
                {
                    tok.parseTag(tok.getStringValue(), tag);
                    if (tok.getRawString().toLowerCase().startsWith("<!--[if"))
                    {continue;}
                    if(tag.getTagString().toLowerCase().equals("div"))
                    {
                        flag1=true;
                        if(!tag.isEndTag())
                        {
                            Enumeration en=tag.getParamNames();
                            String name="";
                            String value="";
                            while(en.hasMoreElements())
                            {
                                name=(String)en.nextElement();
                                value=tag.getParam(name);
                                if(name.toLowerCase().equals("class"))
                                {
                                    if(value.toLowerCase().equals("section"+page))
                                    {
                                        flag=true;
                                    }
                                }else  if(flag)
                                {
                                    flag2=true;
                                }
                            }
                        }
                        else
                        {
                            if(flag && !flag2)
                            {
                                //entra a este if y se rompe el ciclo solo si la página actual es menos al total de páginas encontradas en el documento,
                                //si es igual, entonces no lo rompe y se termina hasta que se acaba el html, para que funcionen los pie de página si existen 
                                //al final del dicumento
                                if(page<itpages){ 
                                    break;
                                }
                            }else if(flag && flag2)
                            {
                                flag2=false;
                            }
                        }
                    }
                    else if(flag1 && flag)
                    {
                        if(tag.getTagString().toLowerCase().equals("a"))
                        {
                            if (!tag.isEndTag())
                            {
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                String actionval = "";
                                while (en.hasMoreElements())
                                {
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);
                                    if(name.toLowerCase().equals("name") && value.equals(ancla))
                                    { //emcontrado
                                        hAnchors.put(value, new Integer(page));
                                        return true;
                                    }
                                    else if(name.toLowerCase().equals("name") && value.startsWith("_"))
                                    { //es una ancla, guardarla en hash de anclas
                                        if(hAnchors.get(value)==null) {
                                            hAnchors.put(value, new Integer(page));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            log.error(e);
        }
        return false;
    }
    
    private String findImagesInScript(String value, String ext, String ruta)
    {
        StringBuffer aux = new StringBuffer(value.length());
        int off = 0;
        int f = 0;
        int i = 0;
        int j = 0;
        do
        {
            f = value.indexOf(ext, off);
            i = value.lastIndexOf("'", f);
            j = value.lastIndexOf("/", f);
            if (f > 0 && i >= 0)
            {
                i++;
                if (value.startsWith("/", i) || value.startsWith("http://", i))
                {
                    aux.append(value.substring(off, f + ext.length()));
                } else if (j > -1)
                {
                    aux.append(value.substring(off, i) + ruta + value.substring(j + 1, f + ext.length()));
                } else
                {
                    aux.append(value.substring(off, i) + ruta + value.substring(i, f + ext.length()));
                }
                off = f + ext.length();
            }
        } while (f > 0 && i > 0);
        aux.append(value.substring(off));
        return aux.toString();
    }
}