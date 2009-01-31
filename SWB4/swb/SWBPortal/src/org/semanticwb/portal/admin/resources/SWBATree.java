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
import org.semanticwb.model.*;
import org.semanticwb.portal.admin.resources.wbtree.MenuElement;
import org.semanticwb.portal.admin.resources.wbtree.SWBTreeExt;
import org.semanticwb.portal.admin.resources.wbtree.SWBTreeUtil;
import org.semanticwb.portal.api.*;

/**
 * Recurso para la administraci�n de WebBuilder que muestra el �rbol principal de
 * la administraci�n con todos los elementos que existen en la aplicaci�n.
 *
 * Principal Tree of WebBuilder Administration that shows the existing elements in
 * the aplication.
 * @author Javier Solis Gonzalez
 */
public class SWBATree extends GenericResource
{
    ArrayList ext=new ArrayList();
    private Logger log = SWBUtils.getLogger(SWBATree.class);
    boolean agzip=true;

    //public static final String WBGLOBAL="WBGlobal";
    public static final String WBADMIN=SWBContext.getAdminWebSite().getId(); //"WBAdmin";

    //TODO:Provicional hasta que este AdmFilterMgr
    public static final int NO_ACCESS = 0;
    public static final int PARCIAL_ACCESS = 1;
    public static final int FULL_ACCESS = 2;


