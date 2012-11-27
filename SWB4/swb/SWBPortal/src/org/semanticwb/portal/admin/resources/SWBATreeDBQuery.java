/*
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
 */
package org.semanticwb.portal.admin.resources;

/*
 * WBTreeDBQuery.java
 *
 * Created on Febrero 2, 2006
 */




import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;
import org.semanticwb.*;
import org.semanticwb.base.db.DBConnectionPool;
import org.semanticwb.model.*;
import org.semanticwb.portal.admin.resources.wbtree.SWBTreeExt;
import org.semanticwb.portal.admin.resources.wbtree.SWBTreeUtil;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * Recurso para la administraci�n de WebBuilder que muestra el �rbol con el pool
 * de conexiones configuradas en webbuilder, que muestran las tablas de las bases de datos con sus columnas.
 *
 * Connection poll Tree availables on webbuilder configuration, that show the existing tables and columns.
 *
 * @author Juan Antonio Fernandez
 */
public class SWBATreeDBQuery extends GenericResource
{
    
    /** The Constant STATUS_TOPIC. */
    static final String STATUS_TOPIC="WBAd_mnui_DBQuery"; //   ////WBAd_inti_DBQuery////
    
    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBATreeDBQuery.class);
    
    /** The ext. */
    ArrayList ext=new ArrayList();
    
    /** The agzip. */
    boolean agzip=true;
    
    /** The hmoper. */
    HashMap hmoper=null;
    //public static final String WBGLOBAL="WBGlobal";
    /** The Constant WBADMIN. */
    public static final String WBADMIN=SWBContext.WEBSITE_ADMIN;

    //TODO:Provicional hasta que este AdmFilterMgr
    /** The Constant NO_ACCESS. */
    public static final int NO_ACCESS = 0;
    
    /** The Constant PARCIAL_ACCESS. */
    public static final int PARCIAL_ACCESS = 1;
    
    /** The Constant FULL_ACCESS. */
    public static final int FULL_ACCESS = 2;

    
    /**
     * Creates a new instance of WBTree.
     */
    public SWBATreeDBQuery()
    {
        agzip = SWBPlatform.getEnv("wb/responseGZIPEncoding","true").equalsIgnoreCase("true");
        try
        {
            BufferedReader in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/org/semanticwb/portal/admin/resources/SWBATree.properties")));            String str=null;
            while((str=in.readLine())!=null)
            {
                try
                {
                    if(!str.startsWith("#"))
                    {
                        Class cls=Class.forName(str);
                        ext.add(cls.newInstance());
                    }
                }
                catch(Exception e)
                {log.error(e);}
            }
        }
        catch(Exception e)
        {log.error(e);}
        
    }

    /**
     * Process request.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        if(paramRequest.getMode().equals("gateway"))
        {
            doGateway(request,response,paramRequest);
        }
        else super.processRequest(request,response,paramRequest);
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
     * @return
     */
    public Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {
        if (cmd.equals("initTree"))
        {
            return initTree(user, src);
        }
        else
        {
            return getDocument(user, src, cmd);
        }
    }
    
    /**
     * Inits the tree.
     * 
     * @param user the user
     * @param src the src
     * @return the document
     * @return
     */
    public Document initTree(User user, Document src)
    {
        return initTree(user, src, false);
    }
    
    /**
     * Inits the tree.
     * 
     * @param user the user
     * @param src the src
     * @param isFilter the is filter
     * @return the document
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
            icon.setAttribute("path","images/icon-sitioa.gif");
            icon=addNode("icon","siter","Site",icons);
            icon.setAttribute("path","images/icon-sitior.gif");
            icon=addNode("icon","hijor","Topic",icons);
            icon.setAttribute("path","images/icon-pagwebr.gif");
            icon=addNode("icon","hijov","Topic",icons);
            icon.setAttribute("path","images/icon-pagweba.gif");
            icon=addNode("icon","homer","Topic",icons);
            icon.setAttribute("path","images/i_home_rojo.gif");
            icon=addNode("icon","homev","Topic",icons);
            icon.setAttribute("path","images/i_home_verde.gif");
            icon=addNode("icon","virtual","Topic",icons);
            icon.setAttribute("path","images/icon-pagweba.gif");
            
            icon=addNode("icon","folder","Folder",icons);
            icon.setAttribute("path","images/icon-foldera.gif");
            icon=addNode("icon","root","Root",icons);
            icon.setAttribute("path","images/icon-servera.gif");
            icon=addNode("icon","global","Global",icons);
            icon.setAttribute("path","images/icon-servera.gif");
            icon=addNode("icon","flows","Flows",icons);
            icon.setAttribute("path","images/ico_virtual.gif");
            icon=addNode("icon","flow","Flow",icons);
            icon.setAttribute("path","images/ico_virtual.gif");
            
            Iterator it=ext.iterator();
            while(it.hasNext())
            {
                SWBTreeExt e=(SWBTreeExt)it.next();
                e.initTree(user, res, isFilter);
            }
            
            addServer(user, res);
            
        }
        catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
    }
    
    /**
     * Inits the tree filter.
     * 
     * @param user the user
     * @param src the src
     * @return the document
     * @return
     */
    public Document initTreeFilter(User user, Document src)
    {
        return initTree(user, src,true);
    }
    
    /**
     * Gets the document.
     * 
     * @param user the user
     * @param src the src
     * @param act the act
     * @return the document
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
                String ids = new String("");
                
                cmd=act.substring(0,ind);
                id=act.substring(ind+1);
                //System.out.println(cmd+":"+id);
            }
            else cmd=act;
            
            if(cmd.equals("getServer"))
            {
                addServer(user, res);
            }
            else if (cmd.equals("getPool"))
            {
                String dbcon = id;
                //TODO: AdmFilterMgr.PARCIAL_ACCESS; addPoolConn(user, dbcon, res, AdmFilterMgr.PARCIAL_ACCESS,true);
                addPoolConn(user, dbcon, res, PARCIAL_ACCESS,true); //AdmFilterMgr.PARCIAL_ACCESS
            }
            else if (cmd.equals("getTable"))
            {
                String dbcon=id.substring(0,id.indexOf('.'));
                String tablename=id.substring(id.indexOf('.')+1);
                
                addTable(user, tablename, res,dbcon);
            }
            else if (cmd.equals("getColumn"))
            {
                String dbcon=id.substring(0,id.indexOf('.'));
                String tablename=id.substring(id.indexOf('.')+1,id.lastIndexOf('.'));
                String columnname=id.substring(id.lastIndexOf('.')+1);
                addColumn(user, columnname, res, dbcon, tablename);
            }
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
        }
        catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
    }
    
    /**
     * Adds the opt refresh.
     * 
     * @param menu the menu
     * @param user the user
     */
    private void addOptRefresh(Element menu, User user)
    {
        //SWBTreeUtil.addOptRefresh(menu,user);
    }
    
    /**
     * Adds the separator.
     * 
     * @param menu the menu
     */
    private void addSeparator(Element menu)
    {
        SWBTreeUtil.addSeparator(menu);
    }
    
    /**
     * Adds the server.
     * 
     * @param user the user
     * @param res the res
     */
    protected void addServer(User user, Element res)
    {
        //TODO: int access=AdmFilterMgr.getInstance().haveAccess2Server(user);
        int access=1; //modificar
        
        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        Element events=null;
        Element event=null;
        
        //tree nodes
        Element root=addNode("node","server","Connection Pool",res);
        root.setAttribute("reload","getServer");
        root.setAttribute("icon","root");
        root.setAttribute("access",""+access);
        menu=addNode("menu","menu","Menu",root);
        addSeparator(menu);
        addOptRefresh(menu,user);
        
        /// Pool de conexiones
        Enumeration<DBConnectionPool> en=SWBUtils.DB.getPools();
        while(en.hasMoreElements())
        {
            DBConnectionPool pool = en.nextElement();
            String name=pool.getName();
            addPoolConn(user, name, root, access);
        }
        
    }
    
    
    
    /**
     * Adds the pool conn.
     * 
     * @param user the user
     * @param dbcon the dbcon
     * @param root the root
     * @param access the access
     */
    protected void addPoolConn(User user, String dbcon, Element root, int access)
    {
        addPoolConn(user, dbcon, root, access, false);
    }
    
    /**
     * Adds the pool conn.
     * 
     * @param user the user
     * @param dbcon the dbcon
     * @param root the root
     * @param access the access
     * @param loadChild the load child
     */
    protected void addPoolConn(User user, String dbcon, Element root, int access, boolean loadChild)
    {
        //TODO: AdmFilterMgr
        //        if(access!=AdmFilterMgr.FULL_ACCESS)
        //        {
        //            access=AdmFilterMgr.getInstance().haveAccess2TopicMap(user, tm.getId());
        //            if(access==AdmFilterMgr.NO_ACCESS)return;
        //        }
        
        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        Element events=null;
        Element event=null;
        
        Element poolconn=addNode("node",dbcon,dbcon,root);
        poolconn.setAttribute("view","showurl=javascript:upd('"+dbcon+"');");//+tma.getWebPage(STATUS_TOPIC).getUrl()+"?pool="+dbcon+"&act=jsupdpool&status=true"
//        poolconn.setAttribute("vtarget","");//status
        poolconn.setAttribute("reload","getPool."+dbcon);
//                poolconn.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_TopicMapInfo").getUrl()+"?tm="+dbcon);
        //poolconn.setAttribute("vtarget","info");
        poolconn.setAttribute("access",""+access);
        
        
        menu=addNode("menu","menu","Menu",poolconn);
        
        poolconn.setAttribute("icon","folder");
        
        addSeparator(menu);
        addOptRefresh(menu,user);
        
        
        // Tables
        
        String [] as =
        { "TABLE" } ;
        try
        {
            Connection conn = SWBUtils.DB.getConnection(dbcon, "SWBATreeDBQuery.addPoolConn");
            if(loadChild)
            {
                ResultSet rstable= conn.getMetaData().getTables(null, null, null, as);
                
                while(rstable.next())
                {
                    addTable(user, rstable.getString("TABLE_NAME"), poolconn, dbcon);
                }
                rstable.close();
            }
            else
            {
                if(conn!=null)
                {
                    Element dummy=addNode("node","dummy","dummy",poolconn);
                }
                
                //events
                events=addNode("events","events","Events",poolconn);
                event=addNode("willExpand","willExpand","WillExpand",events);
                event.setAttribute("action","reload");
            }
            
            if(conn!=null)conn.close();
        }
        catch(Exception e)
        {
            log.error("Error while trying to load tables. ",e);
        }
        
    }
    
    /**
     * Adds the table.
     * 
     * @param user the user
     * @param tablename the tablename
     * @param res the res
     * @param dbcon the dbcon
     */
    protected void addTable(User user, String tablename, Element res, String dbcon)
    {
        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        Element events=null;
        Element event=null;
        
        Element table=addNode("node",tablename,tablename,res);
        table.setAttribute("view","showurl=javascript:upd('"+dbcon+"');"); //+tma.getWebPage(STATUS_TOPIC).getUrl()+"?pool="+dbcon+"&act=jsupdpool&status=true&table="+tablename
        //table.setAttribute("vtarget","");//status
        table.setAttribute("reload","getTable."+dbcon+"."+tablename);
        //        topic.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_TopicInfo").getUrl(tp));
        //topic.setAttribute("vtarget","info");
        table.setAttribute("dragEnabled","true");
        table.setAttribute("dragValue","Select * from "+tablename+"");
        
        //Menu
        menu=addNode("menu","menu","Menu",table);
        
        if(hmoper.size()>0)
        {
            Iterator iteOper = hmoper.keySet().iterator();
            while(iteOper.hasNext())
            {
                String thisToken = (String) iteOper.next();
                if(!"create".equals(thisToken.toLowerCase()))
                {
                    option=addNode("option",thisToken,thisToken,menu);
                    option.setAttribute("action","showurl=javascript:mnuexample('"+dbcon+"','"+thisToken+"','"+tablename+"');"); //+tma.getWebPage(STATUS_TOPIC).getUrl()+"?pool="+dbcon+"&act=jsupdpool&status=true&table="+tablename+"&cmd="+thisToken
//                    option.setAttribute("target","");//status
                }
                
            }
        }
        
        
        
        table.setAttribute("icon","virtual");
        
        
        
        addSeparator(menu);
        addOptRefresh(menu,user);
        
        
        //add primary keys table
        Element  primaryKeys=addNode("node","PK_"+dbcon+tablename,"Primary Keys",table);
        primaryKeys.setAttribute("icon","sitev");
        try
        {
            Connection conn = SWBUtils.DB.getConnection(dbcon,"SWBATreeDBQuery.addTable(PK)");
            ResultSet rsColumn = conn.getMetaData().getPrimaryKeys(null,  null, tablename);
            
            while(rsColumn.next())
            {
                String column = rsColumn.getString(4);
                addColumn(user, rsColumn.getString(4), primaryKeys, dbcon, tablename);
            }
            rsColumn.close();
            conn.close();
        }
        catch(Exception e)
        {
            log.error("Error while trying to load tables. ",e);
        }
        
        // Columnas
        
        try
        {
            Connection conn = SWBUtils.DB.getConnection(dbcon,"SWBATreeDBQuery.addTable(Cols)");
            ResultSet rsColumn = conn.getMetaData().getColumns(null, null, tablename, null);
            
            while(rsColumn.next())
            {
                addColumn(user, rsColumn.getString(4), table, dbcon, tablename);
            }
            rsColumn.close();
            conn.close();
        }
        catch(Exception e)
        {
            log.error("Error while trying to load tables. ",e);
        }
    }
    
    
    
    /**
     * Adds the column.
     * 
     * @param user the user
     * @param columnname the columnname
     * @param res the res
     * @param dbcon the dbcon
     * @param tablename the tablename
     */
    protected void addColumn(User user, String columnname, Element res, String dbcon, String tablename)
    {
        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        
        Element node=addNode("node",""+dbcon+"_"+columnname,columnname,res);
        node.setAttribute("view","showurl=javascript:upd('"+dbcon+"');"); //+tma.getWebPage(STATUS_TOPIC).getUrl()+"?pool="+dbcon+"&act=jsupdpool&status=true"
//        node.setAttribute("vtarget","");//status
        node.setAttribute("reload","getColumn."+dbcon+"."+tablename+"."+columnname);
        //        node.setAttribute("view","showurl="+tma.getWebPage("WBAd_sysi_DBQuery").getUrl()+"?pool="+dbcon+"&act=jsupdpool&status=true&cmd="+thisToken);
        //        node.setAttribute("vtarget","info");
        node.setAttribute("icon","flow");
        
        //menu
        menu=addNode("menu","menu","Menu",node);
        
        addSeparator(menu);
        addOptRefresh(menu,user);
        // add column properties
        
        try
        {
            Connection conn = SWBUtils.DB.getConnection(dbcon,"SWBATreeDBQuery.addColumn()");
            ResultSet rsProp = conn.getMetaData().getColumns(null, null, tablename, null);
            
            while(rsProp.next())
            {
                String column = rsProp.getString(4);
                String [] propsName =
                {
                    "Data type" ,
                    "Type Name" ,
                    "Size" ,
                    "Default value" ,
                    "Allows NULL" };
                String [] props =
                {
                    rsProp.getString(5) ,
                    rsProp.getString(6) ,
                    rsProp.getString(7) ,
                    rsProp.getString(13),
                    rsProp.getString(18) };
                if(columnname.equals(column))
                {
                    for(int j=0;j<props.length;j++)
                    {
                        addProperties(user, propsName[j], node, props[j]);
                    }
                }
            }
            rsProp.close();
            conn.close();
        }
        catch(Exception e)
        {
            log.error("Error while trying to add property to a column. ",e);
        }
        
    }
    
    /**
     * Adds the properties.
     * 
     * @param user the user
     * @param propname the propname
     * @param res the res
     * @param valor the valor
     */
    protected void addProperties(User user, String propname, Element res, String valor)
    {
        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        
        Element node=addNode("node",""+propname+"_"+valor,propname+": "+valor,res);
//                node.setAttribute("action","showurl="+tma.getWebPage("WBAd_sys_FlowsInfo").getUrl()+"?id="+dbcon+"&tm="+columnname);
        //        node.setAttribute("target","work");
        //        node.setAttribute("reload","getFlow."+rec.getId()+"."+tm.getId());
        //        node.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_FlowsInfo").getUrl()+"?id="+dbcon+"&tm="+columnname);
        //        node.setAttribute("vtarget","info");
        node.setAttribute("icon","flow");
        
        
    }
    
    
    /**
     * Adds the node.
     * 
     * @param node the node
     * @param id the id
     * @param name the name
     * @param parent the parent
     * @return the element
     * @return
     */
    protected Element addNode(String node, String id, String name, Element parent)
    {
        return SWBTreeUtil.addNode(node,id,name,parent);
    }
    
    /**
     * Adds the element.
     * 
     * @param name the name
     * @param value the value
     * @param parent the parent
     * @return the element
     * @return
     */
    protected Element addElement(String name, String value, Element parent)
    {
        return SWBTreeUtil.addElement(name,value,parent);
    }
    
    /**
     * Gets the error.
     * 
     * @param id the id
     * @return the error
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
            }
            else if (id == 1)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_nouser") + "...", err);
            }
            else if (id == 2)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noservice") + "...", err);
            }
            else if (id == 3)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_serviceprocessfail") + "...", err);
            }
            else if (id == 4)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_parametersprocessfail") + "...", err);
            }
            else if (id == 5)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopicmap") + "...", err);
            }
            else if (id == 6)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopic") + "...", err);
            }
            else if (id == 7)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_usernopermiss") + "...", err);
            }
            else if (id == 8)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicAlreadyexist") + "...", err);
            }
            else if (id == 9)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_byImplement") + "...", err);
            }
            else if (id == 10)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicMapAlreadyExist") + "...", err);
            }
            else if (id == 11)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_FileNotFound") + "...", err);
            }
            else if (id == 12)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noversions") + "...", err);
            }
            else if (id == 13)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_xmlinconsistencyversion") + "...", err);
            }
            else if (id == 14)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noResourcesinMemory") + "...", err);
            }
            else if (id == 15)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noTemplatesinMemory") + "...", err);
            }
            else if (id == 16)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_TemplatenotRemovedfromFileSystem") + "...", err);
            }
            else if (id == 17)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_adminUsernotCreated") + "...", err);
            }
            else
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_errornotfound") + "...", err);
            }
        }
        catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_documentError") + "...",e);
        }
        
        return dom;
    }
    
    
    /**
     * Do gateway.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
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
            }
            else
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
        }
        else
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
            }
            else
                ret = SWBUtils.XML.domToXml(res, true);
        }
        catch(Exception e)
        {log.error(e);}
        out.print(new String(ret.getBytes()));
        out.flush();
        out.close();
        //System.out.println(new String(ret.getBytes()));
    }

    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        Resource base = getResourceBase();
        String operValidas = SWBPlatform.getEnv("wb/resDBQueryFilter","select");
        hmoper = new HashMap();
        if(operValidas.indexOf(';')>-1||operValidas.indexOf(',')>-1)
        {
            StringTokenizer token = null;
            if(operValidas.indexOf(';')>-1)
            {
                token = new StringTokenizer(operValidas,";");
            }
            else
            {
                token = new StringTokenizer(operValidas,",");
            }
            
            while(token.hasMoreTokens())
            {
                String thisToken = token.nextToken();
                hmoper.put(thisToken.toLowerCase(),thisToken.toLowerCase());
                
            }
        }

        out.println("<script language=\"javascript\">");
        out.println("function upd(connpool)");
        out.println("{");
        out.println("   document.forms['f"+getResourceBase().getId()+"treeDBQuery'].dbcon.value=connpool;"); //poolname
        out.println("   document.forms['f"+getResourceBase().getId()+"treeDBQuery'].poolname.value=connpool;");
        out.println("}");
        out.println("function queryUpd(comm)");
        out.println("{");
        out.println("   document.forms['f"+getResourceBase().getId()+"treeDBQuery'].query.value=comm;");
        out.println("}");
        out.println("function copyQueryVal(sel)");
        out.println("{");
        out.println("   var valor =  sel[sel.selectedIndex].value;");
        out.println("   var connpool = valor.substring(0,valor.indexOf('|')); ");
        out.println("   var strquery = valor.substring(valor.indexOf('|')+1); ");
        out.println("   document.forms['f"+getResourceBase().getId()+"treeDBQuery'].dbcon.value=connpool;");
        out.println("   document.forms['f"+getResourceBase().getId()+"treeDBQuery'].poolname.value=connpool;");
        out.println("   document.forms['f"+getResourceBase().getId()+"treeDBQuery'].query.value=strquery;");
        out.println("}");
        out.println("function mnuexample(pcon, comm,tblname)");
        out.println("{ ");
        out.println(" upd(pcon);");
        out.println(" var sqlcp = '';");
        out.println(" if('drop'==comm)");
        out.println(" {");
        out.println("   sqlcp = 'drop table '+tblname;");
        out.println(" }");
        out.println(" else if('insert'==comm)");
        out.println(" {");
        out.println("   sqlcp = 'insert into '+ tblname + '( [ column name 1], [column name 2], ...) \\n\\r values ( [value for column name 1], [ value for column name 2], ....)';");
        out.println(" }");
        out.println(" else if('delete'==comm)");
        out.println(" {");
        out.println("   sqlcp = 'delete from '+ tblname + ' where column_name=some_value';");
        out.println(" }");
        out.println(" else if('update'==comm)");
        out.println(" {");
        out.println("   sqlcp = 'update '+ tblname + ' set column_name=new_value where column_name=some_value ';");
        out.println(" }");
        out.println(" else if('select'==comm)");
        out.println(" {");
        out.println("   sqlcp = 'select *, column_name(s) from '+ tblname + ' where column_name=some_value order by column_name asc';");
        out.println(" }");
        out.println(" else if('alter'==comm)");
        out.println(" {");
        out.println("   sqlcp = 'alter table '+ tblname + ' ADD [column_name datatype] or DROP COLUMN [column_name] ';");
        out.println(" }");
        out.println(" else ");
        out.println(" {");
        out.println("   sqlcp = 'command: '+comm+ ' ... on table: '+ tblname + ' not supported.';");
        out.println(" }");
        out.println("   queryUpd(sqlcp);");
        out.println("}");
        out.println("</script>");

        
        if(request.getParameter("act")==null)
        {
          
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<table cellpadding=\"5\" cellspacing=\"0\" border=\"0\" width=\"100%\">");
            out.println("<tr><td width=\"200\" valign=\"top\">");
            out.println("<div class=\"applet\">");
            out.println("<applet id=\"apptree\" name=\"apptree\" code=\"applets.generictree.GenericTree.class\" codebase=\""+SWBPlatform.getContextPath()+"/\" ARCHIVE=\"swbadmin/lib/SWBAplGenericTree.jar, swbadmin/lib/SWBAplCommons.jar\" width=\"250\" height=\"380\">");
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode("gateway");
            url.setCallMethod(url.Call_DIRECT);
            out.println("<param name =\"jsess\" value=\""+request.getSession().getId()+"\">");
            out.println("<param name =\"cgipath\" value=\""+url+"\">");
            out.println("</applet>");
            out.println("</div>");
            out.println("</td>");
            
            String query=request.getParameter("query");
            HashMap hmpool = null;
            if(query==null)query="";
            else query=query.trim();
            String dbcon=request.getParameter("dbcon");
            
            Enumeration<DBConnectionPool> en=SWBUtils.DB.getPools();
            while(en.hasMoreElements())
            {
                DBConnectionPool pool = en.nextElement();
                String name=pool.getName();
                if(null==dbcon) dbcon = name;
                
                String [] as =
                { "TABLE" } ;
                try
                {
                    Connection conn = SWBUtils.DB.getConnection(name,"SWBATreeDBQuery.doView()");
                    ResultSet rstable= conn.getMetaData().getTables(null, null, null, as);
                    int orden=0;
                    Vector vctable = new Vector();
                    
                    int numCols = rstable.getMetaData().getColumnCount();
                    
                    while(rstable.next())
                    {
                        vctable.add(orden, rstable.getString("TABLE_NAME"));
                        orden++;
                    }
                    
                    if(null==hmpool) hmpool = new HashMap();
                    hmpool.put(name, vctable);
                    rstable.close();
                    conn.close();
                }
                catch(Exception e)
                {
                    log.error("Error while trying to load tables. ",e);
                }
                
            }
            
            out.println("<td valign=\"top\" align=\"left\">");
            
            
            out.println("<form id=\"f"+getResourceBase().getId()+"treeDBQuery\" name=\"f"+getResourceBase().getId()+"treeDBQuery\"  action=\""+paramRequest.getRenderUrl()+"\" method=\"post\">");
            out.println("<table border=\"0\"cellspacing=\"0\" height=\"100%\" cellpadding=\"5\" width=\"100%\">");
            out.println("<tr><td valign=\"top\">");
            //out.println(paramRequest.getLocaleString("connPool"));
            out.println("Querys utilizados:");
            out.println("</td></tr><tr><td colspan=\"2\">");
            out.println("<select  name=\"querys\" size=\"5\" style=\"width:100%\">");

            HashMap queryList = null;
            if(request.getSession().getAttribute(""+base.getId()+paramRequest.getWebPage().getWebSiteId())!=null)
            {
                queryList = (HashMap)request.getSession().getAttribute(""+base.getId()+paramRequest.getWebPage().getWebSiteId());
                Iterator iteQuery = queryList.keySet().iterator();
                while(iteQuery.hasNext())
                {
                    String thisQuery = (String) iteQuery.next();
                    out.println("<option value=\""+thisQuery+"\">"+thisQuery.substring(thisQuery.indexOf('|')+1)+"</option>");
                }
            }
            else
            {
                queryList = new HashMap();
                String tmpCreate = (String) hmoper.get("create");
                if(null!=tmpCreate)
                {
                    queryList.put(dbcon+"|create table table_name (column_name1 data_type, column_name2 data_type, .....)","create table table_name (column_name1 data_type, column_name2 data_type, .....)");
                    out.println("<option selected value=\""+dbcon+"|create table table_name (column_name1 data_type, column_name2 data_type, .....) \">create table table_name (column_name1 data_type, column_name2 data_type, .....)</option>");
                }
            }
            if(null!=request.getParameter("query"))
            {
                String pQuery = request.getParameter("query");
                
                if(pQuery.trim().length()>0)
                {
                    if(queryList.get(dbcon+"|"+pQuery)==null)
                    {
                        queryList.put(dbcon+"|"+pQuery, pQuery);
                        out.println("<option value=\""+dbcon+"|"+pQuery+"\">"+pQuery+"</option>");
                    }
                }
            }
            
            out.println("</select><br>");
            out.println("<input type=\"button\" name=\"copyQuery\" onclick=\"copyQueryVal(querys);\" value=\"utilizar query\">");
            request.getSession().setAttribute(""+base.getId()+paramRequest.getWebPage().getWebSiteId(), queryList);
            
            out.println("<input type=\"hidden\" name=\"dbcon\" value=\""+dbcon+"\">");
            
            out.println("</td></tr>");
            
            out.println("<tr><td >");
            out.println("Pool: <input type=\"text\" name=\"poolname\" value=\""+dbcon+"\" style=\"border:0\">");
            out.println("</td></tr>");
            out.println("<tr><td>");
            out.println("Query:");
            out.println("</td></tr>");
            out.println("<tr><td>");
            out.print("<textarea name=\"query\" rows=\"5\" style=\"width:100%\">");
            //out.print(query);
            out.println("</textarea>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("<input type=\"submit\" name=\"btnsubmit\" value=\""+paramRequest.getLocaleString("send")+"\">");
            out.println("</form>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</div>");
            
            
            try
            {
                if(query.length()>0)
                {
                    out.println("<script language=\"javascript\">");
                    out.println("function hideDiv(objDIV) { ");
                    out.println("    objDIV.style.visibility = 'hidden'; ");
                    out.println("} ");
                    out.println("");
                    out.println("function showDiv(objDIV) { ");
                    out.println("    objDIV.style.visibility = 'visible'; ");
                    out.println("} ");
                    out.println("</script>");
                    out.println("<div class=\"swbform\">");
                    out.println("<table border=\"0\" width=\"100%\" valign=\"top\" cellpadding=\"5\" cellspacing=\"0\">");
                    out.println("<tr>");
                    out.println("<td>");
                    out.println("<font size=\"3\"><b><i>"+query+"</i></b></font>");
                    out.println("</td>");
                    out.println("</tr>");
                    boolean ejecutar = false;
                    if(hmoper.size()>0)
                    {
                        Iterator iteOper = hmoper.keySet().iterator();
                        while(iteOper.hasNext())
                        {
                            String thisToken = (String) iteOper.next();
                            thisToken = thisToken.toLowerCase();
                            String tmpquery = query.toLowerCase();
                            //query=query.toLowerCase();
                            if(tmpquery.indexOf(thisToken.trim())>-1) ejecutar=true;
                            
                        }
                    }
                    
                    
                    if(ejecutar)
                    {
                        Connection con;
                        if (dbcon != null && dbcon.length() > 0)
                            con = SWBUtils.DB.getConnection(dbcon,"SWBATreeDBQuery");
                        else
                            con = SWBUtils.DB.getDefaultConnection();
                        
                        Statement st = con.createStatement();
                        int affectedRows = 0;
                        if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update")||query.toLowerCase().startsWith("drop")||query.toLowerCase().startsWith("alter")||query.toLowerCase().startsWith("create")) //
                        {
                            affectedRows = st.executeUpdate(query);
                            out.println("<tr>");                            
                            out.println("<td>");
                            out.println("Registros afectados: ");
                            out.println("<font size=\"1\">("+affectedRows+")</font>");
                            out.println("</td>");
                            out.println("</tr>");
                        }
                        else
                        {
                            ResultSet rs = st.executeQuery(query);
                            try
                            {
                                ResultSetMetaData md = rs.getMetaData();
                                int col = md.getColumnCount();
                                out.println("<tr>");
                                for (int x = 1; x <= col; x++)
                                {
                                    out.println("<th>");
                                    out.println(md.getColumnName(x));
                                    out.println("<font size=\"1\">("+md.getColumnTypeName(x)+")</font>");
                                    out.println("</th>");
                                }
                                out.println("</tr>");
                                int ch=0;
                                int cuenta=0;
                                while (rs.next())
                                {
                                    if(ch==0)
                                    {
                                        ch=1;
                                        out.println("<tr bgcolor=\"#EFEDEC\">");
                                    }
                                    else
                                    {
                                        ch=0;
                                        out.println("<tr>");
                                    }
                                    for (int x = 1; x <= col; x++)
                                    {
                                        
                                        String aux = rs.getString(x);
                                        if (aux == null) aux = "";
                                        out.println("<td>");
                                        
                                        if(aux.indexOf("<?xml")>-1)
                                        {
                                            cuenta++;
                                            //aux =aux.replaceAll("<", "&lt;");
                                            //aux =aux.replaceAll(">", "&gt;");
                                            out.println("<a onclick=\"javascript:showDiv(div"+cuenta+");\"><img border=0 src=\""+SWBPlatform.getContextPath()+"/swbadmin/images/preview.gif\" alt=\"view\"></a>"
                                                    +"<div id=\"div"+cuenta+"\" name=\"div"+cuenta+"\" style=\"position: absolute; border: 1px none #000000; visibility:hidden;\" onmouseout=\"javascript:hideDiv(div"+cuenta+");\" ><textarea rows=\"15\" cols=\"50\" >"+aux+"</textarea></div>");
                                        }
                                        else
                                        {
                                            out.println(aux);
                                        }
                                        out.println("</td>");
                                    }
                                    out.println("</tr>");
                                }
                            }
                            catch(java.sql.SQLException e)
                            {}
                        rs.close();
                        
                        }
                        st.close();
                        con.close();
                        out.println("</table>");
                        out.println("</div>");
                    }
                    else
                    {
                        out.println("<div class=\"swbform\">");
                        out.println("<fieldset>");
                        out.println("Error: Operaci&oacute;n no permitida <BR>");
                        out.println("<textarea name=\"query\" rows=\"20\" cols=\"80\">");
                        out.println(query);
                        out.println("</textarea>");
                        out.println("</fieldset>");
                        out.println("</div>");
                    }
                    
                }
            }
            catch(Exception e)
            {
                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("Error: <BR>");
                out.println("<textarea name=\"query\" rows=\"20\" cols=\"80\">");
                e.printStackTrace(out);
                out.println("</textarea>");
                out.println("</fieldset>");
                out.println("</div>");
            }
            
        }
    }
    
    /**
     * Sort iterator.
     * 
     * @param it the it
     * @return the iterator
     * @return
     */
    public Iterator sortIterator(Iterator it)
    {
        return SWBTreeUtil.sortIterator(it);
    }
    
//    /**
//     *
//     * @param en
//     * @return
//     */
//    public Iterator sortEnumeration(Enumeration en)
//    {
//        return SWBTreeUtil.sortEnumeration(en);
//    }
//
//    /**
//     *
//     * @param collection
//     * @return
//     */
//    public Iterator sortCollection(Collection collection)
//    {
//        return SWBTreeUtil.sortCollection(collection);
//    }
    
}
