package org.semanticwb.bsc.tracing;


   /**
   * Persiste la información de una Sesión. Existen  dos tipos de sesiones: RAE y NOA 
   */
public class Meeting extends org.semanticwb.bsc.tracing.base.MeetingBase 
{
    public Meeting(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public int getSerial() {
        return Integer.parseInt(getId());
    }

    @Override
    public synchronized void setSerial(int value) {
        super.setSerial(value);
    }
    
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if(prefix==null) {
            try {
                prefix = getBSC().getId()+"-"+(getMeetingType()!= null
                        ?getMeetingType().toUpperCase() : "")+"-"+(getSerial()>9
                        ?"":"0")+getSerial();
            }catch(Exception e) {
                prefix = "Untitled";
            }
            setPrefix(prefix);
        }
        return prefix;
    }

    @Override
    public void setPrefix(String value) {
        super.setPrefix(value);           
    }
}
