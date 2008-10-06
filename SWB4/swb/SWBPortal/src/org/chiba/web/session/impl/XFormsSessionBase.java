// Copyright 2001-2007 ChibaXForms GmbH
/*
 *
 * Artistic License
 *
 * Preamble
 *
 * The intent of this document is to state the conditions under which a
 * Package may be copied, such that the Copyright Holder maintains some
 * semblance of artistic control over the development of the package, while
 * giving the users of the package the right to use and distribute the
 * Package in a more-or-less customary fashion, plus the right to make
 * reasonable modifications.
 *
 * Definitions:
 *
 * "Package" refers to the collection of files distributed by the Copyright
 * Holder, and derivatives of that collection of files created through
 * textual modification.
 *
 * "Standard Version" refers to such a Package if it has not been modified,
 * or has been modified in accordance with the wishes of the Copyright
 * Holder.
 *
 * "Copyright Holder" is whoever is named in the copyright or copyrights
 * for the package.
 *
 * "You" is you, if you're thinking about copying or distributing this
 * Package.
 *
 * "Reasonable copying fee" is whatever you can justify on the basis of
 * media cost, duplication charges, time of people involved, and so
 * on. (You will not be required to justify it to the Copyright Holder, but
 * only to the computing community at large as a market that must bear the
 * fee.)
 *
 * "Freely Available" means that no fee is charged for the item itself,
 * though there may be fees involved in handling the item. It also means
 * that recipients of the item may redistribute it under the same
 * conditions they received it.
 *
 * 1. You may make and give away verbatim copies of the source form of the
 *    Standard Version of this Package without restriction, provided that
 *    you duplicate all of the original copyright notices and associated
 *    disclaimers.
 *
 * 2. You may apply bug fixes, portability fixes and other modifications
 *    derived from the Public Domain or from the Copyright Holder. A
 *    Package modified in such a way shall still be considered the Standard
 *    Version.
 *
 * 3. You may otherwise modify your copy of this Package in any way,
 *    provided that you insert a prominent notice in each changed file
 *    stating how and when you changed that file, and provided that you do
 *    at least ONE of the following:
 *
 *    a) place your modifications in the Public Domain or otherwise make
 *    them Freely Available, such as by posting said modifications to
 *    Usenet * or an equivalent medium, or placing the modifications on a
 *    major * archive site such as ftp.uu.net, or by allowing the Copyright
 *    Holder * to include your modifications in the Standard Version of the
 *    Package.
 *
 *    b) use the modified Package only within your corporation or *
 *    organization.
 *
 *    c) rename any non-standard executables so the names do not conflict
 *    with standard executables, which must also be provided, and provide a
 *    separate manual page for each non-standard executable that clearly
 *    documents how it differs from the Standard Version.
 *
 *    d) make other distribution arrangements with the Copyright Holder.
 *
 * 4. You may distribute the programs of this Package in object code or
 *    executable form, provided that you do at least ONE of the following:
 *
 *    a) distribute a Standard Version of the executables and library
 *    files, together with instructions (in the manual page or equivalent)
 *    on where to get the Standard Version.
 *
 *    b) accompany the distribution with the machine-readable source of the
 *    Package with your modifications.
 *
 *    c) accompany any non-standard executables with their corresponding
 *    Standard Version executables, giving the non-standard executables
 *    non-standard names, and clearly documenting the differences in manual
 *    pages (or equivalent), together with instructions on where to get the
 *    Standard Version.
 *
 *    d) make other distribution arrangements with the Copyright Holder.
 *
 * 5. You may charge a reasonable copying fee for any distribution of this
 *    Package. You may charge any fee you choose for support of this
 *    Package. You may not charge a fee for this Package itself.  However,
 *    you may distribute this Package in aggregate with other (possibly
 *    commercial) programs as part of a larger (possibly commercial)
 *    software distribution provided that you do not advertise this Package
 *    as a product of your own.
 *
 * 6. The scripts and library files supplied as input to or produced as
 *    output from the programs of this Package do not automatically fall
 *    under * the copyright of this Package, but belong to whomever
 *    generated them, * and may be sold commercially, and may be aggregated
 *    with this Package.
 *
 * 7. C or perl subroutines supplied by you and linked into this Package
 *    shall not be considered part of this Package.
 *
 * 8. The name of the Copyright Holder may not be used to endorse or
 *    promote products derived from this software without specific prior *
 *    written permission.
 *
 * 9. THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 *    WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 *    MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 */

