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
package org.semanticwb.servlet.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.CRC32;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.util.LongFileUploadUtils;
import org.semanticwb.portal.util.PendingFile;

/**
 *
 * @author serch
 */
public final class LongFileUploader implements InternalServlet {

    private static Logger log = SWBUtils.getLogger(LongFileUploader.class);
    private static final int CHUNK_SIZE = 10 * 1024 * 1024; //10MB
    private static LongFileUploadUtils fileUtil;
    private static File tmpplace;

    {
        tmpplace = new File(org.semanticwb.SWBPortal.getWorkPath() + "/lngtmp/"); //workingpath
        fileUtil = LongFileUploadUtils.getFileManager(tmpplace);
        //Maintenance and startup
    }

    public LongFileUploader() throws IOException {
    }

    @Override
    public void init(ServletContext config) throws ServletException {
    }

    @Override
    public void doProcess(HttpServletRequest request,
            HttpServletResponse response, DistributorParams dparams) 
            throws IOException, ServletException {
        //if (!dparams.getUser().isSigned()) return;
        String uri = request.getRequestURI();
        String cntx = request.getContextPath();
        String path = uri.substring(cntx.length());
        int inicmd = path.indexOf("/", 1);
        int endcmd = path.indexOf("/", inicmd + 1);
        String cmd = "";
        if (endcmd > -1) {
            cmd = path.substring(inicmd + 1, endcmd);
        } else {
            cmd = path.substring(inicmd + 1);
        }
        String param = "";
        if (endcmd > -1 && path.length() > endcmd) {
            param = path.substring(endcmd + 1);
        }
        log.debug("cmd: "+cmd+" - "+param);
        if (cmd.equals("start")) {
            startUploadProcess(request, response, dparams);
        } else if (cmd.equals("uploadSolicitude")) {
            uploadSolicitude(request, response, dparams);
        } else if (cmd.equals("uploadchunk")) {
            uploadChunk(request, response, dparams, param);
        } else if (cmd.equals("abortupload")) {
            abortUpload(request, response, dparams, param);
        } else if (cmd.equals("eofcheck")) {
            eofCheck(request, response, dparams, param);
        } else if (cmd.equals("status")) {
            giveStatus(request, response, dparams, param);
        }

    }

    private void startUploadProcess(HttpServletRequest request,
            HttpServletResponse response, DistributorParams dparams)
            throws IOException, ServletException {
        String userStr = dparams.getUser().getUserRepository().getId() + ":"
                + dparams.getUser().getId() + ":" + dparams.getUser().getLogin();
        response.setHeader("mimetype", "text/json-comment-filtered");
        PrintWriter out = response.getWriter();
        out.print("{\"chunkSize\":" + CHUNK_SIZE);
        ArrayList<PendingFile> lpf = fileUtil.getListOfPendingFiles(userStr);
        if (!lpf.isEmpty()) {
            out.print(", \"pendingFiles\":[");
            boolean doComma = false;
            for (PendingFile pf : lpf) {
                if (pf.isDone()) continue;
                if (doComma) out.print(",");
                out.print("{\"id\":\"" + pf.getId() + "\",\"size\":"
                        + pf.getSize() + ",\"filename\":\"" + pf.getFilename()
                        + "\",\"crc\":\"" + pf.getIniCRC() + "\"}");
                doComma = true;
            }
            out.print("]");
        }
        out.print("}");
    }

    private void uploadSolicitude(HttpServletRequest request,
            HttpServletResponse response, DistributorParams dparams)
            throws IOException, ServletException {
        String filename = request.getParameter("filename");
        String size = request.getParameter("size");
        String crc = request.getParameter("iniCRC");
        String sdate = request.getParameter("sDate");
        String localId = request.getParameter("localId");
        String userStr = dparams.getUser().getUserRepository().getId() + ":"
                + dparams.getUser().getId() + ":" + dparams.getUser().getLogin();
        PendingFile pf = fileUtil.haveWorkingFile(filename, Long.parseLong(size),
                crc, userStr);
        if (null == pf) {
            if (fileUtil.addWorkingFile(filename, Long.parseLong(size), crc,
                    sdate, userStr)) {
                pf = fileUtil.haveWorkingFile(filename, Long.parseLong(size),
                        crc, userStr);
            }
        }
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
        out.print("{\"file2Upload\":{\"localId\":" + localId + ",\"id\":\""
                + pf.getId() + "\",\"bytesRecived\":" + localsize + "}}");
    }

    private void uploadChunk(HttpServletRequest request,
            HttpServletResponse response, DistributorParams dparams,
            String param) throws IOException, ServletException {
        File workfiledir = new File(tmpplace, param);
        PendingFile pf = fileUtil.getPendingFileFromId(param);
        if (!workfiledir.exists()) {
            workfiledir.mkdirs();
        }
        String crc = "";
        String tmpFileName = UUID.randomUUID().toString();
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

    private void abortUpload(HttpServletRequest request,
            HttpServletResponse response, DistributorParams dparams,
            String param) throws IOException, ServletException {
        boolean ret=fileUtil.removePendingFile(param);
        response.setHeader("mimetype", "text/json-comment-filtered");
        PrintWriter out = response.getWriter();
        out.print("{\"deleted\":\"" + ret+"\"}");
    }

    private void eofCheck(HttpServletRequest request,
            HttpServletResponse response, DistributorParams dparams,
            String param) throws IOException, ServletException {
        String pdir = request.getParameter("dirToPlace");
        boolean ret = false;
        File dir = new File(org.semanticwb.SWBPortal.getWorkPath(), pdir);
        if (dir.exists()) {
            PendingFile pf = fileUtil.getPendingFileFromId(param);
            File dest = new File(dir,pf.getFilename());
            if (!dest.exists()) {
                File workfiledir = new File(tmpplace, param);
                File orig = new File(workfiledir,pf.getFilename());
                ret = orig.renameTo(dest);
                if (!ret){
                    FileInputStream fis = new FileInputStream(orig);
                    FileOutputStream fos = new FileOutputStream(dest);
                    byte[] buffer = new byte[8192];
                    int readbytes = 0;
                    while ((readbytes=fis.read(buffer))>-1){
                        fos.write(buffer, 0, readbytes);
                    }
                    fis.close();
                    fos.flush();
                    fos.close();
                    orig.delete();
                    ret = true;
                }
                workfiledir.delete();
                fileUtil.updateChanges();
            }
        }
        response.setHeader("mimetype", "text/json-comment-filtered");
        PrintWriter out = response.getWriter();
        out.print("{\"saved\":\"" + ret+"\"}");
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

    private void giveStatus(HttpServletRequest request, 
            HttpServletResponse response, DistributorParams dparams, 
            String param) throws IOException {
        PendingFile pf = fileUtil.getPendingFileFromId(param);
        response.setHeader("mimetype", "text/json-comment-filtered");
        PrintWriter out = response.getWriter();
        long localsize = 0;
        File wrkfile = new File(tmpplace, pf.getId());
        File updFile = new File(wrkfile, pf.getFilename());
        if (updFile.exists()) {
            localsize = updFile.length();
        }
        out.print("{\"id\":\""+ pf.getId() + "\",\"bytesRecived\":" + 
                localsize + "}");
    }
}
