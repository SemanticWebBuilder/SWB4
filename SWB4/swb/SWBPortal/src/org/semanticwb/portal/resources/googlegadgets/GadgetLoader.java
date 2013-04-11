/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources.googlegadgets;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public class GadgetLoader
{

    private static Logger log = SWBUtils.getLogger(GoogleGadget.class);
    private int start = 1;
    private List<URL> gadgets = new ArrayList<URL>();
    private String GOOGLE_URL = "http://www.google.com/ig/directory?type=gadgets&start=";

    public GadgetLoader()
    {
    }

    public GadgetLoader(String url_google)
    {
        this.GOOGLE_URL = url_google;
    }
    public int getPage()
    {
        return start;
    }
    public int next()
    {
        start+=7;
        String urlTofind = GOOGLE_URL + start;
        try
        {
            URL url = new URL(urlTofind);
            String html = loadHTML(url);
            if (html == null)
            {
                return 0;
            }
            return parse(html);
        }
        catch (Exception e)
        {
            return 0;
        }

    }

    private int parseContent(String content)
    {
        String parameter = "url=";
        int pos = content.indexOf(parameter);
        if (pos != -1)
        {
            String surl = content.substring(pos + parameter.length());
            surl = URLDecoder.decode(surl);
            if (!surl.startsWith("http://"))
            {
                surl = "http://" + surl;
            }
            if (surl.endsWith(".xml"))
            {
                try
                {
                    URL url = new URL(surl);
                    System.out.println("url: " + url);
                    this.gadgets.add(url);
                    return 1;
                }
                catch (Exception e)
                {
                    log.error(e);

                    return 0;
                }
            }
            else
            {
                return 0;
            }
        }
        else
        {
            return 0;
        }
    }

    private int parseAnchor(String anchor)
    {
        String href = "href=\"";
        int pos = anchor.indexOf(href);
        if (pos != -1)
        {
            int pos2 = anchor.indexOf("\"", pos + href.length());
            if (pos2 != -1)
            {
                String content = anchor.substring(pos + href.length() - 1, pos2);
                content = content.replace('\"', ' ').trim();
                return parseContent(content);
            }
            else
            {
                return 0;
            }
        }
        else
        {
            return 0;
        }
    }

    private int parse(String html)
    {
        int parse = 0;
        String anchorToFind = "<a id=\"gadget_image_link";
        int pos = html.indexOf(anchorToFind);
        while (pos != -1)
        {
            int pos2 = html.indexOf(">", pos + anchorToFind.length());
            if (pos2 != -1)
            {
                String anchor = html.substring(pos, pos2 + 1);
                parse += parseAnchor(anchor);
            }
            pos = html.indexOf(anchorToFind, pos + anchorToFind.length());
        }
        return parse;
    }

    private String loadHTML(URL url)
    {
        try
        {
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String html = SWBUtils.IO.readInputStream(in, "utf-8"); //AFUtils.getInstance().readInputStream(in);
            return html;
        }
        catch (Exception e)
        {
            log.debug(e);
            return null;
        }
    }

    public URL[] getList()
    {
        return gadgets.toArray(new URL[gadgets.size()]);
    }
}
