package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class ClassElement extends org.semanticwb.model.base.ClassElementBase 
{
    public ClassElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        if(type.equals("dojo"))
        {
            setAttribute("isValid", "return validateElement('"+prop.getName()+"','"+getValidateURL(obj, prop)+"',this.textbox.value);");
        }else
        {
            setAttribute("isValid", null);
        }
        return super.renderElement(request, obj, prop, type, mode, lang);
    }

    @Override
    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop) throws FormValidateException
    {
        super.validate(request, obj, prop);
        String value=request.getParameter(prop.getName());
        //System.out.println("validate:"+value);
        try
        {
            Class cls=Class.forName(value);
        }catch(ClassNotFoundException e)
        {
            throw new FormValidateException(getLocaleString("error","Clase no encontrada:")+value);
        }
    }



}
