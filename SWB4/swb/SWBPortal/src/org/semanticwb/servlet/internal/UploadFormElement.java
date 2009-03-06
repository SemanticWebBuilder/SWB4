/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.servlet.internal;

import com.missiondata.fileupload.MonitoredDiskFileItemFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.util.fileupload.FileUploadListener;
import org.semanticwb.portal.util.fileupload.UploadStatus;


/**
 *
 * @author jorge.jimenez
 */
public class UploadFormElement implements InternalServlet {

    private static Logger log = SWBUtils.getLogger(UploadFormElement.class);
    public static final String UPLOAD_STATUS = "uploadStatus";
    public static final String FILE_UPLOAD_STATS = "fileUploadStats";
    public static final String FILES_UPLOADED = "FILES_UPLOADED";
    public static final String UNIQUE_IDENTIFIER = "uniqueFileIdentifier";

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UploadStatus status = new UploadStatus();
        session.setAttribute(UploadFormElement.UPLOAD_STATUS, null);

        try {
            FileUploadListener listener = new FileUploadListener(request.getContentLength());
            session.setAttribute(FILE_UPLOAD_STATS, listener.getFileUploadStats());
            MonitoredDiskFileItemFactory factory = new MonitoredDiskFileItemFactory(listener);
            factory.setSizeThreshold(4096); // 4Kb tamaño por encima del cual los ficheros son escritos directamente en disco
            factory.setRepository(new File(SWBPlatform.getWorkPath()));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(1024*512); //512 Kb tamaño maximo de archivo a subir
            List items = upload.parseRequest(request);
            boolean hasError = false;

            // process the files
            storeFilesOnSession(request, items);

            // one can retrieve the FileItem objects in a different servlet, using
            // List filesUploaded = (List) session.getAttribute(UploadServlet.FILES_UPLOADED);

            if (!hasError) {
                status.setStatus(UploadStatus.STATUS_OK);
            } else {
                status.setStatus(UploadStatus.STATUS_ERROR);
                status.setMessage("Could not process uploaded file. Please see log for details.");
            }
        }
        catch (Exception e) {
            status.setStatus(UploadStatus.STATUS_ERROR);
            status.setMessage(e.getMessage());
        }
        session.setAttribute(UPLOAD_STATUS, status);

    }

    public void init(ServletContext config) throws ServletException {
        log.event("Initializing InternalServlet UploadFormElement...");
    }

    private void storeFilesOnSession(HttpServletRequest request, List items) {
        // check if there's a request to identify the file to be uploaded
        String uniqueIdentifier = request.getParameter(UNIQUE_IDENTIFIER);
        
        //if (uniqueIdentifier != null) { //comentado Jorge Jiménez
            // if an identifier for these files has been passed, make the FileItem objects available
            // in the session
            List<FileItem> files = (List<FileItem>) request.getSession().getAttribute(uniqueIdentifier);
            if (files == null) {
                files = new ArrayList<FileItem>();
            }

            for (Iterator i = items.iterator(); i.hasNext();) {
                FileItem fileItem = (FileItem) i.next();
                if (!fileItem.isFormField()) {
                    files.add(fileItem);
                }
            }
            request.getSession().setAttribute(FILES_UPLOADED, files);
        //}
    }

}
