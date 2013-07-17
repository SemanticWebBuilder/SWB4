package com.infotec.pic.swb.base;


public abstract class CPBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,com.infotec.pic.swb.Sepomex,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_Colonia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Colonia");
    public static final org.semanticwb.platform.SemanticProperty pic_hasColoniaCP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasColoniaCP");
    public static final org.semanticwb.platform.SemanticClass pic_Municipio=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Municipio");
    public static final org.semanticwb.platform.SemanticProperty pic_municipioCPInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#municipioCPInv");
    public static final org.semanticwb.platform.SemanticClass pic_CP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#CP");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#CP");

    public static class ClassMgr
    {
       /**
       * Returns a list of CP for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.CP
       */

        public static java.util.Iterator<com.infotec.pic.swb.CP> listCPs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CP>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.CP for all models
       * @return Iterator of com.infotec.pic.swb.CP
       */

        public static java.util.Iterator<com.infotec.pic.swb.CP> listCPs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CP>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.CP
       * @param id Identifier for com.infotec.pic.swb.CP
       * @param model Model of the com.infotec.pic.swb.CP
       * @return A com.infotec.pic.swb.CP
       */
        public static com.infotec.pic.swb.CP getCP(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.CP)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.CP
       * @param id Identifier for com.infotec.pic.swb.CP
       * @param model Model of the com.infotec.pic.swb.CP
       * @return A com.infotec.pic.swb.CP
       */
        public static com.infotec.pic.swb.CP createCP(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.CP)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.CP
       * @param id Identifier for com.infotec.pic.swb.CP
       * @param model Model of the com.infotec.pic.swb.CP
       */
        public static void removeCP(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.CP
       * @param id Identifier for com.infotec.pic.swb.CP
       * @param model Model of the com.infotec.pic.swb.CP
       * @return true if the com.infotec.pic.swb.CP exists, false otherwise
       */

        public static boolean hasCP(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCP(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.CP with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.CP
       * @return Iterator with all the com.infotec.pic.swb.CP
       */

        public static java.util.Iterator<com.infotec.pic.swb.CP> listCPByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CP> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.CP with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.CP
       */

        public static java.util.Iterator<com.infotec.pic.swb.CP> listCPByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CP> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.CP with a determined Colonia
       * @param value Colonia of the type com.infotec.pic.swb.Colonia
       * @param model Model of the com.infotec.pic.swb.CP
       * @return Iterator with all the com.infotec.pic.swb.CP
       */

        public static java.util.Iterator<com.infotec.pic.swb.CP> listCPByColonia(com.infotec.pic.swb.Colonia value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CP> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasColoniaCP, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.CP with a determined Colonia
       * @param value Colonia of the type com.infotec.pic.swb.Colonia
       * @return Iterator with all the com.infotec.pic.swb.CP
       */

        public static java.util.Iterator<com.infotec.pic.swb.CP> listCPByColonia(com.infotec.pic.swb.Colonia value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CP> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasColoniaCP,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.CP with a determined MunicipioCPInv
       * @param value MunicipioCPInv of the type com.infotec.pic.swb.Municipio
       * @param model Model of the com.infotec.pic.swb.CP
       * @return Iterator with all the com.infotec.pic.swb.CP
       */

        public static java.util.Iterator<com.infotec.pic.swb.CP> listCPByMunicipioCPInv(com.infotec.pic.swb.Municipio value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CP> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_municipioCPInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.CP with a determined MunicipioCPInv
       * @param value MunicipioCPInv of the type com.infotec.pic.swb.Municipio
       * @return Iterator with all the com.infotec.pic.swb.CP
       */

        public static java.util.Iterator<com.infotec.pic.swb.CP> listCPByMunicipioCPInv(com.infotec.pic.swb.Municipio value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CP> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_municipioCPInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.CP with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.CP
       * @return Iterator with all the com.infotec.pic.swb.CP
       */

        public static java.util.Iterator<com.infotec.pic.swb.CP> listCPByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CP> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.CP with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.CP
       */

        public static java.util.Iterator<com.infotec.pic.swb.CP> listCPByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CP> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CPBase.ClassMgr getCPClassMgr()
    {
        return new CPBase.ClassMgr();
    }

   /**
   * Constructs a CPBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CP
   */
    public CPBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the com.infotec.pic.swb.Colonia
   * @return A GenericIterator with all the com.infotec.pic.swb.Colonia
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia> listColonias()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Colonia>(getSemanticObject().listObjectProperties(pic_hasColoniaCP));
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
           ret=getSemanticObject().hasObjectProperty(pic_hasColoniaCP,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Colonia
   * @param value com.infotec.pic.swb.Colonia to add
   */

    public void addColonia(com.infotec.pic.swb.Colonia value)
    {
        getSemanticObject().addObjectProperty(pic_hasColoniaCP, value.getSemanticObject());
    }
   /**
   * Removes all the Colonia
   */

    public void removeAllColonia()
    {
        getSemanticObject().removeProperty(pic_hasColoniaCP);
    }
   /**
   * Removes a Colonia
   * @param value com.infotec.pic.swb.Colonia to remove
   */

    public void removeColonia(com.infotec.pic.swb.Colonia value)
    {
        getSemanticObject().removeObjectProperty(pic_hasColoniaCP,value.getSemanticObject());
    }

   /**
   * Gets the Colonia
   * @return a com.infotec.pic.swb.Colonia
   */
    public com.infotec.pic.swb.Colonia getColonia()
    {
         com.infotec.pic.swb.Colonia ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasColoniaCP);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Colonia)obj.createGenericInstance();
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
   * Sets the value for the property MunicipioCPInv
   * @param value MunicipioCPInv to set
   */

    public void setMunicipioCPInv(com.infotec.pic.swb.Municipio value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_municipioCPInv, value.getSemanticObject());
        }else
        {
            removeMunicipioCPInv();
        }
    }
   /**
   * Remove the value for MunicipioCPInv property
   */

    public void removeMunicipioCPInv()
    {
        getSemanticObject().removeProperty(pic_municipioCPInv);
    }

   /**
   * Gets the MunicipioCPInv
   * @return a com.infotec.pic.swb.Municipio
   */
    public com.infotec.pic.swb.Municipio getMunicipioCPInv()
    {
         com.infotec.pic.swb.Municipio ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_municipioCPInv);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Municipio)obj.createGenericInstance();
         }
         return ret;
    }
}
