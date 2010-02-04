package org.semanticwb.process.model.base;


public abstract class AnnotationBase extends org.semanticwb.process.model.Artifact implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticProperty swp_text=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#text");
       public static final org.semanticwb.platform.SemanticClass swp_Annotation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Annotation");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Annotation");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Annotation> listAnnotations(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Annotation>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Annotation> listAnnotations()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Annotation>(it, true);
       }

       public static org.semanticwb.process.model.Annotation createAnnotation(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Annotation.ClassMgr.createAnnotation(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Annotation getAnnotation(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Annotation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Annotation createAnnotation(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Annotation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeAnnotation(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasAnnotation(String id, org.semanticwb.model.SWBModel model)
       {
           return (getAnnotation(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Annotation> listAnnotationByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Annotation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Annotation> listAnnotationByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Annotation> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public AnnotationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getText()
    {
        return getSemanticObject().getProperty(swp_text);
    }

    public void setText(String value)
    {
        getSemanticObject().setProperty(swp_text, value);
    }
}