    /** Creates a new instance of WBTree */
    public SWBATree()
    {
        agzip = SWBPlatform.getEnv("wb/responseGZIPEncoding","true").equalsIgnoreCase("true");
        try
        {
            BufferedReader in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/com/infotec/wb/admin/resources/wbtree/WBTree.properties")));
            String str=null;
            while((str=in.readLine())!=null)
            {
                try
                {
                    if(!str.startsWith("#"))
                    {
                        Class cls=Class.forName(str);
                        ext.add(cls.newInstance());
                    }
                }catch(Exception e){log.error(e);}
            }
        }catch(Exception e){log.error(e);}

    }

    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(paramRequest.getMode().equals("gateway"))
        {
            doGateway(request,response,paramRequest);
        }else super.processRequest(request,response,paramRequest);
    }


    /**
     *
     * @param cmd
     * @param src
     * @param user
     * @param request
     * @param response
     * @return
     */
    public Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {
        //System.out.println("Service mi entrada:"+cmd);
        if (cmd.equals("initTree"))
        {
            return initTree(user, src);
        } else if (cmd.equals("initTreeFilter"))
        {
            return initTreeFilter(user, src);
        } else if (cmd.startsWith("getPath."))
        {
            return getPath(user, src,cmd.substring("getPath.".length()));
        }  else
        {
            return getDocument(user, src, cmd);
        }
    }

    /**
     *
     * @param user
     * @param src
     * @return
     */
    public Document initTree(User user, Document src)
    {
        return initTree(user, src, false);
    }

    /**
     *
     * @param user
     * @param src
     * @param isFilter
     * @return
     */
    public Document initTree(User user, Document src, boolean isFilter)
    {
        Document dom = null;
        try
        {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            //config
            Element config=addNode("config","config","Config",res);

            Element icons=addNode("icons","icons","Icons",config);

            Element icon=addNode("icon","sitev","Site",icons);
            icon.setAttribute("path","images/f_site_verde.gif");
            icon=addNode("icon","siter","Site",icons);
            icon.setAttribute("path","images/f_site_rojo.gif");
            icon=addNode("icon","hijor","WebPage",icons);
            icon.setAttribute("path","images/i_general_rojo.gif");
            icon=addNode("icon","hijov","WebPage",icons);
            icon.setAttribute("path","images/i_general_verde.gif");
            icon=addNode("icon","homer","WebPage",icons);
            icon.setAttribute("path","images/i_home_rojo.gif");
            icon=addNode("icon","homev","WebPage",icons);
            icon.setAttribute("path","images/i_home_verde.gif");
            icon=addNode("icon","virtual","WebPage",icons);
            icon.setAttribute("path","images/ico_virtual.gif");

            icon=addNode("icon","folder","Folder",icons);
            icon.setAttribute("path","images/f_general.gif");
            icon=addNode("icon","root","Root",icons);
            icon.setAttribute("path","images/i_root.gif");
            icon=addNode("icon","global","Global",icons);
            icon.setAttribute("path","images/f_global.gif");
            icon=addNode("icon","devices","Devices",icons);
            icon.setAttribute("path","images/f_dispositivos.gif");
            icon=addNode("icon","device","Device",icons);
            icon.setAttribute("path","images/i_dispositivo.gif");
            icon=addNode("icon","dnss","DNS",icons);
            icon.setAttribute("path","images/f_dns.gif");
            icon=addNode("icon","dns","DNS",icons);
            icon.setAttribute("path","images/i_dns.gif");
            icon=addNode("icon","resources","Resources",icons);
            icon.setAttribute("path","images/f_estrategias.gif");
            icon=addNode("icon","resourcetype","ResourceType",icons);
            icon.setAttribute("path","images/f_resourcetype.gif");
            icon=addNode("icon","sysresources","SysResources",icons);
            icon.setAttribute("path","images/f_sistema.gif");
            icon=addNode("icon","resourcer","Resource",icons);
            icon.setAttribute("path","images/i_recurso_rojo.gif");
            icon=addNode("icon","resourcev","Resource",icons);
            icon.setAttribute("path","images/i_recurso_verde.gif");
            icon=addNode("icon","flows","Flows",icons);
            icon.setAttribute("path","images/f_flujos.gif");
            icon=addNode("icon","flow","Flow",icons);
            icon.setAttribute("path","images/i_flujo.gif");
            icon=addNode("icon","languages","Languages",icons);
            icon.setAttribute("path","images/f_idioma.gif");
            icon=addNode("icon","language","Language",icons);
            icon.setAttribute("path","images/i_idioma.gif");
            icon=addNode("icon","metadatas","Metadatas",icons);
            icon.setAttribute("path","images/f_metadatos.gif");
            icon=addNode("icon","metadata","Metadata",icons);
            icon.setAttribute("path","images/i_metadata.gif");
            icon=addNode("icon","camps","Camps",icons);
            icon.setAttribute("path","images/f_camp.gif");
            icon=addNode("icon","campv","Camp",icons);
            icon.setAttribute("path","images/i_camp.gif");
            icon=addNode("icon","campr","Camp",icons);
            icon.setAttribute("path","images/i_camp_r.gif");
            icon=addNode("icon","templates","Templates",icons);
            icon.setAttribute("path","images/f_plantillas.gif");
            icon=addNode("icon","templater","Template",icons);
            icon.setAttribute("path","images/i_plantilla_rojo.gif");
            icon=addNode("icon","templatev","Template",icons);
            icon.setAttribute("path","images/i_plantilla_verde.gif");
            icon=addNode("icon","rules","Rules",icons);
            icon.setAttribute("path","images/f_reglas.gif");
            icon=addNode("icon","rule","Rule",icons);
            icon.setAttribute("path","images/i_regla.gif");
            icon=addNode("icon","userreps","UserReps",icons);
            icon.setAttribute("path","images/f_usuarios.gif");
            icon=addNode("icon","userrep","UserRep",icons);
            icon.setAttribute("path","images/i_repositoriousuarios.gif");
            icon=addNode("icon","role","Role",icons);
            icon.setAttribute("path","images/i_rol.gif");
            //menus
            icon=addNode("icon","trans","Transparent",icons);
            icon.setAttribute("path","images/trans.gif");
            icon=addNode("icon","refresh","Refresh",icons);
            icon.setAttribute("path","images/refresh.gif");
            icon=addNode("icon","edit","Edit",icons);
            icon.setAttribute("path","images/edit.gif");
            icon=addNode("icon","remove","Remove",icons);
            icon.setAttribute("path","images/remove.gif");
            icon=addNode("icon","add","Add",icons);
            icon.setAttribute("path","images/add.gif");
            icon=addNode("icon","active","Active",icons);
            icon.setAttribute("path","images/active.gif");
            icon=addNode("icon","unactive","Unactive",icons);
            icon.setAttribute("path","images/unactive.gif");
            icon=addNode("icon","trash","Trash",icons);
            icon.setAttribute("path","images/papelera.gif");
            icon=addNode("icon","catalog","Catalog",icons);
            icon.setAttribute("path","images/catalogo.gif");

            Iterator it=ext.iterator();
            while(it.hasNext())
            {
                SWBTreeExt e=(SWBTreeExt)it.next();
                e.initTree(user, res, isFilter);
            }

            addServer(user, res, isFilter);

        } catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
    }

    /**
     *
     * @param user
     * @param src
     * @return
     */
    public Document initTreeFilter(User user, Document src)
    {
        return initTree(user, src,true);
    }

    /**
     *
     * @param user
     * @param src
     * @param act
     * @return
     */
    public Document getDocument(User user, Document src, String act)
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
                //System.out.println(cmd+":"+id);
            }else cmd=act;

            if(cmd.equals("getServer"))
            {
                addServer(user, res);
            } else if (cmd.equals("getUserReps"))
            {
                addUserReps(user,res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
            } else if (cmd.equals("getUserRep"))
            {
                addUserRep(user, res, id, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
            } else if (cmd.equals("getRole"))
            {
                String recid=id.substring(0,id.indexOf('.'));
                String repid=id.substring(id.indexOf('.')+1);
                Role rec=SWBContext.getUserRepository(repid).getRole(recid);
                addRole(user, res,rec);
            } else if (cmd.equals("getGlobal"))
            {
                addGlobal(user, res, PARCIAL_ACCESS);    //AdmFilterMgr.PARCIAL_ACCESS
            } else if (cmd.equals("getWebSite"))
            {
                WebSite tm=SWBContext.getWebSite(id);
                addWebSite(user, tm, res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
            } else if (cmd.equals("getWebPage"))
            {
                String tmid=id.substring(0,id.indexOf('.'));
                String tpid=id.substring(id.indexOf('.')+1);
                WebPage tp=SWBContext.getWebSite(tmid).getWebPage(tpid);
                addWebPage(user, tp, res);
            }
//            else if (cmd.equals("getAdvResources"))
//            {
//                addAdvResources(user, SWBContext.getWebSite(id),res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
//            } else if (cmd.equals("getSysResources"))
//            {
//                addSysResources(user, SWBContext.getWebSite(id),res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
//            } else if (cmd.equals("getCntResources"))
//            {
//                addCntResources(user, SWBContext.getWebSite(id),res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
//            } else if (cmd.equals("getResourceType"))
//            {
//                String recid=id.substring(0,id.indexOf('.'));
//                String tmid=id.substring(id.indexOf('.')+1);
//                PortletType rec=null;
//                if(recid.startsWith("0"))
//                {
//                    rec=SWBContext.getGlobalWebSite().getPortletType(recid);
//                }else
//                {
//                    rec=SWBContext.getWebSite(tmid).getPortletType(recid);
//                }
//                if(rec!=null)
//                {
//                    addResourceType(user, SWBContext.getWebSite(tmid),rec,res,true, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
//                }
//            } else if (cmd.equals("getResourceSubType"))
//            {
//                String recid=id.substring(0,id.indexOf('.'));
//                String tmid=id.substring(id.indexOf('.')+1);
//                PortletSubType rec=null;
//                if(recid.startsWith("0"))
//                {
//                    rec=SWBContext.getGlobalWebSite().getPortletSubType(recid);
//                }else
//                {
//                    rec=SWBContext.getWebSite(tmid).getPortletSubType(recid);
//                }
//                if(rec!=null)
//                {
//                    addResourceSubType(user, SWBContext.getWebSite(tmid),rec,res,true);
//                }
//            } else if (cmd.equals("getResource"))
//            {
//                String recid=id.substring(0,id.indexOf('.'));
//                String tmid=id.substring(id.indexOf('.')+1);
//                Portlet rec=SWBContext.getWebSite(tmid).getPortlet(recid);
//                if(rec!=null)
//                {
//                    addResource(user, rec,res);
//                }
//            } else if (cmd.equals("getCamps"))
//            {
//                addCamps(user, SWBContext.getWebSite(id),res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
//            } else if (cmd.equals("getCamp"))
//            {
//                String recid=id.substring(0,id.indexOf('.'));
//                String tmid=id.substring(id.indexOf('.')+1);
//                Camp rec=SWBContext.getWebSite(tmid).getCamp(recid);
//                if(rec!=null)
//                {
//                    addCamp(user, rec, res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
//                }
//            } else if (cmd.equals("getTemplates"))
//            {
//                addTemplates(user, SWBContext.getWebSite(id),res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
//            } else if (cmd.equals("getGrpTemplate"))
//            {
//                String recid=id.substring(0,id.indexOf('.'));
//                String tmid=id.substring(id.indexOf('.')+1);
//                TemplateGroup rec=SWBContext.getWebSite(tmid).getTemplateGroup(recid);
//                if(rec!=null)
//                {
//                    addGrpTemplate(user, rec, res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
//                }
//            } else if (cmd.equals("getTemplate"))
//            {
//                String recid=id.substring(0,id.indexOf('.'));
//                String tmid=id.substring(id.indexOf('.')+1);
//                Template rec=SWBContext.getWebSite(tmid).getTemplate(recid);
//                if(rec!=null)
//                {
//                    addTemplate(user, rec,res);
//                }
//            } else if (cmd.equals("getLanguages"))
//            {
//                WebSite tm=SWBContext.getWebSite(id);
//                addLanguages(user, tm,res, PARCIAL_ACCESS);        //AdmFilterMgr.PARCIAL_ACCESS
//            } else if (cmd.equals("getLanguage"))
//            {
//                String lid=id.substring(0,id.indexOf('.'));
//                String tmid=id.substring(id.indexOf('.')+1);
//                Language rec=SWBContext.getWebSite(tmid).getLanguage(lid);
//                if(rec!=null)
//                {
//                    addLanguage(user, rec,res);
//                }
//            } else if (cmd.equals("getDevices"))
//            {
//                addDevices(user, res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
//            } else if (cmd.equals("getDevice"))
//            {
//
//                String lid=id.substring(0,id.indexOf('.'));
//                String tmid=id.substring(id.indexOf('.')+1);
//                Device rec=SWBContext.getWebSite(tmid).getDevice(id);
//                if(rec!=null)
//                {
//                    addDevice(user, rec,res);
//                }
//            } else if (cmd.equals("getDnss"))
//            {
//                addDnss(user, SWBContext.getWebSite(id),res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
//            } else if (cmd.equals("getDns"))
//            {
//                String lid=id.substring(0,id.indexOf('.'));
//                String tmid=id.substring(id.indexOf('.')+1);
//                Dns rec=SWBContext.getWebSite(tmid).getDns(lid);
//                if(rec!=null)
//                {
//                    addDns(user, rec,res);
//                }
//            }
//            else if (cmd.equals("getMDTables"))
//            {
//                addMDTables(user, WebPageMgr.getInstance().getWebSite(id),res, AdmFilterMgr.PARCIAL_ACCESS);
//            }
//            else if (cmd.equals("getMDTable"))
//            {
//                String lid=id.substring(0,id.indexOf('.'));
//                String tmid=id.substring(id.indexOf('.')+1);
//                RecMDTable rec=DBMetaData.getInstance().getMDTable(Integer.parseInt(lid),tmid);
//                if(rec!=null)
//                {
//                    addMDTable(user, rec,res);
//                }
//            }
//            else if (cmd.equals("getRules"))
//            {
//                addRules(user, SWBContext.getWebSite(id),res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
//            } else if (cmd.equals("getRule"))
//            {
//                String lid=id.substring(0,id.indexOf('.'));
//                String tmid=id.substring(id.indexOf('.')+1);
//                Rule rec=SWBContext.getWebSite(tmid).getRule(lid);
//                if(rec!=null)
//                {
//                    addRule(user, rec,res);
//                }
//            } else if (cmd.equals("getFlows"))
//            {
//                addFlows(user, SWBContext.getWebSite(id),res, PARCIAL_ACCESS); //AdmFilterMgr.PARCIAL_ACCESS
//            } else if (cmd.equals("getFlow"))
//            {
//                String lid=id.substring(0,id.indexOf('.'));
//                String tmid=id.substring(id.indexOf('.')+1);
//                PFlow rec=SWBContext.getWebSite(tmid).getPFlow(lid);
//                if(rec!=null)
//                {
//                    addFlow(user, SWBContext.getWebSite(tmid),rec,res);
//                }
//            }
            else
            {
                boolean ret=false;
                Iterator itex=ext.iterator();
                while(itex.hasNext())
                {
                    SWBTreeExt e=(SWBTreeExt)itex.next();
                    ret=e.executeCommand(user, res, cmd, id);
                    if(ret)break;
                }
                if(!ret)return getError(2);
            }
        } catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
    }

    private void addOptRefresh(Element menu, User user)
    {
        SWBTreeUtil.addOptRefresh(menu,user);
    }
/*
    private String getConfirm(String WebPage, User user)
    {
        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
        return tma.getWebPage("WBAd_glos_RemoveConfirm").getDisplayName(user.getLanguage())+" "+tma.getWebPage(WebPage).getDisplayName(user.getLanguage())+"?";
    }

    private String getVariantName(String WebPage, User user)
    {
        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
        String str="No Variant";
        try
        {
            str=((Variant)tma.getWebPage(WebPage).getDisplayBaseName(user.getLanguage()).getVariants().get(0)).getVariantName().getResourceData();
        }catch(Exception e){log.error(e);}
        return str;
    }

    private Element addMenu(Element menu, String WebPage, String WebPage_action, String act, String params, User user)
    {
        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
        Element option=addNode("option",WebPage,tma.getWebPage(WebPage).getDisplayName(user.getLanguage()),menu);
        if(act.equals("add"))
        {
            option.setAttribute("action","showurl="+tma.getWebPage(WebPage_action).getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act="+act+params);
            option.setAttribute("target", "work");
        }else if(act.equals("edit"))
        {
            option.setAttribute("action","showurl="+tma.getWebPage(WebPage_action).getUrl()+"?act="+act+params);
            option.setAttribute("target", "work");
        }
        return option;
    }
*/
    private void addSeparator(Element menu)
    {
        SWBTreeUtil.addSeparator(menu);
    }

    /**
     *
     * @param user
     * @param src
     * @param action
     * @return
     */
    public Document getPath(User user, Document src, String action)
    {
        Document dom = null;
        try
        {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);

            StringTokenizer st=new StringTokenizer(action,".");
            String cmd=st.nextToken();

            if(cmd.equals("WebPage"))
            {
                String tmid=st.nextToken();
                String tpid=st.nextToken();

                StringBuffer ret=new StringBuffer();
                WebPage tp=SWBContext.getWebSite(tmid).getWebPage(tpid);
                ret.append(tp.getId());
                //ArrayList arr=AdmFilterMgr.getInstance().getWebPages(user, tmid);
                Iterator<WebPage> arr=SWBContext.getWebSite(tmid).listWebPages();
                while(arr.hasNext())
                {
                    WebPage wp = arr.next();
                    if(wp!=tp)
                    {
                        tp=tp.getParent();
                        ret.insert(0,tp.getId()+".");
                    }
                }
                ret.insert(0,tmid+".");
                res.appendChild(dom.createTextNode(ret.toString()));
            }
        } catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
    }

    /**
     *
     * @param user
     * @param res
     */
    protected void addServer(User user, Element res)
    {
        addServer(user, res, false);
    }

    /**
     *
     * @param user
     * @param res
     * @param isFilter
     */
    protected void addServer(User user, Element res, boolean isFilter)
    {
        int access=FULL_ACCESS; //AdmFilterMgr.getInstance().haveAccess2Server(user);

        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        Element events=null;
        Element event=null;

        //tree nodes
        Element root=addNode("node","server",tma.getWebPage("WBAd_sys_Server").getDisplayName(user.getLanguage()),res);
        root.setAttribute("reload","getServer");
        root.setAttribute("icon","root");
        root.setAttribute("access",""+access);
        menu=addNode("menu","menu","Menu",root);

//        int sites=0;
//        Iterator it=sortIterator(WebPageMgr.getInstance().getWebSites());
//        while(it.hasNext())
//        {
//            WebSite map=(WebSite)it.next();
//            if(!map.getId().equals(WebPageMgr.TM_ADMIN) && !map.getId().equals(WebPageMgr.TM_GLOBAL))
//            {
//                sites++;
//            }
//        }

        option=MenuElement.add(menu,"WBAd_mnui_WebSiteCreate","WBAd_sysi_WebSitesInfo",null,user,null,null);
        //option=addNode("option","create_site",tma.getWebPage("WBAd_mnui_WebSiteCreate").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebSitesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add");
        //option.setAttribute("target","work");
        addSeparator(menu);

        addOptRefresh(menu,user);

        addGlobal(user, root, access, isFilter);

        //WebPage maps
        Iterator<WebSite> it=sortIterator(SWBContext.listWebSites());
        while(it.hasNext())
        {
            //WebSite
            WebSite tm=it.next();
            if(!tm.isDeleted() && !tm.getId().equals(SWBContext.getGlobalWebSite().getId()))
            {
                addWebSite(user, tm, root, access,isFilter,isFilter);
            }
        }

        addUserReps(user, root, access);

        Iterator itex=ext.iterator();
        while(itex.hasNext())
        {
            SWBTreeExt e=(SWBTreeExt)itex.next();
            e.addServer(user, root, isFilter);
        }

    }

    /**
     *
     * @param user
     * @param root
     * @param access
     */
    protected void addUserReps(User user, Element root, int access)
    {
        if(access!=FULL_ACCESS) //AdmFilterMgr.FULL_ACCESS
        {
            access=FULL_ACCESS; //AdmFilterMgr.getInstance().haveAccess2UserReps(user);
            if(access==NO_ACCESS)return; //AdmFilterMgr.NO_ACCESS
        }

        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;

        Element ele=addNode("node","userreps",tma.getWebPage("WBAd_sys_UserReps").getDisplayName(user.getLanguage()),root);
        //ele.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?id="+rec.getId()+"&stype="+rec.getIdSubType());
        //ele.setAttribute("target","work");
        ele.setAttribute("reload","getUserReps");
        //ele.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_ResourceInfo").getUrl()+"?id="+rec.getId());
        //ele.setAttribute("vtarget","info");
        ele.setAttribute("icon","userreps");
        ele.setAttribute("access",""+access);

        //menu
        menu=addNode("menu","menu","Menu",ele);
        addOptRefresh(menu,user);

        Iterator<UserRepository> it=sortIterator(UserRepository.listUserRepositorys());
        while(it.hasNext())
        {
            UserRepository rep=it.next();
            addUserRep(user, ele, rep.getId(), access);
        }

    }

    /**
     *
     * @param user
     * @param root
     * @param id
     * @param access
     */
    protected void addUserRep(User user, Element root, String id, int access)
    {
        if(access!=FULL_ACCESS) //AdmFilterMgr.FULL_ACCESS
        {
            access=FULL_ACCESS;//AdmFilterMgr.getInstance().haveAccess2UserRep(user, id);
            if(access==NO_ACCESS)return; //AdmFilterMgr.NO_ACCESS
        }

        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;

        //UserRepository rep=UserRepository.getUserRepository(id);
        UserRepository rep=user.getUserRepository();

        Element ele=addNode("node",id,rep.getId(),root);
        ele.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_UserRepsInfo").getUrl()+"?tm="+id);
        ele.setAttribute("target","work");
        ele.setAttribute("reload","getUserRep."+id);
        //ele.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_ResourceInfo").getUrl()+"?id="+rec.getId());
        //ele.setAttribute("vtarget","info");
        ele.setAttribute("icon","userrep");
        ele.setAttribute("access",""+access);

        //menu
        menu=addNode("menu","menu","Menu",ele);
        option=MenuElement.edit(menu,"WBAd_mnui_UserRepEdit","WBAd_sysi_UserRepsEdit",null,user,id,null);
        addSeparator(menu);
        option=MenuElement.add(menu,"WBAd_mnui_RoleAdd","WBAd_sysi_RolesInfo",null,user,null,""+id);
        //option=addNode("option","add_role","Add Role",menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_RolesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&id="+id);
        //option.setAttribute("target","work");
        addSeparator(menu);
        addOptRefresh(menu,user);

        Iterator<Role> it = sortIterator(rep.listRoles());
        while(it.hasNext())
        {
            Role role=(Role)it.next();
            addRole(user, ele,role);
        }

    }

    /**
     *
     * @param user
     * @param root
     * @param role
     */
    public void addRole(User user, Element root, Role role)
    {
        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;

        Element ele=addNode("node",""+role.getId(),role.getTitle(),root);
        ele.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_RolesInfo").getUrl()+"?id="+role.getId()+"&tm="+role.getUserRepository().getId());
        ele.setAttribute("target","work");
        ele.setAttribute("reload","getRole."+role.getId()+"."+role.getUserRepository().getId());
        ele.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_RolesInfo").getUrl()+"?id="+role.getId()+"&tm="+role.getUserRepository().getId());
        ele.setAttribute("vtarget","info");
        ele.setAttribute("icon","role");

        //menu
        menu=addNode("menu","menu","Menu",ele);
        option=MenuElement.edit(menu,"WBAd_mnui_RoleEdit","WBAd_sysi_RolesInfo",null,user,role.getUserRepository().getId(),""+role.getId());
        //option=addNode("option","edit_role","Edit Role",menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_RolesInfo").getUrl()+"?id="+role.getId()+"&tm="+role.getRepository()+"&act=edit");
        //option.setAttribute("target","work");
        addSeparator(menu);
        option=MenuElement.remove(menu,"WBAd_mnui_RoleRemove","WBAd_inti_StatusRole",null,user,"WBAd_mnui_Role", role.getUserRepository().getId(),""+role.getId());
        //option=addNode("option","remove_role","Remove Role",menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_RolesInfo").getUrl()+"?id="+role.getId()+"&tm="+role.getRepository()+"&act=remove&status=true");
        //option.setAttribute("target","status");
        //option.setAttribute("confirm","Are you sure of delete the Role?");
        //option.setAttribute("shortCut","DELETE");
        addSeparator(menu);
        addOptRefresh(menu,user);
    }

    /**
     *
     * @param user
     * @param root
     * @param access
     */
    protected void addGlobal(User user, Element root, int access)
    {
        addGlobal(user, root, access, false);
    }

    /**
     *
     * @param user
     * @param root
     * @param access
     * @param isFilter
     */
    protected void addGlobal(User user, Element root, int access, boolean isFilter)
    {
        if(access!=FULL_ACCESS)  //AdmFilterMgr.FULL_ACCESS
        {
            access=FULL_ACCESS; //AdmFilterMgr.getInstance().haveAccess2TMGlobal(user);
            if(access==NO_ACCESS)return; //AdmFilterMgr.NO_ACCESS
        }

        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;

        WebSite tm=SWBContext.getGlobalWebSite();

        Element ele=addNode("node",tm.getId(),tma.getWebPage("WBAd_sys_Global").getDisplayName(user.getLanguage()),root);
        //ele.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?id="+rec.getId()+"&stype="+rec.getIdSubType());
        //ele.setAttribute("target","work");
        ele.setAttribute("reload","getGlobal");
        //ele.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_ResourceInfo").getUrl()+"?id="+rec.getId());
        //ele.setAttribute("vtarget","info");
        ele.setAttribute("icon","global");
        ele.setAttribute("access",""+access);

        //menu
        menu=addNode("menu","menu","Menu",ele);
        option=MenuElement.addOption(menu,"WBAd_mnui_Trash","WBAd_sysi_TrashWebPages",null,user, null, tm.getId(),null);
        if(option!=null)option.setAttribute("icon","trash");
        //option=addNode("option","trash",tma.getWebPage("WBAd_sys_Trash").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TrashWebPages").getUrl()+"?tm="+tm.getId());
        //option.setAttribute("target","work");
        option=MenuElement.addOption(menu,"WBAd_mnui_ResourceTypes","WBAd_sys_ResourceCatalogInfo",null,user, null, tm.getId(), null);
        if(option!=null)option.setAttribute("icon","catalog");
        //option=addNode("option","resourceTypes",tma.getWebPage("WBAd_sys_ResourceCatalog").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sys_ResourceCatalog").getUrl()+"?tm="+tm.getId());
        //option.setAttribute("target","work");
        addSeparator(menu);
        addOptRefresh(menu,user);

//        //Templates
//        addTemplates(user, tm,ele, access);
//
//        //Rules
//        addRules(user, tm, ele, access);
//
//        //Rules
//        addFlows(user, tm, ele, access);
//
//        //Languages
//        addLanguages(user, tm,ele, access);
//
//        //Devices
//        addDevices(user, ele, access);
//
//        //Metadata
//        addMDTables(user, tm,ele, access);
//
//        //DNS
//        addDnss(user, tm,ele, access);
//
//        //Estrategias
//        addAdvResources(user, tm, ele, access);
//
//        //Sistema
//        addSysResources(user, tm, ele, access);
//
//        //Contenido
//        if(isFilter)addCntResources(user, tm, ele, access);
//
//        //Camps
//        addCamps(user, tm,ele,access);

        Iterator itex=ext.iterator();
        while(itex.hasNext())
        {
            SWBTreeExt e=(SWBTreeExt)itex.next();
            e.addGlobal(user, ele, access, isFilter);
        }
    }


    /**
     *
     * @param user
     * @param rec
     * @param root
     */
