/**
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
*
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
* del SemanticWebBuilder 4.0.
*
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
* de la misma.
*
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
* dirección electrónica:
*  http://www.semanticwebbuilder.org
**/


/*
 * Environment.java
 *
 * Created on 3 de junio de 2002, 13:22
 */

package com.infotec.wb.util;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.*;
import java.util.*;

import com.infotec.appfw.lib.AFAppObject;
import java.text.SimpleDateFormat;

import com.infotec.appfw.exception.*;
import com.infotec.appfw.util.AFUtils;

import java.sql.Connection;

import java.util.zip.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarFile;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;

/** objeto: Utilerias de uso comun para desarrolladores de WB.
 * @author Javier Solis Gonzalez
 * @version 1.1
 */

public class WBUtils implements AFAppObject
{
    static private WBUtils instance;       // The single instance
//    private Properties pversion;
//    private String workPath = "";
//    private String webPath = null;
//    private String webWorkPath = "/work";
//    private String distPath ="/wb";
//    private String remoteWorkPath = null;
//    private boolean client = false;
//
//    private HashMap admFiles=new HashMap();
//    private HashMap hAnchors=null;
    
    /** Creates new utils */
    private WBUtils() {
        AFUtils.log(SWBUtils.TEXT.getLocaleString("locale_wb2_util", "log_WBUtils_iniciando"));
        init();
    }
    
    /** Get Instance.
     * @return  */
    static public WBUtils getInstance() {
        if (instance == null) {
            makeInstance();
        }
        return instance;
    }
    
    static private synchronized void makeInstance()
    {
        if (instance == null)
        {
            instance = new WBUtils();
        }
    }       
    
    public void init() {
//        String confCS = (String) AFUtils.getInstance().getEnv("wb/clientServer");
//        if (confCS != null && (confCS.equalsIgnoreCase("Client") || confCS.equalsIgnoreCase("SAC") || confCS.equalsIgnoreCase("ClientFR"))) client = true;
//
//        try {
//            setWebPath(AFUtils.getInstance().getEnv("wb/webPath",null));
//            if (confCS.equalsIgnoreCase("Client")) remoteWorkPath = (String) AFUtils.getInstance().getEnv("wb/remoteWorkPath");
//            //if(remoteWorkPath!=null)remoteWorkPath=java.net.URLEncoder.encode(remoteWorkPath);
//
//            workPath = (String) AFUtils.getInstance().getEnv("wb/workPath");
//            if (workPath.startsWith("file:")) {
//                workPath = (new File(workPath.substring(5))).toString();
//            } else if (workPath.startsWith("http://")) {
//                workPath = (com.infotec.appfw.util.URLEncoder.encode(workPath));
//            } else {
//                workPath = AFUtils.getInstance().getAppPath()+workPath;
//            }
//
//        } catch (Exception e) {
//            AFUtils.log(e, "Can't read the context path...\n", true);
//            workPath = "";
//        }
//
//        try {
//            //log("Revizando Versi�n del Core...",true);
//            pversion = new Properties();
//            InputStream in = getClass().getResourceAsStream("/wbversion.properties");
//            try {
//                pversion.load(in);
//            } catch (Exception e) {
//                //log("Guardando Versi�n del Core...",true);
//                try {
//                    FileOutputStream fos = new FileOutputStream(AFUtils.getInstance().getAppPath() + "/WEB-INF/classes/wbversion.properties");
//                    pversion.setProperty("Version", WBLoader.getInstance().getCoreVersion());
//                    pversion.save(fos, AFUtils.getLocaleString("locale_wb2_util", "log_WBUtils_versioncore"));
//                    fos.close();
//                } catch (Exception e2) {
//                    AFUtils.log(e2, AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_noversionfile"), true);
//                }
//            }
//        } catch (Exception e) {
//            AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_IOversion"), true);
//        }
//
//        try {
//            AFUtils.debug("Loading admin Files from: /WEB-INF/lib/WBAdmin.jar");
//            String zipPath=AFUtils.getInstance().getAppPath()+"/WEB-INF/lib/WBAdmin.jar";
//            ZipFile zf=new ZipFile(zipPath);
//            Enumeration e=zf.entries();
//            while (e.hasMoreElements()) {
//                ZipEntry ze=(ZipEntry)e.nextElement();
//                AFUtils.debug("/"+ze.getName()+", "+ze.getSize()+", "+ze.getTime());
//                admFiles.put("/"+ze.getName(),new JarFile(ze,zipPath));
//            }
//            zf.close();
//            AFUtils.log("Admin Files in Memory:\t"+admFiles.size());
//        }catch(Exception e) {
//            AFUtils.log(e,"Error loading files for Webbuilder Administration:"
//                    +AFUtils.getInstance().getAppPath()+"/WEB-INF/lib/WBAdmin.jar",true);
//        }
    }
    
    
    public void destroy() {
        instance=null;        
    }
    
