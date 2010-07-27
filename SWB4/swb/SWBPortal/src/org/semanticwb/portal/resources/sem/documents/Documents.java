package org.semanticwb.portal.resources.sem.documents;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class Documents extends org.semanticwb.portal.resources.sem.documents.base.DocumentsBase {
    private static Logger log = SWBUtils.getLogger(Documents.class);

    public Documents() {
    }

    public Documents(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.println(renderListDocuments(paramRequest));

        SWBResourceURL addURL = paramRequest.getActionUrl().setAction(paramRequest.Action_ADD);
        
        out.println("<script type=\"text/javascript\">");
        out.println("<!--");
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
        out.println("-->");
        out.println("</script>");
        out.println("<div class=\"\">");
        out.println("  <div class=\"adminTools\">");
        out.println("    <a class=\"adminTool\" onclick=\"validaForma()\" href=\"#\">Guardar</a>");
        out.println("    <a class=\"adminTool\" href=\"<%=paramRequest.getRenderUrl()%>\">Cancelar</a>");
        out.println("  </div>");
        out.println("  <form name=\"frmadddoc\" id=\"frmadddoc\" enctype=\"multipart/form-data\" method=\"post\" action=\""+addURL+"\">");
        out.println("    <div>");
        out.println("      <fieldset>");
        out.println("        <legend>Agregar documento</legend>");
        out.println("        <div>");
        out.println("          <p>");
        out.println("            <label for=\"docto\">Documento:&nbsp;</label><br />");
        out.println("            <input id=\"docto\" type=\"file\" name=\"docto\" size=\"45\" />");
        out.println("          </p>");
        out.println("          <p>");
        out.println("            <label for=\"title\">Título:&nbsp;</label><br />");
        out.println("            <input id=\"title\" type=\"text\" name=\"title\" maxlength=\"50\" size=\"45\" />");
        out.println("          </p>");
        out.println("          <p>");
        out.println("            <label for=\"description\">Descripción</label><br />");
        out.println("            <textarea id=\"description\" cols=\"45\" rows=\"3\" name=\"description\"></textarea>");
        out.println("          </p>");
//        out.println("                 <p>");
//        out.println("                   <label for=\"tags\">Etiquetas:&nbsp;</label><br />");
//        out.println("                   <input id=\"tags\" type=\"text\" name=\"tags\" maxlength=\"50\" size=\"45\" />");
//        out.println("                  </p>");
        out.println("        </div>");
        out.println("      </fieldset>");
        out.println("      <fieldset>");
        out.println("        <legend>¿Quién puede ver este documento?</legend>");
        out.println("        <div>");
        out.println("          <p><label for=\"scope0\"><input type=\"radio\" name=\"scope\" id=\"scope0\" value=\"false\" />&nbsp;Cualquiera</label></p>");
        out.println("          <p><label for=\"scope1\"><input type=\"radio\" name=\"scope\" id=\"scope1\" value=\"true\" checked=\"checked\" />&nbsp;Sólo miembros del comité</label></p>");
//        out.println("        <p>");
//        out.println("            <label for=\"level\"><input type=\"radio\" name=\"level\" value=\"3\" />&nbsp;Sólo yo</label>");
//        out.println("        </p>");
        out.println("        </div>");
        out.println("      </fieldset>        ");
        out.println("    </div>");
        out.println("  </form>");
        out.println("</div>");
        out.flush();
        out.close();
//agregar propiedad URL ****LISTO
//agregar autogenID para Document ****LISTO
//agregar editar un objeto Document
//como usar los metodos setIconClass y getIconClass
    }

    private String renderListDocuments(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder html = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy | HH:mm");
        //long ordinal = SWBUtils.Collections.sizeOf(listComments());
        //int ordinal = 1;
        SWBResourceURL url = paramRequest.getActionUrl().setAction(paramRequest.Action_REMOVE);

        System.out.println("renderListDocuments....");
        
        html.append("<div class=\"swb-comentario-sem-lista\">");
        Iterator<Document> itDocuments = SWBComparator.sortByCreated(listDocuments(),false);
        if(itDocuments.hasNext()) {
            html.append("<h2>documentos</h2>");
            html.append("<ol>");
            User user = paramRequest.getUser();
            UserGroup ug = user.getUserGroup();
            System.out.println("ug="+ug);
            while(itDocuments.hasNext()) {
                Document document = itDocuments.next();
                if(document.getCreator()==null)
                    continue;
                if( !document.getCreator().getUserGroup().equals(ug) && document.isHidden() )
                    continue;
                html.append("<li>");
                html.append("<p>"+document.getTitle()+"</p>");
                html.append("<p>"+document.getDescription()+"</p>");
                html.append("<p><a href=\""+SWBPortal.getWorkPath()+document.getWorkPath()+"/"+document.getFilename()+"\">"+document.getFilename()+"</a></p>");
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
                System.out.println("\nError.....\n"+e);
            }
        }else if(action.equalsIgnoreCase(response.Action_REMOVE)) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            Document document = (Document)semObject.createGenericInstance();
            removeDocument(document);
        }
    }
    
    protected void add(HttpServletRequest request, SWBActionResponse response) throws Exception {
        WebPage page = response.getWebPage();
//        Member mem = Member.getMember(response.getUser(), page);
//        if(!mem.canAdd()) return;

        System.out.println("*********** add");

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
                    //long serial = (new Date()).getTime();
                    String filename = null;
                    try {
                        //filename = serial + currentFile.getName().substring(currentFile.getName().lastIndexOf("."));
                        filename = currentFile.getName().replaceAll(" ", "_");
                        System.out.println("filename="+filename);
                        file = new File(path +"/"+ filename);
                        currentFile.write(file);
                        //params.put("filename", doc.getWorkPath()+"/"+filename);
                        params.put("filename", filename);
                    }catch(StringIndexOutOfBoundsException iobe) {
                        System.out.println("error en 100. "+iobe);
                    }
                }
            }
        }
        if(doc!=null) {
            doc.setFilename(params.get("filename"));
            doc.setTitle(params.get("title"));
            doc.setDescription(params.get("description"));
            doc.setHidden(Boolean.parseBoolean(params.get("scope")));
            addDocument(doc);
            System.out.println("se agrego documento");
        }
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