//    protected void addResource(User user, Portlet rec, Element root)
//    {
//        WebSite tma=SWBContext.getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        Element ele=addNode("node",""+rec.getId(),rec.getTitle(),root);
//        ele.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId());//+"&stype="+rec.getIdSubType());
//        ele.setAttribute("target","work");
//        ele.setAttribute("reload","getResource."+rec.getId()+"."+rec.getWebSiteId());
//        ele.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_ResourceInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId());
//        ele.setAttribute("vtarget","info");
//
//        //menu
//        menu=addNode("menu","menu","Menu",ele);
//        option=MenuElement.addOption(menu,"WBAd_mnui_ResourcePreview","WBAd_sysi_ResourcesInfo",null,user,"/_rid/"+rec.getId()+"/_idtm/"+rec.getWebSiteId()+"/_mod/view/_wst/maximized?tpcomm=WBAd_mnui_ResourcePreview",null,null);
//
//        option=MenuElement.addOption(menu,"WBAd_mnui_ResourceAdmin","WBAd_sysi_ResourcesInfo",null,user,"/_rid/"+rec.getId()+"/_idtm/"+rec.getWebSiteId()+"/_mod/admin/_wst/maximized?tpcomm=WBAd_mnui_ResourceAdmin",null,null);
//        //option=addNode("option","admin_resource","Resource Administration",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"/_rid/"+rec.getId()+"/_idtm/"+rec.getWebSiteId()+"/_mod/admin/_wst/maximized");
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        option=MenuElement.edit(menu,"WBAd_mnui_ResourceEdit","WBAd_sysi_ResourcesInfo",null,user, rec.getWebSiteId(),""+rec.getId());
//        //option=addNode("option","edit_resource","Edit Resource",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=edit");
//        //option.setAttribute("target","work");
//        if(rec.isActive())
//        {
//            option=MenuElement.unactive(menu,"WBAd_mnui_ResourceActive","WBAd_inti_StatusResurce",null,user, rec.getWebSiteId(),""+rec.getId());
//            //option=addNode("option","unactive_resource","Unactive Resource",menu);
//            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=unactive&status=true");
//            //option.setAttribute("target","status");
//            ele.setAttribute("icon","resourcev");
//        }
//        else
//        {
//            option=MenuElement.active(menu,"WBAd_mnui_ResourceActive","WBAd_inti_StatusResurce",null,user, rec.getWebSiteId(),""+rec.getId());
//            //option=addNode("option","active_resource","Active Resource",menu);
//            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=active&status=true");
//            //option.setAttribute("target","status");
//            ele.setAttribute("icon","resourcer");
//        }
//        addSeparator(menu);
//        option=MenuElement.copy(menu,"WBAd_mnui_ResourceCopy","WBAd_inti_StatusResurce",null,user,"WBAd_mnui_Resource",rec.getWebSiteId(),""+rec.getId());
//        addSeparator(menu);
//        option=MenuElement.remove(menu,"WBAd_mnui_ResourceRemove","WBAd_inti_StatusResurce",null,user,"WBAd_mnui_Resource",rec.getWebSiteId(),""+rec.getId());
//        //option=addNode("option","remove_resource","Remove Resource",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=remove&status=true");
//        //option.setAttribute("target","status");
//        //option.setAttribute("confirm","Are you sure of delete the Resource?");
//        //option.setAttribute("shortCut","DELETE");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//    }

    /**
     *
     * @param user
     * @param tm
     * @param rec
     * @param root
     */
