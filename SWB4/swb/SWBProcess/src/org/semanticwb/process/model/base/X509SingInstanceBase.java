/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.model.base;


public abstract class X509SingInstanceBase extends org.semanticwb.process.model.BaseElement implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swp_signedString=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#signedString");
    public static final org.semanticwb.platform.SemanticProperty swp_originalString=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#originalString");
    public static final org.semanticwb.platform.SemanticClass swp_X509Certificate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#X509Certificate");
    public static final org.semanticwb.platform.SemanticProperty swp_X509CertificateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#X509CertificateRef");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNodeInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNodeInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_X509FlowNodeInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#X509FlowNodeInstance");
    public static final org.semanticwb.platform.SemanticClass swp_X509SingInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#X509SingInstance");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#X509SingInstance");

    public static class ClassMgr
    {
       /**
       * Returns a list of X509SingInstance for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.X509SingInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509SingInstance> listX509SingInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509SingInstance>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.X509SingInstance for all models
       * @return Iterator of org.semanticwb.process.model.X509SingInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509SingInstance> listX509SingInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509SingInstance>(it, true);
        }

        public static org.semanticwb.process.model.X509SingInstance createX509SingInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.X509SingInstance.ClassMgr.createX509SingInstance(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.X509SingInstance
       * @param id Identifier for org.semanticwb.process.model.X509SingInstance
       * @param model Model of the org.semanticwb.process.model.X509SingInstance
       * @return A org.semanticwb.process.model.X509SingInstance
       */
        public static org.semanticwb.process.model.X509SingInstance getX509SingInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.X509SingInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.X509SingInstance
       * @param id Identifier for org.semanticwb.process.model.X509SingInstance
       * @param model Model of the org.semanticwb.process.model.X509SingInstance
       * @return A org.semanticwb.process.model.X509SingInstance
       */
        public static org.semanticwb.process.model.X509SingInstance createX509SingInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.X509SingInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.X509SingInstance
       * @param id Identifier for org.semanticwb.process.model.X509SingInstance
       * @param model Model of the org.semanticwb.process.model.X509SingInstance
       */
        public static void removeX509SingInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.X509SingInstance
       * @param id Identifier for org.semanticwb.process.model.X509SingInstance
       * @param model Model of the org.semanticwb.process.model.X509SingInstance
       * @return true if the org.semanticwb.process.model.X509SingInstance exists, false otherwise
       */

        public static boolean hasX509SingInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getX509SingInstance(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.X509SingInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.X509SingInstance
       * @return Iterator with all the org.semanticwb.process.model.X509SingInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509SingInstance> listX509SingInstanceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509SingInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.X509SingInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.X509SingInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509SingInstance> listX509SingInstanceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509SingInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.X509SingInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.X509SingInstance
       * @return Iterator with all the org.semanticwb.process.model.X509SingInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509SingInstance> listX509SingInstanceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509SingInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.X509SingInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.X509SingInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509SingInstance> listX509SingInstanceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509SingInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.X509SingInstance with a determined Certificate
       * @param value Certificate of the type org.semanticwb.process.model.X509Certificate
       * @param model Model of the org.semanticwb.process.model.X509SingInstance
       * @return Iterator with all the org.semanticwb.process.model.X509SingInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509SingInstance> listX509SingInstanceByCertificate(org.semanticwb.process.model.X509Certificate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509SingInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_X509CertificateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.X509SingInstance with a determined Certificate
       * @param value Certificate of the type org.semanticwb.process.model.X509Certificate
       * @return Iterator with all the org.semanticwb.process.model.X509SingInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509SingInstance> listX509SingInstanceByCertificate(org.semanticwb.process.model.X509Certificate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509SingInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_X509CertificateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.X509SingInstance with a determined FlowNodeInstance
       * @param value FlowNodeInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.X509SingInstance
       * @return Iterator with all the org.semanticwb.process.model.X509SingInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509SingInstance> listX509SingInstanceByFlowNodeInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509SingInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_X509FlowNodeInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.X509SingInstance with a determined FlowNodeInstance
       * @param value FlowNodeInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.X509SingInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509SingInstance> listX509SingInstanceByFlowNodeInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509SingInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_X509FlowNodeInstance,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static X509SingInstanceBase.ClassMgr getX509SingInstanceClassMgr()
    {
        return new X509SingInstanceBase.ClassMgr();
    }

   /**
   * Constructs a X509SingInstanceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the X509SingInstance
   */
    public X509SingInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the SignedString property
* @return String with the SignedString
*/
    public String getSignedString()
    {
        return getSemanticObject().getProperty(swp_signedString);
    }

/**
* Sets the SignedString property
* @param value long with the SignedString
*/
    public void setSignedString(String value)
    {
        getSemanticObject().setProperty(swp_signedString, value);
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
* Gets the OriginalString property
* @return String with the OriginalString
*/
    public String getOriginalString()
    {
        return getSemanticObject().getProperty(swp_originalString);
    }

/**
* Sets the OriginalString property
* @param value long with the OriginalString
*/
    public void setOriginalString(String value)
    {
        getSemanticObject().setProperty(swp_originalString, value);
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
   * Sets the value for the property Certificate
   * @param value Certificate to set
   */

    public void setCertificate(org.semanticwb.process.model.X509Certificate value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_X509CertificateRef, value.getSemanticObject());
        }else
        {
            removeCertificate();
        }
    }
   /**
   * Remove the value for Certificate property
   */

    public void removeCertificate()
    {
        getSemanticObject().removeProperty(swp_X509CertificateRef);
    }

   /**
   * Gets the Certificate
   * @return a org.semanticwb.process.model.X509Certificate
   */
    public org.semanticwb.process.model.X509Certificate getCertificate()
    {
         org.semanticwb.process.model.X509Certificate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_X509CertificateRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.X509Certificate)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property FlowNodeInstance
   * @param value FlowNodeInstance to set
   */

    public void setFlowNodeInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_X509FlowNodeInstance, value.getSemanticObject());
        }else
        {
            removeFlowNodeInstance();
        }
    }
   /**
   * Remove the value for FlowNodeInstance property
   */

    public void removeFlowNodeInstance()
    {
        getSemanticObject().removeProperty(swp_X509FlowNodeInstance);
    }

   /**
   * Gets the FlowNodeInstance
   * @return a org.semanticwb.process.model.FlowNodeInstance
   */
    public org.semanticwb.process.model.FlowNodeInstance getFlowNodeInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_X509FlowNodeInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
    }
}
