package org.semanticwb.bsc.tracing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.bsc.accessory.Determinant;
import org.semanticwb.bsc.element.Risk;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;

/**
 * Clase que define un factor de riesgo.
 */
public class Factor extends org.semanticwb.bsc.tracing.base.FactorBase {
    
    static
    {
        Factor.bsc_hasControl.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
System.out.println("\nFactor bsc_hasControl notify....");
System.out.println("obj="+obj);
System.out.println("prop="+prop);
System.out.println("action="+action);

                if("ADD".equalsIgnoreCase(action))
                {
                    SWBModel model = (SWBModel)obj.getModel().getModelObject().getGenericInstance();
                    Iterator<Determinant> determinants = Determinant.ClassMgr.listDeterminants(model);
                    
                    Factor factor = (Factor)obj.createGenericInstance();
                    Control control = factor.getLastControl();
                    if(control!=null) {
                        while(determinants.hasNext()) {
                            Determinant d = determinants.next();
                            if(!d.isValid()) {
                                continue;
                            }
                            DeterminantValue dv = DeterminantValue.ClassMgr.createDeterminantValue(model);
                            dv.setDeterminant(d);
                            control.addDeterminantValue(dv);
                        }
                    }
                }
            }
        });
    }

    public Factor(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }
    
    public Control getLastControl() {
        return getControl();
    }

    @Override
    public synchronized Control getControl() {
        Iterator<Control> it = SWBComparator.sortByCreated(listControls(), false);
        return it.hasNext()?it.next():null;
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
            Risk risk = factor.getRisk();
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
        List map = new ArrayList();
        Iterator<Factor> it = risk.listFactors();
        while (it.hasNext()) {
            Factor obj = it.next();
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
            consecutive = prefixRisk + "." + index;
        } catch (Exception ex) {
            consecutive = prefixRisk + "." + "1";
        }
        return consecutive;
    }
}
