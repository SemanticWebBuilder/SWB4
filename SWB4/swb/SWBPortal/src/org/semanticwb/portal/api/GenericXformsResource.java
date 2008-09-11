/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.api;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.chiba.web.WebFactory;
import org.chiba.web.session.XFormsSession;
import org.chiba.web.session.XFormsSessionManager;
import org.chiba.web.session.impl.DefaultXFormsSessionManagerImpl;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.util.XmlBundle;
import org.semanticwb.model.Portlet;

/**
 *
 * @author jorge.jimenez
 */

public class GenericXformsResource extends GenericResource {
    
    private static Logger log = SWBUtils.getLogger(GenericXformsResource.class);
    static Hashtable bundles=new Hashtable();
    XmlBundle bundle=null;
    
    @Override
    public void setResourceBase(Portlet base) throws SWBResourceException
    {
        super.setResourceBase(base);
        String name=getClass().getName();
        String className=name;
        bundle=(XmlBundle)bundles.get(name);
        if(bundle==null) {
            { //Para archivos de Administración
                bundle= new XmlBundle(className, name);
                bundles.put(name, bundle);
                //Colocar el xForms Inst
                String name_xFormsInst=name+"_inst";
                bundle= new XmlBundle(className, name_xFormsInst);
                bundles.put(name_xFormsInst, bundle);
            } { //Para archivos de Vista (Front-End)
                name=name+"_V";
                bundle= new XmlBundle(className, name);
                bundles.put(name, bundle);
                //Colocar el xForms Inst
                String name_xFormsInst=name+"_inst";
                bundle= new XmlBundle(className, name_xFormsInst);
                bundles.put(name_xFormsInst, bundle);
            }
        }
    }
    
