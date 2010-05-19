/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.sip;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author hasdai
 */
public class TServices extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(TServices.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/swbadmin/jsp/sip/listServices.jsp";
        Resource base = getResourceBase();

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("maxServices", base.getAttribute("maxviews", "6").trim());
            request.setAttribute("parentSection", base.getAttribute("parentwp", "").trim());
            request.setAttribute("defCssIcon", base.getAttribute("defclass", "icono_8").trim());
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
