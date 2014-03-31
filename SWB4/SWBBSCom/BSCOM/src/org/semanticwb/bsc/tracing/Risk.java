package org.semanticwb.bsc.tracing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.platform.SemanticObject;

public class Risk extends org.semanticwb.bsc.tracing.base.RiskBase {

    public Risk(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Obtiene el prefijo en base al a&ntilde;o actual seguido de un n&uacute;mero consecutivo. 
     * @return el objeto String que representa el prefijo para un Riesgo
     */
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if (prefix == null) {
            prefix = getConsecutive();
            setPrefix(prefix);
        }
        return prefix;
    }

    /**
     * Obtiene el siguiente prefijo consecutivo para un Riesgo.
     * @return el objeto String que representa el siguiente prefijo para un Riesgo
     */
    private String getConsecutive() {
        String consecutive = "";
        List<String> map = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        Iterator<SemanticObject> it = getBSC().getSemanticModel()
                .listSubjects(bsc_yearRisk, year + "");
        while (it.hasNext()) {
            SemanticObject obj = it.next();
            Risk risk = (Risk) obj.createGenericInstance();
            String prefix = risk.getSemanticObject()
                    .getProperty(bsc_prefix, false);
            if (prefix != null && prefix.lastIndexOf("_") > -1) {
                prefix = prefix.substring(prefix.lastIndexOf("_") + 1,
                        prefix.length());
                map.add(prefix);
            }
        }
        try {
            Collections.sort(map);
            int index = Integer.parseInt(map.get(map.size() - 1)
                    .toString());
            index++;
            consecutive = year + "_" + index;
        } catch (Exception ex) {
            consecutive = year + "_" + "1";
        }
        return consecutive;
    }

    /**
     * Almacena el prefijo del Riesgo actual.
     * @param value el objeto String que representa el prefijo de un Riesgo
     */
    @Override
    public void setPrefix(String value) {
        super.setPrefix(value);
        String prefix = value.substring(0, value.lastIndexOf("_"));
        setYearRisk(prefix);
    }
}
