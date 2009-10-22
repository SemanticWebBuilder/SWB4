package org.semanticwb.model.base;


public class SelectTreeBase extends org.semanticwb.model.SelectOne 
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swbxf_so_globalScope=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#so_globalScope");
       public static final org.semanticwb.platform.SemanticProperty swbxf_so_nullSuport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#so_nullSuport");
       public static final org.semanticwb.platform.SemanticProperty swbxf_st_startElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#st_startElement");
       public static final org.semanticwb.platform.SemanticClass swbxf_SelectTree=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectTree");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectTree");

       public static java.util.Iterator<org.semanticwb.model.SelectTree> listSelectTrees(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectTree>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.model.SelectTree> listSelectTrees()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectTree>(it, true);
       }

       public static org.semanticwb.model.SelectTree getSelectTree(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.model.SelectTree)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.model.SelectTree createSelectTree(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.model.SelectTree)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeSelectTree(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasSelectTree(String id, org.semanticwb.model.SWBModel model)
       {
           return (getSelectTree(id, model)!=null);
       }
    }

    public SelectTreeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getStartElement()
    {
        return getSemanticObject().getProperty(ClassMgr.swbxf_st_startElement);
    }

    public void setStartElement(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbxf_st_startElement, value);
    }
}
