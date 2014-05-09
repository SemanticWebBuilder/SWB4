package org.semanticwb.bsc.accessory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;


   /**
   * Determinante que define un criterio para evaluar si un control es suficiente o insuficiente para un Riesgo. Un riesgo puede tener varios determinantes. 
   */
public class Determinant extends org.semanticwb.bsc.accessory.base.DeterminantBase 
{
    public Determinant(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }
    
    /**
     * Obtiene el conjunto de determinantes cuyo estado actual es activo, no eliminado y para los cuales
     * el usuario en sesi&oacute;n tiene acceso
     * @param model el modelo del cual se obtendr&aacute; el conjunto de determinantes para evaluar
     * @return un {@code Iterator} que contiene el cunjunto de determinantes que cumplen con las condiciones mencionadas
     */
    public static List<Determinant> listValidDeterminants(SWBModel model) {
        
        List validItems = new ArrayList(8);
        
        if (model != null) {
            validItems = SWBUtils.Collections.filterIterator(Determinant.ClassMgr.listDeterminants(model),
                    new GenericFilterRule<Determinant>() {
                        @Override
                        public boolean filter(Determinant det) {
                            User user = SWBContext.getSessionUser(((SWBModel)
                                    det.getSemanticObject().getModel().getModelObject().createGenericInstance()
                                    ).getParentWebSite().getUserRepository().getId());
                            if (user == null) {
                                user = SWBContext.getAdminUser();
                            }
                            return !det.isValid() || !user.haveAccess(det);
                        }
                    });
        }
        return validItems;
    }
}
