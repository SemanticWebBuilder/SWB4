package org.semanticwb.model.base;


public class UserFavoritesBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass owl_Thing=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2002/07/owl#Thing");
    public static final org.semanticwb.platform.SemanticProperty swb_usrfHasObjects=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrfHasObjects");
    public static final org.semanticwb.platform.SemanticClass swb_UserFavorites=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorites");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorites");

    public UserFavoritesBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.UserFavorites getUserFavorites(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.UserFavorites)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.UserFavorites> listUserFavoritess(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFavorites>(org.semanticwb.model.UserFavorites.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.UserFavorites> listUserFavoritess()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFavorites>(org.semanticwb.model.UserFavorites.class, it, true);
    }

    public static org.semanticwb.model.UserFavorites createUserFavorites(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.UserFavorites)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static org.semanticwb.model.UserFavorites createUserFavorites(org.semanticwb.model.SWBModel model)
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(model.getSemanticObject().getModel().getName()+"/"+sclass.getName());
        return org.semanticwb.model.UserFavorites.createUserFavorites(String.valueOf(id), model);
    }

    public static void removeUserFavorites(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasUserFavorites(String id, org.semanticwb.model.SWBModel model)
    {
        return (getUserFavorites(id, model)!=null);
    }

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listObjects()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swb_usrfHasObjects.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addObject(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().addObjectProperty(swb_usrfHasObjects, semanticobject);
    }

    public void removeAllObject()
    {
        getSemanticObject().removeProperty(swb_usrfHasObjects);
    }

    public void removeObject(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(swb_usrfHasObjects,semanticobject);
    }

    public org.semanticwb.platform.SemanticObject getObject()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swb_usrfHasObjects);
         return ret;
    }

    public org.semanticwb.model.UserRepository getUserRepository()
    {
        return new org.semanticwb.model.UserRepository(getSemanticObject().getModel().getModelObject());
    }
}
