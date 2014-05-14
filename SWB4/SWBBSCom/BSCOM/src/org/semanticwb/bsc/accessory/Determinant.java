package org.semanticwb.bsc.accessory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.tracing.Control;
import org.semanticwb.bsc.tracing.DeterminantValue;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;


   /**
   * Determinante que define un criterio para evaluar si un control es suficiente o insuficiente
   * para un Riesgo. Un riesgo puede tener varios determinantes. 
   */
public class Determinant extends org.semanticwb.bsc.accessory.base.DeterminantBase 
{
    public Determinant(org.semanticwb.platform.SemanticObject base) {
        super(base);
        //relateToControls();
    }
    
    /**
     * Obtiene el conjunto de determinantes cuyo estado actual es activo, no eliminado y para los cuales
     * el usuario en sesi&oacute;n tiene acceso
     * @param webSite el modelo del cual se obtendr&aacute; el conjunto de determinantes para evaluar
     * @return un {@code Iterator} que contiene el cunjunto de determinantes que cumplen con las condiciones mencionadas
     */
    public static List<Determinant> listValidDeterminants(WebSite webSite) {
        
        List validItems = new ArrayList(8);
        
        if (webSite != null) {
            Iterator<Determinant> determIt = Determinant.ClassMgr.listDeterminants(webSite);
            User user = SWBContext.getSessionUser(webSite.getUserRepository().getId());
            
            while (determIt != null && determIt.hasNext()) {
                Determinant det = determIt.next();
                if (det.isValid() && user.haveAccess(det)) {
                    validItems.add(det);
                }
            }
        }
        return validItems;
    }
    
    /**
     * Crea una instancia de {@code DeterminantValue} por cada instancia de {@code Control}
     * existente en el sitio BSC correspondiente.
     */
    private void relateToControls() {
        
        SWBModel model = (SWBModel) this.getSemanticObject().getModel().getModelObject().createGenericInstance();
        Iterator<Control> controlIt = Control.ClassMgr.listControls(model);
        while (controlIt != null && controlIt.hasNext()) {
            Control control = controlIt.next();
            synchronized (control) {
                DeterminantValue detValue = DeterminantValue.ClassMgr.createDeterminantValue(model);
                detValue.setDeterminant(this);
                control.addDeterminantValue(detValue);
            }
        }
    }
}
