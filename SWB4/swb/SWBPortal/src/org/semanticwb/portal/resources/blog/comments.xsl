<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" version="1.0" encoding="ISO-8859-1" omit-xml-declaration="yes" />

    <xsl:template match="/">

        <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.9.1/dijit/themes/nihilo/nihilo.css" type="text/css" media="screen" id="themeCss"></link>
        <!-- script TYPE="text/javascript" SRC="{blog/@webpath}/swbadmin/js/dojo/dojo/dojo.js"></script -->
        <link rel="stylesheet" href="{/blog/@webpath}/swbadmin/js/dojo/dojo/resources/dojo.css" type="text/css" media="screen" id="themeCss"></link>
		<link rel="stylesheet" href="{/blog/@webpath}/swbadmin/js/dojo/dijit/themes/dijit.css" type="text/css" media="screen" id="themeCss"></link>
		<link rel="stylesheet" href="{/blog/@webpath}/swbadmin/js/dojo/dojox/layout/resources/FloatingPane.css" type="text/css" media="screen" id="themeCss"></link>
		<link rel="stylesheet" href="{/blog/@webpath}/swbadmin/js/dojo/dojox/layout/resources/ResizeHandle.css" type="text/css" media="screen" id="themeCss"></link>
        <link rel="stylesheet" href="{/blog/@webpath}/swbadmin/js/dojo/dojox/grid/_grid/nihiloGrid.css" type="text/css" media="screen" id="themeCss"></link>
        

                <style>
                    body
                    {
                        font-family: Verdana, "Times New Roman", serif;
                    }

                    .postDate
                    {
                        color: #800000;
                        font-size: 10px;
                        font-weight: normal;
                        font-style:italic;
                    }
                    .postAutor
                    {
                        color: gray;
                        font-size: 11px;
                        font-weight: normal;
                        font-style:italic;
                    }

                    .postDescription
                    {
                        font-size: 12px;
                        font-weight: normal;
                    }

                    .postTitle
                    {
                        color: #000000;
                        font-size: 20px;
                    }
                    .commentDate
                    {
                        color: #800000;
                        font-size: 10px;
                        font-weight: normal;
                        font-style:italic;
                    }

                    .commentUser
                    {
                        color: gray;
                        font-size: 11px;
                        font-weight: normal;
                        font-style:italic;
                    }

                    .commentText
                    {
                        font-size: 12px;
                        font-weight: normal;
                    }

                    .titleBlog
                    {
                        color: #005C89;
                        text-align: center;
                        font-size: 24px;
                        font-family: Verdana, "Times New Roman", serif;
                    }

                    .post fieldset
                    {
                        border-style: solid;
                        border-color: silver;
                        border-width: thin;
                        margin:2em;
                        padding-left:1em;
                        padding-right:1em;
                        padding-bottom:1em;
                        padding-top:1em;
                        text-align:justify;
                    }

                    .post a
                    {
                        font-weight:lighter;
                        color:teal;
                        text-decoration:none;
                        font-size:11px;
                        font-style:italic;
                    }

                </style>
                <script>                                         
                    function EnviaComentarioPost(forma)
                    {                        
                    if(forma.comment.value=='')
                    {
                    alert('Debe indicar el comentario');
                    return;
                    }                        
                    forma.action=url_comment_post;
                    forma.submit();
                    }
                </script>

                <xsl:for-each select="comments">
                    <span class="titleBlog" style="margin:2em;"><xsl:value-of select='@name' /><br/><span style="margin:2em;"><a class="post"><xsl:attribute name="href"><xsl:value-of select='@url' /></xsl:attribute>Ver todas las entradas</a></span></span>
                    <fieldset class="post">
                        <span class="postTitle" ><xsl:value-of select='@title' /></span><br/><br/>
                        <span class="postAutor" >Entrada publicada por <span style="color:black;font-weight:bold;"><xsl:value-of select='@author' /></span></span><br/>
                        <span class="postDate" ><xsl:value-of select='@date' /></span><br/>
                        <p class="postDescription"><xsl:value-of select='description' /></p>
                    </fieldset>
                </xsl:for-each>
                <xsl:for-each select="comments/comment">
                <fieldset class="post" style="background-color:#F2F2F2">
                    <span class="commentUser">Comentario publicado por <span style="color:black;font-weight:bold;"><xsl:value-of select='@user' /></span></span><br/>
                    <span class="commentDate"><xsl:value-of select='@date' /></span><br/>
                    <p class="commentText"><xsl:value-of select='.' /></p>
                </fieldset>
                </xsl:for-each>                   
                <xsl:if test="/comments/@viewall != ''">
                    <span style="margin:2em;"><a class="post" name="viewallcomments" >
                                <xsl:attribute name="href"><xsl:value-of select='/comments/@viewall' /></xsl:attribute>
                                Ver todos los comentarios
                    </a></span>
                </xsl:if>
                <xsl:if test="/comments/@level= '1'">
                <form action="" method="post">
                    <xsl:for-each select="comments">
                        <input type="hidden" name="blogid" >
                            <xsl:attribute name="value"><xsl:value-of select='@blogid' /></xsl:attribute>
                        </input>
                        <input type="hidden" name="postid" >
                            <xsl:attribute name="value"><xsl:value-of select='@postid' /></xsl:attribute>
                        </input>    
                    </xsl:for-each>                    
                    <fieldset class="post">
                        <legend>Nuevo comentario</legend>
                            <textarea id="comment" rows="10" cols="50" name="comment"></textarea><br/>
                            <button type="button" onClick="javascript:EnviaComentarioPost(this.form);" name="save">Agregar</button>
                    </fieldset>
                </form>
                </xsl:if>
    </xsl:template>
    
</xsl:stylesheet>
