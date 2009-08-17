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

package org.semanticwb.portal.community.utilresources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.Subject;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author serch
 */
public class RegisterUser extends GenericResource {

    private static Logger log=SWBUtils.getLogger(RegisterUser.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Iterator<SemanticProperty> list = paramRequest.getUser().getUserRepository().listExtendedAttributes();
        while (list.hasNext()) {System.out.println(list.next().getURI());}
        String act=request.getParameter("act");
        if(act==null) {
            act="view";
            if (paramRequest.getUser().isSigned()) act = "edit";
        }

        String path="/swbadmin/jsp/microsite/RegisterUser/linkNewUser.groovy";
        if(act.equals("add"))path="/swbadmin/jsp/microsite/RegisterUser/newUser.groovy";
        if(act.equals("edit"))path="/swbadmin/jsp/microsite/RegisterUser/userEditForm.groovy";
        if(act.equals("detail"))path="/swbadmin/jsp/microsite/RegisterUser/userDetail.groovy";

        RequestDispatcher dis=request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }catch(Exception e){log.error(e);}
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        UserRepository ur = response.getWebPage().getWebSite().getUserRepository();
        User user = response.getUser();
        String login = request.getParameter("login");
        if ("create".equals(response.getAction()) && (null!=login) &&(!user.isSigned()) && (null == ur.getUserByLogin(login)))
        {
            Subject subject = SWBPortal.getUserMgr().getSubject(request, response.getWebPage().getWebSiteId());
            User newUser = ur.createUser();
                newUser.setLogin(login);
                subject.getPrincipals().clear();
                subject.getPrincipals().add(newUser);
                newUser.setLanguage(user.getLanguage());
                newUser.setIp(user.getIp());
                newUser.setActive(true);
                newUser.setDevice(user.getDevice());
                String pwd = request.getParameter("passwd");
                newUser.setPassword(pwd);
                try {newUser.checkCredential(pwd.toCharArray());} catch (Exception ne) {}
                user = newUser; 
        }
        if ("edit".equals(response.getAction())&& user.isSigned())
        {
            user.setFirstName(request.getParameter("usrFirstName"));
            user.setLastName(request.getParameter("usrLastName"));
            user.setSecondLastName(request.getParameter("usrSecondLastName"));
            user.setEmail(request.getParameter("usrEmail"));
            try {
                user.setExtendedAttribute("userAge", request.getParameter("userAge"));
                user.setExtendedAttribute("userSex", request.getParameter("userSex"));
                user.setExtendedAttribute("userStatus", request.getParameter("userStatus"));
                user.setExtendedAttribute("userInterest", request.getParameter("userInterest"));
                user.setExtendedAttribute("userHobbies", request.getParameter("userHobbies"));
                user.setExtendedAttribute("userInciso", request.getParameter("userInciso"));
            } catch (SWBException nex) {log.error(nex);}
            response.sendRedirect(response.getWebPage().getRealUrl()+"?act=detail");
            return;
        }
        if ("upload".equals(response.getAction()) && user.isSigned())
        {
            final Percentage per = new Percentage();
        try
            {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                HashMap<String,String> params = new HashMap<String,String>();
                // Create a factory for disk-based file items
                File tmpwrk = new File(SWBPlatform.getWorkPath()+"/tmp");
                if (!tmpwrk.exists()) tmpwrk.mkdirs();
                FileItemFactory factory = new DiskFileItemFactory(1*1024*1024,tmpwrk);
                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
                //Create a progress listener
                ProgressListener progressListener = new ProgressListener(){
                    private long kBytes = -1;
                public void update(long pBytesRead, long pContentLength, int pItems) {
                    long mBytes = pBytesRead / 10000;
                    if (kBytes == mBytes) {
                    return;
                    }
                    kBytes = mBytes;
                    int percent = (int)(pBytesRead * 100 / pContentLength);
                    per.setPercentage(percent);
                }
                };
                upload.setProgressListener(progressListener);
                // Parse the request
                List items = upload.parseRequest(request); /* FileItem */
                FileItem currentFile = null;
                // Process the uploaded items
                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        String value = item.getString();
                        params.put(name, value);
                    } else {
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

                String path = SWBPlatform.getWorkPath()+user.getWorkPath();
                File file = new File(path);
                if (!file.exists()) file.mkdirs();
                String name = user.getLogin()+currentFile.getName().substring(currentFile.getName().lastIndexOf("."));
                currentFile.write(new File(path+"/"+name));
                path = SWBPlatform.getWebWorkPath()+user.getWorkPath();
                user.setPhoto(path+"/"+name);
                per.setPercentage(100);

            } catch (Exception ex)
            {
                log.error(ex);
            }
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        SWBResourceURL url  = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_CONTENT
                ).setMode(SWBResourceURL.Mode_VIEW).setParameter("act", "edit");
        StringBuffer ret = new StringBuffer();
        ret.append("<script type=\"text/javascript\">\ndijit.byId('swbDialog').hide();\n");
        ret.append("location.href='"+url+"';\n");
        ret.append("</script>");
        response.getWriter().write(ret.toString());
    }

    @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Percentage pers = (Percentage) request.getSession(true).getAttribute(request.getParameter("sid"));
            PrintWriter out = response.getWriter();
            if (null!=pers)
            {
                out.println(pers.getPercentage());
            }
            else
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

