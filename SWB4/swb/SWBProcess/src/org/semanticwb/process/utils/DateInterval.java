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
package org.semanticwb.process.utils;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 *
 * @author Sergio Téllez
 */
public class DateInterval {

    private String dateInit = null;
    private String dateFinal = null;

    public DateInterval(String dateInit, String dateFinal) {
        this.dateInit = dateInit;
        this.dateFinal = dateFinal;
    }

    public void setDateInit(String dateInit) {
        this.dateInit = dateInit;
    }

    public void setDateFinal(String dateFinal) {
        this.dateFinal = dateFinal;
    }

    public String getDateInit() {
        if (null!=dateInit)
            return this.dateInit;
        else
            return "";
    }

    public String getDateFinal() {
        if (null!=dateFinal)
            return this.dateFinal;
        else
            return "";
    }

    public int getYearDateInit() {
        return Integer.parseInt(dateInit.substring(6,10));
    }

    public int getMonthDateInit() {
        return Integer.parseInt(dateInit.substring(3,5));
    }

    public int getDayDateInit() {
        return Integer.parseInt(dateInit.substring(0,2));
    }

    public int getYearDateFinal() {
        return Integer.parseInt(dateFinal.substring(6,10));
    }

    public int getMonthDateFinal() {
        return Integer.parseInt(dateFinal.substring(3,5));
    }

    public int getDayDateFinal() {
        return Integer.parseInt(dateFinal.substring(0,2));
    }

    public boolean equalsToDateInit(Date anotherDate) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(anotherDate);
        if (calendar.get(Calendar.YEAR)==getYearDateInit() && calendar.get(Calendar.MONTH)==(getMonthDateInit()-1) && calendar.get(Calendar.DAY_OF_MONTH)==getDayDateInit())
            return true;
        return false;
    }

    public boolean equalsToDateFinal(Date anotherDate) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(anotherDate);
        if (calendar.get(Calendar.YEAR)==getYearDateFinal() && calendar.get(Calendar.MONTH)==(getMonthDateFinal()-1) && calendar.get(Calendar.DAY_OF_MONTH)==getDayDateFinal())
            return true;
        return false;
    }

    public Date dateInit() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(getYearDateInit(), getMonthDateInit()-1, getDayDateInit());
        return calendar.getTime();
    }

    public Date dateFinal() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(getYearDateFinal(), getMonthDateFinal()-1, getDayDateFinal());
        return calendar.getTime();
    }

    @Override
    public String toString() {
        return getDateInit() + " " + getDateFinal();
    }
}
