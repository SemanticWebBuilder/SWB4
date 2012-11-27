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
package org.semanticwb.model;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBClass.
 */
public class SWBClass extends org.semanticwb.model.base.SWBClassBase {
    
    /**
     * Instantiates a new sWB class.
     * 
     * @param base the base
     */
    public SWBClass(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.SWBClassBase#isValid()
     */
    public boolean isValid() {

        // System.out.println("isValid:"+getURI()+" "+getClass().getName());
        boolean ret = true;

        if (this instanceof Activeable && !((Activeable) this).isActive()) {
            ret = false;
        }

        if (ret && (this instanceof Trashable) && ((Trashable) this).isDeleted())
        {
            ret = false;
        }

        if (ret && (this instanceof Viewable)) {
            long val = ((Viewable) this).getViews();
            long max = ((Viewable) this).getMaxViews();

            // System.out.println("views:"+max+" "+val);
            if ((max > 0) && (val >= max)) {
                ret = false;
            }
        }

        if (ret && (this instanceof Hitable)) {
            long val = ((Hitable) this).getHits();
            long max = ((Hitable) this).getMaxHits();

            // System.out.println("hits:"+max+" "+val);
            if ((max > 0) && (val >= max)) {
                ret = false;
            }
        }

        if (ret && (this instanceof Expirable))
        {
            java.util.Date date = ((Expirable) this).getExpiration();
            if ((date != null) && (System.currentTimeMillis() > date.getTime()))
            {
                ret = false;
            }
        }

        if (ret && (this instanceof CalendarRefable))
        {
            Boolean temp=null;
            Iterator<CalendarRef> it = ((CalendarRefable) this).listCalendarRefs();
            while (it.hasNext())
            {
                CalendarRef calref = it.next();
                if (calref.isActive())
                {
                    Calendar cal = calref.getCalendar();
                    if ((cal != null) && cal.isActive())
                    {
                        if(cal.isOnSchedule())
                        {
                            temp = true;
                            break;
                        }else
                        {
                            temp = false;
                        }
                    }
                }
            }
            if(temp!=null)ret=temp;
        }

/*
        if(ret && this instanceof Calendarable)
        {
            Iterator<Calendar> it=((Calendarable)this).listCalendars();
            while(it.hasNext())
            {
                Calendar cal=it.next();
                if(cal.isActive() && !cal.isOnSchedule())
                {
                    ret=false;
                    break;
                }
            }
        }
 */
        return ret;
    }
}
