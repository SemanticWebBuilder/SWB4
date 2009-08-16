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
package org.semanticwb.portal.community;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceURL;


public class EventElement extends org.semanticwb.portal.community.base.EventElementBase 
{
    public EventElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static Iterator<EventElement> listEventElementsByDate(Date date) {
        ArrayList<EventElement> res = new ArrayList<EventElement>();
        Iterator<EventElement> eit = listEventElements();
        while (eit.hasNext()) {
            EventElement ev = eit.next();
            //System.out.println(">>>>Evento " + ev.getTitle() + " - " + ev.getStartDate());
            //If event starts at, is carried out, or ends in date, add it to the list
            if (ev.getStartDate().compareTo(date) == 0 || ev.getEndDate().compareTo(date) == 0) {
                System.out.println("Agregando " + ev.getTitle());
                res.add(ev);
            }
        }
        return res.iterator();
    }


}
