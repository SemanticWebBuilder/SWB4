package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class VersionInfoBase extends SemanticObject implements Valueable
{
    public VersionInfoBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }
    public SemanticIterator<org.semanticwb.model.VersionInfo> listVersionInfo()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.nextVersion.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.VersionInfo>(org.semanticwb.model.VersionInfo.class, stit);
    }
    public void addVersionInfo(org.semanticwb.model.VersionInfo versioninfo)
    {
        addObjectProperty(Vocabulary.nextVersion, versioninfo);
    }
    public void removeAllVersionInfo()
    {
        getRDFResource().removeAll(Vocabulary.nextVersion.getRDFProperty());
    }
    public VersionInfo getVersionInfo()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.nextVersion.getRDFProperty());
         SemanticIterator<org.semanticwb.model.VersionInfo> it=new SemanticIterator<org.semanticwb.model.VersionInfo>(VersionInfo.class, stit);
         return it.next();
    }
    public String getValue()
    {
        return getProperty(Vocabulary.value);
    }
    public void setValue(String value)
    {
        setProperty(Vocabulary.value, value);
    }
    public String getVersionComment()
    {
        return getProperty(Vocabulary.versionComment);
    }
    public void setVersionComment(String versionComment)
    {
        setProperty(Vocabulary.versionComment, versionComment);
    }
    public Date getVersionCreated()
    {
        return getDateProperty(Vocabulary.versionCreated);
    }
    public void setVersionCreated(Date versionCreated)
    {
        setDateProperty(Vocabulary.versionCreated, versionCreated);
    }
}
