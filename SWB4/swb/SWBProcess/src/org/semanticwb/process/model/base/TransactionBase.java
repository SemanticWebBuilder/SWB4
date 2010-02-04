package org.semanticwb.process.model.base;


public abstract class TransactionBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticProperty swp_transactionMethod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#transactionMethod");
       public static final org.semanticwb.platform.SemanticProperty swp_transactionProtocol=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#transactionProtocol");
       public static final org.semanticwb.platform.SemanticClass swp_Transaction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Transaction");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Transaction");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Transaction> listTransactions(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Transaction>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Transaction> listTransactions()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Transaction>(it, true);
       }

       public static org.semanticwb.process.model.Transaction getTransaction(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Transaction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Transaction createTransaction(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Transaction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeTransaction(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasTransaction(String id, org.semanticwb.model.SWBModel model)
       {
           return (getTransaction(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Transaction> listTransactionByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Transaction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Transaction> listTransactionByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Transaction> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public TransactionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getTransactionMethod()
    {
        return getSemanticObject().getProperty(swp_transactionMethod);
    }

    public void setTransactionMethod(String value)
    {
        getSemanticObject().setProperty(swp_transactionMethod, value);
    }

    public String getTransactionProtocol()
    {
        return getSemanticObject().getProperty(swp_transactionProtocol);
    }

    public void setTransactionProtocol(String value)
    {
        getSemanticObject().setProperty(swp_transactionProtocol, value);
    }
}
