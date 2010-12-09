/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.publish;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPortal;

/**
 *
 * @author victor.lorenzana
 */
public class RestManager
{

    private final String servlet;
    private static final Map<String, RestModule> modules = Collections.synchronizedMap(new HashMap<String, RestModule>());

    public static void addModule(String path, RestModule module)
    {
        modules.put(path, module);
    }
    public RestManager(String servlet)
    {
        this.servlet = servlet;
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String context = SWBPortal.getContextPath();
        String uri = request.getRequestURI();
        if (uri.startsWith(context))
        {
            uri = uri.substring(context.length()).trim();
        }
        int pos = uri.indexOf(".");
        if (pos == -1)
        {
            if (uri.startsWith("/"))
            {
                uri = uri.substring(1);
            }
            ArrayList<String> listPath = new ArrayList<String>();
            listPath.addAll(Arrays.asList(uri.split("/")));
            if (listPath.size()==1) // root
            {
                
            }
            else
            {
                String basePath=context+"/"+listPath.get(0);
                listPath.remove(0);
                String module=listPath.get(0);
                if(modules.containsKey(module))
                {
                    RestModule omodule=modules.get(module);
                    basePath+="/"+listPath.get(0);
                    listPath.remove(0);
                    omodule.service(request, response, servlet,listPath,basePath);
                }
                else
                {
                    response.sendError(404);
                }
            }
        }
    }
}
