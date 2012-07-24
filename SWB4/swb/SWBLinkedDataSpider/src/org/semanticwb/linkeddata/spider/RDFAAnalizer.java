/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public class RDFAAnalizer
{

    private static Logger log = SWBUtils.getLogger(Spider.class);
    private static URI type;

    static
    {
        URI _type = null;
        try
        {
            _type = new URI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        }
        catch (URISyntaxException e)
        {
            log.error(e);
        }
        type = _type;
    }
    private String[] attributes =
    {
        "typeof", "about", "property", "datatype", "rel", "rev"
    };
    private HashMap<String, String> prefix = new HashMap<String, String>();
    public static final String DOCTYPE_RFDA = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML+RDFa 1.0//EN\" \"http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd\">";
    private HtmlStreamTokenizer tok;
    private Spider spider;
    private String rowhtml;
    private String xmlhtml;
    private URI suj;
    private String doc_type;

    public RDFAAnalizer(String html, Spider spider, URI suj) throws SpiderException
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
                    doc_type = doc_type.replace("  ", " ");
                    if (!doc_type.toString().equalsIgnoreCase(DOCTYPE_RFDA))
                    {
                        throw new SpiderException("The document is not a rdfa document, url: " + spider.getURL(), spider);
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new SpiderException(e, spider);
        }

    }

    private String nextText()
    {
        try
        {
            int tt_type = tok.nextToken();
            if(tt_type==tok.TT_TEXT)
                return tok.getStringValue().toString();
            else
                return "";
        }
        catch (Exception e)
        {
            return null;
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

    private boolean hasAttribute(HtmlTag tag)
    {
        Enumeration names = tag.getParamNames();
        while (names.hasMoreElements())
        {
            String name = names.nextElement().toString();
            String value = tag.getParam(name);
            if (name.startsWith("xmlns:"))
            {
                String _prefix = name.substring(6);
                prefix.put(_prefix, value);
            }
        }
        names = tag.getParamNames();
        while (names.hasMoreElements())
        {
            String name = names.nextElement().toString();
            for (String att : attributes)
            {
                if (att.equals(name))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /*private AttributeRDFA getAttribute(HtmlTag tag)
    {
    Enumeration names = tag.getParamNames();
    while (names.hasMoreElements())
    {
    String name = names.nextElement().toString();
    String value = tag.getParam(name);
    for (String att : attributes)
    {
    if (att.equals(name))
    {
    if (name.equals("about"))
    {
    AttributeRDFA attret = new AttributeRDFA();
    attret.value = value;
    attret.attribute = name;
    return attret;
    }
    else
    {
    for (String _prefix : prefix.keySet())
    {
    String tofind = _prefix + ":";
    if (value.startsWith(tofind))
    {
    AttributeRDFA attret = new AttributeRDFA();
    attret.value = value;
    attret.attribute = name;
    attret.prefix = _prefix;
    return attret;
    }
    }
    }

    }
    }
    }
    return null;
    }*/
    private String getFragment(HtmlTag tag)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            int tt_type = tok.nextToken();
            while (tt_type == HtmlStreamTokenizer.TT_TEXT || tt_type == HtmlStreamTokenizer.TT_TAG || tt_type == HtmlStreamTokenizer.TT_COMMENT)
            {
                if (tt_type == HtmlStreamTokenizer.TT_EOF)
                {
                    break;
                }
                if (tt_type == HtmlStreamTokenizer.TT_TEXT)
                {
                    sb.append(tok.getRawString());
                }
                if (tt_type == HtmlStreamTokenizer.TT_TAG)
                {
                    String rowtag = tok.getRawString();
                    HtmlTag _tag = new HtmlTag();
                    try
                    {
                        tok.parseTag(tok.getStringValue(), _tag);
                        if (_tag.getTagString().equals(tag.getTagString()) && _tag.isEndTag())
                        {
                            break;
                        }
                        sb.append(rowtag);
                    }
                    catch (Exception e)
                    {
                        spider.fireError(e);
                    }
                }
                tt_type = tok.nextToken();
            }
        }
        catch (Exception e)
        {
            spider.fireError(e);
        }
        return sb.toString();
    }

    private void onPred(URI pred, Spider spider)
    {
        spider.onPred(pred);
    }

    private void procesaRel(HTMLElement tag, AttributeRDFA att)
    {
        if (att.prefix != null && tag.tag.hasParam("href"))
        {
            try
            {
                URI obj = new URI(tag.tag.getParam("href"));
                String spred = att.value.replace(att.prefix + ":", prefix.get(att.prefix));
                URI pred = new URI(spred);
                onPred(pred, spider);
                String _lang = tag.tag.getParam("xml:lang");
                spider.onNewSubject(obj,TYPE.OBJECT,spider);
                spider.onNewSubject(pred,TYPE.PREDICATE,spider);
                spider.fireEventnewTriple(suj, pred, obj, spider, _lang);
            }
            catch (URISyntaxException e)
            {
                spider.fireError(e);
            }
        }
    }

    private void procesaRev(HTMLElement tag, AttributeRDFA att)
    {
        if (att.prefix != null && tag.tag.hasParam("href"))
        {

            try
            {
                URI obj = new URI(tag.tag.getParam("href"));
                String spred = att.value.replace(att.prefix + ":", prefix.get(att.prefix));
                URI pred = new URI(spred);
                onPred(pred, spider);
                String _lang = tag.tag.getParam("xml:lang");
                spider.onNewSubject(pred,TYPE.PREDICATE,spider);
                spider.onNewSubject(obj,TYPE.OBJECT,spider);
                // relacion  inversa
                spider.fireEventnewTriple(obj, pred, suj, spider, _lang);
            }
            catch (URISyntaxException e)
            {
                spider.fireError(e);

            }
        }
    }

    private void procesaAbout(HTMLElement tag, AttributeRDFA att)
    {
        try
        {
            if (!att.value.equals(""))
            {
                URI _suj = new URI(att.value);
                String htmlfragment = getFragment(tag.tag);
                htmlfragment = this.xmlhtml + this.doc_type + this.rowhtml + htmlfragment + "</html>";
                RDFAAnalizer newAnalizer = new RDFAAnalizer(htmlfragment, spider, _suj);
                for (String _prefix : prefix.keySet())
                {
                    newAnalizer.prefix.put(_prefix, prefix.get(_prefix));

                }
                newAnalizer.start();
                spider.fireVisit(suj,TYPE.SUBJECT,spider);

            }
            else if (tag.tag.hasParam("resource"))
            {
                String resource = tag.tag.getParam("resource");
                String rel = tag.tag.getParam("rel");
                if (rel != null && !rel.equals(""))
                {
                    for (String _prefix : prefix.keySet())
                    {
                        if (rel.startsWith(_prefix + ":") && !resource.equals(""))
                        {
                            try
                            {
                                URI obj = new URI(resource);
                                String spred = rel.replace(_prefix + ":", prefix.get(_prefix));
                                URI pred = new URI(spred);
                                onPred(pred, spider);
                                spider.onNewSubject(obj,TYPE.OBJECT,spider);
                                spider.onNewSubject(pred,TYPE.PREDICATE,spider);
                                String _lang = tag.tag.getParam("xml:lang");
                                spider.fireEventnewTriple(suj, pred, obj, spider, _lang);
                                break;
                            }
                            catch (URISyntaxException e)
                            {
                                spider.fireError(e);
                            }
                        }
                    }
                }

            }

        }
        catch (Exception e)
        {
            spider.fireError(e);
        }
    }

    private void procesaTypeOf(HTMLElement tag, AttributeRDFA att)
    {
        // es un nievo item
        if (att.prefix != null)
        {
            if (tag.tag.hasParam("about"))
            {
                try
                {
                    String value = tag.tag.getParam("about");
                    if(value.startsWith("#"))
                    {
                        value=suj.toString()+value;
                    }
                    URI _suj = new URI(value);
                    URI obj = new URI(att.value.replace(att.prefix + ":", prefix.get(att.prefix)));
                    String _lang = tag.tag.getParam("xml:lang");
                    spider.onNewSubject(_suj,TYPE.SUBJECT,spider);
                    spider.onNewSubject(type,TYPE.PREDICATE,spider);
                    spider.onNewSubject(obj,TYPE.OBJECT,spider);

                    spider.fireEventnewTriple(_suj, type, obj, spider, _lang);

                }
                catch (URISyntaxException e)
                {
                    spider.fireError(e);
                }

            }
            else
            {
                System.out.println("debug");
            }
            /*try
            {
            URI type = new URI(att.value.replace(att.prefix + ":", prefix.get(att.prefix)));
            URI _suj = new URI(att.value);

            String htmlfragment = getFragment(tag.tag);
            htmlfragment = this.xmlhtml + this.doc_type + this.rowhtml + htmlfragment + "</html>";
            try
            {
            RDFAAnalizer newAnalizer = new RDFAAnalizer(htmlfragment, spider, _suj);
            for (String _prefix : prefix.keySet())
            {
            newAnalizer.prefix.put(_prefix, prefix.get(_prefix));

            }
            newAnalizer.start();
            spider.fireVisit(suj);
            }
            catch (SpiderException e)
            {
            spider.fireError(e);
            }
            }
            catch (URISyntaxException e)
            {
            spider.fireError(e);
            }*/
        }


    }

    private void procesaDataType(HTMLElement tag, AttributeRDFA att)
    {
        if (att.prefix != null)
        {
        }
    }

    private void procesaProperty(HTMLElement tag, AttributeRDFA att)
    {
        if (att.prefix != null)
        {
            String content = tag.tag.getParam("content");
            if (content == null)
            {
                try
                {
                    String obj = nextText();
                    String spred = att.value.replace(att.prefix + ":", prefix.get(att.prefix));
                    URI pred = new URI(spred);
                    onPred(pred, spider);
                    String _lang = tag.tag.getParam("xml:lang");
                    spider.onNewSubject(pred, TYPE.PREDICATE, spider);
                    spider.fireEventnewTriple(suj, pred, obj, spider, _lang);
                }
                catch (URISyntaxException e)
                {
                    spider.fireError(e);
                }
            }
            else
            {
                try
                {
                    String obj = content;
                    String spred = att.value.replace(att.prefix + ":", prefix.get(att.prefix));
                    URI pred = new URI(spred);
                    onPred(pred, spider);
                    String _lang = tag.tag.getParam("xml:lang");
                    spider.onNewSubject(pred, TYPE.PREDICATE, spider);
                    spider.fireEventnewTriple(suj, pred, obj, spider, _lang);
                }
                catch (URISyntaxException e)
                {
                    spider.fireError(e);
                }
            }
        }
    }

    private void procesaTag(HTMLElement tag)
    {
        if (hasAttribute(tag.tag))
        {
            int count = tag.tag.getParamCount();
            for (int i = 0; i < count; i++)
            {

                String name = tag.tag.getParamName(i);
                if (isAttribute(name))
                {

                    AttributeRDFA att = new AttributeRDFA();
                    att.attribute = name;
                    att.value = tag.tag.getParam(name);
                    int pos = att.value.indexOf(":");
                    if (pos != -1)
                    {
                        for (String _prefix : prefix.keySet())
                        {
                            if (att.value.startsWith(_prefix + ":"))
                            {
                                att.prefix = _prefix;
                                break;
                            }
                        }
                    }
                    if (name.equals("property"))
                    {
                        procesaProperty(tag, att);
                    }
                    else if ("rev".equals(att.attribute))
                    {
                        procesaRev(tag, att);
                    }
                    else if ("rel".equals(att.attribute))
                    {
                        procesaRel(tag, att);
                    }
                    else if (att.attribute.equals("about"))
                    {
                        procesaAbout(tag, att);
                    }
                    else if (att.attribute.equals("typeof"))
                    {
                        procesaTypeOf(tag, att);
                    }
                    else if (att.attribute.equals("datatype"))
                    {
                        procesaDataType(tag, att);
                    }
                }
            }
        }


    }

    private boolean isAttribute(String name)
    {
        for (String _name : attributes)
        {
            if (_name.equals(name))
            {
                return true;
            }
        }
        return false;
    }
}
