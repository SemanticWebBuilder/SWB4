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
package org.semanticwb.portal.resources.sem.events.base;


// TODO: Auto-generated Javadoc
/**
 * The Class EventsBase.
 */
public abstract class EventsBase extends org.semanticwb.portal.api.GenericSemResource 
{
    
    /** The Constant evnts_Event. */
    public static final org.semanticwb.platform.SemanticClass evnts_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Event");
    
    /** The Constant evnts_hasEvent. */
    public static final org.semanticwb.platform.SemanticProperty evnts_hasEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#hasEvent");
    
    /** The Constant evnts_Events. */
    public static final org.semanticwb.platform.SemanticClass evnts_Events=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Events");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Events");

    /**
     * Instantiates a new events base.
     */
    public EventsBase()
    {
    }

    /**
     * Instantiates a new events base.
     * 
     * @param base the base
     */
    public EventsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * List events.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event> listEvents()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event>(getSemanticObject().listObjectProperties(evnts_hasEvent));
    }

    /**
     * Checks for event.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasEvent(org.semanticwb.portal.resources.sem.events.Event value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(evnts_hasEvent,value.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the event.
     * 
     * @param value the value
     */
    public void addEvent(org.semanticwb.portal.resources.sem.events.Event value)
    {
        getSemanticObject().addObjectProperty(evnts_hasEvent, value.getSemanticObject());
    }

    /**
     * Removes the all event.
     */
    public void removeAllEvent()
    {
        getSemanticObject().removeProperty(evnts_hasEvent);
    }

    /**
     * Removes the event.
     * 
     * @param value the value
     */
    public void removeEvent(org.semanticwb.portal.resources.sem.events.Event value)
    {
        getSemanticObject().removeObjectProperty(evnts_hasEvent,value.getSemanticObject());
    }

    /**
     * Gets the event.
     * 
     * @return the event
     */
    public org.semanticwb.portal.resources.sem.events.Event getEvent()
    {
         org.semanticwb.portal.resources.sem.events.Event ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(evnts_hasEvent);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.events.Event)obj.createGenericInstance();
         }
         return ret;
    }
}
