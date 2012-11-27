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

import java.io.*;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;
import org.semanticwb.base.util.ImageResizer;

public class DocumentResource extends org.semanticwb.portal.community.base.DocumentResourceBase {
    private static Logger log = SWBUtils.getLogger(PhotoResource.class);
    
    public DocumentResource()
    {
    }

    public DocumentResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String action = request.getParameter("act");
        if(action==null) {
            action = (String)request.getSession(true).getAttribute("act");
        }
        if(action==null) {
            action = "view";
        }

        String path="/swbadmin/jsp/microsite/DocumentResource/documentView.jsp";
        if(action.equals("add"))path="/swbadmin/jsp/microsite/DocumentResource/documentAdd.jsp";
        if(action.equals("edit"))path="/swbadmin/jsp/microsite/DocumentResource/documentEdit.jsp";
        if(action.equals("detail"))path="/swbadmin/jsp/microsite/DocumentResource/documentDetail.jsp";

        RequestDispatcher dis=request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }catch(Exception e){
            log.error(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebPage page = response.getWebPage();
        Member mem=Member.getMember(response.getUser(),response.getWebPage());
        if(!mem.canView())return;

        String action=request.getParameter("act");
        if("add".equalsIgnoreCase(action)) {
            try {
                add(request, response);
            }catch(Exception e) {
                throw new SWBResourceException(e.getMessage());
            }
        }else if("edit".equalsIgnoreCase(action)) {
            try {
                edit(request, response);
                response.setRenderParameter("act", "view");
            }catch(Exception e) {
                throw new SWBResourceException(e.getMessage());
            }
        }else if("remove".equals(action)) {
            String uri = request.getParameter("uri");
            DocumentElement doc = (DocumentElement)SemanticObject.createSemanticObject(uri).createGenericInstance();
            if(doc!=null && doc.canModify(mem)) {
                doc.remove();
            }
        }else {
            super.processAction(request, response);
        }
    }

    private void add(HttpServletRequest request, SWBActionResponse response) throws Exception {
        WebPage page = response.getWebPage();
        Member mem = Member.getMember(response.getUser(), page);
        if(!mem.canAdd()) return;

        DocumentElement doc = null;
        HashMap<String, String> params = new HashMap<String,String>();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart) {
            File tmpwrk = new File(SWBPortal.getWorkPath()+"/tmp");
            if (!tmpwrk.exists()) {
                tmpwrk.mkdirs();
            }
            FileItemFactory factory = new DiskFileItemFactory(1*1024*1024, tmpwrk);
            ServletFileUpload upload = new ServletFileUpload(factory);
            ProgressListener progressListener = new ProgressListener() {
                private long kBytes = -1;
                public void update(long pBytesRead, long pContentLength, int pItems) {
                    long mBytes = pBytesRead / 10000;
                    if (kBytes == mBytes) {
                    return;
                    }
                    kBytes = mBytes;
                    int percent = (int)(pBytesRead * 100 / pContentLength);
                }
            };
            upload.setProgressListener(progressListener);
            List items = null;
            try {
                items = upload.parseRequest(request);
            }catch(FileUploadException fue) {
                throw fue;
            }
            FileItem currentFile = null;
            Iterator iter = items.iterator();
            while(iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if(item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString();
                    params.put(name, value);
                }else {
                    currentFile = item;

                    doc = DocumentElement.ClassMgr.createDocumentElement(getResourceBase().getWebSite());
                    String path = SWBPortal.getWorkPath() + doc.getWorkPath();
                    File file = new File(path);
                    if(!file.exists()) {
                        file.mkdirs();
                    }
                    long serial = (new Date()).getTime();
                    String filename = null;
                    try {
                        filename = serial + currentFile.getName().substring(currentFile.getName().lastIndexOf("."));
                        file = new File(path +"/"+ filename);
                        currentFile.write(file);
                        //params.put("filename", doc.getWorkPath()+"/"+filename);
                        params.put("filename", filename);
                    }catch(StringIndexOutOfBoundsException iobe) {
                    }                    
                }
            }
        }
        if(doc!=null) {
            doc.setDocumentURL(params.get("filename"));
            doc.setTitle(params.get("title"));
            doc.setDescription(params.get("description"));
            doc.setTags(params.get("tags"));
            doc.setVisibility(Integer.parseInt(params.get("level")));
            if(page instanceof MicroSiteWebPageUtil) {
                ((MicroSiteWebPageUtil)page).sendNotification(doc);
            }
            doc.setDocumentWebPage(page);
        }
    }

    protected void edit(HttpServletRequest request, SWBActionResponse response) throws Exception {
        WebPage page = response.getWebPage();
        Member mem = Member.getMember(response.getUser(), page);

        String uri = request.getParameter("uri");
        DocumentElement doc = (DocumentElement)SemanticObject.createSemanticObject(uri).createGenericInstance();
        if(doc==null || !doc.canModify(mem)) return;
        
        HashMap<String, String> params = new HashMap<String,String>();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart) {
            File tmpwrk = new File(SWBPortal.getWorkPath()+"/tmp");
            if (!tmpwrk.exists()) {
                tmpwrk.mkdirs();
            }
            FileItemFactory factory = new DiskFileItemFactory(1*1024*1024, tmpwrk);
            ServletFileUpload upload = new ServletFileUpload(factory);
            ProgressListener progressListener = new ProgressListener() {
                private long kBytes = -1;
                public void update(long pBytesRead, long pContentLength, int pItems) {
                    long mBytes = pBytesRead / 10000;
                    if (kBytes == mBytes) {
                    return;
                    }
                    kBytes = mBytes;
                    int percent = (int)(pBytesRead * 100 / pContentLength);
                }
            };
            upload.setProgressListener(progressListener);
            List items = null;
            try {
                items = upload.parseRequest(request);
            }catch(FileUploadException fue) {
                throw fue;
            }
            FileItem currentFile = null;
            Iterator iter = items.iterator();
            while(iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if(item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString();
                    params.put(name, value);
                }else {
                    currentFile = item;
                    String path = SWBPortal.getWorkPath() + doc.getWorkPath();
                    long serial = (new Date()).getTime();
                    String filename = null;
                    try {
                        filename = serial + currentFile.getName().substring(currentFile.getName().lastIndexOf("."));
                        File file = new File(path +"/"+ filename);
                        currentFile.write(file);
                        //params.put("filename", doc.getWorkPath()+"/"+filename);
                        params.put("filename", filename);
                    }catch(StringIndexOutOfBoundsException iobe) {
                    }                    
                }
            }
        }

        if(params.containsKey("filename")) {
            //String rp = SWBPortal.getWorkPath()+doc.getDocumentURL();
            String rp = SWBPortal.getWorkPath()+doc.getWorkPath()+"/"+doc.getDocumentURL();
            File f = new File(rp);
            if(f!=null && f.exists()) {
                f.delete();
            }
            doc.setDocumentURL(params.get("filename"));
        }
        if(params.containsKey("title"))
            doc.setTitle(params.get("title"));
        if(params.containsKey("description"))
            doc.setDescription(params.get("description"));
        if(params.containsKey("tags"))
            doc.setTags(params.get("tags"));
        if(params.containsKey("level"))
            doc.setVisibility(Integer.parseInt(params.get("level")));
        if(page instanceof MicroSiteWebPageUtil) {
            ((MicroSiteWebPageUtil)page).sendNotification(doc);
        }
    }
}