//    protected void addResourceSubType(User user, WebSite tm,PortletSubType rec,Element root, boolean loadChild)
//    {
//        WebSite tma=SWBContext.getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//        Element events=null;
//        Element event=null;
//
//        String tmsid=tm.getId();
//
//        String sglobal="";
//        if(!rec.getWebSite().getId().equals(tm.getId()))sglobal="0";
//
//        Element ele=addNode("node",sglobal+rec.getId(),rec.getTitle(),root);
//        ele.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourceSubTypeInfo").getUrl()+"?id="+sglobal+rec.getId()+"&idsmap="+rec.getWebSite().getId()+"&tm="+tmsid);
//        ele.setAttribute("target","work");
//        ele.setAttribute("reload","getResourceSubType."+sglobal+rec.getId()+"."+tmsid);
//        ele.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_ResourceSubTypeInfo").getUrl()+"?id="+sglobal+rec.getId()+"&tm="+tmsid);
//        ele.setAttribute("vtarget","info");
//        ele.setAttribute("icon","folder");
//        ele.setAttribute("dragEnabled","true");
//        PortletType recobj=rec.getType(); //DBResourceType.getInstance().getResourceType(rec.getTypeMap(),rec.getType());
//        ele.setAttribute("dragValue","<RESOURCE NAME=\""+recobj.getTitle()+"\" TYPE=\""+recobj.getTitle()+"\" STYPE=\""+rec.getTitle()+"\"/>");
//
//        menu=addNode("menu","menu","Menu",ele);
//        option=MenuElement.add(menu,"WBAd_mnui_ResourceAdd","WBAd_sysi_ResourcesInfo",null,user, tmsid,sglobal+rec.getType().getId(),"&typemap="+rec.getType().getWebSite().getId()+"&ids="+rec.getId()+"&idsmap="+rec.getWebSite().getId());
//        //option=addNode("option","add_resource","Add Resource",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&id="+sglobal+rec.getType()+"&typemap="+rec.getTypeMap()+"&ids="+rec.getId()+"&idsmap="+rec.getWebSiteId()+"&tm="+tmsid);
//        //option.setAttribute("target","work");
//
//        if(sglobal.length()==0)
//        {
//            option=MenuElement.edit(menu,"WBAd_mnui_ResourceSubtypeEdit","WBAd_sysi_ResourceSubTypeInfo",null,user, rec.getWebSite().getId(),sglobal+rec.getId(),"&idsmap="+rec.getWebSite().getId());
//            //option=addNode("option","edit_subtype","Edit SubType",menu);
//            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourceSubTypeInfo").getUrl()+"?id="+sglobal+rec.getId()+"&idsmap="+rec.getWebSiteId()+"&tm="+rec.getWebSiteId()+"&act=edit");
//            //option.setAttribute("target","work");
//            addSeparator(menu);
//            option=MenuElement.remove(menu,"WBAd_mnui_ResourceSubtypeRemove","WBAd_inti_StatusSubtype",null,user, "WBAd_mnui_ResourceSubtype", rec.getWebSite().getId(),sglobal+rec.getId(),"&idsmap="+rec.getWebSite().getId());
//            //option=addNode("option","remove_subtype","Remove SubType",menu);
//            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourceSubTypeInfo").getUrl()+"?id="+sglobal+rec.getId()+"&idsmap="+rec.getWebSiteId()+"&tm="+rec.getWebSiteId()+"&act=remove&status=true");
//            //option.setAttribute("target","status");
//            //option.setAttribute("confirm","Are you sure of delete the Resource?");
//            //option.setAttribute("shortCut","DELETE");
//        }
//
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        if(loadChild)
//        {
//            //System.out.println("addResourceSubType:"+tmsid+" "+rec.getType()+" "+rec.getWebSiteId());
//            Iterator it=sortCollection(ResourceMgr.getInstance().getResourcesBaseOfType(tmsid,rec.getType(),rec.getTypeMap()).values());
//            while(it.hasNext())
//            {
//                Portlet res=(Portlet)it.next();
//                //System.out.println(res.getSubType()+" "+res.getSubTypeMap()+" "+rec.getId()+" "+rec.getWebSiteId());
//                if(res.getPortletSubType().getId().equals(rec.getId()) && res.getPortletType().getWebSite().getId().equals(rec.getWebSite().getId()) && !res.isDeleted())
//                {
//                    try
//                    {
//                        //RecResource recs=res.getRecResource();
//                        addResource(user, res, ele);
//                    }catch(Exception e){log.error(e);}
//                }
//            }
//        }else
//        {
//            //have child
//
//            Iterator it=sortCollection(ResourceMgr.getInstance().getResourcesBaseOfType(tmsid,rec.getType(),rec.getTypeMap()).values());
//            while(it.hasNext())
//            {
//                Portlet res=(Portlet)it.next();
//                //System.out.println(res.getSubType()+" "+res.getSubTypeMap()+" "+rec.getId()+" "+rec.getWebSiteId());
//                if(res.getPortletSubType().getId().equals(rec.getId()) && res.getPortletSubType().getWebSite().getId().equals(rec.getWebSite().getId()) && !res.isDeleted())
//                {
//                    Element dummy=addNode("node","dummy","dummy",ele);
//                    dummy.setAttribute("reload","getResource");
//                    break;
//                }
//            }
//
//            //events
//            events=addNode("events","events","Events",ele);
//            event=addNode("willExpand","willExpand","WillExpand",events);
//            event.setAttribute("action","reload");
//        }
//    }

    /**
     *
     * @param user
     * @param tm
     * @param recobj
     * @param root
     * @param loadChild
     * @param access
     */
//    protected void addResourceType(User user, WebSite tm, PortletType recobj,Element root, boolean loadChild, int access)
//    {
//        if(access!=FULL_ACCESS) //AdmFilterMgr.FULL_ACCESS
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2ResourceType(user, tm.getId(), recobj.getId(), recobj.getWebSite().getId());
//            if(access==NO_ACCESS)return; //AdmFilterMgr.NO_ACCESS
//        }
//
//        WebSite tma=SWBContext.getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//        Element events=null;
//        Element event=null;
//
//        String tmsid=tm.getId();
//
//        String sglobal="";
//        if(!recobj.getWebSite().getId().equals(tm.getId()))sglobal="0";
//
//        Element ele=addNode("node",sglobal+recobj.getId(),recobj.getTitle(),root);
//        ele.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourceTypeInfo").getUrl()+"?id="+sglobal+recobj.getId()+"&typemap="+recobj.getWebSite().getId()+"&tm="+tmsid);
//        ele.setAttribute("target","work");
//        ele.setAttribute("reload","getResourceType."+sglobal+recobj.getId()+"."+tmsid);
//        ele.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_ResourceTypeInfo").getUrl()+"?id="+sglobal+recobj.getId()+"&tm="+tmsid);
//        ele.setAttribute("vtarget","info");
//        ele.setAttribute("icon","resourcetype");
//        ele.setAttribute("access",""+access);
//        ele.setAttribute("dragEnabled","true");
//        ele.setAttribute("dragValue","<RESOURCE NAME=\""+recobj.getTitle()+"\" TYPE=\""+recobj.getTitle()+"\"/>");
//
//        menu=addNode("menu","menu","Menu",ele);
//        option=MenuElement.add(menu,"WBAd_mnui_ResourceSubtypeAdd","WBAd_sysi_ResourceSubTypeInfo",null,user, tmsid,sglobal+recobj.getId(),"&typemap="+recobj.getWebSite().getId());
//        //option=addNode("option","add_ressubtype","Add SubResource Type",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourceSubTypeInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&id="+sglobal+recobj.getId()+"&typemap="+recobj.getWebSiteId()+"&tm="+tmsid);
//        //option.setAttribute("target","work");
//        option=MenuElement.add(menu,"WBAd_mnui_ResourceAdd","WBAd_sysi_ResourcesInfo",null,user, tmsid,sglobal+recobj.getId(),"&typemap="+recobj.getWebSite().getId());
//        //option=addNode("option","add_resource","Add Resource",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&id="+sglobal+recobj.getId()+"&typemap="+recobj.getWebSiteId()+"&tm="+tmsid);
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        if(loadChild)
//        {
//            recobj.listPortlets()
//            HashMap map=SWBPortal.getResourceMgr().getResources(recobj, stype, user, topic, params, tpl).getResourcesBaseOfType(tmsid,recobj.getId(),recobj.getWebSiteId());
//            if(map!=null)
//            {
//                Iterator it=map.values().iterator();
//                while(it.hasNext())
//                {
//                    Portlet res=(Portlet)it.next();
//                    if(!res.isDeleted())
//                    {
//                        if(res.getPortletSubType().getId().equals("0"))
//                        {
//                            try
//                            {
//                                //RecResource rec=res.getRecResource();
//                                addResource(user, res, ele);
//                            }catch(Exception e){log.error(e);}
//                        }
//                    }
//                }
//            }
//
//            //Iterator it=sortIterator(DBCatalogs.getInstance().getAllSubTypes(tmsid,recobj.getId(),recobj.getWebSite().getId()));
//            Iterator<PortletSubType> it=sortIterator(recobj.getWebSite().listPortletSubTypes());
//            while(it.hasNext())
//            {
//                PortletSubType r=it.next();
//                try
//                {
//                    addResourceSubType(user, tm,r,ele,false);
//                }catch(Exception e){log.error(e);}
//            }
//        }else
//        {
//                //have child
//
//                //Iterator it=sortIterator(DBCatalogs.getInstance().getAllSubTypes(tmsid,recobj.getId(),recobj.getWebSiteId()));
//            Iterator<PortletSubType> it=sortIterator(recobj.getWebSite().listPortletSubTypes());
//                if(it.hasNext())
//                {
//                    Element dummy=addNode("node","dummy","dummy",ele);
//                    dummy.setAttribute("reload","getResourceSubType");
//                }else
//                {
//                    //System.out.println("getResourcesBaseOfType:"+tmsid+" "+recobj.getId()+" "+recobj.getWebSiteId());
//                    it=sortCollection(ResourceMgr.getInstance().getResourcesBaseOfType(tmsid,recobj.getId(),recobj.getWebSiteId()).values());
//                    while(it.hasNext())
//                    {
//                        Resource res=(Resource)it.next();
//                        if(res.getRecResource().getDeleted()==0)
//                        {
//                            Element dummy=addNode("node","dummy","dummy",ele);
//                            dummy.setAttribute("reload","getResource");
//                            break;
//                        }
//                    }
//                }
//
//                //events
//                events=addNode("events","events","Events",ele);
//                event=addNode("willExpand","willExpand","WillExpand",events);
//                event.setAttribute("action","reload");
//        }
//
//
//    }


    /**
     *
     * @param user
     * @param tm
     * @param root
     * @param access
     */
//    protected void addSysResources(User user, WebSite tm, Element root, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2SysResources(user, tm.getId());
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        String tmsid=tm.getId();
//
//        Element ele=addNode("node","sysresources",tma.getWebPage("WBAd_sys_SystemResources").getDisplayName(user.getLanguage()),root);
//        ele.setAttribute("reload","getSysResources."+tmsid);
//        ele.setAttribute("icon","sysresources");
//        ele.setAttribute("access",""+access);
//
//        menu=addNode("menu","menu","Menu",ele);
//        //option=addNode("option","add_sysresource","Add System Resource",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add");
//        //option.setAttribute("target","work");
//
//        //addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        Iterator it=sortIterator(DBResourceType.getInstance().getAllResourceTypes(tm.getId()));
//        while(it.hasNext())
//        {
//            PortletType rec=(PortletType)it.next();
//            if(rec.getType()==3)
//            {
//                addResourceType(user, tm, rec, ele,false, access);
//            }
//        }
//    }

    /**
     *
     * @param user
     * @param tm
     * @param root
     * @param access
     */
//    protected void addAdvResources(User user, WebSite tm, Element root, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2AdvResources(user, tm.getId());
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        String tmsid=tm.getId();
//
//        Element ele=addNode("node","advresources",tma.getWebPage("WBAd_sys_AdvertisingResources").getDisplayName(user.getLanguage()),root);
//        ele.setAttribute("reload","getAdvResources."+tmsid);
//        ele.setAttribute("icon","resources");
//        ele.setAttribute("access",""+access);
//
//
//        menu=addNode("menu","menu","Menu",ele);
//        //option=addNode("option","add_sysresource","Add System Resource",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add");
//        //option.setAttribute("target","work");
//
//        //addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        Iterator it=sortIterator(DBResourceType.getInstance().getAllResourceTypes(tm.getId()));
//        while(it.hasNext())
//        {
//            PortletType rec=(PortletType)it.next();
//            //System.out.println("rec:"+rec.getName());
//            if(rec.getType()==2)
//            {
//                addResourceType(user, tm, rec, ele, false, access);
//            }
//        }
//    }

    /**
     *
     * @param user
     * @param tm
     * @param root
     * @param access
     */
