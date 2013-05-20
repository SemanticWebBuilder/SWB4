package org.semanticwb.bsc.element;


public class Objective extends org.semanticwb.bsc.element.base.ObjectiveBase 
{
    public Objective(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public synchronized int getSerial() {
        setSerial(super.getSerial()+1);
        return super.getSerial();
    }

    @Override
    public synchronized void setSerial(int value) {
        super.setSerial(value);
    }

    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if(prefix==null) {
            setPrefix(getTheme().getPerspective().getPrefix());
        }
        return prefix;
    }

    @Override
    public void setPrefix(String value) {
        super.setPrefix(value + getTheme().getPerspective().getSerial());
           
    }
}
