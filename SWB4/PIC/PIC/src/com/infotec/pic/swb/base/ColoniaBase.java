package com.infotec.pic.swb.base;


public abstract class ColoniaBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,com.infotec.pic.swb.Sepomex,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_Municipio=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Municipio");
    public static final org.semanticwb.platform.SemanticProperty pic_municipioColoniasInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#municipioColoniasInv");
    public static final org.semanticwb.platform.SemanticClass pic_CP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#CP");
    public static final org.semanticwb.platform.SemanticProperty pic_cpInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#cpInv");
    public static final org.semanticwb.platform.SemanticClass pic_Colonia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Colonia");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Colonia");

    public static class ClassMgr
    {
       /**
       * Returns a list of Colonia for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Colonia
       */

        public static java.util.Iterator<com.infotec.pic.swb.Colonia> listColonias(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Colonia for all models
       * @return Iterator of com.infotec.pic.swb.Colonia
       */

        public static java.util.Iterator<com.infotec.pic.swb.Colonia> listColonias()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.Colonia
       * @param id Identifier for com.infotec.pic.swb.Colonia
       * @param model Model of the com.infotec.pic.swb.Colonia
       * @return A com.infotec.pic.swb.Colonia
       */
        public static com.infotec.pic.swb.Colonia getColonia(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Colonia)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Colonia
       * @param id Identifier for com.infotec.pic.swb.Colonia
       * @param model Model of the com.infotec.pic.swb.Colonia
       * @return A com.infotec.pic.swb.Colonia
       */
        public static com.infotec.pic.swb.Colonia createColonia(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Colonia)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Colonia
       * @param id Identifier for com.infotec.pic.swb.Colonia
       * @param model Model of the com.infotec.pic.swb.Colonia
       */
        public static void removeColonia(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Colonia
       * @param id Identifier for com.infotec.pic.swb.Colonia
       * @param model Model of the com.infotec.pic.swb.Colonia
       * @return true if the com.infotec.pic.swb.Colonia exists, false otherwise
       */

        public static boolean hasColonia(String id, org.semanticwb.model.SWBModel model)
        {
            return (getColonia(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Colonia with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Colonia
       * @return Iterator with all the com.infotec.pic.swb.Colonia
       */

        public static java.util.Iterator<com.infotec.pic.swb.Colonia> listColoniaByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Colonia with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Colonia
       */

        public static java.util.Iterator<com.infotec.pic.swb.Colonia> listColoniaByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Colonia with a determined MunicipioColoniasInv
       * @param value MunicipioColoniasInv of the type com.infotec.pic.swb.Municipio
       * @param model Model of the com.infotec.pic.swb.Colonia
       * @return Iterator with all the com.infotec.pic.swb.Colonia
       */

        public static java.util.Iterator<com.infotec.pic.swb.Colonia> listColoniaByMunicipioColoniasInv(com.infotec.pic.swb.Municipio value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_municipioColoniasInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Colonia with a determined MunicipioColoniasInv
       * @param value MunicipioColoniasInv of the type com.infotec.pic.swb.Municipio
       * @return Iterator with all the com.infotec.pic.swb.Colonia
       */

        public static java.util.Iterator<com.infotec.pic.swb.Colonia> listColoniaByMunicipioColoniasInv(com.infotec.pic.swb.Municipio value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_municipioColoniasInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Colonia with a determined CpInv
       * @param value CpInv of the type com.infotec.pic.swb.CP
       * @param model Model of the com.infotec.pic.swb.Colonia
       * @return Iterator with all the com.infotec.pic.swb.Colonia
       */

        public static java.util.Iterator<com.infotec.pic.swb.Colonia> listColoniaByCpInv(com.infotec.pic.swb.CP value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_cpInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Colonia with a determined CpInv
       * @param value CpInv of the type com.infotec.pic.swb.CP
       * @return Iterator with all the com.infotec.pic.swb.Colonia
       */

        public static java.util.Iterator<com.infotec.pic.swb.Colonia> listColoniaByCpInv(com.infotec.pic.swb.CP value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_cpInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Colonia with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Colonia
       * @return Iterator with all the com.infotec.pic.swb.Colonia
       */

        public static java.util.Iterator<com.infotec.pic.swb.Colonia> listColoniaByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Colonia with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Colonia
       */

        public static java.util.Iterator<com.infotec.pic.swb.Colonia> listColoniaByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ColoniaBase.ClassMgr getColoniaClassMgr()
    {
        return new ColoniaBase.ClassMgr();
    }

   /**
   * Constructs a ColoniaBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Colonia
   */
    public ColoniaBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property MunicipioColoniasInv
   * @param value MunicipioColoniasInv to set
   */

    public void setMunicipioColoniasInv(com.infotec.pic.swb.Municipio value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_municipioColoniasInv, value.getSemanticObject());
        }else
        {
            removeMunicipioColoniasInv();
        }
    }
   /**
   * Remove the value for MunicipioColoniasInv property
   */

    public void removeMunicipioColoniasInv()
    {
        getSemanticObject().removeProperty(pic_municipioColoniasInv);
    }

   /**
   * Gets the MunicipioColoniasInv
   * @return a com.infotec.pic.swb.Municipio
   */
    public com.infotec.pic.swb.Municipio getMunicipioColoniasInv()
    {
         com.infotec.pic.swb.Municipio ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_municipioColoniasInv);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Municipio)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property CpInv
   * @param value CpInv to set
   */

    public void setCpInv(com.infotec.pic.swb.CP value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_cpInv, value.getSemanticObject());
        }else
        {
            removeCpInv();
        }
    }
   /**
   * Remove the value for CpInv property
   */

    public void removeCpInv()
    {
        getSemanticObject().removeProperty(pic_cpInv);
    }

   /**
   * Gets the CpInv
   * @return a com.infotec.pic.swb.CP
   */
    public com.infotec.pic.swb.CP getCpInv()
    {
         com.infotec.pic.swb.CP ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_cpInv);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.CP)obj.createGenericInstance();
         }
         return ret;
    }
}
