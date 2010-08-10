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
 * SWBAFTP.java
 *
 * Created on 11 de noviembre de 2004, 10:51 AM
 */

package org.semanticwb.portal.admin.resources;


import java.io.*;
import java.text.SimpleDateFormat;

import javax.servlet.http.*;
import javax.servlet.*;
import org.semanticwb.portal.api.*;
import org.w3c.dom.*;

import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.AdminFilter;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.portal.api.GenericResource;

// TODO: Auto-generated Javadoc
/** Recurso que despliega los archivos que se encuentran en el servidor, permitiendo
 * agregar archivos o carpetas, renombrarlos, eliminarlos y descargarlos.
 *
 * Resource that unfolds the archives that are in the servant, allowing to add
 * archives or folders, to rename, to eliminate and to download.
 * @author Victor Lorenzana
 */
public class SWBAFTP extends GenericResource{

    private static final SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBAFTP.class);


    public static final String[] prohibitedPaths={"/work",
        "/swbadmin","/work/models","/work/logs","/work/logs/*",
        "/WEB-INF","/work/config","/work/sitetemplates","/META-INF",
        "/WEB-INF/owl","/WEB-INF/classes","/WEB-INF/lib","/WEB-INF/dtds","/WEB-INF/license",
        "/WEB-INF/classes/db.properties",
        "/WEB-INF/classes/license.properties",
        "/WEB-INF/classes/topicmaps.properties",
        "/WEB-INF/classes/user.properties",
        "/WEB-INF/classes/web.properties",
        "/WEB-INF/web.xml",
    };
    /**
     * Creates a new instance of SWBAFTP.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */


    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(paramRequest.getMode().equals("gateway"))
        {
            doGateway(request,response,paramRequest);
        }        
        else if(paramRequest.getMode().equals("upload"))
        {
            doUpload(request,response,paramRequest);
        }
        else if(paramRequest.getMode().equals("download"))
        {
            doDownload(request,response,paramRequest);
        }        
        else super.processRequest(request,response,paramRequest);
        
        
    }

    public static void log(String msg,String ip)
    {
        SimpleDateFormat dfFile=new SimpleDateFormat("yyyy-MM");
        String logPath = SWBPortal.getWorkPath() + "/logs/wb_SWBAFTP."+ dfFile.format(new Date(System.currentTimeMillis()))  +".log";
        msg=df.format(new Date(System.currentTimeMillis()))+"|"+msg+"|IP:"+ip;
        try
        {
            SWBUtils.IO.log2File(logPath, msg);
        }
        catch(Exception e)
        {
            log.error(e);
        }
    }

    /**
     * Do download.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void doDownload(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path=request.getHeader("PATHFILEWB");        
        if(path!=null)
        {
            try
            {
                
                File f=new File(path);
                if(f.exists() && hasPermission(paramRequest.getUser(), f))
                {                    
                    response.setContentLength((int)f.length());
                    FileInputStream fin=new FileInputStream(f);            
                    OutputStream out=response.getOutputStream();
                    byte[] bcont=new byte[8192];
                    int ret=fin.read(bcont);
                    while(ret!=-1)
                    {
                        out.write(bcont,0, ret);
                        ret=fin.read(bcont);
                    }
                    fin.close();
                    out.close();
                }
                else
                {
                    response.sendError(500);
                }
            }
            catch(Exception e)
            {
                response.sendError(500,e.getMessage());
            }
        }
        else
        {
            response.sendError(500);
        }
    }
    
    /**
     * Do upload.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void doUpload(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path=request.getHeader("PATHFILEWB");        
        
        if(path!=null)
        {
            try
            {
                File f=new File(path);
                if(!hasPermission(paramRequest.getUser(), f))
                {
                    return;
                }
                if(isProtected(f))
                {
                   return;
                }
                if(f.isDirectory())
                {
                    log("CREATED|DIR:\""+f.getCanonicalPath() +"\"|USER:\""+paramRequest.getUser().getLogin()+"_"+ paramRequest.getUser().getUserRepository().getId() +"\"",request.getRemoteAddr());
                }
                else
                {
                    log("CREATED|FILE:\""+f.getCanonicalPath() +"\"|USER:\""+paramRequest.getUser().getLogin()+"_"+ paramRequest.getUser().getUserRepository().getId() +"\"",request.getRemoteAddr());
                }
                
                FileOutputStream fout=new FileOutputStream(f);            
                InputStream in=request.getInputStream();
                byte[] bcont=new byte[8192];
                int ret=in.read(bcont);
                while(ret!=-1)
                {
                    fout.write(bcont,0, ret);
                    ret=in.read(bcont);
                }
                in.close();
                fout.close();                
            }
            catch(Exception e)
            {
                response.sendError(500,e.getMessage());
            }
        }
        else
        {
            response.sendError(500);
        }
    }
    
    /**
     * Do gateway.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        ServletInputStream in = request.getInputStream();
        Document dom = SWBUtils.XML.xmlToDom(in);
        if (!dom.getFirstChild().getNodeName().equals("req"))
        {
            response.sendError(404, request.getRequestURI());
            return;
        }
        String cmd = null;
        if (dom.getElementsByTagName("cmd").getLength() > 0)
            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();

        if (cmd == null)
        {
            response.sendError(404, request.getRequestURI());
            return;
        }
        String ret="";
        try
        {
            Document res = getService(cmd, dom, paramRequest.getUser(), request, response);
            if (res == null)
            {
                ret = SWBUtils.XML.domToXml(getError(3));
            } else
                ret = SWBUtils.XML.domToXml(res, true);
        }catch(Exception e){log.error(e);}
        out.print(new String(ret.getBytes()));
        
    }
    
    /**
     * Gets the service.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the service
     */
    private Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {
        return getDocument(user, src, cmd,request.getRemoteAddr());
    }
    
    /**
     * Adds the node.
     * 
     * @param node the node
     * @param id the id
     * @param name the name
     * @param parent the parent
     * @return the element
     */
    private static Element addNode(String node, String id, String name, Element parent)
    {
        Element ret=addElement(node,null,parent);
        if(id!=null)ret.setAttribute("id",id);
        if(name!=null)ret.setAttribute("name",name);
        return ret;
    }

    /**
     * Adds the element.
     * 
     * @param name the name
     * @param value the value
     * @param parent the parent
     * @return the element
     */
    private static Element addElement(String name, String value, Element parent)
    {
        Document doc = parent.getOwnerDocument();
        Element ele = doc.createElement(name);
        if (value != null) ele.appendChild(doc.createTextNode(value));
        parent.appendChild(ele);
        return ele;
    }  
    
    /**
     * Gets the error.
     * 
     * @param id the id
     * @return the error
     */
    private Document getError(int id)
    {
        Document dom = null;
        try
        {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            Element err = dom.createElement("err");
            res.appendChild(err);
            addElement("id", "" + id, err);
            if (id == 0)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_loginfail") + "...", err);
            } else if (id == 1)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_nouser") + "...", err);
            } else if (id == 2)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noservice") + "...", err);
            } else if (id == 3)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_serviceprocessfail") + "...", err);
            } else if (id == 4)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_parametersprocessfail") + "...", err);
            } else if (id == 5)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopicmap") + "...", err);
            } else if (id == 6)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopic") + "...", err);
            } else if (id == 7)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_usernopermiss") + "...", err);
            } else if (id == 8)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicAlreadyexist") + "...", err);
            } else if (id == 9)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_byImplement") + "...", err);
            } else if (id == 10)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicMapAlreadyExist") + "...", err);
            } else if (id == 11)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_FileNotFound") + "...", err);
            } else if (id == 12)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noversions") + "...", err);
            } else if (id == 13)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_xmlinconsistencyversion") + "...", err);
            } else if (id == 14)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noResourcesinMemory") + "...", err);
            } else if (id == 15)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noTemplatesinMemory") + "...", err);
            } else if (id == 16)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_TemplatenotRemovedfromFileSystem") + "...", err);
            } else if (id == 17)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_adminUsernotCreated") + "...", err);
            } else
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_errornotfound") + "...", err);
            }
        } catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_documentError") + "...", e);
        }
        
        return dom;
    }
    
    /**
     * Checks for subdirectories.
     * 
     * @param fdir the fdir
     * @return true, if successful
     * @return
     */    
    public static boolean hasSubdirectories(File fdir)
    {
        File[] dirs=fdir.listFiles();
        for(int i=0;i<dirs.length;i++)
        {
            File file=dirs[i];
            if(file.isDirectory())
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the directories.
     * 
     * @param edir the edir
     * @param fdir the fdir
     * @return the directories
     */    
    public static void getDirectories(Element edir,File fdir,User user)
    {
        File[] dirs=fdir.listFiles();
        Arrays.sort(dirs, new FileComprator());
        for(int i=0;i<dirs.length;i++)
        {
            File file=dirs[i];
            if(file.isDirectory() && showDirectory(user, file))
            {
                Element dir=addNode("dir", "", file.getName(), edir);                
                dir.setAttribute("path",file.getAbsolutePath());
                dir.setAttribute("hasChild",String.valueOf(hasSubdirectories(file)));                
            }
        }
    }

    public static void hasPermissionFile(Element edir,Document src,User user)
    {       
        String path=SWBUtils.getApplicationPath();
        if(src.getElementsByTagName("path").getLength()>0)
        {
            Element epath=(Element)src.getElementsByTagName("path").item(0);
            Text etext=(Text)epath.getFirstChild();
            path=etext.getNodeValue();
        }
        File file=new File(path);
        Element dir=addNode("dir", "", file.getName(), edir);
        dir.setAttribute("path",file.getAbsolutePath());
        dir.setAttribute("permission",String.valueOf(hasPermission(user,file)));
    }
    
    /**
     * Gets the directories.
     * 
     * @param res the res
     * @param src the src
     * @return the directories
     */    
    public static void getDirectories(Element res,Document src,User user)
    {
        String path=SWBUtils.getApplicationPath();
        if(src.getElementsByTagName("path").getLength()>0)
        {
            Element epath=(Element)src.getElementsByTagName("path").item(0);
            Text etext=(Text)epath.getFirstChild();
            path=etext.getNodeValue();            
        }
        File apppath=new File(path);
        if(apppath.isDirectory() && showDirectory(user, apppath))
        {
            Element dir=addNode("dir", "", apppath.getName(), res);
            dir.setAttribute("path",apppath.getAbsolutePath());
            dir.setAttribute("hasChild",String.valueOf(hasSubdirectories(apppath)));                
            getDirectories(dir,apppath,user);
        }
    }
    
    /**
     * Creates the dir.
     * 
     * @param res the res
     * @param src the src
     */    
    public static void createDir(Element res,Document src,User user,String ip)
    {
        if(src.getElementsByTagName("path").getLength()>0)
        {
            Element epath=(Element)src.getElementsByTagName("path").item(0);
            Text etext=(Text)epath.getFirstChild();
            String path=etext.getNodeValue();                         
            File f=new File(path);
            if(!hasPermission(user, f))
            {
                return;
            }
            if(f.mkdirs())
            {
                try
                {
                    if(f.isDirectory())
                    {
                        log("CREATED|DIR:\""+f.getCanonicalPath() +"\"|USER:\""+user.getLogin()+"_"+ user.getUserRepository().getId() +"\"",ip);
                    }
                    else
                    {
                        log("CREATED|FILE:\""+f.getCanonicalPath() +"\"|USER:\""+user.getLogin()+"_"+ user.getUserRepository().getId() +"\"",ip);
                    }
                }
                catch(Exception e)
                {
                    log.error(e);
                }
                addElement("create", "true", res);
                return;
            }
        }
        addElement("create", "false", res);
    }
    
    /**
     * Rename.
     * 
     * @param res the res
     * @param src the src
     */    
    public static void rename(Element res,Document src,User user,String ip)
    {
        if(src.getElementsByTagName("path").getLength()>0 && src.getElementsByTagName("newpath").getLength()>0)
        {
            Element epath=(Element)src.getElementsByTagName("path").item(0);
            Text etext=(Text)epath.getFirstChild();
            String path=etext.getNodeValue();             
            
            Element ename=(Element)src.getElementsByTagName("newpath").item(0);
            etext=(Text)ename.getFirstChild();
            String newname=etext.getNodeValue();
            File newfile=new File(newname);           
            File f=new File(path);
            if(!hasPermission(user, f))
            {
                return;
            }
            if(!isProtected(f))
            {
                if(f.renameTo(newfile))
                {
                    try
                    {
                        if(f.isDirectory())
                        {
                            log("RENAME|DIR:\""+f.getCanonicalPath() +"\"|USER:\""+user.getLogin()+"_"+ user.getUserRepository().getId() +"\"|NEWFILE:\""+ newfile +"\"",ip);
                        }
                        else
                        {
                            log("RENAME|FILE:\""+f.getCanonicalPath() +"\"|USER:\""+user.getLogin()+"_"+ user.getUserRepository().getId() +"\"|NEWFILE:\""+ newfile +"\"",ip);
                        }
                    }
                    catch(Exception e)
                    {
                        log.error(e);
                    }
                    addElement("rename", "true", res);
                    return;
                }
            }
            else
            {
                addElement("err", java.util.ResourceBundle.getBundle("org/semanticwb/portal/admin/resources/SWBAFTP").getString("msg"), res);
            }
        }
        addElement("rename", "false", res);
    }
    
    /**
     * Exists.
     * 
     * @param res the res
     * @param src the src
     */    
    public static void exists(Element res,Document src)
    {
        if(src.getElementsByTagName("path").getLength()>0)
        {
            Element epath=(Element)src.getElementsByTagName("path").item(0);
            Text etext=(Text)epath.getFirstChild();
            String path=etext.getNodeValue();            
            File f=new File(path);            
            if(f.exists())
            {
                addElement("exists", "true", res);
                return;
            }
            else
            {
                addElement("exists", "false", res);
                return;
            }
        }
        addElement("exists", "false", res);
    }
    
    /**
     * Checks if is protected.
     * 
     * @param f the f
     * @return true, if is protected
     * @return
     */    
    public static boolean isProtected(File f)
    {
        try
        {
            String path=f.getCanonicalPath();
            String appPath=new File(SWBUtils.getApplicationPath()).getCanonicalPath();
            path=path.substring(appPath.length()).replace('\\','/');
            for(String pathProhibited : prohibitedPaths)
            {
                if(pathProhibited.endsWith("*"))
                {
                    pathProhibited=pathProhibited.substring(0,pathProhibited.length()-1);
                    if(path.startsWith(pathProhibited))
                    {
                        return true;
                    }
                }
                else
                {
                    if(pathProhibited.equals(path))
                    {
                        return true;
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
    
    /**
     * Delete.
     * 
     * @param res the res
     * @param src the src
     */    
    public static void delete(Element res,Document src,User user,String ip)
    {
        if(src.getElementsByTagName("path").getLength()>0)
        {
            Element epath=(Element)src.getElementsByTagName("path").item(0);
            Text etext=(Text)epath.getFirstChild();
            String path=etext.getNodeValue();                                                
            File f=new File(path);
            if(!hasPermission(user, f))
            {
                return;
            }
            if(!isProtected(f))
            {                
                if(f.delete())
                {
                    try
                    {
                        if(f.isDirectory())
                        {
                            log("DELETED|DIR:\""+f.getCanonicalPath() +"\"|USER:\""+user.getLogin()+"_"+ user.getUserRepository().getId() +"\"",ip);
                        }
                        else
                        {
                            log("DELETED|FILE:\""+f.getCanonicalPath() +"\"|USER:\""+user.getLogin()+"_"+ user.getUserRepository().getId() +"\"",ip);
                        }
                    }
                    catch(Exception e)
                    {
                        log.error(e);
                    }
                    addElement("delete", "true", res);
                    return;
                }
            }
            else
            {
                addElement("err", java.util.ResourceBundle.getBundle("org/semanticwb/portal/admin/resources/SWBAFTP").getString("msg"), res);
            }
        }
        addElement("delete", "false", res);
    }
    public static boolean hasPermission(User user, File directory)
    {
        UserGroup su=UserGroup.ClassMgr.getUserGroup("su", SWBContext.getAdminRepository());
        boolean permision=false;
        if(user.getAdminFilter()!=null || (su!=null && user.hasUserGroup(su)))
        {
            permision=true;
            if(user.getAdminFilter()!=null)
            {
                permision=false;
                GenericIterator<AdminFilter> filters=user.listAdminFilters();
                while(filters.hasNext())
                {
                    Document doc=filters.next().getDom();
                    if(doc.getElementsByTagName("dirs").getLength()>0)
                    {
                        Element dirs=(Element)doc.getElementsByTagName("dirs").item(0);
                        NodeList ldirs=dirs.getElementsByTagName("dir");
                        for(int i=0;i<ldirs.getLength();i++)
                        {
                            try
                            {
                                if(ldirs.item(i) instanceof Element)
                                {
                                    Element edir=(Element)ldirs.item(i);
                                    String pathPermission=edir.getAttribute("path");
                                    pathPermission=SWBUtils.getApplicationPath()+pathPermission;
                                    File filePermission=new File(pathPermission);
                                    pathPermission=filePermission.getCanonicalPath();
                                    String testPath=directory.getCanonicalPath();
                                    if((pathPermission.equals(testPath) || testPath.startsWith(pathPermission)))
                                    {
                                        permision=true;
                                        break;
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                                log.error(e);
                            }                            
                        }
                    }
                }
            }
        }
        return permision;
    }

    public static boolean showDirectory(User user, File directory)
    {
        UserGroup su=UserGroup.ClassMgr.getUserGroup("su", SWBContext.getAdminRepository());
        boolean permision=false;
        if(user.getAdminFilter()!=null || (su!=null && user.hasUserGroup(su)))
        {
            permision=true;
            if(user.getAdminFilter()!=null)
            {
                permision=false;
                GenericIterator<AdminFilter> filters=user.listAdminFilters();
                while(filters.hasNext())
                {
                    Document doc=filters.next().getDom();
                    if(doc.getElementsByTagName("dirs").getLength()>0)
                    {
                        Element dirs=(Element)doc.getElementsByTagName("dirs").item(0);
                        NodeList ldirs=dirs.getElementsByTagName("dir");
                        for(int i=0;i<ldirs.getLength();i++)
                        {
                            try
                            {
                                if(ldirs.item(i) instanceof Element)
                                {
                                    Element edir=(Element)ldirs.item(i);
                                    String pathPermission=edir.getAttribute("path");
                                    pathPermission=SWBUtils.getApplicationPath()+pathPermission;
                                    File filePermission=new File(pathPermission);
                                    pathPermission=filePermission.getCanonicalPath().replace('\\', '/')+"/";
                                    String testPath=directory.getCanonicalPath().replace('\\', '/')+"/";
                                    if(filePermission.isDirectory() && (pathPermission.startsWith(testPath) || testPath.startsWith(pathPermission)))
                                    {
                                        permision=true;
                                        break;
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                                log.error(e);
                            }                            
                        }
                    }
                }
            }
        }
        return permision;
    }
    /**
     * Gets the files.
     * 
     * @param res the res
     * @param src the src
     * @return the files
     */    
    public static void getFiles(Element res,Document src,User user)
    {     
        String path=SWBUtils.getApplicationPath();
        if(src.getElementsByTagName("path").getLength()>0)
        {
            Element epath=(Element)src.getElementsByTagName("path").item(0);
            Text etext=(Text)epath.getFirstChild();
            path=etext.getNodeValue();
        }
        File apppath=new File(path);

        if(hasPermission(user, apppath))
        {

            if(apppath.isDirectory())
            {
                java.text.SimpleDateFormat simpleDateFormat=new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
                File[] files=apppath.listFiles();
                Vector<File> vfiles=new Vector();
                for(File temp : files)
                {
                    vfiles.add(temp);
                }
                Collections.sort(vfiles);
                Iterator itfiles=vfiles.iterator();
                while(itfiles.hasNext())
                {
                    File file=(File)itfiles.next();
                    if(file.isFile())
                    {
                        Element efile=addNode("file", "", file.getName(), res);
                        efile.setAttribute("path",file.getAbsolutePath());
                        efile.setAttribute("size",String.valueOf(file.length()));
                        java.sql.Date date=new java.sql.Date(file.lastModified());
                        efile.setAttribute("lastupdate",simpleDateFormat.format(date));
                    }
                }
            }
        }
    }
    
    /**
     * Gets the document.
     * 
     * @param user the user
     * @param src the src
     * @param cmd the cmd
     * @return the document
     * @return
     */    
    public Document getDocument(User user, Document src, String cmd,String ip)
    {
        Document dom = null;
        try
        {
                       
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);           
            
            if(cmd.equals("getDirectories"))
            {
                getDirectories(res,src,user);
            }
            else if(cmd.equals("hasPermissionFile"))
            {
                hasPermissionFile(res,src,user);
            }
            else if(cmd.equals("getFiles"))
            {
                getFiles(res,src,user);
            }
            else if(cmd.equals("delete"))
            {
                delete(res,src,user,ip);
            }
            else if(cmd.equals("createDir"))
            {
                createDir(res,src,user,ip);
            }
            else if(cmd.equals("rename"))
            {
                rename(res,src,user,ip);
            }
            else if(cmd.equals("exists"))
            {
                exists(res,src);
            }           
        } catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
    }

    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        User user=paramRequest.getUser();
        PrintWriter out=response.getWriter();        
        String act="view";
        if(request.getParameter("act")!=null)
        {
            act=request.getParameter("act");
        }
        if(act.equals("view"))
        {
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<div class=\"applet\">");
            out.println("<applet id=\"ftp\" name=\"ftp\" code=\"applets.ftp.ftp.class\" codebase=\""+SWBPlatform.getContextPath()+"/\" ARCHIVE=\"swbadmin/lib/SWBAplFtp.jar, swbadmin/lib/SWBAplCommons.jar\" width=\"100%\" height=\"450\">");
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode("gateway");
            url.setCallMethod(url.Call_DIRECT);            
            out.println("<param name=\"jsess\" value=\""+request.getSession().getId()+"\">");
            out.println("<param name =\"locale\" value=\""+user.getLanguage()+"\">");
            out.println("<param name =\"cgipath\" value=\""+url+"\">");
            url.setMode("upload");
            url.setCallMethod(url.Call_DIRECT);            
            out.println("<param name =\"uploadpath\" value=\""+url+"\">");
            url.setMode("download");
            url.setCallMethod(url.Call_DIRECT);  
            out.println("<param name =\"downloadpath\" value=\""+url+"\">");
            //out.println("<PARAM NAME =\"jsess\" VALUE=\""+request.getSession().getId()+"\">");            
            out.println("</applet>");
            out.println("</div>");
            out.println("</fieldset>");
            out.println("</div>");
        }
    }
    
}

class FileComprator implements Comparator
{

    public int compare(Object o1, Object o2)
    {
        if(o1 instanceof File && o2 instanceof File)
        {
            return ((File)o1).getName().compareToIgnoreCase(((File)o2).getName());
        }
        return 0;
    }

}
