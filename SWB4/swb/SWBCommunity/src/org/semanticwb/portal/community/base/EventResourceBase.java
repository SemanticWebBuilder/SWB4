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
package org.semanticwb.portal.community.base;


public abstract class EventResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_EventElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#EventElement");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasEvent");
    public static final org.semanticwb.platform.SemanticClass swbcomm_EventResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#EventResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#EventResource");

    public EventResourceBase()
    {
    }

    public EventResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement> listEvents()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement>(getSemanticObject().listObjectProperties(swbcomm_hasEvent));
    }

    public boolean hasEvent(org.semanticwb.portal.community.EventElement eventelement)
    {
        boolean ret=false;
        if(eventelement!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasEvent,eventelement.getSemanticObject());
        }
        return ret;
    }

    public void addEvent(org.semanticwb.portal.community.EventElement value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasEvent, value.getSemanticObject());
    }

    public void removeAllEvent()
    {
        getSemanticObject().removeProperty(swbcomm_hasEvent);
    }

    public void removeEvent(org.semanticwb.portal.community.EventElement eventelement)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasEvent,eventelement.getSemanticObject());
    }

    public org.semanticwb.portal.community.EventElement getEvent()
    {
         org.semanticwb.portal.community.EventElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasEvent);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.EventElement)obj.createGenericInstance();
         }
         return ret;
    }
}
