/**  
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
 **/
package org.semanticwb.model.base;


   // TODO: Auto-generated Javadoc
/**
    * Referencia a un objeto de tipo Calendar.
    */
public abstract class CalendarRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
   /**
   * Objeto de calendarización que permite configurar una página o un recurso para desplegarse en cierta fecha, entre un rango de fechas o incluso en periodos de tiempo definidos.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");
    
    /** The Constant swb_calendar. */
    public static final org.semanticwb.platform.SemanticProperty swb_calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#calendar");
   
   /** Referencia a un objeto de tipo Calendar. */
    public static final org.semanticwb.platform.SemanticClass swb_CalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRef");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRef");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of CalendarRef for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.CalendarRef
        */

        public static java.util.Iterator<org.semanticwb.model.CalendarRef> listCalendarRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.CalendarRef for all models
       * @return Iterator of org.semanticwb.model.CalendarRef
       */

        public static java.util.Iterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(it, true);
        }

        /**
         * Creates the calendar ref.
         * 
         * @param model the model
         * @return the org.semanticwb.model. calendar ref
         */
        public static org.semanticwb.model.CalendarRef createCalendarRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.CalendarRef.ClassMgr.createCalendarRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.CalendarRef
       * @param id Identifier for org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.model.CalendarRef
       * @return A org.semanticwb.model.CalendarRef
       */
        public static org.semanticwb.model.CalendarRef getCalendarRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.CalendarRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.CalendarRef
       * @param id Identifier for org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.model.CalendarRef
       * @return A org.semanticwb.model.CalendarRef
       */
        public static org.semanticwb.model.CalendarRef createCalendarRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.CalendarRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.CalendarRef
       * @param id Identifier for org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.model.CalendarRef
       */
        public static void removeCalendarRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.CalendarRef
       * @param id Identifier for org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.model.CalendarRef
       * @return true if the org.semanticwb.model.CalendarRef exists, false otherwise
       */

        public static boolean hasCalendarRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCalendarRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.CalendarRef with a determined Calendar
       * @param value Calendar of the type org.semanticwb.model.Calendar
       * @param model Model of the org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.model.CalendarRef
       */

        public static java.util.Iterator<org.semanticwb.model.CalendarRef> listCalendarRefByCalendar(org.semanticwb.model.Calendar value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_calendar, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.CalendarRef with a determined Calendar
       * @param value Calendar of the type org.semanticwb.model.Calendar
       * @return Iterator with all the org.semanticwb.model.CalendarRef
       */

        public static java.util.Iterator<org.semanticwb.model.CalendarRef> listCalendarRefByCalendar(org.semanticwb.model.Calendar value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_calendar,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a CalendarRefBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the CalendarRef
    */
    public CalendarRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   
   /**
    * Sets the value for the property Calendar.
    * 
    * @param value Calendar to set
    */

    public void setCalendar(org.semanticwb.model.Calendar value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_calendar, value.getSemanticObject());
        }else
        {
            removeCalendar();
        }
    }
   
   /**
    * Remove the value for Calendar property.
    */

    public void removeCalendar()
    {
        getSemanticObject().removeProperty(swb_calendar);
    }

   /**
    * Gets the Calendar.
    * 
    * @return a org.semanticwb.model.Calendar
    */
    public org.semanticwb.model.Calendar getCalendar()
    {
         org.semanticwb.model.Calendar ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_calendar);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Calendar)obj.createGenericInstance();
         }
         return ret;
    }

   /**
    * Gets the WebSite.
    * 
    * @return a instance of org.semanticwb.model.WebSite
    */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
