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
package org.semanticwb.servlet.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;

// TODO: Auto-generated Javadoc
/**
 * The Class EditFile.
 * 
 * @author jorge.jimenez
 */
public class EditFile implements InternalServlet {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(EditFile.class);

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.servlet.internal.DistributorParams)
     */
    @Override
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException {
System.out.println("\n\nEditFile.........\n");
        try {
            String lang=dparams.getUser().getLanguage();
            String path = request.getParameter("file");
            String f = request.getParameter("file");
System.out.println("file="+f);    
            String fileName=null;
//            int posfileName=path.lastIndexOf("/");
//            if(posfileName>-1){
//                fileName=path.substring(posfileName+1);
//            }
//System.out.println("fileName="+fileName);    
//            String path2Save=path;
            Resource base=null;
//            String resUri=request.getParameter("resUri");
//System.out.println("resUri="+resUri);    
//            if(resUri!=null){
//                base=SWBPortal.getResourceMgr().getResource(resUri).getResourceBase();
//                path2Save=base.getWorkPath()+"/"+fileName;
//            }
//System.out.println("path2Save="+path2Save);    
            String newcontent = request.getParameter("content");
            PrintWriter out = response.getWriter();
//            if (newcontent == null) {
              if (Boolean.TRUE) {
//                String ext=null;
//                int pos=path.lastIndexOf(".");
//                if(pos>-1){
//                    ext=path.substring(pos+1);
//                }
//                String pathType=request.getParameter("pathType");
//System.out.println("pathType="+pathType);    
//                String content = "";
//                if(pathType.equals("def")){
//                        content=SWBUtils.IO.readInputStream(SWBPortal.getAdminFileStream(path));
//                }else if(pathType.equals("res")){
//                        content=SWBUtils.IO.readInputStream(SWBPortal.getFileFromWorkPath(path));
//                }else {
//                    FileInputStream fileInput=new FileInputStream(path);
//                    content=SWBUtils.IO.readInputStream(fileInput);
//                }
//                long time = new Date().getTime();

                out.println("<html>");
                out.println("<head>");
                out.println("<title>EditArea Test</title>");
                out.println("<script language=\"javascript\" type=\"text/javascript\" src=\"/swb/swbadmin/js/editarea/edit_area/edit_area_full.js\"></script>");
                out.println("</head>");
                out.println("<body>");
                out.println("<applet alt=\"editar xsl\" codebase=\""+SWBPlatform.getContextPath()+"/\" code=\"applets.edit.XSLEditorApplet\" archive=\"swbadmin/lib/SWBAplXSLEditor.jar, swbadmin/lib/rsyntaxtextarea.jar\" width=\"100%\" height=\"100%\">");
                out.println("  <param name=\"file\" value=\""+f+"\" />");
                out.println("</applet>");
//out.println("<APPLET  WIDTH=100% HEIGHT=50% CODE=\"applets.dragdrop.DragDrop.class\" codebase=\"/\" archive=\"swbadmin/lib/SWBAplDragDrop.jar, swbadmin/lib/SWBAplCommons.jar\" border=0>");
//out.println("<PARAM NAME=\"webpath\" VALUE=\"/\"/>");
//out.println("<PARAM NAME=\"foreground\" VALUE=\"000000\"/>");
//out.println("<PARAM NAME=\"background\" VALUE=\"979FC3\"/>");
//out.println("<PARAM NAME=\"foregroundSelection\" VALUE=\"ffffff\"/>");
//out.println("<PARAM NAME=\"backgroundSelection\" VALUE=\"666699\"/>");
//out.println("<PARAM NAME=\"path\" value=\"C:/Users/carlos.ramos/desarrollo/SWB4/swb/build/web/work/models/C/Resource/14/images/\"/>");
//out.println("<PARAM NAME=\"clientpath\" value=\"/\"/>");
//out.println("<PARAM NAME=\"files\" value=\";images/WBMenuMap.css;\"/>");
//out.println("<PARAM NAME=\"locale\" value=\"en\"/>");
//out.println("</APPLET>");
                
                
                
                
                
                
                /*out.println("<form method=\"post\" name=\"editor_"+time+"\" action=\""+SWBPlatform.getContextPath()+"/editfile"+"\"> \n" +
                        "<script language=\"javascript\" type=\"text/javascript\"> editAreaLoader.init({ \n" +
                        "id : \"textarea_" + time + "\" \n" + // id of the textarea to transform
                        ",start_highlight: true	\n" +
                        ",font_size: \"8\" \n" +
                        ",font_family: \"verdana, monospace\" \n" +
                        ",allow_resize: \"y\" \n" +
                        ",allow_toggle: false \n" +
                        ",language: \""+lang+"\" \n" +
                        ",syntax: \""+ext+"\"	\n" +
                        ",toolbar: \"save, search, go_to_line, |, undo, redo, |, highlight, reset_highlight, |, help\" \n" +
                        ",load_callback: \"my_load\" \n" +
                        ",save_callback: \"my_save\" \n" +
                        ",plugins: \"charmap\" \n" +
                        ",charmap_default: \"arrows\" \n" +
                        "}); \n" +
                        "function my_save(id, content){ \n" +
                            "document.editor_"+time+".content.value=content; \n"+
                            "document.editor_"+time+".submit(); \n" +
                        "} \n" +
                        "</script> \n" +
                        "<textarea id=\"textarea_" + time + "\" name=\"content\" cols=\"100\" rows=\"45\">" +
                        content+
                        "</textarea>" +
                        "<input type=\"hidden\" name=\"resUri\" value=\""+resUri+"\">"+
                        "<input type=\"hidden\" name=\"file\" value=\""+path2Save+"\">"+
                        "<input type=\"hidden\" name=\"attr\" value=\""+request.getParameter("attr")+"\">"+
                        "<br/><input type=\"button\" name=\"return\" onclick=\"history.go(-1);\" value=\"Regresar\">"+
                        "</form> \n");*/
                out.println("</body> \n");
                out.println("</html> \n");
            } else {
                File file=new File(SWBPortal.getWorkPath()+path);
                FileOutputStream output = new FileOutputStream(file);
                output.write(newcontent.getBytes());
                output.flush();
                output.close();
                String attr=request.getParameter("attr");
                if(attr!=null){
                    base.setAttribute(request.getParameter("attr"), fileName);
                    base.updateAttributesToDB();                    
                }
                if(base!=null){
                    //Redireccionamiento a la administración del recurso en cuestion
                    SWBResourceURLImp url=new SWBResourceURLImp(request, base, dparams.getWebPage(), SWBResourceURL.UrlType_RENDER);
                    url.setResourceBase(base);
                    url.setMode(url.Mode_ADMIN);
                    url.setWindowState(url.WinState_MAXIMIZED);
                    url.setAction("edit");
                    response.sendRedirect(url.toString());

//                    out.println("<script type=\"text/javascript\">");
//                    out.println("showStatus('Archivo modificado y guardado');");
//                    out.println("addNewTab('"+url.toString()+"');");
//                    out.println("</script>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(javax.servlet.ServletContext)
     */
    public void init(ServletContext config) throws ServletException {
        log.event("Initializing InternalServlet EditFile...");
    }
}
