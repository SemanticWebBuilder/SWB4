package org.semanticwb.process.model.base;


public abstract class TimeDateExpressionBase extends org.semanticwb.process.model.Expression implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_TimeDateExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimeDateExpression");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimeDateExpression");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.TimeDateExpression> listTimeDateExpressions(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimeDateExpression>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.TimeDateExpression> listTimeDateExpressions()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimeDateExpression>(it, true);
       }

       public static org.semanticwb.process.model.TimeDateExpression createTimeDateExpression(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.TimeDateExpression.ClassMgr.createTimeDateExpression(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.TimeDateExpression getTimeDateExpression(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TimeDateExpression)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.TimeDateExpression createTimeDateExpression(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.TimeDateExpression)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeTimeDateExpression(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasTimeDateExpression(String id, org.semanticwb.model.SWBModel model)
       {
           return (getTimeDateExpression(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.TimeDateExpression> listTimeDateExpressionByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimeDateExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.TimeDateExpression> listTimeDateExpressionByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimeDateExpression> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public TimeDateExpressionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
