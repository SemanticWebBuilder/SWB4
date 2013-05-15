package org.semanticwb.bsc;


public class Indicator extends org.semanticwb.bsc.base.IndicatorBase 
{
    public Indicator(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String getFolio() {
        String folio = super.getFolio();
        Indicator indicator = (Indicator)getSemanticObject().createGenericInstance();
        if(folio == null) {
            String folioIndicator = "";
            if(indicator.getParent() instanceof Objective) {
                Objective objective = (Objective) indicator.getParent();
                objective.setProperty("folioObjective", "F1");
                folioIndicator = objective.getProperty("folioObjective") + "." + indicator.getId();
                
            }
            return folioIndicator;
        } else {
            return super.getFolio();
        }
    }

    @Override
    public void setFolio(String value) {
        super.setFolio(value);
    }
    
    
}
