package org.semanticwb.model.catalogs.base;


public class CatalogsBase extends org.semanticwb.model.SWBModel 
{
    public static final org.semanticwb.platform.SemanticClass swbc_Catalogs=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#Catalogs");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#Catalogs");

    public CatalogsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.Catalogs> listCatalogss(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.Catalogs>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.Catalogs> listCatalogss()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.Catalogs>(it, true);
    }

    public static org.semanticwb.model.catalogs.Catalogs getCatalogs(String id)
    {
       org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
       org.semanticwb.model.catalogs.Catalogs ret=null;
       org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
       if(model!=null)
       {
           org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
           if(obj!=null)
           {
               ret=(org.semanticwb.model.catalogs.Catalogs)obj.createGenericInstance();
           }
       }
       return ret;
    }

    public static org.semanticwb.model.catalogs.Catalogs createCatalogs(String id, String namespace)
    {
        org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
        org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
        return (org.semanticwb.model.catalogs.Catalogs)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
    }

    public static void removeCatalogs(String id)
    {
       org.semanticwb.model.catalogs.Catalogs obj=getCatalogs(id);
       if(obj!=null)
       {
           obj.remove();
       }
    }

    public static boolean hasCatalogs(String id)
    {
        return (getCatalogs(id)!=null);
    }
}
