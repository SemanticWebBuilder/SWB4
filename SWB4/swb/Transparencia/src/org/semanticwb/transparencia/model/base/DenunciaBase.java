package org.semanticwb.transparencia.model.base;


public abstract class DenunciaBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass trans_Dependencia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Dependencia");
    public static final org.semanticwb.platform.SemanticProperty trans_dependencia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.transparencia.org/ontology#dependencia");
    public static final org.semanticwb.platform.SemanticProperty trans_emailDenunciante=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.transparencia.org/ontology#emailDenunciante");
    public static final org.semanticwb.platform.SemanticProperty trans_denunciante=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.transparencia.org/ontology#denunciante");
    public static final org.semanticwb.platform.SemanticProperty trans_curp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.transparencia.org/ontology#curp");
    public static final org.semanticwb.platform.SemanticProperty trans_estatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.transparencia.org/ontology#estatus");
    public static final org.semanticwb.platform.SemanticProperty trans_activa=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.transparencia.org/ontology#activa");
    public static final org.semanticwb.platform.SemanticProperty trans_tipoDenuncia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.transparencia.org/ontology#tipoDenuncia");
    public static final org.semanticwb.platform.SemanticProperty trans_otroTipoDenuncia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.transparencia.org/ontology#otroTipoDenuncia");
    public static final org.semanticwb.platform.SemanticProperty trans_denuncia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.transparencia.org/ontology#denuncia");
    public static final org.semanticwb.platform.SemanticProperty trans_personaDenunciar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.transparencia.org/ontology#personaDenunciar");
    public static final org.semanticwb.platform.SemanticClass trans_Area=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Area");
    public static final org.semanticwb.platform.SemanticProperty trans_area=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.transparencia.org/ontology#area");
    public static final org.semanticwb.platform.SemanticClass trans_Denuncia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Denuncia");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Denuncia");

    public static class ClassMgr
    {
       /**
       * Returns a list of Denuncia for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.transparencia.model.Denuncia
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Denuncia> listDenuncias(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Denuncia>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.transparencia.model.Denuncia for all models
       * @return Iterator of org.semanticwb.transparencia.model.Denuncia
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Denuncia> listDenuncias()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Denuncia>(it, true);
        }

        public static org.semanticwb.transparencia.model.Denuncia createDenuncia(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.transparencia.model.Denuncia.ClassMgr.createDenuncia(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.transparencia.model.Denuncia
       * @param id Identifier for org.semanticwb.transparencia.model.Denuncia
       * @param model Model of the org.semanticwb.transparencia.model.Denuncia
       * @return A org.semanticwb.transparencia.model.Denuncia
       */
        public static org.semanticwb.transparencia.model.Denuncia getDenuncia(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.Denuncia)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.transparencia.model.Denuncia
       * @param id Identifier for org.semanticwb.transparencia.model.Denuncia
       * @param model Model of the org.semanticwb.transparencia.model.Denuncia
       * @return A org.semanticwb.transparencia.model.Denuncia
       */
        public static org.semanticwb.transparencia.model.Denuncia createDenuncia(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.Denuncia)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.transparencia.model.Denuncia
       * @param id Identifier for org.semanticwb.transparencia.model.Denuncia
       * @param model Model of the org.semanticwb.transparencia.model.Denuncia
       */
        public static void removeDenuncia(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.transparencia.model.Denuncia
       * @param id Identifier for org.semanticwb.transparencia.model.Denuncia
       * @param model Model of the org.semanticwb.transparencia.model.Denuncia
       * @return true if the org.semanticwb.transparencia.model.Denuncia exists, false otherwise
       */

        public static boolean hasDenuncia(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDenuncia(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Denuncia with a determined Dependencia
       * @param value Dependencia of the type org.semanticwb.transparencia.model.Dependencia
       * @param model Model of the org.semanticwb.transparencia.model.Denuncia
       * @return Iterator with all the org.semanticwb.transparencia.model.Denuncia
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Denuncia> listDenunciaByDependencia(org.semanticwb.transparencia.model.Dependencia value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Denuncia> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(trans_dependencia, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Denuncia with a determined Dependencia
       * @param value Dependencia of the type org.semanticwb.transparencia.model.Dependencia
       * @return Iterator with all the org.semanticwb.transparencia.model.Denuncia
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Denuncia> listDenunciaByDependencia(org.semanticwb.transparencia.model.Dependencia value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Denuncia> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(trans_dependencia,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Denuncia with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.transparencia.model.Denuncia
       * @return Iterator with all the org.semanticwb.transparencia.model.Denuncia
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Denuncia> listDenunciaByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Denuncia> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Denuncia with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.transparencia.model.Denuncia
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Denuncia> listDenunciaByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Denuncia> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Denuncia with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.transparencia.model.Denuncia
       * @return Iterator with all the org.semanticwb.transparencia.model.Denuncia
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Denuncia> listDenunciaByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Denuncia> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Denuncia with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.transparencia.model.Denuncia
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Denuncia> listDenunciaByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Denuncia> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Denuncia with a determined Area
       * @param value Area of the type org.semanticwb.transparencia.model.Area
       * @param model Model of the org.semanticwb.transparencia.model.Denuncia
       * @return Iterator with all the org.semanticwb.transparencia.model.Denuncia
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Denuncia> listDenunciaByArea(org.semanticwb.transparencia.model.Area value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Denuncia> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(trans_area, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Denuncia with a determined Area
       * @param value Area of the type org.semanticwb.transparencia.model.Area
       * @return Iterator with all the org.semanticwb.transparencia.model.Denuncia
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Denuncia> listDenunciaByArea(org.semanticwb.transparencia.model.Area value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Denuncia> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(trans_area,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a DenunciaBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Denuncia
   */
    public DenunciaBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Dependencia
   * @param value Dependencia to set
   */

    public void setDependencia(org.semanticwb.transparencia.model.Dependencia value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(trans_dependencia, value.getSemanticObject());
        }else
        {
            removeDependencia();
        }
    }
   /**
   * Remove the value for Dependencia property
   */

    public void removeDependencia()
    {
        getSemanticObject().removeProperty(trans_dependencia);
    }

   /**
   * Gets the Dependencia
   * @return a org.semanticwb.transparencia.model.Dependencia
   */
    public org.semanticwb.transparencia.model.Dependencia getDependencia()
    {
         org.semanticwb.transparencia.model.Dependencia ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(trans_dependencia);
         if(obj!=null)
         {
             ret=(org.semanticwb.transparencia.model.Dependencia)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the EmailDenunciante property
* @return String with the EmailDenunciante
*/
    public String getEmailDenunciante()
    {
        return getSemanticObject().getProperty(trans_emailDenunciante);
    }

/**
* Sets the EmailDenunciante property
* @param value long with the EmailDenunciante
*/
    public void setEmailDenunciante(String value)
    {
        getSemanticObject().setProperty(trans_emailDenunciante, value);
    }

/**
* Gets the Denunciante property
* @return String with the Denunciante
*/
    public String getDenunciante()
    {
        return getSemanticObject().getProperty(trans_denunciante);
    }

/**
* Sets the Denunciante property
* @param value long with the Denunciante
*/
    public void setDenunciante(String value)
    {
        getSemanticObject().setProperty(trans_denunciante, value);
    }

/**
* Gets the Curp property
* @return String with the Curp
*/
    public String getCurp()
    {
        return getSemanticObject().getProperty(trans_curp);
    }

/**
* Sets the Curp property
* @param value long with the Curp
*/
    public void setCurp(String value)
    {
        getSemanticObject().setProperty(trans_curp, value);
    }

/**
* Gets the Estatus property
* @return String with the Estatus
*/
    public String getEstatus()
    {
        return getSemanticObject().getProperty(trans_estatus);
    }

/**
* Sets the Estatus property
* @param value long with the Estatus
*/
    public void setEstatus(String value)
    {
        getSemanticObject().setProperty(trans_estatus, value);
    }

/**
* Gets the Activa property
* @return boolean with the Activa
*/
    public boolean isActiva()
    {
        return getSemanticObject().getBooleanProperty(trans_activa);
    }

/**
* Sets the Activa property
* @param value long with the Activa
*/
    public void setActiva(boolean value)
    {
        getSemanticObject().setBooleanProperty(trans_activa, value);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the TipoDenuncia property
* @return String with the TipoDenuncia
*/
    public String getTipoDenuncia()
    {
        return getSemanticObject().getProperty(trans_tipoDenuncia);
    }

/**
* Sets the TipoDenuncia property
* @param value long with the TipoDenuncia
*/
    public void setTipoDenuncia(String value)
    {
        getSemanticObject().setProperty(trans_tipoDenuncia, value);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the OtroTipoDenuncia property
* @return String with the OtroTipoDenuncia
*/
    public String getOtroTipoDenuncia()
    {
        return getSemanticObject().getProperty(trans_otroTipoDenuncia);
    }

/**
* Sets the OtroTipoDenuncia property
* @param value long with the OtroTipoDenuncia
*/
    public void setOtroTipoDenuncia(String value)
    {
        getSemanticObject().setProperty(trans_otroTipoDenuncia, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Denuncia property
* @return String with the Denuncia
*/
    public String getDenuncia()
    {
        return getSemanticObject().getProperty(trans_denuncia);
    }

/**
* Sets the Denuncia property
* @param value long with the Denuncia
*/
    public void setDenuncia(String value)
    {
        getSemanticObject().setProperty(trans_denuncia, value);
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PersonaDenunciar property
* @return String with the PersonaDenunciar
*/
    public String getPersonaDenunciar()
    {
        return getSemanticObject().getProperty(trans_personaDenunciar);
    }

/**
* Sets the PersonaDenunciar property
* @param value long with the PersonaDenunciar
*/
    public void setPersonaDenunciar(String value)
    {
        getSemanticObject().setProperty(trans_personaDenunciar, value);
    }
   /**
   * Sets the value for the property Area
   * @param value Area to set
   */

    public void setArea(org.semanticwb.transparencia.model.Area value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(trans_area, value.getSemanticObject());
        }else
        {
            removeArea();
        }
    }
   /**
   * Remove the value for Area property
   */

    public void removeArea()
    {
        getSemanticObject().removeProperty(trans_area);
    }

   /**
   * Gets the Area
   * @return a org.semanticwb.transparencia.model.Area
   */
    public org.semanticwb.transparencia.model.Area getArea()
    {
         org.semanticwb.transparencia.model.Area ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(trans_area);
         if(obj!=null)
         {
             ret=(org.semanticwb.transparencia.model.Area)obj.createGenericInstance();
         }
         return ret;
    }
}
