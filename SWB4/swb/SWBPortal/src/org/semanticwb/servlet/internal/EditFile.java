/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import org.semanticwb.model.Portlet;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;

/**
 *
 * @author jorge.jimenez
 */
public class EditFile implements InternalServlet {

    private static Logger log = SWBUtils.getLogger(Upload.class);

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException {
        try {
            String lang=dparams.getUser().getLanguage();
            String path = request.getParameter("file");
            String fileName=null;
            int posfileName=path.lastIndexOf("/");
            if(posfileName>-1){
                fileName=path.substring(posfileName+1);
            }
            String path2Save=path;
            Portlet base=null;
            String resUri=request.getParameter("resUri");
            if(resUri!=null){
                base=SWBPortal.getResourceMgr().getResource(resUri).getResourceBase();
                path2Save=base.getWorkPath()+"/"+fileName;
            }
            String newcontent = request.getParameter("content");
            PrintWriter out = response.getWriter();
            if (newcontent == null) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>EditArea Test</title>");
                out.println("<script language=\"javascript\" type=\"text/javascript\" src=\"/swb/swbadmin/js/editarea/edit_area/edit_area_full.js\"></script>");
                out.println("</head>");
                out.println("<body>");
                String ext=null;
                int pos=path.lastIndexOf(".");
                if(pos>-1){
                    ext=path.substring(pos+1);
                }
                String pathType=request.getParameter("pathType");
                String content = "";
                if(pathType.equals("def")){
                        content=SWBUtils.IO.readInputStream(SWBPortal.getAdminFileStream(path));
                }else if(pathType.equals("res")){
                        content=SWBUtils.IO.readInputStream(SWBPlatform.getFileFromWorkPath(path));
                }else {
                    FileInputStream fileInput=new FileInputStream(path);
                    content=SWBUtils.IO.readInputStream(fileInput);
                }
                long time = new Date().getTime();
                out.println("<form method=\"post\" name=\"editor_"+time+"\" action=\""+SWBPlatform.getContextPath()+"/editfile"+"\"> \n" +
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
                        "</form> \n");
                out.println("</body> \n");
                out.println("</html> \n");
            } else {
                File file=new File(SWBPlatform.getWorkPath()+path);
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

    public void init(ServletContext config) throws ServletException {
        log.event("Initializing InternalServlet EditFile...");
    }
}
