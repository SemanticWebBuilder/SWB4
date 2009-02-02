/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources.forum;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class UploadTest extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String action=paramRequest.getAction();
        try{
            System.out.println("UploadTest J/Action:"+action);
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd=request.getRequestDispatcher("/swbadmin/jsp/upload/uploadTest.jsp");
            rd.include(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
