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

package org.semanticwb.process.utils;

/**
 *
 * @author Sergio Téllez
 */
public class TimeInterval {

    private int unit;
    private int operator;
    private int time;

    public final static int MILISECOND=0;
    public final static int SECOND=1;
    public final static int MINUTE=2;
    public final static int HOUR=3;
    public final static int DAY=4;
    public final static int MONTH=5;
    public final static int YEAR=6;

    public TimeInterval(int unit, int operator, int time) {
        this.unit = unit;
        this.operator = operator;
        this.time = time;
    }

    public int getUnit() {
        return unit;
    }

    public int getOperator() {
        return operator;
    }

    public int getTime() {
        return time;
    }

    public static boolean match(int unit, int operator, int response, long mstime) {
        long converse = 0;
        boolean match = false;
        switch(unit) {
            case 0: converse = response; break;
            case 1: converse = response*1000; break;
            case 2: converse = response*1000*60; break;
            case 3: converse = response*1000*60*60; break;
            case 4: converse = response*1000*60*60*24; break;
            case 5: converse = response*1000*60*60*24*30; break;
            case 6: converse = response*1000*60*60*24*30*365; break;
            default: converse = 0;
        }
        switch(operator) {
            case 0: if (converse == mstime) match=true; break;
            case 1: if (converse > mstime) match=true; break;
            case 2: if (converse < mstime) match=true; break;
            case 3: if (converse >= mstime) match=true; break;
            case 4: if (converse <= mstime) match=true; break;
        }
        return match;
    }

    @Override
    public String toString() {
        return "[" + getOperator() + " " + getTime() + " " + getUnit() + "]";
    }
}
