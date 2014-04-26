package org.semanticwb.bsc.element;

import java.util.GregorianCalendar;
import org.semanticwb.bsc.tracing.Meeting;


   /**
   * Define las características de un Acuerdo. 
   */
public class Agreement extends org.semanticwb.bsc.element.base.AgreementBase {
    
    
    public Agreement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public String getAgreementNumber() {
        String agreementNumber = super.getAgreementNumber();
        
        if (agreementNumber == null) {
            Agreement agreement = (Agreement) getSemanticObject().createGenericInstance();
            Meeting meeting = agreement.getMeeting();
            int meetingId = Integer.getInteger(meeting.getId());
            int agreementId = Integer.getInteger(this.getId());
            java.util.GregorianCalendar systemDate = new java.util.GregorianCalendar();
            int anio = systemDate.get(GregorianCalendar.YEAR) % 100;
            agreementNumber = agreement.getBSC().getTitle() + "-" + meeting.getMeetingType().toUpperCase() +
                    (meetingId > 9 ? "" : "0") + meetingId + "-" +
                    (agreementId > 9 ? "" : "0") + agreementId + anio;
            this.setAgreementNumber(agreementNumber);
        }
        return agreementNumber;
    }
    
    
}
