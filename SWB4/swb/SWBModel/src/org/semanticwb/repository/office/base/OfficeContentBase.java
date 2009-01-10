package org.semanticwb.repository.office.base;


public class OfficeContentBase extends org.semanticwb.repository.File implements org.semanticwb.content.Descriptiveable,org.semanticwb.repository.Lockable,org.semanticwb.repository.Versionable
{
    public static final org.semanticwb.platform.SemanticProperty cm_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#title");
    public static final org.semanticwb.platform.SemanticProperty cm_file=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#file");
    public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
    public static final org.semanticwb.platform.SemanticProperty cm_officetype=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#officetype");
    public static final org.semanticwb.platform.SemanticProperty jcr_lockIsDeep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lockIsDeep");
    public static final org.semanticwb.platform.SemanticProperty cm_user=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#user");
    public static final org.semanticwb.platform.SemanticProperty jcr_lockOwner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lockOwner");
    public static final org.semanticwb.platform.SemanticProperty jcr_isCheckedOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#isCheckedOut");
    public static final org.semanticwb.platform.SemanticClass nt_VersionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");
    public static final org.semanticwb.platform.SemanticProperty jcr_versionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#versionHistory");
    public static final org.semanticwb.platform.SemanticClass nt_Version=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
    public static final org.semanticwb.platform.SemanticProperty jcr_baseVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#baseVersion");
    public static final org.semanticwb.platform.SemanticProperty jcr_mergeFailed=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mergeFailed");
    public static final org.semanticwb.platform.SemanticProperty cm_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#description");
    public static final org.semanticwb.platform.SemanticClass cm_OfficeContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#OfficeContent");

    public OfficeContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(cm_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(cm_title, title);
    }

    public String getFile()
    {
        return getSemanticObject().getProperty(cm_file);
    }

    public void setFile(String file)
    {
        getSemanticObject().setProperty(cm_file, file);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String uuid)
    {
        getSemanticObject().setProperty(jcr_uuid, uuid);
    }

    public String getOfficetype()
    {
        return getSemanticObject().getProperty(cm_officetype);
    }

    public void setOfficetype(String officetype)
    {
        getSemanticObject().setProperty(cm_officetype, officetype);
    }

    public boolean isLockIsDeep()
    {
        return getSemanticObject().getBooleanProperty(jcr_lockIsDeep);
    }

    public void setLockIsDeep(boolean lockIsDeep)
    {
        getSemanticObject().setBooleanProperty(jcr_lockIsDeep, lockIsDeep);
    }

    public String getUser()
    {
        return getSemanticObject().getProperty(cm_user);
    }

    public void setUser(String user)
    {
        getSemanticObject().setProperty(cm_user, user);
    }

    public String getLockOwner()
    {
        return getSemanticObject().getProperty(jcr_lockOwner);
    }

    public void setLockOwner(String lockOwner)
    {
        getSemanticObject().setProperty(jcr_lockOwner, lockOwner);
    }

    public boolean isCheckedOut()
    {
        return getSemanticObject().getBooleanProperty(jcr_isCheckedOut);
    }

    public void setCheckedOut(boolean isCheckedOut)
    {
        getSemanticObject().setBooleanProperty(jcr_isCheckedOut, isCheckedOut);
    }

    public void setVersionHistory(org.semanticwb.repository.VersionHistory versionhistory)
    {
        getSemanticObject().setObjectProperty(jcr_versionHistory, versionhistory.getSemanticObject());
    }

    public void removeVersionHistory()
    {
        getSemanticObject().removeProperty(jcr_versionHistory);
    }

    public org.semanticwb.repository.VersionHistory getVersionHistory()
    {
         org.semanticwb.repository.VersionHistory ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_versionHistory);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.VersionHistory)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setBaseVersion(org.semanticwb.repository.Version version)
    {
        getSemanticObject().setObjectProperty(jcr_baseVersion, version.getSemanticObject());
    }

    public void removeBaseVersion()
    {
        getSemanticObject().removeProperty(jcr_baseVersion);
    }

    public org.semanticwb.repository.Version getBaseVersion()
    {
         org.semanticwb.repository.Version ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_baseVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.Version)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setMergeFailed(org.semanticwb.repository.Version version)
    {
        getSemanticObject().setObjectProperty(jcr_mergeFailed, version.getSemanticObject());
    }

    public void removeMergeFailed()
    {
        getSemanticObject().removeProperty(jcr_mergeFailed);
    }

    public org.semanticwb.repository.Version getMergeFailed()
    {
         org.semanticwb.repository.Version ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_mergeFailed);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.Version)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(cm_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(cm_description, description);
    }
}
