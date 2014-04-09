package org.semanticwb.bsc.element.base;


   /**
   * Define las características de un Acuerdo. 
   */
public abstract class AgreementBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.model.RuleRefable,org.semanticwb.model.Referensable,org.semanticwb.bsc.Help,org.semanticwb.model.FilterableNode,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Roleable,org.semanticwb.model.Filterable,org.semanticwb.model.UserGroupable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable
{
   /**
   * Define el número de un acuerdo, tiene las siguientes características: Título del scorecard+ "-S" + número de sesión a 2 dígitos+ "-" + consecutivo del acuerdo por sesión a 2 dígitos + año actual a 2 dígitos
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_agreementNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#agreementNumber");
   /**
   * Define el responsable del acuerdo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_agreementResponsible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#agreementResponsible");
   /**
   * Fecha compromiso de un acuerdo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_commitmentDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#commitmentDate");
   /**
   * Conjunto de usuarios que podra ver y actualizar información de un acuerdo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_agreementVisibility=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#agreementVisibility");
   /**
   * Persiste información del estatus de un acuerdo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_agreementStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#agreementStatus");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   /**
   * Define un dueño para el acuerdo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_agreementSponsor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#agreementSponsor");
   /**
   * Persiste información de los temas a los que se asocia un acuerdo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_theme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#theme");
    public static final org.semanticwb.platform.SemanticClass bsc_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Comment");
   /**
   * Almacena los comentarios que se hagan hacia un acuerdo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasComment");
   /**
   * Define las características de un Acuerdo.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Agreement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Agreement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Agreement");

    public static class ClassMgr
    {
       /**
       * Returns a list of Agreement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Agreement for all models
       * @return Iterator of org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement>(it, true);
        }

        public static org.semanticwb.bsc.element.Agreement createAgreement(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Agreement.ClassMgr.createAgreement(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Agreement
       * @param id Identifier for org.semanticwb.bsc.element.Agreement
       * @param model Model of the org.semanticwb.bsc.element.Agreement
       * @return A org.semanticwb.bsc.element.Agreement
       */
        public static org.semanticwb.bsc.element.Agreement getAgreement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Agreement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Agreement
       * @param id Identifier for org.semanticwb.bsc.element.Agreement
       * @param model Model of the org.semanticwb.bsc.element.Agreement
       * @return A org.semanticwb.bsc.element.Agreement
       */
        public static org.semanticwb.bsc.element.Agreement createAgreement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Agreement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Agreement
       * @param id Identifier for org.semanticwb.bsc.element.Agreement
       * @param model Model of the org.semanticwb.bsc.element.Agreement
       */
        public static void removeAgreement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Agreement
       * @param id Identifier for org.semanticwb.bsc.element.Agreement
       * @param model Model of the org.semanticwb.bsc.element.Agreement
       * @return true if the org.semanticwb.bsc.element.Agreement exists, false otherwise
       */

