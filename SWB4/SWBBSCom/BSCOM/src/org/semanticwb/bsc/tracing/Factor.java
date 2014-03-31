package org.semanticwb.bsc.tracing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que define un factor de riesgo.
 */
public class Factor extends org.semanticwb.bsc.tracing.base.FactorBase {

    public Factor(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Obtiene el prefijo en base al prefijo del Riesgo padre. La nomenclatura en la
     * que se basa se muestra a continuaci&oacute;n:  N&uacute;mero consecutivo del Riesgo 
     * + "." + n&uacute;mero consecutivo del Factor.
     * @return el objeto String que representa el prefijo para un Factor 
     */
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if (prefix == null) {
            Factor factor = (Factor) getSemanticObject()
                    .createGenericInstance();
            Risk risk = factor.getFactorInv();
            prefix = risk.getPrefix();
            String tmpPrefix = "";
            if (prefix != null && prefix.lastIndexOf("_") > -1) {
                tmpPrefix = prefix.substring(prefix.lastIndexOf("_") + 1,
                        prefix.length());
            }
            prefix = getConsecutive(tmpPrefix, risk);
            setPrefix(prefix);
        }
        return prefix;
    }

    /**
     * Obtiene el siguiente prefijo consecutivo para un Factor dado un Riesgo.
     * @param prefixRisk el prefijo del Riesgo
     * @param risk el Riesgo que contiene al Factor actual
     * @return el objeto String que representa el siguiente prefijo para un Factor 
     */
    private String getConsecutive(String prefixRisk, Risk risk) {
        String consecutive = "";
        List<String> map = new ArrayList<String>();
        Iterator<Factor> it = risk.listFactors();
        while (it.hasNext()) {
            Factor obj = it.next();
            String prefix = obj.getSemanticObject()
                    .getProperty(bsc_prefix, false);
            if (prefix != null && prefix.lastIndexOf(".") > -1) {
                prefix = prefix.substring(prefix.lastIndexOf(".") + 1,
                        prefix.length());
                map.add(prefix);
            }
        }
        try {
            Collections.sort(map);
            int index = Integer.parseInt(map.get(map.size() - 1).toString());
            index++;
            consecutive = prefixRisk + "." + index;
        } catch (Exception ex) {
            consecutive = prefixRisk + "." + "1";
        }
        return consecutive;
    }
}
