package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class VersionInfoBase extends SemanticObject implements Valueable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public VersionInfoBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public void setNextVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        addObjectProperty(vocabulary.nextVersion, versioninfo);
    }

    public void removeNextVersion()
    {
        removeProperty(vocabulary.nextVersion);
    }

    public VersionInfo getNextVersion()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.nextVersion.getRDFProperty());
         SemanticIterator<org.semanticwb.model.VersionInfo> it=new SemanticIterator<org.semanticwb.model.VersionInfo>(VersionInfo.class, stit);
         return it.next();
    }

    public String getValue()
    {
        return getProperty(vocabulary.value);
    }

    public void setValue(String value)
    {
        setProperty(vocabulary.value, value);
    }

    public void setPreviousVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        addObjectProperty(vocabulary.previousVersion, versioninfo);
    }

    public void removePreviousVersion()
    {
        removeProperty(vocabulary.previousVersion);
    }

    public VersionInfo getPreviousVersion()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.previousVersion.getRDFProperty());
         SemanticIterator<org.semanticwb.model.VersionInfo> it=new SemanticIterator<org.semanticwb.model.VersionInfo>(VersionInfo.class, stit);
         return it.next();
    }

    public String getVersionComment()
    {
        return getProperty(vocabulary.versionComment);
    }

    public void setVersionComment(String versionComment)
    {
        setProperty(vocabulary.versionComment, versionComment);
    }

    public Date getVersionCreated()
    {
        return getDateProperty(vocabulary.versionCreated);
    }

    public void setVersionCreated(Date versionCreated)
    {
        setDateProperty(vocabulary.versionCreated, versionCreated);
    }
}
