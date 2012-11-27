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

// TODO: Auto-generated Javadoc
/**
 * Contains upload status information: error message if any, bytes processed, time remaining, etc.
 * The status indicators (STATUS_IN_PROGRESS, STATUS_OK, STATUS_ERROR, STATUS_RETRY) provide meaningful
 * information for the web page. If an error occurs, the details are offered accessing the embedded message.
 * 
 * @author Marius Hanganu
 */
public class UploadStatus {
    
    /** Upload is in progress (continue making progress requests). */
    public static final int STATUS_IN_PROGRESS = 1;

    /** Upload finished successfully (stop monitoring the upload). */
    public static final int STATUS_OK = 2;

    /** An error occured during upload (check the message). */
    public static final int STATUS_ERROR = 3;

    /** Perhaps progress requests were made before the upload started, so keep asking the upload status. */
    public static final int STATUS_RETRY = 4;

    /** The status. */
    private int status;
    
    /** The message. */
    private String message;
    
    /** The bytes processed. */
    private long bytesProcessed;
    
    /** The size total. */
    private long sizeTotal;
    
    /** The percent complete. */
    private long percentComplete;
    
    /** The upload rate. */
    private double uploadRate;
    
    /** The time in seconds. */
    private long timeInSeconds;
    
    /** The estimated runtime. */
    private double estimatedRuntime;

    /**
     * Gets the status.
     * 
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * 
     * @param status the new status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the message.
     * 
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     * 
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the bytes processed.
     * 
     * @return the bytes processed
     */
    public long getBytesProcessed() {
        return bytesProcessed;
    }

    /**
     * Sets the bytes processed.
     * 
     * @param bytesProcessed the new bytes processed
     */
    public void setBytesProcessed(long bytesProcessed) {
        this.bytesProcessed = bytesProcessed;
    }

    /**
     * Gets the size total.
     * 
     * @return the size total
     */
    public long getSizeTotal() {
        return sizeTotal;
    }

    /**
     * Sets the size total.
     * 
     * @param sizeTotal the new size total
     */
    public void setSizeTotal(long sizeTotal) {
        this.sizeTotal = sizeTotal;
    }

    /**
     * Gets the percent complete.
     * 
     * @return the percent complete
     */
    public long getPercentComplete() {
        return percentComplete;
    }

    /**
     * Sets the percent complete.
     * 
     * @param percentComplete the new percent complete
     */
    public void setPercentComplete(long percentComplete) {
        this.percentComplete = percentComplete;
    }

    /**
     * Gets the upload rate.
     * 
     * @return the upload rate
     */
    public double getUploadRate() {
        return uploadRate;
    }

    /**
     * Sets the upload rate.
     * 
     * @param uploadRate the new upload rate
     */
    public void setUploadRate(double uploadRate) {
        this.uploadRate = uploadRate;
    }

    /**
     * Gets the time in seconds.
     * 
     * @return the time in seconds
     */
    public long getTimeInSeconds() {
        return timeInSeconds;
    }

    /**
     * Sets the time in seconds.
     * 
     * @param timeInSeconds the new time in seconds
     */
    public void setTimeInSeconds(long timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    /**
     * Gets the estimated runtime.
     * 
     * @return the estimated runtime
     */
    public double getEstimatedRuntime() {
        return estimatedRuntime;
    }

    /**
     * Sets the estimated runtime.
     * 
     * @param estimatedRuntime the new estimated runtime
     */
    public void setEstimatedRuntime(double estimatedRuntime) {
        this.estimatedRuntime = estimatedRuntime;
    }
}
