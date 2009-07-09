package org.semanticwb.resource.office.sem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.util.ContentUtils;

/**
 *
 * @author Jorge Jiménez
 */

public class WordResource extends org.semanticwb.resource.office.sem.base.WordResourceBase {

    public WordResource() {
        super();
    }

    public WordResource(SemanticObject obj) {
        super(obj);
    }
    private static Logger log = SWBUtils.getLogger(WordResource.class);

    protected void beforePrintDocument(PrintWriter out) {
    }

    protected void afterPrintDocument(PrintWriter out) {
    }

    protected void printDocument(PrintWriter out, String path, String workpath, String html) {
        out.write(html);
    }

    public static org.semanticwb.resource.office.sem.WordResource createWordResource(String id, org.semanticwb.model.SWBModel model) {
        return (org.semanticwb.resource.office.sem.WordResource) model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        ContentUtils contentUtils = new ContentUtils();
        Resource base = paramRequest.getResourceBase();
        WebPage page = paramRequest.getTopic();
        String version = getVersionToShow();
        String contentId = getContent();
        String repositoryName = getRepositoryName();
        OfficeDocument document = new OfficeDocument();
        try {
            User user = paramRequest.getUser();
            String file = document.getContentFile(repositoryName, contentId, version, user);
            if (file != null) {
                file = file.replace(".doc", ".html");
                String path = SWBPlatform.getWorkPath();
                if (path.endsWith("/")) {
                    path = path.substring(0, path.length() - 1);
                    path += getResourceBase().getWorkPath() + "/" + file;
                } else {
                    path += getResourceBase().getWorkPath() + "/" + file;
                }

                File filecontent = new File(path);
                if (filecontent.exists()) {
                    String workpath = SWBPlatform.getWebWorkPath() + getResourceBase().getWorkPath() + "/";
                    StringBuffer html = new StringBuffer();
                    try {
                        FileInputStream in = new FileInputStream(path);
                        byte[] buffer = new byte[2048];
                        int read = in.read(buffer);
                        while (read != -1) {
                            html.append(new String(buffer, 0, read));
                            read = in.read(buffer);
                        }
                        String htmlOut = null;
                        if (isPages() && getNpages() > 0) {
                            htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath, getNpages());
                        } else {
                            htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath);
                        }
                        PrintWriter out = response.getWriter();
                        beforePrintDocument(out);

                        //Agregado por Jorge Jiménez para poner estilos predefinidos y paginación MsWord y OpenOffice (5/07/2009)
                        boolean iswordcontent = true; 
                        int posword = htmlOut.toLowerCase().indexOf("name=\"generator\" content=\"openoffice.org"); //detección de si el contenido es de openOffice
                        if (posword > -1) iswordcontent = false;
                        if (iswordcontent) { //Contenido MsWord
                            htmlOut=contentUtils.predefinedStyles(htmlOut, base); //Estilos predefinidos
                            if(isPages()) {
                                htmlOut = contentUtils.paginationMsWord(htmlOut, page, request.getParameter("page"), base);
                            } //Paginación
                        } else if(isPages()){ //Contenido OpenOffice
                            htmlOut = contentUtils.paginationOpenOffice(htmlOut, page, request.getParameter("page"), base); //Paginación
                        }
                        //Termina Agregado por Jorge Jiménez (5/07/2009)

                        printDocument(out, path, workpath, htmlOut);
                        afterPrintDocument(out);
                        out.close();
                    } catch (Exception e) {
                        log.error(e);
                    }

                } else {
                    log.error("Contenido no encontrado en ruta: " + filecontent.getAbsolutePath() + ": " + getContent() + "@" + getRepositoryName());
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

     /**
     * Inicializa la clase creando objetos de configuración del recurso
     */
    @Override
    public void setResourceBase(Resource base) {
        try{
            ContentUtils contentUtils = new ContentUtils();
            super.setResourceBase(base);
            contentUtils.setResourceBase(base, "Content");
        }catch(Exception e){
            log.error(e);
        }

    }
    
}

