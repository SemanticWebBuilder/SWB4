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
package org.semanticwb.portal.util.fileupload;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;

import javax.servlet.http.HttpSession;
import org.semanticwb.servlet.internal.UploadFormElement;

// TODO: Auto-generated Javadoc
/**
 * Handles DWR progress requests. Progress requests should start after the upload request occurs. If
 * a request for progress is still made, this proxy should return an UploadStatus.STATUS_RETRY. If
 * the upload finished, the servlet should mark the finished upload by setting an UploadStatus object
 * on the session, in which case, the next progress request to this proxy should send that very same object.
 *
 * @author Marius Hanganu
 * @see ro.tremend.upload.UploadStatus
 * @see ro.tremend.upload.UploadServlet
 *
 */
public class UploadProxy {

    /**
     * Gets the status.
     * 
     * @return the status
     */
    public UploadStatus getStatus() {
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
        status = new UploadStatus();
        if (fileUploadStats != null) {
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
            if (fileUploadStats != null && fileUploadStats.getBytesRead() == fileUploadStats.getTotalSize()) {
                status.setStatus(UploadStatus.STATUS_OK);
            }
        } else {
            status.setStatus(UploadStatus.STATUS_RETRY);
        }
        return status;
    }
}
