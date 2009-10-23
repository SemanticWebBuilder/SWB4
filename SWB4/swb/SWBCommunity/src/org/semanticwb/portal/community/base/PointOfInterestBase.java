package org.semanticwb.portal.community.base;


public class PointOfInterestBase extends org.semanticwb.portal.community.DirectoryObject implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirWebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDirTopicWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDirTopicWebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryResource");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_directoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#directoryResource");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirPhoto");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDirProfileWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDirProfileWebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirHasExtraPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirHasExtraPhoto");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbcomm_PointOfInterest=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PointOfInterest");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PointOfInterest");

       public static java.util.Iterator<org.semanticwb.portal.community.PointOfInterest> listPointOfInterests(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PointOfInterest>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.PointOfInterest> listPointOfInterests()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PointOfInterest>(it, true);
       }

       public static org.semanticwb.portal.community.PointOfInterest getPointOfInterest(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.PointOfInterest)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.community.PointOfInterest createPointOfInterest(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.PointOfInterest)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removePointOfInterest(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasPointOfInterest(String id, org.semanticwb.model.SWBModel model)
       {
           return (getPointOfInterest(id, model)!=null);
       }
    }

    public PointOfInterestBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
