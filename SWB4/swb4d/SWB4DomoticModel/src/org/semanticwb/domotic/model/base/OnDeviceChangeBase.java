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


public abstract class OnDeviceChangeBase extends org.semanticwb.domotic.model.DomEvent implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swb4d_deviceStat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#deviceStat");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDevice=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDevice");
    public static final org.semanticwb.platform.SemanticProperty swb4d_domDevice4Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#domDevice4Event");
    public static final org.semanticwb.platform.SemanticClass swb4d_OnDeviceChange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnDeviceChange");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnDeviceChange");

    public static class ClassMgr
    {
       /**
       * Returns a list of OnDeviceChange for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChanges(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.OnDeviceChange for all models
       * @return Iterator of org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChanges()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange>(it, true);
        }

        public static org.semanticwb.domotic.model.OnDeviceChange createOnDeviceChange(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.OnDeviceChange.ClassMgr.createOnDeviceChange(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.OnDeviceChange
       * @param id Identifier for org.semanticwb.domotic.model.OnDeviceChange
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       * @return A org.semanticwb.domotic.model.OnDeviceChange
       */
        public static org.semanticwb.domotic.model.OnDeviceChange getOnDeviceChange(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnDeviceChange)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.OnDeviceChange
       * @param id Identifier for org.semanticwb.domotic.model.OnDeviceChange
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       * @return A org.semanticwb.domotic.model.OnDeviceChange
       */
        public static org.semanticwb.domotic.model.OnDeviceChange createOnDeviceChange(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnDeviceChange)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.OnDeviceChange
       * @param id Identifier for org.semanticwb.domotic.model.OnDeviceChange
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       */
        public static void removeOnDeviceChange(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.OnDeviceChange
       * @param id Identifier for org.semanticwb.domotic.model.OnDeviceChange
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       * @return true if the org.semanticwb.domotic.model.OnDeviceChange exists, false otherwise
       */

        public static boolean hasOnDeviceChange(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOnDeviceChange(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnDeviceChange with a determined DomDevice
       * @param value DomDevice of the type org.semanticwb.domotic.model.DomDevice
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       * @return Iterator with all the org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChangeByDomDevice(org.semanticwb.domotic.model.DomDevice value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domDevice4Event, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnDeviceChange with a determined DomDevice
       * @param value DomDevice of the type org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChangeByDomDevice(org.semanticwb.domotic.model.DomDevice value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domDevice4Event,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnDeviceChange with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       * @return Iterator with all the org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChangeByDomAction(org.semanticwb.domotic.model.DomAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomAction, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnDeviceChange with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChangeByDomAction(org.semanticwb.domotic.model.DomAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomAction,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnDeviceChange with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       * @return Iterator with all the org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChangeByDomContext(org.semanticwb.domotic.model.DomContext value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnDeviceChange with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChangeByDomContext(org.semanticwb.domotic.model.DomContext value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static OnDeviceChangeBase.ClassMgr getOnDeviceChangeClassMgr()
    {
        return new OnDeviceChangeBase.ClassMgr();
    }

   /**
   * Constructs a OnDeviceChangeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the OnDeviceChange
   */
    public OnDeviceChangeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the DeviceStat property
* @return String with the DeviceStat
*/
    public String getDeviceStat()
    {
        return getSemanticObject().getProperty(swb4d_deviceStat);
    }

/**
* Sets the DeviceStat property
* @param value long with the DeviceStat
*/
    public void setDeviceStat(String value)
    {
        getSemanticObject().setProperty(swb4d_deviceStat, value);
    }
   /**
   * Sets the value for the property DomDevice
   * @param value DomDevice to set
   */

    public void setDomDevice(org.semanticwb.domotic.model.DomDevice value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_domDevice4Event, value.getSemanticObject());
        }else
        {
            removeDomDevice();
        }
    }
   /**
   * Remove the value for DomDevice property
   */

    public void removeDomDevice()
    {
        getSemanticObject().removeProperty(swb4d_domDevice4Event);
    }

   /**
   * Gets the DomDevice
   * @return a org.semanticwb.domotic.model.DomDevice
   */
    public org.semanticwb.domotic.model.DomDevice getDomDevice()
    {
         org.semanticwb.domotic.model.DomDevice ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_domDevice4Event);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomDevice)obj.createGenericInstance();
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
