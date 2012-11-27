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
package org.semanticwb.domotic.model.base;


public abstract class DomMacroBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomAction");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasMacroAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasMacroAction");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomMacro=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomMacro");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomMacro");

    public static class ClassMgr
    {
       /**
       * Returns a list of DomMacro for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.DomMacro
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomMacro> listDomMacros(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomMacro>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.DomMacro for all models
       * @return Iterator of org.semanticwb.domotic.model.DomMacro
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomMacro> listDomMacros()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomMacro>(it, true);
        }

        public static org.semanticwb.domotic.model.DomMacro createDomMacro(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.DomMacro.ClassMgr.createDomMacro(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.DomMacro
       * @param id Identifier for org.semanticwb.domotic.model.DomMacro
       * @param model Model of the org.semanticwb.domotic.model.DomMacro
       * @return A org.semanticwb.domotic.model.DomMacro
       */
        public static org.semanticwb.domotic.model.DomMacro getDomMacro(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomMacro)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.DomMacro
       * @param id Identifier for org.semanticwb.domotic.model.DomMacro
       * @param model Model of the org.semanticwb.domotic.model.DomMacro
       * @return A org.semanticwb.domotic.model.DomMacro
       */
        public static org.semanticwb.domotic.model.DomMacro createDomMacro(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomMacro)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.DomMacro
       * @param id Identifier for org.semanticwb.domotic.model.DomMacro
       * @param model Model of the org.semanticwb.domotic.model.DomMacro
       */
        public static void removeDomMacro(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.DomMacro
       * @param id Identifier for org.semanticwb.domotic.model.DomMacro
       * @param model Model of the org.semanticwb.domotic.model.DomMacro
       * @return true if the org.semanticwb.domotic.model.DomMacro exists, false otherwise
       */

        public static boolean hasDomMacro(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDomMacro(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomMacro with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.domotic.model.DomMacro
       * @return Iterator with all the org.semanticwb.domotic.model.DomMacro
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomMacro> listDomMacroByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomMacro> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomMacro with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.domotic.model.DomMacro
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomMacro> listDomMacroByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomMacro> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomMacro with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.domotic.model.DomMacro
       * @return Iterator with all the org.semanticwb.domotic.model.DomMacro
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomMacro> listDomMacroByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomMacro> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomMacro with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.domotic.model.DomMacro
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomMacro> listDomMacroByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomMacro> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomMacro with a determined MacroAction
       * @param value MacroAction of the type org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.DomMacro
       * @return Iterator with all the org.semanticwb.domotic.model.DomMacro
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomMacro> listDomMacroByMacroAction(org.semanticwb.domotic.model.DomAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomMacro> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasMacroAction, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomMacro with a determined MacroAction
       * @param value MacroAction of the type org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.DomMacro
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomMacro> listDomMacroByMacroAction(org.semanticwb.domotic.model.DomAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomMacro> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasMacroAction,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DomMacroBase.ClassMgr getDomMacroClassMgr()
    {
        return new DomMacroBase.ClassMgr();
    }

   /**
   * Constructs a DomMacroBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DomMacro
   */
    public DomMacroBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
   * Gets all the org.semanticwb.domotic.model.DomAction
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomAction
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction> listMacroActions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction>(getSemanticObject().listObjectProperties(swb4d_hasMacroAction));
    }

   /**
   * Gets true if has a MacroAction
   * @param value org.semanticwb.domotic.model.DomAction to verify
   * @return true if the org.semanticwb.domotic.model.DomAction exists, false otherwise
   */
    public boolean hasMacroAction(org.semanticwb.domotic.model.DomAction value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasMacroAction,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a MacroAction
   * @param value org.semanticwb.domotic.model.DomAction to add
   */

    public void addMacroAction(org.semanticwb.domotic.model.DomAction value)
    {
        getSemanticObject().addObjectProperty(swb4d_hasMacroAction, value.getSemanticObject());
    }
   /**
   * Removes all the MacroAction
   */

    public void removeAllMacroAction()
    {
        getSemanticObject().removeProperty(swb4d_hasMacroAction);
    }
   /**
   * Removes a MacroAction
   * @param value org.semanticwb.domotic.model.DomAction to remove
   */

    public void removeMacroAction(org.semanticwb.domotic.model.DomAction value)
    {
        getSemanticObject().removeObjectProperty(swb4d_hasMacroAction,value.getSemanticObject());
    }

   /**
   * Gets the MacroAction
   * @return a org.semanticwb.domotic.model.DomAction
   */
    public org.semanticwb.domotic.model.DomAction getMacroAction()
    {
         org.semanticwb.domotic.model.DomAction ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasMacroAction);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomAction)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the DomiticSite
   * @return a instance of org.semanticwb.domotic.model.DomiticSite
   */
    public org.semanticwb.domotic.model.DomiticSite getDomiticSite()
    {
        return (org.semanticwb.domotic.model.DomiticSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