    public String loadXform(String className, String name, String lang) {
        bundle=(XmlBundle)bundles.get(name);
        if(bundle==null) {
            {
                bundle= new XmlBundle(className, name);
                bundles.put(name, bundle);
            }
        }
        return bundle.getBundle(name, new java.util.Locale(lang));
    }
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if(paramRequest.getMode().equals("loadInstance")) {
            doLoadInstance(request,response,paramRequest); //Utilizado para cargar el modelo
        }else{
            super.processRequest(request, response, paramRequest);
        }
    }
    
    /**
     * Carga instancia, ya sea la de inicio o una ya grabada en BD del recurso en cuestión.
     */
    public void doLoadInstance(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) {
        try{
            if(request.getParameter("wbmode")!=null && request.getParameter("wbmode").equals("view")) { //Se desea la instancia de vista (Front-End)
                //Carga la instancia de archivo
                String res=initViewModel(request,paramsRequest);
                //response.getOutputStream().write(res.getBytes());
                response.getWriter().print(res);
            }else{ //Se desea la instancia de Administración
                //response.getOutputStream().write(initAdminModel(request,paramsRequest).getBytes());
                response.getWriter().print(initAdminModel(request,paramsRequest));
            }
        }catch(Exception e){
            log.error(e);
        }
        return;
    }
    
    /**
     * Metodo que proporciona una instancia de inicio para el recurso xForms, busca un archivo NombreClase_inst_locale
     **/
    private String initViewModel(HttpServletRequest request,SWBParamRequest paramsRequest) // Inicializa el modelo de la forma, puede leerse de un archivo xml
    {
        String instanceName=getClass().getName()+"_V_inst";
        if(request.getParameter("instanceName")!=null){
            instanceName=request.getParameter("instanceName");
        }
        return bundle.getBundle(instanceName, new java.util.Locale(paramsRequest.getUser().getLanguage()));
    }
    
    /**
     * Metodo que proporciona una instancia de inicio para el recurso xForms, busca un archivo NombreClase_inst_locale
     **/
    private String initAdminModel(HttpServletRequest request,SWBParamRequest paramsRequest) // Inicializa el modelo de la forma, puede leerse de un archivo xml
    {
        Portlet base=getResourceBase();
        Document dom=null;
        if(base.getXml()!=null) {
            dom=SWBUtils.XML.xmlToDom(base.getXml());
            NodeList nviewNode=dom.getElementsByTagName("wbadm");
            if(nviewNode.getLength()==0){ //Carga la instancia de archivo
                return initAdminModelFromFile(request,paramsRequest);
            }else{ //Carga la instancia de BD
                for(int i=0;i<nviewNode.getLength();i++) {
                    try{
                        Document domAdmin=toDocument(nviewNode.item(0).getFirstChild());
                        return SWBUtils.XML.domToXml(domAdmin);
                    }catch(Exception e){
                        log.error(e);
                    }
                }
            }
        }else{
            // De lo contrario que cargue una instancia inicial del modelo
            return initAdminModelFromFile(request,paramsRequest);
        }
        return "";
    }
    
    /**
     * Metodo que proporciona una instancia de inicio para el recurso xForms, busca un archivo NombreClase_inst_locale
     **/
    private String initAdminModelFromFile(HttpServletRequest request,SWBParamRequest paramsRequest) // Inicializa el modelo de la forma, puede leerse de un archivo xml
    {
        
        String instanceName=getClass().getName()+"_inst";;
        if(request.getAttribute("instanceName")!=null){
            instanceName=(String)request.getAttribute("instanceName");
        }
        return bundle.getBundle(instanceName, new java.util.Locale(paramsRequest.getUser().getLanguage()));
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        System.out.println("Entra a doView de GenericXforms-1");
        doMethod(request, response, paramRequest);
    }
    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        System.out.println("Entra a doAdmin de GenericXforms-2");
        doMethod(request, response, paramRequest);
    }
    
    private void doMethod(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException 
    {
        String xml=null;
        request.setCharacterEncoding("UTF-8");
        org.chiba.web.servlet.WebUtil.nonCachingResponse(response);
        
        String action =  request.getParameter("action");
        if(action==null || (action!=null && action.trim().equals(""))) {
            action=(String)request.getAttribute("action");
            if(action==null || (action!=null && action.trim().equals(""))) {
                action=paramsRequest.getAction();
            }
        }
        if(action.equals("add") || action.equals("edit")) { //Cuando es alta o edición de la forma
            String instanceName=getClass().getName();
            if(paramsRequest.getMode().equals(paramsRequest.Mode_VIEW)){
                instanceName=instanceName+"_V";
            }
            if(request.getAttribute("xformsDoc")!=null){
                xml=SWBUtils.XML.domToXml((Document)request.getAttribute("xformsDoc"));
            }
            if(xml==null){ //Toma de bundle
                xml=bundle.getBundle(instanceName, new java.util.Locale(paramsRequest.getUser().getLanguage()));
            }
            if(xml!=null && xml.trim().length()>0) {
                try{
                    Document dom=SWBUtils.XML.xmlToDom(xml);
                    request.setAttribute(WebFactory.XFORMS_NODE, dom);
                    configxForms(request, response,paramsRequest);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        else if(action.equals("update")) { //Actualiza el recurso
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            PrintWriter out=response.getWriter();
            out.println(ProcessData(buffer.toString()));
        }
    }
    
    private void configxForms(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) {
        boolean isAdmin=true;
        if(paramsRequest.getMode().equals(paramsRequest.Mode_VIEW)) {
            isAdmin=false;
        }
        
        HttpSession session = request.getSession(true);
        request.setAttribute(WebFactory.SCRIPTED,"true");
        log.info("Start Filter XForm Servlet");
        
        XFormsSession xFormsSession = null;
        try {
            XFormsSessionManager sessionManager = DefaultXFormsSessionManagerImpl.getXFormsSessionManager();
            xFormsSession = sessionManager.createXFormsSession(request, response, session);
            xFormsSession.setBaseURI(request.getRequestURL().toString());
            xFormsSession.setXForms();
            
            SWBResourceURL url=null;
            //String wbPRequestURL=null;
            { // set instance model src uri
                url=paramsRequest.getRenderUrl();
                url.setCallMethod(url.Call_DIRECT);
                url.setAction("update");
                url.setMode("loadInstance");
                url.setParameter("instanceName",(String)request.getAttribute("instanceName"));
                url.setParameter("instance",(String)request.getAttribute("instance"));
                if(!isAdmin) {
                    url.setParameter("wbmode","view");
                }
                /*
                if(!isAdmin) {
                    wbPRequestURL=paramsRequest.getActionUrl().toString();
                    url.setParameter("wbmode","view");
                }*/
                xFormsSession.getAdapter().setContextParam("wbInstance",url.toString());
            }
            if(isAdmin){
                url.setMode(url.Mode_ADMIN);
            }else{
                url.setMode(url.Mode_VIEW);
            }
            xFormsSession.init();
            
            //Redirecciona a esta misma administración para actualizar el recurso al momento
            //que len den submit a la forma en cuestion (xForm)
            /*
            if(!isAdmin) {
                xFormsSession.getAdapter().setContextParam("wbRedirect",wbPRequestURL);
            }else{
                xFormsSession.getAdapter().setContextParam("wbRedirect",url.toString());
            }
             */
            xFormsSession.getAdapter().setContextParam("wbRedirect",url.toString());
            xFormsSession.handleRequest();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            if(xFormsSession != null){
                try{
                    xFormsSession.close(e);
                }catch(Exception ex){ex.printStackTrace();}
            }
        }
        log.info("End Render XForm Servlet");
    }
    
    public String ProcessData(String data) {
        Portlet base=getResourceBase();
        //RecResource recRsc=base.
        try{
            String xml=base.getXml();
            int pos=-1;
            if(data!=null){
                if((pos=data.indexOf("<?xml version="))>-1){
                    pos=data.indexOf(">",pos);
                    data=data.substring(pos+1);
                }
            }
            if(xml!=null) {
                if((pos=xml.indexOf("<resource>"))>-1){
                    xml=xml.substring(pos+10,xml.indexOf("</resource>"));
                }
                //Graba modo Admin
                if((pos=xml.indexOf("<wbadm>"))>-1){
                    xml=xml.substring(0,pos)+"<wbadm>"+data+"</wbadm>"+xml.substring(xml.indexOf("</wbadm>")+8);
                }
            }else{ // Insertar el xml como nuevo
                xml="<wbadm>"+data+"</wbadm>";
            }
            base.setXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?><resource>"+xml+"</resource>");
            //base..update(paramsRequest.getUser().getId(), "Resource width id:"+ base.getId()+",was updated succefully");
        }catch(Exception e){
            log.error(e);
        }
        return data;
    }
    
    /**
     * Metodo para hacer operaciones
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
    }
    
    //TODO: PASAR LOS SIGUIENTES METODOS A SWBUitls en version SWB 4.0
    
    /**
     * Comvierte un Node a Document
     */
    private Document toDocument(Node node) throws SWBResourceException, IOException {
        // ensure xerces dom
        if (node instanceof org.apache.xerces.dom.DocumentImpl) {
            return (Document) node;
        }
        Document document = getDocumentBuilder().newDocument();
        if (node instanceof Document) {
            node = ((Document) node).getDocumentElement();
        }
        document.appendChild(document.importNode(node, true));
        return document;
    }
    
    /**
     * Comvierte un Node a Document
     */
    private DocumentBuilder getDocumentBuilder() throws SWBResourceException, IOException 
    {
        // ensure xerces dom
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            factory.setAttribute("http://apache.org/xml/properties/dom/document-class-name", "org.apache.xerces.dom.DocumentImpl");
            return factory.newDocumentBuilder();
        }catch(Exception e){
            log.error(e);
        }
        return null;
    }
    
}
