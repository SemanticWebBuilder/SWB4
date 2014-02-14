package org.semanticwb.bsc.base;

   /**
   * Interface que definirá si puede organizarse una área o departamento 
   */
public interface DepartmentOrganizableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty bsc_area=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#area");
   /**
   * Interface que definirá si puede organizarse una área o departamento 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_DepartmentOrganizable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DepartmentOrganizable");

    public String getArea();

    public void setArea(String value);
}
