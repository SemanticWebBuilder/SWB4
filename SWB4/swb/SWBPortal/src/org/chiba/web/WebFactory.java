// Copyright 2001-2007 ChibaXForms GmbH
/*
 *
 *    Artistic License
 *
 *    Preamble
 *
 *    The intent of this document is to state the conditions under which a Package may be copied, such that
 *    the Copyright Holder maintains some semblance of artistic control over the development of the
 *    package, while giving the users of the package the right to use and distribute the Package in a
 *    more-or-less customary fashion, plus the right to make reasonable modifications.
 *
 *    Definitions:
 *
 *    "Package" refers to the collection of files distributed by the Copyright Holder, and derivatives
 *    of that collection of files created through textual modification.
 *
 *    "Standard Version" refers to such a Package if it has not been modified, or has been modified
 *    in accordance with the wishes of the Copyright Holder.
 *
 *    "Copyright Holder" is whoever is named in the copyright or copyrights for the package.
 *
 *    "You" is you, if you're thinking about copying or distributing this Package.
 *
 *    "Reasonable copying fee" is whatever you can justify on the basis of media cost, duplication
 *    charges, time of people involved, and so on. (You will not be required to justify it to the
 *    Copyright Holder, but only to the computing community at large as a market that must bear the
 *    fee.)
 *
 *    "Freely Available" means that no fee is charged for the item itself, though there may be fees
 *    involved in handling the item. It also means that recipients of the item may redistribute it under
 *    the same conditions they received it.
 *
 *    1. You may make and give away verbatim copies of the source form of the Standard Version of this
 *    Package without restriction, provided that you duplicate all of the original copyright notices and
 *    associated disclaimers.
 *
 *    2. You may apply bug fixes, portability fixes and other modifications derived from the Public Domain
 *    or from the Copyright Holder. A Package modified in such a way shall still be considered the
 *    Standard Version.
 *
 *    3. You may otherwise modify your copy of this Package in any way, provided that you insert a
 *    prominent notice in each changed file stating how and when you changed that file, and provided that
 *    you do at least ONE of the following:
 *
 *        a) place your modifications in the Public Domain or otherwise make them Freely
 *        Available, such as by posting said modifications to Usenet or an equivalent medium, or
 *        placing the modifications on a major archive site such as ftp.uu.net, or by allowing the
 *        Copyright Holder to include your modifications in the Standard Version of the Package.
 *
 *        b) use the modified Package only within your corporation or organization.
 *
 *        c) rename any non-standard executables so the names do not conflict with standard
 *        executables, which must also be provided, and provide a separate manual page for each
 *        non-standard executable that clearly documents how it differs from the Standard
 *        Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    4. You may distribute the programs of this Package in object code or executable form, provided that
 *    you do at least ONE of the following:
 *
 *        a) distribute a Standard Version of the executables and library files, together with
 *        instructions (in the manual page or equivalent) on where to get the Standard Version.
 *
 *        b) accompany the distribution with the machine-readable source of the Package with
 *        your modifications.
 *
 *        c) accompany any non-standard executables with their corresponding Standard Version
 *        executables, giving the non-standard executables non-standard names, and clearly
 *        documenting the differences in manual pages (or equivalent), together with instructions
 *        on where to get the Standard Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    5. You may charge a reasonable copying fee for any distribution of this Package. You may charge
 *    any fee you choose for support of this Package. You may not charge a fee for this Package itself.
 *    However, you may distribute this Package in aggregate with other (possibly commercial) programs as
 *    part of a larger (possibly commercial) software distribution provided that you do not advertise this
 *    Package as a product of your own.
 *
 *    6. The scripts and library files supplied as input to or produced as output from the programs of this
 *    Package do not automatically fall under the copyright of this Package, but belong to whomever
 *    generated them, and may be sold commercially, and may be aggregated with this Package.
 *
 *    7. C or perl subroutines supplied by you and linked into this Package shall not be considered part of
 *    this Package.
 *
 *    8. The name of the Copyright Holder may not be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *    9. THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 *    WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 *    MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 */

package org.chiba.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.chiba.web.session.XFormsSessionManager;
import org.chiba.web.session.impl.DefaultXFormsSessionManagerImpl;
import org.chiba.xml.xforms.config.Config;
import org.chiba.xml.xforms.config.XFormsConfigException;
import org.chiba.xml.xslt.TransformerService;
import org.chiba.xml.xslt.impl.CachingTransformerService;
import org.chiba.xml.xslt.impl.FileResourceResolver;

import javax.servlet.ServletContext;
import javax.xml.transform.TransformerFactory;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Joern Turner
 */
public class WebFactory {
    private static final Log LOGGER = LogFactory.getLog(WebFactory.class);
    public static final String CHIBA_CONFIG_PATH = "chiba.configfile";
    public static final String LOG_CONFIG = "log4j.file";
    public static final String CHIBA_SUBMISSION_RESPONSE = "chiba.submission.response";
    public static final String XSLT_CACHE_PROPERTY = "xslt.cache.enabled";
    public static final String XSLT_PATH_PROPERTY = "xslt.path";
    public static final String XSLT_DEFAULT_PROPERTY = "xslt.default";
    public static final String SESSION_MANAGER_PROPERTY = "sessionManager";
//    public static final String SESSION_IMPL_PROPERTY = "sessionClass";
    public static final String SESSION_CHECKING_PROPERTY = "sessionChecking";
    public static final String SESSION_TIMEOUT_PROPERTY = "sessionTimeout";
    public static final String PROCESSOR_BASE_PROPERTY = "defaultProcessorBase";
    public static final String UPLOADDIR_PROPERTY = "uploadDir";
    public static final String RELATIVE_URI_PROPERTY = "forms.uri.relative";
    public static final String SCRIPT_PATH_PROPERTY = "scriptPath";
    public static final String CSS_PATH_PROPERTY = "CSSPath";
    public static final String AJAX_AGENT_PROPERTY = "useragent.ajax.path";
    public static final String PLAINHTML_AGENT_PROPERTY = "useragent.plainhtml.path";
    public static final String ERROPAGE_PROPERTY = "error.page";

