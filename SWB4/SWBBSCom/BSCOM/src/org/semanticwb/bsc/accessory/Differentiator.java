package org.semanticwb.bsc.accessory;


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
}
