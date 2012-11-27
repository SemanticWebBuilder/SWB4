/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.base.util.parser.html;

// TODO: Auto-generated Javadoc
/**
 * The Interface HTMLParserConstants.
 */
public interface HTMLParserConstants
{

    /** The EOF. */
    int EOF = 0;
    
    /** The Tag name. */
    int TagName = 1;
    
    /** The Decl name. */
    int DeclName = 2;
    
    /** The Comment1. */
    int Comment1 = 3;
    
    /** The Comment2. */
    int Comment2 = 4;
    
    /** The Word. */
    int Word = 5;
    
    /** The LET. */
    int LET = 6;
    
    /** The NUM. */
    int NUM = 7;
    
    /** The Entity. */
    int Entity = 8;
    
    /** The Space. */
    int Space = 9;
    
    /** The SP. */
    int SP = 10;
    
    /** The Punct. */
    int Punct = 11;
    
    /** The Arg name. */
    int ArgName = 12;
    
    /** The Arg equals. */
    int ArgEquals = 13;
    
    /** The Tag end. */
    int TagEnd = 14;
    
    /** The Arg value. */
    int ArgValue = 15;
    
    /** The Arg quote1. */
    int ArgQuote1 = 16;
    
    /** The Arg quote2. */
    int ArgQuote2 = 17;
    
    /** The Quote1 text. */
    int Quote1Text = 19;
    
    /** The Close quote1. */
    int CloseQuote1 = 20;
    
    /** The Quote2 text. */
    int Quote2Text = 21;
    
    /** The Close quote2. */
    int CloseQuote2 = 22;
    
    /** The Comment text1. */
    int CommentText1 = 23;
    
    /** The Comment end1. */
    int CommentEnd1 = 24;
    
    /** The Comment text2. */
    int CommentText2 = 25;
    
    /** The Comment end2. */
    int CommentEnd2 = 26;

    /** The DEFAULT. */
    int DEFAULT = 0;
    
    /** The Within tag. */
    int WithinTag = 1;
    
    /** The After equals. */
    int AfterEquals = 2;
    
    /** The Within quote1. */
    int WithinQuote1 = 3;
    
    /** The Within quote2. */
    int WithinQuote2 = 4;
    
    /** The Within comment1. */
    int WithinComment1 = 5;
    
    /** The Within comment2. */
    int WithinComment2 = 6;

    /** The token image. */
    String[] tokenImage = {
        "<EOF>",
        "<TagName>",
        "<DeclName>",
        "\"<!--\"",
        "\"<!\"",
        "<Word>",
        "<LET>",
        "<NUM>",
        "<Entity>",
        "<Space>",
        "<SP>",
        "<Punct>",
        "<ArgName>",
        "\"=\"",
        "<TagEnd>",
        "<ArgValue>",
        "\"\\\'\"",
        "\"\\\"\"",
        "<token of kind 18>",
        "<Quote1Text>",
        "<CloseQuote1>",
        "<Quote2Text>",
        "<CloseQuote2>",
        "<CommentText1>",
        "\"-->\"",
        "<CommentText2>",
        "\">\"",
    };

}
