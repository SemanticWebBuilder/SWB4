<%-- 
    Document   : postViewFiles
    Created on : 01-ago-2013, 17:30:17
    Author     : jorge.jimenez
--%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%

if(request.getParameter("uri") != null) 
{
    SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("uri"));
    if(semObj!=null)
    {
        ArrayList aPostImages=new ArrayList();
        ArrayList aPostVideos=new ArrayList();
        String path=SWBPortal.getWorkPath()+semObj.getWorkPath();
        File postDirectory=new File(path);
        if(postDirectory.exists() && postDirectory.isDirectory())
        {
            File[] directoryFiles=postDirectory.listFiles();
            for(int i=0;i<directoryFiles.length;i++)
            {
                File file=directoryFiles[i];
                if(file.isFile())
                {
                    //Para Imagenes. Extensiones soportados en la ontología para subir en los Post
                    if(file.getName().toLowerCase().endsWith(".png") || file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".gif")) 
                    {
                        aPostImages.add(file);
                    }else if(file.getName().toLowerCase().endsWith(".mov") || file.getName().toLowerCase().endsWith(".avi") || file.getName().toLowerCase().endsWith(".wmv"))   //Ver que extensiones para video soportamos 
                    {
                        aPostVideos.add(file);
                    }
                }
            }
            
            //Despliegue de Imagenes
            if(aPostImages.size()>0) 
            {
                Iterator<File> imageList=aPostImages.iterator();
                while(imageList.hasNext())
                {
                    File file=imageList.next();
                    System.out.println("file:"+SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file.getName());
                    %>
                         <img src="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file.getName()%>"/>
                    <%
                }
            }
            
            //Despliegue de Videos
            
            if(aPostVideos.size()>0)
            {
                Iterator<File> videoList=aPostVideos.iterator();
                while(videoList.hasNext())
                {
                    File file=videoList.next();
                    System.out.println("file:"+SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file.getName());
                    %>
                        <object data="<%=file.getAbsolutePath()%>" type="video/quicktime" title="video" width="150" height="150">
                            <param name="pluginspage" value="http://quicktime.apple.com/">
                            <param name="autoplay" value="true">
                        </object>
                    <%
                }
            }
            
        }

    } 
}       
%>