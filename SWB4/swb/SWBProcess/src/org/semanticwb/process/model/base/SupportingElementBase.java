package org.semanticwb.process.model.base;


public abstract class SupportingElementBase extends org.semanticwb.process.model.BPMNElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_SupportingElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SupportingElement");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SupportingElement");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.SupportingElement> listSupportingElements(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SupportingElement>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.SupportingElement> listSupportingElements()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SupportingElement>(it, true);
       }

       public static org.semanticwb.process.model.SupportingElement getSupportingElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.SupportingElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.SupportingElement createSupportingElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.SupportingElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeSupportingElement(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasSupportingElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (getSupportingElement(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.SupportingElement> listSupportingElementByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SupportingElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.SupportingElement> listSupportingElementByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SupportingElement> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public SupportingElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
