package org.semanticwb.portal.community;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import javax.mail.internet.InternetAddress;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.util.db.GenericDB;


public class MicroSiteWebPageUtil extends org.semanticwb.portal.community.base.MicroSiteWebPageUtilBase 
{
    private static Logger log = SWBUtils.getLogger(MicroSiteWebPageUtil.class);

    static
    {
        try {
            Connection con = SWBUtils.DB.getDefaultConnection("Revisión si existe tabla swb_commlog. MicroSiteWebPageUtil (static)");
            Statement st = con.createStatement();
            try {
                ResultSet rs = st.executeQuery("select count(*) from swb_commlog");
                if (rs.next()) {
                    int x = rs.getInt(1);
                }
                rs.close();
            } catch (SQLException ne) {
                //ne.printStackTrace();
                log.event("Creating Community Log Table...");
                GenericDB db = new GenericDB();
                String xml = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath() + "/WEB-INF/xml/swb_commlog.xml");
                db.executeSQLScript(xml, SWBUtils.DB.getDatabaseName(), null);
            }
            st.close();
            con.close();
        } catch (SQLException e) {
            log.error(e);
        }
    }



    public MicroSiteWebPageUtil(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static MicroSiteWebPageUtil getMicroSiteWebPageUtil(WebPage page)
    {
        if(page instanceof MicroSiteWebPageUtil)
        {
            return (MicroSiteWebPageUtil)page;
        }
        return null;
    }

    public boolean sendNotification(MicroSiteElement element)
    {
        System.out.println("sendNotification:"+element);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm");
        boolean sended = Boolean.FALSE;
        MicroSiteWebPageUtil mswpu = this;

        if(null!= element)
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

            addCommunityLog(mswpu.getMicroSite(), creator, element);

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
            data.append("\n<fieldset>");
            data.append("\n<legend>Notificación de cambios en la comunidad <i>"+commTitle+"</i></legend>");
            data.append("\n  <dl>");
            data.append("\n   <dt>Utilería <strong>"+utilName+"</strong></dt>");
            data.append("\n   <dd><p>"+utilDesc+"</p></dd>");

            data.append("\n   <dt>Elemento <strong>"+title+"</strong></dt>");
            data.append("\n   <dd><p>"+description+"</p></dd>"); 

            data.append("\n  </dl>");
            data.append("\n</fieldset>");
            data.append("\n<fieldset>");
            data.append("\n  <p>Por el usuario: <strong>"+creator.getName()+"</strong>");
            data.append("\n( <a href=\"mailto:"+creator.getEmail()+"?subject=RE:"+txtSubject+"\">"+creator.getEmail()+"</a><br/>");
            data.append("\nFecha del cambio: "+sdf.format(element.getUpdated()));
            data.append("\n  </p>");
            data.append("\n</fieldset>");
            data.append("\n</div>");
            data.append("\n</body>");
            data.append("\n</html>");

            SWBMail mail = new SWBMail();

            String webbuilderemail = SWBPlatform.getEnv("af/adminEmail");
            String smtpserver = SWBPlatform.getEnv("swb/smtpServer");

            mail.setHostName(smtpserver);

            InternetAddress emailaddr = new InternetAddress();
            emailaddr.setAddress(webbuilderemail);

            mail.setFromEmail(webbuilderemail);
            mail.setFromName("Administrador");
            mail.setSubject(txtSubject);
            mail.setContentType("text/html");
            mail.setData(data.toString());

            ArrayList memberlist = new ArrayList();
            memberlist.add(emailaddr);

            mail.setToEmail(memberlist);

            memberlist = new ArrayList();

            Iterator<Member> itm = mswpu.listSubscribedMembers();
            while(itm.hasNext())
            {
                Member member = itm.next();
                String emailmember = member.getUser().getEmail();
                if(emailmember!=null)
                {
                    emailaddr = new InternetAddress();
                    emailaddr.setAddress(emailmember);
                    memberlist.add(emailaddr);
                }
                
            }
            if(memberlist.size()>0)
            {
                mail.setBccEmail(memberlist);

                try {
                    System.out.println("SendMail:"+memberlist);
                    SWBUtils.EMAIL.sendBGEmail(mail);
                    sended = Boolean.TRUE;
                } catch (Exception e) {
                    System.out.println("Error al enviar notificacion por correo.");
                    e.printStackTrace(System.out);
                    sended = Boolean.FALSE;
                }
            }

        }

        return sended;
    }

    private void addCommunityLog(MicroSite community, User usr, MicroSiteElement element)
    {
        String commURI = community.getURI();
        String usrURI = usr.getURI();
        String elementURI = element.getURI();
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = SWBUtils.DB.getDefaultConnection("MicroSiteWebPageUtil.addComminityLog");
            pst = conn.prepareStatement("insert into swb_commlog (commuri, usruri, elementuri) values (?,?,?)");
            pst.setString(1,commURI);
            pst.setString(2,usrURI);
            pst.setString(3,elementURI);
            int nerec=pst.executeUpdate();
            pst.close();
            conn.close();
        } catch (Exception e) {
            log.error("Error al insertar el log de la comunidad.",e);
        } finally
        {
            try { pst.close(); } catch (Exception e) { }
            try { conn.close(); } catch (Exception e) { }
        }

    }  

    public void subscribeToElement(Member member)
    {
        System.out.println("subscribeToElement1:"+member);
        if(member!=null)
        {
            if(!isSubscribed(member))
            {
                System.out.println("subscribeToElement2");
                member.addSubscriptions(this);
            }
        }
    }

    public void unSubscribeFromElement(Member member)
    {
        System.out.println("unSubscribeFromElement1:"+member);
        if(member!=null)
        {
            System.out.println("unSubscribeFromElement2");
            member.removeSubscriptions(this);
        }
    }


    public boolean isSubscribed(Member member)
    {
        boolean ret=false;
        if(member!=null && !member.getSemanticObject().isVirtual())
        {
            ret=member.hasSubscriptions(this);
        }
        return ret;
    }
}
