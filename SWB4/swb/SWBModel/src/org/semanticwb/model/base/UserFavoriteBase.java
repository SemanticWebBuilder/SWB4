package org.semanticwb.model.base;


public abstract class UserFavoriteBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass owl_Thing=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2002/07/owl#Thing");
    public static final org.semanticwb.platform.SemanticProperty swb_usrfHasObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrfHasObject");
    public static final org.semanticwb.platform.SemanticClass swb_UserFavorite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorite");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorite");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.UserFavorite> listUserFavorites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFavorite>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.UserFavorite> listUserFavorites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFavorite>(it, true);
        }

        public static org.semanticwb.model.UserFavorite createUserFavorite(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.UserFavorite.ClassMgr.createUserFavorite(String.valueOf(id), model);
        }

        public static org.semanticwb.model.UserFavorite getUserFavorite(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserFavorite)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.UserFavorite createUserFavorite(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserFavorite)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeUserFavorite(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasUserFavorite(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserFavorite(id, model)!=null);
        }
    }

    public UserFavoriteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listObjects()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swb_usrfHasObject.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addObject(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swb_usrfHasObject, value);
    }

    public void removeAllObject()
    {
        getSemanticObject().removeProperty(swb_usrfHasObject);
    }

    public void removeObject(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().removeObjectProperty(swb_usrfHasObject,value);
    }

    public org.semanticwb.platform.SemanticObject getObject()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swb_usrfHasObject);
         return ret;
    }

    public org.semanticwb.model.UserRepository getUserRepository()
    {
        return (org.semanticwb.model.UserRepository)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
