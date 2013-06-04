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
 * http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.CRC32;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.util.LongFileUploadUtils;
import org.semanticwb.portal.util.PendingFile;

/**
 *
 * @author serch
 */
public class LongFileUploader extends GenericResource {

    private static Logger log = SWBUtils.getLogger(LongFileUploader.class);
    private static ConcurrentHashMap<String, ArrayList<SemanticObject>> enProceso =
            new ConcurrentHashMap<String, ArrayList<SemanticObject>>();
    private static final int CHUNK_SIZE = 10 * 1024 * 1024; //10MB
    private static LongFileUploadUtils fileUtil;
    private static File tmpplace;
    private String parameter = "";
    private String classUri = "";
    private String propertyName = "";
    private String redirectURL = "";

    {
        tmpplace = new File(org.semanticwb.SWBPortal.getWorkPath() + "/lngtmp/"); //workingpath
        fileUtil = LongFileUploadUtils.getFileManager(tmpplace);
        //Maintenance and startup
    }

    public LongFileUploader() throws IOException {
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String uri = request.getRequestURI();
        String urlBase = paramRequest.getRenderUrl().setCallMethod(
                SWBResourceURL.Call_DIRECT).toString();//paramRequest.getWebPage().getRealUrl();
        SemanticObject so = null;
//        System.out.println("requestUri:" + uri);
//        System.out.println("RenderURL:" + paramRequest.getRenderUrl());
//        System.out.println("WebPage:" + paramRequest.getWebPage().getRealUrl());
//        System.out.println("WebPageId:" + paramRequest.getWebPage().getId());
//        System.out.println("urlBase:" + urlBase);
//        int start = uri.indexOf(paramRequest.getWebPage().getId()) + 
//                paramRequest.getWebPage().getId().length();
        String path = "";
        String cmd = "";
        String param = "";

        if ("".equals(cmd) && request.getParameter(parameter) != null) {
            log.debug("LongFileUploader: parameter " + parameter + ":" + request.getParameter(parameter));
            so = paramRequest.getWebPage().getWebSite().getSemanticModel()
                    .getSemanticObject(request.getParameter(parameter));
            log.debug("LongFileUploader: so:"+so);
            log.debug("LongFileUploader: sosClass:"+so.getSemanticClass().getURI());
            log.debug("LongFileUploader: classUri:"+classUri);
            if (so.getSemanticClass().getURI().equals(classUri)) {
                String key = request.getSession(true).getId();
                log.debug("LongFileUploader: key "+key);
                if (!enProceso.containsKey(key)) {
                    enProceso.put(key, new ArrayList<SemanticObject>());
                }
                ArrayList<SemanticObject> also = enProceso.get(key);
                if (!also.contains(so)) {
                    also.add(so);
                }
            }
        }
        if (SWBResourceURL.Call_DIRECT == paramRequest.getCallMethod()) {
            if (uri.indexOf(urlBase) > -1) {
                path = uri.substring(urlBase.length());
            }
            int inicmd = path.indexOf("/");
            int endcmd = path.indexOf("/", inicmd + 1);

            if (endcmd > -1) {
                cmd = path.substring(inicmd + 1, endcmd);
            } else {
                cmd = path.substring(inicmd + 1);
            }

            if (endcmd > -1 && path.length() > endcmd) {
                param = path.substring(endcmd + 1);
            }
        }
        log.debug("LongFileUploader: cmd:"+cmd);
        if (cmd.equals("start")) {
            startUploadProcess(request, response, paramRequest);
        } else if (cmd.equals("uploadSolicitude")) {
            uploadSolicitude(request, response, paramRequest);
        } else if (cmd.equals("uploadchunk")) {
            uploadChunk(request, response, paramRequest, param);
        } else if (cmd.equals("abortupload")) {
            abortUpload(request, response, paramRequest, param);
        } else if (cmd.equals("eofcheck")) {
            eofCheck(request, response, paramRequest, param);
        } else if (cmd.equals("status")) {
            giveStatus(request, response, paramRequest, param);
        } else {
            String id = SWBUtils.TEXT.replaceSpecialCharacters(
                    paramRequest.getResourceBase().getTitle(), true);
            String url = "/bduplaoder";
            String redirectedURL = "/";
            log.debug("LongFileUploader: uri en " + redirectURL + ":" + request.getParameter(redirectURL));
            try {
                redirectedURL = URLDecoder.decode(request.getParameter(redirectURL), "UTF-8");
            } catch (Exception noe) {
                log.debug("LongFileUploader: Sin Redirect URL");
            }
            log.debug("LongFileUploader: uri decoded: " + redirectedURL);
            if (!redirectedURL.startsWith("/")) {
                redirectedURL = "";
            }
            if (null != so) {
                out.println("<script src=\"/swbadmin/js/longfu/json2.js\"></script>"
                        + "<script src=\"/swbadmin/js/longfu/swblongfileuploader.js\">"
                        + "</script><script type=\"text/javascript\">"
                        + "var " + id + "_lfu = new LongFileUploader(\"" + urlBase
                        + "\",\"" + so.getId() //Revisar...
                        + "\", \"" + id + "\",\"" + redirectedURL + "\");</script>");
                out.println("<div id=\"" + id + "\"><form>file: <input type=\"file\" "
                        + "name=\"updfile\" id=\"updfile\" "
                        + "onchange=\"" + id + "_lfu.sendFile(this)\"/>"
                        + "<div id=\"progressBar\" style=\"width:100%; height:15px; "
                        + "border:1px solid #000; overflow:hidden;\">"
                        + "<div id=\"" + id + "_percentage\" style=\"width:0%; height:15px; "
                        + "border-right: 1px solid #000000; background: #0000ff;\">"
                        + "</div><div id=\"" + id + "_label\" style=\"color: #000000; "
                        + "font-size: 15px; font-style: italic; font-weight: "
                        + "bold; left: 25px; position: relative; top: -16px;\">"
                        + "</div></div>"
                        + "</form></div>");
            } else {
                out.print("No se encontró el objeto semántico relacionado");
            }
        }

    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String frmParameter = getResourceBase().getAttribute("frmParameter", "");
        String frmClass = getResourceBase().getAttribute("frmClass", "");
        String frmProperty = getResourceBase().getAttribute("frmProperty", "");
        String frmRedirectURL = getResourceBase().getAttribute("frmRedirectURL", "");

        String act = request.getParameter("act");
        if (act != null) {
            frmParameter = request.getParameter("frmParameter");
            getResourceBase().setAttribute("frmParameter", frmParameter);
            frmClass = request.getParameter("frmClass");
            getResourceBase().setAttribute("frmClass", frmClass);
            frmProperty = request.getParameter("frmProperty");
            getResourceBase().setAttribute("frmProperty", frmProperty);
            frmRedirectURL = request.getParameter("frmRedirectURL");
            getResourceBase().setAttribute("frmRedirectURL", frmRedirectURL);
            try {
                getResourceBase().updateAttributesToDB();
                parameter = frmParameter;
                classUri = frmClass;
                propertyName = frmProperty;
                redirectURL = frmRedirectURL;
                out.print("<script type=\"text/javascript\">showStatus('Configuración actualizada');</script>");
            } catch (Exception e) {
                log.error(e);
            }
        }

        out.println("<script type=\"text/javascript\">");
        out.println("  dojo.require(\"dijit.form.Form\");");
        out.println("  dojo.require(\"dijit.form.Button\");");
        out.println("  dojo.require(\"dijit.form.CheckBox\");");
        out.println("</script>");

        out.println("<div class=\"swbform\">");
        out.println(new StringBuilder().append(
                "<form dojoType=\"dijit.form.Form\" id=\"").append(
                getResourceBase().getId()).append("/frmPath\" action=\"").append(
                paramRequest.getRenderUrl()).append("\" method=\"post\" class=\"swbform\"  >").toString());
        out.println("<input type=\"hidden\" name=\"act\" value=\"upd\">");

        out.println("<fieldset>");
        out.println("<legend>Configuraci&oacute;n </legend>");
        out.println("<br/>");
        out.println("Nombre del parámetro donde se recibira el URI del objeto "
                + "semántico de referencia");
        out.println("<br/>");
        out.print("<input name=\"frmParameter\" value=\"" + frmParameter + "\">");

        out.println("<br/>");
        out.println("URI de la clase semántica del objeto");
        out.println("<br/>");
        out.print("<input name=\"frmClass\" value=\"" + frmClass + "\">");

        out.println("<br/>");
        out.println("Nombre de la propiedad a actualizar");
        out.println("<br/>");
        out.print("<input name=\"frmProperty\" value=\"" + frmProperty + "\">");

        out.println("<br/>");
        out.println("Nombre del parámetro que contiene el URL para redireccionar al terminar");
        out.println("<br/>");
        out.print("<input name=\"frmRedirectURL\" value=\"" + frmRedirectURL + "\">");

        out.println("<br/>");

        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" "
                + "name=\"submit/btnSend\" >Enviar</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");

    }

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        if (null == base.getAttribute("frmParameter")) {
            base.setAttribute("frmParameter", "");
        }
        parameter = base.getAttribute("frmParameter");
        if (null == base.getAttribute("frmClass")) {
            base.setAttribute("frmClass", "");
        }
        classUri = base.getAttribute("frmClass");
        if (null == base.getAttribute("frmProperty")) {
            base.setAttribute("frmProperty", "");
        }
        propertyName = base.getAttribute("frmProperty");
        if (null == base.getAttribute("frmRedirectURL")) {
            base.setAttribute("frmRedirectURL", "");
        }
        redirectURL = base.getAttribute("frmRedirectURL");
    }

    private void startUploadProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws IOException {
        String userStr = paramRequest.getUser().getUserRepository().getId() + ":"
                + paramRequest.getUser().getId() + ":"
                + paramRequest.getUser().getLogin();
        log.debug("LongFileUploader.startUploadProcess: userStr:"+userStr);
        response.setHeader("mimetype", "text/json-comment-filtered");
        PrintWriter out = response.getWriter();
        out.print("{\"chunkSize\":" + CHUNK_SIZE);
        ArrayList<PendingFile> lpf = fileUtil.getListOfPendingFiles(userStr);
        if (!lpf.isEmpty()) {
            out.print(", \"pendingFiles\":[");
            boolean doComma = false;
            for (PendingFile pf : lpf) {
                if (pf.isDone()) {
                    continue;
                }
                if (doComma) {
                    out.print(",");
                }
                out.print("{\"id\":\"" + pf.getId() + "\",\"size\":"
                        + pf.getSize() + ",\"filename\":\"" + pf.getFilename()
                        + "\",\"crc\":\"" + pf.getIniCRC() + "\"}");
                doComma = true;
            }
            out.print("]");
        }
        out.print("}");
    }

    private void uploadSolicitude(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws IOException {
        String filename = request.getParameter("filename");
        String size = request.getParameter("size");
        String crc = request.getParameter("iniCRC");
        String sdate = request.getParameter("sDate");
        String localId = request.getParameter("localId");
        String userStr = paramRequest.getUser().getUserRepository().getId() + ":"
                + paramRequest.getUser().getId() + ":"
                + paramRequest.getUser().getLogin();
        PendingFile pf = fileUtil.haveWorkingFile(filename, Long.parseLong(size),
                crc, userStr);
        if (null == pf) {
            if (fileUtil.addWorkingFile(filename, Long.parseLong(size), crc,
                    sdate, userStr)) {
                pf = fileUtil.haveWorkingFile(filename, Long.parseLong(size),
                        crc, userStr);
            }
        }
        log.debug("LongFileUploader.uploadSolicitude: filename:"+filename);
        response.setHeader("mimetype", "text/json-comment-filtered");
        PrintWriter out = response.getWriter();
        long localsize = 0;
        File wrkfile = new File(tmpplace, pf.getId());
        if (!wrkfile.exists()) {
            wrkfile.mkdirs();
        }
        File updFile = new File(wrkfile, pf.getFilename());
        if (updFile.exists()) {
            localsize = updFile.length();
        }
        log.debug("LongFileUploader.uploadSolicitude: bytes uploaded:"+localsize);
        out.print("{\"file2Upload\":{\"localId\":" + localId + ",\"id\":\""
                + pf.getId() + "\",\"bytesRecived\":" + localsize + "}}");
    }

    private void uploadChunk(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String param) {
        File workfiledir = new File(tmpplace, param);
        PendingFile pf = fileUtil.getPendingFileFromId(param);
        if (!workfiledir.exists()) {
            workfiledir.mkdirs();
        }
        String crc = "";
        String tmpFileName = UUID.randomUUID().toString();
        log.debug("LongFileUploader.uploadChunk:"+tmpFileName);
        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 1024,
                    workfiledir);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(CHUNK_SIZE + 1024);
            try {
                List<FileItem> items = upload.parseRequest(request);
                File uplFile = new File(workfiledir, tmpFileName);
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        item.write(uplFile);
                    } else {
                        if ("crc".equals(item.getFieldName())) {
                            crc = item.getString();
                        }
                    }
                }
                String calcCrc = calcCRC(uplFile, false);
                if (calcCrc.equals(crc)) {
                    File updfile = new File(workfiledir, pf.getFilename());
                    if (!updfile.exists()) {
                        boolean worked = uplFile.renameTo(updfile);
                        if (!worked) {
                            throw new IOException("Can't rename a file");
                        }
                    } else {
                        FileOutputStream fos = new FileOutputStream(updfile,
                                true);
                        FileInputStream fis = new FileInputStream(uplFile);
                        byte[] tempBytes = new byte[CHUNK_SIZE];
                        int blksize = 0;
                        while ((blksize = fis.read(tempBytes)) > -1) {
                            fos.write(tempBytes, 0, blksize);
                        }
                        fis.close();
                        fos.flush();
                        fos.close();
                        if (updfile.length() == pf.getSize()) {
                            pf.setDone(true);
                        }
                        uplFile.delete();
                    }
                }
            } catch (Exception ioe) {
                log.error(ioe);
            }
        }
    }

    private static String calcCRC(File file, boolean start) {
        CRC32 crc = new CRC32();
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int read = 0;
            while ((read = fin.read(buffer)) != -1) {
                crc.update(buffer, 0, read);
                if (start) {
                    break;
                }
            }
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            if (null != fin) {
                try {
                    fin.close();
                } catch (IOException ex) {
                    log.error(ex);
                }
            }
        }
        return Long.toHexString(crc.getValue());
    }

    private void abortUpload(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String param)
            throws IOException {
        boolean ret = fileUtil.removePendingFile(param);
        log.debug("LongFileUploader.abortUpload: "+param+" : "+ret);
        response.setHeader("mimetype", "text/json-comment-filtered");
        PrintWriter out = response.getWriter();
        out.print("{\"deleted\":\"" + ret + "\"}");
    }

    private void eofCheck(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String param)
            throws IOException {
        String pdir = request.getParameter("dirToPlace");
        log.debug("LongFileUploader.eofCheck: pdir="+pdir);
        String key = request.getSession(true).getId();
        log.debug("LongFileUploader.eofCheck: key "+key);
        ArrayList<SemanticObject> also = enProceso.get(key);
        boolean ret = false;
        if (null != also) {
            SemanticObject so = null;
            for (SemanticObject currSo : also) {
                log.trace("LongFileUploader.eofCheck: currSo.getId() " +currSo.getId());
                if (currSo.getId().equals(pdir)) {
                    so = currSo;
                    break;
                }
            }
            log.trace("LongFileUploader.eofCheck: so:"+so);
            if (null != so) {
                pdir = so.getWorkPath();
                log.debug("LongFileUploader.eofCheck: pdir2="+pdir);
                File dir = new File(org.semanticwb.SWBPortal.getWorkPath(), pdir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                if (dir.exists()) {
                    PendingFile pf = fileUtil.getPendingFileFromId(param);
                    File dest = new File(dir, SWBUtils.TEXT.replaceSpecialCharacters(pf.getFilename().toString(),'.', true));
                    if (!dest.exists()) {
                        File workfiledir = new File(tmpplace, param);
                        File orig = new File(workfiledir, pf.getFilename());
                        ret = orig.renameTo(dest);
                        if (!ret) {
                            FileInputStream fis = new FileInputStream(orig);
                            FileOutputStream fos = new FileOutputStream(dest);
                            byte[] buffer = new byte[8192];
                            int readbytes = 0;
                            while ((readbytes = fis.read(buffer)) > -1) {
                                fos.write(buffer, 0, readbytes);
                            }
                            fis.close();
                            fos.flush();
                            fos.close();
                            orig.delete();
                            ret = true;
                        }
                        SemanticProperty sp = so.getSemanticClass()
                                .getProperty(propertyName);
                        log.trace("LongFileUploader.eofCheck: SemanticProperty:"+sp);
                        if (propertyName.startsWith("has")) {
                            so.addLiteralProperty(sp,
                                    new SemanticLiteral(dest.getName()));
                        } else {
                            String currVal = so.getProperty(sp);
                            if (null != currVal) {
                                File currFile = new File(dir, currVal);
                                if (currFile.exists()) {
                                    currFile.delete();
                                }
                            }
                            so.setProperty(sp, dest.getName());
                        }
                        Iterator<SemanticLiteral> iter = so.listLiteralProperties(sp);
                        while(iter.hasNext()){
                            SemanticLiteral sl = iter.next();
                            log.trace("Value found: "+sl.getString());
                        }
                        workfiledir.delete();
                        fileUtil.updateChanges();
                    }
                }
            }
        }
        log.debug("LongFileUploader.eofCheck: ret:"+ret);
        response.setHeader("mimetype", "text/json-comment-filtered");
        PrintWriter out = response.getWriter();
        out.print("{\"saved\":\"" + ret + "\"}");
    }

    private void giveStatus(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String param)
            throws IOException {
        PendingFile pf = fileUtil.getPendingFileFromId(param);
        response.setHeader("mimetype", "text/json-comment-filtered");
        PrintWriter out = response.getWriter();
        long localsize = 0;
        File wrkfile = new File(tmpplace, pf.getId());
        File updFile = new File(wrkfile, pf.getFilename());
        if (updFile.exists()) {
            localsize = updFile.length();
        }
        log.debug("LongFileUploader.giveStatus: id "+pf.getId()+" bytes:"+localsize);
        out.print("{\"id\":\"" + pf.getId() + "\",\"bytesRecived\":"
                + localsize + "}");
    }
}
