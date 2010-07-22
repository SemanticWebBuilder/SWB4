package org.semanticwb.model.base;


public abstract class CodeEditorBase extends org.semanticwb.model.TextArea 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_codeEditorLanguage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#codeEditorLanguage");
    public static final org.semanticwb.platform.SemanticClass swbxf_CodeEditor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#CodeEditor");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#CodeEditor");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.CodeEditor> listCodeEditors(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CodeEditor>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.CodeEditor> listCodeEditors()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CodeEditor>(it, true);
        }

        public static org.semanticwb.model.CodeEditor getCodeEditor(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.CodeEditor)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.CodeEditor createCodeEditor(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.CodeEditor)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeCodeEditor(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCodeEditor(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCodeEditor(id, model)!=null);
        }
    }

    public CodeEditorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getLanguage()
    {
        return getSemanticObject().getProperty(swbxf_codeEditorLanguage);
    }

    public void setLanguage(String value)
    {
        getSemanticObject().setProperty(swbxf_codeEditorLanguage, value);
    }
}
