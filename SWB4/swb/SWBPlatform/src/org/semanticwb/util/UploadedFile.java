/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.util;

// TODO: Auto-generated Javadoc
/**
 * The Class UploadedFile.
 * 
 * @author serch
 */
public class UploadedFile
{

    /** The original name. */
    private final String originalName;
    
    /** The tmpuploaded canonical file name. */
    private final String tmpuploadedCanonicalFileName;
    
    /** The group id. */
    private final String groupID;

    /**
     * Instantiates a new uploaded file.
     * 
     * @param originalName the original name
     * @param tmpuploadedCanonicalFileName the tmpuploaded canonical file name
     * @param groupID the group id
     */
    public UploadedFile(String originalName, String tmpuploadedCanonicalFileName, String groupID)
    {
        this.originalName = originalName;
        this.tmpuploadedCanonicalFileName = tmpuploadedCanonicalFileName;
        this.groupID = groupID;
    }

    /**
     * Gets the group id.
     * 
     * @return the group id
     */
    public String getGroupID()
    {
        return groupID;
    }

    /**
     * Gets the original name.
     * 
     * @return the original name
     */
    public String getOriginalName()
    {
        return originalName;
    }

    /**
     * Gets the tmpuploaded canonical file name.
     * 
     * @return the tmpuploaded canonical file name
     */
    public String getTmpuploadedCanonicalFileName()
    {
        return tmpuploadedCanonicalFileName;
    }
}
