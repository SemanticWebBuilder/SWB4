package org.semanticwb.model;

import static org.semanticwb.model.base.XMLableBase.swb_xml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


   /**
   * Superclase de filtrado por secciones para recursos y usuarios 
   */
public class SectionFilter extends org.semanticwb.model.base.SectionFilterBase 
{
//    private Document m_dom=null;
    /** The m_filter. */
    private Document m_filter = null;
    
    /** The m_filternode. */
    private NodeList m_filternode = null;      
    
    
    public SectionFilter(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /** Getter for property filterMap.
     * @return Value of property filterMap.
     */
    public org.w3c.dom.NodeList getFilterNode() {
        Document aux = this.getSemanticObject().getDomProperty(swb_xml);
        if (aux != m_filter) {
            m_filter = aux;
            NodeList nl = aux.getElementsByTagName("topicmap");
            int n = nl.getLength();
            if (n > 0) {
                m_filternode = nl;
            } else {
                m_filternode = null;
            }
        }
        //System.out.println("getFilterNode:"+getURI()+" "+m_filternode);
        return m_filternode;
    }

    /**
     * org.semanticwb.model.Inheritable
     * 
     * @param topic the topic
     * @return true, if successful
     * @return
     */
    public boolean evalFilterMap(WebPage topic) {  
        //System.out.println("evalFilterMap:"+this+" "+topic);
        boolean negative=false;
        boolean ret = false;
        boolean hasSiteFilter=false;
        NodeList fi = getFilterNode();
        if (fi != null) {
            for (int x = 0; x < fi.getLength(); x++) 
            {
                Element el = (Element) fi.item(x);
                negative=(el.getAttribute("negative")!=null?el.getAttribute("negative").equals("true"):false);
                //System.out.println("evalFilterMap:"+topic.getWebSiteId()+"="+el.getAttribute("id"));
                if (topic.getWebSiteId().equals(el.getAttribute("id"))) 
                {
                    hasSiteFilter=true;
                    NodeList ti = el.getElementsByTagName("topic");
                    for (int y = 0; y < ti.getLength(); y++) {
                        Element eltp = (Element) ti.item(y);
                        WebPage atopic = topic.getWebSite().getWebPage(eltp.getAttribute("id"));
                        if (atopic != null) {
                            if (topic.equals(atopic)) {
                                ret = true;
                            } else if (eltp.getAttribute("childs").equals("true") && topic.isChildof(atopic))
                            {
                                ret = true;
                            }
                        }
                    }
                }
            }
            if(!hasSiteFilter)
            {
                ret = true;
                negative=false;
            }
        } else {
            ret = true;
        }
        
        //System.out.println("ret:"+(ret^negative));
                
        return ret^negative;
    }     
    
    
}
