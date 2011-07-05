package org.semanticwb.portal.resources.sem.contact;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;

public class Address extends org.semanticwb.portal.resources.sem.contact.base.AddressBase 
{
    public Address(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*public void edit(HttpServletRequest request, SWBActionResponse response) {
        setStreet(SWBUtils.TEXT.nullValidate(request.getParameter("str"), ""));
        setSuburb(SWBUtils.TEXT.nullValidate(request.getParameter("sb"), ""));
        setCity(SWBUtils.TEXT.nullValidate(request.getParameter("ct"), ""));
        setState(SWBUtils.TEXT.nullValidate(request.getParameter("st"), ""));
        setCp(SWBUtils.TEXT.nullValidate(request.getParameter("cp"), ""));
//        setCountry(Country.ClassMgr.getCountry(request.getParameter("country"),));
        setIsToCheck(request.getParameter("ck")==null?false:true);
        setIsToMail(request.getParameter("ml")==null?false:true);
    }*/

    public String toString(String language) {
        StringBuilder sb = new StringBuilder();
        if(getStreet()!=null)
            sb.append(getStreet()).append(";");
        if(getSuburb()!=null)
            sb.append(getSuburb()).append(";");
        if(getCity()!=null)
            sb.append(getCity()).append(";");
        if(getState()!=null)
            sb.append(getState()).append(";");
        if(getCp()!=null)
            sb.append(getCp()).append(";");
        if(getCountry()!=null)
            sb.append(getCountry().getDisplayTitle(language));
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(getStreet()!=null)
            sb.append(getStreet()).append(";");
        if(getSuburb()!=null)
            sb.append(getSuburb()).append(";");
        if(getCity()!=null)
            sb.append(getCity()).append(";");
        if(getState()!=null)
            sb.append(getState()).append(";");
        if(getCp()!=null)
            sb.append(getCp()).append(";");
        if(getCountry()!=null)
            sb.append(getCountry().getTitle());
        return sb.toString();
    }
}
