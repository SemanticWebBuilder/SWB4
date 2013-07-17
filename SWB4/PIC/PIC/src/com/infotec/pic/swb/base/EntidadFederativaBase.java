package com.infotec.pic.swb.base;


public abstract class EntidadFederativaBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,com.infotec.pic.swb.Sepomex,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_Municipio=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Municipio");
    public static final org.semanticwb.platform.SemanticProperty pic_hasMunicipio=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasMunicipio");
    public static final org.semanticwb.platform.SemanticClass pic_EntidadFederativa=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#EntidadFederativa");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#EntidadFederativa");

    public static class ClassMgr
    {
       /**
       * Returns a list of EntidadFederativa for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.EntidadFederativa
       */

        public static java.util.Iterator<com.infotec.pic.swb.EntidadFederativa> listEntidadFederativas(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.EntidadFederativa>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.EntidadFederativa for all models
       * @return Iterator of com.infotec.pic.swb.EntidadFederativa
       */

        public static java.util.Iterator<com.infotec.pic.swb.EntidadFederativa> listEntidadFederativas()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.EntidadFederativa>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.EntidadFederativa
       * @param id Identifier for com.infotec.pic.swb.EntidadFederativa
       * @param model Model of the com.infotec.pic.swb.EntidadFederativa
       * @return A com.infotec.pic.swb.EntidadFederativa
       */
        public static com.infotec.pic.swb.EntidadFederativa getEntidadFederativa(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.EntidadFederativa)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.EntidadFederativa
       * @param id Identifier for com.infotec.pic.swb.EntidadFederativa
       * @param model Model of the com.infotec.pic.swb.EntidadFederativa
       * @return A com.infotec.pic.swb.EntidadFederativa
       */
        public static com.infotec.pic.swb.EntidadFederativa createEntidadFederativa(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.EntidadFederativa)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.EntidadFederativa
       * @param id Identifier for com.infotec.pic.swb.EntidadFederativa
       * @param model Model of the com.infotec.pic.swb.EntidadFederativa
       */
        public static void removeEntidadFederativa(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.EntidadFederativa
       * @param id Identifier for com.infotec.pic.swb.EntidadFederativa
       * @param model Model of the com.infotec.pic.swb.EntidadFederativa
       * @return true if the com.infotec.pic.swb.EntidadFederativa exists, false otherwise
       */

        public static boolean hasEntidadFederativa(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEntidadFederativa(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.EntidadFederativa with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.EntidadFederativa
       * @return Iterator with all the com.infotec.pic.swb.EntidadFederativa
       */

        public static java.util.Iterator<com.infotec.pic.swb.EntidadFederativa> listEntidadFederativaByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.EntidadFederativa> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.EntidadFederativa with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.EntidadFederativa
       */

        public static java.util.Iterator<com.infotec.pic.swb.EntidadFederativa> listEntidadFederativaByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.EntidadFederativa> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.EntidadFederativa with a determined Municipio
       * @param value Municipio of the type com.infotec.pic.swb.Municipio
       * @param model Model of the com.infotec.pic.swb.EntidadFederativa
       * @return Iterator with all the com.infotec.pic.swb.EntidadFederativa
       */

        public static java.util.Iterator<com.infotec.pic.swb.EntidadFederativa> listEntidadFederativaByMunicipio(com.infotec.pic.swb.Municipio value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.EntidadFederativa> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasMunicipio, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.EntidadFederativa with a determined Municipio
       * @param value Municipio of the type com.infotec.pic.swb.Municipio
       * @return Iterator with all the com.infotec.pic.swb.EntidadFederativa
       */

        public static java.util.Iterator<com.infotec.pic.swb.EntidadFederativa> listEntidadFederativaByMunicipio(com.infotec.pic.swb.Municipio value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.EntidadFederativa> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasMunicipio,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.EntidadFederativa with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.EntidadFederativa
       * @return Iterator with all the com.infotec.pic.swb.EntidadFederativa
       */

        public static java.util.Iterator<com.infotec.pic.swb.EntidadFederativa> listEntidadFederativaByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.EntidadFederativa> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.EntidadFederativa with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.EntidadFederativa
       */

        public static java.util.Iterator<com.infotec.pic.swb.EntidadFederativa> listEntidadFederativaByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.EntidadFederativa> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static EntidadFederativaBase.ClassMgr getEntidadFederativaClassMgr()
    {
        return new EntidadFederativaBase.ClassMgr();
    }

   /**
   * Constructs a EntidadFederativaBase with a SemanticObject
   * @param base The SemanticObject with the properties for the EntidadFederativa
   */
    public EntidadFederativaBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Clave property
* @return String with the Clave
*/
    public String getClave()
    {
        return getSemanticObject().getProperty(pic_clave);
    }

/**
* Sets the Clave property
* @param value long with the Clave
*/
    public void setClave(String value)
    {
        getSemanticObject().setProperty(pic_clave, value);
    }
   /**
   * Gets all the com.infotec.pic.swb.Municipio
   * @return A GenericIterator with all the com.infotec.pic.swb.Municipio
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio> listMunicipios()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio>(getSemanticObject().listObjectProperties(pic_hasMunicipio));
    }

   /**
   * Gets true if has a Municipio
   * @param value com.infotec.pic.swb.Municipio to verify
   * @return true if the com.infotec.pic.swb.Municipio exists, false otherwise
   */
    public boolean hasMunicipio(com.infotec.pic.swb.Municipio value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasMunicipio,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Municipio
   * @param value com.infotec.pic.swb.Municipio to add
   */

    public void addMunicipio(com.infotec.pic.swb.Municipio value)
    {
        getSemanticObject().addObjectProperty(pic_hasMunicipio, value.getSemanticObject());
    }
   /**
   * Removes all the Municipio
   */

    public void removeAllMunicipio()
    {
        getSemanticObject().removeProperty(pic_hasMunicipio);
    }
   /**
   * Removes a Municipio
   * @param value com.infotec.pic.swb.Municipio to remove
   */

    public void removeMunicipio(com.infotec.pic.swb.Municipio value)
    {
        getSemanticObject().removeObjectProperty(pic_hasMunicipio,value.getSemanticObject());
    }

   /**
   * Gets the Municipio
   * @return a com.infotec.pic.swb.Municipio
   */
    public com.infotec.pic.swb.Municipio getMunicipio()
    {
         com.infotec.pic.swb.Municipio ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasMunicipio);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Municipio)obj.createGenericInstance();
         }
         return ret;
    }
}
