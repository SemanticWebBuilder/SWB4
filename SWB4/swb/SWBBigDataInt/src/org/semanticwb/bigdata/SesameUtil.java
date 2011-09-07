/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
