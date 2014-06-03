/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.bsc.utils.EmailLog;

import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author ana.garcias
 */
public class EmailLogResource extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        //super.doView(request, response, paramRequest); //To change body of generated methods, choose Tools | Templates.
        PrintWriter out = response.getWriter();
        StringBuilder toReturn = new StringBuilder();
        SWBResourceURL url = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW);
        User usr = null;
        String uriUser = request.getParameter("selectUser") == null ? "0" : (String) request.getParameter("selectUser");
        String dateFrom = request.getParameter("dateFrom") == null ? "" : (String) request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo") == null ? "" : (String) request.getParameter("dateTo");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        Calendar cal3 = new GregorianCalendar();

        Iterator<User> itUser = User.ClassMgr.listUsers();
        toReturn.append("<script type=\"text/javascript\">");
        toReturn.append("dojo.require(\"dijit.form.DateTextBox\");");
        toReturn.append("dojo.require(\"dijit.form.ComboBox\");");
        toReturn.append("dojo.require('dijit.form.ValidationTextBox');\n");
        toReturn.append("dojo.require('dijit.form.Form');\n");
        toReturn.append("</script>");

        toReturn.append("<div>");
        toReturn.append("<form method=\"post\" id=\"frmAdd\" action=\" " + url
                + "\" class=\"swbform\" type=\"dijit.form.Form\" onsubmit=\""
                + "submitForm('frmAdd');return false;\">");
        toReturn.append("<fieldset>");
        toReturn.append(paramRequest.getLocaleString("lbl_title"));
        toReturn.append("</fieldset>");
        toReturn.append("<fieldset>");
        toReturn.append("<legend>" + paramRequest.getLocaleString("lbl_legend") + "</legend>");
        toReturn.append("<table width=\"60%\">");
        toReturn.append("<tr>");
        toReturn.append("<td width=\"30%\">&nbsp;&nbsp;&nbsp;" + paramRequest.getLocaleString("lbl_userFrom") + ":</td>");
        toReturn.append("<td>");
        toReturn.append("<select name=\"selectUser\">");
        toReturn.append("<option value=\"0\">Selecciona un usuario...</option>");
        while (itUser.hasNext()) {
            usr = itUser.next();
            if (!usr.getUserRepository().getId().equals("uradm")) {
                toReturn.append("<option id=\"userFrom\" value=\"" + usr.getURI() + "\"");
                if (uriUser.equals(usr.getURI())) {
                    toReturn.append("selected=\"selected\"");
                }
                toReturn.append(">");
                toReturn.append(usr.getFullName());
                toReturn.append("</option>");
            }
        }

        toReturn.append("</select>");
        toReturn.append("</td>");
        toReturn.append("</tr>");
        toReturn.append("<tr>");
        toReturn.append("<td>&nbsp;&nbsp;&nbsp;" + paramRequest.getLocaleString("lbl_from") + ": <input type=\"text\" name=\"dateFrom\" onblur=\"if(!this.value){this.focus();}\" id=\"dateFrom\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\"" + dateFrom + "\"></td>");
        toReturn.append("<td>" + paramRequest.getLocaleString("lbl_to") + ": <input type=\"text\" name=\"dateTo\" onblur=\"if(!this.value){this.focus();}\" id=\"dateTo\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\"" + dateTo + "\"></td>");
        toReturn.append("</tr>");
        toReturn.append("<tr>");
        toReturn.append("<td colspan=\"2\">&nbsp;&nbsp;&nbsp;</td></tr>");
        toReturn.append("<tr><td align=\"left\"><button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("lbl_apply"));
        toReturn.append("</button></td>");
        toReturn.append("<td></td></tr>");
        toReturn.append("<tr>");
        toReturn.append("<td colspan=\"2\">&nbsp;&nbsp;&nbsp;</td></tr>");
        toReturn.append("</table>");
        toReturn.append("</fieldset>");
        toReturn.append("</form>");
        toReturn.append("<p>&nbsp;</p>");
        toReturn.append("</div>");

        EmailLog log = null;
        Iterator<EmailLog> itlogUser = null;
        User to = null;
        User cc = null;
        String from = "";
        String subject = "";
        String otherAccounts = "";
        Date date1;
        Date date2;
        Date date3;
        
        toReturn.append("<table width=\"94%\">");
        toReturn.append("<thead>");
        toReturn.append("<tr>");
        toReturn.append("<th>" + paramRequest.getLocaleString("lbl_fromData") + "</th>");
        toReturn.append("<th>" + paramRequest.getLocaleString("lbl_toData") + "</th>");
        toReturn.append("<th>" + paramRequest.getLocaleString("lbl_cc") + "</th>");
        toReturn.append("<th>" + paramRequest.getLocaleString("lbl_other") + "</th>");
        toReturn.append("<th>" + paramRequest.getLocaleString("lbl_subject") + "</th>");
        toReturn.append("<th>" + paramRequest.getLocaleString("lbl_date") + "</th>");
        toReturn.append("</tr>");
        toReturn.append("</thead>");

       

        if ((uriUser.equals("0")) && (dateFrom.equals("")) && (dateTo.equals(""))) {
            toReturn.append("<script type=\"text/javascript\">");
            toReturn.append("        alert('" + paramRequest.getLocaleString("lbl_msg1") + "');\n");
            toReturn.append("    </script>");
        } else if ((!dateFrom.equals("")) && (dateTo.equals(""))) {
            toReturn.append("<script type=\"text/javascript\">");
            toReturn.append("        alert('" + paramRequest.getLocaleString("lbl_msg2") + "');\n");
            toReturn.append("    </script>");
        } else if ((dateFrom.equals("")) && (!dateTo.equals(""))) {
            toReturn.append("<script type=\"text/javascript\">");
            toReturn.append("        alert('" + paramRequest.getLocaleString("lbl_msg3") + "');\n");
            toReturn.append("    </script>");
        }
        //Consulta por Usuario
        if ((!uriUser.equals("0")) && (dateFrom.equals("")) && (dateTo.equals(""))) {
            SemanticObject sObj = SemanticObject.getSemanticObject(uriUser);
            SWBModel model = (SWBModel) sObj.getModel().getModelObject().createGenericInstance();
            SWBModel modelWS = model.getParentWebSite();

            User user = User.ClassMgr.getUser(sObj.getId(), model);
            itlogUser = EmailLog.ClassMgr.listEmailLogByFrom(user, modelWS);
            while (itlogUser.hasNext()) {
                log = itlogUser.next();
                subject = log.getSubject() == null ? "" : log.getSubject();
                otherAccounts = log.getOtherAccounts() == null ? "" : log.getOtherAccounts();
                if (log.getFrom() != null) {
                    from = log.getFrom().getEmail();
                }
                Date dateLog = log.getCreated();
                toReturn.append("<tr>");
                toReturn.append("<td>");
                toReturn.append(from);
                toReturn.append("</td>");
                Iterator<User> itTo = log.listTos();
                if (itTo != null) {
                    toReturn.append("<td>");
                    while (itTo.hasNext()) {
                        to = itTo.next();
                        toReturn.append(to.getEmail() + "; &nbsp;");
                    }
                    toReturn.append("</td>");
                } else {
                    toReturn.append("<td>&nbsp;</td>");
                }

                Iterator<User> itCc = log.listCcs();
                if (itCc != null) {
                    toReturn.append("<td>");
                    while (itCc.hasNext()) {
                        cc = itCc.next();
                        toReturn.append(cc.getEmail() + "; &nbsp;");
                    }
                    toReturn.append("</td>");
                } else {
                    toReturn.append("<td></td>");
                }
                toReturn.append("<td>" + otherAccounts + "</td>");
                toReturn.append("<td>" + subject + "</td>");
                toReturn.append("<td>" + dateFormat.format(dateLog) + "</td>");
                toReturn.append("</tr>");
            }
        }
        //Consulta por Fechas
        if ((uriUser.equals("0")) && (!dateFrom.equals("")) && (!dateTo.equals(""))) {
            try {
                date1 = dateFormat2.parse(dateFrom);
                date2 = dateFormat2.parse(dateTo);
                cal1.setTime(date1);
                cal2.setTime(date2);
            } catch (ParseException ex) {
                Logger.getLogger(EmailLogResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (cal2.before(cal1)) {
                toReturn.append("<script type=\"text/javascript\">");
                toReturn.append("        alert('" + paramRequest.getLocaleString("lbl_msg4") + "');\n");
                toReturn.append("    </script>");
            } else {
                //Trae la lista de todols los EmailLog
                Iterator<EmailLog> itLogDate = EmailLog.ClassMgr.listEmailLogs();
                while (itLogDate.hasNext()) {
                    log = itLogDate.next();
                    //String dateL = dateFormat2.format(log.getDate());
                    //try {
                        //date3 = dateFormat2.parse(dateL);
                    Date dateLog = log.getCreated();
                        cal3.setTime(dateLog);
                    //} catch (ParseException ex) {
                    //    Logger.getLogger(EmailLogResource.class.getName()).log(Level.SEVERE, null, ex);
                    //}
                    if ((cal3.after(cal1) || cal3.equals(cal1)) && (cal3.before(cal2) || cal3.equals(cal2))) {
                        subject = log.getSubject() == null ? "" : log.getSubject();
                        otherAccounts = log.getOtherAccounts() == null ? "" : log.getOtherAccounts();
                        
                        if (log.getFrom() != null) {
                            from = log.getFrom().getEmail();
                        }
                        //Date dateLog = log.getDate();
                        toReturn.append("<tr>");
                        toReturn.append("<td>");
                        toReturn.append(from);
                        toReturn.append("</td>");
                        Iterator<User> itTo = log.listTos();
                        if (itTo != null) {
                            toReturn.append("<td>");
                            while (itTo.hasNext()) {
                                to = itTo.next();
                                toReturn.append(to.getEmail() + "; &nbsp;");
                            }
                            toReturn.append("</td>");
                        } else {
                            toReturn.append("<td>&nbsp;</td>");
                        }

                        Iterator<User> itCc = log.listCcs();
                        if (itCc != null) {
                            toReturn.append("<td>");
                            while (itCc.hasNext()) {
                                cc = itCc.next();
                                toReturn.append(cc.getEmail() + "; &nbsp;");
                            }
                            toReturn.append("</td>");
                        } else {
                            toReturn.append("<td></td>");
                        }
                        toReturn.append("<td>" + otherAccounts + "</td>");
                        toReturn.append("<td>" + subject + "</td>");
                        toReturn.append("<td>" + dateFormat.format(dateLog) + "</td>");
                        toReturn.append("</tr>");
                    }
                }
            }
        }
        //Consulta por usuario y por fechas
        if ((!uriUser.equals("0")) && (!dateFrom.equals("")) && (!dateTo.equals(""))) {
            SemanticObject sObj = SemanticObject.getSemanticObject(uriUser);
            SWBModel model = (SWBModel) sObj.getModel().getModelObject().createGenericInstance();
            SWBModel modelWS = model.getParentWebSite();
            User user = User.ClassMgr.getUser(sObj.getId(), model);
            try {
                date1 = dateFormat2.parse(dateFrom);
                date2 = dateFormat2.parse(dateTo);
                cal1.setTime(date1);
                cal2.setTime(date2);
            } catch (ParseException ex) {
                Logger.getLogger(EmailLogResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (cal2.before(cal1)) {
                toReturn.append("<script type=\"text/javascript\">");
                toReturn.append("        alert('" + paramRequest.getLocaleString("lbl_msg4") + "');\n");
                toReturn.append("    </script>");
            } else {
                Iterator<EmailLog> itLog = EmailLog.ClassMgr.listEmailLogByFrom(user, modelWS);
                while (itLog.hasNext()) {
                    log = itLog.next();
                    Date dateLog = log.getCreated();
//                    String dateL = dateFormat2.format(log.getDate());
//                    try {
//                        date3 = dateFormat2.parse(dateL);
                        cal3.setTime(dateLog);
//                    } catch (ParseException ex) {
//                        Logger.getLogger(EmailLogResource.class.getName()).log(Level.SEVERE, null, ex);
//                    }

                    if ((cal3.after(cal1) || cal3.equals(cal1)) && (cal3.before(cal2) || cal3.equals(cal2))) {
                        subject = log.getSubject() == null ? "" : log.getSubject();
                        otherAccounts = log.getOtherAccounts() == null ? "" : log.getOtherAccounts();
                        if (log.getFrom() != null) {
                            from = log.getFrom().getEmail();
                        }
                        
                        toReturn.append("<tr>");
                        toReturn.append("<td>");
                        toReturn.append(from);
                        toReturn.append("</td>");
                        Iterator<User> itTo = log.listTos();
                        if (itTo != null) {
                            toReturn.append("<td>");
                            while (itTo.hasNext()) {
                                to = itTo.next();
                                toReturn.append(to.getEmail() + "; &nbsp;");
                            }
                            toReturn.append("</td>");
                        } else {
                            toReturn.append("<td>&nbsp;</td>");
                        }

                        Iterator<User> itCc = log.listCcs();
                        if (itCc != null) {
                            toReturn.append("<td>");
                            while (itCc.hasNext()) {
                                cc = itCc.next();
                                toReturn.append(cc.getEmail() + "; &nbsp;");
                            }
                            toReturn.append("</td>");
                        } else {
                            toReturn.append("<td></td>");
                        }
                        toReturn.append("<td>" + otherAccounts + "</td>");
                        toReturn.append("<td>" + subject + "</td>");
                        toReturn.append("<td>" + dateFormat.format(dateLog) + "</td>");
                        toReturn.append("</tr>");
                    }
                }
            }

        }
        toReturn.append("</table>");
        out.println(toReturn.toString());
    }
}