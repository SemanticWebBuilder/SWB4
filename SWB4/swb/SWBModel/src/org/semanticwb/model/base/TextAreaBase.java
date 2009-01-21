package org.semanticwb.model.base;


public class TextAreaBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_textAreaRows=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#textAreaRows");
    public static final org.semanticwb.platform.SemanticProperty swbxf_textAreaCols=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#textAreaCols");
    public static final org.semanticwb.platform.SemanticClass swbxf_TextArea=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TextArea");


    public static org.semanticwb.model.TextArea createTextArea(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.TextArea)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, swbxf_TextArea), swbxf_TextArea);
    }

    public TextAreaBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getRows()
    {
        return getSemanticObject().getIntProperty(swbxf_textAreaRows);
    }

    public void setRows(int textAreaRows)
    {
        getSemanticObject().setLongProperty(swbxf_textAreaRows, textAreaRows);
    }

    public int getCols()
    {
        return getSemanticObject().getIntProperty(swbxf_textAreaCols);
    }

    public void setCols(int textAreaCols)
    {
        getSemanticObject().setLongProperty(swbxf_textAreaCols, textAreaCols);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator((org.semanticwb.platform.SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }
}
