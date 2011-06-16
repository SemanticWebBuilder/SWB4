package org.semanticwb.model.base;

public interface ImageUploadableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgThumbnailHeight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgThumbnailHeight");
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgMaxWidth=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgMaxWidth");
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgThumbnail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgThumbnail");
   /**
   * lista de medidas de los thumbnails ejemplo: 100x50|200x100|300x150 
   */
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgThumbnailList=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgThumbnailList");
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgCrop=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgCrop");
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgMaxHeight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgMaxHeight");
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgThumbnailWidth=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgThumbnailWidth");
    public static final org.semanticwb.platform.SemanticClass swb_ImageUploadable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ImageUploadable");

    public int getImgThumbnailHeight();

    public void setImgThumbnailHeight(int value);

    public int getImgMaxWidth();

    public void setImgMaxWidth(int value);

    public boolean isImgThumbnail();

    public void setImgThumbnail(boolean value);

    public String getImgThumbnailList();

    public void setImgThumbnailList(String value);

    public boolean isImgCrop();

    public void setImgCrop(boolean value);

    public int getImgMaxHeight();

    public void setImgMaxHeight(int value);

    public int getImgThumbnailWidth();

    public void setImgThumbnailWidth(int value);
}
