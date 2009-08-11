/**  
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
**/ 
 
package org.semanticwb.portal.util.fileupload;

/**
 * Contains upload status information: error message if any, bytes processed, time remaining, etc.
 * The status indicators (STATUS_IN_PROGRESS, STATUS_OK, STATUS_ERROR, STATUS_RETRY) provide meaningful
 * information for the web page. If an error occurs, the details are offered accessing the embedded message.
 * 
 * @author Marius Hanganu
 */
public class UploadStatus {
    /**
     * Upload is in progress (continue making progress requests)
     */
    public static final int STATUS_IN_PROGRESS = 1;

    /**
     * Upload finished successfully (stop monitoring the upload)
     */
    public static final int STATUS_OK = 2;

    /**
     * An error occured during upload (check the message)
     */
    public static final int STATUS_ERROR = 3;

    /**
     * Perhaps progress requests were made before the upload started, so keep asking the upload status
     */
    public static final int STATUS_RETRY = 4;

    private int status;
    private String message;
    private long bytesProcessed;
    private long sizeTotal;
    private long percentComplete;
    private double uploadRate;
    private long timeInSeconds;
    private double estimatedRuntime;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getBytesProcessed() {
        return bytesProcessed;
    }

    public void setBytesProcessed(long bytesProcessed) {
        this.bytesProcessed = bytesProcessed;
    }

    public long getSizeTotal() {
        return sizeTotal;
    }

    public void setSizeTotal(long sizeTotal) {
        this.sizeTotal = sizeTotal;
    }

    public long getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(long percentComplete) {
        this.percentComplete = percentComplete;
    }

    public double getUploadRate() {
        return uploadRate;
    }

    public void setUploadRate(double uploadRate) {
        this.uploadRate = uploadRate;
    }

    public long getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(long timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public double getEstimatedRuntime() {
        return estimatedRuntime;
    }

    public void setEstimatedRuntime(double estimatedRuntime) {
        this.estimatedRuntime = estimatedRuntime;
    }
}