    public static final String XFORMS_NODE = "XFormsInputNode";
    public static final String XFORMS_INPUTSTREAM = "XFormsInputStream";
    public static final String XFORMS_INPUTSOURCE ="XFormsInputSource";
    public static final String XFORMS_URI = "XFormsInputURI";
    public static final String FORM_PARAM_NAME = "form";
    public static final String IGNORE_RESPONSEBODY = "filter.ignoreResponseBody";
    
    private ServletContext servletContext;
    private Config config;
    private XFormsSessionManager manager;
    public static final String SCRIPTED = "script.enabled";
    public static final String PARSE_RESPONSE_BODY  = "chiba.filter.parseResponseBody";
    public static final String IGNORE_RESPONSE_BODY  = "chiba.filter.ignoreResponseBody";


    public WebFactory(){
    }


    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public Config getConfig(){
        return this.config;
    }
    /**
     * reads the the context param 'chiba.configfile' to get the location of the configuration file. Then
     * the configuration will be initialized and stored within the servlet context. It may be accessed by using the constant key
     * AbstractChibaServlet.CHIBA_CONFIGURATION.
     *
     * @throws javax.servlet.ServletException if something goes terribly wrong with accessing the servlet context.
     */
    public void initConfiguration() throws XFormsConfigException {
        LOGGER.info("--------------- initing Chiba... ---------------");
       
        String configPath = servletContext.getInitParameter("chiba.configfile");
        if (configPath == null) {
            throw new XFormsConfigException("Parameter 'chiba.configfile' not specified in web.xml");
        }        

       this.config = Config.getInstance(resolvePath(configPath, servletContext));
    }

    /**
     * initializes a XSLT Transformer service. Currently an implementation of CachingTransformerService is
     * instanciated. Future versions may make this configurable.
     *
     * @throws XFormsConfigException a Config exception will occur in case there's no valid setting for XSLT_CACHE_PROPERTY,XSLT_DEFAULT_PROPERTY or
     * XSLT_PATH_PROPERTY
     */
    public void initTransformerService() throws XFormsConfigException {
        TransformerService transformerService = new CachingTransformerService(new FileResourceResolver());
        transformerService.setTransformerFactory(TransformerFactory.newInstance());

        boolean xsltCacheEnabled = Config.getInstance().getProperty(WebFactory.XSLT_CACHE_PROPERTY).equalsIgnoreCase("true");
        String xsltPath = Config.getInstance().getProperty(WebFactory.XSLT_PATH_PROPERTY);
        String xsltDefault = Config.getInstance().getProperty(WebFactory.XSLT_DEFAULT_PROPERTY);

        if(xsltCacheEnabled)
        {
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("initializing xslt cache");
            }

            // load default stylesheet
            try
            {
                
                URI uri = getXsltURI(xsltPath, xsltDefault);
                transformerService.getTransformer(uri);
            }
            catch(Exception e)
            {
                throw new XFormsConfigException(e);
            }
        }

        // store service in servlet context
        // todo: contemplate about transformer service thread-safety
        servletContext.setAttribute(TransformerService.class.getName(), transformerService);
    }

    public URI getXsltURI(String xsltPath, String xsltDefault) throws URISyntaxException {	
    	return new File(resolvePath(xsltPath, servletContext)).toURI().resolve(new URI(xsltDefault));
    }


    public void initLogging(Class theClass) throws XFormsConfigException {
        String logConfig = this.config.getProperty(WebFactory.LOG_CONFIG);
            
        DOMConfigurator.configure(resolvePath(logConfig, servletContext));
        Log logger = LogFactory.getLog(theClass);
        if (logger.isDebugEnabled()) {
            logger.debug("Logger initialized");
        }
    }

    public void initXFormsSessionManager() throws XFormsConfigException {
        String sessionManager = this.config.getProperty(WebFactory.SESSION_MANAGER_PROPERTY);
        int wipingInterval = Integer.parseInt(this.config.getProperty(WebFactory.SESSION_CHECKING_PROPERTY));
        int xformsSessionTimeout = Integer.parseInt(this.config.getProperty(WebFactory.SESSION_TIMEOUT_PROPERTY));

		manager = DefaultXFormsSessionManagerImpl.createXFormsSessionManager(sessionManager);
        manager.setInterval(wipingInterval);
        manager.setTimeout(xformsSessionTimeout);
        manager.init();
    }

    /**
     * allow absolute paths otherwise resolve relative to the servlet context
     * 
     * @param path XPath locationpath
     * @return the absolute path or path relative to the servlet context
     */
    public static final String resolvePath(String path, ServletContext servletContext)
    {
    	if(!new File(path).isAbsolute())
    		path = servletContext.getRealPath(path);
    	
    	return path;
    }
    
    public void destroyXFormsSessionManager() {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("cleanups allocated resources");
        }
        manager.destroy();
    }
}
