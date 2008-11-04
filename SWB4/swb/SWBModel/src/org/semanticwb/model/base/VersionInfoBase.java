package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class VersionInfoBase extends GenericObjectBase implements Traceable,Valueable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public VersionInfoBase(SemanticObject base)
    {
        super(base);
    }

    public Date getCreated()
    {
        return getSemanticObject().getDateProperty(vocabulary.created);
    }

    public void setCreated(Date created)
    {
        getSemanticObject().setDateProperty(vocabulary.created, created);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.modifiedBy);
    }

    public User getModifiedBy()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.modifiedBy);
         if(obj!=null)
         {
             ret=(User)vocabulary.User.newGenericInstance(obj);
         }
         return ret;
    }

    public String getValue()
    {
        return getSemanticObject().getProperty(vocabulary.value);
    }

    public void setValue(String value)
    {
        getSemanticObject().setProperty(vocabulary.value, value);
    }

    public void setVersionLockedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.versionLockedBy, user.getSemanticObject());
    }

    public void removeVersionLockedBy()
    {
        getSemanticObject().removeProperty(vocabulary.versionLockedBy);
    }

    public User getVersionLockedBy()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.versionLockedBy);
         if(obj!=null)
         {
             ret=(User)vocabulary.User.newGenericInstance(obj);
         }
         return ret;
    }

    public void setPreviousVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(vocabulary.previousVersion, versioninfo.getSemanticObject());
    }

    public void removePreviousVersion()
    {
        getSemanticObject().removeProperty(vocabulary.previousVersion);
    }

    public VersionInfo getPreviousVersion()
    {
         VersionInfo ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.previousVersion);
         if(obj!=null)
         {
             ret=(VersionInfo)vocabulary.VersionInfo.newGenericInstance(obj);
         }
         return ret;
    }

    public String getVersionFile()
    {
        return getSemanticObject().getProperty(vocabulary.versionFile);
    }

    public void setVersionFile(String versionFile)
    {
        getSemanticObject().setProperty(vocabulary.versionFile, versionFile);
    }

    public String getVersionComment()
    {
        return getSemanticObject().getProperty(vocabulary.versionComment);
    }

    public void setVersionComment(String versionComment)
    {
        getSemanticObject().setProperty(vocabulary.versionComment, versionComment);
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.updated, updated);
    }

    public void setNextVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(vocabulary.nextVersion, versioninfo.getSemanticObject());
    }

    public void removeNextVersion()
    {
        getSemanticObject().removeProperty(vocabulary.nextVersion);
    }

    public VersionInfo getNextVersion()
    {
         VersionInfo ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.nextVersion);
         if(obj!=null)
         {
             ret=(VersionInfo)vocabulary.VersionInfo.newGenericInstance(obj);
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.creator);
    }

    public User getCreator()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.creator);
         if(obj!=null)
         {
             ret=(User)vocabulary.User.newGenericInstance(obj);
         }
         return ret;
    }

    public int getVersionNumber()
    {
        return getSemanticObject().getIntProperty(vocabulary.versionNumber);
    }

    public void setVersionNumber(int versionNumber)
    {
        getSemanticObject().setLongProperty(vocabulary.versionNumber, versionNumber);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public Iterator<GenericObject> listRelatedObjects()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, null, getSemanticObject().getRDFResource());
        return new GenericIterator((SemanticClass)null, stit,true);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
