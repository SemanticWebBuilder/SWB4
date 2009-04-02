package org.semanticwb.repository.office.base;


public class ArticuloBase extends org.semanticwb.repository.office.OfficeContent implements org.semanticwb.content.Descriptiveable,org.semanticwb.repository.Referenceable,org.semanticwb.repository.Versionable,org.semanticwb.repository.Traceable,org.semanticwb.repository.Lockable
{
    public static final org.semanticwb.platform.SemanticClass nt_VersionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");
    public static final org.semanticwb.platform.SemanticProperty jcr_versionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#versionHistory");
    public static final org.semanticwb.platform.SemanticClass nt_Version=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
    public static final org.semanticwb.platform.SemanticProperty jcr_baseVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#baseVersion");
    public static final org.semanticwb.platform.SemanticProperty jcr_mergeFailed=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mergeFailed");
    public static final org.semanticwb.platform.SemanticProperty jcr_isCheckedOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#isCheckedOut");
    public static final org.semanticwb.platform.SemanticProperty cm_Category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#Category");
    public static final org.semanticwb.platform.SemanticClass cm_Articulo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#Articulo");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#Articulo");

    public ArticuloBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.repository.office.Articulo getArticulo(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.office.Articulo)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.repository.office.Articulo> listArticulos(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.Articulo>(org.semanticwb.repository.office.Articulo.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.repository.office.Articulo> listArticulos()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.Articulo>(org.semanticwb.repository.office.Articulo.class, it, true);
    }

    public static org.semanticwb.repository.office.Articulo createArticulo(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.office.Articulo)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeArticulo(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasArticulo(String id, org.semanticwb.model.SWBModel model)
    {
        return (getArticulo(id, model)!=null);
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
             ret=(org.semanticwb.repository.VersionHistory)obj.createGenericInstance();
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
             ret=(org.semanticwb.repository.Version)obj.createGenericInstance();
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
             ret=(org.semanticwb.repository.Version)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isCheckedOut()
    {
        return getSemanticObject().getBooleanProperty(jcr_isCheckedOut);
    }

    public void setCheckedOut(boolean isCheckedOut)
    {
        getSemanticObject().setBooleanProperty(jcr_isCheckedOut, isCheckedOut);
    }

    public String getCategory()
    {
        return getSemanticObject().getProperty(cm_Category);
    }

    public void setCategory(String Category)
    {
        getSemanticObject().setProperty(cm_Category, Category);
    }
}
