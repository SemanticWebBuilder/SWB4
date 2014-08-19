package org.semanticwb.bsc.resources.maps;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author carlos.ramos
 */
public class ImpactMap extends GenericResource {
    private final Logger log = SWBUtils.getLogger(ImpactMap.class);
    public static String path;

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        path = SWBPlatform.getContextPath()+"/swbadmin/jsp/swbstrgy/maps/"+this.getClass().getSimpleName()+"/view.jsp";
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("this", this);
            dis.include(request, response);
        }catch(ServletException e) {
            log.error(e);
        }catch(IOException e) {
            log.error(e);
        }catch(NullPointerException e) {
            log.error(e);
        }
    }
}
