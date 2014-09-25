package org.semanticwb.bsc.formelement;

import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.UserRepository;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * Check box accessible only with permission once marked 
   */
public class CheckboxReadOnly extends org.semanticwb.bsc.formelement.base.CheckboxReadOnlyBase 
{
    public CheckboxReadOnly(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }

        boolean DOJO   = false;

        if (type.equals("dojo")) {
            DOJO = true;
        }

        StringBuilder   ret         = new StringBuilder();
        String         name         = propName;
        String         label        = prop.getDisplayName(lang);
        SemanticObject sobj         = prop.getDisplayProperty();
        boolean        required     = prop.isRequired();
        String         pmsg         = null;
        String         imsg         = null;
        boolean        isDisabled   = false;
        String trueTitle            = getDisplayTrueTitle(lang);
        String falseTitle           = getDisplayFalseTitle(lang);
        String displayType          = getDisplayType();

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);
            pmsg         = dobj.getPromptMessage();
            imsg         = dobj.getInvalidMessage();
            isDisabled   = dobj.isDisabled();
        }

        String disabled = "";

        if (DOJO) {
            if (imsg == null) {
                if (required) {
                    imsg = label + " es requerido.";

                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                } else {
                    imsg = "Dato invalido.";

                    if (lang.equals("en")) {
                        imsg = "Invalid data.";
                    }
                }
            }

            if (pmsg == null) {
                pmsg = "Captura " + label + ".";

                if (lang.equals("en")) {
                    pmsg = "Enter " + label + ".";
                }
            }
        }

        if (isDisabled) {
            disabled = " disabled=\"disabled\"";
        }

        if (prop.isDataTypeProperty() && prop.isBoolean()) {
            if (prop.isBoolean()) {
                String  checked = "";
                boolean value   = false;
                String  aux     = request.getParameter(propName);

                if (aux != null) {
                    value = true;
                    if (aux.equals("false")) {
                        value = false;
                    }
                } else {
                    value = obj.getBooleanProperty(prop);
                }

                if (value) {
                    checked = "checked=\"checked\"";
                }

                if (displayType.equals("checkbox")) {
                    
System.out.println("\n\nCheckboxreadonly");
SemanticModel model = getModel();
System.out.println("model="+model);
SWBModel m = (SWBModel) model.getModelObject().createGenericInstance();
System.out.println("m="+m);
if(m.getParentWebSite()!=null) {
    m = m.getParentWebSite();
}                    
User user;
user = SWBContext.getSessionUser(SWBContext.USERREPOSITORY_ADMIN);
System.out.println("user="+user);
System.out.println("admin rep id="+SWBContext.getAdminRepository().getId());


                    
                    ret.append("<input type=\"checkbox\" id_=\"").append(name).append("\" name=\"").append(name).append("\" ").append(checked);
                    if (DOJO) {
                        ret.append(" dojoType=\"dijit.form.CheckBox\"");
                        if(required)ret.append(" required=\"").append(required).append("\"");
                        ret.append(" promptMessage=\"").append(pmsg).append("\"");
                        ret.append(" invalidMessage=\"").append(imsg).append("\"");
                    }
                    //ret.append(disabled);
                    if (   !filterObject(request, sobj, user.getSemanticObject(), prop, propName, type, mode, lang) ||  isDisabled || mode.equals("view")) {
                        ret.append(" disabled=\"disabled\"");
                    }
                    ret.append("/>");
                } else if (displayType.equals("select")) {
                    ret.append("<select id_=\"").append(name).append("\" name=\"").append(name).append("\" ");
                    if (DOJO) {
                        ret.append(" dojoType=\"dijit.form.FilteringSelect\"");
                        if(required)ret.append(" required=\"").append(required).append("\"");
                        ret.append(" promptMessage=\"").append(pmsg).append("\"");
                        ret.append(" invalidMessage=\"").append(imsg).append("\"");
                    }
                    ret.append(disabled);
                    if (mode.equals("view")) {
                        ret.append(" disabled=\"disabled\"");
                    }
                    ret.append("/>");
                    ret.append("<option value=\"true\"").append(value?"selected":"").append(" >").append(trueTitle).append("</option>");
                    ret.append("<option value=\"false\"").append(!value?"selected":"").append(" >").append(falseTitle).append("</option>");
                    ret.append("</select>");
                } else if (displayType.equals("radio")) {
                    ret.append("<input type=\"radio\" id_=\"").append(name).append("\" id=\"").append(name).append("_True\" name=\"").append(name).append("\" ").append(value?"checked=\"checked\"":"").append(" value=\"true\"");
                    if (DOJO) {
                        ret.append(" dojoType=\"dijit.form.RadioButton\"");
                        if(required)ret.append(" required=\"").append(required).append("\"");
                        ret.append(" promptMessage=\"").append(pmsg).append("\"");
                        ret.append(" invalidMessage=\"").append(imsg).append("\"");
                    }
                    ret.append(disabled);
                    if (mode.equals("view")) {
                        ret.append(" disabled=\"disabled\"");
                    }
                    ret.append("/>");
                    ret.append("<label for=\"").append(name).append("_True\">").append(trueTitle).append("</label> ");
                    
                    ret.append("<input type=\"radio\" id_=\"").append(name).append("\" id=\"").append(name).append("_False\" name=\"").append(name).append("\" ").append(!value?"checked=\"checked\"":"").append(" value=\"false\"");
                    if (DOJO) {
                        ret.append(" dojoType=\"dijit.form.RadioButton\"");
                        if(required)ret.append(" required=\"").append(required).append("\"");
                        ret.append(" promptMessage=\"").append(pmsg).append("\"");
                        ret.append(" invalidMessage=\"").append(imsg).append("\"");
                    }
                    ret.append(disabled);
                    if (mode.equals("view")) {
                        ret.append(" disabled=\"disabled\"");
                    }
                    ret.append("/>");
                    ret.append("<label for=\"").append(name).append("_False\">").append(falseTitle).append("</label>");
                }
            } else {
                String value = request.getParameter(propName);

                if (value == null) {
                    value = obj.getProperty(prop);
                }

                if (value == null) {
                    value = "";
                }

                //System.out.print(value);
                value=value.replace("\"", "&quot;");
                //System.out.println(" "+value);

                if (mode.equals("edit") || mode.equals("create") || mode.equals("filter")) {
                    ret.append("<input _id=\"").append(name).append("\" name=\"").append(name).append("\" value=\"").append(value + "\"");

                    if (DOJO) {
                        ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
                    }

                    if (!mode.equals("filter") || DOJO) {
                        if(required)ret.append(" required=\"").append(required).append("\"");
                    }

                    // + " propercase=\"true\""
                    if (DOJO) {
                        ret.append(" promptMessage=\"").append(pmsg).append("\"");
                    }

                    if (DOJO) {
                        ret.append(" invalidMessage=\"").append(imsg).append("\"");
                    }

                    ret.append(" style=\"width:300px;\"");
                    ret.append(" ").append(getAttributes());

                    if (DOJO) {
                        ret.append(" trim=\"true\"");
                    }

                    ret.append(disabled);
                    ret.append("/>");
                } else if (mode.equals("view")) {
                    ret.append("<span _id=\"").append(name).append("\" name=\"").append(name).append("\">").append(value).append("</span>");
                }
            }
        }

        // System.out.println("ret:"+ret);
        return ret.toString();
    }
    
    public boolean filterObject(HttpServletRequest request, SemanticObject base_obj, SemanticObject filter_obj, SemanticProperty prop, String propName, String type, String mode, String lang) {        
        SWBModel m;
        m = (SWBModel) filter_obj.getModel().getModelObject().createGenericInstance();
        User filterUser;
        boolean hasUserGroup = false;
        boolean hasRole = false;
        boolean ret = true;
        //if (filter_obj != null) {
            filterUser = (User) filter_obj.createGenericInstance();
        //}
        
        //No se especificó un grupo o roles para filtrar
        if ((getReadonlyRoleIds()==null || getReadonlyRoleIds().isEmpty()) && (getReadonlyUserGroupIds()==null || getReadonlyUserGroupIds().isEmpty())) {
            hasUserGroup = true;
            hasRole = true;
        } else {
            //Revisar si tiene el grupo de usuarios especificado
            UserGroup filterUserGroup = ((UserRepository)m).getUserGroup(getReadonlyUserGroupIds());
            //UserGroup filterUserGroup = UserGroup.ClassMgr.getUserGroup(getFilterUserGroupId(), m);
            if (filterUser != null && filterUserGroup != null) {
                if (filterUser.hasUserGroup(filterUserGroup)) {
                    hasUserGroup = true;
                }
            }
            
            //Revisar si tiene alguno de los roles definidos
            if (getReadonlyRoleIds()!= null && getReadonlyRoleIds().indexOf("|") > -1) {
                StringTokenizer stk = new StringTokenizer(getReadonlyRoleIds(), "|");
                while (stk.hasMoreTokens()) {
                    String roleId = stk.nextToken();
                    //Role filterRole = Role.ClassMgr.getRole(roleId, m);
                    Role filterRole = ((UserRepository)m).getRole(roleId);
                    if (filterUser != null && filterRole != null) {
                        if (filterUser.hasRole(filterRole)) {
                            hasRole = true;
                            break;
                        }
                    }
                }
            } else {
                Role filterRole = ((UserRepository)m).getRole(getReadonlyRoleIds());
                if (filterUser != null && filterRole != null) {
                    if (filterUser.hasRole(filterRole)) {
                        hasRole = true;
                    }
                }
            }
        }
        
        if (hasUserGroup || hasRole) ret = false;
        return ret;
    }
}
