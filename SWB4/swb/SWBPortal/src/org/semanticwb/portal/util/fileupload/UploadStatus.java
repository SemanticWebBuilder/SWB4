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
        System.out.println("entra a UploadStatus/getStatus-J");
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
