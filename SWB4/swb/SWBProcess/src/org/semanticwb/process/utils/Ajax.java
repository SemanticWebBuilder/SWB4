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
import java.util.Iterator;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

import org.semanticwb.process.model.ProcessInstance;

/**
 *
 * @author Sergio Téllez
 */
public class Ajax {

    public static Object notNull(Object parameter) {
        if (null!=parameter) return parameter; else return "";
    }

    public static Object notNull(Object parameter, Object omission) {
        if (null!=parameter) return parameter; else return omission;
    }

    public static String notNull(String parameter) {
        if (null!=parameter) return parameter; else return "";
    }

    public static String notNull(Date parameter) {
        if (null!=parameter) return parameter.toString(); else return "";
    }

    public static String getModifiedBy(ProcessInstance pinst) {
        if (null != pinst.getModifiedBy())
            return pinst.getModifiedBy().getFullName();
        else if (null != pinst.getCreator())
            return pinst.getCreator().getFullName();
        else
            return "";
    }

    public static java.util.HashMap getParameters(java.util.Map map) {
        java.util.HashMap attributes = new java.util.HashMap();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            java.util.Map.Entry n = (java.util.Map.Entry)iter.next();
            attributes.put(n.getKey().toString(), ((String[])n.getValue())[0].toString());
        }
        return attributes;
    }

    public static String statusLabel(int status, SWBParamRequest paramRequest) throws SWBResourceException {
        String statusLabel = null;
        switch(status) {
            case 0: statusLabel = paramRequest.getLocaleString("STATUS_INIT"); break;
            case 1: statusLabel = paramRequest.getLocaleString("STATUS_PROCESSING"); break;
            case 2: statusLabel = paramRequest.getLocaleString("STATUS_STOPED"); break;
            case 3: statusLabel = paramRequest.getLocaleString("STATUS_ABORTED"); break;
            case 4: statusLabel = paramRequest.getLocaleString("STATUS_CLOSED"); break;
            case 5: statusLabel = paramRequest.getLocaleString("STATUS_OPEN"); break;
            default: statusLabel = "";
        }
        return statusLabel;
    }

    public static String operatorLabel(int operator, SWBParamRequest paramRequest) throws SWBResourceException {
        String operatorLabel = null;
        switch(operator) {
            case 0: operatorLabel = "="; break;
            case 1: operatorLabel = ">"; break;
            case 2: operatorLabel = "<"; break;
            case 3: operatorLabel = ">="; break;
            case 4: operatorLabel = "<="; break;
            case 5: operatorLabel = paramRequest.getLocaleString("EQUALS_TO"); break;
            case 6: operatorLabel = paramRequest.getLocaleString("EQUALS_IGNORE_CASE"); break;
            case 7: operatorLabel = paramRequest.getLocaleString("STARTS_WITH"); break;
            case 8: operatorLabel = paramRequest.getLocaleString("ENDS_WITH"); break;
            case 9: operatorLabel = paramRequest.getLocaleString("LIKES"); break;
            default: operatorLabel = "";
        }
        return operatorLabel;
    }

    public static String getChartScript() {
        StringBuilder script = new StringBuilder();
        script.append("<script src=\"/swbadmin/jsp/process/charts/coregraph.js\" type=\"text/javascript\"></script>\n");
        //script.append("<script src=\"/swbadmin/jsp/charts/raphael-min.js\" type=\"text/javascript\"></script>\n");
        //script.append("<script src=\"/swbadmin/jsp/charts/grafico.base.js\" type=\"text/javascript\"></script>\n");
        //script.append("<script src=\"/swbadmin/jsp/charts/grafico.line.js\" type=\"text/javascript\"></script>\n");
        //script.append("<script src=\"/swbadmin/jsp/charts/grafico.bar.js\" type=\"text/javascript\"></script>\n");
        //script.append("<script src=\"/swbadmin/jsp/charts/grafico.spark.js\" type=\"text/javascript\"></script>\n");
        return script.toString();
    }
}
