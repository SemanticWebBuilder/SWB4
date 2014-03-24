/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.util.Iterator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.bsc.BSC;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.portal.SWBResourceMgr;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author martha.jimenez
 */
public class UserProfileTest {

    final private String modelId = "InfotecPEMP2";
    final private String userGroupId = "Actualizador";
    static SWBResourceMgr mgr = SWBPortal.getResourceMgr();

    @BeforeClass
    public static void setUpClass() throws Exception {
        SWBPlatform plat = SWBPlatform.createInstance();
        plat.setPersistenceType(SWBPlatform.PRESIST_TYPE_SWBTRIPLESTOREEXT);
        SWBPlatform.getSemanticMgr().initializeDB();

        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Desarrollos5/SWB4/SWBBSCom/build/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Desarrollos5/SWB4/SWBBSCom/build/web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Desarrollos5/SWB4/SWBBSCom/build/web/WEB-INF/owl/ext/bscom.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    public void setUpSessionUser(BSC model) {
        Iterator it = model.getUserRepository().listUsers();
        UserGroup userGroup = UserGroup.ClassMgr.getUserGroup(userGroupId, model);
        while (it.hasNext()) {
            User user = (User) it.next();
            if (user.hasUserGroup(userGroup)) {
                SWBContext.setSessionUser(user);
                break;
            }
        }
    }

    @Test
    public void toChangePassword() throws SWBResourceException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (BSC.ClassMgr.hasBSC(modelId)) {

            StringBuilder toReturn = new StringBuilder();
            String url = "#";

            toReturn.append("\n<script language=\"JavaScript\" >");
            toReturn.append("\nfunction jsValidate(form) {");
            toReturn.append("\n var obj = dojo.byId(form);");
            toReturn.append("\n     if(!obj.validate()) {");
            toReturn.append("\n        alert('");
            toReturn.append("lblCheckData");
            toReturn.append("');");
            toReturn.append("\n           return false;");
            toReturn.append("\n     }");

            toReturn.append("\n       if(dojo.byId('newPassword').value != ");
            toReturn.append("           dojo.byId('rePassword').value) {");
            toReturn.append("\n           alert('");
            toReturn.append("msgErrNewPassword");
            toReturn.append("             ');");
            toReturn.append("\n           dijit.byId('newPassword').attr('value','');");
            toReturn.append("\n           dijit.byId('rePassword').attr('value','');");
            toReturn.append("\n           dijit.byId('newPassword').focus();");
            toReturn.append("\n           return false;");
            toReturn.append("\n       }");
            toReturn.append("\n       if(dojo.byId('newPassword').value.length > 10 ||");
            toReturn.append("         dojo.byId('newPassword').value.length < 5) {");
            toReturn.append("\n           alert('");
            toReturn.append("lblErrSize");
            toReturn.append("             ');");
            toReturn.append("\n           dijit.byId('newPassword').attr('value','');");
            toReturn.append("\n           dijit.byId('rePassword').attr('value','');");
            toReturn.append("\n           dijit.byId('newPassword').focus();");
            toReturn.append("\n           return false;");
            toReturn.append("\n       }");
            toReturn.append("\n}");
            toReturn.append("\n</script>");

            toReturn.append("\n<form id=\"");
            toReturn.append(User.swb_User.getClassName());
            toReturn.append("/edit\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\"");
            toReturn.append(url);
            toReturn.append("\" method=\"post\" onsubmit=\"return jsValidate(this);\">");
            toReturn.append("\n<table>");
            toReturn.append("\n<tbody>");
            toReturn.append("\n<tr>");
            toReturn.append("\n<td align=\"right\"><label>");
            toReturn.append("lblCurPassword");
            toReturn.append("</label></td>");
            toReturn.append("\n<td><input type=\"password\" name=\"curPassword\" id=\"curPassword\" ");
            toReturn.append("size=\"30\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" ");
            toReturn.append("promptMessage=\"");
            toReturn.append("userCurPassword");
            toReturn.append("\" ");
            toReturn.append(" invalidMessage=\"");
            toReturn.append("userCurPassword");
            toReturn.append("\" isValid=\"if(this.textbox.value == '') ");
            toReturn.append("{return false;} else { return true;}\" ");
            toReturn.append("trim=\"true\" /></td>");
            toReturn.append("\n</tr>");
            toReturn.append("\n<tr>");
            toReturn.append("\n<td align=\"right\"><label>");
            toReturn.append("lblNewPassword");
            toReturn.append("</label></td>");
            toReturn.append("\n<td><input type=\"password\" name=\"newPassword\" ");
            toReturn.append("id=\"newPassword\" size=\"30\" dojoType=\"dijit.form.ValidationTextBox\" ");
            toReturn.append("required=\"true\" ");
            toReturn.append("promptMessage=\"");
            toReturn.append("userNewPassword");
            toReturn.append("\" invalidMessage=\"");
            toReturn.append("userNewPassword");
            toReturn.append("\" isValid=\"if(this.textbox.value == '') {return false;");
            toReturn.append("} else { return true;}\" trim=\"true\" ></td>");
            toReturn.append("\n</tr>");
            toReturn.append("\n<tr>");
            toReturn.append("\n<td align=\"right\"><label>");
            toReturn.append("lblConfirmPassword");
            toReturn.append("</label></td>");
            toReturn.append("\n<td><input type=\"password\" name=\"rePassword\" ");
            toReturn.append("id=\"rePassword\" size=\"30\"  dojoType=\"dijit.form.ValidationTextBox\" ");
            toReturn.append("required=\"true\" ");
            toReturn.append("promptMessage=\"");
            toReturn.append("userRePassword");
            toReturn.append("\" invalidMessage=\"");
            toReturn.append("userRePassword");
            toReturn.append("\" isValid=\"if(this.textbox.value == '') {return false;} ");
            toReturn.append("else { return true;}\" trim=\"true\" ></td>");
            toReturn.append("\n</tr>");
            toReturn.append("\n</tbody>");
            toReturn.append("\n<tbody>");
            toReturn.append("\n<tr>");
            toReturn.append("\n<td align=\"center\" colspan=\"2\">");
            toReturn.append("\n<button dojoType='dijit.form.Button' type=\"submit\">");
            toReturn.append("lbl_Save");
            toReturn.append("</button>\n");
            toReturn.append("\n<button dojoType='dijit.form.Button' ");
            toReturn.append("onclick=\"dijit.byId('swbDialog').hide();\">");
            toReturn.append("lbl_Cancel");
            toReturn.append("</button>\n");
            toReturn.append("\n</td>");
            toReturn.append("\n</tr>");
            toReturn.append("\n</tbody>");

            toReturn.append("\n</table>");
            toReturn.append("\n</form>");
            System.out.println(toReturn.toString());

        }
    }
}
