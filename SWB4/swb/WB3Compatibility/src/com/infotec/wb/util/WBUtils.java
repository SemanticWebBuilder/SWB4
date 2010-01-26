/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * Environment.java
 *
 * Created on 3 de junio de 2002, 13:22
 */

package com.infotec.wb.util;

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
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

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
        AFUtils.log(AFUtils.getLocaleString("locale_wb2_util", "log_WBUtils_iniciando"));
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
        return SWBPortal.getContextPath();
    }
    
    public void setWebPath(String webpath)
    {
        if(webpath==null)return;
        try {
            webPath = webpath;
            if (webPath.endsWith("/")) {
                webWorkPath = webPath + AFUtils.getInstance().getEnv("wb/webWorkPath").substring(1);
            } else {
                webWorkPath = webPath + AFUtils.getInstance().getEnv("wb/webWorkPath");
            }
            
            distPath = webPath + AFUtils.getInstance().getEnv("wb/distributor", "wb");
            
        } catch (Exception e) {
            AFUtils.log(e, "Can't read the context path...\n", true);
            workPath = "";
        }        
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
        return webWorkPath;
    }
    
    /**
     * Regresa ruta del servlet distribuidor
     * Por defecto: /wb
     * @return Value of property distPath.
     */
    public java.lang.String getDistPath() {
        return distPath;
    }
    
    
    public String parseHTML(String datos, String ruta) {
        return parseHTML(datos,ruta,0);
    }
    
    
    /**
     * @param datos
     * @param ruta
     * @return  */
    public String parseHTML(String datos, String ruta,int pages) {
        hAnchors=new HashMap();
        //detecci�n de si el contenido es de word
        boolean iswordcontent=false;
        int posword=-1;
        posword=datos.toLowerCase().indexOf("name=generator content=\"microsoft word");
        if(posword>-1) {
            iswordcontent=true;
        }
        //termina detecci�n de si es contenido de word
        
        HtmlTag tag = new HtmlTag();
        int pos = -1;
        int pos1 = -1;
        StringBuffer ret = new StringBuffer();
        try {
            HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
            while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
                int ttype = tok.getTokenType();
                //System.out.println("type:"+tok.getTokenType()+" : "+tok.getRawString());
                //if (ttype==HtmlStreamTokenizer.TT_COMMENT) continue;
                if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) {
                    if(ttype==HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->")) {
                        //System.out.println("comm1"+tok.getTokenType());
                        continue;
                    }
                    tok.parseTag(tok.getStringValue(), tag);
                    
                    if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                        continue;
                    }
                    //System.out.println("tag:"+tag.getTagString().toLowerCase());
                    if ((tag.getTagString().toLowerCase().equals("img")
                    || tag.getTagString().toLowerCase().equals("applet")
                    || tag.getTagString().toLowerCase().equals("script")
                    || tag.getTagString().toLowerCase().equals("tr")
                    || tag.getTagString().toLowerCase().equals("td")
                    || tag.getTagString().toLowerCase().equals("table")
                    || tag.getTagString().toLowerCase().equals("body")
                    || tag.getTagString().toLowerCase().equals("input")
                    || tag.getTagString().toLowerCase().equals("a")
                    || tag.getTagString().toLowerCase().equals("form")
                    || tag.getTagString().toLowerCase().equals("area")
                    || tag.getTagString().toLowerCase().equals("meta")
                    || tag.getTagString().toLowerCase().equals("bca")
                    || tag.getTagString().toLowerCase().equals("link")
                    || tag.getTagString().toLowerCase().equals("param")
                    || tag.getTagString().toLowerCase().equals("embed")
                    || tag.getTagString().toLowerCase().equals("iframe")
                    || tag.getTagString().toLowerCase().equals("frame"))
                    && !tok.getRawString().startsWith("<!--")
                    ) {
                        if (!tag.isEndTag()) {
                            ret.append("<");
                            ret.append(tag.getTagString());
                            ret.append(" ");
                            Enumeration en = tag.getParamNames();
                            String name = "";
                            String value = "";
                            String actionval = "";
                            while (en.hasMoreElements()) {
                                boolean bwritestyle=true;
                                name = (String) en.nextElement();
                                value = tag.getParam(name);
                                String sruta=null;
                                if((name.toLowerCase().equals("src")||name.toLowerCase().equals("href")||name.toLowerCase().equals("background")||name.toLowerCase().equals("codebase")||name.toLowerCase().equals("value"))&&!value.startsWith("http://")&&!value.startsWith("https://")&&!value.startsWith("mailto:")&&!value.startsWith("javascript:")&&!value.startsWith("ftp:")&&!value.startsWith("rtsp:")&&!value.startsWith("telnet:")&&!value.startsWith("#")&&!value.startsWith("/")&&!value.startsWith("../")&&!value.startsWith("{")) {
                                    if(!tag.getTagString().toLowerCase().equals("input") && !value.toLowerCase().equals("true") && !value.toLowerCase().equals("false") && value.indexOf(".")>-1) {
                                        sruta=ruta;
                                    }
                                    //poner solo archivo
                                    if(((pos=value.indexOf("/"))>-1) || (pos=value.indexOf("\\"))>-1)
                                        value=findFileName(value);
                                }else if(name.toLowerCase().equals("href") && value.startsWith("../")){
                                    value="/"+takeOffString(value,"../");
                                }
                                else if(name.toLowerCase().equals("href") && value.startsWith("#_") && pages>1) { //Es un ancla
                                    int page=findAnchorInContent(datos,value,pages);
                                    if(page>0) value="?page="+page+"&"+value;
                                }
                                else if(name.toLowerCase().equals("onmouseover") || name.toLowerCase().equals("onload")|| name.toLowerCase().equals("onmouseout")||name.toLowerCase().equals("onclick")) {
                                    String out=findImagesInScript(value,".gif'",ruta);
                                    out=findImagesInScript(out,".jpg'",ruta);
                                    //ret.append(ruta);
                                    if(!out.equals("")) value=out;
                                }else if(tag.getTagString().toLowerCase().equals("body") && iswordcontent && (name.equals("link") || name.equals("vlink"))) { //elimina los liks
                                    bwritestyle=false;
                                }
                                if(bwritestyle) {
                                    ret.append(name);
                                    ret.append("=\"");
                                    if(sruta!=null)
                                    { ret.append(sruta);}
                                    ret.append(value);
                                    ret.append("\" ");
                                }
                            }
                            if(tag.isEmpty()) {
                                ret.append("/");
                            }
                            ret.append(">");
                            if (tag.getTagString().toLowerCase().equals("form")) ret.append(actionval);
                        } else {
                            //System.out.println("else:"+tok.getRawString());
                            ret.append(tok.getRawString());
                        }
                    } else {
                        //System.out.println("otro:"+tok.getRawString());
                        ret.append(tok.getRawString());
                    }
                } else //if (ttype == HtmlStreamTokenizer.TT_TEXT)
                {
                    //System.out.println("text:"+tok.getRawString());
                    ret.append(tok.getRawString());
                }
            }
        } catch (NumberFormatException f) {
            AFUtils.log(f, AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_decodifica"), true);
        } catch (Exception e) {
            AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_IOHTML"), true);
        }
        //System.out.println("ret:"+ret.toString());
        return ret.toString();
    }
    
    
    private int findAnchorInContent(String content,String ancla,int pages) {
        ancla=ancla.substring(1);
        Integer page=(Integer)hAnchors.get(ancla);
        if(page!=null) { //existe en hash de anclas
            return page.intValue();
        }
        else {
            for(int i=0;i<=pages;i++) {
                if(findAnchorInContentPage(content,ancla,i,pages)) return i;
            }
        }
        return 0;
    }
    
    
    /**
     * @param datos
     * @param ruta
     */
    public boolean findAnchorInContentPage(String datos,String ancla,int page,int itpages) {
        HtmlTag tag = new HtmlTag();
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
                            Enumeration en=tag.getParamNames();
                            String name="";
                            String value="";
                            String actionval="";
                            while(en.hasMoreElements()) {
                                name=(String)en.nextElement();
                                value=tag.getParam(name);
                                if(name.toLowerCase().equals("class")) {
                                    if(value.toLowerCase().equals("section"+page)) {
                                        flag=true;
                                    }
                                }else  if(flag) {
                                    flag2=true;
                                }
                            }
                        }
                        else {
                            if(flag && !flag2) {
                                //entra a este if y se rompe el ciclo solo si la p�gina actual es menos al total de p�ginas encontradas en el documento,
                                //si es igual, entonces no lo rompe y se termina hasta que se acaba el html, para que funcionen los pie de p�gina si existen
                                //al final del dicumento
                                if(page<itpages){
                                    break;
                                }
                                
                            }else if(flag && flag2) {
                                flag2=false;
                            }
                        }
                    }
                    else if(flag1 && flag) {
                        if(tag.getTagString().toLowerCase().equals("a")) {
                            if (!tag.isEndTag()) {
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                String actionval = "";
                                while (en.hasMoreElements()) {
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);
                                    if(name.toLowerCase().equals("name") && value.equals(ancla)) { //emcontrado
                                        hAnchors.put(value, new Integer(page));
                                        return true;
                                    }
                                    else if(name.toLowerCase().equals("name") && value.startsWith("_")) { //es una ancla, guardarla en hash de anclas
                                        if(hAnchors.get(value)==null) hAnchors.put(value, new Integer(page));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e) {
            AFUtils.log(e);
        }
        return false;
    }
    
    
    
    /**
     * @param datos
     * @param ruta
     * @return  */
    public String parseXsl(String datos, String ruta) {
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
                    if(ttype==HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->")) {
                        continue;
                    }
                    tok.parseTag(tok.getStringValue(), tag);
                    
                    if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                        continue;
                    }
                    //if (tag.getTagString().toLowerCase().startsWith("o:")){System.out.println("o:Salto");   continue;}
                    if (tag.getTagString().toLowerCase().equals("img")
                    || tag.getTagString().toLowerCase().equals("applet")
                    || tag.getTagString().toLowerCase().equals("script")
                    || tag.getTagString().toLowerCase().equals("td")
                    || tag.getTagString().toLowerCase().equals("table")
                    || tag.getTagString().toLowerCase().equals("body")
                    || tag.getTagString().toLowerCase().equals("input")
                    || tag.getTagString().toLowerCase().equals("a")
                    || tag.getTagString().toLowerCase().equals("form")
                    || tag.getTagString().toLowerCase().equals("area")
                    || tag.getTagString().toLowerCase().equals("meta")
                    || tag.getTagString().toLowerCase().equals("bca")
                    || tag.getTagString().toLowerCase().equals("link")
                    || tag.getTagString().toLowerCase().equals("param")
                    || tag.getTagString().toLowerCase().equals("embed")
                    || tag.getTagString().toLowerCase().equals("iframe")
                    || tag.getTagString().toLowerCase().equals("frame")
                    ) {
                        
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
                                if((name.toLowerCase().equals("src")||name.toLowerCase().equals("href")||name.toLowerCase().equals("background")||name.toLowerCase().equals("codebase")||name.toLowerCase().equals("value"))&&!value.startsWith("http://")&&!value.startsWith("mailto:")&&!value.startsWith("javascript:")&&!value.startsWith("ftp:")&&!value.startsWith("rtsp:")&&!value.startsWith("telnet:")&&!value.startsWith("#")&&!value.startsWith("/")&&!value.startsWith("{")) {
                                    if(!tag.getTagString().toLowerCase().equals("input") && !value.toLowerCase().equals("true") && !value.toLowerCase().equals("false") && value.indexOf(".")>-1)
                                        ret.append(ruta);
                                    //poner solo archivo
                                    if(((pos=value.indexOf("/"))>-1) || (pos=value.indexOf("\\"))>-1)
                                        value=findFileName(value);
                                }
                                if(name.toLowerCase().equals("onmouseover") || name.toLowerCase().equals("onload")|| name.toLowerCase().equals("onmouseout")||name.toLowerCase().equals("onclick")) {
                                    String out=findImagesInScript(value,".gif'",ruta);
                                    out=findImagesInScript(out,".jpg'",ruta);
                                    //ret.append(ruta);
                                    if(!out.equals("")) value=out;
                                    //System.out.println("out:"+out);
                                }
                                ret.append(value);
                                ret.append("\" ");
                            }
                            //if(tag.getTagString().toLowerCase().equals("img") && tok.getStringValue().toString().endsWith("/")) {
                            if(tag.isEmpty()) {
                                ret.append("/");
                            }
                            
                            ret.append(">");
                            
                            if (tag.getTagString().toLowerCase().equals("form")) ret.append(actionval);
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
            AFUtils.log(f, AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_decodifica"), true);
        } catch (Exception e) {
            AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_IOHTML"), true);
        }
        return ret.toString();
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
                    if (tag.getTagString().toLowerCase().equals("img")
                    || tag.getTagString().toLowerCase().equals("applet")
                    || tag.getTagString().toLowerCase().equals("script")
                    || tag.getTagString().toLowerCase().equals("td")
                    || tag.getTagString().toLowerCase().equals("table")
                    || tag.getTagString().toLowerCase().equals("body")
                    || tag.getTagString().toLowerCase().equals("input")
                    || tag.getTagString().toLowerCase().equals("a")
                    || tag.getTagString().toLowerCase().equals("area")
                    || tag.getTagString().toLowerCase().equals("link")
                    || tag.getTagString().toLowerCase().equals("param")
                    || tag.getTagString().toLowerCase().equals("embed")
                    ) {
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
                                    String stype="";
                                    if(tag.getTagString().toLowerCase().equals("input")) {
                                        stype=tag.getParam("type").toLowerCase();
                                    }
                                    if (!value.startsWith("http://") && !value.startsWith("https://") && (!tag.getTagString().toLowerCase().equals("input") || (tag.getTagString().toLowerCase().equals("input") && stype.equals("image")))) {
                                        if(value.toLowerCase().endsWith(".gif") || value.toLowerCase().endsWith(".jpg") || value.toLowerCase().endsWith(".jpeg") || value.toLowerCase().endsWith(".bmp") ||
                                        value.toLowerCase().endsWith(".doc") || value.toLowerCase().endsWith(".htm") || value.toLowerCase().endsWith(".html") || value.toLowerCase().endsWith(".zip") ||
                                        value.toLowerCase().endsWith(".txt") || value.toLowerCase().endsWith(".pdf") || value.toLowerCase().endsWith(".xls") || value.toLowerCase().endsWith(".ppt") ||
                                        value.toLowerCase().endsWith(".xsl") || value.toLowerCase().endsWith(".xslt") || value.toLowerCase().endsWith(".bin") || value.toLowerCase().endsWith(".tar")) {
                                            out = value;
                                        }
                                    }
                                }else if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && value.startsWith("{")) {
                                    int pos = -1;
                                    pos = value.indexOf("}");
                                    if (pos != -1) {
                                        out = value.substring(pos + 1);
                                    }
                                }
                                else if (name.toLowerCase().equals("href") && value.startsWith("/")) {
                                    out = value;
                                }
                                else if (name.toLowerCase().equals("onmouseover")) {
                                    //if(!value.startsWith("http://") && !value.startsWith("https://"))
                                    int pos = -1,pos1 = -1;
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
                                        if (out.equals((String) vvector.elementAt(i)))
                                            flag = true;
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
            AFUtils.log(f, AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_decodifica"), true);
        } catch (Exception e) {
            AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_IOHTML"), true);
        }
        //System.out.println("entra a FindAttaches regresando:"+ret.toString());
        return ret.toString();
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
    
    public JarFile getAdminFile(String path) {
        JarFile f=(JarFile)admFiles.get(path);
        if(f==null)f=new JarFile(path);
        return f;
    }
    
    public InputStream getAdminFileStream(String path) {
        JarFile f=(JarFile)admFiles.get(path);
        if(f==null)f=new JarFile(path);
        if(!f.exists())return null;
        return f.getInputStream();
    }
    
    /**
     * @param path
     * @throws AFException
     * @return  */
    public InputStream getFileFromWorkPath(String path) throws AFException {
        InputStream ret = null;
        try {
            String confCS = (String) AFUtils.getInstance().getEnv("wb/clientServer");
            
            if(confCS.equalsIgnoreCase("ClientFR")) {
                try {
                    ret = new FileInputStream(getWorkPath() + path);
                }catch(FileNotFoundException fnfe) {
                    //ret=getFileFromAdminWorkPath(path);
                    DownloadDirectory downdir=new DownloadDirectory(AFUtils.getEnv("wb/serverURL"),getWorkPath(),"workpath");
                    downdir.download(path);
                    ret = new FileInputStream(getWorkPath() + path);
                }
            }else if(confCS.equalsIgnoreCase("Client")) {
                ret=getFileFromAdminWorkPath(path);
            } else {
                ret = new FileInputStream(getWorkPath() + path);
            }
        } catch (Exception e) {
            throw new AFException(e.getMessage(), "getFileFromWorkPath");
        }
        return ret;
    }
    
    public InputStream getFileFromAdminWorkPath(String path) throws MalformedURLException, IOException {
        InputStream ret = null;
        //String str = getRemoteWorkPath() + path;
        String servlet=DownloadDirectory.SERVDWN;
        String str = AFUtils.getEnv("wb/serverURL")+servlet+ "?workpath=" + path;
        
        str = str.substring(0, str.lastIndexOf("/") + 1) + com.infotec.appfw.util.URLEncoder.encode(str.substring(str.lastIndexOf("/") + 1));
        ret = new java.net.URL(str).openStream();
        return ret;
    }
    
    public void writeFileToWorkPath(String path, InputStream in, String userid) throws AFException {
        //System.out.println("writeFileToWorkPath:"+path);
        try {
            String confCS = (String) AFUtils.getInstance().getEnv("wb/clientServer");
            
            //System.out.println("clientServer:"+confCS);
            if(confCS.equalsIgnoreCase("ClientFR")||confCS.equalsIgnoreCase("Client")) {
                String str = AFUtils.getEnv("wb/serverURL")+DownloadDirectory.SERVUP;
                URL url=new URL(str);
                //System.out.println("url:"+url);
                URLConnection urlconn=url.openConnection();
                //if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
                urlconn.setRequestProperty("type","workpath");
                urlconn.setRequestProperty("path",path);
                urlconn.setRequestProperty("user",userid);
                urlconn.setDoOutput(true);
                AFUtils.copyStream(in,urlconn.getOutputStream());
                //System.out.println("copyStream");
                String ret=AFUtils.getInstance().readInputStream(urlconn.getInputStream());
                //System.out.println("ret:"+ret);
            }else {
                File file=new File(getWorkPath() + path);
                file.getParentFile().mkdirs();
                FileOutputStream out=new FileOutputStream(file);
                AFUtils.copyStream(in,out);
            }
        } catch (Exception e) {
            throw new AFException(e.getMessage(), "writeFileToWorkPath");
        }
    }
    
    /**
     * @param path
     * @throws AFException
     * @return  */
    public String getFileFromWorkPath2(String path) throws AFException {
        return AFUtils.getInstance().readInputStream(getFileFromWorkPath(path));
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
        StringBuffer ret = new StringBuffer(AFUtils.getBufferSize());
        try {
            InputStreamReader file = getFileFromWorkPath(path, encode);
            char[] bfile = new char[AFUtils.getBufferSize()];
            int x;
            while ((x = file.read(bfile, 0, AFUtils.getBufferSize())) > -1) {
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
        return client;
    }
    
    /** Getter for property remoteWorkPath.
     * @return Value of property remoteWorkPath.
     */
    public String getRemoteWorkPath() {
        return remoteWorkPath;
    }
    
    /** Obtiene objeto de propiedades de la version de webbuilder.
     * @return Value of property pversion.
     */
    public Properties getVersionProperties() {
        return pversion;
    }
    
    /**
     * @param obj
     * @param file
     * @throws java.lang.Exception
     */
    public void XMLObjectEncoder(Object obj, String file) throws Exception {
        String logFile = null;
        
        logFile = workPath + (String) AFUtils.getInstance().getEnv("wb/persistPath") + file;
        
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
        
        logFile = workPath + AFUtils.getInstance().getEnv("wb/persistPath") + file;
        
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
        return AFUtils.getDBConnection(AFUtils.getEnv("wb/db/nameconn"));
        //return dbPool.getNoPoolConnection((String)getEnv("wb/db/nameconn"));
    }
    
    /** Getter for default wb2 Connection form DBPool.
     * @return Connection from DBPool.
     * @param name  */
    public static Connection getNoPoolDBConnection() {
        return AFUtils.getNoPoolDBConnection(AFUtils.getEnv("wb/db/nameconn"));
    }
    
    /** Nombre de base de datos que esta utilizando WB.
     *  @return String nombre de la base de datos.
     */
    public static String getDBName() {
        String ret = null;
        try {
            Connection con = getDBConnection();
            if(con!=null)
            {
                java.sql.DatabaseMetaData md = con.getMetaData();
                ret = md.getDatabaseProductName();
                con.close();
            }
        } catch (Exception e) {
            AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBUtils_nodataname2"), true);
        }
        return ret;
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
        {AFUtils.log(f,AFUtils.getLocaleString("locale_wb2_util","error_WBUtils_decodifica"),true);}
        catch(Exception e)
        {AFUtils.log(e,AFUtils.getLocaleString("locale_wb2_util","error_WBUtils_IOHTML"),true);}
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
            String period = AFUtils.getEnv("wb/accessLogPeriod","monthly");
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
        if (AFUtils.getEnv("wb/accessLog","/logs/wb_log").startsWith("file:")) {
            try {
                workp = new File(AFUtils.getEnv("wb/accessLog","/logs/wb_log").substring(5)).getCanonicalPath().replace('\\','/');
            } catch (Exception e) {
                workp = AFUtils.getEnv("wb/accessLog","/logs/wb_log").substring(5);
            }
        } else {
            workp = getWorkPath() + AFUtils.getEnv("wb/accessLog");
        }
        return workp;
    }
    
    /**
     * Getter for property bufferSize.
     * @return Value of property bufferSize.
     */
    public int getBufferSize() {
        return AFUtils.getBufferSize();
    }
    
    /**
     * Setter for property bufferSize.
     * @param bufferSize New value of property bufferSize.
     */
    public void setBufferSize(int bufferSize) {
        AFUtils.setBufferSize(bufferSize);
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