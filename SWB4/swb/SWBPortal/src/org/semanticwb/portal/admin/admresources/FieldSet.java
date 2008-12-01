/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.admresources;

import org.semanticwb.portal.admin.admresources.lib.*;
import org.w3c.dom.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
/**
 *
 * @author jorge.jimenez
 */
public class FieldSet extends WBContainerFE
{
    private static Logger log = SWBUtils.getLogger(FieldSet.class);
    
    private String legend=null;
    protected Node tag=null;
    
    public FieldSet(String id) {
        this.id=id;
    }
    
    /** Creates a new instwance with the default parameters */
    public FieldSet(Node tag){
        this.tag=tag;
        setAttributes();
        createObjs();
    }
    
    public void setLeyend(String legend){
        this.legend=legend;
    }
    
    public String getLeyend(){
        return legend;
    }
    
    
    
    @Override
     public void setAttributes(){
        if(tag!=null){
            NamedNodeMap nnodemap=tag.getAttributes();
            if(nnodemap.getLength()>0){
                for(int i=0;i<nnodemap.getLength();i++){
                    String attrName=nnodemap.item(i).getNodeName();
                    String attrValue=nnodemap.item(i).getNodeValue();
                    if(attrValue!=null && !attrValue.equals("")){
                        //defecto
                        if(attrName.equalsIgnoreCase("id")) {
                            id=attrValue;
                        }
                        else if(attrName.equalsIgnoreCase("style")) {
                            style=attrValue;
                        }
                        else if(attrName.equalsIgnoreCase("class")) {
                            styleclass=attrValue;
                        }
                        else if(attrName.equalsIgnoreCase("moreattr")) {
                            moreattr=attrValue;
                        }
                        //propios
                        else if(attrName.equalsIgnoreCase("legend")) {
                            legend=attrValue;
                        }
                    }
                }
            }
        }
    }
    
    /**
    * Crea objetos html de acuerdo a tags del xml de la administraci�n de los recursos
    * Creates html objects according with the tags of xml admin resources
    */ 
    private void createObjs(){
        if(tag!=null){
            NodeList ndlchilds = tag.getChildNodes();
            for(int i=0;i<ndlchilds.getLength(); i++){
                //TODO
            }
        }
    }
    
}
