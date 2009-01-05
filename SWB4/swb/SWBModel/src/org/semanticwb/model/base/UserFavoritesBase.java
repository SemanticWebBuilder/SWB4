package org.semanticwb.model.base;


public class UserFavoritesBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass owl_Thing=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2002/07/owl#Thing");
    public static final org.semanticwb.platform.SemanticProperty swb_usrfHasObjects=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrfHasObjects");
    public static final org.semanticwb.platform.SemanticClass swb_UserFavorites=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorites");

    public UserFavoritesBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
