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
System.out.println("Objetivo getSerial="+super.getSerial());
        return super.getSerial();
    }

    @Override
    public synchronized void setSerial(int value) {
System.out.println("Objetivo setSerial value="+value);
        super.setSerial(value);
    }

    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if(prefix==null) {
            prefix = getTheme().getPerspective().getPrefix() + getTheme().getPerspective().getSerial();
            setPrefix(prefix);
        }
System.out.println("Objetivo prefix="+prefix);
        return prefix;
    }

    @Override
    public void setPrefix(String value) {
System.out.println("Objetivo setPrefix value="+value);
        super.setPrefix(value);
           
    }
}