        public static boolean hasAgreement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAgreement(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Agreement
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.element.Agreement
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Agreement
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.element.Agreement
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.bsc.element.Agreement
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined AgreementSponsor
       * @param value AgreementSponsor of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Agreement
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByAgreementSponsor(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_agreementSponsor, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined AgreementSponsor
       * @param value AgreementSponsor of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByAgreementSponsor(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_agreementSponsor,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined Comment
       * @param value Comment of the type org.semanticwb.bsc.catalogs.Comment
       * @param model Model of the org.semanticwb.bsc.element.Agreement
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByComment(org.semanticwb.bsc.catalogs.Comment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasComment, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Agreement with a determined Comment
       * @param value Comment of the type org.semanticwb.bsc.catalogs.Comment
       * @return Iterator with all the org.semanticwb.bsc.element.Agreement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Agreement> listAgreementByComment(org.semanticwb.bsc.catalogs.Comment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasComment,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static AgreementBase.ClassMgr getAgreementClassMgr()
    {
        return new AgreementBase.ClassMgr();
    }

   /**
   * Constructs a AgreementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Agreement
   */
    public AgreementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the AgreementNumber property
* @return String with the AgreementNumber
*/
    public String getAgreementNumber()
    {
        return getSemanticObject().getProperty(bsc_agreementNumber);
    }

/**
* Sets the AgreementNumber property
* @param value long with the AgreementNumber
*/
    public void setAgreementNumber(String value)
    {
        getSemanticObject().setProperty(bsc_agreementNumber, value);
    }

/**
* Gets the AgreementResponsible property
* @return String with the AgreementResponsible
*/
    public String getAgreementResponsible()
    {
        return getSemanticObject().getProperty(bsc_agreementResponsible);
    }

/**
* Sets the AgreementResponsible property
* @param value long with the AgreementResponsible
*/
    public void setAgreementResponsible(String value)
    {
        getSemanticObject().setProperty(bsc_agreementResponsible, value);
    }

/**
* Gets the CommitmentDate property
* @return java.util.Date with the CommitmentDate
*/
    public java.util.Date getCommitmentDate()
    {
        return getSemanticObject().getDateProperty(bsc_commitmentDate);
    }

/**
* Sets the CommitmentDate property
* @param value long with the CommitmentDate
*/
    public void setCommitmentDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_commitmentDate, value);
    }

/**
* Gets the AgreementVisibility property
* @return String with the AgreementVisibility
*/
    public String getAgreementVisibility()
    {
        return getSemanticObject().getProperty(bsc_agreementVisibility);
    }

/**
* Sets the AgreementVisibility property
* @param value long with the AgreementVisibility
*/
    public void setAgreementVisibility(String value)
    {
        getSemanticObject().setProperty(bsc_agreementVisibility, value);
    }

/**
* Gets the AgreementStatus property
* @return String with the AgreementStatus
*/
    public String getAgreementStatus()
    {
        return getSemanticObject().getProperty(bsc_agreementStatus);
    }

/**
* Sets the AgreementStatus property
* @param value long with the AgreementStatus
*/
    public void setAgreementStatus(String value)
    {
        getSemanticObject().setProperty(bsc_agreementStatus, value);
    }
   /**
   * Sets the value for the property AgreementSponsor
   * @param value AgreementSponsor to set
   */

    public void setAgreementSponsor(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_agreementSponsor, value.getSemanticObject());
        }else
        {
            removeAgreementSponsor();
        }
    }
   /**
   * Remove the value for AgreementSponsor property
   */

    public void removeAgreementSponsor()
    {
        getSemanticObject().removeProperty(bsc_agreementSponsor);
    }

   /**
   * Gets the AgreementSponsor
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getAgreementSponsor()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_agreementSponsor);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Theme property
* @return String with the Theme
*/
    public String getTheme()
    {
        return getSemanticObject().getProperty(bsc_theme);
    }

/**
* Sets the Theme property
* @param value long with the Theme
*/
    public void setTheme(String value)
    {
        getSemanticObject().setProperty(bsc_theme, value);
    }
   /**
   * Gets all the org.semanticwb.bsc.catalogs.Comment
   * @return A GenericIterator with all the org.semanticwb.bsc.catalogs.Comment
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Comment> listComments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Comment>(getSemanticObject().listObjectProperties(bsc_hasComment));
    }

   /**
   * Gets true if has a Comment
   * @param value org.semanticwb.bsc.catalogs.Comment to verify
   * @return true if the org.semanticwb.bsc.catalogs.Comment exists, false otherwise
   */
    public boolean hasComment(org.semanticwb.bsc.catalogs.Comment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasComment,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Comment
   * @param value org.semanticwb.bsc.catalogs.Comment to add
   */

    public void addComment(org.semanticwb.bsc.catalogs.Comment value)
    {
        getSemanticObject().addObjectProperty(bsc_hasComment, value.getSemanticObject());
    }
   /**
   * Removes all the Comment
   */

    public void removeAllComment()
    {
        getSemanticObject().removeProperty(bsc_hasComment);
    }
   /**
   * Removes a Comment
   * @param value org.semanticwb.bsc.catalogs.Comment to remove
   */

    public void removeComment(org.semanticwb.bsc.catalogs.Comment value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasComment,value.getSemanticObject());
    }

   /**
   * Gets the Comment
   * @return a org.semanticwb.bsc.catalogs.Comment
   */
    public org.semanticwb.bsc.catalogs.Comment getComment()
    {
         org.semanticwb.bsc.catalogs.Comment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.catalogs.Comment)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
