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

import com.missiondata.fileupload.OutputStreamListener;

// TODO: Auto-generated Javadoc
/**
 * Originally from carsonm: http://blogs.missiondata.com/java/28/file-upload-progress-with-ajax-and-java-and-prototype/
 * @author (integrated by) Jorge Jiménez
 */
public class FileUploadListener implements OutputStreamListener {
    
    /** The file upload stats. */
    private FileUploadStats fileUploadStats = new FileUploadStats();

    /**
     * Instantiates a new file upload listener.
     * 
     * @param totalSize the total size
     */
    public FileUploadListener(long totalSize) {
        fileUploadStats.setTotalSize(totalSize);
    }

    /* (non-Javadoc)
     * @see com.missiondata.fileupload.OutputStreamListener#start()
     */
    /**
     * Start.
     */
    public void start() {
        fileUploadStats.setCurrentStatus("start");
    }

    /* (non-Javadoc)
     * @see com.missiondata.fileupload.OutputStreamListener#bytesRead(int)
     */
    /**
     * Bytes read.
     * 
     * @param byteCount the byte count
     */
    public void bytesRead(int byteCount) {
        fileUploadStats.incrementBytesRead(byteCount);
        fileUploadStats.setCurrentStatus("reading");
    }

    /* (non-Javadoc)
     * @see com.missiondata.fileupload.OutputStreamListener#error(java.lang.String)
     */
    /**
     * Error.
     * 
     * @param s the s
     */
    public void error(String s) {
        fileUploadStats.setCurrentStatus("error");
    }

    /* (non-Javadoc)
     * @see com.missiondata.fileupload.OutputStreamListener#done()
     */
    /**
     * Done.
     */
    public void done() {
        fileUploadStats.setBytesRead(fileUploadStats.getTotalSize());
        fileUploadStats.setCurrentStatus("done");
    }

    /**
     * Gets the file upload stats.
     * 
     * @return the file upload stats
     */
    public FileUploadStats getFileUploadStats() {
        return fileUploadStats;
    }

    /**
     * The Class FileUploadStats.
     */
    public static class FileUploadStats {
        
        /** The total size. */
        private long totalSize = 0;
        
        /** The bytes read. */
        private long bytesRead = 0;
        
        /** The start time. */
        private long startTime = System.currentTimeMillis();
        
        /** The current status. */
        private String currentStatus = "none";

        /**
         * Gets the total size.
         * 
         * @return the total size
         */
        public long getTotalSize() {
            return totalSize;
        }

        /**
         * Sets the total size.
         * 
         * @param totalSize the new total size
         */
        public void setTotalSize(long totalSize) {
            this.totalSize = totalSize;
        }

        /**
         * Gets the bytes read.
         * 
         * @return the bytes read
         */
        public long getBytesRead() {
            return bytesRead;
        }

        /**
         * Gets the elapsed time in seconds.
         * 
         * @return the elapsed time in seconds
         */
        public long getElapsedTimeInSeconds() {
            return (System.currentTimeMillis() - startTime) / 1000;
        }

        /**
         * Gets the current status.
         * 
         * @return the current status
         */
        public String getCurrentStatus() {
            return currentStatus;
        }

        /**
         * Sets the current status.
         * 
         * @param currentStatus the new current status
         */
        public void setCurrentStatus(String currentStatus) {
            this.currentStatus = currentStatus;
        }

        /**
         * Sets the bytes read.
         * 
         * @param bytesRead the new bytes read
         */
        public void setBytesRead(long bytesRead) {
            this.bytesRead = bytesRead;
        }

        /**
         * Increment bytes read.
         * 
         * @param byteCount the byte count
         */
        public void incrementBytesRead(int byteCount) {
            this.bytesRead += byteCount;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return "FileUploadStats{" +
                    "totalSize=" + totalSize +
                    ", bytesRead=" + bytesRead +
                    ", startTime=" + startTime +
                    ", currentStatus='" + currentStatus + '\'' +
                    '}';
        }
    }
}
