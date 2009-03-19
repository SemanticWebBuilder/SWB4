package org.semanticwb.model.base;


public class ArticuloBase extends org.semanticwb.repository.office.OfficeContent implements org.semanticwb.repository.Traceable,org.semanticwb.repository.Versionable,org.semanticwb.repository.Referenceable,org.semanticwb.repository.Lockable,org.semanticwb.content.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty cm_Category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#Category");
    public static final org.semanticwb.platform.SemanticClass cm_Articulo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#Articulo");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#Articulo");

    public ArticuloBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.Articulo getArticulo(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.Articulo)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.Articulo> listArticulos(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Articulo>(org.semanticwb.model.Articulo.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.Articulo> listArticulos()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Articulo>(org.semanticwb.model.Articulo.class, it, true);
    }

    public static org.semanticwb.model.Articulo createArticulo(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.Articulo)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeArticulo(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasArticulo(String id, org.semanticwb.model.SWBModel model)
    {
        return (getArticulo(id, model)!=null);
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
