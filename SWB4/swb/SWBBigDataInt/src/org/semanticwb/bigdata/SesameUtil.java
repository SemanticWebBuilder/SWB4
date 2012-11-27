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
package org.semanticwb.bigdata;

import com.hp.hpl.jena.datatypes.BaseDatatype;
import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.impl.LiteralLabel;
import com.hp.hpl.jena.graph.impl.LiteralLabelFactory;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.impl.LiteralImpl;
import org.openrdf.model.BNode;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.impl.ValueFactoryImpl;

/**
 *
 * @author jei
 */
public class SesameUtil {

    public static Value node2Value(Node node)
    {
        if(node==null)return null;
        if(node.isBlank())return new ValueFactoryImpl().createBNode(node.getBlankNodeId().toString());
        if(node.isURI())
        {
            if(node.getURI().length()==0)
            {
                //return null;
                return new ValueFactoryImpl().createBNode();
            }
            //System.out.println(node.getURI());
            return new ValueFactoryImpl().createURI(node.getURI());
        }
        if(node.isLiteral())
        {
            LiteralLabel l=node.getLiteral();
            String type=l.getDatatypeURI();
            String lang=l.language();
            if(type==null)
            {
                return new ValueFactoryImpl().createLiteral(l.getValue().toString(), lang);
            }
            return new ValueFactoryImpl().createLiteral(l.getValue().toString(), new URIImpl(type));
        }
        return null;
    }

    public static Node value2Node(Value value)
    {
        if(value==null)return null;
        if(value instanceof URI)return Node.createURI(value.stringValue());
        if(value instanceof Resource)return Node.createURI(value.stringValue());
        if(value instanceof BNode)return Node.createAnon(new AnonId(((BNode)value).getID()));
        //System.out.println("valueClass:"+value.getClass()+" "+value);
        if(value instanceof Literal)
        {
            Literal l=(Literal)value;
            //System.out.println("value:"+l+" "+l.stringValue()+" "+l.getLanguage()+" "+l.getDatatype());
            if(l.getDatatype()!=null)
            {
                //System.out.println("("+l.getDatatype().stringValue()+")");
                LiteralLabel ll = null;
                if(l.getDatatype().toString().endsWith("#boolean"))
                {
                    ll=LiteralLabelFactory.create( Boolean.parseBoolean(l.stringValue()));
                }else if(l.getDatatype().toString().endsWith("#character"))
                {
                    //ll=LiteralLabelFactory.create( Character.parseBoolean(l.stringValue()));
                }else if(l.getDatatype().toString().endsWith("#double"))
                {
                    ll=LiteralLabelFactory.create( Double.parseDouble(l.stringValue()));
                }else if(l.getDatatype().toString().endsWith("#float"))
                {
                    ll=LiteralLabelFactory.create( Float.parseFloat(l.stringValue()));
                }else if(l.getDatatype().toString().endsWith("#integer") || l.getDatatype().toString().endsWith("#int"))
                {
                    ll=LiteralLabelFactory.create( Integer.parseInt(l.stringValue()));
                }else if(l.getDatatype().toString().endsWith("#long"))
                {
                    ll=LiteralLabelFactory.create( Long.parseLong(l.stringValue()));
                }else if(l.getDatatype().toString().endsWith("#dateTime"))
                {
                    //ll=LiteralLabelFactory.create( Long.parseLong(l.stringValue()));
                }else if(l.getDatatype().toString().endsWith("#date"))
                {
                    //ll=LiteralLabelFactory.create( Long.parseLong(l.stringValue()));
                }
                if(ll!=null)
                {
                    Node n=Node.createLiteral( ll );
                    //Node n=Node.createLiteral(l.stringValue(), l.getLanguage(), new BaseDatatype(l.getDatatype().stringValue()));
                    //System.out.println(n+" "+n.getLiteralValue().getClass());
                    return n;
                }else
                {
                    return Node.createLiteral(l.stringValue(), l.getLanguage(), new BaseDatatype(l.getDatatype().stringValue()));
                }
            }
            return Node.createLiteral(l.stringValue(), l.getLanguage(), false);
        }
        return null;
    }


}
