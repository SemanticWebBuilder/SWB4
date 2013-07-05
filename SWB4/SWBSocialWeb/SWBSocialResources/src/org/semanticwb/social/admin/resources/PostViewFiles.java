/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class PostViewFiles extends GenericResource{
    
    private static Logger log = SWBUtils.getLogger(PostViewFiles.class);
    
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        System.out.println("Entra a PostViewFiles/doView-1:"+request.getParameter("po"));
        try {
            String jspResponse=null;
            if(request.getParameter("po") != null) {
                SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("po"));
                System.out.println("Entra a PostViewFiles/doView-2:"+semObj);
                if(semObj!=null)
                {
                    ArrayList aPostImages=new ArrayList();
                    ArrayList aPostVideos=new ArrayList();
                    String path=SWBPortal.getWebWorkPath()+semObj.getWorkPath();
                    System.out.println("Entra a PostViewFiles/doView-3:"+path);
                    File postDirectory=new File(path);
                    if(postDirectory.exists() && postDirectory.isDirectory())
                    {
                        System.out.println("Entra a PostViewFiles/doView-4");
                        File[] directoryFiles=postDirectory.listFiles();
                        for(int i=0;i<directoryFiles.length;i++)
                        {
                            File file=directoryFiles[i];
                            System.out.println("Entra a PostViewFiles/doView-5:"+file);
                            if(file.isFile())
                            {
                                //Para Imagenes. Extensiones soportados en la ontología para subir en los Post
                                if(file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".gif")) 
                                {
                                    System.out.println("Entra a PostViewFiles/doView-6:"+file);
                                    aPostImages.add(file);
                                }else if(file.getName().endsWith(".mov")) 
                                {
                                    aPostVideos.add(file);
                                }
                            }
                        }
                        
                        jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/postViewFiles.jsp";
                        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
                        try {
                            request.setAttribute("imageList", aPostImages.iterator());
                            request.setAttribute("videoList", aPostVideos.iterator());
                            request.setAttribute("paramRequest", paramRequest);
                            System.out.println("Entra a PostViewFiles/doView-7:"+aPostImages);
                            dis.include(request, response);
                        } catch (Exception e) {
                            log.error(e);
                        }
                    }
                }
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }
    
}
