package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class VersionInfoBase extends GenericObjectBase implements Valueable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public VersionInfoBase(SemanticObject base)
    {
        super(base);
    }

    public String getValue()
    {
        return getSemanticObject().getProperty(vocabulary.value);
    }

    public void setValue(String value)
    {
        getSemanticObject().setProperty(vocabulary.value, value);
    }

    public void setPreviousVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().addObjectProperty(vocabulary.previousVersion, versioninfo.getSemanticObject());
    }

    public void removePreviousVersion()
    {
        getSemanticObject().removeProperty(vocabulary.previousVersion);
    }

    public VersionInfo getPreviousVersion()
    {
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.previousVersion);
         return (VersionInfo)vocabulary.VersionInfo.newGenericInstance(obj);
    }

    public String getVersionComment()
    {
        return getSemanticObject().getProperty(vocabulary.versionComment);
    }

    public void setVersionComment(String versionComment)
    {
        getSemanticObject().setProperty(vocabulary.versionComment, versionComment);
    }

    public String getVersionFile()
    {
        return getSemanticObject().getProperty(vocabulary.versionFile);
    }

    public void setVersionFile(String versionFile)
    {
        getSemanticObject().setProperty(vocabulary.versionFile, versionFile);
    }

    public void setNextVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().addObjectProperty(vocabulary.nextVersion, versioninfo.getSemanticObject());
    }

    public void removeNextVersion()
    {
        getSemanticObject().removeProperty(vocabulary.nextVersion);
    }

    public VersionInfo getNextVersion()
    {
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.nextVersion);
         return (VersionInfo)vocabulary.VersionInfo.newGenericInstance(obj);
    }

    public Date getVersionCreated()
    {
        return getSemanticObject().getDateProperty(vocabulary.versionCreated);
    }

    public void setVersionCreated(Date versionCreated)
    {
        getSemanticObject().setDateProperty(vocabulary.versionCreated, versionCreated);
    }

    public int getVersionNumber()
    {
        return getSemanticObject().getIntProperty(vocabulary.versionNumber);
    }

    public void setVersionNumber(int versionNumber)
    {
        getSemanticObject().setLongProperty(vocabulary.versionNumber, versionNumber);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
