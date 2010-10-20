/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * WBResourceUtils.java
 *
 * Created on 3 de junio de 2002, 13:22
 */

package com.infotec.wb.util;

import java.io.InputStream;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;
import com.infotec.appfw.lib.AFAppObject;
import com.infotec.appfw.exception.AFException;
import com.infotec.appfw.util.AFUtils;
import com.infotec.appfw.util.FileUpload;
import com.infotec.appfw.util.WBFileUpload;
import com.infotec.wb.core.Resource;


/** objeto: Utilerias de uso comun para los Recursos desarrolladores de WB.
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class WBResourceUtils implements AFAppObject
{
    static private WBResourceUtils instance;       // The single instance
    private String appPath = "/";
    private String workPath = "";
    private String webPath = "/";
    private String webWorkPath = "/work";
    private String remoteWorkPath = null;

    /** Creates new utils */
    private WBResourceUtils()
    {
        init();
    }

    /** Get Instance.
     * @return  */
    static public WBResourceUtils getInstance()
    {
        if (instance == null)
        {
            makeInstance();
        }
        return instance;
    }
    
    static private synchronized void makeInstance()
    {
        if (instance == null)
        {
            instance = new WBResourceUtils();
            instance.init();
        }
    }       

    public void init()
    {
        try
        {
            appPath = (String) WBUtils.getInstance().getAppPath();
            webPath = (String) WBUtils.getInstance().getWebPath();
            workPath = (String) WBUtils.getInstance().getWorkPath();
            webWorkPath = (String) WBUtils.getInstance().getWebWorkPath();
            remoteWorkPath = (String) WBUtils.getInstance().getRemoteWorkPath();
        } 
        catch (Exception e) { AFUtils.getInstance().log(e,"Error while initializing WBResourceUtils.",true); }
    }

    public void destroy() {
        instance=null;        
    }

    public void refresh() {
    }

    /**
     * Elimina archivos del filesystem relacionados al recurso.
     *
     * @param     base      La informaci�n del recurso en memoria.
     * @return    Regresa un nuevo String que contiene el nombre del archivo que se guard�.
     */
    public String removeResource(Resource base)
    {
        StringBuffer sbfRet = new StringBuffer();
        String strError = AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_htmlAlerta");
        try
        {
            strError += AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_removeResource_nodelete") + base.getTitle() + AFUtils.getLocaleString("locale_wb2_util", "error_WBResourceremoveResource_tipo") + " " + base.getResourceType().getName() + " " + AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_removeResource_ident") + base.getId() + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_html2");

            File file = new File(workPath + base.getResourceWorkPath());
            if (file.exists() && file.isDirectory())
            {
                try
                {
                    AFUtils.removeDirectory(workPath + base.getResourceWorkPath() +"/");
                    sbfRet.append(AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_ok1") + base.getTitle() + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_ok2") + base.getResourceType().getName() + " " + com.infotec.appfw.util.AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_ok3") + base.getId() + com.infotec.appfw.util.AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_ok4"));
                    sbfRet.append(AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_ubica") + workPath + base.getResourceWorkPath() +" "+ AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_coment"));
                } 
                catch (Exception e) 
                { 
                    sbfRet.append("\n<br>" + strError + "<br>\n<!-- " + e + " -->"); 
                    AFUtils.log(e, strError, true);
                }
            } 
            else sbfRet.append("\n<br>" + strError + "<br>\n<!-- Error no "+ workPath + base.getResourceWorkPath() +" -->");
        } 
        catch (Exception e) { AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_run_recerror") + base.getId() + AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_removeResource_tiperec") + base.getType() + ".", true); }
        return sbfRet.toString();
    }
    
    
     /**
     * Elimina un archivo dado de .
     * @return a boolean value if the file was removed or not.
     */
    public boolean removeFileFromFS(String path)
    {
        try
        {
            if(path!=null)
            {
                File file = new File(path.trim());
                if (file!=null && file.exists() && file.isFile()) {
                    return file.delete();
                }
            }
        } 
        catch (Exception e) { AFUtils.getInstance().log(e, "Error while removing file..:"+path, true); }
        return false;
    }

    /**
     * Obtiene un archivo ubicado en el filesystem.
     *
     * @param   path    Ruta donde se localiza el archivo.
     * @throws  com.infotec.appfw.exception.AFException
     * @return  Regresa un InputStream.
     */
    public InputStream getFileInputStream(String path) throws AFException
    {
        InputStream file = null;
        try
        {
            if (remoteWorkPath != null) file = new java.net.URL(remoteWorkPath + path).openStream();
            else file = new FileInputStream(path);
        } 
        catch (Exception e) { throw new AFException(e.getMessage(), AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_getFileInputStream_exception")); }
        return file;
    }

    /**
     * Obtiene el contenido de un archivo ubicado en el filesystem.
     *
     * @param   path    Ruta donde se localiza el archivo.
     * @throws  com.infotec.appfw.exception.AFException
     * @return  Regresa un nuevo String que contiene el contenido del archivo.
     */
    public String getFileToString(String path) throws AFException
    {
        StringBuffer sbfRet = new StringBuffer(8192);
        try
        {
            InputStream file = getFileInputStream(path);
            byte[] bfile = new byte[8192];
            int x;
            while ((x = file.read(bfile, 0, 8192)) > -1) {
                sbfRet.append(new String(bfile, 0, x));
            }
            file.close();
        } 
        catch (Exception e) { throw new AFException(e.getMessage(), AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_getFileToString_except")); }
        return sbfRet.toString();
    }

    /**
     * Obtiene el contenido parseado de un archivo ubicado en el filesystem.
     *
     * @param   path    Ruta donde se localiza el archivo.
     * @throws  com.infotec.appfw.exception.AFException
     * @return  Regresa un nuevo String que contiene el contenido parseado del archivo.
     */
    public String getFileParsed(String path) throws AFException
    {
        StringBuffer sbfRet = new StringBuffer("");
        String strSource = "";
        try
        {
            strSource = getFileToString(path);
            strSource = strSource.replaceAll("<", "&lt;");
            strSource = strSource.replaceAll(">", "&gt;");
            sbfRet.append(strSource);
        } 
        catch (Exception e) { throw new AFException(e.getMessage(), AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_getFileParsed_except")); }
        return sbfRet.toString();
    }

    public String getJSPHeader()
    {
        StringBuffer ret = new StringBuffer();
        ret.append("<%@page contentType=\"text/html\"%>\n");
        ret.append("<%@page import=\"java.io.*\"%>\n");
        ret.append("<%@page import=\"java.util.*\"%>\n");
        ret.append("<html>\n");
        ret.append("<body>\n");
        return  ret.toString();
    }

    public String getJSPFooter()
    {
        StringBuffer ret = new StringBuffer();
        ret.append("</body>\n");
        ret.append("</html>\n");
        return  ret.toString();
    }
   
    public boolean createJSP(String content, String header, String footer, String workpath)
    {
        StringBuffer ret = new StringBuffer();
        if(header!=null) ret.append(header);
        ret.append(content);
        if(footer!=null) ret.append(footer);
        return  saveSourceToFile(ret.toString(), workpath);
    }
    
    /**
     * Guarda un texto en un archivo.
     *
     * @param     content    Texto que se va a grabar.
     * @param     workpath  Archivo en el que se va a grabar el texto.
     * @return    Regresa true si se realiz� la operaci�n con �xito, de lo contrario regresa false.
     */
    public boolean saveSourceToFile(String content, String workpath)
    {
        boolean bOk = false;
        try
        {
            workpath=workpath.replace('\\', '/');
            File file = null;
            if(workpath.lastIndexOf('/')>-1) file = new File(workpath.substring(0,workpath.lastIndexOf('/')+1));
            else return bOk;
            
            if(file!=null)
            {
                if(!file.exists()) file.mkdirs();
                if (file.exists() && file.isDirectory())
                {
                    DataOutputStream dosOut = new DataOutputStream(new FileOutputStream(workpath));
                    dosOut.writeBytes(content);
                    dosOut.close();
                    bOk = true;
                }
            }
        } 
        catch (IOException e)
        {
            bOk = false;
            AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_saveSourceToFile_except"), true);
        }
        return bOk;
    }

    /**
     * Sube un archivo al filesystem.
     *
     * @param     base      La informaci�n del recurso en memoria.
     * @param     pfUpload  Objeto que permite obtener la informaci�n del formulario con tipo de codificaci�n multipart/form-data.
     * @param     pInForm   El nombre del campo del formulario donde se defini� el archivo.
     * @return    Regresa un nuevo String que contiene el nombre del archivo que se guard�.
     */
    public boolean uploadFile(Resource base, FileUpload fUp, String pInForm)
    {
        String strWorkPath = workPath;
        String strFile = null;
        boolean bOk = false;
        try
        {
            strFile = fUp.getFileName(pInForm);
            if (strFile != null && !strFile.trim().equals(""))
            {
                strWorkPath+=base.getResourceWorkPath()+"/";
                File file=new File(strWorkPath);
                if(file!=null) 
                {
                    if(!file.exists())file.mkdirs();
                    if (file.exists() && file.isDirectory()) bOk = fUp.saveFile(pInForm, strWorkPath, new String(), new String());
                }
            }
        } 
        catch (Exception e) { AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_uploadFile_exc01") + " " + base.getId() + AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_uploadFile_exc02") + " " + base.getType() + AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_uploadFile_exc03") + " " + strFile + ".", true); }
        return bOk;
    }

    /**
     * Sube un archivo parseado al filesystem.
     *
     * @param     base      La informaci�n del recurso en memoria.
     * @param     pfUpload  Objeto que permite obtener la informaci�n del formulario con tipo de codificaci�n multipart/form-data.
     * @param     pInForm   El nombre del campo del formulario donde se defini� el archivo.
     * @return    Regresa un nuevo String que contiene el applet para subir las im�genes relativas al archivo parseado.
     */
    public String uploadFileParsed(Resource base, FileUpload fUp, String pInForm)
    {
        return uploadFileParsed(base, fUp, pInForm, fUp.getSessid(),null);
    }

    
    public String uploadFileParsed(Resource base, FileUpload fUp, String pInForm, String idsession){
        return uploadFileParsed(base, fUp, pInForm, idsession,null);
    }
    
    
    /**
     * Sube un archivo parseado al filesystem.
     *
     * @param     base      La informaci�n del recurso en memoria.
     * @param     pfUpload  Objeto que permite obtener la informaci�n del formulario con tipo de codificaci�n multipart/form-data.
     * @param     pInForm   El nombre del campo del formulario donde se defini� el archivo.
     * @return    Regresa un nuevo String que contiene el applet para subir las im�genes relativas al archivo parseado.
     */
    public String uploadFileParsed(Resource base, FileUpload fUp, String pInForm, String idsession,String path2save)
    {
        StringBuffer sbfRet = new StringBuffer();
        String strWorkPath = workPath;
        String strFile = null, strClientPath = "";
        int intPos;
        try
        {
            strFile = fUp.getFileName(pInForm);
            if (strFile != null && !strFile.trim().equals(""))
            {
                strFile=strFile.replace('\\','/');
                intPos=strFile.lastIndexOf("/");
                if(intPos != -1) 
                {
                    strClientPath=strFile.substring(0, intPos);
                    strFile=strFile.substring(intPos+1).trim();
                }
                if(!strClientPath.endsWith("/")) strClientPath=strClientPath+"/";
                strWorkPath+=base.getResourceWorkPath()+"/";
                File file=new File(strWorkPath);
                if(file!=null)
                {
                    if(!file.exists()) file.mkdirs();
                    if (file.exists() && file.isDirectory() && (strFile.endsWith(".html") || strFile.endsWith(".htm") || strFile.endsWith(".xsl") || strFile.endsWith(".xslt") ))
                    {
                        fUp.saveFileParsed(pInForm, strWorkPath, webWorkPath + base.getResourceWorkPath() +"/images/");
                        String strAttaches = fUp.FindAttaches(pInForm);

                        file = new File(strWorkPath + "images/");
                        if (!file.exists()) file.mkdir();
                        if (file.exists() && file.isDirectory())
                        {
                            sbfRet.append("\n");
                            //sbfRet.append("<OBJECT WIDTH=100% HEIGHT=100% classid=\"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93\" codebase=\"http://java.sun.com/products/plugin/autodl/jinstall-1_4_0-win.cab#Version=1,4,0,0\" border=0><NOEMBED><XMP>\n");
                            sbfRet.append("<APPLET  WIDTH=100% HEIGHT=100% CODE=\"applets.dragdrop.DragDrop.class\" codebase=\""+WBUtils.getInstance().getWebPath()+"\" archive=\"wbadmin/lib/DragDrop.jar, wbadmin/lib/WBCommons.jar\" border=0>");
                            //sbfRet.append("<PARAM NAME =\"JSESS\" VALUE=\"" + idsession + "\">\n");
                            sbfRet.append("<PARAM NAME=\"webpath\" VALUE=\""+WBUtils.getInstance().getWebPath()+"\">\n");
                            sbfRet.append("<PARAM NAME=\"foreground\" VALUE=\"000000\">\n");
                            sbfRet.append("<PARAM NAME=\"background\" VALUE=\"979FC3\">\n");
                            sbfRet.append("<PARAM NAME=\"foregroundSelection\" VALUE=\"ffffff\">\n");
                            sbfRet.append("<PARAM NAME=\"backgroundSelection\" VALUE=\"666699\">\n");
                            sbfRet.append("<PARAM NAME=\"path\" value=\"" + strWorkPath + "images/" + "\">\n");
                            sbfRet.append("<PARAM NAME=\"clientpath\" value=\"" + strClientPath + "\">\n");
                            sbfRet.append("<PARAM NAME=\"files\" value=\"" + strAttaches + "\">\n");
                            //sbfRet.append("<COMMENT>\n");
                            //sbfRet.append("<EMBED\n");
                            //sbfRet.append("     type=\"application/x-java-applet;version=1.4\"\n");
                            //sbfRet.append("     CODE = \"DragDrop.class\"\n");
                            //sbfRet.append("     ARCHIVE = \"DragDrop.jar\"\n");
                            //sbfRet.append("     WIDTH = 100%\n");
                            //sbfRet.append("     HEIGHT = 100%\n");
                            //sbfRet.append("     foreground=\"979FC3\"\n");
                            //sbfRet.append("     background=\"979FC3\"\n");
                            //sbfRet.append("     scriptable=false\n");
                            //sbfRet.append("     pluginspage=\"http://java.sun.com/products/plugin/index.html#download\">\n");
                            //sbfRet.append("<NOEMBED></NOEMBED>\n");
                            //sbfRet.append("</EMBED>\n");
                            //sbfRet.append("</COMMENT>\n");
                            sbfRet.append("</APPLET>\n");
                        }
                    }
                    else uploadFile(base, fUp, pInForm);
                }
            }
        } 
        catch (Exception e) { AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_uploadFileParsed_exc01") + " " + base.getId() + AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_uploadFileParsed_exc02") + " " + base.getType() + AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_uploadFileParsed_exc03") + " " + strFile + ".", true); }
        return sbfRet.toString();
    }

    /**
     * Sube un texto parseado al filesystem.
     *
     * @param     base      La informaci�n del recurso en memoria.
     * @param     pfUpload  Objeto que permite obtener la informaci�n del formulario con tipo de codificaci�n multipart/form-data.
     * @param     pInSource   El nombre del campo del formulario donde se defini� el archivo.
     * @return    Regresa un nuevo String que contiene el applet para subir las im�genes relativas al texto parseado.
     */
    public String uploadSourceParsed(Resource base, FileUpload fUp, String pInSource, String pInPath, String pInFile, String idsession)
    {
        StringBuffer sbfRet = new StringBuffer();
        String strWorkPath = workPath;
        String strSource = null, strFile = null,  strClientPath = "";
        int intPos;
        try
        {
            strSource = fUp.getValue(pInSource);
            strFile = fUp.getValue(pInFile);
            if (strSource != null && !strSource.trim().equals("") && strFile != null && !strFile.trim().equals(""))
            {
                if (fUp.getValue(pInPath) != null && !fUp.getValue(pInPath).trim().equals(""))
                {
                    strClientPath = fUp.getValue(pInPath);
                    strClientPath=strClientPath.replace('\\','/');
                    if (!strClientPath.endsWith("/")) strClientPath = strClientPath + "/";
                }
                strWorkPath+=base.getResourceWorkPath()+"/";
                File file=new File(strWorkPath);
                if(file!=null)
                {
                    if(!file.exists()) file.mkdirs();
                    if (file.exists() && file.isDirectory())
                    {
                        String strAttaches = WBUtils.getInstance().FindAttaches(strSource);
                        String strSrcParsed = WBUtils.getInstance().parseHTML(strSource, webWorkPath + base.getResourceWorkPath() +"/images/");

                        if (saveSourceToFile(strSrcParsed, strWorkPath + strFile))
                        {
                            file = new File(strWorkPath + "images/");
                            if (!file.exists()) file.mkdir();
                            if (file.exists() && file.isDirectory())
                            {
                                sbfRet.append("\n");
                                //sbfRet.append("<OBJECT WIDTH=100% HEIGHT=100% classid=\"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93\" codebase=\"http://java.sun.com/products/plugin/autodl/jinstall-1_4_0-win.cab#Version=1,4,0,0\" border=0><NOEMBED><XMP>\n");
                                sbfRet.append("<APPLET  WIDTH=100% HEIGHT=100% CODE=\"applets.dragdrop.DragDrop.class\" codebase=\""+WBUtils.getInstance().getWebPath()+"\" archive=\"wbadmin/lib/DragDrop.jar, wbadmin/lib/WBCommons.jar\" border=0>");
                                //sbfRet.append("<APPLET  WIDTH=100% HEIGHT=100% CODE=\"DragDrop.class\" archive=\"" + webPath + "admin/DragDrop.jar\" border=0></XMP>");
                                //sbfRet.append("<PARAM NAME=CODE VALUE=\"DragDrop.class\">\n");
                                //sbfRet.append("<PARAM NAME=ARCHIVE VALUE=\"" + webPath + "admin/DragDrop.jar\">\n");
                                //sbfRet.append("<PARAM NAME=\"cache_archive\" VALUE=\"" + webPath + "admin/DragDrop.jar\">\n");
                                //sbfRet.append("<PARAM NAME=\"_cache_version\" VALUE=\"1.0.0.1\">\n");
                                //sbfRet.append("<PARAM NAME=\"_cache_archive_ex\" VALUE=\"applet.jar;preload, util.jar;preload,tools.jar;preload\">\n");
                                //sbfRet.append("<PARAM NAME=\"type\" VALUE=\"application/x-java-applet;version=1.4\">\n");
                                //sbfRet.append("<PARAM NAME=\"scriptable\" VALUE=\"false\">\n");
                                //sbfRet.append("<PARAM NAME =\"JSESS\" VALUE=\"" + idsession + "\">\n");
                                sbfRet.append("<PARAM NAME=\"webpath\" VALUE=\""+WBUtils.getInstance().getWebPath()+"\">\n");
                                sbfRet.append("<PARAM NAME=\"foreground\" VALUE=\"000000\">\n");
                                sbfRet.append("<PARAM NAME=\"background\" VALUE=\"979FC3\">\n");
                                sbfRet.append("<PARAM NAME=\"foregroundSelection\" VALUE=\"ffffff\">\n");
                                sbfRet.append("<PARAM NAME=\"backgroundSelection\" VALUE=\"666699\">\n");
                                sbfRet.append("<PARAM NAME=\"path\" value=\"" + strWorkPath + "images/" + "\">\n");
                                sbfRet.append("<PARAM NAME=\"clientpath\" value=\"" + strClientPath + "\">\n");
                                sbfRet.append("<PARAM NAME=\"files\" value=\"" + strAttaches + "\">\n");
                                //sbfRet.append("<COMMENT>\n");
                                //sbfRet.append("<EMBED\n");
                                //sbfRet.append("     type=\"application/x-java-applet;version=1.4\"\n");
                                //sbfRet.append("     CODE = \"DragDrop.class\"\n");
                                //sbfRet.append("     ARCHIVE = \"DragDrop.jar\"\n");
                                ////sbfRet.append("     WIDTH = 100%\n");
                                //sbfRet.append("     HEIGHT = 100%\n");
                                //sbfRet.append("     foreground=\"979FC3\"\n");
                                //sbfRet.append("     background=\"979FC3\"\n");
                                //sbfRet.append("     scriptable=false\n");
                                //sbfRet.append("     pluginspage=\"http://java.sun.com/products/plugin/index.html#download\">\n");
                                //sbfRet.append("<NOEMBED></NOEMBED>\n");
                                //sbfRet.append("</EMBED>\n");
                                //sbfRet.append("</COMMENT>\n");
                                sbfRet.append("</APPLET>\n");
                            }
                        }
                    }
                }
            }
        } 
        catch (Exception e) { AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_uploadSourceParced_Exc01") + " " + base.getId() + AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_uploadSourceParced_Exc02") + " " + base.getType() + ".", true); }
        return sbfRet.toString();
    }

    /**
     * Obtiene el nombre del archivo de una ruta.
     *
     * @param     pFile     El nombre del archivo que se va a guardar.
     * @return    Regresa un nuevo String que contiene el nombre del archivo que se guard�.
     */
    public String getFileName(Resource base, String pFile)
    {
        String ret="";
        try 
        { 
            ret=getFileName(pFile);
        } 
        catch (Exception e) { AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_getFileName_exc01") + " " + base.getId() + AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_getFileName_exc02") + " " + base.getType() + AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_getFileName_excp03") + " " + pFile + ".", true); }
        return ret;
    }
    
    /**
     * Obtiene el nombre del archivo de una ruta.
     *
     * @param     pFile     El nombre del archivo que se va a guardar.
     * @return    Regresa un nuevo String que contiene el nombre del archivo que se guard�.
     */
    public String getFileName(String pFile)
    {
        String ret="";
        if(pFile!=null && !"".equals(pFile.trim())) 
        {
            //ret=(new File(pFile)).getName(); 
            pFile=pFile.replace('\\','/');
            int intPos = pFile.lastIndexOf("/");
            if (intPos != -1) ret = pFile.substring(intPos + 1).trim();
            else ret=pFile;
        }
        return ret;
    }
        

    /**
     * Valida extensi�n de archivo
     *
     * @param     pFile     El nombre del archivo que se va a guardar.
     * @param     pExt      Lista de extensiones dentro de las cuales el archivo debe pertenecer a alguna.
     * @return    Regresa un nuevo String que contiene la descripci�n del error en caso de que el archivo
     *            no cumpliera con la extensi�n requerida.
     */
    public boolean isFileType(String pFile, String pExt)
    {
        boolean bOk = false;
        try
        {
            String strExt="";
            if(pFile!=null && !"".equals(pFile.trim())) 
            {
                //pFile=(new File(pFile)).getName();
                pFile=getFileName(pFile);
                int intPos = pFile.lastIndexOf(".");
                if (intPos != -1) 
                {
                    strExt = pFile.substring(intPos + 1).trim().toLowerCase();
                    StringTokenizer strToken = new StringTokenizer(pExt, "|");
                    while (strToken.hasMoreTokens())
                    {
                        if (strExt.equals(strToken.nextToken()))
                        {
                            bOk = true;
                            break;
                        }
                    }
                }
            }
        } 
        catch (Exception e) { AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_isFileType_error") + pFile, true); }
        return bOk;
    }

    /**
     * Despliega imagen en su representaci�n html.
     *
     * @param   base    La informaci�n del recurso en memoria.
     * @param   pImage  El nombre de la imagen que se va a desplegar.
     * @return  Regresa un nuevo String que contiene la representaci�n html de la imagen.
     */
    public String displayImage(Resource base, String pImage, String pNode)
    {
        StringBuffer sbfRet = new StringBuffer("");
        try
        {
            String img = base.getAttribute(pNode, "").trim();
            if (!img.equals(""))
            {
                String width = base.getAttribute("imgwidth", "").trim();
                String height = base.getAttribute("imgheight", "").trim();

                if (img.endsWith(".swf"))
                {
                    sbfRet.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0\"");
                    if (!width.equals("")) sbfRet.append(" width=\"" + width + "\"");
                    if (!height.equals(""))  sbfRet.append(" height=\"" + height + "\"");
                    sbfRet.append(">");
                    sbfRet.append("<param name=movie value=\"" + webWorkPath + base.getResourceWorkPath() + "/"+ img +"\">");
                    sbfRet.append("<param name=quality value=high>");
                    sbfRet.append("<embed src=\"");
                    sbfRet.append(webWorkPath + base.getResourceWorkPath() + "/"+ img);
                    sbfRet.append("\" quality=high pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\"");
                    if (!width.equals("")) sbfRet.append(" width=\"" + width + "\"");
                    if (!height.equals("")) sbfRet.append(" height=\"" + height + "\"");
                    sbfRet.append(">");
                    sbfRet.append("</embed>");
                    sbfRet.append("</object>");
                } 
                else
                {
                    sbfRet.append("\n<img src=\"");
                    sbfRet.append(webWorkPath + base.getResourceWorkPath() +"/"+ img + "\"");
                    if (!width.equals("")) sbfRet.append(" width=\"" + width + "\"");
                    if (!height.equals("")) sbfRet.append(" height=\"" + height + "\"");
                    sbfRet.append(" hspace=5 border=0>");
                }
            }
        }
        catch (Exception e) 
        { 
            sbfRet.append("\n<br>\n<!--Exception " + e + "-->"); 
            AFUtils.log(e, "Error while displaying image in resource "+base.getId()+".", true);
        }
        return sbfRet.toString();
    }

    /**
     * Envia correo electr�nico.
     *
     * @param     fromEmail El correo electr�nico del remitente.
     * @param     toEmail   El correo electr�nico del destinatario.
     * @param     subject   El asunto del mensaje.
     * @param     headerMsg El encabezado del mensaje.
     * @param     footerMsg El pie de p�gina del mensaje.
     * @param     mensaje   El texto del mensaje.
     * @return    Regresa si el correo electr�nico fue enviado con �xito o no.
     */
    public boolean sendEmail(String fromEmail, String toEmail, String subject, String headerMsg, String footerMsg, String mensaje)
    {
        String strMsg = "";
        if (headerMsg != null) strMsg += headerMsg;
        if (mensaje != null)
        {
            //strMsg+= AFUtils.getLocaleString("locale_wb2_util","error_WBResource_sendEmail_escribe");
            strMsg += mensaje;
            strMsg += " <br><br>";
        }
        if (footerMsg != null) strMsg += footerMsg;

        if (fromEmail != null && toEmail != null)
        {
            try
            {
                AFUtils.getInstance().sendBGEmail(fromEmail, toEmail, null, null, subject, "text/html", 4, strMsg);
                /*
                sun.net.smtp.SmtpClient sm = new sun.net.smtp.SmtpClient((String)AFUtils.getInstance().getEnv("wb/smtpServer"));
                sm.from(fromEmail);
                if(toEmail.indexOf("|") != -1)
                {
                    StringTokenizer strToken=new StringTokenizer(toEmail,"|");
                    while(strToken.hasMoreTokens()) sm.to(strToken.nextToken());
                }
                else sm.to(toEmail);
                java.io.PrintStream msg = sm.startMessage();
                msg.println("From: " + fromEmail);
                msg.println("To: " +  toEmail);
                msg.println("Subject: " + subject);
                msg.println("X-Priority: 4");
                msg.println("Content-Type: text/html");
                msg.println();
                msg.println(strMsg);
                msg.println();
                sm.closeServer();
                */
                return true;
            }
            catch (Exception e) { AFUtils.log(e, AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_sendEmail_error"), true); }
        }
        return false;
    }

    /**
     * Encabezado de la administraci�n de recurso.
     *
     * @return    Regresa un nuevo String que contiene el encabezado de la administraci�n de recurso.
     */
    public String loadAdminHeader()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" bgcolor=\"#CCCCCC\">");
        sbfRet.append("\n<tr>");
        sbfRet.append("\n<td>");
        sbfRet.append("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" bgcolor=\"#FFFFFF\">");
        sbfRet.append("\n<tr>");
        sbfRet.append("\n<td>");
        sbfRet.append("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">");
        sbfRet.append("\n<tr>");
        sbfRet.append("\n<td><font face=\"Verdana, Arial, Helvetica, sans-serif\" color=\"#666699\"><img src=\"" + webPath + "admin/images/ico_agregar.gif\" width=\"17\" height=\"17\"> ");
        sbfRet.append("\n" + AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_loadAdminHeader_datosgenerales") + ".</font></td>");
        sbfRet.append("\n</tr>");
        sbfRet.append("\n<tr> ");
        sbfRet.append("\n<td bgcolor=\"#979FC3\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\" color=\"#FFFFFF\">");
        sbfRet.append("\n" + AFUtils.getLocaleString("locale_wb2_util", "error_WBResource_loadAdminHeader_pidedatos") + ":</font></td>");
        sbfRet.append("\n</tr>");
        sbfRet.append("\n<tr> ");
        sbfRet.append("\n<td> ");
        return sbfRet.toString();
    }

    /**
     * Pie de p�gina de la administraci�n de recurso.
     *
     * @return    Regresa un nuevo String que contiene el iie de p�gina de la administraci�n de recurso.
     */
    public String loadAdminFooter()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\n</td>");
        sbfRet.append("\n</tr>");
        sbfRet.append("\n</table>");
        sbfRet.append("\n</td>");
        sbfRet.append("\n</tr>");
        sbfRet.append("\n</table>");
        sbfRet.append("\n</td>");
        sbfRet.append("\n</tr>");
        sbfRet.append("\n</table>");
        sbfRet.append(loadHtmlStyles());
        return sbfRet.toString();
    }

    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n setPrefix() de JavaScript.
     */
    public String loadSetPrefix()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction setPrefix(pIn, pPx)");
        sbfRet.append("\n{");
        sbfRet.append("\n   if(pIn.type==\"text\")");
        sbfRet.append("\n   {");
        sbfRet.append("\n       if (pIn.value.substring(0, pPx.length).indexOf(pPx)==-1)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadSetPrefix_msg1") + ": ' + pPx);");
        sbfRet.append("\n           pIn.value=pPx+pIn.value;");
        sbfRet.append("\n           pIn.focus();");
        sbfRet.append("\n           return false;");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n   if(pIn.type==\"select-one\" || pIn.type==\"select-multiple\")");
        sbfRet.append("\n   {");
        sbfRet.append("\n       for(var i=0; i<pIn.length; i++)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           if (pIn.options[i].value.substring(0, pPx.length).indexOf(pPx)==-1)");
        sbfRet.append("\n           {");
        sbfRet.append("\n               alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadSetPrefixmsg2") + ": ' + pPx);");
        sbfRet.append("\n               pIn.focus();");
        sbfRet.append("\n               return false;");
        sbfRet.append("\n           }");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n   return true;");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n isNumber() de JavaScript.
     */
    public String loadIsNumber()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction isNumber(pIn)");
        sbfRet.append("\n{");
        sbfRet.append("\n   pCaracter=pIn.value;");
        sbfRet.append("\n   for (var i=0;i<pCaracter.length;i++)");
        sbfRet.append("\n   {");
        sbfRet.append("\n       var sByte=pCaracter.substring(i,i+1);");
        sbfRet.append("\n       if (sByte<\"0\" || sByte>\"9\")");
        sbfRet.append("\n       {");
        sbfRet.append("\n           pIn.focus();");
        sbfRet.append("\n           alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsNumber_msg") + ".');");
        sbfRet.append("\n           return false;");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n   return true;");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n isFormat() de JavaScript.
     */
    public String loadIsFormat()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction isFormat(pIn)");
        sbfRet.append("\n{");
        sbfRet.append("\n   var swFormat=\"0123456789\";");
        sbfRet.append("\n   for(var i=0; i < pIn.value.length; i++)");
        sbfRet.append("\n   {");
        sbfRet.append("\n       swOk= pIn.value.substring(i, i+1);");
        sbfRet.append("\n       if (swFormat.indexOf(swOk, 0)==-1)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsFormat_msgdecimal") + ".');");
        sbfRet.append("\n           pIn.focus();");
        sbfRet.append("\n           return false;");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n   return true;");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }
    
    public String loadIsHexadecimal()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction isHexadecimal(pIn)");
        sbfRet.append("\n{");
        sbfRet.append("\n   var swFormat=\"0123456789ABCDEF\";");
        sbfRet.append("\n   pIn.value=pIn.value.toUpperCase();");
        sbfRet.append("\n   if(pIn.value.length<7)");
        sbfRet.append("\n   {");
        sbfRet.append("\n       alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsHexadecinal_msgLength") + "');");
        sbfRet.append("\n       pIn.focus();");
        sbfRet.append("\n       return false;");
        sbfRet.append("\n   }");
        sbfRet.append("\n   if (!setPrefix(pIn, '#')) return false;");
        sbfRet.append("\n   for(var i=1; i < pIn.value.length; i++)");
        sbfRet.append("\n   {");
        sbfRet.append("\n       swOk= pIn.value.substring(i, i+1);");
        sbfRet.append("\n       if (swFormat.indexOf(swOk, 0)==-1)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsHexadecinal_msgHexadecinal") + "');");
        sbfRet.append("\n           pIn.focus();");
        sbfRet.append("\n           return false;");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n   return true;");
        sbfRet.append("\n}");  
        return sbfRet.toString();
    }    

    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n isFileType() de JavaScript.
     */
    public String loadIsFileType()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction isFileType(pFile, pExt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   if(pFile.value.length > 0)");
        sbfRet.append("\n   {");
        sbfRet.append("\n      var swFormat=pExt + '|';");
        sbfRet.append("\n      sExt=pFile.value.substring(pFile.value.indexOf(\".\")).toLowerCase();");
        sbfRet.append("\n      var sType='';");
        sbfRet.append("\n      while(swFormat.length > 0 )");
        sbfRet.append("\n      {");
        sbfRet.append("\n         sType= swFormat.substring(0, swFormat.indexOf(\"|\"));");
        sbfRet.append("\n         if(sExt.indexOf(sType)!=-1) return true;");
        sbfRet.append("\n         swFormat=swFormat.substring(swFormat.indexOf(\"|\")+1);");
        sbfRet.append("\n      }");
        sbfRet.append("\n      while(pExt.indexOf(\"|\")!=-1) pExt=pExt.replace('|',',');");
        sbfRet.append("\n      alert(\"" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsFile_msgext") + ": \" + pExt.replace('|',','));");
        sbfRet.append("\n      return false;");
        sbfRet.append("\n   }");
        sbfRet.append("\n   else return true;");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n isEmail() de JavaScript.
     */
    public String loadIsEmail()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction isEmail(pInTxt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   var swOK=2;");
        sbfRet.append("\n   pCaracter=pInTxt.value.replace(\" \",\"\0\");");
        sbfRet.append("\n   for (var i = 0; i < pCaracter.length; i++)");
        sbfRet.append("\n   {");
        sbfRet.append("\n      var sByte = pCaracter.substring(i, i + 1);");
        sbfRet.append("\n      if (sByte==\"@\" || sByte==\".\") { swOK = swOK - 1; }");
        sbfRet.append("\n   }");
        sbfRet.append("\n   if (swOK > 0 || pCaracter.length < 5 || pCaracter.charAt(0) == '@' || pCaracter.charAt(0) == '.' || pCaracter.charAt(pCaracter.length-1)=='@' || pCaracter.charAt(pCaracter.length-1)=='.' || pCaracter.charAt(pCaracter.indexOf(\"@\")+1)=='.' || pCaracter.indexOf(\"@\") == -1)");
        sbfRet.append("\n   {");
        sbfRet.append("\n      pInTxt.focus();");
        sbfRet.append("\n      alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsEmail_msg") + ".');");
        sbfRet.append("\n      return false;");
        sbfRet.append("\n   }");
        sbfRet.append("\n   return true;");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }
    
    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n isValidTime() de JavaScript.
     */
    public String loadIsValidTime()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction isValidTime(field) {");
        sbfRet.append("\n    var timeStr = field.value;");
        sbfRet.append("\n    if (timeStr!='') {");
        sbfRet.append("\n       var timePat = /^(\\d{1,2}):(\\d{2})(:(\\d{2}))?(\\s?(AM|am|PM|pm))?$/;");
        sbfRet.append("\n       var matchArray = timeStr.match(timePat);");
        sbfRet.append("\n       var ok = 'yes';");
        sbfRet.append("\n       if (matchArray != null) {");
        sbfRet.append("\n           hour = matchArray[1];");
        sbfRet.append("\n           minute = matchArray[2];");
        sbfRet.append("\n           second = matchArray[4];");
        sbfRet.append("\n           ampm = matchArray[6];");
        sbfRet.append("\n           if (second=='') { second = null; }");
        sbfRet.append("\n           if (ampm=='') { ampm = null }");
        sbfRet.append("\n           if (hour < 0  || hour > 23) {");
        sbfRet.append("\n           alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsValidTime_msgHour") + "');");
        sbfRet.append("\n           ok = 'no';");
        sbfRet.append("\n       }");
        sbfRet.append("\n       if  (hour > 12 && ampm != null) {");
        sbfRet.append("\n           alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsValidTime_msgNotAMPM") + "');");
        sbfRet.append("\n           ok = 'no';");
        sbfRet.append("\n       }");
        sbfRet.append("\n       if (minute<0 || minute > 59) {");
        sbfRet.append("\n           alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsValidTime_msgMinutes") + "');");
        sbfRet.append("\n           ok = 'no';");
        sbfRet.append("\n       }");
        sbfRet.append("\n       if (second != null && (second < 0 || second > 59)) {");
        sbfRet.append("\n           alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsValidTime_msgSeconds") + "');");
        sbfRet.append("\n           ok = 'no';");
        sbfRet.append("\n       }");
        sbfRet.append("\n    }");
        sbfRet.append("\n    else{");
        sbfRet.append("\n        alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsValidTime_msgWrongFormat") + "');");
        sbfRet.append("\n        ok = 'no';");
        sbfRet.append("\n    }");
        sbfRet.append("\n    if (ok == 'no') {");
        sbfRet.append("\n       field.focus();");
        sbfRet.append("\n       field.select();");
        sbfRet.append("\n    }");
        sbfRet.append("\n   }");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }    


    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n replaceChars() de JavaScript.
     */
    public String loadReplaceChars()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction replaceChars(pIn)");
        sbfRet.append("\n{");
        sbfRet.append("\n   out = \"\\r\"; // replace this");
        sbfRet.append("\n   add = \"<br/>\"; // with this");
        sbfRet.append("\n   temp = \"\" + pIn.value; // temporary holder");
        sbfRet.append("\n   while (temp.indexOf(out)>-1)");
        sbfRet.append("\n   {");
        sbfRet.append("\n      pos= temp.indexOf(out);");
        sbfRet.append("\n      temp = \"\" + (temp.substring(0, pos) + add + temp.substring((pos + out.length), temp.length));");
        sbfRet.append("\n   }");
        sbfRet.append("\n   out = \"\\n\"; // replace this");
        sbfRet.append("\n   add = \" \"; // with this");
        sbfRet.append("\n   temp = \"\" + temp; // temporary holder");
        sbfRet.append("\n   while (temp.indexOf(out)>-1)");
        sbfRet.append("\n   {");
        sbfRet.append("\n      pos= temp.indexOf(out);");
        sbfRet.append("\n      temp = \"\" + (temp.substring(0, pos) + add + temp.substring((pos + out.length), temp.length));");
        sbfRet.append("\n   }");
        sbfRet.append("\n   pIn.value = temp;");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }
    
    public String loadReplaceCharsBySpaces()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction replaceCharsBySpaces(pIn)");
        sbfRet.append("\n{");
        sbfRet.append("\n   out = \"\\r\"; // replace this");
        sbfRet.append("\n   add = \" \"; // with this");
        sbfRet.append("\n   temp = \"\" + pIn.value; // temporary holder");
        sbfRet.append("\n   while (temp.indexOf(out)>-1)");
        sbfRet.append("\n   {");
        sbfRet.append("\n      pos= temp.indexOf(out);");
        sbfRet.append("\n      temp = \"\" + (temp.substring(0, pos) + add + temp.substring((pos + out.length), temp.length));");
        sbfRet.append("\n   }");
        sbfRet.append("\n   out = \"\\n\"; // replace this");
        sbfRet.append("\n   add = \" \"; // with this");
        sbfRet.append("\n   temp = \"\" + temp; // temporary holder");
        sbfRet.append("\n   while (temp.indexOf(out)>-1)");
        sbfRet.append("\n   {");
        sbfRet.append("\n      pos= temp.indexOf(out);");
        sbfRet.append("\n      temp = \"\" + (temp.substring(0, pos) + add + temp.substring((pos + out.length), temp.length));");
        sbfRet.append("\n   }");
        sbfRet.append("\n   pIn.value = temp;");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }    
    
    public String loadIsNotNull()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction isNotNull(pIn, msg)");
        sbfRet.append("\n{");
        sbfRet.append("\n   if(pIn.type==\"text\")");
        sbfRet.append("\n   {");
        sbfRet.append("\n       trim(pIn);");
        sbfRet.append("\n       if (pIn.value=='')");
        sbfRet.append("\n       {");
        sbfRet.append("\n           alert(msg);");
        sbfRet.append("\n           pIn.focus();");
        sbfRet.append("\n           return false;");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n   if(pIn.type==\"select-one\" || pIn.type==\"select-multiple\")");
        sbfRet.append("\n   {");
        sbfRet.append("\n       for(var i=0; i<pIn.length; i++)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           if (pIn.options[i].selected && pIn.options[i].value=='')");
        sbfRet.append("\n           {");
        sbfRet.append("\n               alert(msg);");
        sbfRet.append("\n               pIn.focus();");
        sbfRet.append("\n               return false;");
        sbfRet.append("\n           }");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n   return true;");
        sbfRet.append("\n}");        
        sbfRet.append(loadTrim());
        return sbfRet.toString();
    }
    
    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n trim() de JavaScript.
     */
    public String loadTrim()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction trim(field) ");
        sbfRet.append("\n{");
        sbfRet.append("\n   var retVal = '';");
        sbfRet.append("\n   var start = 0;");
        sbfRet.append("\n   while ((start < field.value.length) && (field.value.charAt(start) == ' ')) {");
        sbfRet.append("\n      ++start;");
        sbfRet.append("\n   }");
        sbfRet.append("\n   var end = field.value.length;");
        sbfRet.append("\n   while ((end > 0) && (field.value.charAt(end - 1) == ' ')) {");
        sbfRet.append("\n      --end;");
        sbfRet.append("\n   }");
        sbfRet.append("\n   retVal = field.value.substring(start, end);");
        sbfRet.append("\n   if (end == 0) field.value='';");
        sbfRet.append("\n   else field.value=retVal;");
        sbfRet.append("\n}");        
        return sbfRet.toString();
    }    

    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n addOption() de JavaScript.
     */
    public String loadAddOption()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction addOption(pInSel, pInTxt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   duplicateOption(pInSel, pInTxt);");
        sbfRet.append("\n   if(swOk!=1) ");
        sbfRet.append("\n   {");
        sbfRet.append("\n       optionObj = new Option(pInTxt.value, pInTxt.value);");
        sbfRet.append("\n       pInSel.options[pInSel.length]=optionObj;");
        sbfRet.append("\n   }");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n editOption() de JavaScript.
     */
    public String loadEditOption()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction editOption(pInSel, pInTxt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   pInTxt.value=pInSel.options[pInSel.selectedIndex].value;");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n updateOption() de JavaScript.
     */
    public String loadUpdateOption()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction updateOption(pInSel, pInTxt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   duplicateOption(pInSel, pInTxt);");
        sbfRet.append("\n   if(swOk!=1)");
        sbfRet.append("\n   {");
        sbfRet.append("\n       if(confirm('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadUpdateOption_msg") + " ' + pInSel.options[pInSel.selectedIndex].value + '?'))");
        sbfRet.append("\n       pInSel.options[pInSel.selectedIndex].value=pInTxt.value;");
        sbfRet.append("\n       pInSel.options[pInSel.selectedIndex].text=pInTxt.value;");
        sbfRet.append("\n   }");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n deleteOption() de JavaScript.
     */
    public String loadDeleteOption()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction deleteOption(pInSel, pInTxt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   var aryEle = new Array();");
        sbfRet.append("\n   if(confirm('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadDeleteOption_msg") + "'))");
        sbfRet.append("\n   {");
        sbfRet.append("\n       pInTxt.value='';");
        sbfRet.append("\n       for(var i=0, j=0; i<pInSel.length; i++)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           if(!pInSel[i].selected)");
        sbfRet.append("\n           {");
        sbfRet.append("\n               aryEle[j]=pInSel.options[i].value;");
        sbfRet.append("\n               j++;");
        sbfRet.append("\n           }");
        sbfRet.append("\n       }");
        sbfRet.append("\n       while(pInSel.length!=0)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           for( i=1;i<=pInSel.length;i++)");
        sbfRet.append("\n           {");
        sbfRet.append("\n               pInSel.options[0]=null;");
        sbfRet.append("\n           }");
        sbfRet.append("\n       }");
        sbfRet.append("\n       for(var i=0; i<aryEle.length; i++)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           optionObj = new Option(aryEle[i], aryEle[i]);");
        sbfRet.append("\n           pInSel.options[pInSel.length]=optionObj;");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n duplicateOption() de JavaScript.
     */
    public String loadDuplicateOption()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction duplicateOption(pInSel, pInTxt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   swOk=0;");
        sbfRet.append("\n   if(pInTxt.value==null || pInTxt.value=='' || pInTxt.value==' ')");
        sbfRet.append("\n   {");
        sbfRet.append("\n       alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadDuplicateOption_error") + ".');");
        sbfRet.append("\n       swOk=1;");
        sbfRet.append("\n   }");
        sbfRet.append("\n   for(var i=0; i<pInSel.length; i++)");
        sbfRet.append("\n   {");
        sbfRet.append("\n       if(pInSel.options[i].value==pInTxt.value)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           alert('" + AFUtils.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadDuplicateOption_msg") + " '+ pInTxt.value);");
        sbfRet.append("\n           swOk=1;");
        sbfRet.append("\n           break;");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Crea una funci�n JavaScript espec�fica.
     *
     * @return    Regresa un nuevo String que contiene la funci�n openHelp() de JavaScript.
     */
    public String loadOpenHelp()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction openHelp(pIdRsc, pField, pTemplate)");
        sbfRet.append("\n{");
        sbfRet.append("\n   window.open('" + webPath + "admin/help/resources/HelpResource.jsp?rsc=' + pIdRsc + '&fld=' + pField + '&tpl=' + pTemplate,'WBHelp','menubar=no,toolbar=no,status=no,location=no,directories=no,scrollbars=yes,resizable=yes,width=500,height=380,top=20,left=20');");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Obtiene los estilos por default.
     *
     * @return    Regresa un nuevo String que contiene los estilos.
     */
    public String loadHtmlStyles()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\n<STYLE type=\"text/css\">");
        sbfRet.append("\nA {");
        sbfRet.append("\nfont-family: Arial, Helvetica, sans-serif; font-size: 12px; ");
        sbfRet.append("\nfont-style: normal; line-height: 16px; font-weight: normal; font-variant: normal; ");
        sbfRet.append("\ntext-transform: none; color:#000099; text-decoration: none; }");
        sbfRet.append("\nA:hover {");
        sbfRet.append("\nfont-family: Arial, Helvetica, sans-serif; font-size: 12px; ");
        sbfRet.append("\nfont-style: normal; line-height: 16px; font-weight: bold; font-variant: normal; ");
        sbfRet.append("\ntext-transform: none; color:#000099; text-decoration: underline; ");
        sbfRet.append("\nbackground-color: #F4F4FB }");
        sbfRet.append("\n</STYLE>");
        return sbfRet.toString();
    }
    
    public String loadColorApplet(java.util.HashMap param)
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\n<applet name=\"");
        if(param.get("id")!=null) sbfRet.append((String)param.get("id"));
        else sbfRet.append("selColor");
        sbfRet.append("\" width=\"");
        if(param.get("width")!=null) sbfRet.append((String)param.get("width"));
        else sbfRet.append("293");
        sbfRet.append("\" height=\"");
        if(param.get("height")!=null) sbfRet.append((String)param.get("height"));
        else sbfRet.append("240");
        sbfRet.append("\" code=\"applets.selcolor.SelColor.class\" codebase=\""+WBUtils.getInstance().getWebPath()+"\" archive=\"wbadmin/lib/SelColor.jar\" border=\"0\">");
        sbfRet.append("\n<param name=\"name\" value=\"");
        if(param.get("id")!=null) sbfRet.append((String)param.get("id"));
        else sbfRet.append("selColor");
        sbfRet.append("\">");
        sbfRet.append("\n<param name=\"cache_archive\" value=\"" + webPath + "wbadmin/lib/SelColor.jar\">");
        sbfRet.append("\n<param name=\"_cache_version\" value=\"1.0.0.1\">");
        sbfRet.append("\n<param name=\"_cache_archive_ex\" value=\"applet.jar;preload, util.jar;preload,tools.jar;preload\">");
        sbfRet.append("\n<param name=\"type\" value=\"application/x-java-applet;version=1.4\">");
        sbfRet.append("\n<param name=\"scriptable\" value=\"true\">");
        //sbfRet.append("\n<param name=\"JSESS\" value=\"");
        //if(param.get("session")!=null) sbfRet.append((String)param.get("session"));
        //sbfRet.append("\">");
        sbfRet.append("\n<param name=\"webpath\" value=\"" + webPath + "\">");
        sbfRet.append("\n<param name=\"foreground\" value=\"");
        if(param.get("foreground")!=null) sbfRet.append((String)param.get("foreground"));
        else  sbfRet.append("99cccc");
        sbfRet.append("\">");
        sbfRet.append("\n<param name=\"background\" value=\"");
        if(param.get("background")!=null) sbfRet.append((String)param.get("background"));
        else  sbfRet.append("99cccc");
        sbfRet.append("\">");
        sbfRet.append("\n<param name=\"linkcolor\" value=\"");
        if(param.get("linkcolor")!=null) sbfRet.append((String)param.get("linkcolor"));
        else  sbfRet.append("000000");
        sbfRet.append("\">");
        sbfRet.append("\n<param name=\"linkactual\" value=\"");
        if(param.get("linkactual")!=null) sbfRet.append((String)param.get("linkactual"));
        else  sbfRet.append("000000");
        sbfRet.append("\">");
        sbfRet.append("\n</applet>");
        return sbfRet.toString();
    }

    public String loadWindowConfiguration(Resource base, com.infotec.wb.lib.WBParamRequest paramsRequest)
    {
        StringBuffer ret = new StringBuffer("");
        try
        {        
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgMenubar")+"</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"menubar\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("menubar", "")))  ret.append(" checked");
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgToolbar")+"</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"toolbar\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("toolbar", "")))  ret.append(" checked");
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgStatusbar")+"</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"status\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("status", "")))  ret.append(" checked");
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgLocation")+"</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"location\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("location", "")))  ret.append(" checked");
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgDirectories")+"</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"directories\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("directories", "")))  ret.append(" checked");
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgScrollbars")+"</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"scrollbars\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("scrollbars", "")))  ret.append(" checked");
            ret.append("></td> \n");
            ret.append("</tr> \n");        
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgResizable")+"</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"resizable\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("resizable", "")))  ret.append(" checked");
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgWidth")+" "+ paramsRequest.getLocaleString("msgPixels") +":</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=text size=4 maxlength=4 name=width ");
            if (!"".equals(base.getAttribute("width", "").trim()))  ret.append(" value=\""+ base.getAttribute("width").trim() +"\"");
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgHeight")+" "+ paramsRequest.getLocaleString("msgPixels") +":</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=text size=4 maxlength=4 name=height ");
            if (!"".equals(base.getAttribute("height", "").trim()))  ret.append(" value=\""+ base.getAttribute("height").trim() +"\"");
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgTop")+" "+ paramsRequest.getLocaleString("msgPixels") +":</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=text size=4 maxlength=4 name=top ");
            if (!"".equals(base.getAttribute("top", "").trim()))  ret.append(" value=\""+ base.getAttribute("top").trim() +"\"");
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgLeft")+" "+ paramsRequest.getLocaleString("msgPixels") +":</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=text size=4 maxlength=4 name=left ");
            if (!"".equals(base.getAttribute("left", "").trim()))  ret.append(" value=\""+ base.getAttribute("left").trim() +"\"");
            ret.append("></td> \n");
            ret.append("</tr> \n");
        }
        catch(Exception e) {  AFUtils.log(e, "Error while generating form to load window configuration in resource "+base.getId()+".", true); }
        return ret.toString();
    }
    
    /**
     * Sube un archivo al filesystem. 
     *
     * @param     base      La informaci�n del recurso en memoria.
     * @param     pfUpload  Objeto que permite obtener la informaci�n del formulario con tipo de codificaci�n multipart/form-data.
     * @param     pInForm   El nombre del campo del formulario donde se defini� el archivo.
     * @return    Regresa un nuevo String que contiene el nombre del archivo que se guard�.
     */
     public boolean uploadFile(Resource base, WBFileUpload fUp, String pInForm) 
     {
        String strWorkPath=workPath;
        String strFile=null;
        boolean bOk=false;
        try
        {
            strFile=fUp.getFileName(pInForm);
            if(strFile!=null && !strFile.trim().equals(""))
            {
                strWorkPath+=base.getResourceWorkPath()+"/";
                File file=new File(strWorkPath);
                if(file!=null)
                {
                    if(!file.exists()) file.mkdirs();
                    if(file.exists() && file.isDirectory()) bOk=fUp.saveFile(pInForm, strWorkPath, new String(), new String());
                }
            }
        }
        catch(Exception e) { AFUtils.getInstance().log(e,"Error while uploading file in resource "+base.getId()+".", true); }
        return bOk;
    }
    
     /**
     * Sube un archivo parseado al filesystem. 
     *
     * @param     base      La informaci�n del recurso en memoria.
     * @param     pfUpload  Objeto que permite obtener la informaci�n del formulario con tipo de codificaci�n multipart/form-data.
     * @param     pInForm   El nombre del campo del formulario donde se defini� el archivo.
     * @return    Regresa un nuevo String que contiene el applet para subir las im�genes relativas al archivo parseado.
     */
     public String uploadFileParsed(Resource base, WBFileUpload fUp, String pInForm, String idsession) 
     {
        StringBuffer strb=new StringBuffer("");
        String strWorkPath=workPath;
        String strFile=null, strClientPath="";
        int intPos;
        try
        {
            strFile=fUp.getFileName(pInForm);
            if(strFile!=null && !strFile.trim().equals(""))
            {
                strFile=strFile.replace('\\','/');
                intPos=strFile.lastIndexOf("/");
                if(intPos != -1) 
                {
                    strClientPath=strFile.substring(0, intPos);
                    strFile=strFile.substring(intPos+1).trim();
                }
                if(!strClientPath.endsWith("/")) strClientPath=strClientPath+"/";

                strWorkPath+=base.getResourceWorkPath()+"/";
                File file=new File(strWorkPath);
                if(file!=null)
                {
                    if(!file.exists()) file.mkdirs();                
                    if(file.exists() && file.isDirectory() && (strFile.endsWith(".html") || strFile.endsWith(".htm") || strFile.endsWith(".xsl") || strFile.endsWith(".xslt") ))
                    {
                        fUp.saveFileParsed(pInForm, strWorkPath, webWorkPath + base.getResourceWorkPath() +"/images/");
                        String strAttaches=fUp.FindAttaches(pInForm);                    
                        
                        file=new File(strWorkPath + "images/");
                        if(!file.exists()) file.mkdir();
                        if(file.exists() && file.isDirectory())
                        {
                            strb.append("\n");
                            //strb.append("<OBJECT WIDTH=100% HEIGHT=100% classid=\"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93\" codebase=\"http://java.sun.com/products/plugin/autodl/jinstall-1_4_0-win.cab#Version=1,4,0,0\" border=0><NOEMBED><XMP>\n");
                            strb.append("<APPLET  WIDTH=100% HEIGHT=100% CODE=\"applets.dragdrop.DragDrop.class\" codebase=\""+WBUtils.getInstance().getWebPath()+"\" archive=\"wbadmin/lib/DragDrop.jar, wbadmin/lib/WBCommons.jar\" border=0>");
                            //strb.append("<PARAM NAME=CODE VALUE=\"DragDrop.class\">\n");
                            //strb.append("<PARAM NAME=ARCHIVE VALUE=\""+ webPath + "admin/DragDrop.jar\">\n");
                            //strb.append("<PARAM NAME=\"cache_archive\" VALUE=\""+ webPath + "admin/DragDrop.jar\">\n");
                            //strb.append("<PARAM NAME=\"_cache_version\" VALUE=\"1.0.0.1\">\n");
                            //strb.append("<PARAM NAME=\"_cache_archive_ex\" VALUE=\"applet.jar;preload, util.jar;preload,tools.jar;preload\">\n");
                            //strb.append("<PARAM NAME=\"type\" VALUE=\"application/x-java-applet;version=1.4\">\n");
                            //strb.append("<PARAM NAME=\"scriptable\" VALUE=\"false\">\n");
                            //strb.append("<PARAM NAME =\"JSESS\" VALUE=\""+idsession+"\">\n");
                            strb.append("<PARAM NAME=\"webpath\" VALUE=\""+WBUtils.getInstance().getWebPath()+"\">\n");
                            strb.append("<PARAM NAME=\"foreground\" VALUE=\"000000\">\n");
                            strb.append("<PARAM NAME=\"background\" VALUE=\"979FC3\">\n");
                            strb.append("<PARAM NAME=\"foregroundSelection\" VALUE=\"ffffff\">\n");
                            strb.append("<PARAM NAME=\"backgroundSelection\" VALUE=\"666699\">\n");
                            strb.append("<PARAM NAME=\"path\" value=\""+ strWorkPath + "images/" +"\">\n");
                            strb.append("<PARAM NAME=\"clientpath\" value=\""+ strClientPath +"\">\n");
                            strb.append("<PARAM NAME=\"files\" value=\""+ strAttaches +"\">\n");
                            strb.append("<PARAM NAME=\"locale\" value=\""+ AFUtils.getLocale() +"\">\n");
                            //strb.append("<COMMENT>\n");
                            //strb.append("<EMBED\n");
                            //strb.append("     type=\"application/x-java-applet;version=1.4\"\n");
                            //strb.append("     CODE = \"DragDrop.class\"\n");
                            //strb.append("     ARCHIVE = \"DragDrop.jar\"\n");
                            //strb.append("     WIDTH = 100%\n");
                            //strb.append("     HEIGHT = 100%\n");
                            //strb.append("     foreground=\"979FC3\"\n");
                            //strb.append("     background=\"979FC3\"\n");
                            //strb.append("     scriptable=false\n");
                            //strb.append("     pluginspage=\"http://java.sun.com/products/plugin/index.html#download\">\n");
                            //strb.append("<NOEMBED></NOEMBED>\n");
                            //strb.append("</EMBED>\n");
                            //strb.append("</COMMENT>\n");
                            strb.append("</APPLET>\n");
                        }
                    }
                    else uploadFile(base, fUp, pInForm);
                }
            }
        }
        catch(Exception e) { AFUtils.getInstance().log(e,"Error while uploading file in resource "+base.getId()+".", true); }
        if(strb!=null) return strb.toString();
        else return null;
    }    
    
     
     /**
     * Despliega imagen en su representaci�n html.
     *
     * @param   base    La informaci�n del recurso en memoria.
     * @param   filename  El nombre de la imagen que se va a desplegar.
     * @return  Regresa un nuevo String que contiene la representaci�n html de la imagen.
     */
     public String displayImage(Resource base, String filename, int width, int height) 
     {
        StringBuffer strb=new StringBuffer();
        try
        {
            if(filename.endsWith(".swf")) 
            { //para desplegar imagenes de flash
                strb.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0\"");
                if(width>0) strb.append(" width=\""+width+"\"");
                if(height>0) strb.append(" height=\""+height+"\"");
                strb.append(">");
                strb.append("<param name=movie value=\""+webWorkPath + base.getResourceWorkPath() +"/");
                strb.append(filename);
                strb.append("\">");
                strb.append("<param name=quality value=high>");
                strb.append("<embed src=\"");
                strb.append(webWorkPath + base.getResourceWorkPath() +"/");
                strb.append(filename);
                strb.append("\" quality=high pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\"");
                if(width>0) strb.append(" width=\""+width+"\"");
                if(height>0) strb.append(" height=\""+height+"\"");
                strb.append(">");
                strb.append("</embed>");
                strb.append("</object>");
            }
            else 
            { //para desplegar imagenes otros tipos de imagenes
                strb.append("\n<img src=\"");
                strb.append(webWorkPath + base.getResourceWorkPath() +"/"+ filename);
                strb.append("\"");
                if(width>0) strb.append(" width=\""+width+"\"");
                if(height>0) strb.append(" height=\""+height+"\"");
                strb.append(" hspace=5 border=0>");
            }
        }
        catch(Exception e) { AFUtils.log(e, "Error while displaying image "+webWorkPath + base.getResourceWorkPath() +"/"+ filename+" in resource "+base.getId()+".", true); }
        return strb.toString();
     }     
    
    /**
     * Codifica un String.
     *
     * @param     source  El String a codificar.
     * @return    Regresa la representaci�n en String del argumento en hexadecimal (base 16).
     * @see       java.lang.Integer#toHexString(int)
     */
    public String encodeString(String source)
    {
        if(source==null) return "";
        StringBuffer ret = new StringBuffer();
        byte[] bytes = source.getBytes();
        for (int i = 0; i < bytes.length; i++)
        {
            Integer code = new Integer(bytes[i]);
            ret.append(Integer.toHexString(code.intValue()).toUpperCase());
        }
        return ret.toString();
    }

    public static ArrayList toArray(String str, String regex) 
    {
        ArrayList ret= new ArrayList();
        if(str!=null)
        {
            String[] obj=str.split(regex);
            for(int i=0; i < obj.length; i++)
                ret.add(obj[i]);
        }
        return ret;
    }
    
    /**
     * @param  date  Date with format  mm/dd/yyyy
     * @param  time  Time with format  0:00 � 0:00:00 (AM|PM optional) 
     */
    public java.sql.Timestamp parseTimestamp(String date, String time)
    {
        java.sql.Timestamp dt = new java.sql.Timestamp(System.currentTimeMillis());
        if(date!=null && date.length()==10)
        try
        {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(date.substring(6)));
            cal.set(Calendar.MONTH, Integer.parseInt(date.substring(0,2))-1);
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(3,5)));
            if(time!=null && time.length() > 3 && time.length() <=10)
            {
                time=time.toUpperCase();
                String[] timer=time.split(":");
                if(time.endsWith("AM") || time.endsWith("PM"))
                {
                    if(time.endsWith("AM")) cal.set(Calendar.AM_PM, Calendar.AM);
                    else if(time.endsWith("PM"))  cal.set(Calendar.AM_PM, Calendar.PM);

                    if(!timer[0].endsWith("AM") && !timer[0].endsWith("PM"))
                    cal.set(Calendar.HOUR, Integer.parseInt(timer[0]));
                    else cal.set(Calendar.HOUR, Integer.parseInt(timer[0].substring(0,2)));

                    if(timer.length>1) 
                    {
                        if(!timer[1].endsWith("AM") && !timer[1].endsWith("PM"))
                            cal.set(Calendar.MINUTE, Integer.parseInt(timer[1]));
                        else cal.set(Calendar.MINUTE, Integer.parseInt(timer[1].substring(0,2)));
                   }
                   if(timer.length>2) 
                   {
                        if(!timer[2].endsWith("AM") && !timer[2].endsWith("PM"))
                        cal.set(Calendar.SECOND, Integer.parseInt(timer[2]));
                        else cal.set(Calendar.SECOND, Integer.parseInt(timer[2].substring(0,2)));
                   }                    
                }
                else 
                {
                    cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timer[0]));
                    if(timer.length>1) cal.set(Calendar.MINUTE, Integer.parseInt(timer[1]));
                    if(timer.length>2) cal.set(Calendar.SECOND, Integer.parseInt(timer[2]));
                }
            }
            dt= new java.sql.Timestamp(cal.getTimeInMillis());
        }
        catch(Exception e) { AFUtils.log(e, "Error while trying to execute parseTimestamp: "+date +" "+time, true); }
        return dt;
    }
    
    /**
     * "HH:mm"      21:00           Hour in day (0-23) 
     * "h:mm a"     9:00 PM         Hour in am/pm (1-12) 
     * "K:mm a, z"  9:00 PM, PDT    Hour in am/pm (0-11)
     */
    public static String timeFormat(java.sql.Timestamp date) 
    {
        return dateFormat(date, "h:mm a");
    }
    
    public static String dateFormat(java.sql.Timestamp date) 
    {
        return dateFormat(date, "yyyy-MM-dd h:mm a");
    } 

    public static String dateFormat(java.sql.Timestamp date, String format) 
    {
        if(date==null) return "";
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(format);
        return df.format(date);
    }    
}