//    protected void addCntResources(User user, WebSite tm, Element root, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2AdvResources(user, tm.getId());
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        String tmsid=tm.getId();
//
//        Element ele=addNode("node","cntresources",tma.getWebPage("WBAd_sys_ContentResources").getDisplayName(user.getLanguage()),root);
//        ele.setAttribute("reload","getCntResources."+tmsid);
//        ele.setAttribute("icon","resources");
//        ele.setAttribute("access",""+access);
//
//
//        menu=addNode("menu","menu","Menu",ele);
//        //option=addNode("option","add_sysresource","Add System Resource",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add");
//        //option.setAttribute("target","work");
//
//        //addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        Iterator it=sortIterator(DBResourceType.getInstance().getAllResourceTypes(tm.getId()));
//        while(it.hasNext())
//        {
//            PortletType rec=(PortletType)it.next();
//            //System.out.println("rec:"+rec.getName());
//            if(rec.getType()==1)
//            {
//                addResourceType(user, tm, rec, ele, false, access);
//            }
//        }
//    }

    /**
     *
     * @param user
     * @param tm
     * @param root
     * @param access
     */
    protected void addWebSite(User user, WebSite tm, Element root, int access)
    {
        addWebSite(user, tm, root, access, true, false);
    }

    /**
     *
     * @param user
     * @param tm
     * @param root
     * @param access
     * @param isFilter
     */
    protected void addWebSite(User user, WebSite tm, Element root, int access, boolean loadChild, boolean isFilter)
    {
        if(access!=FULL_ACCESS) //AdmFilterMgr.FULL_ACCESS
        {
            access=FULL_ACCESS; //AdmFilterMgr.getInstance().haveAccess2WebSite(user, tm.getId());
            if(access==NO_ACCESS)return; //AdmFilterMgr.NO_ACCESS
        }

        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        Element events=null;
        Element event=null;

        Element webSite=addNode("node",tm.getId(),tm.getTitle(),root);
        webSite.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebSitesInfo").getUrl()+"?tm="+tm.getId());
        webSite.setAttribute("target","work");
        webSite.setAttribute("reload","getWebSite."+tm.getId());
        webSite.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_WebSiteInfo").getUrl()+"?tm="+tm.getId());
        webSite.setAttribute("vtarget","info");
        webSite.setAttribute("access",""+access);


        menu=addNode("menu","menu","Menu",webSite);
        option=MenuElement.edit(menu,"WBAd_mnui_WebSiteEdit","WBAd_sysi_WebSitesInfo",null,user,tm.getId(),null);
        //option=addNode("option","edit_site",tma.getWebPage("WBAd_mnui_WebSiteEdit").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebSitesInfo").getUrl()+"?tm="+tm.getId()+"&act=edit");
        //option.setAttribute("target","work");
        if(tm.isActive())
        {
            webSite.setAttribute("icon","sitev");
            option=MenuElement.unactive(menu,"WBAd_mnui_WebSiteActive","WBAd_inti_StatusWebSite",null,user,tm.getId(),null);
            //option=addNode("option","deactivate_site",getVariantName("WBAd_mnui_WebSiteActive",user),menu);
            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebSitesInfo").getUrl()+"?tm="+tm.getId()+"&act=unactive&status=true");
            //option.setAttribute("target","status");
        }
        else
        {
            webSite.setAttribute("icon","siter");
            option=MenuElement.active(menu,"WBAd_mnui_WebSiteActive","WBAd_inti_StatusWebSite",null,user,tm.getId(),null);
            //option=addNode("option","activate_site",tma.getWebPage("WBAd_mnui_WebSiteActive").getDisplayName(user.getLanguage()),menu);
            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebSitesInfo").getUrl()+"?tm="+tm.getId()+"&act=active&status=true");
            //option.setAttribute("target","status");
        }
        //menu
        addSeparator(menu);
        option=MenuElement.remove(menu,"WBAd_mnui_WebSiteRemove","WBAd_inti_StatusWebSite",null,user,"WBAd_mnui_WebSite",tm.getId(),null);
        //option=addNode("option","remove_site",tma.getWebPage("WBAd_mnui_WebSiteRemove").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebSitesInfo").getUrl()+"?tm="+tm.getId()+"&act=remove&status=true");
        //option.setAttribute("target","status");
        //option.setAttribute("confirm",getConfirm("WBAd_mnui_WebSite",user));
        //option.setAttribute("shortCut","DELETE");
        addSeparator(menu);
        option=MenuElement.addOption(menu,"WBAd_mnui_Trash","WBAd_sysi_TrashWebPages",null,user,null, tm.getId(),null);
        if(option!=null)option.setAttribute("icon","trash");
        //option=addNode("option","trash",tma.getWebPage("WBAd_sys_Trash").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TrashWebPages").getUrl()+"?tm="+tm.getId());
        //option.setAttribute("target","work");
        option=MenuElement.addOption(menu,"WBAd_mnui_ResourceTypes","WBAd_sys_ResourceCatalogInfo",null,user,null,tm.getId(),null);
        if(option!=null)option.setAttribute("icon","catalog");
        //option=addNode("option","resourceTypes",tma.getWebPage("WBAd_sys_ResourceCatalog").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sys_ResourceCatalog").getUrl()+"?tm="+tm.getId());
        //option.setAttribute("target","work");
        addSeparator(menu);
        addOptRefresh(menu,user);



        if(loadChild)
        {
//            //Templates
//            addTemplates(user, tm,WebSite, access);
//
//            //Rules
//            addRules(user, tm,WebSite, access);
//
//            //Flows
//            addFlows(user, tm,WebSite, access);
//
//            //Languages
//            addLanguages(user, tm,WebSite, access);
//
//            //Metadata
//            addMDTables(user, tm,WebSite, access);
//
//            //DNS
//            addDnss(user, tm,WebSite, access);
//
//            //Estrategias
//            addAdvResources(user, tm,WebSite, access);
//
//            //Sistema
//            addSysResources(user, tm,WebSite, access);
//
//            //Contenido
//            if(isFilter)addCntResources(user, tm,WebSite, access);
//
//            //Camps
//            addCamps(user, tm, WebSite, access);

            Iterator itex=ext.iterator();
            while(itex.hasNext())
            {
                SWBTreeExt e=(SWBTreeExt)itex.next();
                e.addWebSite(user, webSite, tm, access, isFilter);
            }

            //Home
            Iterator it=tm.listWebPages(); //TODO: AdmFilterMgr.getInstance().getWebPages(user, tm.getId()).iterator();
            while(it.hasNext())
            {
                WebPage tp=(WebPage)it.next();
                //com.infotec.WebSites.WebPage tp=tm.getHome();
                try
                {
                    addWebPage(user, tp, webSite);
                }catch(Exception e){log.error(e);}
            }
        }else
        {
                Element dummy=addNode("node","dummy","dummy",webSite);
                dummy.setAttribute("reload","getResourceSubType");

                //events
                events=addNode("events","events","Events",webSite);
                event=addNode("willExpand","willExpand","WillExpand",events);
                event.setAttribute("action","reload");
        }

    }

    /**
     *
     * @param user
     * @param tp
     * @param res
     */
    protected void addWebPage(User user, WebPage tp, Element res)
    {
        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        Element events=null;
        Element event=null;

        Element WebPage=addNode("node",tp.getId(),tp.getDisplayName(user.getLanguage()),res);
        WebPage.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebPagesInfo").getUrl(tp));
        WebPage.setAttribute("target","work");
        WebPage.setAttribute("reload","getWebPage."+tp.getWebSiteId()+"."+tp.getId());
        WebPage.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_WebPageInfo").getUrl(tp));
        WebPage.setAttribute("vtarget","info");
        WebPage.setAttribute("dragEnabled","true");
        WebPage.setAttribute("dragValue","<a href=\""+tp.getUrl()+"\">"+tp.getDisplayName(user.getLanguage())+"</a>");

        //Menu
        menu=addNode("menu","menu","Menu",WebPage);
        option=MenuElement.add(menu,"WBAd_mnui_WebPageCreate","WBAd_sysi_WebPagesInfo",tp,user,null,null);
        //option=addNode("option","add_WebPage",tma.getWebPage("WBAd_mnui_WebPageCreate").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebPagesInfo").getUrl(tp)+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add");
        //option.setAttribute("target","work");
        addSeparator(menu);
        option=MenuElement.edit(menu,"WBAd_mnui_WebPageEdit","WBAd_sysi_WebPagesInfo",tp,user,null,null);
        //option=addNode("option","edit_WebPage",tma.getWebPage("WBAd_mnui_WebPageEdit").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebPagesInfo").getUrl(tp)+"?act=edit");
        //option.setAttribute("target","work");
        if(tp.isActive())
        {
            option=MenuElement.unactive(menu,"WBAd_mnui_WebPageActive","WBAd_inti_StatusWebPage",tp,user,null,null);
            //option=addNode("option","unactive_WebPage",getVariantName("WBAd_mnui_WebPageActive",user),menu);
            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebPagesInfo").getUrl(tp)+"?act=unactive&status=true");
            //option.setAttribute("target","status");
            if(tp==tp.getWebSite().getHomePage())
            {
                WebPage.setAttribute("icon","homev");
            }
            else
            {
                WebPage.setAttribute("icon","hijov");
            }
        }
        else
        {
            option=MenuElement.active(menu,"WBAd_mnui_WebPageActive","WBAd_inti_StatusWebPage",tp,user,null,null);
            //option=addNode("option","active_WebPage",tma.getWebPage("WBAd_mnui_WebPageActive").getDisplayName(user.getLanguage()),menu);
            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebPagesInfo").getUrl(tp)+"?act=active&status=true");
            //option.setAttribute("target","status");
            if(tp==tp.getWebSite().getHomePage())
            {
                WebPage.setAttribute("icon","homer");
            }
            else
            {
                WebPage.setAttribute("icon","hijor");
            }
        }

        if(tp!=tp.getWebSite().getHomePage())
        {
            addSeparator(menu);
            option=MenuElement.copy(menu,"WBAd_mnui_WebPageCopy","WBAd_sysi_WebPagesInfo",tp,user,"WBAd_mnui_WebPage",null,null,null,"work");
            addSeparator(menu);
            option=MenuElement.remove(menu,"WBAd_mnui_WebPageRemove","WBAd_inti_StatusWebPage",tp,user,"WBAd_mnui_WebPage",null,null);
            //option=addNode("option","remove_section",tma.getWebPage("WBAd_mnui_WebPageRemove").getDisplayName(user.getLanguage()),menu);
            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebPagesInfo").getUrl(tp)+"?act=remove&status=true");
            //option.setAttribute("target","status");
            //option.setAttribute("confirm",getConfirm("WBAd_mnui_WebPage",user));
            //option.setAttribute("icon","sitev");
            //option.setAttribute("shortCut","DELETE");
        }

        addSeparator(menu);
        addOptRefresh(menu,user);

        //child
        Iterator<WebPage> it=tp.listChilds(user.getLanguage(), true, false, false, true);  //tp.getSortChild(false);
        while(it.hasNext())
        {
            WebPage tp2=it.next();
            Element child=addNode("node",tp2.getId(),tp2.getDisplayName(user.getLanguage()),WebPage);
            child.setAttribute("reload","getWebPage."+tp2.getWebSiteId()+"."+tp2.getId());
            child.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_WebPageInfo").getUrl(tp2));
            child.setAttribute("vtarget","info");
            child.setAttribute("dragEnabled","true");
            child.setAttribute("dragValue","<a href=\""+tp2.getUrl()+"\">"+tp2.getDisplayName(user.getLanguage())+"</a>");

            if(tp2.getParent()!=tp) //virtual
            {
                child.setAttribute("action","gotonode.WebPage."+tp2.getWebSiteId()+"."+tp2.getId());
                child.setAttribute("icon","virtual");
                child.setAttribute("alt","Virtual Section");
            }else
            {
                //Menu
                menu=addNode("menu","menu","Menu",child);
                option=MenuElement.add(menu,"WBAd_mnui_WebPageCreate","WBAd_sysi_WebPagesInfo",tp2,user,null,null);
                //option=addNode("option","add_WebPage",tma.getWebPage("WBAd_mnui_WebPageCreate").getDisplayName(user.getLanguage()),menu);
                //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebPagesInfo").getUrl(tp2)+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add");
                //option.setAttribute("target","work");
                addSeparator(menu);
                option=MenuElement.edit(menu,"WBAd_mnui_WebPageEdit","WBAd_sysi_WebPagesInfo",tp2,user,null,null);
                //option=addNode("option","edit_WebPage",tma.getWebPage("WBAd_mnui_WebPageEdit").getDisplayName(user.getLanguage()),menu);
                //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebPagesInfo").getUrl(tp2)+"?act=edit");
                //option.setAttribute("target","work");
                if(tp2.isActive())
                {
                    child.setAttribute("icon","hijov");
                    option=MenuElement.unactive(menu,"WBAd_mnui_WebPageActive","WBAd_inti_StatusWebPage",tp2,user,null,null);
                    //option=addNode("option","unactive_WebPage",getVariantName("WBAd_mnui_WebPageActive",user),menu);
                    //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebPagesInfo").getUrl(tp2)+"?act=unactive&status=true");
                    //option.setAttribute("target","status");
                }
                else
                {
                    child.setAttribute("icon","hijor");
                    option=MenuElement.active(menu,"WBAd_mnui_WebPageActive","WBAd_inti_StatusWebPage",tp2,user,null,null);
                    //option=addNode("option","active_WebPage",tma.getWebPage("WBAd_mnui_WebPageActive").getDisplayName(user.getLanguage()),menu);
                    //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebPagesInfo").getUrl(tp2)+"?act=active&status=true");
                    //option.setAttribute("target","status");
                }
                addSeparator(menu);
                option=MenuElement.copy(menu,"WBAd_mnui_WebPageCopy","WBAd_sysi_WebPagesInfo",tp2,user,"WBAd_mnui_WebPage",null,null,null,"work");
                addSeparator(menu);
                option=MenuElement.remove(menu,"WBAd_mnui_WebPageRemove","WBAd_inti_StatusWebPage",tp2,user,"WBAd_mnui_WebPage",null,null);
                //option=addNode("option","remove_section",tma.getWebPage("WBAd_mnui_WebPageRemove").getDisplayName(user.getLanguage()),menu);
                //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebPagesInfo").getUrl(tp2)+"?act=remove&status=true");
                //option.setAttribute("target","status");
                //option.setAttribute("confirm",getConfirm("WBAd_mnui_WebPage",user));
                //option.setAttribute("shortCut","DELETE");
                addSeparator(menu);
                addOptRefresh(menu,user);

                //have child
                Iterator<WebPage> it2=tp2.listChilds(user.getLanguage(), true, false, false, true);//tp2.getSortChild(false);
                if(it2.hasNext())
                {
                    WebPage tp3=it2.next();
                    Element child2=addNode("node",tp3.getId(),tp3.getDisplayName(user.getLanguage()),child);
                }else
                {
                    child.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_WebPagesInfo").getUrl(tp2));
                    child.setAttribute("target","work");
                }

                //events
                events=addNode("events","events","Events",child);
                event=addNode("willExpand","willExpand","WillExpand",events);
                event.setAttribute("action","reload");
            }

        }
    }

    /**
     *
     * @param user
     * @param rec
     * @param res
     */
