/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.model;

import org.semanticwb.model.CalendarRef;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.UserGroupRef;


public class WrapperTaskWebPage extends org.semanticwb.process.model.base.WrapperTaskWebPageBase 
{
    public WrapperTaskWebPage(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public GenericIterator<Resource> listResources() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listResources();
        }
        return super.listResources();
    }

    @Override
    public boolean hasResource(Resource value) {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.hasResource(value);
        }
        return super.hasResource(value);
    }

    @Override
    public Resource getResource() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getResource();
        }
        return super.getResource();
    }


    @Override
    public boolean isValid() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.isValid();
        }
        return super.isValid();
    }

    @Override
    public GenericIterator<RoleRef> listRoleRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listRoleRefs();
        }
        return super.listRoleRefs();
    }

    @Override
    public GenericIterator<RoleRef> listInheritRoleRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listInheritRoleRefs();
        }
        return super.listInheritRoleRefs();
    }


    @Override
    public boolean hasRoleRef(RoleRef value) {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.hasRoleRef(value);
        }
        return super.hasRoleRef(value);
    }

    @Override
    public RoleRef getRoleRef() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getRoleRef();
        }
        return super.getRoleRef();
    }

    @Override
    public GenericIterator<CalendarRef> listCalendarRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listCalendarRefs();
        }
        return super.listCalendarRefs();
    }

    @Override
    public boolean hasCalendarRef(CalendarRef value) {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.hasCalendarRef(value);
        }
        return super.hasCalendarRef(value);
    }

    @Override
    public CalendarRef getCalendarRef() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getCalendarRef();
        }
        return super.getCalendarRef();
    }

    
    @Override
    public GenericIterator<RuleRef> listRuleRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listRuleRefs();
        }
        return super.listRuleRefs();
    }

    @Override
    public GenericIterator<RuleRef> listInheritRuleRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listInheritRuleRefs();
        }
        return super.listInheritRuleRefs();
    }

    @Override
    public boolean hasRuleRef(RuleRef value) {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.hasRuleRef(value);
        }
        return super.hasRuleRef(value);
    }

    @Override
    public RuleRef getRuleRef() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getRuleRef();
        }
        return super.getRuleRef();
    }


    @Override
    public GenericIterator<UserGroupRef> listUserGroupRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listUserGroupRefs();
        }
        return super.listUserGroupRefs();
    }

    @Override
    public GenericIterator<UserGroupRef> listInheritUserGroupRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listInheritUserGroupRefs();
        }
        return super.listInheritUserGroupRefs();
    }



    @Override
    public boolean hasUserGroupRef(UserGroupRef value) {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.hasUserGroupRef(value);
        }
        return super.hasUserGroupRef(value);
    }

    @Override
    public UserGroupRef getUserGroupRef() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getUserGroupRef();
        }
        return super.getUserGroupRef();
    }


    @Override
    public GenericIterator<TemplateRef> listTemplateRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listTemplateRefs();
        }
        return super.listTemplateRefs();
    }

    @Override
    public GenericIterator<TemplateRef> listInheritTemplateRefs() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.listInheritTemplateRefs();
        }
        return super.listInheritTemplateRefs();
    }

    @Override
    public boolean hasTemplateRef(TemplateRef value) {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.hasTemplateRef(value);
        }
        return super.hasTemplateRef(value);
    }

    @Override
    public TemplateRef getTemplateRef() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getTemplateRef();
        }
        return super.getTemplateRef();
    }

    @Override
    public String getDisplayName() {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getDisplayTitle(null);
        }
        return super.getDisplayName();
    }

    @Override
    public String getDisplayName(String lang)
    {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getDisplayTitle(lang);
        }
        return super.getDisplayName(lang);
    }

   @Override
    public String getDisplayTitle(String lang)
    {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getDisplayTitle(lang);
        }        
        return super.getDisplayTitle(lang);
    }
    
    @Override
    public String getTitle(String lang)
    {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getTitle(lang);
        }        
        return super.getTitle(lang);
    }
    
    @Override
    public String getTitle()
    {
        UserTask ut=getUserTask();
        if(ut!=null)
        {
            return ut.getTitle();
        }        
        return super.getTitle();
    }      
    
}
