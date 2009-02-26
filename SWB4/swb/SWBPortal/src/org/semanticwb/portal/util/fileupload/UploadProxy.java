package org.semanticwb.portal.util.fileupload;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;

import javax.servlet.http.HttpSession;
import org.semanticwb.servlet.internal.UploadFormElement;

/**
 * Handles DWR progress requests. Progress requests should start after the upload request occurs. If
 * a request for progress is still made, this proxy should return an UploadStatus.STATUS_RETRY. If
 * the upload finished, the servlet should mark the finished upload by setting an UploadStatus object
 * on the session, in which case, the next progress request to this proxy should send that very same object.
 *
 * @author Marius Hanganu
 * @see ro.tremend.upload.UploadStatus
 * @see ro.tremend.upload.UploadServlet
 */
public class UploadProxy {

    public UploadStatus getStatus() {
        System.out.println("entra a UploadProxy/getStatus-J:"+UploadFormElement.UPLOAD_STATUS);
        WebContext webCtx = WebContextFactory.get();
        HttpSession session = webCtx.getSession();

        // if status has been set by the UploadServlet, then return that status
        // this occurs when upload has finished and UploadServlet specify itself a termination status
        UploadStatus status = (UploadStatus) session.getAttribute(UploadFormElement.UPLOAD_STATUS);
        if (status != null) {
            session.setAttribute(UploadFormElement.UPLOAD_STATUS, null);
            return status;
        }

        // otherwise continue gathering info
        FileUploadListener.FileUploadStats fileUploadStats = (FileUploadListener.FileUploadStats) session.getAttribute(UploadFormElement.FILE_UPLOAD_STATS);
        System.out.println("entra a UploadProxy/getStatus-J1");
        status = new UploadStatus();
        if (fileUploadStats != null) {
            System.out.println("entra a UploadProxy/getStatus-J2");
            long bytesProcessed = fileUploadStats.getBytesRead();
            long sizeTotal = fileUploadStats.getTotalSize();
            long percentComplete = (long) Math.floor(((double) bytesProcessed / (double) sizeTotal) * 100.0);
            long timeInSeconds = fileUploadStats.getElapsedTimeInSeconds();
            double uploadRate = bytesProcessed / (timeInSeconds + 0.00001);
            double estimatedRuntime = sizeTotal / (uploadRate + 0.00001);

            if (fileUploadStats.getBytesRead() < fileUploadStats.getTotalSize()) {
                status.setStatus(UploadStatus.STATUS_IN_PROGRESS);
                status.setBytesProcessed(bytesProcessed);
                status.setEstimatedRuntime(estimatedRuntime);
                status.setPercentComplete(percentComplete);
                status.setSizeTotal(sizeTotal);
                status.setTimeInSeconds(timeInSeconds);
                status.setUploadRate(uploadRate);
            } else {
                status.setStatus(UploadStatus.STATUS_OK);
            }
            System.out.println("entra a UploadProxy/getStatus-J3");
            if (fileUploadStats != null && fileUploadStats.getBytesRead() == fileUploadStats.getTotalSize()) {
                status.setStatus(UploadStatus.STATUS_OK);
            }
        } else {
            status.setStatus(UploadStatus.STATUS_RETRY);
        }
        System.out.println("entra a UploadProxy/getStatus-J4:"+status);
        return status;
    }
}
