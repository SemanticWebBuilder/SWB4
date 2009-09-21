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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;

/**
 * Wrapper to event properties. Uset in the events manager resource.
 * Wrapper para las propiedades de un evento. Usada por el recurso administrador
 * de eventos.
 * 
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class EventElement extends org.semanticwb.portal.community.base.EventElementBase 
{
    public EventElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets an iterator tho the user's events carried out in the given date.
     * Obtiene un iterador a los eventos del usuario a llevarse a cabo en la
     * fecha dada.
     * @param user User to get events for. Propietario de los eventos.
     * @param date Date of the event. Fecha de los eventos.
     * @param wpage WebPage of the events manager resource. WebPage del recurso
     * administrador de eventos.
     * @param model Model to use. Modelo a usar.
     * @return Iterator to the user's event list. Iterador a la lista de eventos
     * del usuario.
     */
    public static Iterator<EventElement> listEventElementsByDate(User user, Date date, WebPage wpage, SWBModel model) {
        Iterator<EventElement> evs;        
        if (user == null) {
             evs = listEventElementByEventWebPage(wpage, model);
        } else {
            evs = listEventElementByAttendant(user, model);
        }
        ArrayList<EventElement> res = new ArrayList<EventElement>();
        
        while(evs.hasNext()) {
            EventElement ev = evs.next();
            //System.out.println("----" + ev.getTitle() + " : " + ev.getStartDate());
            //If event starts at, is carried out, or ends in date, add it to the list
            if(isEqual(ev.getStartDate(), date) || isEqual(ev.getEndDate(), date)) {
                res.add(ev);
            } else if (ev.getStartDate().before(date) && !ev.getEndDate().before(date)) {
                res.add(ev);
            }
        }
        return res.iterator();        
    }    

    /**
     * Chacks wheter date1 and date2 are equal.
     * @param date1
     * @param date2
     * @return true if year, month and day of date1 are equal to year, month
     * and day of date2.
     */
    public static boolean isEqual(Date date1, Date date2) {
        return (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate());
    }

    @Override
    public String getURL()
    {
        String url = "#";
        url=this.getEventWebPage().getUrl();
        url+="?act=detail&uri="+URLEncoder.encode(this.getURI());
        return url;
    }
    
    @Override
    public WebPage getWebPage()
    {
        return super.getEventWebPage();
    }
}
