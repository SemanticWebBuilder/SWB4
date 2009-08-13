package org.semanticwb.portal.community;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;


public class MicroSiteWebPageUtil extends org.semanticwb.portal.community.base.MicroSiteWebPageUtilBase 
{
    public MicroSiteWebPageUtil(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean sendNotification(WebPage util, MicroSiteElement element)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm");
        boolean sended = Boolean.FALSE;
        MicroSiteWebPageUtil mswpu = null;
        if(util instanceof MicroSiteWebPageUtil && null!= element)
        {
            User creator = element.getModifiedBy();
            String title = element.getDisplayTitle(null);
            String description = element.getDisplayDescription(null);

            String commTitle = mswpu.getMicroSite().getDisplayTitle(null);
            if(null==commTitle) commTitle = "---";
            String utilName = mswpu.getDisplayTitle(null);
            if(null==utilName) utilName ="---";
            String utilDesc = mswpu.getDisplayDescription(null);
            if(null==utilDesc) utilDesc="";

            String txtSubject = "Cambios en la comunidad "+commTitle+" de la utilería "+utilName;


            StringBuffer data = new StringBuffer();
            data.append("\n<html>\n<head>\n<title>Notificación de cambios</title>");
            data.append("\n<style>");
            data.append("\n#notify {");
            data.append("\n { ");
            data.append("\n    width:95%; ");
            data.append("\n    font-family:arial,verdana; ");
            data.append("\n    font-size:14px; ");
            data.append("\n } ");
            data.append("\n</style>");
            data.append("\n</head>");
            data.append("\n<body>");

            data.append("\n<div id=\"notify\">");
            data.append("\n<h3>Notificación de cambios en la comunidad "+commTitle+"</h3>");
            data.append("\n<table>");
            data.append("\n<tr>");
            data.append("\n<td>En la utilería "+utilName+"</td>");
            data.append("\n<td>&nbsp;"+utilDesc+"</td>");
            data.append("\n</tr>");
            data.append("\n</dd>");

            data.append("\n<tr>");
            data.append("\n<td>Se modificó el elemento "+title+"</td>");
            data.append("\n<td>&nbsp;"+description+", el día "+sdf.format(element.getUpdated())+"</td>");
            data.append("\n</tr>");

            data.append("\n<tr>");
            data.append("\n<td>Por el usuario: "+creator.getName()+"</td>");
            data.append("\n<td colspan=\"2\"><a href=\"mailto:"+creator.getEmail()+"?subject=RE:"+txtSubject+"\">"+creator.getEmail()+"</a></td>");
            data.append("\n</tr>");
            data.append("\n</table>");

            data.append("\n</div>");
            data.append("\n</body>");
            data.append("\n</html>");

            SWBMail mail = new SWBMail();
            mail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
            mail.setFromName("Administrador");
            mail.setSubject(txtSubject);
            mail.setContentType("text/html");
            mail.setData(data.toString());

            ArrayList memberlist = new ArrayList();
            mswpu = (MicroSiteWebPageUtil)util;
            Iterator<Member> itm = mswpu.listSubscribedMembers();
            while(itm.hasNext())
            {
                Member member = itm.next();
                String emailmember = member.getUser().getEmail();
                memberlist.add(emailmember);
            }
            if(memberlist.size()>0)
            {
                mail.setBccEmail(memberlist);

                try {
                    SWBUtils.EMAIL.sendBGEmail(mail);
                    sended = Boolean.TRUE;
                } catch (Exception e) {
                    sended = Boolean.FALSE;
                }
            }

        }
        return sended;
    }

    public boolean subscribeToElement(Member member)
    {
        return Boolean.TRUE;
    }

    public boolean unSubscribeFromElement(Member member)
    {
        return Boolean.TRUE;
    }


    public boolean isSubscribed(Member member)
    {
        return Boolean.TRUE;
    }
}
