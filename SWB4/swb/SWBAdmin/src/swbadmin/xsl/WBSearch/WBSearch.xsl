<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>
    <xsl:template match="/">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
		<td align="left" width="100%">
                    <xsl:if test="SEARCH/@size">
                        <font size="2" face="verdana">Resultado de la b<xsl:text disable-output-escaping="yes">&amp;uacute;</xsl:text>squeda <b><font color="#0000FF"><xsl:value-of select="SEARCH/@words" /></font></b>
                            Resultados: <xsl:value-of select="SEARCH/@off" /> - <xsl:value-of select="SEARCH/@seg" /> de <xsl:value-of select="SEARCH/@size" />
                            <br/>
			</font>
                        <p>
                            <xsl:if test="SEARCH/@sort">
                                <font size="2" face="verdana">
                                    <a href="?q={/SEARCH/@wordsEnc}">Ordenar por puntuaci<xsl:text disable-output-escaping="yes">&amp;oacute;</xsl:text>n</a>
                                </font>
                            </xsl:if>
                            <xsl:if test="not(SEARCH/@sort)">
                                <font size="2" face="verdana">
                                    <a href="?q={/SEARCH/@wordsEnc}&amp;sort=date">Ordenar por fecha</a>
                                </font>
                            </xsl:if>
                        </p>
                        <xsl:if test="SEARCH/Object">
                            <hr color="#16458D" width="100%" size="1" /><br/>
			</xsl:if>
                        <xsl:for-each select="SEARCH/Object">
                            <xsl:choose>
                                <xsl:when test="objType='Resource'">
                                    <font size="2" face="verdana"><B><IMG src="{/SEARCH/@path}images/i_content.gif" width="29" height="24"/></B><a href="{objUrl}" ><xsl:value-of select="objTitle" /></a></font>
                                    <i><FONT size="1" face="verdana">(contenido)</FONT></i><BR/>
                                </xsl:when>
                                <xsl:when test="objType='file'">
                                    <font size="2" face="verdana"><B><IMG src="{/SEARCH/@path}images/i_doc.gif" width="29" height="24"/></B><a href="{objUrl}" ><xsl:value-of select="objTitle" /></a></font>
                                    <i><FONT size="1" face="verdana">(documento)</FONT></i><BR/>
                                </xsl:when>
                                <xsl:when test="objType='url'">
                                    <font size="2" face="verdana"><B><IMG src="{/SEARCH/@path}images/i_link.gif" width="29" height="24"/></B><a href="{objUrl}" ><xsl:value-of select="objTitle" /></a></font>
                                    <i><FONT size="1" face="verdana">(link)</FONT></i><BR/>
                                </xsl:when>
                                <xsl:when test="objType='data'">
                                    <font size="2" face="verdana"><B><IMG src="{/SEARCH/@path}images/i_data.gif" width="29" height="24"/></B><a href="{objUrl}" ><xsl:value-of select="objTitle" /></a></font>
                                    <i><FONT size="1" face="verdana">(datos)</FONT></i><BR/>
                                </xsl:when>
                                <xsl:when test="objType='html'">
                                    <font size="2" face="verdana"><B><IMG src="{/SEARCH/@path}images/i_html.gif" width="29" height="24"/></B><a href="{objUrl}" ><xsl:value-of select="objTitle" /></a></font>
                                    <i><FONT size="1" face="verdana">(html)</FONT></i><BR/>
                                </xsl:when>
                                <xsl:when test="objType='user'">
                                    <font size="2" face="verdana"><B><IMG src="{/SEARCH/@path}images/i_user.gif" width="29" height="24"/></B><a href="{objUrl}" ><xsl:value-of select="objTitle" /></a></font>
                                    <i><FONT size="1" face="verdana">(html)</FONT></i><BR/>
                                </xsl:when>
                                <xsl:when test="objType='WebPage'">
                                    <font size="2" face="verdana"><B><IMG src="{/SEARCH/@path}images/i_topic.gif" width="29" height="24"/></B><a href="{objUrl}" ><xsl:value-of select="objTitle" /></a></font>
                                    <i><FONT size="1" face="verdana">(secci<xsl:text disable-output-escaping="yes">&amp;oacute;</xsl:text>n)</FONT></i><BR/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <font size="2" face="verdana"><B><IMG src="{/SEARCH/@path}images/i_data.gif" width="29" height="24"/></B><a href="{objUrl}" ><xsl:value-of select="objTitle" /></a></font>
                                    <i><FONT size="1" face="verdana">(gen<xsl:text disable-output-escaping="yes">&amp;eacute;</xsl:text>rico)</FONT></i>
                                    <BR/>
                                </xsl:otherwise>
                            </xsl:choose>
                            <xsl:if test="navPath">
                                <I><FONT color="#FF0000" size="1"><xsl:value-of select="navPath" /></FONT></I><BR/>
                            </xsl:if>
                            <xsl:if test="objSummary">
                                <font face="verdana" size="2"><xsl:value-of select="objSummary" /></font><BR/>
                            </xsl:if>
                            <table width="250" border="0" cellpadding="0" cellspacing="0" >
                                <tr>
                                    <td >
                                        <font color="#707070" size="1" face="Verdana">Puntuaci<xsl:text disable-output-escaping="yes">&amp;oacute;</xsl:text>n: <xsl:value-of select="objScore" /></font>
                                    </td>
                                    <td>
                                        <table width="100" border="0" cellpadding="0" cellspacing="1" bgcolor="#006699">
                                            <tr>
                                                <td bgcolor="#FFFFFF" >
                                                    <table width="{objScore}%" border="0" cellpadding="0" cellspacing="0" bgcolor="#589A42">
                                                        <tr>
                                                            <td><font color="#589A42" size="1">.</font></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                            <BR/>
                        </xsl:for-each>
                        <hr color="#16458D" width="100%" size="1" /><br/>
                    </xsl:if>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <center>
                        <xsl:if test="SEARCH/@bPagination">
                            <FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">P<xsl:text disable-output-escaping="yes">&amp;aacute;</xsl:text>ginas de Resultados:</FONT>
                            <xsl:if test="SEARCH/@backPage">
                                <font size="2" face="verdana">
                                    <b><a href="?q={/SEARCH/@wordsEnc}{/SEARCH/@params}&amp;wbSpage={/SEARCH/@backPage}" ><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">Anterior</FONT></a></b>
				</font>
                            </xsl:if>
                            <xsl:for-each select="SEARCH/pagination/page">
                                <xsl:choose>
                                    <xsl:when test="actualPage='1'">
                                        <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                                        <b><xsl:value-of select="spageview"/></b>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
					<b><a href="?q={/SEARCH/@wordsEnc}&amp;wbSpage={ipage}"><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><xsl:value-of select="spageview"/></FONT></a></b>
		          	    </xsl:otherwise>
  				</xsl:choose>
                            </xsl:for-each>
                            <xsl:if test="SEARCH/@nextPage">
                                <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                                <font size="2" face="verdana">
                                    <b><a href="?q={/SEARCH/@wordsEnc}{/SEARCH/@params}&amp;wbSpage={/SEARCH/@nextPage}" ><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">Siguiente</FONT></a></b>
				</font>
                            </xsl:if>
                        </xsl:if>
                        <form method="get" action="{SEARCH/@url}" >
                            <table cellpadding="0" cellspacing="5" border="0" width="100%" >
                                <tr>
                                    <td align="center">
                                        <font color="#003399" size="2" face="Verdana, Arial, Helvetica, sans-serif">
                                            <b>Escriba las palabras con las que desea realizar una b<xsl:text disable-output-escaping="yes">&amp;uacute;</xsl:text>squeda</b>
                                        </font>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <input size="50" name="q" type="text" /> 
                                        <input type="SUBMIT" value="Aceptar" />
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </center>
                </td>
            </tr>
	</table>
    </xsl:template>
</xsl:stylesheet>