//    protected void addTemplate(User user, Template rec, Element res)
//    {
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        Element dev=addNode("node",""+rec.getId(),rec.getTitle(),res);
//        dev.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TemplatesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId());
//        dev.setAttribute("target","work");
//        dev.setAttribute("reload","getTemplate."+rec.getId()+"."+rec.getWebSiteId());
//        dev.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_TemplatesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId());
//        dev.setAttribute("vtarget","info");
//
//
//        //menu
//        menu=addNode("menu","menu","Menu",dev);
//        option=MenuElement.edit(menu,"WBAd_mnui_TemplateEdit","WBAd_sysi_TemplatesInfo",null,user,rec.getWebSiteId(),""+rec.getId());
//        //option=addNode("option","edit_template",tma.getWebPage("WBAd_mnui_TemplateEdit").getDisplayName(user.getLanguage()),menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TemplatesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=edit");
//        //option.setAttribute("target","work");
//        if(rec.getActive()==1)
//        {
//            option=MenuElement.unactive(menu,"WBAd_mnui_TemplateActive","WBAd_inti_StatusTemplate",null,user,rec.getWebSiteId(),""+rec.getId());
//            //option=addNode("option","unactive_template",getVariantName("WBAd_mnui_TemplateActive",user),menu);
//            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TemplatesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=unactive&status=true");
//            //option.setAttribute("target","status");
//            dev.setAttribute("icon","templatev");
//        }
//        else
//        {
//            option=MenuElement.active(menu,"WBAd_mnui_TemplateActive","WBAd_inti_StatusTemplate",null,user,rec.getWebSiteId(),""+rec.getId());
//            //option=addNode("option","active_template",tma.getWebPage("WBAd_mnui_TemplateActive").getDisplayName(user.getLanguage()),menu);
//            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TemplatesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=active&status=true");
//            //option.setAttribute("target","status");
//            dev.setAttribute("icon","templater");
//        }
//        addSeparator(menu);
//        option=MenuElement.copy(menu,"WBAd_mnui_TemplateCopy","WBAd_inti_StatusTemplate",null,user,"WBAd_mnui_Template",rec.getWebSiteId(),""+rec.getId());
//        addSeparator(menu);
//        option=MenuElement.remove(menu,"WBAd_mnui_TemplateRemove","WBAd_inti_StatusTemplate",null,user,"WBAd_mnui_Template",rec.getWebSiteId(),""+rec.getId());
//        //option=addNode("option","remove_template",tma.getWebPage("WBAd_mnui_TemplateRemove").getDisplayName(user.getLanguage()),menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TemplatesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=remove&status=true");
//        //option.setAttribute("target","status");
//        //option.setAttribute("confirm",getConfirm("WBAd_mnui_Template",user));
//        //option.setAttribute("shortCut","DELETE");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//    }

    /**
     *
     * @param user
     * @param rec
     * @param res
     * @param access
     */
//    protected void addCamp(User user, Camp rec, Element res, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2Camp(user, rec.getWebSiteId(), rec.getId());
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        String title=rec.getTitle();
//        if(rec.getId()==1)
//        {
//            title=tma.getWebPage("WBAd_glos_defect").getDisplayName(user.getLanguage());
//        }else if(rec.getId()==2)
//        {
//            title=tma.getWebPage("WBAd_glos_priority").getDisplayName(user.getLanguage());
//        }
//
//        Element dev=addNode("node",""+rec.getId(),title,res);
//        dev.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_CampsInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId());
//        dev.setAttribute("target","work");
//        dev.setAttribute("reload","getCamp."+rec.getId()+"."+rec.getWebSiteId());
//        dev.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_CampInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId());
//        dev.setAttribute("vtarget","info");
//        dev.setAttribute("icon","campv");
//        dev.setAttribute("access",""+access);
//
//        menu=addNode("menu","menu","Menu",dev);
//        if(rec.getId()>2)
//        {
//            //menu
//            option=MenuElement.add(menu,"WBAd_mnui_CampAdd","WBAd_sysi_CampsInfo",null,user,rec.getWebSiteId(),null,"&grpid="+rec.getId());
//            //option=addNode("option","add_template",tma.getWebPage("WBAd_mnui_TemplateAdd").getDisplayName(user.getLanguage()),menu);
//            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TemplatesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&grpid="+rec.getId()+"&tm="+rec.getWebSiteId());
//            //option.setAttribute("target","work");
//            addSeparator(menu);
//            option=MenuElement.edit(menu,"WBAd_mnui_CampEdit","WBAd_sysi_CampsInfo",null,user,rec.getWebSiteId(),""+rec.getId());
//            //option=addNode("option","edit_grptemplate",tma.getWebPage("WBAd_mnui_TemplateGroupEdit").getDisplayName(user.getLanguage()),menu);
//            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_GrpTemplateInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=edit");
//            //option.setAttribute("target","work");
//
//            if(rec.getActive()==1)
//            {
//                option=MenuElement.unactive(menu,"WBAd_mnui_CampActive","WBAd_inti_StatusCamp",null,user,rec.getWebSiteId(),""+rec.getId());
//                //option=addNode("option","unactive_template",getVariantName("WBAd_mnui_TemplateActive",user),menu);
//                //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TemplatesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=unactive&status=true");
//                //option.setAttribute("target","status");
//                dev.setAttribute("icon","campv");
//            }
//            else
//            {
//                option=MenuElement.active(menu,"WBAd_mnui_CampActive","WBAd_inti_StatusCamp",null,user,rec.getWebSiteId(),""+rec.getId());
//                //option=addNode("option","active_template",tma.getWebPage("WBAd_mnui_TemplateActive").getDisplayName(user.getLanguage()),menu);
//                //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TemplatesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=active&status=true");
//                //option.setAttribute("target","status");
//                dev.setAttribute("icon","campr");
//            }
//
//            addSeparator(menu);
//            option=MenuElement.remove(menu,"WBAd_mnui_CampRemove","WBAd_inti_StatusCamp",null,user,"WBAd_mnui_Camp",rec.getWebSiteId(),""+rec.getId());
//            //option=addNode("option","remove_grptemplate",tma.getWebPage("WBAd_mnui_TemplateGroupRemove").getDisplayName(user.getLanguage()),menu);
//            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_GrpTemplateInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=remove&status=true");
//            //option.setAttribute("target","status");
//            //option.setAttribute("confirm",getConfirm("WBAd_mnui_TemplateGroup",user));
//            //option.setAttribute("shortCut","DELETE");
//            addSeparator(menu);
//        }
//        addOptRefresh(menu,user);
//    }

    /**
     *
     * @param user
     * @param tm
     * @param res
     * @param access
     */
//    protected void addCamps(User user, WebSite tm, Element res, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2Camps(user, tm.getId());
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        String tmsid=tm.getId();
//
//        Element devs=addNode("node","camps",tma.getWebPage("WBAd_sys_Camps").getDisplayName(user.getLanguage()),res);
//        devs.setAttribute("reload","getCamps."+tmsid);
//        devs.setAttribute("icon","camps");
//        devs.setAttribute("access",""+access);
//
//        menu=addNode("menu","menu","Menu",devs);
//        option=MenuElement.add(menu,"WBAd_mnui_CampAdd","WBAd_sysi_CampsInfo",null,user,tmsid,null);
//        //option=addNode("option","add_template",tma.getWebPage("WBAd_mnui_TemplateAdd").getDisplayName(user.getLanguage()),menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TemplatesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&tm="+tmsid);
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        Iterator it=sortCollection(DBCatalogs.getInstance().getCamps(tmsid).values());
//        while(it.hasNext())
//        {
//            Camp rec=(Camp)it.next();
//            addCamp(user, rec, devs, access);
//        }
//    }

    /**
     *
     * @param user
     * @param rec
     * @param res
     * @param access
     */
//    protected void addGrpTemplate(User user, TemplateGroup rec, Element res, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2GrpTemplate(user, rec.getWebSiteId(), rec.getId());
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        Element dev=addNode("node",""+rec.getId(),rec.getTitle(),res);
//        dev.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_GrpTemplateInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId());
//        dev.setAttribute("target","work");
//        dev.setAttribute("reload","getGrpTemplate."+rec.getId()+"."+rec.getWebSiteId());
//        dev.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_GrpTemplateInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId());
//        dev.setAttribute("vtarget","info");
//        dev.setAttribute("icon","folder");
//        dev.setAttribute("access",""+access);
//
//        //menu
//        menu=addNode("menu","menu","Menu",dev);
//        option=MenuElement.add(menu,"WBAd_mnui_TemplateAdd","WBAd_sysi_TemplatesInfo",null,user,rec.getWebSiteId(),null,"&grpid="+rec.getId());
//        //option=addNode("option","add_template",tma.getWebPage("WBAd_mnui_TemplateAdd").getDisplayName(user.getLanguage()),menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TemplatesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&grpid="+rec.getId()+"&tm="+rec.getWebSiteId());
//        //option.setAttribute("target","work");
//        option=MenuElement.edit(menu,"WBAd_mnui_TemplateGroupEdit","WBAd_sysi_GrpTemplateInfo",null,user,rec.getWebSiteId(),""+rec.getId());
//        //option=addNode("option","edit_grptemplate",tma.getWebPage("WBAd_mnui_TemplateGroupEdit").getDisplayName(user.getLanguage()),menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_GrpTemplateInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=edit");
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        option=MenuElement.remove(menu,"WBAd_mnui_TemplateGroupRemove","WBAd_inti_StatusGrpTemplate",null,user,"WBAd_mnui_TemplateGroup",rec.getWebSiteId(),""+rec.getId());
//        //option=addNode("option","remove_grptemplate",tma.getWebPage("WBAd_mnui_TemplateGroupRemove").getDisplayName(user.getLanguage()),menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_GrpTemplateInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=remove&status=true");
//        //option.setAttribute("target","status");
//        //option.setAttribute("confirm",getConfirm("WBAd_mnui_TemplateGroup",user));
//        //option.setAttribute("shortCut","DELETE");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        //borrar esta linea al cambiar grupoi de templates
//        String tm=WebPageMgr.getInstance().getWebSite(rec.getWebSiteId()).getId();
//
//        Iterator it=sortCollection(TemplateMgr.getInstance().getTemplates(tm).values());
//        while(it.hasNext())
//        {
//            Template tpl=(Template)it.next();
//            try
//            {
//                if(tpl.getRecTemplate().getGrpid()==rec.getId() && tpl.getRecTemplate().getDeleted()==0)
//                {
//                    addTemplate(user, tpl.getRecTemplate(), dev);
//                }
//            }catch(Exception e){log.error(e);}
//        }
//    }

    /**
     *
     * @param user
     * @param tm
     * @param res
     * @param access
     */
