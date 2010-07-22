package org.semanticwb.model.base;


public abstract class FlashImageUploadBase extends org.semanticwb.model.FlashFileUpload 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgThumbnailWidth=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgThumbnailWidth");
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgMaxHeight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgMaxHeight");
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgMaxWidth=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgMaxWidth");
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgCrop=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgCrop");
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgThumbnail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgThumbnail");
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgThumbnailHeight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgThumbnailHeight");
    public static final org.semanticwb.platform.SemanticClass swb_FlashImageUpload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#FlashImageUpload");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#FlashImageUpload");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.FlashImageUpload> listFlashImageUploads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FlashImageUpload>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.FlashImageUpload> listFlashImageUploads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FlashImageUpload>(it, true);
        }

        public static org.semanticwb.model.FlashImageUpload getFlashImageUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FlashImageUpload)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.FlashImageUpload createFlashImageUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FlashImageUpload)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeFlashImageUpload(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasFlashImageUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFlashImageUpload(id, model)!=null);
        }
    }

    public FlashImageUploadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getImgThumbnailWidth()
    {
        return getSemanticObject().getIntProperty(swbxf_imgThumbnailWidth);
    }

    public void setImgThumbnailWidth(int value)
    {
        getSemanticObject().setIntProperty(swbxf_imgThumbnailWidth, value);
    }

    public int getImgMaxHeight()
    {
        return getSemanticObject().getIntProperty(swbxf_imgMaxHeight);
    }

    public void setImgMaxHeight(int value)
    {
        getSemanticObject().setIntProperty(swbxf_imgMaxHeight, value);
    }

    public int getImgMaxWidth()
    {
        return getSemanticObject().getIntProperty(swbxf_imgMaxWidth);
    }

    public void setImgMaxWidth(int value)
    {
        getSemanticObject().setIntProperty(swbxf_imgMaxWidth, value);
    }

    public boolean isImgCrop()
    {
        return getSemanticObject().getBooleanProperty(swbxf_imgCrop);
    }

    public void setImgCrop(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_imgCrop, value);
    }

    public boolean isImgThumbnail()
    {
        return getSemanticObject().getBooleanProperty(swbxf_imgThumbnail);
    }

    public void setImgThumbnail(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_imgThumbnail, value);
    }

    public int getImgThumbnailHeight()
    {
        return getSemanticObject().getIntProperty(swbxf_imgThumbnailHeight);
    }

    public void setImgThumbnailHeight(int value)
    {
        getSemanticObject().setIntProperty(swbxf_imgThumbnailHeight, value);
    }
}
