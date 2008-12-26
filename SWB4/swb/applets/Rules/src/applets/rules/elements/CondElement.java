/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * CondElement.java
 *
 * Created on 17 de febrero de 2006, 12:23 PM
 */

package applets.rules.elements;

import applets.commons.WBTreeNode;
import applets.commons.WBXMLParser;
import java.awt.Color;
import java.awt.Graphics;
import applets.modeler.elements.GraphElement;
import applets.rules.GenericDialog;
import applets.rules.RuleApplet;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Locale;

/**
 *
 * @author  Juan Antonio
 */
public class CondElement extends GraphElement
{
    
    private String _attribute=null;
    private String _operator="";
    private String _value="";
    private String xmlAttr = null;
    private String toolTipText="";
    
    boolean _new=true;
    /** Creates a new instance of StartElement */
    public CondElement()
    {
        setTitle("Cond");
        setToolTipText("Condition");
        setNumChilds(0);
        setMinChilds(0);
        setMaxChilds(0);          
    }
    
    public void init()
    {
        setImage(getContainer().getApplet().getImage(getClass().getResource("/applets/rules/images/cond.gif")));        
        //setWidth(getImage().getWidth(getContainer().getApplet()));
        //setHeight(getImage().getWidth(getContainer().getApplet()));
        setWidth(84);
        setHeight(84);

    }    
    
    public void onMouseMoved(java.awt.event.MouseEvent evt)
    {
    }
    
    public void onMouseClicked(java.awt.event.MouseEvent evt)
    {
        if(evt.getClickCount()==2)
        {
            getDialog();
        }
    }
    