package org.chiba.web.session.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chiba.adapter.ChibaEvent;
import org.chiba.adapter.DefaultChibaEventImpl;
import org.chiba.adapter.ui.UIGenerator;
import org.chiba.adapter.ui.XSLTGenerator;
import org.chiba.web.WebFactory;
import org.chiba.web.flux.FluxAdapter;
import org.chiba.web.servlet.HttpRequestHandler;
import org.chiba.web.servlet.ServletAdapter;
import org.chiba.web.servlet.WebUtil;
import org.chiba.web.session.XFormsSession;
import org.chiba.web.session.XFormsSessionManager;
import org.chiba.xml.events.ChibaEventNames;
import org.chiba.xml.events.XMLEvent;
import org.chiba.xml.xforms.config.Config;
import org.chiba.xml.xforms.config.XFormsConfigException;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xslt.TransformerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.semanticwb.SWBUtils;

/**
 * encapsulates the objects needed by a Chiba form session.
 *
 * @author joern turner</a>
 * @version $Id: XFormsSessionBase.java 2875 2007-09-28 09:43:30Z lars $
 *
 * todo: should use something different than simple timestamps as session keys for security reasons.
 * todo: These are easily guessable so a malicious program might get access to open sessions.
 *
 */
public class XFormsSessionBase extends AbstractXFormsSession {
    public static final String XSL_PARAM_NAME = "xslt";
    public static final String ACTIONURL_PARAM_NAME = "action_url";
    protected Config configuration;
    private String requestURI;
    private String formURI;

    private static final Log LOGGER = LogFactory.getLog(XFormsSessionBase.class);


    public XFormsSessionBase(HttpServletRequest request,
                             HttpServletResponse response,
                             HttpSession session)
            throws XFormsException {
        super(request, session, response);
        this.lastUseTime = System.currentTimeMillis();
        this.key = "" + this.lastUseTime;
        this.properties = new HashMap();
        this.configuration = Config.getInstance();
        createAdapter();
        initChibaConfig();
        storeCookies();
        setContextParams();
        acceptLanguage();
    }

    /**
     * the string identifier generated by this XFormsSession for use in the client
     *
     * @return the string identifier generated by this XFormsSession for use in the client
     */
    public String getKey() {
        return this.key;
    }

