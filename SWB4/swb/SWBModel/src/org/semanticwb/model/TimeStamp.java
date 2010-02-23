package org.semanticwb.model;

//~--- non-JDK imports --------------------------------------------------------

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

//~--- JDK imports ------------------------------------------------------------

import java.sql.Timestamp;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class TimeStamp extends org.semanticwb.model.base.TimeStampBase {
    private static Logger log = SWBUtils.getLogger(TimeStamp.class);

    public TimeStamp(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop) {
        if (prop.getDisplayProperty() == null) {
            return;
        }

        String value = request.getParameter(prop.getName());

        if (value != null && prop.isDateTime()) {
            // System.out.println("old:"+old+" value:"+value);
            try {
                obj.setDateTimeProperty(prop, new Timestamp(Long.parseLong(value)));
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type,
                                String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }

//        boolean IPHONE = false;
//        boolean XHTML  = false;
//        boolean DOJO   = false;

//        if (type.equals("iphone")) {
//            IPHONE = true;
//        } else if (type.equals("xhtml")) {
//            XHTML = true;
//        } else if (type.equals("dojo")) {
//            DOJO = true;
//        }

        StringBuffer ret   = new StringBuffer();
        String       name  = prop.getName();
        //String       label = prop.getDisplayName(lang);
        Date         ndate = new Date();
        Date         date  = null;

        if (prop.isDateTime()) {
            date = obj.getDateTimeProperty(prop);
        }

        if (date != null) {
            ndate = date;
        }

        String value = ndate.toString();

        if (mode.equals("edit") || mode.equals("create")) {
            ret.append("<span name=\"" + name + "\">" + value + "</span>");

            if (date != null) {
                ret.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + date.getTime() + "\">");
            }
        } else if (mode.equals("view")) {
            ret.append("<span name=\"" + name + "\">" + value + "</span>");
        }

        return ret.toString();
    }
}
