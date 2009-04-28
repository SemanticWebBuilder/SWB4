<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:output method="html" version="1.0" encoding="ISO-8859-1" omit-xml-declaration="yes" />
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <style>
                    .postDate
                    {
                    color: #800000;
                    font-size: 12px;
                    font-weight: normal
                    font-family: Georgia, "Times New Roman", serif;
                    }
                    .postDescription
                    {
                    color: #000000;
                    font-size: 14px;
                    font-family: sans-serif,"Verdana";
                    font-style: oblique;
                    }
                    .titleBlog
                    {
                    color: #005C89;
                    text-align: center;
                    font-size: 30px;
                    font-family: Georgia, "Times New Roman", serif;
                    }
                    .titlepost
                    {
                    color: #000000;
                    font-size: 26px;
                    font-family: Georgia, "Times New Roman", serif;
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
                <title>comments.xsl</title>                
                <xsl:for-each select="comments">                    
                    <table width="100%">
                        <tr>
                            <td>
                                <table width="100%">
                                    <tr>                                                                        
                                        <td align="center">
                                            <a>
                                                <xsl:attribute name="href"><xsl:value-of select='@url' /></xsl:attribute>
                                            <h1><xsl:value-of select='@name' /></h1></a>
                                        </td>                                    
                                    </tr>
                                    <tr>
                                        <td colspan="2"> 
                                            <table width="100%">
						<tr>
						<td>
							<h2><xsl:value-of select='@author' /></h2>
						</td>
						<td align="right">
							<div class="postDate" ><xsl:value-of select='@date' /></div>
						</td>
						</tr>
						</table> 	                        
                                            
                                        </td>
                                    </tr>                                     
                                    <tr>                                                                        
                                        <td align="center">
                                            <h2><xsl:value-of select='@title' /></h2>
                                        </td>                                    
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>                                
                                <p><xsl:value-of select='description' /></p>                                                                                            
                            </td>
                        </tr>                        
                    </table> 
                    <hr></hr>
                </xsl:for-each>
                <center><h3>Comentarios</h3></center>
                <xsl:for-each select="comments/comment">                    
                    <table width="100%">
                        <tr>
                            <td>
                            	<table width="100%">
                            	<tr>
                            	<td>
                            		<h3><xsl:value-of select='@user' /></h3>
                            	</td>
                            	<td align="right">
                            		<div class="postDate"><xsl:value-of select='@date' /></div>	
                            	</td>
                            	</tr>
                            	</table>
                                
                                
                            </td>
                        </tr>                        
                        <tr>
                            <td>                                
                                <p><xsl:value-of select='.' /></p>
                            </td>
                        </tr>
                        
                    </table>                                        
                    <hr></hr>
                </xsl:for-each>                   
                <xsl:if test="/comments/@viewall != ''">
                    <center><p><a name="viewallcomments" > 
                                <xsl:attribute name="href"><xsl:value-of select='/comments/@viewall' /></xsl:attribute>
                                Ver todos los comentarios
                    </a></p></center>
                </xsl:if>
                <form action="" method="post">
                    <xsl:for-each select="comments">
                        <input type="hidden" name="blogid" >
                            <xsl:attribute name="value"><xsl:value-of select='@blogid' /></xsl:attribute>
                        </input>
                        <input type="hidden" name="postid" >
                            <xsl:attribute name="value"><xsl:value-of select='@postid' /></xsl:attribute>
                        </input>    
                    </xsl:for-each>                    
                    <fieldset>
                        <legend>Agregar un comentario:</legend>
                        <table  width="100%">
                            <tr>
                                <td align="center">
                                    <table  width="100%">
                                        <tr>
                                            <td>
                                                <p><label>Comentario:</label></p>
                                            </td>
                                            <td>
                                                <textarea rows="10" cols="30" name="comment"></textarea>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                
                            </tr>
                            <tr>
                                <td align="center">
                                    <input type="button" onClick="javascript:EnviaComentarioPost(this.form);" name="save" value="Agregar comentario"></input>
                                </td>
                            </tr>                        
                        </table>
                    </fieldset>
                </form>
            </head>
            <body>
            </body>
        </html>
    </xsl:template>
    
</xsl:stylesheet>
