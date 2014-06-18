package org.semanticwb.bsc.accessory;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.platform.SemanticProperty;


   /**
   * Un Differentiator se dibuja en el mapa estratégico del scorecard. Los Differentiator unicamente sirvan para alojar etiquetas de differenciadores dentro de un mapa estratégico 
   */
public class Differentiator extends org.semanticwb.bsc.accessory.base.DifferentiatorBase implements Comparable<Differentiator>
{
    public Differentiator(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public int compareTo(Differentiator anotherDifferentiator)
    {
        int compare = getIndex()>anotherDifferentiator.getIndex()?1:-1;
        return compare;
    }

    @Override
    public void validOrder(HttpServletRequest request, SemanticProperty prop, String propName) throws FormValidateException {
        int ordinal;
        try {
            String value = request.getParameter(propName);
            ordinal = Integer.parseInt(value);
        }catch(NumberFormatException pe) {            
            throw new FormValidateException("El valor debe ser numérico y no puede repetirse");
        }
        
        GenericIterator<Differentiator> it = getDifferentiatorGroup().listDifferentiators();
        while(it.hasNext()) {
            Differentiator d = it.next();
            if( this.equals(d) ) {
                continue;
            }
            if(d.getIndex() == ordinal) {
                throw new FormValidateException("El valor ordinal debe ser numérico y no puede repetirse");
            }
        }
    }
}
