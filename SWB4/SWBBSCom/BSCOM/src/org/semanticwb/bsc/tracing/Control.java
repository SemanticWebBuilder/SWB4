package org.semanticwb.bsc.tracing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Gestiona la informaci&oacute;n de un control en un Riesgo.
 */
public class Control extends org.semanticwb.bsc.tracing.base.ControlBase {

    public Control(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Obtiene el prefijo en base al prefijo del Factor padre. La nomenclatura en la
     * que se basa se muestra a continuaci&oacute;n:  Prefijo del Factor  + "."
     *  + n&uacute;mero consecutivo del Control.
     * @return el objeto String que representa el prefijo para un Control 
     */
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if (prefix == null) {
            Control control = (Control) getSemanticObject()
                    .createGenericInstance();
            Factor factor = control.getControlInv();
            prefix = factor.getPrefix();
            prefix = getConsecutive(prefix, factor);
            setPrefix(prefix);
        }
        return prefix;
    }

    /**
     * Obtiene el siguiente prefijo consecutivo para un Control dado un Riesgo.
     * @param prefixControl el prefijo del Factor
     * @param factor el Factor que contiene al Control actual
     * @return el objeto String que representa el siguiente prefijo para un Control 
     */
    private String getConsecutive(String prefixFactor, Factor factor) {
        String consecutive = "";
        List<String> map = new ArrayList<String>();
        Iterator<Control> it = factor.listControls();
        while (it.hasNext()) {
            Control obj = it.next();
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
            consecutive = prefixFactor + "." + index;
        } catch (Exception ex) {
            consecutive = prefixFactor + "." + "1";
        }
        return consecutive;
    }
}