//    protected void addTemplates(User user, WebSite tm, Element res, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2Templates(user, tm.getId());
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        String tmsid=tm.getId();
//
//        Element devs=addNode("node","templates",tma.getWebPage("WBAd_sys_Templates").getDisplayName(user.getLanguage()),res);
//        devs.setAttribute("reload","getTemplates."+tmsid);
//        devs.setAttribute("icon","templates");
//        devs.setAttribute("access",""+access);
//
//        menu=addNode("menu","menu","Menu",devs);
//        option=MenuElement.add(menu,"WBAd_mnui_TemplateAdd","WBAd_sysi_TemplatesInfo",null,user,tmsid,null);
//        //option=addNode("option","add_template",tma.getWebPage("WBAd_mnui_TemplateAdd").getDisplayName(user.getLanguage()),menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TemplatesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&tm="+tmsid);
//        //option.setAttribute("target","work");
//        option=MenuElement.add(menu,"WBAd_mnui_TemplateGroupAdd","WBAd_sysi_GrpTemplateInfo",null,user,tmsid,null);
//        //option=addNode("option","add_grptemplate",tma.getWebPage("WBAd_mnui_TemplateGroupAdd").getDisplayName(user.getLanguage()),menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_GrpTemplateInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&tm="+tmsid);
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        Iterator itd=sortCollection(DBCatalogs.getInstance().getGrpTemplates(tmsid).values());
//        while(itd.hasNext())
//        {
//            TemplateGroup rec=(TemplateGroup)itd.next();
//            addGrpTemplate(user, rec, devs, access);
//        }
//    }

    /**
     *
     * @param user
     * @param tm
     * @param res
     * @param access
     */
//    protected void addMDTables(User user, WebSite tm, Element res, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2MDTables(user, tm.getId());
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        String tmsid=tm.getId();
//
//        Element devs=addNode("node","mdtables",tma.getWebPage("WBAd_sys_MDTables").getDisplayName(user.getLanguage()),res);
//        devs.setAttribute("reload","getMDTables."+tmsid);
//        devs.setAttribute("icon","metadatas");
//        devs.setAttribute("access",""+access);
//
//        menu=addNode("menu","menu","Menu",devs);
//        option=MenuElement.add(menu,"WBAd_mnui_MDTableAdd","WBAd_sysi_MDTablesInfo",null,user,tmsid,null);
//        //option=addNode("option","add_mdtable","Add MDTable",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_MDTablesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&tm="+tmsid);
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        Iterator itd=sortIterator(DBMetaData.getInstance().getMDTables(tmsid));
//        while(itd.hasNext())
//        {
//            RecMDTable rec=(RecMDTable)itd.next();
//            //if(rec.getWebSiteId()==tmnid)
//            {
//                addMDTable(user, rec, devs);
//            }
//        }
//    }

    /**
     *
     * @param user
     * @param rec
     * @param res
     */
//    protected void addMDTable(User user, RecMDTable rec, Element res)
//    {
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//        WebSite tm=WebPageMgr.getInstance().getWebSite(rec.getWebSiteId());
//
//        Element dev=addNode("node",""+rec.getId(),rec.getName(),res);
//        dev.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_MDTablesInfo").getUrl()+"?id="+rec.getId()+"&tm="+tm.getId());
//        dev.setAttribute("target","work");
//        dev.setAttribute("reload","getMDTable."+rec.getId()+"."+rec.getWebSiteId());
//        dev.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_MDTablesInfo").getUrl()+"?id="+rec.getId()+"&tm="+tm.getId());
//        dev.setAttribute("vtarget","info");
//        dev.setAttribute("icon","metadata");
//
//        //menu
//        menu=addNode("menu","menu","Menu",dev);
//        option=MenuElement.edit(menu,"WBAd_mnui_MDTableEdit","WBAd_sysi_MDTablesInfo",null,user,tm.getId(),""+rec.getId());
//        //option=addNode("option","edit_mdtable","Edit MDTable",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_MDTablesInfo").getUrl()+"?id="+rec.getId()+"&act=edit"+"&tm="+tm.getId());
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        option=MenuElement.remove(menu,"WBAd_mnui_MDTableRemove","WBAd_inti_StatusMDTables",null,user,"WBAd_mnui_MDTable",tm.getId(),""+rec.getId());
//        //option=addNode("option","remove_mdtable","Remove MDTable",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_MDTablesInfo").getUrl()+"?id="+rec.getId()+"&act=remove&status=true"+"&tm="+tm.getId());
//        //option.setAttribute("target","status");
//        //option.setAttribute("confirm","Are you sure of delete the MDTable?");
//        //option.setAttribute("shortCut","DELETE");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//    }

    /**
     *
     * @param user
     * @param rec
     * @param res
     */
//    protected void addLanguage(User user, Language rec, Element res)
//    {
//        if(rec==null)
//        {
//            log.error(new Exception("Language is null..."));
//            return;
//        }
//        WebSite tma=SWBContext.getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//        WebSite tm=rec.getWebSite();
//
//        Element dev=addNode("node",""+rec.getId(),tm.getDisplayTitle(user.getLanguage()),res);
//        dev.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_LasnguagesInfo").getUrl()+"?id="+rec.getId()+"&tm="+tm.getId());
//        dev.setAttribute("target","work");
//        dev.setAttribute("reload","getLanguage."+rec.getId()+"."+rec.getWebSiteId());
//        dev.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_LasnguagesInfo").getUrl()+"?id="+rec.getId()+"&tm="+tm.getId());
//        dev.setAttribute("vtarget","info");
//        dev.setAttribute("icon","language");
//
//
//        //menu
//        menu=addNode("menu","menu","Menu",dev);
//        option=MenuElement.edit(menu,"WBAd_mnui_LanguageEdit","WBAd_sysi_LasnguagesInfo",null,user,tm.getId(),""+rec.getId());
//        //option=addNode("option","edit_language","Edit Language",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_LasnguagesInfo").getUrl()+"?id="+rec.getId()+"&act=edit"+"&tm="+tm.getId());
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        option=MenuElement.remove(menu,"WBAd_mnui_LanguageRemove","WBAd_inti_StatusLanguage",null,user,"WBAd_mnui_Language", tm.getId(),""+rec.getId());
//        //option=addNode("option","remove_language","Remove Language",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_LasnguagesInfo").getUrl()+"?id="+rec.getId()+"&act=remove&status=true"+"&tm="+tm.getId());
//        //option.setAttribute("target","status");
//        //option.setAttribute("confirm","Are you sure of delete the Language?");
//        //option.setAttribute("shortCut","DELETE");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//    }

    /**
     *
     * @param user
     * @param tm
     * @param res
     * @param access
     */
//    protected void addLanguages(User user, WebSite tm,Element res, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2Languages(user, tm.getId());
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        Element ele=addNode("node","languages",tma.getWebPage("WBAd_sys_Languages").getDisplayName(user.getLanguage()),res);
//        ele.setAttribute("reload","getLanguages."+tm.getId());
//        ele.setAttribute("icon","languages");
//        ele.setAttribute("access",""+access);
//
//        menu=addNode("menu","menu","Menu",ele);
//        option=MenuElement.add(menu,"WBAd_mnui_LanguageAdd","WBAd_sysi_LasnguagesInfo",null,user,tm.getId(),null);
//        //option=addNode("option","add_language","Add Languages",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_LasnguagesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&tm="+tm.getId());
//        //option.setAttribute("target","work");
//
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        Iterator itd=sortCollection(DBCatalogs.getInstance().getLanguages(tm.getId()).values());
//        while(itd.hasNext())
//        {
//            Language rec=(Language)itd.next();
//            try
//            {
//                addLanguage(user, rec, ele);
//            }catch(Exception e){log.error(e);}
//        }
//    }


    /**
     *
     * @param user
     * @param rec
     * @param res
     */
