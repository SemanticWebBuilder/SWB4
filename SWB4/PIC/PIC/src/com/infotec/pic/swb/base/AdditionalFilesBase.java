package com.infotec.pic.swb.base;

public interface AdditionalFilesBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty pic_hasFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasFile");
    public static final org.semanticwb.platform.SemanticClass pic_AdditionalFiles=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#AdditionalFiles");

    public java.util.Iterator<String> listFiles();

    public void addFile(String value);
    public void removeAllFile();
    public void removeFile(String value);
}
