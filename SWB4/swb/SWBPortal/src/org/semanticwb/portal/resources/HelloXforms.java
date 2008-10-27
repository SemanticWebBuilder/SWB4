/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericXformsResource;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;

/**
 *
 * @author jorge.jimenez
 */
public class HelloXforms extends GenericXformsResource {

    private static Logger log = SWBUtils.getLogger(GenericXformsResource.class);

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