    public void refresh() {
    }
    
    /** Getter for property workPath.
     * @return Value of property workPath.
     */
    public String getWorkPath() {
        return SWBPortal.getWorkPath();
    }
    
    /** Getter for property workPath.
     * @return Value of property appPath.
     */
    public String getAppPath() {
        return SWBUtils.getApplicationPath();
    }
    
    /** Getter for property webPath.
     * @return Value of property webPath.
     */
    public String getWebPath() {
        return SWBPortal.getContextPath()+"/";
    }
    
    public void setWebPath(String webpath)
    {
//        if(webpath==null)return;
//        try {
//            webPath = webpath;
//            if (webPath.endsWith("/")) {
//                webWorkPath = webPath + AFUtils.getInstance().getEnv("wb/webWorkPath").substring(1);
//            } else {
//                webWorkPath = webPath + AFUtils.getInstance().getEnv("wb/webWorkPath");
//            }
//
//            distPath = webPath + AFUtils.getInstance().getEnv("wb/distributor", "wb");
//
//        } catch (Exception e) {
//            AFUtils.log(e, "Can't read the context path...\n", true);
//            workPath = "";
//        }
    }
    
    /**
     * @param p
     * @return  */
    public int calcPriority(int p) {
        if (p == 0)
            return 0;
        else if (p == 1)
            return 1;
        else if (p == 5)
            return 50;
        else if (p > 5)
            return 60;
        else {
            return (int) (Math.random() * 10 * p) + 2;
        }
    }
    
    
    /** Getter for property webWorkPath.
     * @return Value of property webWorkPath.
     */
    public String getWebWorkPath() {
        return SWBPortal.getWebWorkPath();
    }
    
    /**
     * Regresa ruta del servlet distribuidor
     * Por defecto: /wb
     * @return Value of property distPath.
     */
    public java.lang.String getDistPath() {
        return SWBPortal.getDistributorPath();
    }
    
    
    public String parseHTML(String datos, String ruta) {
        return SWBPortal.UTIL.parseHTML(datos, ruta);
    }
    
    
    /**
     * @param datos
     * @param ruta
     * @return  */
    public String parseHTML(String datos, String ruta,int pages) {
        return SWBPortal.UTIL.parseHTML(datos, ruta,pages);
    }
    
    
    private int findAnchorInContent(String content,String ancla,int pages) {
        return SWBPortal.UTIL.findAnchorInContentPage(content, ancla, pages, 0)?1:0;
    }
    
    
    /**
     * @param datos
     * @param ruta
     */
    public boolean findAnchorInContentPage(String datos,String ancla,int page,int itpages) {
        return SWBPortal.UTIL.findAnchorInContentPage(datos, ancla, page, itpages);
    }
    
    
    
    /**
     * @param datos
     * @param ruta
     * @return  */
    public String parseXsl(String datos, String ruta) {
        return SWBPortal.UTIL.parseXsl(datos, ruta);
    }
    
    
    private String findFileName(String value) {
        //System.out.println("value:"+value);
        String out="";
        if(value.startsWith("../")) {
            out=takeOffString(value,"../");
            if(!out.equals(""))value=out;
        }
        int x=value.lastIndexOf(".");
        if(x>-1) {
            int y=value.lastIndexOf("\\",x);
            if(y>-1) {
                //System.out.println("entra a y:"+x);
                value=value.substring(y+1);
            }
            y=value.lastIndexOf("/",x);
            if(y>-1) {
                //System.out.println("entra a y :"+y);
                value=value.substring(y+1);
            }
        }
        //System.out.println("regresa:"+value);
        return value;
    }
    
    
    public String takeOffString(String value,String takeOff) {
        int pos=-1;
        do {
            pos=value.indexOf(takeOff);
            if(pos!=-1) {
                value=value.substring(pos+takeOff.length());
            }
        }while(pos!=-1);
        return value;
    }
    
    
    /**
     * @param datos
     * @param ruta
     */
    public String FindAttaches(String datos) {
        return SWBPortal.UTIL.FindAttaches(datos);
    }
    
    
/*
    private String findImageInScript(String value, String ext, String ruta) {
        int f=value.indexOf(ext);
        int i=value.lastIndexOf("'",f);
        int j=value.lastIndexOf("/",f);
 
        if(f>0 && i>=0) {
            i++;
            if(value.startsWith("/",i)||value.startsWith("http://",i)) {
                return value;
            }
            else {
                return value.substring(0,i)+ruta+value.substring(j+1,value.length());
            }
        }
        return value;
    }
 
 */
    
