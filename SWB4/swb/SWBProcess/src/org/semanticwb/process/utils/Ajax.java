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

    public static String specialChars(String label) {
        return label.replaceAll("á", "&acute;").replaceAll("é", "&eacute;").replaceAll("í", "&iacute;").replaceAll("ó", "&oacute;").replaceAll("ú", "&uacute;").replaceAll("ñ", "&ntilde;");
    }

    public static Object notNull(Object parameter) {
        if (null!=parameter) return parameter; else return "";
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
}
