package org.semanticwb.bsc.tracing;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;
import org.semanticwb.bsc.accessory.Determinant;
import org.semanticwb.model.SWBModel;

/**
 * Gestiona la informaci&oacute;n de un control en un Riesgo.
 */
public class Control extends org.semanticwb.bsc.tracing.base.ControlBase {

    public Control(org.semanticwb.platform.SemanticObject base) {
        super(base);
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
            prefix = prefix + "." + getConsecutive(factor);
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
    private synchronized int getConsecutive(Factor factor) {
        int consecutive = 0;
        SortedSet<Integer> map = new TreeSet<Integer>();
        //List map = new ArrayList();
        Iterator<Control> it = factor.listControls();
        while (it.hasNext()) {
            Control obj = it.next();
            String prefix = obj.getSemanticObject().getProperty(bsc_prefix, false);
            if (prefix != null && prefix.lastIndexOf(".") > -1) {
                prefix = prefix.substring(prefix.lastIndexOf(".") + 1,
                        prefix.length());
                int serial = Integer.parseInt(prefix);
                map.add(serial);
            }
        }
        /*try {
            //Collections.sort(map);
            int index = Integer.parseInt(map.get(map.size() - 1).toString());
            index++;
            consecutive = prefixFactor + "." + index;
        } catch (Exception ex) {
            consecutive = prefixFactor + "." + "1";
        }*/
        
        try {
            consecutive = map.last();  //map.get(map.size() - 1;
        }catch(NoSuchElementException nse) {
            consecutive = 0;
        }finally {
            consecutive++;
        }
        return consecutive;
    }
    
    /**
     * Relaciona un {@code Control} recientemente creado al conjunto de instancias de {@code Determinant}
     * existentes en el BSC correspondiente, al crear las instancias de {@code DeterminantValue} necesarias.
     */
    public void relateToDeterminants() {
        
        SWBModel model = (SWBModel) this.getSemanticObject().getModel().getModelObject().createGenericInstance();
        Iterator<Determinant> determinantIt = Determinant.ClassMgr.listDeterminants(model);
        HashMap<Determinant, Boolean> existent = new HashMap<Determinant, Boolean>();
        
        while (determinantIt != null && determinantIt.hasNext()) {
            Determinant det = determinantIt.next();
            existent.put(det, Boolean.FALSE);
        }
        Iterator<DeterminantValue> detValIt = this.listDeterminantValues();
        while (detValIt != null && detValIt.hasNext()) {
            DeterminantValue detValue = detValIt.next();
            existent.put(detValue.getDeterminant(), Boolean.TRUE);
        }
        
        if (existent.containsValue(Boolean.FALSE)) {
            synchronized (this) {
                Iterator<Determinant> inSetDeterminant = existent.keySet().iterator();
                while (inSetDeterminant.hasNext()) {
                    Determinant det = inSetDeterminant.next();
                    if (!existent.get(det)) {
                        DeterminantValue detValue = DeterminantValue.ClassMgr.createDeterminantValue(model);
                        detValue.setDeterminant(det);
                        this.addDeterminantValue(detValue);
                    }
                }
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
                Determinant det = detValue.getDeterminant();
                if (det.isValid() && (detValue.getIsDeterminant() == null ||
                        (detValue.getIsDeterminant() != null &&
                         detValue.getIsDeterminant().equalsIgnoreCase("no")))) {
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
