package com.infotec.pic.swb.base;


public abstract class MunicipioBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,com.infotec.pic.swb.Sepomex,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_CP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#CP");
    public static final org.semanticwb.platform.SemanticProperty pic_hasCP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasCP");
    public static final org.semanticwb.platform.SemanticClass pic_EntidadFederativa=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#EntidadFederativa");
    public static final org.semanticwb.platform.SemanticProperty pic_entidadInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#entidadInv");
    public static final org.semanticwb.platform.SemanticClass pic_Colonia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Colonia");
    public static final org.semanticwb.platform.SemanticProperty pic_hasColonia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasColonia");
    public static final org.semanticwb.platform.SemanticClass pic_Municipio=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Municipio");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Municipio");

    public static class ClassMgr
    {
       /**
       * Returns a list of Municipio for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Municipio
       */

        public static java.util.Iterator<com.infotec.pic.swb.Municipio> listMunicipios(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Municipio for all models
       * @return Iterator of com.infotec.pic.swb.Municipio
       */

        public static java.util.Iterator<com.infotec.pic.swb.Municipio> listMunicipios()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.Municipio
       * @param id Identifier for com.infotec.pic.swb.Municipio
       * @param model Model of the com.infotec.pic.swb.Municipio
       * @return A com.infotec.pic.swb.Municipio
       */
        public static com.infotec.pic.swb.Municipio getMunicipio(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Municipio)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Municipio
       * @param id Identifier for com.infotec.pic.swb.Municipio
       * @param model Model of the com.infotec.pic.swb.Municipio
       * @return A com.infotec.pic.swb.Municipio
       */
        public static com.infotec.pic.swb.Municipio createMunicipio(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Municipio)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Municipio
       * @param id Identifier for com.infotec.pic.swb.Municipio
       * @param model Model of the com.infotec.pic.swb.Municipio
       */
        public static void removeMunicipio(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Municipio
       * @param id Identifier for com.infotec.pic.swb.Municipio
       * @param model Model of the com.infotec.pic.swb.Municipio
       * @return true if the com.infotec.pic.swb.Municipio exists, false otherwise
       */

        public static boolean hasMunicipio(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMunicipio(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Municipio with a determined CP
       * @param value CP of the type com.infotec.pic.swb.CP
       * @param model Model of the com.infotec.pic.swb.Municipio
       * @return Iterator with all the com.infotec.pic.swb.Municipio
       */

        public static java.util.Iterator<com.infotec.pic.swb.Municipio> listMunicipioByCP(com.infotec.pic.swb.CP value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasCP, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Municipio with a determined CP
       * @param value CP of the type com.infotec.pic.swb.CP
       * @return Iterator with all the com.infotec.pic.swb.Municipio
       */

        public static java.util.Iterator<com.infotec.pic.swb.Municipio> listMunicipioByCP(com.infotec.pic.swb.CP value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasCP,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Municipio with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Municipio
       * @return Iterator with all the com.infotec.pic.swb.Municipio
       */

        public static java.util.Iterator<com.infotec.pic.swb.Municipio> listMunicipioByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Municipio with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Municipio
       */

        public static java.util.Iterator<com.infotec.pic.swb.Municipio> listMunicipioByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Municipio with a determined EntidadInv
       * @param value EntidadInv of the type com.infotec.pic.swb.EntidadFederativa
       * @param model Model of the com.infotec.pic.swb.Municipio
       * @return Iterator with all the com.infotec.pic.swb.Municipio
       */

        public static java.util.Iterator<com.infotec.pic.swb.Municipio> listMunicipioByEntidadInv(com.infotec.pic.swb.EntidadFederativa value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_entidadInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Municipio with a determined EntidadInv
       * @param value EntidadInv of the type com.infotec.pic.swb.EntidadFederativa
       * @return Iterator with all the com.infotec.pic.swb.Municipio
       */

        public static java.util.Iterator<com.infotec.pic.swb.Municipio> listMunicipioByEntidadInv(com.infotec.pic.swb.EntidadFederativa value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_entidadInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Municipio with a determined Colonia
       * @param value Colonia of the type com.infotec.pic.swb.Colonia
       * @param model Model of the com.infotec.pic.swb.Municipio
       * @return Iterator with all the com.infotec.pic.swb.Municipio
       */

        public static java.util.Iterator<com.infotec.pic.swb.Municipio> listMunicipioByColonia(com.infotec.pic.swb.Colonia value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasColonia, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Municipio with a determined Colonia
       * @param value Colonia of the type com.infotec.pic.swb.Colonia
       * @return Iterator with all the com.infotec.pic.swb.Municipio
       */

        public static java.util.Iterator<com.infotec.pic.swb.Municipio> listMunicipioByColonia(com.infotec.pic.swb.Colonia value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasColonia,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Municipio with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Municipio
       * @return Iterator with all the com.infotec.pic.swb.Municipio
       */

        public static java.util.Iterator<com.infotec.pic.swb.Municipio> listMunicipioByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Municipio with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Municipio
       */

        public static java.util.Iterator<com.infotec.pic.swb.Municipio> listMunicipioByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Municipio> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MunicipioBase.ClassMgr getMunicipioClassMgr()
    {
        return new MunicipioBase.ClassMgr();
    }

   /**
   * Constructs a MunicipioBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Municipio
   */
    public MunicipioBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the com.infotec.pic.swb.CP
   * @return A GenericIterator with all the com.infotec.pic.swb.CP
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CP> listCPs()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CP>(getSemanticObject().listObjectProperties(pic_hasCP));
    }

   /**
   * Gets true if has a CP
   * @param value com.infotec.pic.swb.CP to verify
   * @return true if the com.infotec.pic.swb.CP exists, false otherwise
   */
    public boolean hasCP(com.infotec.pic.swb.CP value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasCP,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a CP
   * @param value com.infotec.pic.swb.CP to add
   */

    public void addCP(com.infotec.pic.swb.CP value)
    {
        getSemanticObject().addObjectProperty(pic_hasCP, value.getSemanticObject());
    }
   /**
   * Removes all the CP
   */

    public void removeAllCP()
    {
        getSemanticObject().removeProperty(pic_hasCP);
    }
   /**
   * Removes a CP
   * @param value com.infotec.pic.swb.CP to remove
   */

    public void removeCP(com.infotec.pic.swb.CP value)
    {
        getSemanticObject().removeObjectProperty(pic_hasCP,value.getSemanticObject());
    }

   /**
   * Gets the CP
   * @return a com.infotec.pic.swb.CP
   */
    public com.infotec.pic.swb.CP getCP()
    {
         com.infotec.pic.swb.CP ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasCP);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.CP)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property EntidadInv
   * @param value EntidadInv to set
   */

    public void setEntidadInv(com.infotec.pic.swb.EntidadFederativa value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_entidadInv, value.getSemanticObject());
        }else
        {
            removeEntidadInv();
        }
    }
   /**
   * Remove the value for EntidadInv property
   */

    public void removeEntidadInv()
    {
        getSemanticObject().removeProperty(pic_entidadInv);
    }

   /**
   * Gets the EntidadInv
   * @return a com.infotec.pic.swb.EntidadFederativa
   */
    public com.infotec.pic.swb.EntidadFederativa getEntidadInv()
    {
         com.infotec.pic.swb.EntidadFederativa ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_entidadInv);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.EntidadFederativa)obj.createGenericInstance();
         }
         return ret;
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
   * Gets all the com.infotec.pic.swb.Colonia
   * @return A GenericIterator with all the com.infotec.pic.swb.Colonia
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia> listColonias()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia>(getSemanticObject().listObjectProperties(pic_hasColonia));
    }

   /**
   * Gets true if has a Colonia
   * @param value com.infotec.pic.swb.Colonia to verify
   * @return true if the com.infotec.pic.swb.Colonia exists, false otherwise
   */
    public boolean hasColonia(com.infotec.pic.swb.Colonia value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasColonia,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Colonia
   * @param value com.infotec.pic.swb.Colonia to add
   */

    public void addColonia(com.infotec.pic.swb.Colonia value)
    {
        getSemanticObject().addObjectProperty(pic_hasColonia, value.getSemanticObject());
    }
   /**
   * Removes all the Colonia
   */

    public void removeAllColonia()
    {
        getSemanticObject().removeProperty(pic_hasColonia);
    }
   /**
   * Removes a Colonia
   * @param value com.infotec.pic.swb.Colonia to remove
   */

    public void removeColonia(com.infotec.pic.swb.Colonia value)
    {
        getSemanticObject().removeObjectProperty(pic_hasColonia,value.getSemanticObject());
    }

   /**
   * Gets the Colonia
   * @return a com.infotec.pic.swb.Colonia
   */
    public com.infotec.pic.swb.Colonia getColonia()
    {
         com.infotec.pic.swb.Colonia ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasColonia);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Colonia)obj.createGenericInstance();
         }
         return ret;
    }
}
