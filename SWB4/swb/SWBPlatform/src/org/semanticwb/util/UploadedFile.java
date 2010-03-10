/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.util;

/**
 *
 * @author serch
 */
public class UploadedFile
{

    private final String originalName;
    private final String tmpuploadedCanonicalFileName;
    private final String groupID;

    public UploadedFile(String originalName, String tmpuploadedCanonicalFileName, String groupID)
    {
        this.originalName = originalName;
        this.tmpuploadedCanonicalFileName = tmpuploadedCanonicalFileName;
        this.groupID = groupID;
    }

    public String getGroupID()
    {
        return groupID;
    }

    public String getOriginalName()
    {
        return originalName;
    }

    public String getTmpuploadedCanonicalFileName()
    {
        return tmpuploadedCanonicalFileName;
    }
}
