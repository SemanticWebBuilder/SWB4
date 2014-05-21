package org.semanticwb.bsc.tracing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.bsc.accessory.Determinant;
import org.semanticwb.model.SWBModel;

/**
 * Gestiona la informaci&oacute;n de un control en un Riesgo.
 */
public class Control extends org.semanticwb.bsc.tracing.base.ControlBase {

    public Control(org.semanticwb.platform.SemanticObject base) {
        super(base);
        //relateToDeterminants();
    }

    /**
     * Obtiene el prefijo en base al prefijo del Factor padre. La nomenclatura
     * en la que se basa se muestra a continuaci&oacute;n: Prefijo del Factor +
     * "." + n&uacute;mero consecutivo del Control.
     *
     * @return el objeto String que representa el prefijo para un Control
     */
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if (prefix == null) {
            Control control = (Control) getSemanticObject()
                    .createGenericInstance();
            Factor factor = control.getFactor();
            prefix = factor.getPrefix();
            prefix = getConsecutive(prefix, factor);
            setPrefix(prefix);
        }
        return prefix;
    }

    /**
     * Obtiene el siguiente prefijo consecutivo para un Control dado un Riesgo.
     *
     * @param prefixControl el prefijo del Factor
     * @param factor el Factor que contiene al Control actual
     * @return el objeto String que representa el siguiente prefijo para un
     * Control
     */
    private String getConsecutive(String prefixFactor, Factor factor) {
        String consecutive = "";
        List map = new ArrayList();
        Iterator<Control> it = factor.listControls();
        while (it.hasNext()) {
            Control obj = it.next();
            String prefix = obj.getSemanticObject()
                    .getProperty(bsc_prefix, false);
            if (prefix != null && prefix.lastIndexOf(".") > -1) {
                prefix = prefix.substring(prefix.lastIndexOf(".") + 1,
                        prefix.length());
                int intPrefix = Integer.parseInt(prefix);
                map.add(intPrefix);
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
    
    /**
     * Relaciona un {@code Control} recientemente creado al conjunto de instancias de {@code Determinant}
     * existentes en el BSC correspondiente, al crear las instancias de {@code DeterminantValue} necesarias.
     */
    private void relateToDeterminants() {
        
        SWBModel model = (SWBModel) this.getSemanticObject().getModel().getModelObject().createGenericInstance();
        Iterator<Determinant> determinantIt = Determinant.ClassMgr.listDeterminants(model);
        synchronized (this) {
            while (determinantIt != null && determinantIt.hasNext()) {
                Determinant det = determinantIt.next();
                DeterminantValue detValue = DeterminantValue.ClassMgr.createDeterminantValue(model);
                detValue.setDeterminant(det);
            }
        }
    }
    
    /**
     * Calcula el resultado de la determinacion del control en base al valor asignado a cada determinante asociado al control
     * @return {@code true} en caso de que el valor asignado a cada determinante asociado al control sea positivo, {@code false} en
     * caso de que al menos uno de los determinantes tenga asociado un valor negativo.
     */
    public boolean calculateDetermination() {
        
        boolean result = true;
        Iterator<DeterminantValue> detIt = this.listDeterminantValues();
        if (detIt != null) {
            while (detIt.hasNext()) {
                DeterminantValue detValue = detIt.next();
                System.out.println("determinanValue.getIsDeterminant(): " + detValue.getIsDeterminant());
                if (detValue.getIsDeterminant() == null ||
                        (detValue.getIsDeterminant() != null && detValue.getIsDeterminant().equalsIgnoreCase("no"))) {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }
        return result;
    }
}