    private String findImagesInScript(String value, String ext, String ruta) {
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
    
    
    private String findImageInScript1(String value, String ext, String ruta) {
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
    
    public org.semanticwb.util.JarFile getAdminFile(String path) {
        return SWBPortal.getAdminFile(path);
    }
    
    public InputStream getAdminFileStream(String path) {
        return SWBPortal.getAdminFileStream(path);
    }
    
    /**
     * @param path
     * @throws AFException
     * @return  */
    public InputStream getFileFromWorkPath(String path) throws AFException {
        InputStream ret = null;
        try {
            ret= SWBPortal.getFileFromWorkPath(path);
        } catch (Exception e) {
        }
        return ret;

    }
    
    public InputStream getFileFromAdminWorkPath(String path) throws MalformedURLException, IOException {
        //TODO
        InputStream ret = null;
        try {
            ret = SWBPortal.getAdminFileStream(path);
        } catch (Exception e) {
        }
        return ret;
    }
    
    public void writeFileToWorkPath(String path, InputStream in, String userid) throws AFException {
        SemanticObject obj=SemanticObject.getSemanticObject(userid);
        User user=null;
        if(obj!=null)
        {
            user=(User)obj.createGenericInstance();
        }
        try {
            SWBPortal.writeFileToWorkPath(path, in, user);
        } catch (Exception e) {
            throw new AFException(e.getMessage(), "writeFileToWorkPath");
        }
    }
    
    /**
     * @param path
     * @throws AFException
     * @return  */
    public String getFileFromWorkPath2(String path) throws AFException {
        String ret=null;
        try {
            ret=SWBUtils.IO.readInputStream(getFileFromWorkPath(path));
        } catch (Exception e) {
             throw new AFException(e.getMessage(), "getFileFromWorkPath2");
        }
        return ret;
    }
    
    /**
     * @param path
     * @param encode
     * @throws AFException
     * @return  */
    public InputStreamReader getFileFromWorkPath(String path, String encode) throws AFException {
        InputStreamReader ret = null;
        try {
            ret = new InputStreamReader(getFileFromWorkPath(path), encode);
        } catch (Exception e) {
            throw new AFException(e.getMessage(), "getFileFromWorkPath");
        }
        return ret;
    }
    
    /**
     * @param path
     * @param encode
     * @throws AFException
     * @return  */
    public String getFileFromWorkPath2(String path, String encode) throws AFException {
        StringBuffer ret = new StringBuffer(SWBUtils.IO.getBufferSize());
        try {
            InputStreamReader file = getFileFromWorkPath(path, encode);
            char[] bfile = new char[SWBUtils.IO.getBufferSize()];
            int x;
            while ((x = file.read(bfile, 0, SWBUtils.IO.getBufferSize())) > -1) {
                ret.append(bfile, 0, x);
            }
            file.close();
        } catch (Exception e) {
            throw new AFException(e.getMessage(), "getFileFromWorkPath2");
        }
        return ret.toString();
    }
    
    /** La instancia de WB esta configurada como cliente?.
     * @return Value of property client.
     */
    public boolean isClient() {
        return SWBPortal.isClient();
    }
    
    /** Getter for property remoteWorkPath.
     * @return Value of property remoteWorkPath.
     */
    public String getRemoteWorkPath() {
        //TODO
        return ""; //remoteWorkPath;
    }
    
    /** Obtiene objeto de propiedades de la version de webbuilder.
     * @return Value of property pversion.
     */
    public Properties getVersionProperties() {
        
        return SWBPortal.getWebProperties();
    }
    
    /**
     * @param obj
     * @param file
     * @throws java.lang.Exception
     */
    public void XMLObjectEncoder(Object obj, String file) throws Exception {
        String logFile = null;
        
        logFile = SWBPortal.getWorkPath() + (String) SWBPortal.getEnv("wb/persistPath") + file;
        
        // Serialize object into XML
/*
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(logFile)));
        encoder.writeObject(obj);
        encoder.close();
 */
        FileOutputStream f = new FileOutputStream(logFile);
        ObjectOutput s = new ObjectOutputStream(f);
        s.writeObject(obj);
        s.flush();
        s.close();
    }
    
    /**
     * @param file
     * @throws java.lang.Exception
     * @return  */
    public Object XMLObjectDecoder(String file) throws Exception {
        String logFile = null;
        
        logFile = SWBPortal.getWorkPath() + (String) SWBPortal.getEnv("wb/persistPath") + file;
        
        Object obj = null;
        
        //throw new AFException("No se encontro archivo...","XMLObjectDecoder");
/*
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(logFile)));
        obj = decoder.readObject();
        decoder.close();
 */
        FileInputStream in = new FileInputStream(logFile);
        ObjectInputStream s = new ObjectInputStream(in);
        obj = s.readObject();
        
        return obj;
    }
    
    /** Getter for default wb2 Connection form DBPool.
     * @return Connection from DBPool.
     * @param name  */
    public static Connection getDBConnection() {

        return SWBUtils.DB.getConnection(SWBPortal.getEnv("wb/db/nameconn"));
    }
    
    /** Getter for default wb2 Connection form DBPool.
     * @return Connection from DBPool.
     * @param name  */
    public static Connection getNoPoolDBConnection() {
        return SWBUtils.DB.getNoPoolConnection(SWBPortal.getEnv("wb/db/nameconn"));
    }
    
    /** Nombre de base de datos que esta utilizando WB.
     *  @return String nombre de la base de datos.
     */
    public static String getDBName() {
        return SWBUtils.DB.getDatabaseName();
    }
    
    public static String dateFormat(java.sql.Timestamp date) {
        if(date==null)return "";
        return date.toString();
    }
    
    
    /**
     * @param datos
     * @param ruta
     */
    public String getContentByPage(String datos,int page) {
        HtmlTag tag = new HtmlTag();
        StringBuffer ret=new StringBuffer();
        StringBuffer rettmp=new StringBuffer();
        boolean flag=false;
        boolean flag1=false;
        boolean flag2=false;
        try {
            HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new java.io.ByteArrayInputStream(datos.getBytes()));
            while(tok.nextToken()!=HtmlStreamTokenizer.TT_EOF) {
                int ttype = tok.getTokenType();
                if (ttype == HtmlStreamTokenizer.TT_TAG || ttype==HtmlStreamTokenizer.TT_COMMENT) {
                    tok.parseTag(tok.getStringValue(), tag);
                    if (tok.getRawString().toLowerCase().startsWith("<!--[if"))
                    {continue;}
                    if(tag.getTagString().toLowerCase().equals("div")) {
                        flag1=true;
                        if(!tag.isEndTag()) {
                            rettmp=new StringBuffer();
                            rettmp.append("<");
                            rettmp.append(tag.getTagString());
                            rettmp.append(" ");
                            Enumeration en=tag.getParamNames();
                            String name="";
                            String value="";
                            String actionval="";
                            while(en.hasMoreElements()) {
                                name=(String)en.nextElement();
                                value=tag.getParam(name);
                                rettmp.append(name);
                                rettmp.append("=\"");
                                if(name.toLowerCase().equals("class")) {
                                    if(value.toLowerCase().equals("section"+page)) {
                                        flag=true;
                                        ret.append(rettmp.toString());
                                        ret.append(value);
                                        ret.append("\" ");
                                        ret.append(">");
                                    }
                                }else  if(flag) {
                                    flag2=true;
                                    ret.append(rettmp.toString());
                                    ret.append(value);
                                    ret.append("\" ");
                                    ret.append(">");
                                }
                            }
                        }
                        else {
                            if(flag && !flag2) {
                                ret.append(tok.getRawString());
                                ret.append("</body>");
                                ret.append("</html>");
                                break;
                            }else if(flag && flag2) {
                                ret.append(tok.getRawString());
                                flag2=false;
                            }
                        }
                    }
                    else {
                        if(!flag1 || flag) {
                            ret.append(tok.getRawString());
                        }
                    }
                }
                else if (ttype == HtmlStreamTokenizer.TT_TEXT) {
                    if(!flag1 || flag) {
                        ret.append(tok.getRawString());
                    }
                }
            }
        }
        catch(NumberFormatException f)
        {AFUtils.log(f,SWBUtils.TEXT.getLocaleString("locale_wb2_util","error_WBUtils_decodifica"),true);}
        catch(Exception e)
        {AFUtils.log(e,SWBUtils.TEXT.getLocaleString("locale_wb2_util","error_WBUtils_IOHTML"),true);}
        return ret.toString();
    }
    
    
    // Elimina directorios completos
    public boolean removeDirectory(String path) {
        boolean beliminate=false;
        try {
            File dir=new File(path);
            if (dir!=null && dir.exists()) {
                File[] listado = dir.listFiles();
                for (int i=0; i < listado.length;i++) {
                    if(listado[i].isFile()) {
                        listado[i].delete();
                    }
                    if(listado[i].isDirectory()) {
                        path=listado[i].getPath();
                        boolean flag=removeDirectory(path);
                        if(flag) listado[i].delete();
                    }
                }
            }
            if(dir.delete()) beliminate=true;
        }catch(Exception e)
        {}
        return beliminate;
    }
    
    private SimpleDateFormat df = null;
    
    public String getAccessLogDatePatern() {
        if(df==null) {
            String period = SWBPortal.getEnv("wb/accessLogPeriod","monthly");
            if (period.equalsIgnoreCase("yearly")) {
                df = new SimpleDateFormat("yyyy");
            } else if (period != null && period.equalsIgnoreCase("daily")) {
                df = new SimpleDateFormat("yyyy-MM-dd");
            } else {
                df = new SimpleDateFormat("yyyy-MM");
            }
        }
        return df.format(new Date());
    }
    
    public String getAccessLogPath() {
        String workp=null;;
        if (SWBPortal.getEnv("wb/accessLog","/logs/wb_log").startsWith("file:")) {
            try {
                workp = new File(SWBPortal.getEnv("wb/accessLog","/logs/wb_log").substring(5)).getCanonicalPath().replace('\\','/');
            } catch (Exception e) {
                workp = SWBPortal.getEnv("wb/accessLog","/logs/wb_log").substring(5);
            }
        } else {
            workp = getWorkPath() + SWBPortal.getEnv("wb/accessLog");
        }
        return workp;
    }
    
    /**
     * Getter for property bufferSize.
     * @return Value of property bufferSize.
     */
    public int getBufferSize() {
        return SWBUtils.IO.getBufferSize();
    }
    
    /**
     * Setter for property bufferSize.
     * @param bufferSize New value of property bufferSize.
     */
    public void setBufferSize(int bufferSize) {
        //TODO
        //AFUtils.setBufferSize(bufferSize);
    }
    
    
    public void repaleceStringIntoDirectory(String directoryPath,String string1,String string2,ArrayList extensions) {
        java.io.File dir=new java.io.File(directoryPath);
        if(dir!=null && dir.exists() && dir.isDirectory()) { //para el directorio site
            java.io.File[] listado=dir.listFiles();
            for (int i=0; i < listado.length;i++) {
                if(listado[i].isFile()) // es archivo y se parsea directamente
                {
                    String filename=listado[i].getName();
                    if(filename!=null) {
                        int pos=-1;
                        pos=filename.lastIndexOf(".");
                        if(pos>-1) {
                            String fileExtension=filename.substring(pos+1);
                            if(extensions.contains(fileExtension)) {
                                String content;
                                content=getFileFromPath(listado[i].getPath());
                                if(content!=null) {
                                    replaceString(content,listado[i].getPath(),string1,string2);
                                }
                            }
                        }
                    }
                }
                else if(listado[i].isDirectory()) {
                    goInsideDirectory(listado[i],string1,string2,extensions);
                }
            }
            
        }
    }
    
    
    private void goInsideDirectory(File dir,String string1,String string2,ArrayList extensions) {
        java.io.File[] listado=dir.listFiles();
        for (int i=0; i < listado.length;i++) {
            if(listado[i].isFile()) // es archivo y se parsea directamente
            {
                String filename=listado[i].getName();
                if(filename!=null) {
                    int pos=-1;
                    pos=filename.lastIndexOf(".");
                    if(pos>-1) {
                        String fileExtension=filename.substring(pos);
                        if(extensions.contains(fileExtension)) {
                            String content;
                            content=getFileFromPath(listado[i].getPath());
                            if(content!=null) {
                                replaceString(content,listado[i].getPath(),string1,string2);
                            }
                        }
                    }
                }
            }
            else if(listado[i].isDirectory()) {
                goInsideDirectory(listado[i],string1,string2,extensions);
            }
        }
    }
    
    
    private String getFileFromPath(String path) {
        StringBuffer ret=new StringBuffer(8192);
        try {
            InputStream file=null;
            file=new FileInputStream(path);
            byte[] bfile= new byte[8192];
            int x;
            while((x=file.read(bfile,0,8192))>-1) {
                ret.append(new String(bfile,0,x));
            }
            file.close();
        }catch(Exception e)
        {}
        return ret.toString();
    }
    
    private void replaceString(String content,String path,String string1,String string2) {
        try {
            content=content.replaceAll(string1,string2);
            byte abyte[]=content.getBytes();
            FileOutputStream fileoutputstream = new FileOutputStream(path);
            fileoutputstream.write(abyte, 0, abyte.length);
            fileoutputstream.close();
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}