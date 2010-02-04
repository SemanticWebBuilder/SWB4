package org.semanticwb.process.model.base;


public abstract class GraphicalElementBase extends org.semanticwb.process.model.BPMNElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_GraphicalElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GraphicalElement");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GraphicalElement");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElements(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElements()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(it, true);
       }

       public static org.semanticwb.process.model.GraphicalElement getGraphicalElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.GraphicalElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.GraphicalElement createGraphicalElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.GraphicalElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeGraphicalElement(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasGraphicalElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (getGraphicalElement(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public GraphicalElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
