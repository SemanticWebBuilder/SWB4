package org.semanticwb.bsc;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

   /**
   * Interfaz que define un valor entero para ordenar 
   */
public interface Sortable extends org.semanticwb.bsc.base.SortableBase
{
    public void validOrder(HttpServletRequest request, SemanticProperty prop, String propName) throws FormValidateException;
}
