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
 * WBAWorkflow.java
 *
 * Created on 28 de octubre de 2004, 12:35 AM
 */

package org.semanticwb.portal.admin.resources;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.*;

import java.util.*;
import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.PFlow;
import org.semanticwb.model.PortletType;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.admin.resources.workflow.proxy.WorkflowResponse;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceURL;


/**
 * Recurso para la administraci�n de WebBuilder que permite la administraci�n de
 * los flujos de trabajo.
 *
 * WebBuilder administration resource that allows the work flow administration.
 *
 * @author Victor Lorenzana
 */
public class SWBAWorkflow extends GenericResource{

    private Logger log = SWBUtils.getLogger(SWBAWorkflow.class);

    /** Creates a new instance of WBAWorkflow */
    public SWBAWorkflow() {
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
        }
        else if(paramRequest.getMode().equals("script"))
        {
            doScript(request,response,paramRequest);
        }
        else if(paramRequest.getMode().equals("wsdl"))
        {
            doWSDL(request,response,paramRequest);
        }
        else if(paramRequest.getMode().equals("webservice"))
        {
            doWebService(request,response,paramRequest);
        }  
        else super.processRequest(request,response,paramRequest);
        
    }
    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doWebService(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
    
        
        response.setContentType("text/xml");
        String service=request.getParameter("service");               
        response.setContentType("text/xml");        
        if(service==null)
        {
            return;
        }
        else if(service.equals("mail"))
        {
            this.mailWebService(request, response, paramRequest);
        }
        else if(service.equals("authorize"))
        {
            this.authorizeWebService(request, response, paramRequest);
        }
        else if(service.equals("noauthorize"))
        {
            this.noauthorizeWebService(request, response, paramRequest);
        }
        else if(service.equals("publish"))
        {
            this.publishWebService(request, response, paramRequest);
        }
    }
    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws IOException
     */    
    public void authorizeWebService(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)  throws IOException
    {
        int length=request.getContentLength();
        if(length>0)
        {
            InputStream in=request.getInputStream();
            Document doc=SWBUtils.XML.xmlToDom(in);
            if(doc.getElementsByTagName("content").getLength()>0)
            {
                try
                {
                    Element econtent=(Element)doc.getElementsByTagName("content").item(0);
                    Text etext=(Text)econtent.getFirstChild();
                    String content=etext.getNodeValue();
                    Content ocontent=new Content(content);                 
                    WebSite map=SWBContext.getWebSite(ocontent.getTopicMapID());
                    WebPage topic=map.getWebPage(ocontent.getTopicID());
                    
//                    Occurrence occ=topic.getOccurrence(ocontent.getOccurrence());
//                    occ.getDbdata().setStatus(2);
//                    occ.getDbdata().update();
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                    log.error(e);
                }
            }
        }
        String xmlresp="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
            "<soap:Body>"+
            "<authorizeResponse xmlns=\"http://tempuri.org/\" />"+
            "</soap:Body>"+
            "</soap:Envelope>";
        PrintWriter out=response.getWriter();
        out.write(xmlresp);
        out.close();
    }
    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws IOException
     */    
    public void noauthorizeWebService(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)  throws IOException
    {
        int length=request.getContentLength();
        if(length>0)
        {
            InputStream in=request.getInputStream();
            Document doc=SWBUtils.XML.xmlToDom(in);
            if(doc.getElementsByTagName("content").getLength()>0)
            {
                try
                {
                    Element econtent=(Element)doc.getElementsByTagName("content").item(0);
                    Text etext=(Text)econtent.getFirstChild();
                    String content=etext.getNodeValue();
                    Content ocontent=new Content(content);                 
                    WebSite map=SWBContext.getWebSite(ocontent.getTopicMapID());
                    WebPage topic=map.getWebPage(ocontent.getTopicID());
//                    Occurrence occ=topic.getOccurrence(ocontent.getOccurrence());
//                    occ.getDbdata().setStatus(3);
//                    occ.getDbdata().update();
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                    log.error(e);
                }
            }
        }
        String xmlresp="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
            "<soap:Body>"+
            "<authorizeResponse xmlns=\"http://tempuri.org/\" />"+
            "</soap:Body>"+
            "</soap:Envelope>";
        PrintWriter out=response.getWriter();
        out.write(xmlresp);
        out.close();
    }
    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws IOException
     */    
    public void publishWebService(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)  throws IOException
    {
        int length=request.getContentLength();
        if(length>0)
        {
            InputStream in=request.getInputStream();
            Document doc=SWBUtils.XML.xmlToDom(in);
            if(doc.getElementsByTagName("content").getLength()>0)
            {
                try
                {
                    Element econtent=(Element)doc.getElementsByTagName("content").item(0);
                    Text etext=(Text)econtent.getFirstChild();
                    String content=etext.getNodeValue();
                    Content ocontent=new Content(content);                 
                    WebSite map=SWBContext.getWebSite(ocontent.getTopicMapID());
                    WebPage topic=map.getWebPage(ocontent.getTopicID());
//                    Occurrence occ=topic.getOccurrence(ocontent.getOccurrence());
//                    occ.getDbdata().setActive(1);
//                    occ.getDbdata().update();
                    
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                    log.error(e);
                }
            }
        }
        String xmlresp="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
            "<soap:Body>"+
            "<authorizeResponse xmlns=\"http://tempuri.org/\" />"+
            "</soap:Body>"+
            "</soap:Envelope>";
        PrintWriter out=response.getWriter();
        out.write(xmlresp);
        out.close();
    }
    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws IOException
     */    
    public void mailWebService(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)  throws IOException
    {
        int length=request.getContentLength();
        if(length>0)
        {
            InputStream in=request.getInputStream();
            Document doc=SWBUtils.XML.xmlToDom(in);
            if(doc.getElementsByTagName("content").getLength()>0 && doc.getElementsByTagName("workflowId").getLength()>0 && doc.getElementsByTagName("activity").getLength()>0)
            {
                try
                {
                    Element econtent=(Element)doc.getElementsByTagName("content").item(0);
                    Text etext=(Text)econtent.getFirstChild();
                    String content=etext.getNodeValue();
                    Content ocontent=new Content(content);
                    
                    Element eworkflow=(Element)doc.getElementsByTagName("workflowId").item(0);
                    etext=(Text)eworkflow.getFirstChild();
                    String workflowId=etext.getNodeValue();
                    
                    Element eactivity=(Element)doc.getElementsByTagName("activity").item(0);
                    etext=(Text)eactivity.getFirstChild();
                    String activityName=etext.getNodeValue();
                    
                    String prefix="";
                    String topicmap="";
                    int pos=workflowId.indexOf(prefix);
                    if(pos!=-1)
                    {
                        workflowId=workflowId.substring(pos);
                    }
                    pos=workflowId.indexOf("_");
                    if(pos!=-1)
                    {
                        topicmap=workflowId.substring(pos+1);
                        workflowId=workflowId.substring(0,pos);
                    }     
                    String to="";
                    WebSite map=SWBContext.getWebSite(topicmap);
                    String id=workflowId;
                    PFlow pflow=SWBContext.getWebSite(topicmap).getPFlow(id);
                    Document docdef=SWBUtils.XML.xmlToDom(pflow.getXml());
                    NodeList activities=docdef.getElementsByTagName("activity");
                    for(int i=0;i<activities.getLength();i++)
                    {
                        Element activity=(Element)activities.item(i);
                        NodeList users=activity.getElementsByTagName("user");
                        for(int j=0;j<users.getLength();j++)
                        {
                            Element user=(Element)users.item(j);
                            String userid=user.getAttribute("id");
                            UserRepository dbuser=map.getUserRepository();
                            User recuser =dbuser.getUser(userid);
                            to+=recuser.getUsrEmail()+";";
                        }
                        NodeList roles=activity.getElementsByTagName("role");
                        for(int j=0;j<roles.getLength();j++)
                        {
                            Element role=(Element)roles.item(j);
                            String roleid=role.getAttribute("id");
                            UserRepository dbuser=map.getUserRepository();
                            Iterator<User> enusers=dbuser.listUsers();
                            while(enusers.hasNext())
                            {
                                User user=enusers.next();
                                Iterator<Role> itroles=user.listRoles();
                                while(itroles.hasNext())
                                {
                                    //Role
                                }
                            }
                            
                        }
                    }
                    
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                    log.error(e);
                }
            }
        }
        String xmlresp="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
            "<soap:Body>"+
            "<mailResponse xmlns=\"http://tempuri.org/\" />"+
            "</soap:Body>"+
            "</soap:Envelope>";
        PrintWriter out=response.getWriter();
        out.write(xmlresp);
        out.close();
        
    }
    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws IOException
     */    
    public void mailWSDL(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)  throws IOException
    {
        SWBResourceURL urlmode=paramRequest.getRenderUrl();
        urlmode.setMode("wsdl");
        urlmode.setCallMethod(SWBResourceURL.Call_DIRECT);
        PrintWriter out=response.getWriter();
        String xmlout="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
            "<wsdl:definitions xmlns:http=\"http://schemas.xmlsoap.org/wsdl/http/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:s=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:tns=\"http://tempuri.org/\" xmlns:tm=\"http://microsoft.com/wsdl/mime/textMatching/\" xmlns:mime=\"http://schemas.xmlsoap.org/wsdl/mime/\" targetNamespace=\"http://tempuri.org/\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\">"+
            "<wsdl:types>"+
            "<s:schema elementFormDefault=\"qualified\" targetNamespace=\"http://tempuri.org/\">"+
            "<s:element name=\"mail\">"+
            "<s:complexType>"+
            "<s:sequence>"+
            "<s:element minOccurs=\"1\" maxOccurs=\"1\" name=\"content\" type=\"s:string\" />"+
            "<s:element minOccurs=\"0\" maxOccurs=\"1\" name=\"workflowId\" type=\"s:string\" />"+
            "<s:element minOccurs=\"0\" maxOccurs=\"1\" name=\"activity\" type=\"s:string\" />"+
            "</s:sequence>"+
            "</s:complexType>"+
            "</s:element>"+
            "<s:element name=\"MailResponse\">"+
            "<s:complexType />"+
            "</s:element>"+
            "</s:schema>"+
            "</wsdl:types>"+
            "<wsdl:message name=\"MailSoapIn\">"+
            "<wsdl:part name=\"parameters\" element=\"tns:mail\" />"+
            "</wsdl:message>"+
            "<wsdl:message name=\"MailSoapOut\">"+
            "<wsdl:part name=\"parameters\" element=\"tns:MailResponse\" />"+
            "</wsdl:message>"+
            "<wsdl:portType name=\"mail\">"+
            "<wsdl:operation name=\"mail\">"+
            "<wsdl:input message=\"tns:MailSoapIn\" />"+
            "<wsdl:output message=\"tns:MailSoapOut\" />"+
            "</wsdl:operation>"+
            "</wsdl:portType>"+
            "<wsdl:binding name=\"mail\" type=\"tns:mail\">"+
            "<soap:binding transport=\"http://schemas.xmlsoap.org/soap/http\" style=\"document\" />"+
            "<wsdl:operation name=\"mail\">"+
            "<soap:operation soapAction=\"http://tempuri.org/mail\" style=\"document\" />"+
            "<wsdl:input>"+
            "<soap:body use=\"literal\" />"+
            "</wsdl:input>"+
            "<wsdl:output>"+
            "<soap:body use=\"literal\" />"+
            "</wsdl:output>"+
            "</wsdl:operation>"+
            "</wsdl:binding>"+
            "<wsdl:service name=\"mail\">"+
            "<documentation xmlns=\"http://schemas.xmlsoap.org/wsdl/\" />"+
            "<wsdl:port name=\"mail\" binding=\"tns:mail\">"+
            "<soap:address location=\""+ urlmode+"?service=mail"+"\" />"+
            "</wsdl:port>"+
            "</wsdl:service>"+
            "</wsdl:definitions>";
        out.write(xmlout);
        out.close();
    }
    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws IOException
     */    
    public void authorizeWSDL(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)  throws IOException
    {
        SWBResourceURL urlmode=paramRequest.getRenderUrl();
        urlmode.setMode("wsdl");
        urlmode.setCallMethod(SWBResourceURL.Call_DIRECT);
        PrintWriter out=response.getWriter();
        String xmlout="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
            "<wsdl:definitions xmlns:http=\"http://schemas.xmlsoap.org/wsdl/http/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:s=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:tns=\"http://tempuri.org/\" xmlns:tm=\"http://microsoft.com/wsdl/mime/textMatching/\" xmlns:mime=\"http://schemas.xmlsoap.org/wsdl/mime/\" targetNamespace=\"http://tempuri.org/\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\">"+
            "<wsdl:types>"+
            "<s:schema elementFormDefault=\"qualified\" targetNamespace=\"http://tempuri.org/\">"+
            "<s:element name=\"authorize\">"+
            "<s:complexType>"+
            "<s:sequence>"+
            "<s:element minOccurs=\"1\" maxOccurs=\"1\" name=\"content\" type=\"s:string\" />"+
            "</s:sequence>"+
            "</s:complexType>"+
            "</s:element>"+
            "<s:element name=\"authorizeResponse\">"+
            "<s:complexType />"+
            "</s:element>"+
            "</s:schema>"+
            "</wsdl:types>"+
            "<wsdl:message name=\"authorizeSoapIn\">"+
            "<wsdl:part name=\"parameters\" element=\"tns:authorize\" />"+
            "</wsdl:message>"+
            "<wsdl:message name=\"authorizeSoapOut\">"+
            "<wsdl:part name=\"parameters\" element=\"tns:authorizeResponse\" />"+
            "</wsdl:message>"+
            "<wsdl:portType name=\"authorize\">"+
            "<wsdl:operation name=\"authorize\">"+
            "<wsdl:input message=\"tns:authorizeSoapIn\" />"+
            "<wsdl:output message=\"tns:authorizeSoapOut\" />"+
            "</wsdl:operation>"+
            "</wsdl:portType>"+
            "<wsdl:binding name=\"authorize\" type=\"tns:authorize\">"+
            "<soap:binding transport=\"http://schemas.xmlsoap.org/soap/http\" style=\"document\" />"+
            "<wsdl:operation name=\"authorize\">"+
            "<soap:operation soapAction=\"http://tempuri.org/authorize\" style=\"document\" />"+
            "<wsdl:input>"+
            "<soap:body use=\"literal\" />"+
            "</wsdl:input>"+
            "<wsdl:output>"+
            "<soap:body use=\"literal\" />"+
            "</wsdl:output>"+
            "</wsdl:operation>"+
            "</wsdl:binding>"+
            "<wsdl:service name=\"authorize\">"+
            "<documentation xmlns=\"http://schemas.xmlsoap.org/wsdl/\" />"+
            "<wsdl:port name=\"authorize\" binding=\"tns:authorize\">"+
            "<soap:address location=\""+  urlmode+"?service=authorize" +"\" />"+
            "</wsdl:port>"+
            "</wsdl:service>"+
            "</wsdl:definitions>";
        out.write(xmlout);
        out.close();
    }
    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws IOException
     */    
    public void noauthorizeWSDL(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)  throws IOException
    {
        SWBResourceURL urlmode=paramRequest.getRenderUrl();
        urlmode.setMode("wsdl");
        urlmode.setCallMethod(SWBResourceURL.Call_DIRECT);
        PrintWriter out=response.getWriter();
        String xmlout="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
            "<wsdl:definitions xmlns:http=\"http://schemas.xmlsoap.org/wsdl/http/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:s=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:tns=\"http://tempuri.org/\" xmlns:tm=\"http://microsoft.com/wsdl/mime/textMatching/\" xmlns:mime=\"http://schemas.xmlsoap.org/wsdl/mime/\" targetNamespace=\"http://tempuri.org/\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\">"+
            "<wsdl:types>"+
            "<s:schema elementFormDefault=\"qualified\" targetNamespace=\"http://tempuri.org/\">"+
            "<s:element name=\"noauthorize\">"+
            "<s:complexType>"+
            "<s:sequence>"+
            "<s:element minOccurs=\"1\" maxOccurs=\"1\" name=\"content\" type=\"s:string\" />"+
            "</s:sequence>"+
            "</s:complexType>"+
            "</s:element>"+
            "<s:element name=\"authorizeResponse\">"+
            "<s:complexType />"+
            "</s:element>"+
            "</s:schema>"+
            "</wsdl:types>"+
            "<wsdl:message name=\"authorizeSoapIn\">"+
            "<wsdl:part name=\"parameters\" element=\"tns:noauthorize\" />"+
            "</wsdl:message>"+
            "<wsdl:message name=\"authorizeSoapOut\">"+
            "<wsdl:part name=\"parameters\" element=\"tns:authorizeResponse\" />"+
            "</wsdl:message>"+
            "<wsdl:portType name=\"noauthorize\">"+
            "<wsdl:operation name=\"noauthorize\">"+
            "<wsdl:input message=\"tns:authorizeSoapIn\" />"+
            "<wsdl:output message=\"tns:authorizeSoapOut\" />"+
            "</wsdl:operation>"+
            "</wsdl:portType>"+
            "<wsdl:binding name=\"noauthorize\" type=\"tns:noauthorize\">"+
            "<soap:binding transport=\"http://schemas.xmlsoap.org/soap/http\" style=\"document\" />"+
            "<wsdl:operation name=\"noauthorize\">"+
            "<soap:operation soapAction=\"http://tempuri.org/noauthorize\" style=\"document\" />"+
            "<wsdl:input>"+
            "<soap:body use=\"literal\" />"+
            "</wsdl:input>"+
            "<wsdl:output>"+
            "<soap:body use=\"literal\" />"+
            "</wsdl:output>"+
            "</wsdl:operation>"+
            "</wsdl:binding>"+
            "<wsdl:service name=\"noauthorize\">"+
            "<documentation xmlns=\"http://schemas.xmlsoap.org/wsdl/\" />"+
            "<wsdl:port name=\"noauthorize\" binding=\"tns:noauthorize\">"+
            "<soap:address location=\""+  urlmode+"?service=noauthorize" +"\" />"+
            "</wsdl:port>"+
            "</wsdl:service>"+
            "</wsdl:definitions>";
        out.write(xmlout);
        out.close();
    }
    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws IOException
     */    
    public void publishWSDL(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)  throws IOException
    {
        SWBResourceURL urlmode=paramRequest.getRenderUrl();
        urlmode.setMode("wsdl");
        urlmode.setCallMethod(SWBResourceURL.Call_DIRECT);
        PrintWriter out=response.getWriter();
        String xmlout="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
            "<wsdl:definitions xmlns:http=\"http://schemas.xmlsoap.org/wsdl/http/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:s=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:tns=\"http://tempuri.org/\" xmlns:tm=\"http://microsoft.com/wsdl/mime/textMatching/\" xmlns:mime=\"http://schemas.xmlsoap.org/wsdl/mime/\" targetNamespace=\"http://tempuri.org/\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\">"+
            "<wsdl:types>"+
            "<s:schema elementFormDefault=\"qualified\" targetNamespace=\"http://tempuri.org/\">"+
            "<s:element name=\"publish\">"+
            "<s:complexType>"+
            "<s:sequence>"+
            "<s:element minOccurs=\"1\" maxOccurs=\"1\" name=\"content\" type=\"s:string\" />"+
            "</s:sequence>"+
            "</s:complexType>"+
            "</s:element>"+
            "<s:element name=\"publishResponse\">"+
            "<s:complexType />"+
            "</s:element>"+
            "</s:schema>"+
            "</wsdl:types>"+
            "<wsdl:message name=\"publishSoapIn\">"+
            "<wsdl:part name=\"parameters\" element=\"tns:publish\" />"+
            "</wsdl:message>"+
            "<wsdl:message name=\"publishSoapOut\">"+
            "<wsdl:part name=\"parameters\" element=\"tns:publishResponse\" />"+
            "</wsdl:message>"+
            "<wsdl:portType name=\"publish\">"+
            "<wsdl:operation name=\"publish\">"+
            "<wsdl:input message=\"tns:publishSoapIn\" />"+
            "<wsdl:output message=\"tns:publishSoapOut\" />"+
            "</wsdl:operation>"+
            "</wsdl:portType>"+
            "<wsdl:binding name=\"publish\" type=\"tns:publish\">"+
            "<soap:binding transport=\"http://schemas.xmlsoap.org/soap/http\" style=\"document\" />"+
            "<wsdl:operation name=\"publish\">"+
            "<soap:operation soapAction=\"http://tempuri.org/publish\" style=\"document\" />"+
            "<wsdl:input>"+
            "<soap:body use=\"literal\" />"+
            "</wsdl:input>"+
            "<wsdl:output>"+
            "<soap:body use=\"literal\" />"+
            "</wsdl:output>"+
            "</wsdl:operation>"+
            "</wsdl:binding>"+
            "<wsdl:service name=\"publish\">"+
            "<documentation xmlns=\"http://schemas.xmlsoap.org/wsdl/\" />"+
            "<wsdl:port name=\"publish\" binding=\"tns:publish\">"+
            "<soap:address location=\""+  urlmode+"?service=publish" +"\" />"+
            "</wsdl:port>"+
            "</wsdl:service>"+
            "</wsdl:definitions>";
        out.write(xmlout);
        out.close();
    }
    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doWSDL(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/xml");
        String service=request.getParameter("service");        
        if(service==null)
        {
            return;
        }
        else if(service.equals("mail"))
        {
            this.mailWSDL(request, response, paramRequest);
        }
        else if(service.equals("authorize"))
        {
            this.authorizeWSDL(request, response, paramRequest);
        }
        else if(service.equals("noauthorize"))
        {
            this.noauthorizeWSDL(request, response, paramRequest);
        }
        else if(service.equals("publish"))
        {
            this.publishWSDL(request, response, paramRequest);
        }
        
    }
    
       
    /**
     *
     * @param cmd
     * @param src
     * @param user
     * @param request
     * @param response
     * @param paramRequest
     * @return
     */    
    private Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response,SWBParamRequest paramRequest)
    {
        return getDocument(user, src, cmd,paramRequest,request);
    }
   
    
    /**
     *
     * @param res
     * @param root
     */    
    public void getPermisosGral(Element res,WebPage root)
    {

        ArrayList arr = new ArrayList((Collection)root.listChilds());

        for(int i=0;i<arr.size();i++)
        {
            WebPage child=(WebPage)arr.get(i);
            Element etopic=addNode("topic", child.getId(), child.getDisplayName(), res);            
            getPermisosGral(etopic,child);
        }
    }
    
    /**
     *
     * @param res
     * @param tm
     */    
    public void getCatalogRoles(Element res,String tm)
    {        
        Vector roles=new Vector();
        //TODO: Cambiar en version 3.1
        //WebSite map=TopicMgr.getInstance().getTopicMap(tm);
        WebSite map=SWBContext.getAdminWebSite();
        
        Iterator<Role> it=map.getUserRepository().listRoles();
        while(it.hasNext())
        {      
            Role role=it.next();  
            roles.add(role);
            System.out.println("role:"+role.getTitle());
            
        }
        Collections.sort(roles,new OrdenaRole());
        Iterator itRoles=roles.iterator();
        while(itRoles.hasNext())
        {
            Role role=(Role)itRoles.next();  
            Element erole=addNode("role", ""+role.getId(), role.getTitle(), res);
            erole.setAttribute("id",""+role.getId());
            erole.setAttribute("repository",""+role.getUserRepository().getId());
        }

    }
    /**
     *
     * @param res
     * @param tm
     * @param src
     */    
    public void getProcessCount(Element res,String tm,Document src)
    {
        if(src.getElementsByTagName("workflow").getLength()>0)
        {
            Element workflow=(Element)src.getElementsByTagName("workflow").item(0);
            String workflowID=workflow.getAttribute("id");
            String topicmap="";
            String version=workflow.getAttribute("version");
            try
            {
                //TODO:WBProxyWorkflow.getProcessList
//                ArrayList processes=WBProxyWorkflow.getProcessList(topicmap, workflowID, version);
//                addElement("err",String.valueOf(processes.size()), res);
                addElement("err","1", res);
            }
            catch(Exception e)
            {
                e.printStackTrace(System.out);
                log.error(e);
                addElement("err",e.getMessage(), res);
            }
        }
        addElement("err","The element workflow was not found", res);
    }
    /**
     *
     * @param res
     * @param tm
     * @param src
     */    
    public void getWorkflow(Element res,String tm,Document src)
    {
        if(src.getElementsByTagName("id").getLength()>0)
        {
            Element eid=(Element)src.getElementsByTagName("id").item(0);
            Text etext=(Text)eid.getFirstChild();
            String id=etext.getNodeValue();            
            try
            {
                
                PFlow pflow=SWBContext.getWebSite(tm).getPFlow(id);
                String xml=pflow.getXml();
                Document doc=SWBUtils.XML.xmlToDom(xml);
                
                Element ele=(Element)doc.getElementsByTagName("workflow").item(0);
                Node eworkflow=res.getOwnerDocument().importNode(ele,true);                
                res.appendChild(eworkflow);
                
            }
            catch(Exception e)
            {
                e.printStackTrace(System.out);
                addElement("err", e.getMessage(), res);
            }            
        }
        else
        {
            addElement("err", java.util.ResourceBundle.getBundle("com/infotec/wb/admin/resources/WBAWorkflow").getString("err1"), res);
        }
    }
    /**
     *
     * @param res
     * @param tm
     */    
    public void getCatalogUsers(Element res,String tm)
    {        
        Vector users=new Vector();
        //TODO: Cambiar en version 3.1
        //WebSite map=TopicMgr.getInstance().getTopicMap(tm);
        WebSite map=SWBContext.getAdminWebSite();
        Iterator<User> it=map.getUserRepository().listUsers();
        while(it.hasNext())
        {      
            User user=it.next();
            if(map.getUserRepository().getId().equals(user.getUserRepository().getId()))
            {
                 try
                 {
                     //TODO: user.havePermission
                     //if(user.havePermission(SWBContext.getAdminWebSite().getWebPage("WBAd_per_Administrator")))
                     {
                         users.add(user);
                     }
                 }
                 catch(Exception ue)
                 {
                     ue.printStackTrace(System.out);
                     log.error(ue);
                 }
            }
        }
        
        Collections.sort(users,new OrdenaUsuarios());
        Iterator itUsers=users.iterator();
        while(itUsers.hasNext())
        {    
            User user=(User)itUsers.next();
            try
            {
                Element erole=addNode("user",""+user.getId(), user.getName(), res);
            }
            catch(Exception ue)
             {
                 ue.printStackTrace(System.out);
                 log.error(ue);
             }
        }
        
    }
    /**
     *
     * @param res
     * @param wf
     * @param user
     * @param tm
     * @param paramRequest
     * @param request
     */    
    public void add(Element res,Element wf,User user,String tm,SWBParamRequest paramRequest,HttpServletRequest request)
    {
        //PFlowSrv srv=new PFlowSrv();
        String name=wf.getAttribute("name");
        String description="";
        if(wf.getElementsByTagName("description").getLength()>0)
        {
            Element edesc=(Element)wf.getElementsByTagName("description").item(0);
            Text etext=(Text)edesc.getFirstChild();
            description=etext.getNodeValue();
            
        }
        try
        {
            WebSite ws = SWBContext.getWebSite(tm);
            PFlow pflow=ws.createPFlow();
            pflow.setTitle(name);
            pflow.setDescription(description);
            pflow.setXml(SWBUtils.XML.domToXml(wf.getOwnerDocument()));
            //TODO:
            //PFlow pflow=srv.createPFlow(tm, name, description, wf.getOwnerDocument(), user.getId());
            addElement("workflowid",  String.valueOf(pflow.getId()), res);
            addElement("version", "1", res);
        }
        catch(Exception e)
        {
            log.error(e);
            e.printStackTrace(System.out);
            addElement("err", java.util.ResourceBundle.getBundle("com/infotec/wb/admin/resources/WBAWorkflow").getString("msg1"), res);
        }
        
    }
    /**
     *
     * @param res
     * @param src
     * @param user
     * @param tm
     * @param paramRequest
     * @param request
     */    
    public void update(Element res,Document src,User user,String tm,SWBParamRequest paramRequest,HttpServletRequest request)
    {
        try
        {
            Element wf=(Element)src.getElementsByTagName("workflow").item(0);            
            String id=wf.getAttribute("id");            
            if(id==null || id.trim().equals(""))
            {
                add(res,wf,user,tm,paramRequest,request);
            }
            else
            {
                int idpflow=Integer.parseInt(wf.getAttribute("id"));
//                PFlowSrv sPFlowSrv=new PFlowSrv();
                try
                {
                    WorkflowResponse wresp=updatePflow(tm, src, user.getId());
                    addElement("workflowid", String.valueOf(idpflow), res); 
                    addElement("version", String.valueOf(wresp.getVersion()), res);
                }
                catch(Exception e)
                {
                    log.error(e);
                    e.printStackTrace(System.out);
                    addElement("err", java.util.ResourceBundle.getBundle("com/infotec/wb/admin/resources/WBAWorkflow").getString("msg1"), res);
                }
            }
        }
        catch(Exception e)
        {
             log.error(e);
             e.printStackTrace(System.out);
             addElement("err", java.util.ResourceBundle.getBundle("com/infotec/wb/admin/resources/WBAWorkflow").getString("msg1")+" error: "+e.getMessage(), res);
        }
        
    }    
    /**
     *
     * @param res
     * @param tm
     */    
    public void getResourceTypeCat(Element res,String tm)
    {
        System.out.println("getResourceTypeCat..."+tm);
        WebSite map = SWBContext.getWebSite(tm);
        Iterator<PortletType> elements=map.listPortletTypes();
        while(elements.hasNext())
        {
            PortletType obj=elements.next();
            System.out.println("PType ws:"+obj.getWebSite().getId());
            if(obj.getWebSite().getId().equals(map.getId()))
            {
                if(obj.getPortletMode()==1 || obj.getPortletMode()==3)
                {
                    Element erole=addNode("resourceType",""+obj.getId(), obj.getTitle(), res);
                    erole.setAttribute("topicmap",map.getId());
                    erole.setAttribute("topicmapname",map.getTitle());                
                    this.addElement("description", obj.getDescription(), erole);
                }
            }
        }   
        if(!map.getId().equals(SWBContext.WEBSITE_GLOBAL))
        {
            map=SWBContext.getGlobalWebSite();
            elements=map.listPortletTypes();
            while(elements.hasNext())
            {
                PortletType obj=elements.next();
                if(obj.getWebSite().getId().equals(map.getId()))
                {
                    if(obj.getPortletMode()==1 || obj.getPortletMode()==3)
                    {
                        Element erole=addNode("resourceType",""+obj.getId(), obj.getTitle(), res);
                        erole.setAttribute("topicmap",map.getId());
                        erole.setAttribute("topicmapname",map.getTitle());                
                        this.addElement("description", obj.getDescription(), erole);
                    }
                }
            }   
        }
    }
    /**
     *
     * @param user
     * @param src
     * @param cmd
     * @param paramRequest
     * @param request
     * @return
     */    
    public Document getDocument(User user, Document src, String cmd,SWBParamRequest paramRequest,HttpServletRequest request)
    {
        Document dom = null;
        try
        {
            String tm=null;            
            if(src.getElementsByTagName("tm").getLength()>0)
            {
                Node etm=src.getElementsByTagName("tm").item(0);
                Text etext=(Text)etm.getFirstChild();
                tm=etext.getNodeValue();
            }
            
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);           

            if(tm==null)
            {
                tm = paramRequest.getTopic().getWebSiteId();
            }


            if(cmd.equals("getcatRoles"))
            {
                getCatalogRoles(res,tm);
            }
            else if(cmd.equals("getResourceTypeCat"))
            {
                getResourceTypeCat(res,tm);
            }
            else if(cmd.equals("getcatUsers"))
            {
                getCatalogUsers(res,tm);
            }   
            else if(cmd.equals("getWorkflow"))
            {                
                getWorkflow(res,tm,src);
            }
            else if(cmd.equals("getProcessCount"))
            {
                getProcessCount(res,tm,src);
            }
            else if(cmd.equals("update"))
            {
                update(res,src,user,tm,paramRequest,request);
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
            
            if(cmd.equals("topic"))
            {
                String tmid=st.nextToken();
                String tpid=st.nextToken();

                StringBuffer ret=new StringBuffer();
                WebPage tp=SWBContext.getWebSite(tmid).getWebPage(tpid);
                ret.append(tp.getId());
                while(tp!=tp.getWebSite().getHomePage())
                {
                    tp=tp.getParent();
                    ret.insert(0,tp.getId()+".");
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
     * @param node
     * @param id
     * @param name
     * @param parent
     * @return
     */    
    private Element addNode(String node, String id, String name, Element parent)
    {
        Element ret=addElement(node,null,parent);
        if(id!=null)ret.setAttribute("id",id);
        if(name!=null)ret.setAttribute("name",name);
        System.out.println("res..."+ret.toString());
        return ret;
    }

    /**
     *
     * @param name
     * @param value
     * @param parent
     * @return
     */    
    private Element addElement(String name, String value, Element parent)
    {
        Document doc = parent.getOwnerDocument();
        Element ele = doc.createElement(name);
        if (value != null) ele.appendChild(doc.createTextNode(value));
        parent.appendChild(ele);
        return ele;
    }    
    
    /**
     *
     * @param id
     * @return
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
            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_documentError") + "...",e);
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
            Document res = getService(cmd, dom, paramRequest.getUser(), request, response,paramRequest);
            if (res == null)
            {
                ret = SWBUtils.XML.domToXml(getError(3));
            } else
                ret = SWBUtils.XML.domToXml(res, true);
        }catch(Exception e){log.error(e);}
        out.print(new String(ret.getBytes()));
        System.out.print(new String(ret.getBytes()));
        
    }
    
    
    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doScript(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();  
        out.println("<LINK href=\"/work/sites/WBAdmin/templates/7/1/images/wb3.css\" rel=\"stylesheet\" type=\"text/css\" >");
        out.println("<BODY bgcolor=\"#EDEFEC\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" >");
        out.println("<p class=\"status\">&nbsp;&nbsp;");
        out.println("</p>");
        out.println("</BODY>");
        out.println("</HTML>");
       
        out.println("<script language=\"javascript\" >");
        out.println("function wbTree_executeAction(action)");
        out.println("{");
        out.println("if(top.tree!=null)top.tree.document.applets[0].executeAction(action);");
        out.println("}");
        out.println("function wbTree_remove()");
        out.println("{");
        out.println("wbTree_executeAction('remove');");
        out.println("}");
        out.println("function wbTree_reload()");
        out.println("{");
        out.println("wbTree_executeAction('reload');");
        out.println("}");
        out.println("function wbStatus(msg)");
        out.println("{");
        out.println("top.frames[4].location='/wb/WBAdmin/WBAd_int_Status?stmsg='+msg;");	
        out.println("}");
        //out.println("wbStatus('');");
        out.println("</script>");
        
        out.println("\r\n<script>\r\n");
        //out.println("\r\nfunction UpdateTreeWF(){\r\n");            
        out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+".flows');\r\n");
        out.println("wbTree_reload();\r\n");
        out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+".flows."+request.getParameter("id")+"');\r\n");
        //out.println("\r\n}\r\n");            
        out.println("</script>\r\n");
        out.close();
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        System.out.println("doView: "+request.getParameter("suri"));
        String id = request.getParameter("suri");
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject go = ont.getGenericObject(id);
        PFlow pfgo = (PFlow)go;
        String tm=pfgo.getWebSite().getId();
        try
        {
            User user=paramRequest.getUser();
            PrintWriter out=response.getWriter();        
            String act="edit"; //view
            if(request.getParameter("act")!=null)
            {
                act=request.getParameter("act");
            }
            if(act.equals("remove"))
            {
                try
                {
                    if(id==null || id.equals("") || tm==null || tm.equals(""))
                    {
                        out.println("\r\n<script>\r\n");            
                        out.println("wbTree_executeAction('gotopath."+tm+".flows');\r\n");
                        out.println("wbTree_reload();\r\n");
                        out.println("</script>\r\n");                    
                        log.error(new Exception("The id was incorrect(remove) doview method"));
                        return;
                    }
                    //String tm=request.getParameter("tm");
                    //String id=request.getParameter("suri");
                    PFlow pflow=pfgo;//SWBContext.getWebSite(tm).getPFlow(id);
                    try
                    {
                        try
                        {
                            SWBContext.getWebSite(tm).removePFlow(id);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace(System.out);
                            log.error(e);
                        }                        
                        pflow.remove();
                        out.println("<script>wbTree_remove();</script>");
                        out.println(paramRequest.getLocaleString("msgRemove"));
                        out.println("\r\n<script>\r\n");            
                        //out.println("wbTree_executeAction('gotopath."+request.getParameter("tm")+".flows');\r\n");
                        //out.println("wbTree_reload();\r\n");
                        out.println("</script>\r\n");
                        return;
                    }
                    catch(Exception e)
                    {                    
                        out.println("\r\n<script>\r\n");            
                        out.println("wbTree_executeAction('gotopath."+tm+".flows');\r\n");
                        out.println("wbTree_reload();\r\n");
                        out.println("</script>\r\n");
                        e.printStackTrace(System.out);
                        log.error(e);
                        return;
                    }
                }
                catch(Exception e)
                {
                    out.println("\r\n<script>\r\n");            
                    out.println("wbTree_executeAction('gotopath."+tm+".flows');\r\n");
                    out.println("wbTree_reload();\r\n");
                    out.println("</script>\r\n");
                    e.printStackTrace(System.out);
                    log.error(e);
                    return;
                }            

            }
            else if(act.equals("add"))
            {
                out.println("<APPLET id=\"apptree\" name=\"editrole\" code=\"applets.workflowadmin.EditWorkflow.class\" codebase=\""+SWBPlatform.getContextPath()+"\" ARCHIVE=\"swbadmin/lib/WorkFlowAdmin.jar, swbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"350\">");
                SWBResourceURL url=paramRequest.getRenderUrl();
                url.setMode("gateway");
                url.setCallMethod(SWBResourceURL.Call_DIRECT);
                out.println("<PARAM NAME =\"cgipath\" VALUE=\""+url+"\">");
                out.println("<PARAM NAME =\"locale\" VALUE=\""+user.getLanguage()+"\">");                
                out.println("<PARAM NAME =\"tm\" VALUE=\""+tm+"\">");
                
                url=paramRequest.getRenderUrl();
                url.setMode("script");
                url.setCallMethod(url.Call_DIRECT);                
                out.println("<PARAM NAME =\"script\" VALUE=\""+url+"\">");
                
                out.println("</APPLET>");
                
            }      
            else if(act.equals("edit") && id!=null)
            {

                if(pfgo!=null&&pfgo.getXml()!=null&&pfgo.getXml().trim().length()==0)
                    pfgo.setXml("<workflows><workflow id=\""+id+"\" name=\""+pfgo.getTitle()+"\" version=\"1.0\"><description>"+pfgo.getDescription()+"</description></workflow></workflows>");
                out.println("<APPLET id=\"apptree\" name=\"editrole\" code=\"applets.workflowadmin.EditWorkflow.class\" codebase=\""+SWBPlatform.getContextPath()+"\" ARCHIVE=\"swbadmin/lib/WorkFlowAdmin.jar, swbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"350\">");
                SWBResourceURL url=paramRequest.getRenderUrl();
                url.setMode("gateway");
                url.setCallMethod(SWBResourceURL.Call_DIRECT);
                out.println("<PARAM NAME =\"idworkflow\" VALUE=\""+id+"\">");
                out.println("<PARAM NAME =\"cgipath\" VALUE=\""+url+"\">");
                out.println("<PARAM NAME =\"locale\" VALUE=\""+user.getLanguage()+"\">");
                out.println("<PARAM NAME =\"tm\" VALUE=\""+tm+"\">");
                url=paramRequest.getRenderUrl();
                url.setMode("script");
                url.setCallMethod(url.Call_DIRECT);                
                out.println("<PARAM NAME =\"script\" VALUE=\""+url+"\">");
                out.println("</APPLET>");               
            } 
            else if(act.equals("view") && id!=null && tm!=null)
            {

                String topicmap=tm;
                //String id=request.getParameter("id");
                PFlow pflow=pfgo;//SWBContext.getWebSite(topicmap).getPFlow(id);
                out.println("<html>");


                out.println("<TABLE width=\"95%\"  border\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#CCCCCC\">");
                out.println("<TR>");
                out.println("<TD>");
                out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                out.println("<TR>");
                out.println("<TD width=\"150\" align=\"right\" class=\"datos\">"+paramRequest.getLocaleString("msgIdentifier")+":</TD>");
                out.println("<TD class=\"valores\" align=left>");
                out.println(pflow.getId());

                out.println("</TD>");
                out.println("</TR>");
                out.println("</TABLE>");
                out.println("</TD>");
                out.println("</TR>");
                out.println("</TABLE></P>");
                out.println("<P>");

                out.println("<table width=\"95%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#CCCCCC\">");

                out.println("<TR>");
                out.println("<TD>");

                out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                out.println("<tr>");
                out.println("<td align=\"right\" class=\"datos\" width=\"150\">"+paramRequest.getLocaleString("msgName")+":</td>");
                out.println("<td class=\"valores\" align=left>");
                out.println(pflow.getTitle());
                out.println("</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td align=\"right\" class=\"datos\" width=\"150\">"+paramRequest.getLocaleString("msgDescription")+":</td>");
                out.println("<td class=\"valores\" align=left>");
                out.println(pflow.getDescription());
                out.println("</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td align=\"right\" class=\"datos\" width=\"150\">"+paramRequest.getLocaleString("msgCreated")+":</td>");
                out.println("<td class=\"valores\" align=left>");
                out.println(SWBUtils.TEXT.getStrDate(pflow.getCreated(),user.getLanguage()));
                out.println("</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td align=\"right\" class=\"datos\" width=\"150\">"+paramRequest.getLocaleString("msgLastUpdate")+":</td>");
                out.println("<td class=\"valores\" align=left>");
                out.println(SWBUtils.TEXT.getStrDate(pflow.getUpdated(),user.getLanguage()));
                out.println("</td>");
                out.println("</tr>");

               out.println("</table>");     
               out.println("</td>");
               out.println("</tr>");
               out.println("</table>");

               out.println("</html>");

            }
            else
            {
                out.println("\r\n<script>\r\n");            
                out.println("wbTree_executeAction('gotopath."+tm+".flows');\r\n");
                out.println("wbTree_reload();\r\n");
                out.println("</script>\r\n");                    
                log.error(new Exception("action no was defined '"+ act +"' doview method"));
                return;
            }
        }
        catch(Exception e)
        {                              
            e.printStackTrace(System.out);
            log.error(e);
            return;
        }
    }
    
    private class Content
    {
        String occurrence,topic,topicmap;
        /**
         *
         * @param token
         */        
        public Content(String token)
        {
            StringTokenizer st=new StringTokenizer(token,"|");
            if(st.countTokens()==3)
            {
                occurrence=st.nextToken();
                topic=st.nextToken();
                topicmap=st.nextToken();
            }
        }
        /**
         *
         * @return
         */        
        public String getOccurrence()
        {
            return occurrence;
        }
        /**
         *
         * @return
         */        
        public String getTopicID()
        {
            return topic;
        }
        /**
         *
         * @return
         */        
        public String getTopicMapID()
        {
            return topicmap;
        }
    }
    private class OrdenaUsuarios implements Comparator
    {
        /**
         *
         * @param o1
         * @param o2
         * @return
         */        
        public int compare(Object o1, Object o2) 
        {
            if(o1 instanceof User && o2 instanceof User)
            {
                User u1=(User)o1;
                User u2=(User)o2;
                return u1.getName().trim().toLowerCase().compareTo(u2.getName().trim().toLowerCase());
            }
            return 0;
        }
        
    }
    private class OrdenaRole implements Comparator
    {
        /**
         *
         * @param o1
         * @param o2
         * @return
         */        
        public int compare(Object o1, Object o2) 
        {
            if(o1 instanceof Role && o2 instanceof Role)
            {
                Role u1=(Role)o1;
                Role u2=(Role)o2;
                return u1.getTitle().trim().toLowerCase().compareTo(u2.getTitle().trim().toLowerCase());
            }
            return 0;
        }
        
    }

    public WorkflowResponse updatePflow(String tm, Document xml,String userid) throws SWBResourceException,Exception {
        //regreso inicial WorkflowResponse
        if(xml.getElementsByTagName("workflow").getLength()>0)
        {
            Element wf=(Element)xml.getElementsByTagName("workflow").item(0);
            String idpflow=wf.getAttribute("id");
            wf.setAttribute("id", idpflow+"_"+tm);
            String name=wf.getAttribute("name");
            String description="";
            if(wf.getElementsByTagName("description").getLength()>0) {
                Element edesc=(Element)wf.getElementsByTagName("description").item(0);
                Text etext=(Text)edesc.getFirstChild();
                description=etext.getNodeValue();
            }
            User user = SWBContext.getWebSite(tm).getUserRepository().getUser(userid);
            PFlow pflow=SWBContext.getWebSite(tm).getPFlow(idpflow);
            pflow.setCreator(user);
            pflow.setUpdated(new Timestamp(System.currentTimeMillis()));
            pflow.setDescription(description);
            pflow.setTitle(name);

            try {
                Document docworkflows=SWBUtils.XML.xmlToDom(pflow.getXml());
                if(docworkflows.getElementsByTagName("workflows").getLength()>0) {
                    Element workflows=(Element)docworkflows.getElementsByTagName("workflows").item(0);
                    Element nodewf=(Element)docworkflows.importNode(wf,true);
                    nodewf=(Element)workflows.insertBefore(nodewf,docworkflows.getElementsByTagName("workflow").item(0));
                    if(nodewf.getAttribute("version")!=null && !nodewf.getAttribute("version").equals(""))
                    {
                        String sversion=nodewf.getAttribute("version");
                        int iversion=(int)Double.parseDouble(sversion);
                        iversion++;
                        nodewf.setAttribute("version",iversion+ ".0");
                    }
                    try {
                        Document doc=SWBUtils.XML.getNewDocument();
                        doc.appendChild(doc.importNode(nodewf,true));

                        //TODO: WBProxyWorkflow.PublisFlow(doc)
                        //WorkflowResponse wresp=WBProxyWorkflow.PublisFlow(doc);
                        WorkflowResponse wresp = new WorkflowResponse("1",1);
                        Document docenc=SWBUtils.XML.xmlToDom(pflow.getXml());
                        NodeList nlWorkflows=docenc.getElementsByTagName("workflow");
                        int l=nlWorkflows.getLength();
                        switch(l)
                        {
                            case 0:
                            default:
                                Element oldworkflow=(Element)nlWorkflows.item(0);
                                int version=(int)Double.parseDouble(oldworkflow.getAttribute("version"));
                                version++;
                                nodewf.setAttribute("version",version+".0");
                        }
                        pflow.setXml(SWBUtils.XML.domToXml(docworkflows));
                        //pflow.update(userid,"The workflow was updated");
                        return wresp;
                    }
                    catch(Exception e) {
                        log.error(e);
                        e.printStackTrace(System.out);
                        throw e;
                    }
                }
                else {
                    throw new SWBResourceException("The xml has not a workflows element (updatePflow)");
                }
            }
            catch(Exception e) {
                log.error(e);
                throw e;
            }
        }
        else {
            throw new SWBResourceException("The xml has not a workflow element (updatePflow)");
        }

    }


}
