/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.services.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.wsdl.consume.ClassInfo;
import org.semanticwb.wsdl.consume.Operation;
import org.semanticwb.wsdl.consume.OperationType;
import org.semanticwb.wsdl.consume.Parameter;
import org.semanticwb.wsdl.consume.ParameterDefinition;
import org.semanticwb.wsdl.consume.PropertyInfo;
import org.semanticwb.wsdl.consume.ServiceException;
import org.semanticwb.wsdl.consume.ServiceInfo;
import org.semanticwb.wsdl.consume.WSDL;

/**
 *
 * @author victor.lorenzana
 */
public class TestWSDL
{

    public TestWSDL()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }
    @Test
    //@Ignore
    public void ConversionRate()
    {
        try
        {

            WSDL wsdl = new WSDL(new URL("http://www.webservicex.net/CurrencyConvertor.asmx?WSDL"));
            ServiceInfo info = wsdl.getServiceInfo();
            Operation op=info.getOperationByName("ConversionRate");
            List<Parameter> parameters = new ArrayList<Parameter>();
            ParameterDefinition def=op.getInput().getParameterDefinitionByName("parameters");
            Object conversionRate=def.newInstance();
            PropertyInfo FromCurrencyprop=def.getPropertyInfoByName("FromCurrency");            
            FromCurrencyprop.fill(conversionRate, "BBD");
            

            PropertyInfo ToCurrencyprop=def.getPropertyInfoByName("ToCurrency");            
            ToCurrencyprop.fill(conversionRate, "DZD");

            


            Parameter  p=new Parameter("parameters",conversionRate);
            parameters.add(p);
            Parameter[] result = op.execute(parameters);            
            for (Parameter outparameter : result)
            {
                Object value = outparameter.getValue();
                PropertyInfo prop=outparameter.getDefinition().getPropertyInfoByName("ConversionRateResult");
                value=prop.getValue(value);
                System.out.println(value);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @Ignore
    public void loadWSDL()
    {
        try
        {

            //WSDL wsdl = new WSDL(new URL("http://www.webservicex.net/CurrencyConvertor.asmx?WSDL"));
            //WSDL wsdl = new WSDL(new URL("http://www.webservicex.com/globalweather.asmx?WSDL"));

            WSDL wsdl = new WSDL(new URL("http://www.kirupafx.com/WebService/TopMovies.asmx?WSDL"));
            //WSDL wsdl = new WSDL(new URL("http://soaptest.parasoft.com/calculator.wsdl"));
            //WSDL wsdl = new WSDL(new URL("http://www.currencyserver.de/webservice/currencyserverwebservice.asmx?WSDL"));

            ServiceInfo info = wsdl.getServiceInfo();
            for (Operation op : info.getOperations())
            {
                List<Parameter> parameters = new ArrayList<Parameter>();
                System.out.println("Operation :" + op.getName());
                if (op.getOperationType() == OperationType.REQUEST_RESPONSE)
                {
                    for (ParameterDefinition definition : op.getInput().getParameters())
                    {
                        Class clazz = definition.getDefinitionClass();
                        ClassInfo infoclass = definition.getInfo(clazz);
                        Object instance = definition.newInstance();
                        for (PropertyInfo prop : infoclass.getProperties())
                        {
                            if (prop.isBasic())
                            {
                                if (prop.getType().equals("java.lang.int"))
                                    prop.fill(instance, 1);
                                else if (prop.getType().equals("java.lang.float"))
                                    prop.fill(instance, 5f);
                                else if (prop.getType().equals("java.lang.long"))
                                    prop.fill(instance, 5l);
                                else if (prop.getType().equals("java.lang.double"))
                                    prop.fill(instance, 5d);
                                else if (prop.getType().equals("java.lang.String"))
                                    prop.fill(instance, "mx");
                            }
                            else
                            {
                                try
                                {
                                    Object obj = prop.newInstance();
                                    prop.fill(instance, obj);
                                    for(PropertyInfo innerprop :  prop.getProperties())
                                    {
                                        
                                    }
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        }

                        parameters.add(new Parameter("parameters", instance));
                        System.out.println("Parameter in :" + definition.getName() + " type: " + definition.getType());
                    }
                    for (ParameterDefinition definition : op.getOutput().getParameters())
                    {

                        System.out.println("Parameter out :" + definition.getName() + " type: " + definition.getType());
                    }
                }
                Parameter[] result = op.execute(parameters);
                for (Parameter outparameter : result)
                {
                    Object value = outparameter.getValue();
                    System.out.println(value);
                }
            }

        }
        catch (ServiceException e)
        {
            e.printStackTrace();
            System.out.println(e.getDetail());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
