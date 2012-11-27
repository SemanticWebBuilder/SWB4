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
package org.semanticwb.portal.community;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.base.util.ImageResizer;

public class MembershipResource extends org.semanticwb.portal.community.base.MembershipResourceBase 
{
    private static Logger log=SWBUtils.getLogger(MembershipResource.class);

    public MembershipResource()
    {
    }

    public MembershipResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        


        String act=request.getParameter("act");
        if(act==null)act="view";

        String path="/swbadmin/jsp/microsite/MembershipResource/membershipResView.jsp";
        if(act.equals("edit"))path="/swbadmin/jsp/microsite/MembershipResource/edit.jsp";


        if(request.getParameter("iframe")!=null)
        {
            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setParameter("act",act);
            url.setMode(SWBResourceURL.Mode_VIEW);
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println(" window.parent.location='"+url+"';");
            out.println("</script>");
            return;
        }


        RequestDispatcher dis=request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }catch(Exception e){log.error(e);}

        
    }

    private Member getMember(User user, MicroSite site)
    {
        //System.out.println("getMember:"+user+" "+site);
        if(site!=null)
        {
            Iterator<Member> it=Member.ClassMgr.listMemberByUser(user,site.getWebSite());
            while(it.hasNext())
            {
                Member mem=it.next();
                //System.out.println("mem:"+mem+" "+mem.getMicroSite());
                if(mem.getMicroSite().equals(site))
                {
                   return mem;
                }
            }
        }
        return null;
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        WebPage page=response.getWebPage();
        User user=response.getUser();
        //System.out.println("user:"+user);
        String action=request.getParameter("act");
        //System.out.println("act:"+action);
        if("subscribe".equals(action))
        {
            Member member=Member.ClassMgr.createMember(page.getWebSite());
            MicroSite micro = MicroSite.getMicroSite(page);
            if(micro.getCreator().equals(user)) member.setAccessLevel(Member.LEVEL_OWNER);
            else member.setAccessLevel(Member.LEVEL_EDIT);
            member.setUser(user);
            member.setMicroSite(micro);
            //System.out.println("member:"+member);
        }else if("unsubscribe".equals(action))
        {
            Member member=Member.getMember(user, page);
            member.remove();
        } else if ("upload".equals(action) && user.isSigned())
        {
            if(page.getSemanticObject().getGenericInstance() instanceof MicroSite)
            {
                MicroSite ms = (MicroSite)page.getSemanticObject().getGenericInstance();
                final Percentage per = new Percentage();
                try
                {
                    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                    HashMap<String, String> params = new HashMap<String, String>();
                    // Create a factory for disk-based file items
                    File tmpwrk = new File(SWBPortal.getWorkPath() + "/tmp");
                    if (!tmpwrk.exists())
                    {
                        tmpwrk.mkdirs();
                    }
                    FileItemFactory factory = new DiskFileItemFactory(1 * 1024 * 1024, tmpwrk);
                    // Create a new file upload handler
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    //Create a progress listener
                    ProgressListener progressListener = new ProgressListener()
                    {

                        private long kBytes = -1;

                        public void update(long pBytesRead, long pContentLength, int pItems)
                        {
                            long mBytes = pBytesRead / 10000;
                            if (kBytes == mBytes)
                            {
                                return;
                            }
                            kBytes = mBytes;
                            int percent = (int) (pBytesRead * 100 / pContentLength);
                            per.setPercentage(percent);
                        }
                    };
                    upload.setProgressListener(progressListener);
                    // Parse the request
                    List items = upload.parseRequest(request); /* FileItem */
                    FileItem currentFile = null;
                    // Process the uploaded items
                    Iterator iter = items.iterator();
                    while (iter.hasNext())
                    {
                        FileItem item = (FileItem) iter.next();

                        if (item.isFormField())
                        {
                            String name = item.getFieldName();
                            String value = item.getString();
                            params.put(name, value);
                        } else
                        {
                            currentFile = item;
    //                        String fieldName = item.getFieldName();
    //                        String fileName = item.getName();
    //                        String contentType = item.getContentType();
    //                        boolean isInMemory = item.isInMemory();
    //                        long sizeInBytes = item.getSize();
    //                        File uploadedFile = new File();
    //                        item.write(uploadedFile);
                        }
                    }
                    request.getSession(true).setAttribute(currentFile.getFieldName(), per);

                    String path = SWBPortal.getWorkPath() + page.getWorkPath();
                    File file = new File(path);
                    if (!file.exists())
                    {
                        file.mkdirs();
                    }
                    String name = ms.getId() + currentFile.getName().substring(currentFile.getName().lastIndexOf("."));
                    String photoName = path + "/" + name;
                    currentFile.write(new File(photoName));
                    path = page.getWorkPath();


                    
                    ms.setPhoto(name);
                    per.setPercentage(100);
                    File f = new File(photoName);

                    /*                BufferedImage bi = ImageIO.read(f);
                    int calcHeight = (150 * bi.getHeight() / bi.getWidth());
                    ImageIO.write(createResizedCopy(bi, 150, calcHeight), name.substring(name.lastIndexOf(".")+1), f);*/

                    ImageResizer.resizeCrop(f, 174, f, name.substring(name.lastIndexOf(".") + 1).toLowerCase());


                } catch (Exception ex)
                {
                    log.error(ex);
                }
            }
            response.setRenderParameter("iframe", "true");
            response.setRenderParameter("act", "view");
            response.setMode(SWBActionResponse.Mode_VIEW);
            response.setCallMethod(SWBActionResponse.Call_STRATEGY);
        }
    }

        @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Percentage pers = (Percentage) request.getSession(true).getAttribute(request.getParameter("sid"));
        PrintWriter out = response.getWriter();
        if (null != pers)
        {
            out.println(pers.getPercentage());
        } else
        {
            out.println(0);
        }
    }

    private class Percentage
    {

        int per = 0;

        public void setPercentage(int per)
        {
            this.per = per;
        }

        public int getPercentage()
        {
            return per;
        }
    }
}
