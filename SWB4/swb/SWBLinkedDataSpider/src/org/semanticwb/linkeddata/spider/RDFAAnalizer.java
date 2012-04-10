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


/**
 *
 * @author victor.lorenzana
 */
public class RDFAAnalizer
{

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
                    throw new SpiderException("The xml tag was not found, url: "+spider.getURL(), spider);
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
                    if (!doc_type.toString().equalsIgnoreCase(DOCTYPE_RFDA))
                    {
                        throw new SpiderException("The document is not a rdfa document, url: "+spider.getURL(), spider);
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
            return tok.getStringValue().toString();
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
            String value = tag.getParam(name);
            for (String att : attributes)
            {
                if (att.equals(name))
                {
                    if (name.equals("about"))
                    {
                        return true;
                    }
                    else
                    {
                        for (String _prefix : prefix.keySet())
                        {
                            String tofind = _prefix + ":";
                            if (value.startsWith(tofind))
                            {
                                return true;
                            }
                        }
                    }

                }
            }
        }
        return false;
    }

    private AttributeRDFA getAttribute(HtmlTag tag)
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
    }

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

    private void onPred(URI pred)
    {
        try
        {
            SpiderPred _spider = new SpiderPred(pred.toURL());
            for(SpiderEventListener listener : spider.getListeners())
            {
                _spider.addSpiderListener(listener);
            }
            _spider.addSpiderListener(_spider);
            _spider.get();
        }
        catch (Exception e)
        {
        }
    }

    private void procesaTag(HTMLElement tag)
    {
        if (hasAttribute(tag.tag))
        {
            AttributeRDFA att = getAttribute(tag.tag);
            if (("rel".equals(att.attribute) || "rev".equals(att.attribute)) && (tag.tag.getTagString().equals("link") || tag.tag.getTagString().equals("a")))
            {
                try
                {
                    URI obj = new URI(tag.tag.getParam("href"));
                    String spred = att.value.replace(att.prefix + ":", prefix.get(att.prefix));
                    URI pred = new URI(spred);
                    onPred(pred);
                    String _lang = tag.tag.getParam("xml:lang");
                    spider.visit(obj);
                    spider.fireEventnewTriple(suj, pred, obj.toString(), spider, _lang);
                }
                catch (URISyntaxException e)
                {
                    spider.fireError(e);
                }
            }
            if (att.attribute.equals("typeof"))
            {
                String _lang = tag.tag.getParam("xml:lang");
                String content = att.value;
            }
            if (att.attribute.equals("about"))
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
                        spider.fireVisit(suj);

                    }
                    else
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
                                        onPred(pred);
                                        spider.visit(obj);
                                        String _lang = tag.tag.getParam("xml:lang");
                                        spider.fireEventnewTriple(suj, pred, obj.toString(), spider, _lang);
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
            if (att.attribute.equals("property"))
            {
                String content = tag.tag.getParam("content");
                if (content == null)
                {
                    try
                    {
                        String obj = nextText();
                        String spred = att.value.replace(att.prefix + ":", prefix.get(att.prefix));
                        URI pred = new URI(spred);
                        onPred(pred);
                        String _lang = tag.tag.getParam("xml:lang");
                        spider.fireEventnewTriple(suj, pred, obj.toString(), spider, _lang);
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
                        onPred(pred);
                        String _lang = tag.tag.getParam("xml:lang");
                        spider.fireEventnewTriple(suj, pred, obj.toString(), spider, _lang);
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