//    protected void addDevice(User user, Device rec, Element res)
//    {
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        Element dev=addNode("node",""+rec.getId(),rec.getName(),res);
//        dev.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_DeviceInfo").getUrl()+"?id="+rec.getId());
//        dev.setAttribute("target","work");
//        dev.setAttribute("reload","getDevice."+rec.getId());
//        dev.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_DeviceInfo").getUrl()+"?id="+rec.getId());
//        dev.setAttribute("vtarget","info");
//        dev.setAttribute("icon","device");
//
//        //menu
//        menu=addNode("menu","menu","Menu",dev);
//        option=MenuElement.edit(menu,"WBAd_mnui_DeviceEdit","WBAd_sysi_DeviceInfo",null,user,null,""+rec.getId());
//        //option=addNode("option","edit_device","Edit Device",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_DeviceInfo").getUrl()+"?id="+rec.getId()+"&act=edit");
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        option=MenuElement.remove(menu,"WBAd_mnui_DeviceRemove","WBAd_inti_StatusDevice",null,user,"WBAd_mnui_Device",null,""+rec.getId());
//        //option=addNode("option","remove_device","Remove Device",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_DeviceInfo").getUrl()+"?id="+rec.getId()+"&act=remove&status=true");
//        //option.setAttribute("target","status");
//        //option.setAttribute("confirm","Are you sure of delete the Device?");
//        //option.setAttribute("shortCut","DELETE");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//    }
//
//    /**
//     *
//     * @param user
//     * @param res
//     * @param access
//     */
//    protected void addDevices(User user, Element res, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2Devices(user, WebPageMgr.TM_GLOBAL);
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        Element devs=addNode("node","devices",tma.getWebPage("WBAd_sys_Devices").getDisplayName(user.getLanguage()),res);
//        devs.setAttribute("reload","getDevices");
//        devs.setAttribute("icon","devices");
//        devs.setAttribute("access",""+access);
//
//        menu=addNode("menu","menu","Menu",devs);
//        option=MenuElement.add(menu,"WBAd_mnui_DeviceAdd","WBAd_sysi_DeviceInfo",null,user,null,null);
//        //option=addNode("option","add_device","Add Device",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_DeviceInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add");
//        //option.setAttribute("target","work");
//
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        Iterator itd=sortCollection(DBCatalogs.getInstance().getDevices().values());
//        while(itd.hasNext())
//        {
//            Device rec=(Device)itd.next();
//            addDevice(user, rec, devs);
//        }
//    }
//
//    /**
//     *
//     * @param user
//     * @param rec
//     * @param res
//     */
//    protected void addDns(User user, Dns rec, Element res)
//    {
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        Element dev=addNode("node",""+rec.getId(),rec.getDns(),res);
//        dev.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_DNSInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId());
//        dev.setAttribute("target","work");
//        dev.setAttribute("reload","getDns."+rec.getId()+"."+rec.getWebSiteId());
//        dev.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_DNSInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId());
//        dev.setAttribute("vtarget","info");
//        dev.setAttribute("icon","dns");
//
//        WebSite tm=WebPageMgr.getInstance().getWebSite(rec.getWebSiteId());
//        String tmsid=tm.getId();
//
//        //menu
//        menu=addNode("menu","menu","Menu",dev);
//        option=MenuElement.edit(menu,"WBAd_mnui_DNSEdit","WBAd_sysi_DNSInfo",null,user,tmsid,""+rec.getId());
//        //option=addNode("option","edit_dns","Edit Dns",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_DNSInfo").getUrl()+"?id="+rec.getId()+"&act=edit"+"&tm="+tmsid);
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        option=MenuElement.remove(menu,"WBAd_mnui_DNSRemove","WBAd_inti_StatusDNS",null,user,"WBAd_mnui_DNS",tmsid,""+rec.getId());
//        //option=addNode("option","remove_dns","Remove Dns",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_DNSInfo").getUrl()+"?id="+rec.getId()+"&act=remove&status=true"+"&tm="+tmsid);
//        //option.setAttribute("target","status");
//        //option.setAttribute("confirm","Are you sure of delete the DNS?");
//        //option.setAttribute("shortCut","DELETE");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//    }
//
//    /**
//     *
//     * @param user
//     * @param tm
//     * @param res
//     * @param access
//     */
//    protected void addDnss(User user, WebSite tm, Element res, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2Dnss(user, tm.getId());
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        String tmsid=tm.getId();
//
//        Element devs=addNode("node","dns",tma.getWebPage("WBAd_sys_DNS").getDisplayName(user.getLanguage()),res);
//        devs.setAttribute("reload","getDnss."+tmsid);
//        devs.setAttribute("icon","dnss");
//        devs.setAttribute("access",""+access);
//
//        menu=addNode("menu","menu","Menu",devs);
//        option=MenuElement.add(menu,"WBAd_mnui_DNSAdd","WBAd_sysi_DNSInfo",null,user,tmsid,null);
//        //option=addNode("option","add_dns","Add Dns",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_DNSInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&tm="+tmsid);
//        //option.setAttribute("target","work");
//
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        Iterator itd=sortCollection(DBCatalogs.getInstance().getDnss(tm.getId()).values());
//        while(itd.hasNext())
//        {
//            Dns rec=(RecDns)itd.next();
//            addDns(user, rec, devs);
//        }
//    }
//
//
//    /**
//     *
//     * @param user
//     * @param rec
//     * @param res
//     */
//    protected void addRule(User user, Rule rec, Element res)
//    {
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        Element node=addNode("node",""+rec.getId(),rec.getTitle(),res);
//        node.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_RulesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId());
//        node.setAttribute("target","work");
//        node.setAttribute("reload","getRule."+rec.getId()+"."+rec.getWebSiteId());
//        node.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_RulesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId());
//        node.setAttribute("vtarget","info");
//        node.setAttribute("icon","rule");
//
//        //menu
//        menu=addNode("menu","menu","Menu",node);
//        option=MenuElement.edit(menu,"WBAd_mnui_RuleEdit","WBAd_sysi_RulesInfo",null,user,rec.getWebSiteId(),""+rec.getId());
//        //option=addNode("option","edit_rule","Edit Rule",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_RulesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=edit");
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        option=MenuElement.remove(menu,"WBAd_mnui_RuleRemove","WBAd_inti_StatusRule",null,user,"WBAd_mnui_Rule", rec.getWebSiteId(),""+rec.getId());
//        //option=addNode("option","remove_rule","Remove Rule",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_RulesInfo").getUrl()+"?id="+rec.getId()+"&tm="+rec.getWebSiteId()+"&act=remove&status=true");
//        //option.setAttribute("target","status");
//        //option.setAttribute("confirm","Are you sure of delete the Rule?");
//        //option.setAttribute("shortCut","DELETE");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//    }
//
//    /**
//     *
//     * @param user
//     * @param tm
//     * @param res
//     * @param access
//     */
//    protected void addRules(User user, WebSite tm, Element res, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2Rules(user, tm.getId());
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        String tmsid=tm.getId();
//
//        Element rules=addNode("node","rules",tma.getWebPage("WBAd_sys_Rules").getDisplayName(user.getLanguage()),res);
//        rules.setAttribute("reload","getRules."+tmsid);
//        rules.setAttribute("icon","rules");
//        rules.setAttribute("access",""+access);
//
//        menu=addNode("menu","menu","Menu",rules);
//        option=MenuElement.add(menu,"WBAd_mnui_RuleAdd","WBAd_sysi_RulesInfo",null,user,tmsid,null);
//        //option=addNode("option","add_rule","Add Rule",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_RulesInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&tm="+tmsid);
//        //option.setAttribute("target","work");
//
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        Iterator it=sortEnumeration(DBRule.getInstance().getRules(tm.getId()));
//        while(it.hasNext())
//        {
//            RecRule rec=(RecRule)it.next();
//            addRule(user, rec, rules);
//        }
//    }
//
//    /**
//     *
//     * @param user
//     * @param tm
//     * @param rec
//     * @param res
//     */
//    protected void addFlow(User user, WebSite tm,PFlow rec, Element res)
//    {
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        Element node=addNode("node",""+rec.getId(),rec.getTitle(),res);
//        node.setAttribute("action","showurl="+tma.getWebPage("WBAd_sys_FlowsInfo").getUrl()+"?id="+rec.getId()+"&tm="+tm.getId());
//        node.setAttribute("target","work");
//        node.setAttribute("reload","getFlow."+rec.getId()+"."+tm.getId());
//        node.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_FlowsInfo").getUrl()+"?id="+rec.getId()+"&tm="+tm.getId());
//        node.setAttribute("vtarget","info");
//        node.setAttribute("icon","flow");
//
//        //menu
//        menu=addNode("menu","menu","Menu",node);
//        option=MenuElement.edit(menu,"WBAd_mnui_PFlowEdit","WBAd_sys_FlowsInfo",null,user,tm.getId(),""+rec.getId());
//        //option=addNode("option","edit_flow","Edit Publish Flow",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sys_FlowsInfo").getUrl()+"?id="+rec.getId()+"&tm="+tm.getId()+"&act=edit");
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        option=MenuElement.remove(menu,"WBAd_mnui_PFlowRemove","WBAd_inti_StatusPFlow",null,user,"WBAd_mnui_PFlow", tm.getId(),""+rec.getId());
//        //option=addNode("option","remove_flow","Remove Publish Flow",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sys_FlowsInfo").getUrl()+"?id="+rec.getId()+"&tm="+tm.getId()+"&act=remove&status=true");
//        //option.setAttribute("target","status");
//        //option.setAttribute("confirm","Are you sure of delete the Publish Flow?");
//        //option.setAttribute("shortCut","DELETE");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//    }
//
//    /**
//     *
//     * @param user
//     * @param tm
//     * @param res
//     * @param access
//     */
//    protected void addFlows(User user, WebSite tm, Element res, int access)
//    {
//        if(access!=AdmFilterMgr.FULL_ACCESS)
//        {
//            access=AdmFilterMgr.getInstance().haveAccess2PFlows(user, tm.getId());
//            if(access==AdmFilterMgr.NO_ACCESS)return;
//        }
//
//        WebSite tma=WebPageMgr.getInstance().getAdminWebSite();
//        Element menu=null;
//        Element option=null;
//
//        String tmsid=tm.getId();
//
//        Element flows=addNode("node","flows",tma.getWebPage("WBAd_sys_Flows").getDisplayName(user.getLanguage()),res);
//        flows.setAttribute("reload","getFlows."+tmsid);
//        flows.setAttribute("icon","flows");
//        flows.setAttribute("access",""+access);
//
//        menu=addNode("menu","menu","Menu",flows);
//        option=MenuElement.add(menu,"WBAd_mnui_PFlowAdd","WBAd_sys_FlowsInfo",null,user,tmsid,null);
//        //option=addNode("option","add_flow","Add Publish Flow",menu);
//        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sys_FlowsInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add&tm="+tmsid);
//        //option.setAttribute("target","work");
//        addSeparator(menu);
//        addOptRefresh(menu,user);
//
//        Iterator it=sortEnumeration(DBPFlow.getInstance().getPFlows(tm.getId()));
//        while(it.hasNext())
//        {
//            RecPFlow rec=(RecPFlow)it.next();
//            addFlow(user, tm,rec, flows);
//        }
//    }


    /**
     *
     * @param node
     * @param id
     * @param name
     * @param parent
     * @return
     */
    protected Element addNode(String node, String id, String name, Element parent)
    {
        return SWBTreeUtil.addNode(node,id,name,parent);
    }

    /**
     *
     * @param name
     * @param value
     * @param parent
     * @return
     */
    protected Element addElement(String name, String value, Element parent)
    {
        return SWBTreeUtil.addElement(name,value,parent);
    }

    /**
     *
     * @param id
     * @return
     */
    protected Document getError(int id)
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
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noWebSite") + "...", err);
            } else if (id == 6)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noWebPage") + "...", err);
            } else if (id == 7)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_usernopermiss") + "...", err);
            } else if (id == 8)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_WebPageAlreadyexist") + "...", err);
            } else if (id == 9)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_byImplement") + "...", err);
            } else if (id == 10)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_WebSiteAlreadyExist") + "...", err);
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
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        boolean gzip = false;
        if(agzip)
        {
            if(request.getHeader("Via")!=null
            || request.getHeader("X-Forwarded-For")!=null
            || request.getHeader("Cache-Control")!=null)
            {
                //using proxy -> no zip
            }else
            {
                String accept = request.getHeader("Accept-Encoding");
                if (accept != null && accept.toLowerCase().indexOf("gzip") != -1)
                {
                    gzip = true;
                }
            }
        }

        java.util.zip.GZIPOutputStream garr = null;
        PrintWriter out = null;

        if (gzip)
        {
            garr = new java.util.zip.GZIPOutputStream(response.getOutputStream());
            out = new PrintWriter(garr, true);
            response.setHeader("Content-Encoding", "gzip");
        } else
        {
            out = response.getWriter();
        }

        ServletInputStream in = request.getInputStream();
        Document dom = SWBUtils.XML.xmlToDom(in);
        if (!dom.getFirstChild().getNodeName().equals("req"))
        {
            response.sendError(404, request.getRequestURI());
            return;
        }

        //System.out.println(AFUtils.getInstance().DomtoXml(dom));

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
        out.flush();
        out.close();
        //System.out.println(new String(ret.getBytes()));
    }

    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        out.println("<div class=\"applet\">");
        out.println("<APPLET id=\"apptree\" name=\"apptree\" code=\"applets.generictree.GenericTree.class\" codebase=\""+SWBPlatform.getContextPath()+"\" ARCHIVE=\"swbadmin/lib/GenericTree.jar, swbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"100%\">");
        SWBResourceURL url=paramRequest.getRenderUrl();
        url.setMode("gateway");
        url.setCallMethod(url.Call_DIRECT);
        out.println("<PARAM NAME =\"cgipath\" VALUE=\""+url+"\">");
        //out.println("<PARAM NAME =\"jsess\" VALUE=\""+request.getSession().getId()+"\">");
        out.println("</APPLET>");
        out.println("</div>");
    }

    /**
     *
     * @param it
     * @return
     */
    public Iterator sortIterator(Iterator it)
    {
        return SWBTreeUtil.sortIterator(it);
    }

    /**
     *
     * @param en
     * @return
     */
    public Iterator sortEnumeration(Enumeration en)
    {
        return SWBTreeUtil.sortEnumeration(en);
    }

    /**
     *
     * @param collection
     * @return
     */
    public Iterator sortCollection(Collection collection)
    {
        return SWBTreeUtil.sortCollection(collection);
    }

}