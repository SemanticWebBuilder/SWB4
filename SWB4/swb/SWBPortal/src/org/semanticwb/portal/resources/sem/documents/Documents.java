package org.semanticwb.portal.resources.sem.documents;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class Documents extends org.semanticwb.portal.resources.sem.documents.base.DocumentsBase {
    private static Logger log = SWBUtils.getLogger(Documents.class);

    private static final int DEFAULT_BUFFER_SIZE = 2048; // 2KB.

//    private static String webWorkPath;

    public Documents() {
    }

    public Documents(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

//    @Override
//    public void setResourceBase(Resource base) {
//        try {
//            super.setResourceBase(base);
//        } catch (Exception e) {
//            log.error("Error while setting resource base: " + base.getId() + "-" + base.getTitle(), e);
//        }
//        webWorkPath = SWBPortal.getWebWorkPath()+base.getWorkPath() + "/";
//    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.println(renderListDocuments(paramRequest));

        SWBResourceURL addURL = paramRequest.getActionUrl().setAction(paramRequest.Action_ADD);
        
        out.println("<script type=\"text/javascript\">");
        out.println("<!--");

        out.println("function validaForma(frm) {");
        out.println("  var msg = new Array();");
        out.println("  if( isEmpty(frm.docto.value) )");
        out.println("      msg.push('El archivo es requerido');");
        out.println("  if( isEmpty(frm.title.value) )");
        out.println("      msg.push('El título del documento es requerido');");
        out.println("  if( isEmpty(frm.description.value) )");
        out.println("      msg.push('La descripción es requerida');");
        out.println("  if(msg.length>0) {");
        out.println("      alert(msg.join('\\n'));");
        out.println("      return false;");
        out.println("  }else");
        out.println("      return true;");
        out.println("}");

        out.println("function validaForma() {");
        out.println("  var docto = document.frmadddoc.title.value;");
        out.println("  if(!docto) {");
        out.println("    alert('¡Debe ingresar el archivo del documento!');");
        out.println("    document.frmadddoc.docto.focus();");
        out.println("    return;");
        out.println("  }");
        out.println("  var title = document.frmadddoc.title.value;");
        out.println("  if(!title) {");
        out.println("    alert('¡Debe ingresar el título del documento!');");
        out.println("    document.frmadddoc.description.focus();");
        out.println("    return;");
        out.println("  }");
        out.println("  var description = document.frmadddoc.description.value;");
        out.println("  if(!description) {");
        out.println("    alert('¡Debe ingresar la description del documento!');");
        out.println("    document.frmadddoc.description.focus();");
        out.println("    return;");
        out.println("  }");
        out.println("  document.frmadddoc.submit();");
        out.println("}");

        out.println("function validateRemoveDoctoElement(url) {");
        out.println("  if(confirm('¿Eliminar el documento?')) {");
        out.println("    window.location.href=url;");
        out.println("  }");
        out.println("}");

        out.println("function displayDocto(url, title, size) {");
        out.println("  window.open(url, title, size);    ");
        out.println("}");
        out.println("-->");
        out.println("</script>");
        
        out.println("<div class=\"\">");
        out.println(" <div class=\"adminTools\">");
        out.println("  <a class=\"adminTool\" onclick=\"validaForma()\" href=\"#\">Guardar</a>");
        out.println("  <a class=\"adminTool\" href=\""+paramRequest.getRenderUrl()+"\">Cancelar</a>");
        out.println(" </div>");
        out.println(" <form name=\"frmadddoc\" id=\"frmadddoc\" enctype=\"multipart/form-data\" method=\"post\" action=\""+addURL+"\">");
        out.println("  <div>");
        out.println("  <fieldset>");
        out.println("   <legend>Agregar documento</legend>");
        out.println("   <div>");
        out.println("    <p>");
        out.println("     <label for=\"docto\">Documento:&nbsp;</label><br />");
        out.println("     <input id=\"docto\" type=\"file\" name=\"docto\" size=\"45\" />");
        out.println("     <span class=\"swb-doctos-warn\">"+(request.getParameter("msgErrFilename")==null?"":request.getParameter("msgErrFilename"))+"</span>");
        out.println("    </p>");
        out.println("    <p>");
        out.println("     <label for=\"title\">Título:&nbsp;</label><br />");
        out.println("     <input id=\"title\" type=\"text\" name=\"title\" maxlength=\"50\" size=\"45\" />");
        out.println("     <span class=\"swb-doctos-warn\">"+(request.getParameter("msgErrTitle")==null?"":request.getParameter("msgErrTitle"))+"</span>");
        out.println("    </p>");
        out.println("    <p>");
        out.println("     <label for=\"description\">Descripción</label><br />");
        out.println("     <textarea id=\"description\" cols=\"45\" rows=\"3\" name=\"description\"></textarea>");
        out.println("     <span class=\"swb-doctos-warn\">"+(request.getParameter("msgErrDesc")==null?"":request.getParameter("msgErrDesc"))+"</span>");
        out.println("    </p>");
        out.println("    <p>");
        out.println("     <input type=\"submit\" value=\"Aceptar\" onclick=\"return validaForma(this.form)\" />");
        out.println("     <input type=\"reset\" value=\"Restablecer\" />");
        out.println("    </p>");
        out.println("   </div>");
        out.println("  </fieldset>");
        out.println("  <fieldset>");
        out.println("   <legend>¿Quién puede ver este documento?</legend>");
        out.println("   <div>");
        out.println("    <p><label for=\"scope0\"><input type=\"radio\" name=\"scope\" id=\"scope0\" value=\"false\" />&nbsp;Cualquiera</label></p>");
        out.println("    <p><label for=\"scope1\"><input type=\"radio\" name=\"scope\" id=\"scope1\" value=\"true\" checked=\"checked\" />&nbsp;Sólo miembros del comité</label></p>");
        out.println("   </div>");
        out.println("  </fieldset>        ");
        out.println("  </div>");
        out.println(" </form>");
        out.println("</div>");
        out.flush();
        out.close();
    }

    private String renderListDocuments(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder html = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy | HH:mm");
        //long ordinal = SWBUtils.Collections.sizeOf(listComments());
        //int ordinal = 1;
        SWBResourceURL url = paramRequest.getActionUrl().setAction(paramRequest.Action_REMOVE);
        SWBResourceURL dplyURL = paramRequest.getRenderUrl().setMode(paramRequest.Mode_HELP).setCallMethod(paramRequest.Call_DIRECT);
        
        html.append("<div class=\"swb-comentario-sem-lista\">");
        Iterator<Document> itDocuments = SWBComparator.sortByCreated(listDocuments(),false);
        if(itDocuments.hasNext()) {
            html.append("<h2>documentos</h2>");
            html.append("<ol>");
            User user = paramRequest.getUser();
            UserGroup ug = user.getUserGroup();
            while(itDocuments.hasNext()) {
                Document document = itDocuments.next();
                if(document.getCreator()==null)
                    continue;
                if( !document.getCreator().getUserGroup().equals(ug) && document.isHidden() )
                    continue;
                html.append("<li>");
                html.append("<p>"+document.getTitle()+"</p>");
                html.append("<p>"+document.getDescription()+"</p>");
                //html.append("<p><a onclick=\"displayDocto('"+dplyURL.setParameter("uri", document.getURI())+"', '"+document.getFilename()+"', 'width=640, height=480, scrollbars, resizable, alwaysRaised, menubar')\" href=\"#\" title=\"ver documento\">"+document.getFilename()+"</a></p>");
                html.append("<p><a href=\""+dplyURL.setParameter("uri", document.getURI())+"\" title=\"ver documento\">"+document.getFilename()+"</a></p>");
                if( document.getCreator().equals(user) ) {
                    html.append("<p><a onclick=\"validateRemoveDoctoElement('"+url.setParameter("uri",document.getURI())+"')\" href=\"#\">Eliminar</a></p>");
                }else
                    html.append("<p>Autor: "+(document.getCreator()==null?"Anónimo":document.getCreator().getFullName())+". "+sdf.format(document.getCreated())+"</p>");
                html.append("<p><a href=\"wp61?uri="+document.getEncodedURI()+"\">Comentar</a></p>");
                html.append("</li>");
            }
            html.append("</ol>");
        }else
            html.append("<!-- no hay documentos -->");
        html.append("</div>");
        return html.toString();
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        System.out.println("processAction....");
        if(action.equalsIgnoreCase(response.Action_ADD)) {
            try {
                add(request, response);
            }catch(Exception e) {                
            }
        }else if(action.equalsIgnoreCase(response.Action_REMOVE)) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            Document document = (Document)semObject.createGenericInstance();
            removeDocument(document);
        }
    }
    
    protected void add(HttpServletRequest request, SWBActionResponse response) throws Exception {
        Resource base = getResourceBase();

        Document doc = null;
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
                    System.out.println("name="+name+", value="+value);
                    params.put(name, value);
                }else {
                    currentFile = item;

                    doc = Document.ClassMgr.createDocument(getResourceBase().getWebSite());
                    String path = SWBPortal.getWorkPath() + doc.getWorkPath();
                    System.out.println("path del documento="+path);
                    File file = new File(path);
                    if(!file.exists()) {
                        file.mkdirs();
                    }
                    try {
                        String filename = currentFile.getName().replaceAll(" ", "_").trim();
                        System.out.println("..filename="+filename);
                        if( filename.equals("") ) 
                            throw new IOException("El archivo es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                        file = new File(path +"/"+ filename);
                        currentFile.write(file);
                        params.put("filename", filename);
                    }catch(IOException ioe) {
                        System.out.println("error en 100. "+ioe);
                        response.setRenderParameter("msgErrFilename", "El archivo es requerido.");
                        log.error("El archivo es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                        throw new Exception("El archivo es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                    }
                }
            }
        }
        if(doc!=null) {
            try {
                String filename = params.get("filename").trim();
                if( filename.equals("") ) 
                    throw new Exception("El archivo es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                doc.setFilename(filename);
            }catch(Exception e) {
                response.setRenderParameter("msgErrFilename", "El archivo es requerido.");
                log.error("El archivo es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                throw new Exception("El archivo es requerido. Resource "+base.getTitle()+" with id "+base.getId());
            }
            try {
                String title = params.get("title").trim();
                if( title.equals("") )
                    throw new Exception("El título es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                doc.setTitle(title);
            }catch(Exception e) {
                response.setRenderParameter("msgErrTitle", "El título es requerido.");
                log.error("El título es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                throw new Exception("El título es requerido. Resource "+base.getTitle()+" with id "+base.getId());
            }
            try {
                String desc = params.get("description").trim();
                if( desc.equals("") )
                    throw new Exception("La descripción es requerida. Resource "+base.getTitle()+" with id "+base.getId());
                doc.setDescription(desc);
            }catch(Exception e) {
                response.setRenderParameter("msgErrDesc", "La descripción es requerida.");
                log.error("La descripción es requerida. Resource "+base.getTitle()+" with id "+base.getId());
                throw new Exception("La descripción es requerida. Resource "+base.getTitle()+" with id "+base.getId());
            }
            doc.setHidden(Boolean.parseBoolean(params.get("scope")));
            addDocument(doc);
        }
    }

    @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String uri = request.getParameter("uri");
        Document docto = (Document)SemanticObject.createSemanticObject(uri).createGenericInstance();
        String filename = docto.getFilename();

        if(filename.indexOf(".htm")>-1)
            response.setContentType("text/html");
        else if(filename.indexOf(".xml")>-1)
            response.setContentType("text/xml");
        else if(filename.indexOf(".rtf")>-1)
            response.setContentType("application/rtf");
        else if(filename.indexOf(".pdf")>-1)
            response.setContentType("application/pdf");
        else if (filename.indexOf(".do") > -1)
            response.setContentType("application/msword");
        else if(filename.indexOf(".xl")>-1)
            response.setContentType("application/vnd.ms-excel");
        else if(filename.indexOf(".pp")>-1)
            response.setContentType("application/vnd.ms-powerpoint");
        else if(filename.indexOf(".msg")>-1)
            response.setContentType("application/vnd.ms-outlook");
        else if(filename.indexOf(".mpp")>-1)
            response.setContentType("application/vnd.ms-project");
        else if(filename.indexOf(".iii")>-1)
            response.setContentType("application/x-iphone");
        else if(filename.indexOf(".mdb")>-1)
            response.setContentType("application/x-msaccess");
        else if(filename.indexOf(".pub")>-1)
            response.setContentType("application/x-mspublisher");
        else if(filename.indexOf(".swf")>-1)
            response.setContentType("application/x-shockwave-flash");
        else if(filename.indexOf(".text")>-1)
            response.setContentType("application/x-tex");
        else if (filename.indexOf(".zip") > -1)
            response.setContentType("application/zip");
        else if (filename.indexOf(".jp") > -1)
            response.setContentType("image/jpeg");
        else if (filename.indexOf(".png") > -1)
            response.setContentType("image/png");
        else if (filename.indexOf(".gif") > -1)
            response.setContentType("image/gif");
        else if(filename.indexOf(".bmp")>-1)
            response.setContentType("image/bmp");
        else if(filename.indexOf(".tif")>-1)
            response.setContentType("image/tiff");
        else if(filename.indexOf(".ico")>-1)
            response.setContentType("image/x-icon");
        else if (filename.indexOf(".mp3") > -1)
            response.setContentType("audio/mpeg");
        else if(filename.indexOf(".mp")>-1)
            response.setContentType("video/mpeg");
        else if(filename.indexOf(".mov")>-1)
            response.setContentType("video/quicktime");
        else if(filename.indexOf(".qt")>-1)
            response.setContentType("video/quicktime");
        else if(filename.indexOf(".as")>-1)
            response.setContentType("video/x-ms-asf");
        else if(filename.indexOf(".avi")>-1)
            response.setContentType("video/x-msvideo");
        else
            response.setContentType("application/octet-stream");

        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        String fileURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+SWBPortal.getContextPath()+"/work"+docto.getWorkPath()+"/"+filename;
        URL url = new URL(fileURL);
        BufferedInputStream  bis = new BufferedInputStream(url.openStream());
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[DEFAULT_BUFFER_SIZE];
        int bytesRead;
        while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.flush();
        bos.close();
    }


    protected void edit(HttpServletRequest request, SWBActionResponse response) throws Exception {
        WebPage page = response.getWebPage();
//        Member mem = Member.getMember(response.getUser(), page);

        String uri = request.getParameter("uri");
        Document doc = (Document)SemanticObject.createSemanticObject(uri).createGenericInstance();
//        if(doc==null || !doc.canModify(mem)) return;

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
            String rp = SWBPortal.getWorkPath()+doc.getWorkPath()+"/"+doc.getFilename();
            File f = new File(rp);
            if(f!=null && f.exists()) {
                f.delete();
            }
            doc.setFilename(params.get("filename"));
        }
        if(params.containsKey("title"))
            doc.setTitle(params.get("title"));
        if(params.containsKey("description"))
            doc.setDescription(params.get("description"));
//        if(params.containsKey("tags"))
//            doc.setTags(params.get("tags"));
//        if(params.containsKey("scope"))
//            doc.setVisibility(Integer.parseInt(params.get("scope")));
//        if(page instanceof MicroSiteWebPageUtil) {
//            ((MicroSiteWebPageUtil)page).sendNotification(doc);
//        }
    }

}
