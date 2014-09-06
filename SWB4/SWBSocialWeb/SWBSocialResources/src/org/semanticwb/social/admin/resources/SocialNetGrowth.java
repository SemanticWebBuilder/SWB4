/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.SocialNetwork;
import static org.semanticwb.social.admin.resources.FacebookInteraction.log;

/**
 *
 * @author francisco.jimenez
 */
public class SocialNetGrowth extends GenericResource{

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (request.getParameter("doView") == null) {
            doEdit(request, response, paramRequest);
            return;
        }
        /*
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        System.out.println("SocialNetGrowth....");
        if(SemanticObject.getSemanticObject(suri) != null){
            System.out.println("Social Net 1");
            SocialNetwork socialNet = (SocialNetwork)SemanticObject.getSemanticObject(suri).createGenericInstance();
            Connection con = SWBUtils.DB.getDefaultConnection();
    
            String sql = "select * from socialnets_stats where socialNet=?";
            
            try{
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, socialNet.getURI());                
                ResultSet rs = st.executeQuery();
                
                
                while(rs.next()){
                    out.println("<p>");
                    out.println("socialsite: " + rs.getString("socialsite"));
                    out.println("socialNet: " + rs.getString("socialNet"));
                    out.println("friends: " + rs.getInt("friends"));
                    out.println("followers: " + rs.getInt("followers"));
                    out.println("ptat: " + rs.getInt("ptat"));
                    out.println("date " + rs.getTimestamp("date"));
                    out.println("</p>");
                }
            }catch(SQLException sqle){
                System.out.println("error....." +  sqle.getMessage());
            }
            
            out.println("<script type=\"text/javascript\">");
            out.println("   alert('hola mundo');");
            out.println("</script>");
            
        }*/
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/growthCharts.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {
                request.setAttribute("paramRequest", paramRequest);
                request.setAttribute("suri", request.getParameter("suri"));
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
            }
        }

    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<iframe width=\"100%\" height=\"100%\" src=\"" + paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW).setParameter("doView", "1").setParameter("suri", request.getParameter("suri")) + "\"></iframe> ");
    }
    
    
}
