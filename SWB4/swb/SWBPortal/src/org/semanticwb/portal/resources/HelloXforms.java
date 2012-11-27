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
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericXformsResource;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;

// TODO: Auto-generated Javadoc
/**
 * The Class HelloXforms.
 * 
 * @author jorge.jimenez
 */
public class HelloXforms extends GenericXformsResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(GenericXformsResource.class);
    
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericXformsResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        
        out.println("<script type=\"text/javascript\">" +
		" dojo.require(\"dojox.widget.ColorPicker\"); " +
		" dojo.require(\"dojo.parser\"); "+
		" var handler = function(val,id){ " +
			" dojo.byId(id).value = val; " +
		"};"+		
	"</script>" +  
        "</head>" +
        " <body class=\"tundra\"> " +
        "<div id=\"picker\" dojoType=\"dojox.widget.ColorPicker\"" +
		"onChange=\"handler(arguments[0],'onchangeOne')\"></div>" +
	"<p>Current value: <input readonly=\"true\" id=\"onchangeOne\" value=\"???\"/></p>" +
       "</body>"+
       "</html>");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericXformsResource#saveData(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest, org.w3c.dom.Document)
     */
    @Override
    public void saveData(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest, Document dom) throws SWBResourceException, IOException {
        if (request.getAttribute("wbmode").equals(paramsRequest.Mode_VIEW)) //Cuando es de vista 
        {
            if (request.getParameter("replaceVal") != null && request.getParameter("replaceVal").equals("instance")) {
                String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<data>" +
                        "     <name>George24 View</name>" +
                        "</data>";
                response.getWriter().print(xml);
            } else {
                request.setAttribute("instance", "2");
                String xformsFiles = getClass().getName() + "2_V";
                setData(request, response, paramsRequest, xformsFiles, "add");
            }
        } else { //Cuando es de admin
            if (request.getParameter("replaceVal") != null && request.getParameter("replaceVal").equals("instance")) {
                String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<data>" +
                        "     <name>George25 admin</name>" +
                        "</data>";
                response.getWriter().print(xml);
            } else {
                request.setAttribute("instance", "2");
                String xformsFiles = getClass().getName() + "2";
                setData(request, response, paramsRequest, xformsFiles, "add");
            }
        }
    }

    /**
     * Carga instancia, ya sea la de inicio o una ya grabada en BD del recurso en cuestión.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     */
    @Override
    public void doLoadInstance(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) {
        try {
            String xml = null;
            if (request.getParameter("wbmode") != null && request.getParameter("wbmode").equals(paramsRequest.Mode_VIEW)) { //Se desea la instancia de vista (Front-End)
                if (request.getParameter("instance") == null) {
                    xml = "<data><name>hola View-1</name></data>";
                } else {
                    xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                            "<data>" +
                            "     <name>Hola view-2</name>" +
                            "</data>";
                }
            } else { //Se desea la instancia de Administración
                if (request.getParameter("instance") == null) {
                    //xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    xml = "<data><name>hola Admin-1</name></data>";
                } else {
                    xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                            "<data>" +
                            "     <name>Hola Admin-2</name>" +
                            "</data>";
                }
            }
            response.getWriter().print(xml);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
