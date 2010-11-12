<%@page import="org.semanticwb.rest.BaseServiceInfo"%><%@page import="java.util.TreeSet"%><%@page import="org.semanticwb.rest.RestException"%><%@page import="org.semanticwb.rest.RestParameter"%><%@page import="java.util.Set"%><%@page import="org.w3c.dom.Document"%><%@page import="org.semanticwb.rest.ParameterInfo"%><%@page import="org.semanticwb.rest.AbstractServiceProvider"%><%@page import="org.semanticwb.rest.RestPublish"%><%@page contentType="text/html" pageEncoding="UTF-8"%><%

        String uri=request.getRequestURI();
        String cntx = request.getContextPath();
        uri = uri.substring(cntx.length());
        String[] path=uri.split("/");
        System.out.println(uri);
        System.out.println(path);
        System.out.println(path.length);


    RestPublish rp=new RestPublish();
    String name="Objecto";
    Set<ParameterInfo> parameters=new TreeSet();
    parameters.add(new ParameterInfo("title", "Titulo"));
    rp.registerServiceProvider(new AbstractServiceProvider(new BaseServiceInfo(name, parameters)) {

            @Override
            public Document executeGET(Set<RestParameter> parameters) throws RestException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void executeDelete(Set<RestParameter> parameters) throws RestException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void executePUT(Set<RestParameter> parameters, Document data) throws RestException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void executePOST(Set<RestParameter> parameters, Document data) throws RestException {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    rp.service(request, response);
%>