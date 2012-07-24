/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.StringReader;
import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;

/**
 *
 * @author victor.lorenzana
 */
public class RDDLAnalizer
{

    public static final String DOCTYPE_RDDL = "<!DOCTYPE html PUBLIC \"-//XML-DEV//DTD XHTML RDDL 1.0//EN\" \"http://www.w3.org/2001/rddl/rddl-xhtml.dtd\" >";
    public static final String RDDL_NAMESPACE = "http://www.rddl.org/";
    private Spider spider;
    private URI suj;
    private HtmlStreamTokenizer tok;
    private HashMap<String, String> prefix = new HashMap<String, String>();
    private String xmlhtml;
    private String doc_type;
    private String rowhtml;

    public RDDLAnalizer(String html, Spider spider, URI suj) throws SpiderException
    {


        this.suj = suj;
        tok = new HtmlStreamTokenizer(new StringReader(html));
        this.spider = spider;
        try
        {
            int tt_type = tok.nextToken();
            if (tt_type == HtmlStreamTokenizer.TT_TAG)
            {
                HtmlTag tag = new HtmlTag();
                tok.parseTag(tok.getStringValue(), tag);
                if (!tag.getTagString().equals("?xml"))
                {
                    throw new SpiderException("The xml tag was not found, url: " + spider.getURL(), spider);
                }
                xmlhtml = tok.getRawString();
                tt_type = tok.nextToken();
                while (tt_type == HtmlStreamTokenizer.TT_TEXT && tok.getStringValue().toString().equals("\n"))
                {
                    tt_type = tok.nextToken();
                }
                if (tt_type == HtmlStreamTokenizer.TT_BANGTAG)
                {
                    doc_type = "<!" + tok.getStringValue() + ">";
                    doc_type=doc_type.replace("  ", " ");
                    if (!doc_type.toString().equalsIgnoreCase(DOCTYPE_RDDL))
                    {
                        throw new SpiderException("The document is not a rddl document, url: " + spider.getURL(), spider);
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new SpiderException(e, spider);
        }
    }

    public void start()
    {
        HtmlTag html = new HtmlTag();
        try
        {
            int tt_type = tok.nextToken();
            while (tt_type != HtmlStreamTokenizer.TT_TAG)
            {
                if (tt_type == HtmlStreamTokenizer.TT_EOF)
                {
                    return;
                }
                tt_type = tok.nextToken();
            }
            tok.parseTag(tok.getStringValue(), html);
            if (html.getTagString().equalsIgnoreCase("html"))
            {
                rowhtml = tok.getRawString();
                Enumeration names = html.getParamNames();
                while (names.hasMoreElements())
                {
                    String name = names.nextElement().toString();
                    String value = html.getParam(name);
                    int pos = name.indexOf(":");
                    if (pos != -1)
                    {
                        name = name.substring(pos + 1);
                    }
                    prefix.put(name, value);
                }
            }
            HTMLElement htmlElement = new HTMLElement();
            htmlElement.tag = html;
            HTMLElement tag = nextTag(htmlElement);
            while (tag != null)
            {
                procesaTag(tag);
                tag = nextTag(htmlElement);
            }
        }
        catch (Exception e)
        {
            spider.fireError(e);
        }

    }

    private HTMLElement nextTag(HTMLElement html)
    {
        HTMLElement element = new HTMLElement();
        element.parent = html;
        HtmlTag tag = new HtmlTag();
        try
        {
            int tt_type = tok.nextToken();
            while (tt_type == HtmlStreamTokenizer.TT_TEXT && tok.getStringValue().toString().equals("\n"))
            {
                tt_type = tok.nextToken();
            }
            while (tt_type != HtmlStreamTokenizer.TT_TAG)
            {
                if (tt_type == HtmlStreamTokenizer.TT_EOF)
                {
                    return null;
                }
                tt_type = tok.nextToken();
            }
            tok.parseTag(tok.getStringValue(), tag);
            element.tag = tag;
        }
        catch (Exception e)
        {
            return null;
        }

        return element;
    }

    private void onPred(URI pred,Spider spider)
    {
        spider.onPred(pred);
        
    }
    private void procesaTag(HTMLElement tag)
    {

        String rddl_prefix = null;
        for (String _prefix : prefix.keySet())
        {
            String ns = prefix.get(_prefix);
            if (ns.equals(RDDL_NAMESPACE))
            {
                rddl_prefix = _prefix;
            }
        }
        if (rddl_prefix != null)
        {
            String resourceElementName = rddl_prefix + ":resource";
            if (tag.tag.getTagString().equals(resourceElementName))
            {
                //String id=tag.tag.getParam("id");
                for (String _prefix : prefix.keySet())
                {
                    String namespace = prefix.get(_prefix);
                    Enumeration params = tag.tag.getParamNames();
                    while (params.hasMoreElements())
                    {
                        String paramName = params.nextElement().toString();
                        if (paramName != null && paramName.startsWith(_prefix + ":"))
                        {
                            String obj = tag.tag.getParam(paramName);
                            try
                            {
                                if (!namespace.endsWith("#"))
                                {
                                    namespace += "#";
                                }
                                URI pred = new URI(paramName.replace(_prefix + ":", namespace));
                                onPred(pred,spider);
                                String lang = tag.tag.getParam("xml:lang");
                                spider.onNewSubject(pred,TYPE.SUBJECT,spider);                                
                                spider.fireEventnewTriple(suj, pred, obj, spider, lang);
                            }
                            catch (Exception e)
                            {
                                spider.fireError(e);
                            }

                        }
                    }
                }
            }
        }
    }
}
