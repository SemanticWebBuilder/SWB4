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


public abstract class DomPeriodRefBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomContext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomContext");
    public static final org.semanticwb.platform.SemanticProperty swb4d_domPeriodRefContext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#domPeriodRefContext");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomPeriod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomPeriod");
    public static final org.semanticwb.platform.SemanticProperty swb4d_domPeriod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#domPeriod");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomPeriodRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomPeriodRef");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomPeriodRef");

    public static class ClassMgr
    {
       /**
       * Returns a list of DomPeriodRef for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.DomPeriodRef
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomPeriodRef> listDomPeriodRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPeriodRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.DomPeriodRef for all models
       * @return Iterator of org.semanticwb.domotic.model.DomPeriodRef
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomPeriodRef> listDomPeriodRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPeriodRef>(it, true);
        }

        public static org.semanticwb.domotic.model.DomPeriodRef createDomPeriodRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.DomPeriodRef.ClassMgr.createDomPeriodRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.DomPeriodRef
       * @param id Identifier for org.semanticwb.domotic.model.DomPeriodRef
       * @param model Model of the org.semanticwb.domotic.model.DomPeriodRef
       * @return A org.semanticwb.domotic.model.DomPeriodRef
       */
        public static org.semanticwb.domotic.model.DomPeriodRef getDomPeriodRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomPeriodRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.DomPeriodRef
       * @param id Identifier for org.semanticwb.domotic.model.DomPeriodRef
       * @param model Model of the org.semanticwb.domotic.model.DomPeriodRef
       * @return A org.semanticwb.domotic.model.DomPeriodRef
       */
        public static org.semanticwb.domotic.model.DomPeriodRef createDomPeriodRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomPeriodRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.DomPeriodRef
       * @param id Identifier for org.semanticwb.domotic.model.DomPeriodRef
       * @param model Model of the org.semanticwb.domotic.model.DomPeriodRef
       */
        public static void removeDomPeriodRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.DomPeriodRef
       * @param id Identifier for org.semanticwb.domotic.model.DomPeriodRef
       * @param model Model of the org.semanticwb.domotic.model.DomPeriodRef
       * @return true if the org.semanticwb.domotic.model.DomPeriodRef exists, false otherwise
       */

        public static boolean hasDomPeriodRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDomPeriodRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomPeriodRef with a determined DomPeriodRef
       * @param value DomPeriodRef of the type org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.DomPeriodRef
       * @return Iterator with all the org.semanticwb.domotic.model.DomPeriodRef
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomPeriodRef> listDomPeriodRefByDomPeriodRef(org.semanticwb.domotic.model.DomContext value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPeriodRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domPeriodRefContext, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomPeriodRef with a determined DomPeriodRef
       * @param value DomPeriodRef of the type org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.DomPeriodRef
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomPeriodRef> listDomPeriodRefByDomPeriodRef(org.semanticwb.domotic.model.DomContext value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPeriodRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domPeriodRefContext,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomPeriodRef with a determined DomPeriod
       * @param value DomPeriod of the type org.semanticwb.domotic.model.DomPeriod
       * @param model Model of the org.semanticwb.domotic.model.DomPeriodRef
       * @return Iterator with all the org.semanticwb.domotic.model.DomPeriodRef
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomPeriodRef> listDomPeriodRefByDomPeriod(org.semanticwb.domotic.model.DomPeriod value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPeriodRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domPeriod, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomPeriodRef with a determined DomPeriod
       * @param value DomPeriod of the type org.semanticwb.domotic.model.DomPeriod
       * @return Iterator with all the org.semanticwb.domotic.model.DomPeriodRef
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomPeriodRef> listDomPeriodRefByDomPeriod(org.semanticwb.domotic.model.DomPeriod value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPeriodRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domPeriod,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DomPeriodRefBase.ClassMgr getDomPeriodRefClassMgr()
    {
        return new DomPeriodRefBase.ClassMgr();
    }

   /**
   * Constructs a DomPeriodRefBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DomPeriodRef
   */
    public DomPeriodRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.domotic.model.DomContext
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomContext
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomContext> listDomPeriodRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomContext>(getSemanticObject().listObjectProperties(swb4d_domPeriodRefContext));
    }

   /**
   * Gets true if has a DomPeriodRef
   * @param value org.semanticwb.domotic.model.DomContext to verify
   * @return true if the org.semanticwb.domotic.model.DomContext exists, false otherwise
   */
    public boolean hasDomPeriodRef(org.semanticwb.domotic.model.DomContext value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_domPeriodRefContext,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a DomPeriodRef
   * @param value org.semanticwb.domotic.model.DomContext to add
   */

    public void addDomPeriodRef(org.semanticwb.domotic.model.DomContext value)
    {
        getSemanticObject().addObjectProperty(swb4d_domPeriodRefContext, value.getSemanticObject());
    }
   /**
   * Removes all the DomPeriodRef
   */

    public void removeAllDomPeriodRef()
    {
        getSemanticObject().removeProperty(swb4d_domPeriodRefContext);
    }
   /**
   * Removes a DomPeriodRef
   * @param value org.semanticwb.domotic.model.DomContext to remove
   */

    public void removeDomPeriodRef(org.semanticwb.domotic.model.DomContext value)
    {
        getSemanticObject().removeObjectProperty(swb4d_domPeriodRefContext,value.getSemanticObject());
    }

   /**
   * Gets the DomPeriodRef
   * @return a org.semanticwb.domotic.model.DomContext
   */
    public org.semanticwb.domotic.model.DomContext getDomPeriodRef()
    {
         org.semanticwb.domotic.model.DomContext ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_domPeriodRefContext);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomContext)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property DomPeriod
   * @param value DomPeriod to set
   */

    public void setDomPeriod(org.semanticwb.domotic.model.DomPeriod value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_domPeriod, value.getSemanticObject());
        }else
        {
            removeDomPeriod();
        }
    }
   /**
   * Remove the value for DomPeriod property
   */

    public void removeDomPeriod()
    {
        getSemanticObject().removeProperty(swb4d_domPeriod);
    }

   /**
   * Gets the DomPeriod
   * @return a org.semanticwb.domotic.model.DomPeriod
   */
    public org.semanticwb.domotic.model.DomPeriod getDomPeriod()
    {
         org.semanticwb.domotic.model.DomPeriod ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_domPeriod);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomPeriod)obj.createGenericInstance();
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
