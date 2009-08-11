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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.infotec.wb.resources;

import java.io.BufferedReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericXformsResource;
import org.semanticwb.portal.api.SWBParamRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;

/**
 *
 * @author jorge.jimenez
 */
public class HelloXforms extends GenericXformsResource
{
    private static Logger log = SWBUtils.getLogger(GenericXformsResource.class);
 /*   
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String action=paramRequest.getAction();
        if(action!=null && !action.equals("update")) {
            super.doView(request, response, paramRequest);
        }
        else //Si es update
        {
            //lee datos enviados por post
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                System.out.println("line:"+line);
            }
            Document dom=SWBUtils.XML.xmlToDom(buffer.toString());*/
            /*
            String siteId=dom.getElementsByTagName("siteId").item(0).getFirstChild().getNodeValue(); {
                StringBuffer strb=new StringBuffer();
                strb.append("<script language=\"JavaScript\">");
                strb.append("  alert(\'El id del sitio ya existe..');");
                strb.append("  document.forma.tmid.focus();");
                strb.append("</script>");
                response.getOutputStream().write(strb.toString().getBytes());
            }
             *//*
            request.setAttribute("instance","2");
            String xformsFiles=getClass().getName()+"2";
            setData(request, response, paramRequest, xformsFiles, "add");
        }
    }*/
    /*
    public void setData(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest, String xformsFiles, String action) {
        //Carga archivo xml de formulario y lo agrega como atributo del request
        request.setAttribute("xformsDoc", SWBUtils.XML.xmlToDom(loadXform(getClass().getName(), xformsFiles, paramsRequest.getUser().getLanguage())));
        //Carga archivo xml de instancia del formulario
        loadXform(getClass().getName(), xformsFiles+"_inst",paramsRequest.getUser().getLanguage());
        request.setAttribute("instanceName", xformsFiles+"_inst");  //Agrega instancia del formulario como atributo del request
        request.setAttribute("action",action); //Agrega una acción como atributo del request
        try{
            super.doView(request, response, paramsRequest); //Se manda llamar metodo de clase padre
        }catch(Exception e){
            log.error(e);
        }
    }
    */
    /**
     * Carga instancia, ya sea la de inicio o una ya grabada en BD del recurso en cuestión.
     */
    /*
    public void doLoadInstance(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) {
        try{
            System.out.println("instance attr:"+request.getParameter("instance"));
            if(request.getParameter("wbmode")!=null && request.getParameter("wbmode").equals("view")) { //Se desea la instancia de vista (Front-End)
                String xml=null;
                if(request.getParameter("instance")==null){
                    xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<data>" +
                    "     <title>Titulo 1</title>" +
                    "     <description/>" +
                    "     <siteId/>" +
                    "     <homeTitle/>" +
                    "</data>" ;
                }else{
                    xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<data>" +
                    "     <title2>titulo 2</title2>" +
                    "     <description2/>" +
                    "     <siteId2/>" +
                    "     <homeTitle/>" +
                    "</data>" ;
                }
                //response.getOutputStream().write(xml.getBytes());
                response.getWriter().print(xml);
            }else{ //Se desea la instancia de Administración
                
            }
        }catch(Exception e){
            log.error(e);
        }
        return;
    }
     * */
}
