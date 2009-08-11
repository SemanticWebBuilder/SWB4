/**  
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
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Statement;
import java.sql.Date;
import java.sql.Timestamp;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author Jei
 */
public class SemanticLiteral 
{
    private static Logger log=SWBUtils.getLogger(SemanticLiteral.class);

    Literal literal;
    String m_lang=null;
    Object m_obj=null;
            
    public SemanticLiteral(Statement stmt)
    {
        literal=stmt.getLiteral();
    }
    
    public SemanticLiteral(Literal literal)
    {
        this.literal=literal;
    }    

    public SemanticLiteral(Object value)
    {
        //System.out.println("SemanticLiteral:"+value);
        m_obj=value;
    }

    public SemanticLiteral(Object value, String lang)
    {
        //System.out.println("Val:"+value+" lang:"+lang);
        m_obj=value;
        m_lang=lang;
    }

    public static SemanticLiteral valueOf(SemanticProperty prop, String value)
    {
        SemanticLiteral ret=null;
        if (value != null)
        {
            //System.out.println("valueOf:"+value);
            if(prop.isString())
            {
                 ret=new SemanticLiteral(value);
            }else if(prop.isBoolean())
            {
                ret=new SemanticLiteral(Boolean.valueOf(value));
            }else if(prop.isDouble())
            {
                ret=new SemanticLiteral(Double.valueOf(value));
            }else if(prop.isFloat())
            {
                ret=new SemanticLiteral(Float.valueOf(value));
            }else if(prop.isInt())
            {
                ret=new SemanticLiteral(Integer.valueOf(value));
            }else if(prop.isLong())
            {
                ret=new SemanticLiteral(Long.valueOf(value));
            }else if(prop.isDate())
            {
                ret=new SemanticLiteral(value);
            }
        }
        return ret;
    }

    public Object getValue()
    {
        Object ret=null;
        if(literal!=null)
        {
            ret=literal.getValue();
        }else
        {
            ret=m_obj;
        }
        return ret;
    }
    
    public boolean getBoolean()
    {
        Boolean ret=null;
        if(literal!=null)
        {
            ret=literal.getBoolean();
        }else if(m_obj instanceof Boolean)
        {
            ret=(Boolean)m_obj;
        }
        if(ret==null)ret=false;
        return ret;
    }
    
    public String getString()
    {
        String ret=null;
        if(literal!=null)
        {
            ret=literal.getString();
        }else if(m_obj!=null)
        {
            ret=m_obj.toString();
        }
        return ret;
    }    

    public byte getByte()
    {
        Byte ret=null;
        if(literal!=null)
        {
            ret=literal.getByte();
        }else if(m_obj instanceof Byte)
        {
            ret=(Byte)m_obj;
        }
        if(ret==null)ret=0;
        return ret;
    }    
    
    public char getChar()
    {
        Character ret=null;
        if(literal!=null)
        {
            ret=literal.getChar();
        }else if(m_obj instanceof Character)
        {
            ret=(Character)m_obj;
        }
        if(ret==null)ret=0;
        return ret;
    }    
    
    public double getDouble()
    {
        Double ret=null;
        if(literal!=null)
        {
            ret=literal.getDouble();
        }else if(m_obj instanceof Double)
        {
            ret=(Double)m_obj;
        }
        if(ret==null)ret=0D;
        return ret;
    }    
    
    public float getFloat()
    {
        Float ret=null;
        if(literal!=null)
        {
            ret=literal.getFloat();
        }else if(m_obj instanceof Float)
        {
            ret=(Float)m_obj;
        }
        if(ret==null)ret=0F;
        return ret;
    }    

    public int getInt()
    {
        Integer ret=null;
        if(literal!=null)
        {
            ret=literal.getInt();
        }else if(m_obj instanceof Long)
        {
            ret=((Long)m_obj).intValue();
        }else if(m_obj instanceof Integer)
        {
            ret=(Integer)m_obj;
        }
        if(ret==null)ret=0;
        return ret;
    }    
    
    public short getShort()
    {
        Short ret=null;
        if(literal!=null)
        {
            ret=literal.getShort();
        }else if(m_obj instanceof Short)
        {
            ret=(Short)m_obj;
        }
        if(ret==null)ret=0;
        return ret;
    }    
    
    public String getLanguage()
    {
        String ret=null;
        if(literal!=null)
        {
            ret=literal.getLanguage();
        }else
        {
            ret=m_lang;
        }
        return ret;
    }     
    
    public long getLong()
    {
        Long ret=null;
        if(literal!=null)
        {
            ret=literal.getLong();
        }else if(m_obj instanceof Long)
        {
            ret=(Long)m_obj;
        }
        if(ret==null)ret=0L;
        return ret;
    }      

    public Date getDate()
    {
        Object obj=getValue();
        Date ret=null;
        if(obj instanceof Date)
        {
            ret=(Date)obj;
        }else
        {
            try
            {
                String aux=(String)obj;
                if(aux!=null)
                {
                    ret=new Date(SWBUtils.TEXT.iso8601DateParse(aux).getTime());
                }
            }catch(Exception e){log.error(e);}
        }
        return ret;
    }

    public Timestamp getDateTime()
    {
        Object obj=getValue();
        Timestamp ret=null;
        if(obj instanceof Timestamp)
        {
            ret=(Timestamp)obj;
        }else
        {
            try
            {
                String aux=(String)obj;
                if(aux!=null)
                {
                    ret=new Timestamp(SWBUtils.TEXT.iso8601DateParse(aux).getTime());
                }
            }catch(Exception e){log.error(e);}
        }
        return ret;
    }
}