    /**
     * returns the base URI for the XForms. This is important to set correctly as the base URI is used
     * to resolve relatively addressed resources used by the form such as XML Schemas, instance data sources,
     * images, load URIs etc.
     *
     * @return the base URI for the XForms
     */
    protected String getBaseURI(){
        String baseURI;
        String processorBase = configuration.getProperty(WebFactory.PROCESSOR_BASE_PROPERTY);
        if (processorBase == null || processorBase.equalsIgnoreCase("remote")) {
            baseURI = this.requestURI + this.formURI;
        } else {
            baseURI = new File(this.contextRoot, this.formURI).toURI().toString();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("baseURI: " + baseURI);
            }
        }
        return baseURI;
    }

    /**
     * returns true if scripting is allowed/possible. The using application has 2 ways of signalling that
     * scripting is wanted:
     * <br/><br/>
     * - by setting the request attribute 'SCRIPTED'<br/>
     * - by passing the request parameter named by Config param 'script.param'.<br/>
     * <br/>
     *
     * @return true if scripting is allowed/possible
     */
    protected boolean isScripted(){

        //[1] check for script param on request URL. Name of the param is configureable as 'script.param'.
        String scriptedParam = getScriptedParam();
        //this test can eventually be made harder by defining a fixed value to switch scripting on
        if(scriptedParam != null && request.getParameter(scriptedParam) != null){
            return true;
        }

        //[2]  check for request attribute and use it if it has a value of 'true' or 'false'
        if(((String)request.getAttribute(WebFactory.SCRIPTED)).equalsIgnoreCase("true")){
            return true;
        }else if (((String)request.getAttribute(WebFactory.SCRIPTED)).equalsIgnoreCase("false")){
            return false;
        }

        //[3] if all fails return false for nonscripted mode
        return false;
    }

    protected String getScriptedParam() {
        String scriptedParam;
        try {
             scriptedParam = Config.getInstance().getProperty("script.param");
        } catch (XFormsConfigException e) {
            scriptedParam = "chibajs"; //default
        }
        return scriptedParam;
    }


    /**
     * creates an appropriate WebAdapter for the request. At the moment there's only the distinction between
     * scripted (FluxAdapter) and unscripted (ServletAdapter) HTML processing. If other WebAdapters need to be
     * created this method should be overwritten.
     *
     * Note that the Adapter has to be created early to allow configuration before init() is called.
     *
     */
    protected void createAdapter() {
        if (isScripted()) {
            //do AJAX
            this.adapter = new FluxAdapter();
        } else {
            //do standard browser support without scripting
            this.adapter = new ServletAdapter();
        }
        adapter.setXFormsSession(this);

    }


    /**
      * configures the an Adapter for interacting with the XForms processor (ChibaBean). The Adapter itself
      * will create the XFormsProcessor (ChibaBean) and configure it for processing.
      * <p/>
      * Please take care to also set the baseURI of the processor to a reasonable value
      * cause this will be the fundament for all URI resolutions taking place.
      *
      * @throws org.chiba.xml.xforms.exception.XFormsException in case Adapter cannot be created
     */
    public void init() throws XFormsException {
        contextRoot = WebUtil.getContextRoot(httpSession.getServletContext());
        String uploadDir = configuration.getProperty(WebFactory.UPLOADDIR_PROPERTY);
        if(!isXFormsPresent){
            setXForms();
        }
        if(this.baseURI == null){
            adapter.setBaseURI(getBaseURI());
        }else{
            adapter.setBaseURI(this.baseURI);
        }
        
        //allow absolute paths otherwise resolve relative to the servlet context
        if(!new File(uploadDir).isAbsolute())
        	uploadDir = WebFactory.resolvePath(uploadDir, httpSession.getServletContext()); 
        
        adapter.setUploadDestination(new File(uploadDir).getAbsolutePath());
        adapter.init();
    }

    /**
     * passes the chiba-defaults.xml config file to Chiba Processor.
     *
     * @throws XFormsException
     */
    protected void initChibaConfig() throws XFormsException {
    	String configPath = WebFactory.resolvePath(this.httpSession.getServletContext().getInitParameter(WebFactory.CHIBA_CONFIG_PATH), this.httpSession.getServletContext());
        if ((configPath != null) && !(configPath.equals(""))) {
            adapter.setConfigPath(configPath);
        }
    }

    /**
     * processes the request after init.
     * @throws IOException
     * @throws XFormsException
     * @throws URISyntaxException
     */
    public synchronized void handleRequest() throws XFormsException {
        boolean updating=false; //this will become true in case ServletAdapter is in use
        updateLRU();

        WebUtil.nonCachingResponse(response);
        
        try {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                updating=true;
                // updating ... - this is only called when ServletAdapter is in use
                ChibaEvent chibaEvent = new DefaultChibaEventImpl();
                chibaEvent.initEvent("http-request", null, request);
                adapter.dispatch(chibaEvent);
            }

            XMLEvent exitEvent = adapter.checkForExitEvent();
            if (exitEvent != null) {
                handleExit(exitEvent);
            }else{
                String referer = null;

                if(updating){
                    // updating ... - this is only called when ServletAdapter is in use
                    referer = (String) getProperty(XFormsSession.REFERER);
                    setProperty("update","true");
                    String forwardTo = request.getContextPath() + "/view?sessionKey=" + getKey() + "&referer=" + referer;
                    response.sendRedirect(response.encodeRedirectURL(forwardTo));
                }else{
                    //initing ...
                    referer = request.getQueryString();

                    response.setContentType(WebUtil.HTML_CONTENT_TYPE);
                    //we got an initialization request (GET) - the session is not registered yet
                    UIGenerator uiGenerator = createUIGenerator();
                    //store UIGenerator in this session as a property
                    setProperty(XFormsSession.UIGENERATOR, uiGenerator);
                    //store queryString as 'referer' in XFormsSession
                    setProperty(XFormsSession.REFERER, request.getContextPath() + request.getServletPath() + "?" + referer);
                    //actually register the XFormsSession with the manager
                    getManager().addXFormsSession(this);
                    /*
                    the XFormsSessionManager is kept in the http-session though it is accessible as singleton. Subsequent
                    servlets should access the manager through the http-session attribute as below to ensure the http-session
                    is refreshed.
                    */
                    httpSession.setAttribute(XFormsSessionManager.XFORMS_SESSION_MANAGER, DefaultXFormsSessionManagerImpl.getXFormsSessionManager());

                    uiGenerator.setInput(this.adapter.getXForms());

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    uiGenerator.setOutput(outputStream);
                    uiGenerator.generate();

                    response.setContentLength(outputStream.toByteArray().length);
                    response.getWriter().print(SWBUtils.TEXT.decode(new String(outputStream.toByteArray()),"UTF-8"));
                }
            }
        } catch (IOException e) {
            throw new XFormsException(e);
        } catch (URISyntaxException e) {
            throw new XFormsException(e);
        } 
        WebUtil.printSessionKeys(this.httpSession);
    }

    /**
     * Handles XForms exist events. There are only 2 situations when this occurs. A load action or a submission/@replace='all'
     * happens during XForms Model init.
     *
     * @param exitEvent the XMLEvent representing the exit condition
     * @throws IOException occurs if the redirect fails
     */
    public void handleExit(XMLEvent exitEvent) throws IOException {
        if (ChibaEventNames.REPLACE_ALL.equals(exitEvent.getType())) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/SubmissionResponse?sessionKey=" + getKey()));
        } else if (ChibaEventNames.LOAD_URI.equals(exitEvent.getType())) {
            if (exitEvent.getContextInfo("show") != null) {
                String loadURI = (String) exitEvent.getContextInfo("uri");

                //kill XFormsSession
                getManager().deleteXFormsSession(getKey());
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("loading: " + loadURI);
                }
                response.sendRedirect(response.encodeRedirectURL(loadURI));
            }
        }
        LOGGER.debug("************************* EXITED DURING XFORMS MODEL INIT *************************");
    }

    /**
     * close the XFormsSession in case of an exception. This will close the WebAdapter holding the Chiba Processor instance,
     * remove the XFormsSession from the Manager and redirect to the error page.
     *
     * @param e the root exception causing the close
     * @throws IOException
     */
    public void close(Exception e) throws IOException{
	    // attempt to shutdown processor
	    if (this.adapter != null) {
	        try {
	            this.adapter.shutdown();
	        } catch (XFormsException xfe) {
	            xfe.printStackTrace();
	        }
	    }

	    // store exception
	    httpSession.setAttribute("chiba.exception", e);

        //remove session from XFormsSessionManager
        getManager().deleteXFormsSession(this.key);

        // redirect to error page (after encoding session id if required)
	    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/" +
                configuration.getProperty(WebFactory.ERROPAGE_PROPERTY)));
	}


    /**
     * creates and configures a UIGenerator that transcodes the XHTML/XForms document into the desired target format.
     *
     * todo: make configuration of xsl file more flexible
     * @return an instance of an UIGenerator
     * @throws URISyntaxException
     * @throws XFormsException
     */
    protected UIGenerator createUIGenerator() throws URISyntaxException, XFormsException {
        String xsltPath = configuration.getProperty(WebFactory.XSLT_PATH_PROPERTY);
        String relativeUris = configuration.getProperty(WebFactory.RELATIVE_URI_PROPERTY);

        //todo: should the following two be removed? Use fixed resources dir as convention now - user shouldn't need to touch that
        String scriptPath = configuration.getProperty(WebFactory.SCRIPT_PATH_PROPERTY);
        String cssPath = configuration.getProperty(WebFactory.CSS_PATH_PROPERTY);

        //todo: extract method
        String xslFile = request.getParameter(XSL_PARAM_NAME);
        if (xslFile == null){
            xslFile = configuration.getProperty(WebFactory.XSLT_DEFAULT_PROPERTY);
        }

        TransformerService transformerService = (TransformerService) httpSession.getServletContext().getAttribute(TransformerService.class.getName());
        URI uri = new File(WebFactory.resolvePath(xsltPath, httpSession.getServletContext())).toURI().resolve(new URI(xslFile));

        //todo:make configurable
        XSLTGenerator generator = new XSLTGenerator();
        generator.setTransformerService(transformerService);
        generator.setStylesheetURI(uri);

        if(relativeUris.equals("true"))
        	generator.setParameter("contextroot", ".");
        else
        	generator.setParameter("contextroot", request.getContextPath());
        generator.setParameter("sessionKey", getKey());
        if(getProperty(XFormsSession.KEEPALIVE_PULSE) != null){
            generator.setParameter("keepalive-pulse",getProperty(XFormsSession.KEEPALIVE_PULSE));
        }

        if(isScripted()){
            generator.setParameter("action-url", getActionURL(true));
        }else{
            generator.setParameter("action-url", getActionURL(false));
        }
        generator.setParameter("debug-enabled", String.valueOf(LOGGER.isDebugEnabled()));
        String selectorPrefix = Config.getInstance().getProperty(HttpRequestHandler.SELECTOR_PREFIX_PROPERTY,
                HttpRequestHandler.SELECTOR_PREFIX_DEFAULT);
        generator.setParameter("selector-prefix", selectorPrefix);
        String removeUploadPrefix = Config.getInstance().getProperty(HttpRequestHandler.REMOVE_UPLOAD_PREFIX_PROPERTY,
                HttpRequestHandler.REMOVE_UPLOAD_PREFIX_DEFAULT);
        generator.setParameter("remove-upload-prefix", removeUploadPrefix);
        String dataPrefix = Config.getInstance().getProperty("chiba.web.dataPrefix");
        generator.setParameter("data-prefix", dataPrefix);

        String triggerPrefix = Config.getInstance().getProperty("chiba.web.triggerPrefix");
        generator.setParameter("trigger-prefix", triggerPrefix);

        generator.setParameter("user-agent", request.getHeader("User-Agent"));

        generator.setParameter("scripted", String.valueOf(isScripted()));
        if (scriptPath != null) {
            generator.setParameter("scriptPath", scriptPath);
        }
        if (cssPath != null) {
            generator.setParameter("CSSPath", cssPath);
        }

        String compressedJS = Config.getInstance().getProperty("chiba.js.compressed","false");
        generator.setParameter("js-compressed",compressedJS);

        return generator;
    }


    /**
     * determines the value for the HTML form/@action attribute in the transcoded page
     * @param scripted Client allows scripting or not
     * @return the action url to be used in the HTML form
     */
    protected String getActionURL(boolean scripted) {
        String defaultActionURL = null;

        String ajax_agent = configuration.getProperty(WebFactory.AJAX_AGENT_PROPERTY);
        String plain_html_agent = configuration.getProperty(WebFactory.PLAINHTML_AGENT_PROPERTY);

        if (scripted) {
            defaultActionURL = WebUtil.getRequestURI(request) + ajax_agent;
        } else {
            defaultActionURL = WebUtil.getRequestURI(request) + plain_html_agent;
        }
        String encodedDefaultActionURL = response.encodeURL(defaultActionURL);
        int sessIdx = encodedDefaultActionURL.indexOf(";jsession");
        String sessionId = null;
        if (sessIdx > -1) {
            sessionId = encodedDefaultActionURL.substring(sessIdx);
        }
        String actionURL = request.getParameter(ACTIONURL_PARAM_NAME);
        if (null == actionURL) {
            actionURL = encodedDefaultActionURL;
        } else if (null != sessionId) {
            actionURL += sessionId;
        }

        LOGGER.debug("actionURL: " + actionURL);
        // encode the URL to allow for session id rewriting
        actionURL = response.encodeURL(actionURL);
        return actionURL;
    }

}
