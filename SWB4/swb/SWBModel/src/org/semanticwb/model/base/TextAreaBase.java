package org.semanticwb.model.base;


public abstract class TextAreaBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_textAreaRows=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#textAreaRows");
    public static final org.semanticwb.platform.SemanticProperty swbxf_textAreaCols=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#textAreaCols");
    public static final org.semanticwb.platform.SemanticClass swbxf_TextArea=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TextArea");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TextArea");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.TextArea> listTextAreas(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TextArea>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.TextArea> listTextAreas()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TextArea>(it, true);
        }

        public static org.semanticwb.model.TextArea getTextArea(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TextArea)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.TextArea createTextArea(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TextArea)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeTextArea(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasTextArea(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTextArea(id, model)!=null);
        }
    }

    public TextAreaBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getRows()
    {
        return getSemanticObject().getIntProperty(swbxf_textAreaRows);
    }

    public void setRows(int value)
    {
        getSemanticObject().setIntProperty(swbxf_textAreaRows, value);
    }

    public int getCols()
    {
        return getSemanticObject().getIntProperty(swbxf_textAreaCols);
    }

    public void setCols(int value)
    {
        getSemanticObject().setIntProperty(swbxf_textAreaCols, value);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
