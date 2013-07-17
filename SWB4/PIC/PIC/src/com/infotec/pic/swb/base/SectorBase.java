package com.infotec.pic.swb.base;


   /**
   * Sector al que pertenece 
   */
public abstract class SectorBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Sector al que pertenece
   */
    public static final org.semanticwb.platform.SemanticClass pic_Sector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Sector");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Sector");

    public static class ClassMgr
    {
       /**
       * Returns a list of Sector for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Sector
       */

        public static java.util.Iterator<com.infotec.pic.swb.Sector> listSectors(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Sector>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Sector for all models
       * @return Iterator of com.infotec.pic.swb.Sector
       */

        public static java.util.Iterator<com.infotec.pic.swb.Sector> listSectors()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Sector>(it, true);
        }

        public static com.infotec.pic.swb.Sector createSector(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.Sector.ClassMgr.createSector(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.Sector
       * @param id Identifier for com.infotec.pic.swb.Sector
       * @param model Model of the com.infotec.pic.swb.Sector
       * @return A com.infotec.pic.swb.Sector
       */
        public static com.infotec.pic.swb.Sector getSector(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Sector)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Sector
       * @param id Identifier for com.infotec.pic.swb.Sector
       * @param model Model of the com.infotec.pic.swb.Sector
       * @return A com.infotec.pic.swb.Sector
       */
        public static com.infotec.pic.swb.Sector createSector(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Sector)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Sector
       * @param id Identifier for com.infotec.pic.swb.Sector
       * @param model Model of the com.infotec.pic.swb.Sector
       */
        public static void removeSector(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Sector
       * @param id Identifier for com.infotec.pic.swb.Sector
       * @param model Model of the com.infotec.pic.swb.Sector
       * @return true if the com.infotec.pic.swb.Sector exists, false otherwise
       */

        public static boolean hasSector(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSector(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Sector with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Sector
       * @return Iterator with all the com.infotec.pic.swb.Sector
       */

        public static java.util.Iterator<com.infotec.pic.swb.Sector> listSectorByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Sector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Sector with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Sector
       */

        public static java.util.Iterator<com.infotec.pic.swb.Sector> listSectorByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Sector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Sector with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Sector
       * @return Iterator with all the com.infotec.pic.swb.Sector
       */

        public static java.util.Iterator<com.infotec.pic.swb.Sector> listSectorByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Sector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Sector with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Sector
       */

        public static java.util.Iterator<com.infotec.pic.swb.Sector> listSectorByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Sector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SectorBase.ClassMgr getSectorClassMgr()
    {
        return new SectorBase.ClassMgr();
    }

   /**
   * Constructs a SectorBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Sector
   */
    public SectorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