    public void getDialog()
    {
        try
        {
            xmlAttr = ((RuleApplet)this.getContainer().getApplet()).getXmlAttr();
            Locale locale = ((RuleApplet)this.getContainer().getApplet()).getLocale();
            
            String attFormFile = "AttForm";
            attFormFile= attFormFile + "_" + locale.getLanguage();
            String ruleTitle= java.util.ResourceBundle.getBundle("applets/rules/RuleApplet",locale).getString("Rule_def");
            //GenericDialog gd = new GenericDialog( new java.awt.Frame(),ruleTitle,"applets/rules/AttForm.xml",xmlAttr,_attribute,_operator,_value);
            GenericDialog gd = new GenericDialog( new java.awt.Frame(),ruleTitle,"applets/rules/"+attFormFile+".xml",xmlAttr,_attribute,_operator,_value);
            
            gd.setLocation(((RuleApplet)this.getContainer().getApplet()).getLocationOnScreen());
            WBXMLParser parser = new WBXMLParser();
            WBTreeNode treenode = parser.parse(xmlAttr);
            
            WBTreeNode nodo = treenode.getNodebyName("attributes");
            Iterator iteNode = nodo.getNodesbyName("attribute");
            String tipoAttr="select";
            while(iteNode.hasNext())
            {
                WBTreeNode nodeAttr = (WBTreeNode) iteNode.next();
                String nombre = nodeAttr.getAttribute("name");
                if(null!=_attribute&&nombre.equals(_attribute))
                {
                    tipoAttr = nodeAttr.getAttribute("type");
                    break;
                }
            }
            
            if(null!=_attribute)
                gd.setFieldValue("caracteristica", _attribute);
            if(null!=_operator)
                gd.setFieldValue("operador", _operator);
            if(null!=_value)
            {
                
                
                if("select".equals(tipoAttr))
                    gd.setFieldValue("valor", _value);
                else
                    gd.setFieldValue("valortxt", _value);
                
            }
            
            String modo =  ((RuleApplet)this.getContainer().getApplet()).getAct();
            int viewMode = ((RuleApplet)this.getContainer().getApplet()).getModeView();
            
            if(null!=modo&&"add".equals(modo))
            {
                viewMode=0;
            }
            else if(null!=modo&&"edit".equals(modo))
            {
                viewMode=1;
            }
            else if((null!=modo&&"info".equals(modo))||null==modo)
            {
                viewMode=2;
            }
            gd.displayDialog(300, 280, viewMode);
            
            if(null!=gd.getReturnValue("caracteristica")&&null!=gd.getReturnValue("operador")&&null!=gd.getReturnValue("valor"))
            {
                
                _attribute    = gd.getReturnValue("caracteristica");
                _operator    = gd.getReturnValue("operador");
                if("equal".equals(_operator)) _operator="=";
                if("notequal".equals(_operator)) _operator="!=";
                _value    = gd.getReturnValue("valor");
                if("TEXT".equals(_value))
                {
                    _value    = gd.getReturnValue("valortxt");
                }
                getCondExpression(xmlAttr);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }
    }
    
    public void onMouseEntered(java.awt.event.MouseEvent evt)
    {
    }
    
    public void onMouseExited(java.awt.event.MouseEvent evt)
    {
        
    }
    
    public void onIDECreated()
    {
        getDialog();        
    }
    
    public void endDrag(Point p)
    {
    }
    
    /**
     * Getter for property _attribute.
     * @return Value of property _attribute.
     */
    public java.lang.String get_attribute()
    {
        return _attribute;
    }
    
    /**
     * Setter for property _attribute.
     * @param _attribute New value of property _attribute.
     */
    public void set_attribute(java.lang.String _attribute)
    {
        this._attribute = _attribute;
    }
    
    /**
     * Getter for property _operator.
     * @return Value of property _operator.
     */
    public java.lang.String get_operator()
    {
        return _operator;
    }
    
    /**
     * Setter for property _operator.
     * @param _operator New value of property _operator.
     */
    public void set_operator(java.lang.String _operator)
    {
        this._operator = _operator;
    }
    
    /**
     * Getter for property _value.
     * @return Value of property _value.
     */
    public java.lang.String get_value()
    {
        return _value;
    }
    
    /**
     * Setter for property _value.
     * @param _value New value of property _value.
     */
    public void set_value(java.lang.String _value)
    {
        this._value = _value;
    }
    
    public void getCondExpression(String xmlAttr)
    {

        WBXMLParser parser = new WBXMLParser();
        WBTreeNode treenode = parser.parse(xmlAttr);
        
        WBTreeNode nodo = treenode.getNodebyName("attributes");
        Iterator iteNode = nodo.getNodesbyName("attribute");
        String tipoAttr="select";
        while(iteNode.hasNext())
        {
            WBTreeNode nodeAttr = (WBTreeNode) iteNode.next();
            String nombre = nodeAttr.getAttribute("name");
            if(null!=_attribute&&nombre.equals(_attribute))
            {
                tipoAttr = nodeAttr.getAttribute("type");
                toolTipText = nodeAttr.getAttribute("title");
                WBTreeNode nodeOper = nodeAttr.getNodebyName("operators");
                Iterator iteOper = nodeOper.getNodesbyName("operator");
                while(iteOper.hasNext())
                {
                    WBTreeNode nodeOperator = (WBTreeNode) iteOper.next();
                    String nombreOperator = nodeOperator.getAttribute("value");
                    
                    if(null!=_operator&&nombreOperator.equals(_operator))
                    {
                        toolTipText = toolTipText + " " + nodeOperator.getAttribute("title");
                        if("select".equals(tipoAttr))
                        {
                            WBTreeNode nodeVals = nodeAttr.getNodebyName("catalog");
                            Iterator iteVals = nodeVals.getNodesbyName("option");
                            while(iteVals.hasNext())
                            {
                                WBTreeNode nodeValue= (WBTreeNode) iteVals.next();
                                String nombreValor = nodeValue.getAttribute("value");
                                if(null!=_value && nombreValor.equals(_value))
                                {
                                    toolTipText = toolTipText + " " + nodeValue.getAttribute("title");
                                }
                            }
                        }
                        else
                        {
                            toolTipText = toolTipText + " " + _value;
                        }
                    }
                }
                break;
            }
        }
        setToolTipText(toolTipText);
    }
    
    public String getToolTipText()
    {
        String ret=getToolTipText("<html><font face='Verdana' size='3'>","&nbsp;&nbsp;&nbsp;&nbsp;","</font><html>");
        //System.out.println(ret);
        return ret;
    }
    
    public java.lang.String getToolTipText(String pre, String intr, String pos)
    {
        String s=super.getToolTipText();
        StringBuffer ret=new StringBuffer();
        ret.append(pre);
        ret.append("-"+s);
        ret.append(pos);
        return ret.toString();
    }
    
}
