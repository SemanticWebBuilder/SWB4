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
 * SWBAFTP.java
 *
 * Created on 11 de noviembre de 2004, 10:51 AM
 */

package org.semanticwb.portal.admin.resources;


import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;
import org.semanticwb.portal.api.*;
import org.w3c.dom.*;

import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericResource;

/** Recurso que despliega los archivos que se encuentran en el servidor, permitiendo
 * agregar archivos o carpetas, renombrarlos, eliminarlos y descargarlos.
 *
 * Resource that unfolds the archives that are in the servant, allowing to add
 * archives or folders, to rename, to eliminate and to download.
 * @author Victor Lorenzana
 */
public class SWBAFTP extends GenericResource{
    private Logger log = SWBUtils.getLogger(SWBAFTP.class);
    /** Creates a new instance of WBAFTP */
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
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doDownload(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path=request.getHeader("PATHFILEWB");        
        if(path!=null)
        {
            try
            {
                File f=new File(path);      
                if(f.exists())
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
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doUpload(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path=request.getHeader("PATHFILEWB");        
        
        if(path!=null)
        {
            try
            {
                File f=new File(path);                                  
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
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
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
    private Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {
        return getDocument(user, src, cmd);        
    }
    private Element addNode(String node, String id, String name, Element parent)
    {
        Element ret=addElement(node,null,parent);
        if(id!=null)ret.setAttribute("id",id);
        if(name!=null)ret.setAttribute("name",name);
        return ret;
    }

    private Element addElement(String name, String value, Element parent)
    {
        Document doc = parent.getOwnerDocument();
        Element ele = doc.createElement(name);
        if (value != null) ele.appendChild(doc.createTextNode(value));
        parent.appendChild(ele);
        return ele;
    }  
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
     * @param fdir
     * @return
     */    
    public boolean hasSubdirectories(File fdir)
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
     * @param edir
     * @param fdir
     */    
    public void getDirectories(Element edir,File fdir)
    {
        File[] dirs=fdir.listFiles();
        for(int i=0;i<dirs.length;i++)
        {
            File file=dirs[i];
            if(file.isDirectory())
            {
                Element dir=addNode("dir", "", file.getName(), edir);                
                dir.setAttribute("path",file.getAbsolutePath());
                dir.setAttribute("hasChild",String.valueOf(hasSubdirectories(file)));                
            }
        }
    }
    /**
     * @param res
     * @param src
     */    
    public void getDirectories(Element res,Document src)
    {
        String path=SWBUtils.getApplicationPath();
        if(src.getElementsByTagName("path").getLength()>0)
        {
            Element epath=(Element)src.getElementsByTagName("path").item(0);
            Text etext=(Text)epath.getFirstChild();
            path=etext.getNodeValue();            
        }
        File apppath=new File(path);
        if(apppath.isDirectory())
        {
            Element dir=addNode("dir", "", apppath.getName(), res);
            dir.setAttribute("path",apppath.getAbsolutePath());
            dir.setAttribute("hasChild",String.valueOf(hasSubdirectories(apppath)));                
            getDirectories(dir,apppath); 
        }
    }
    /**
     * @param res
     * @param src
     */    
    public void createDir(Element res,Document src)
    {
        if(src.getElementsByTagName("path").getLength()>0)
        {
            Element epath=(Element)src.getElementsByTagName("path").item(0);
            Text etext=(Text)epath.getFirstChild();
            String path=etext.getNodeValue();                         
            File f=new File(path);                        
            if(f.mkdirs())
            {
                addElement("create", "true", res);
                return;
            }
        }
        addElement("create", "false", res);
    }
    /**
     * @param res
     * @param src
     */    
    public void rename(Element res,Document src)
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
            if(!isProtected(f))
            {
                if(f.renameTo(newfile))
                {
                    addElement("rename", "true", res);
                    return;
                }
            }
            else
            {
                addElement("err", java.util.ResourceBundle.getBundle("org/semanticwb/org/admin/resources/SWBAFTP").getString("msg"), res);
            }
        }
        addElement("rename", "false", res);
    }
    /**
     * @param res
     * @param src
     */    
    public void exists(Element res,Document src)
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
     * @param f
     * @return
     */    
    public boolean isProtected(File f)
    {
        boolean isProtected=false;
        try
        {         
            
            DataInputStream in=new DataInputStream(this.getClass().getResourceAsStream("/ftp.txt"));
            String linea=in.readLine();
            while(linea!=null)
            {
                String path=SWBUtils.getApplicationPath()+"/"+linea;
                File fp=new File(path);
                if(fp.equals(f))
                {
                    isProtected=true;
                }
                linea=in.readLine();
            }
            in.close();
        }
        catch(Exception e)
        {
           e.printStackTrace(System.out);
           log.error(e);
        }
        return isProtected;
    }
    
    /**
     * @param res
     * @param src
     */    
    public void delete(Element res,Document src)
    {
        if(src.getElementsByTagName("path").getLength()>0)
        {
            Element epath=(Element)src.getElementsByTagName("path").item(0);
            Text etext=(Text)epath.getFirstChild();
            String path=etext.getNodeValue();                                                
            File f=new File(path);                        
            if(!isProtected(f))
            {                
                if(f.delete())
                {
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
    /**
     * @param res
     * @param src
     */    
    public void getFiles(Element res,Document src)
    {
        String path=SWBUtils.getApplicationPath();
        if(src.getElementsByTagName("path").getLength()>0)
        {
            Element epath=(Element)src.getElementsByTagName("path").item(0);
            Text etext=(Text)epath.getFirstChild();
            path=etext.getNodeValue();            
        }
        java.text.SimpleDateFormat df=new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        File apppath=new File(path);
        if(apppath.isDirectory())
        {
            File[] files=apppath.listFiles();
            Vector vfiles=new Vector();
            for(int i=0;i<files.length;i++)
            {
                vfiles.add(files[i]);
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
                    efile.setAttribute("lastupdate",df.format(date));
                }
            }
        }
    }
    /**
     * @param user
     * @param src
     * @param cmd
     * @return
     */    
    public Document getDocument(User user, Document src, String cmd)
    {
        Document dom = null;
        try
        {
                       
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);           
            
            if(cmd.equals("getDirectories"))
            {
                getDirectories(res,src);
            }
            else if(cmd.equals("getFiles"))
            {
                getFiles(res,src);
            }
            else if(cmd.equals("delete"))
            {
                delete(res,src);
            }
            else if(cmd.equals("createDir"))
            {
                createDir(res,src);
            }
            else if(cmd.equals("rename"))
            {
                rename(res,src);
            }
            else if(cmd.equals("exists"))
            {
                exists(res,src);
            }
            /*else if(cmd.equals("getcatUsers"))
            {
                getCatalogUsers(res,tm);
            }   
            else if(cmd.equals("getWorkflow"))
            {                
                getWorkflow(res,tm,src);
            }
            else if(cmd.equals("update"))
            {
                update(res,src,user,tm);
            }  */ 
        } catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
    }

    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
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
            out.println("<APPLET id=\"ftp\" name=\"ftp\" code=\"applets.ftp.ftp.class\" codebase=\""+SWBPlatform.getContextPath()+"\" ARCHIVE=\"swbadmin/lib/ftp.jar, swbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"100%\">");
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode("gateway");
            url.setCallMethod(url.Call_DIRECT);            
            out.println("<PARAM NAME =\"locale\" VALUE=\""+user.getLanguage()+"\">");
            out.println("<PARAM NAME =\"cgipath\" VALUE=\""+url+"\">");
            url.setMode("upload");
            url.setCallMethod(url.Call_DIRECT);            
            out.println("<PARAM NAME =\"uploadpath\" VALUE=\""+url+"\">"); 
            url.setMode("download");
            url.setCallMethod(url.Call_DIRECT);  
            out.println("<PARAM NAME =\"downloadpath\" VALUE=\""+url+"\">");
            //out.println("<PARAM NAME =\"jsess\" VALUE=\""+request.getSession().getId()+"\">");            
            out.println("</APPLET>");
            out.println("</div>");
            out.println("</fieldset>");
            out.println("</div>");
        }
    }
    
}
