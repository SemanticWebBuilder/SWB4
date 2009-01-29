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
 * WBTree.java
 *
 * Created on 30 de junio de 2004, 06:55 PM
 */

package org.semanticwb.portal.admin.resources;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import org.w3c.dom.*;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.model.PortletSubType;
import org.semanticwb.model.PortletType;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/** Recurso de administraci�n que permite la edici�n de contenidos y plantillas en
 * la administraci�n de WebBuilder.
 *
 * Portlet of administration that allows to the edition of contents and templates
 * in the administration of WebBuilder.
 * @author Javier Solis Gonzalez
 */
public class SWBAEditor extends GenericResource
{
    private static Logger log=SWBUtils.getLogger(SWBAEditor.class);

    class ResCatComp implements Comparator
    {
        
        public int compare(Object o1, Object o2)
        {
            if(o1 instanceof PortletType && o2 instanceof PortletType)
            {
                PortletType r1=(PortletType)o1;
                PortletType r2=(PortletType)o2;
                return r1.getTitle().toLowerCase().compareTo(r2.getTitle().toLowerCase());
            }
            return 0;
        }
        
    }
    class SubResCatComp implements Comparator
    {
        
        public int compare(Object o1, Object o2)
        {
            if(o1 instanceof PortletSubType && o2 instanceof PortletSubType)
            {
                PortletSubType r1=(PortletSubType)o1;
                PortletSubType r2=(PortletSubType)o2;
                return r1.getTitle().toLowerCase().compareTo(r2.getTitle().toLowerCase());
            }
            return 0;
        }
        
    }
    /** Creates a new instance of WBTree */
    public SWBAEditor()
    {
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        System.out.println("Mode:"+paramsRequest.getMode());
        if(paramsRequest.getMode().equals("gateway"))
        {
            doGateway(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("upload"))
        {
            doUpload(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("download"))
        {
            doDownload(request,response,paramsRequest);
        }else super.processRequest(request,response,paramsRequest);
    }
    
    /**
     * @param it
     * @return
     */    
    public Iterator sortIterator(Iterator it)
    {
        return SWBComparator.sortSermanticObjects(it);
    }    
    
    /**
     * @param res
     * @param tm
     */    
    public void getResourceTypeCat(Element res,String tm)
    {
        
        WebSite map=SWBContext.getWebSite(tm);
        WebSite tmInit=map;
        //TODO:listar globales
        Iterator elements=sortIterator(map.listPortletTypes());
        while(elements.hasNext())
        {            
            PortletType obj=(PortletType)elements.next();
            //System.out.println("obj:"+obj.getDisplayName());
            if(obj.getPortletMode()==2 || obj.getPortletMode()==3)
            {
                Element erole=addNode("resourceType",""+obj.getId(), obj.getTitle(), res);
                erole.setAttribute("topicmap",map.getId());
                erole.setAttribute("topicmapname",map.getTitle());
                //TODO:listar globales
                Iterator itsub=sortIterator(obj.listSubTypes());

                while(itsub.hasNext())
                {
                    PortletSubType sub=(PortletSubType)itsub.next();
                    Element esubType=addNode("subResourceType",""+sub.getId(), sub.getTitle(), erole);            
                    esubType.setAttribute("topicmap",sub.getWebSite().getId());
                }
            }
        }   
    }
    
    private Document getService(SWBParamRequest paramsRequest, String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {
        //System.out.println("Service mi entrada:"+cmd);
        
        String type=request.getHeader("TYPE");
        String tm=request.getHeader("TM");
        String id=request.getHeader("ID");
        String sver=request.getHeader("VER");        
        int ver=0;        
        if(sver==null)sver=request.getParameter("version");  
        
        String webpath=null;
        String workpath=null;
        String filename=null;
        if("Template".equalsIgnoreCase(type))
        {
            Template template=SWBPortal.getTemplateMgr().getTemplateImp(SWBContext.getWebSite(tm).getTemplate(id));
            webpath=SWBPlatform.getWebWorkPath()+template.getWorkPath();
            workpath=template.getWorkPath();
            if(sver==null)ver=template.getLastVersion().getVersionNumber();
            else ver=Integer.parseInt(sver);     
            filename=template.getFileName(ver);
        }else if("LocalContent".equalsIgnoreCase(type))
        {
            //TODO
            /*
            Portlet res=ResourceMgr.getInstance().getResource(tm,id).getResourceBase();
            webpath=WBUtils.getInstance().getWebWorkPath()+res.getResourceWorkPath();
            workpath=WBUtils.getInstance().getWorkPath()+res.getResourceWorkPath();
            if(sver==null)ver=res.getLastversion();
            else ver=Integer.parseInt(sver);     
            filename=res.getAttribute("url"+ver);
            */
        }

        if (cmd.equals("init"))
        {
            return init(user, src, ver);
        } 
        else if (cmd.equals("getResourceTypeCat"))
        {            
            try
            {
                Document doc=SWBUtils.XML.getNewDocument();
                Element res=doc.createElement("res");
                doc.appendChild(res);
                if(src.getElementsByTagName("tm").getLength()>0)
                {
                    getResourceTypeCat(res,src.getElementsByTagName("tm").item(0).getFirstChild().getNodeValue());
                    return doc;
                } 
            }
            catch(Exception e)
            {
                e.printStackTrace(System.out);
                log.error(e);
            }
            return null;
        }        
        else if (cmd.equals("initFiles"))
        {
            return initFiles(paramsRequest, user, src, workpath, webpath, filename, ver);
        } else
        {
            return getDocument(paramsRequest, user, src, cmd, workpath, webpath, filename, ver);
        }
    }
    
    /**
     * @param user
     * @param src
     * @param ver
     * @return
     */    
    public Document init(User user, Document src, int ver)
    {
        Document dom = null;
        try
        {
            WebSite tma=SWBContext.getAdminWebSite();

            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            //config
            Element config=addNode("config","config","Config",res);
            
            Element icons=addNode("icons","icons","Icons",config);
            Element icon=addNode("icon","titulo","Titulo",icons);
            icon.setAttribute("path","images/ico_titulo.gif");
            icon=addNode("icon","idioma","Idioma",icons);
            icon.setAttribute("path","images/ico_idioma.gif");
            icon.setAttribute("text","ES");
            
            Element events=addNode("events","events","Events",config);
            Element unload=addNode("unload","unload","unload",events);
            unload.setAttribute("action","showurl="+tma.getWebPage("WBAd_infoi_TopicTitles").getUrl()+"?act=treeReload&status=true");
            unload.setAttribute("target","status");
            
            
            //addNames(res,topic);

        } catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
    }
    
    /**
     * @param paramsRequest
     * @param user
     * @param src
     * @param workpath
     * @param webpath
     * @param filename
     * @param ver
     * @return
     */    
    public Document initFiles(SWBParamRequest paramsRequest, User user, Document src, String workpath, String webpath, String filename, int ver)
    {
        Document dom = null;
        try
        {
            WebSite tma=SWBContext.getAdminWebSite();

            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            //config
            Element config=addNode("config","config","Config",res);
            config.setAttribute("docPath",webpath+"/"+ver+"/");
            
            Element icons=addNode("icons","icons","Icons",config);
            Element icon=addNode("icon","folderO","Folder Open",icons);
            icon.setAttribute("path","images/f_abierto.gif");
            icon=addNode("icon","folderC","Folder Closed",icons);
            icon.setAttribute("path","images/f_general.gif");
            icon=addNode("icon","file","File",icons);
            icon.setAttribute("path","images/i_documento.gif");
            
            getFiles(paramsRequest, res, workpath, webpath, filename, ver);
            
        } catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
    }    
    
    /**
     * @param paramsRequest
     * @param res
     * @param workpath
     * @param webpath
     * @param filename
     * @param ver
     * @throws SWBResourceException
     */    
    public void getFiles(SWBParamRequest paramsRequest, Element res, String workpath, String webpath, String filename, int ver) throws SWBResourceException
    {
        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        Element events=null;
        Element event=null;
        
        //tree nodes
        Element root=addNode("node","","Files",res);
        root.setAttribute("reload","getFiles");
        //root.setAttribute("icon_open","folderO");
        //root.setAttribute("icon_close","folderC");
        root.setAttribute("type", "DIR");
      
        menu=addNode("menu","menu","Menu",root);
        
        option=addNode("option","add",paramsRequest.getLocaleString("addFile"),menu);
        option.setAttribute("action","addFile");
        option.setAttribute("shortCut","INSERT");
        //option.setAttribute("target","work");
        addNode("separator","-","-",menu);

        option=addNode("option","refresh",paramsRequest.getLocaleString("refresh"),menu);
        option.setAttribute("shortCut","F5");
        option.setAttribute("action","reload");

        
        File dir=new File(workpath+"/"+ver+"/");
        if(!dir.exists())dir.mkdirs();
        File files[]=dir.listFiles();
        for(int x=0;x<files.length;x++)
        {
            File file=files[x];
            if(file.isDirectory())
            {
                getDir(paramsRequest, root, file, workpath, webpath, ver);
            }
            else if(file.isFile())
            {
                if(!file.getName().equals(filename))
                {
                    getFile(paramsRequest, root, file, workpath, webpath, ver);
                }
            }
        }
    }
        
    /**
     * @param paramsRequest
     * @param root
     * @param file
     * @param workpath
     * @param webpath
     * @param ver
     * @throws SWBResourceException
     */    
    public void getFile(SWBParamRequest paramsRequest, Element root, File file, String workpath, String webpath, int ver) throws SWBResourceException
    {
        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        
        File docDir=new File(workpath+"/"+ver+"/");
        String id=file.getPath().substring(docDir.getPath().length()+1);
        id=id.replace('\\','/');
        String path=webpath+"/"+ver+"/"+id;
        //System.out.println("id:"+id);
        //System.out.println("path:"+path);
        Element ele=addNode("node",id,file.getName(),root);
        ele.setAttribute("path",path);
        //ele.setAttribute("action","showurl="+tma.getTopic("WBAd_sysi_RolesInfo").getUrl()+"?id="+role.getId());
        //ele.setAttribute("target","work");
        ele.setAttribute("reload","getFile."+id);
        //ele.setAttribute("view","showurl="+tma.getTopic("WBAd_infoi_RolesInfo").getUrl()+"?id="+role.getId());
        //ele.setAttribute("vtarget","info");
        ele.setAttribute("type", "FILE");
        ele.setAttribute("size", ""+file.length());
        Date dt=new Date(file.lastModified());
        //ele.setAttribute("date", ""+dt.getDate()+"/"+(dt.getMonth()+1)+"/"+(dt.getYear()+1900));
        ele.setAttribute("date", ""+dt.toLocaleString());
        ele.setAttribute("time", ""+dt.getHours()+":"+dt.getMinutes()+":"+dt.getSeconds());
        //ele.setAttribute("icon","file");
        
        //menu
        menu=addNode("menu","menu","Menu",ele);
        option=addNode("option","add",paramsRequest.getLocaleString("addFile"),menu);
        option.setAttribute("action","addFile");
        option.setAttribute("shortCut","INSERT");
        addNode("separator","-","-",menu);
        option=addNode("option","remove",paramsRequest.getLocaleString("removeFile"),menu);
        option.setAttribute("action","removeFile."+id);
        option.setAttribute("shortCut","DELETE");
        option.setAttribute("confirm",paramsRequest.getLocaleString("confirm"));
        addNode("separator","-","-",menu);
        option=addNode("option","refresh",paramsRequest.getLocaleString("refresh"),menu);
        option.setAttribute("shortCut","F5");
        option.setAttribute("action","reload");
 
    }
    
    /**
     * @param paramsRequest
     * @param root
     * @param dir
     * @param workpath
     * @param webpath
     * @param ver
     * @throws SWBResourceException
     */    
    public void getDir(SWBParamRequest paramsRequest, Element root, File dir, String workpath, String webpath, int ver) throws SWBResourceException
    {
        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        
        
        File docDir=new File(workpath+"/"+ver+"/");
        //System.out.println("dir:"+dir);
        //System.out.println("docDir:"+docDir);
        String id=dir.getPath().substring(docDir.getPath().length()+1);
        id=id.replace('\\','/');
        
        //System.out.println("id:"+id);
        Element ele=addNode("node",id,dir.getName(),root);
        //ele.setAttribute("action","showurl="+tma.getTopic("WBAd_sysi_RolesInfo").getUrl()+"?id="+role.getId());
        //ele.setAttribute("target","work");
        ele.setAttribute("reload","getDir."+id);
        //ele.setAttribute("view","showurl="+tma.getTopic("WBAd_infoi_RolesInfo").getUrl()+"?id="+role.getId());
        //ele.setAttribute("vtarget","info");
        ele.setAttribute("type", "DIR");
        //root.setAttribute("icon_open","folderO");
        //root.setAttribute("icon_close","folderC");

        //menu
        menu=addNode("menu","menu","Menu",ele);
        option=addNode("option","add",paramsRequest.getLocaleString("addFile"),menu);
        option.setAttribute("action","addFile");
        option.setAttribute("shortCut","INSERT");
        addNode("separator","-","-",menu);
        option=addNode("option","refresh",paramsRequest.getLocaleString("refresh"),menu);
        option.setAttribute("shortCut","F5");
        option.setAttribute("action","reload");
        
 
        File files[]=dir.listFiles();
        for(int x=0;x<files.length;x++)
        {
            File file=files[x];
            if(file.isDirectory())getDir(paramsRequest, ele, file, workpath, webpath,ver);
            else if(file.isFile())getFile(paramsRequest, ele, file, workpath, webpath,ver);
        }        
    }
    
    
    /**
     * @param paramsRequest
     * @param user
     * @param src
     * @param act
     * @param workpath
     * @param webpath
     * @param filename
     * @param ver
     * @return
     */    
    public Document getDocument(SWBParamRequest paramsRequest, User user, Document src, String act, String workpath, String webpath, String filename, int ver)
    {
        Document dom = null;
        try
        {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            
            String cmd=null;
            String id=null;
            int ind=act.indexOf('.');
            if(ind>0)
            {
                cmd=act.substring(0,ind);
                id=act.substring(ind+1);
            }else cmd=act;
            
            if(cmd.equals("getFiles"))
            {
                getFiles(paramsRequest, res, workpath, webpath,filename, ver);
            }else if(cmd.equals("getFile"))
            {
                File file=new File(workpath+"/"+ver+"/"+id);
                getFile(paramsRequest, res,file, workpath, webpath,ver);
            }else if(cmd.equals("getDir"))
            {
                File file=new File(workpath+"/"+ver+"/"+id);
                getDir(paramsRequest, res, file, workpath, webpath,ver);
            }else if(cmd.equals("removeFile"))
            {
                File file=new File(workpath+"/"+ver+"/"+id);
                //System.out.println(file.exists()+" ->"+file);
                if(file.delete())
                {
                    
                }else
                {
                    return getError(3);
                }
                
            }else
            {
                return getError(2);
            }            
        } catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
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
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doUpload(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        ServletInputStream in = request.getInputStream();
        //StringBuffer str=new StringBuffer();
        User user=paramsRequest.getUser();
        try
        {
            String tm=request.getHeader("TM");
            String id=request.getHeader("ID");
            String tp=request.getHeader("TP");
            int ver=Integer.parseInt(request.getHeader("VER"));
            String type=request.getHeader("TYPE");
            
            String name=request.getHeader("PATHFILEWB");
            String doc=request.getHeader("DOCUMENT");
            String work=null;
            String webWork=null;
            FileOutputStream fout=null;

            System.out.println("TM:"+tm);
            System.out.println("ID:"+id);
            System.out.println("TP:"+tp);
            System.out.println("VER:"+ver);
            System.out.println("TYPE:"+type);
            System.out.println("PATHFILEWB:"+name);
            System.out.println("DOCUMENT:"+doc);
            
            if(type.equalsIgnoreCase("Template"))
            {
                Template template=SWBPortal.getTemplateMgr().getTemplateImp(SWBContext.getWebSite(tm).getTemplate(id));
                work=template.getWorkPath()+"/"+ver+"/";
                
                if(request.getHeader("ATTACHWB")!=null)name="images/"+name;
                

                //System.out.println("work:"+work+" name:"+name);

                if(!("FINDATTACHES".equals(doc)))
                {
                    File fpath=new File(work);
                    fpath.mkdirs();
                    File file=new File(work+name);
                    System.out.println("file:"+file);
                    fout=new FileOutputStream(file);
                }
                
                String ret=writeFile(in, fout);
                System.out.println("ret:"+ret);
                if(doc!=null)
                {
                    if(doc.equals("RELOAD"))
                    {
                        //TODO:
                        //template.getRecTemplate().update(paramsRequest.getUser().getId(), "Template updated...");
                        template.reload();
                        out.println("ok");
                    }else if(doc.equals("REPLACE"))
                    {
                        //new com.infotec.wb.services.TemplateSrv().replaceVersion(template.getTopicMapId(), template.getId(), ver,name,user.getId());
                        //TODO:
                        System.out.print("TODO:SWBAeditor - REPLACE");
                        out.println("ok");
                    }else if(doc.equals("FINDATTACHES"))
                    {
                        //out.print(SWBUtils.TEXT.FindAttaches(ret));
                        //out.print("|");
                        //out.print(SWBUtils.TEXT.parseHTML(ret,"images/"));
                        //TODO:
                        System.out.print("TODO:SWBAeditor - FINDATTACHES");
                    }
                }                
                
            }else if(type.equalsIgnoreCase("LocalContent"))
            {
                //TODO:
/*
                Portlet res=ResourceMgr.getInstance().getResource(tm, id).getResourceBase();
                work=WBUtils.getInstance().getWorkPath()+res.getResourceWorkPath()+"/"+ver+"/";
                webWork=WBUtils.getInstance().getWebWorkPath()+res.getResourceWorkPath()+"/"+ver+"/";
                
                //System.out.println("work:"+work+" name:"+name);

                if(!("FINDATTACHES".equals(doc)))
                {
                    File fpath=new File(work);
                    fpath.mkdirs();
                    File file=new File(work+name);
                    fout=new FileOutputStream(file);
                }
                
                if(doc!=null)
                {
                    String ret=writeFile(in, null);
                    
                    if(doc.equals("RELOAD"))
                    {
                        writeParseFile(fout,webWork,ret);
                        res.getRecResource().update();
                        DBAdmLog.getInstance().saveContentLog(paramsRequest.getUser().getId(),tm,tp,"update",id,"Content updated with id:"+ id + " "+", in topic:"+ tp);
                        out.println("ok");
                    }else if(doc.equals("REPLACE"))
                    {
                        writeParseFile(fout,webWork,ret);
                        res.setAttribute("url"+ver,name);
                        res.updateAttributesToDB();
                        DBAdmLog.getInstance().saveContentLog(user.getId(), tm, tp, "update", res.getId(), "Content created width id:"+res.getId()+",and title:"+res.getTitle());
                        out.println("ok");
                    }else if(doc.equals("FINDATTACHES"))
                    {
                        out.print(WBUtils.getInstance().FindAttaches(ret));
                        out.print("|");
                        out.print(WBUtils.getInstance().parseHTML(ret,""));
                    }
                }else
                {
                    writeFile(in, fout);
                }
 */
            }

        }catch(Exception e){log.error(e);}
     }

    //TODO:
//    /**
//     * @param fout
//     * @param work
//     * @param data
//     */
//    public void writeParseFile(FileOutputStream fout, String work, String data)
//    {
//        try
//        {
//            data=SWBUtils.TEXT.parseHTML(data,work);
//            fout.write(data.getBytes());
//            fout.flush();
//            fout.close();
//        }catch(Exception e){log.error(e);}
//    }
    
    /**
     * @param in
     * @param fout
     * @throws IOException
     * @return
     */    
    public String writeFile(InputStream in, FileOutputStream fout) throws IOException
    {
        StringBuffer str=new StringBuffer();
        //System.out.println("file:"+file);
        try
        {
            byte[] bfile = new byte[8192];
            int x;
            while ((x = in.read(bfile, 0, 8192)) > -1)
            {
                if(fout!=null)fout.write(bfile,0, x);
                else str.append(new String(bfile,0,x));
            }
            in.close();
        } catch (Exception e)
        {
            log.error(e);
            if(fout!=null)fout.close();
            //out.println("Error sending file...");
            return str.toString();
        }            
        if(fout!=null)fout.flush();
        if(fout!=null)fout.close();      
        return str.toString();
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doDownload(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        
    }
    
    
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        //System.out.println("Hola:"+request);
        //System.out.println("URI:"+request.getServletPath());
        PrintWriter out=response.getWriter();
        ServletInputStream in = request.getInputStream();
        Document dom = SWBUtils.XML.xmlToDom(in);
        if (dom==null || !dom.getFirstChild().getNodeName().equals("req"))
        {
            out.print(SWBUtils.XML.domToXml(getError(3)));
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
        String ret;        
        WebSite tm=SWBContext.getWebSite(request.getHeader("TM"));
        
        if(tm!=null)
        {
            
            //WebPage tp=tm.getTopic(request.getHeader("TP"));
            Document res = getService(paramsRequest, cmd, dom, paramsRequest.getUser(), request, response);
            if (res == null)
            {
                ret = SWBUtils.XML.domToXml(getError(3));
            } else
                ret = SWBUtils.XML.domToXml(res, true);
            out.print(new String(ret.getBytes()));
        }
        
    }
    
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("HTML Editor...");
        getTemplateApplet(out, "sep", "1", 1, paramsRequest.getUser());
/*        
        //String act=request.getParameter("act");
        String sid=request.getParameter("id");
        String stm=request.getParameter("tm");
        String sver=request.getParameter("version");
        String nver=request.getParameter("newvers");
        User user=paramsRequest.getUser();
        
        if(nver!=null)
        {
            out.println("<APPLET id=\"apptpleditor\" name=\"apptpleditor\" code=\"applets.htmleditor.TemplateEditor\" codebase=\""+WBUtils.getInstance().getWebPath()+"\" ARCHIVE=\"swbadmin/lib/htmleditor.jar, swbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"100%\">");
            WBResourceURL url=paramsRequest.getRenderUrl();
            url.setMode("upload");
            url.setCallMethod(url.Call_DIRECT);
            out.println("<PARAM NAME =\"upload\" VALUE=\""+url+"\">");
            url.setMode("download");
            out.println("<PARAM NAME =\"download\" VALUE=\""+url+"\">");
            url.setMode("gateway");
            out.println("<PARAM NAME =\"gateway\" VALUE=\""+url+"\">");
            out.println("<PARAM NAME =\"startview\" VALUE=\"text\">");
            out.println("<PARAM NAME =\"tm\" VALUE=\""+stm+"\">");
            out.println("<PARAM NAME =\"id\" VALUE=\""+sid+"\">");
            if(sid!=null)//temaplate)
            {
                int id=Integer.parseInt(sid);
                Template tpl=TemplateMgr.getInstance().getTemplate(stm,id);
                if(sver==null)sver=""+tpl.getLastversion();
                
                int ver=Integer.parseInt(sver);
                if(nver.equals("true"))
                {
                    ver=new com.infotec.wb.services.TemplateSrv().newVersion(stm, tpl.getId(), ver,user.getId());
                }
                
                out.println("<PARAM NAME =\"document\" VALUE=\""+tpl.getWebPath()+"/"+ver+"/"+tpl.getFileName(ver)+"\">");
                out.println("<PARAM NAME =\"filename\" VALUE=\""+tpl.getFileName(ver)+"\">");
                out.println("<PARAM NAME =\"ver\" VALUE=\""+ver+"\">");
            }
            out.println("</APPLET>");
        }else
        {
            WBResourceURL url=paramsRequest.getRenderUrl();
            url.setParameter("id",request.getParameter("id"));
            url.setParameter("tm",request.getParameter("tm"));
            url.setParameter("version",request.getParameter("version"));
            //url.setParameter(""request.getParameter("newvers");     
            String str="<script>if(confirm('Quieres crear una nueva versi�n del template?'))window.location='";
            url.setParameter("newvers","true");
            str+=url;
            str+="';else window.location='";
            url.setParameter("newvers","false");
            str+=url;
            str+="';</script>";
            out.println(str);
        }
 */
    }
    
    /**
     * @param out
     * @param topicmapid
     * @param templateid
     * @param version
     * @param user
     */    
    public static void getTemplateApplet(PrintWriter out, String topicmapid, String templateid, int version, User user)
    {
        out.println("<APPLET id=\"apptpleditor\" name=\"apptpleditor\" code=\"applets.htmleditor.TemplateEditor\" codebase=\""+SWBPlatform.getContextPath()+"\" ARCHIVE=\"swbadmin/lib/htmleditor.jar, swbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"100%\">");
        String url=SWBPortal.getDistributorPath()+"/SWBAdmin/WBAd_utl_HTMLEditor/_rid/swb_Portlet:1/_mto/3/_mod/";

        out.println("<PARAM NAME =\"upload\" VALUE=\""+url+"upload"+"\">");
        out.println("<PARAM NAME =\"download\" VALUE=\""+url+"download"+"\">");
        out.println("<PARAM NAME =\"gateway\" VALUE=\""+url+"gateway"+"\">");
        out.println("<PARAM NAME =\"startview\" VALUE=\"text\">");
        out.println("<PARAM NAME =\"tm\" VALUE=\""+topicmapid+"\">");
        out.println("<PARAM NAME =\"id\" VALUE=\""+templateid+"\">");

        Template tpl=SWBContext.getWebSite(topicmapid).getTemplate(templateid);
        out.println("<PARAM NAME =\"document\" VALUE=\""+SWBPlatform.getWebWorkPath()+tpl.getWorkPath()+"/"+version+"/"+URLEncoder.encode(tpl.getFileName(version))+"\">");
        out.println("<PARAM NAME =\"filename\" VALUE=\""+tpl.getFileName(version)+"\">");
        out.println("<PARAM NAME =\"ver\" VALUE=\""+version+"\">");
        out.println("<PARAM NAME =\"locale\" VALUE=\""+user.getLanguage()+"\">");
        out.println("<PARAM NAME =\"type\" VALUE=\"Template\">");
        out.println("</APPLET>");        
    }
    
    /**
     * @param out
     * @param base
     * @param version
     * @param user
     * @param filename
     */
    //TODO:
    /*
    public static void getContentApplet(PrintWriter out, Portlet base, int version, WebPage topic, User user, String filename)
    {
        out.println("<APPLET id=\"apptpleditor\" name=\"apptpleditor\" code=\"applets.htmleditor.TemplateEditor\" codebase=\""+WBUtils.getInstance().getWebPath()+"\" ARCHIVE=\"swbadmin/lib/htmleditor.jar, swbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"100%\">");
        String url=WBUtils.getInstance().getDistPath()+"/SWBAdmin/WBAd_utl_HTMLEditor/_rid/1/_mto/3/_mod/";

        out.println("<PARAM NAME =\"upload\" VALUE=\""+url+"upload"+"\">");
        out.println("<PARAM NAME =\"download\" VALUE=\""+url+"download"+"\">");
        out.println("<PARAM NAME =\"gateway\" VALUE=\""+url+"gateway"+"\">");
        out.println("<PARAM NAME =\"startview\" VALUE=\"html\">");
        out.println("<PARAM NAME =\"tm\" VALUE=\""+base.getTopicMapId()+"\">");
        out.println("<PARAM NAME =\"id\" VALUE=\""+base.getId()+"\">");
        out.println("<PARAM NAME =\"tp\" VALUE=\""+topic.getId()+"\">");

        //todo: terminar esta parte
        out.println("<PARAM NAME =\"document\" VALUE=\""+SWB WBUtils.getInstance().getWebWorkPath()+base.getResourceWorkPath()+"/"+version+"/"+com.infotec.appfw.util.URLEncoder.encode(filename)+"\">");
        out.println("<PARAM NAME =\"filename\" VALUE=\""+filename+"\">");
        out.println("<PARAM NAME =\"ver\" VALUE=\""+version+"\">");
        out.println("<PARAM NAME =\"locale\" VALUE=\""+user.getLanguage()+"\">");
        out.println("<PARAM NAME =\"type\" VALUE=\"LocalContent\">");
        out.println("</APPLET>");        
    }    
    */
    
}
