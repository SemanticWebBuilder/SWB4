<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>

    <xsl:template match="/">
        <div class="searchResults">
            <p>
                Resultado de la b<xsl:text disable-output-escaping="yes">&amp;uacute;</xsl:text>squeda
                <span class="searchWords"><xsl:value-of select="SEARCH/@words" /></span>
            </p>
            <p>
                Resultados: <xsl:value-of select="SEARCH/@off" /> - <xsl:value-of select="SEARCH/@seg" /> de <xsl:value-of select="SEARCH/@size" />
            </p>
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
            <p></p>
            <xsl:if test="SEARCH/@size">
                <xsl:for-each select="SEARCH/Object">
                    <div class="searchEntry">
                        <xsl:choose>
                            <xsl:when test="objType='Resource'">
                                <h3>
                                    <a href="{objUrl}" ><xsl:value-of select="objTitle" /></a>
                                    <span class="itemType">(contenido)</span>
                                </h3>
                                <img src="{/SEARCH/@path}images/i_content.png" width="29" height="24" alt=""/>
                            </xsl:when>
                            <xsl:when test="objType='file'">
                                <h3>
                                    <a href="{objUrl}" ><xsl:value-of select="objTitle" /></a>
                                    <span class="itemType">(documento)</span>
                                </h3>
                                <img src="{/SEARCH/@path}images/i_doc.png" width="29" height="24" alt=""/>
                            </xsl:when>
                            <xsl:when test="objType='url'">
                                <h3>
                                    <a href="{objUrl}" ><xsl:value-of select="objTitle" /></a>
                                    <span class="itemType">(link)</span>
                                </h3>
                                <img src="{/SEARCH/@path}images/i_link.png" width="29" height="24" alt=""/>
                            </xsl:when>
                            <xsl:when test="objType='data'">
                                <h3>
                                    <a href="{objUrl}" ><xsl:value-of select="objTitle" /></a>
                                    <span class="itemType">(datos)</span>
                                </h3>
                                <img src="{/SEARCH/@path}images/i_data.png" width="29" height="24" alt=""/>
                            </xsl:when>
                            <xsl:when test="objType='html'">
                                <h3>
                                    <a href="{objUrl}" ><xsl:value-of select="objTitle" /></a>
                                    <span class="itemType">(html)</span>
                                </h3>
                                <img src="{/SEARCH/@path}images/i_html.png" width="29" height="24" alt=""/>
                            </xsl:when>
                            <xsl:when test="objType='user'">
                                <h3>
                                    <a href="{objUrl}" ><xsl:value-of select="objTitle" /></a>
                                    <span class="itemType">(html)</span>
                                </h3>
                                <img src="{/SEARCH/@path}images/i_user.png" width="29" height="24" alt=""/>
                            </xsl:when>
                            <xsl:when test="objType='WebPage'">
                                <h3>
                                    <a href="{objUrl}" ><xsl:value-of select="objTitle" /></a>
                                    <span class="itemType">(secci<xsl:text disable-output-escaping="yes">&amp;oacute;</xsl:text>n)</span>
                                </h3>
                                <img src="{/SEARCH/@path}images/i_topic.png" width="29" height="24" alt=""/>
                            </xsl:when>
                            <xsl:otherwise>
                                <h3>
                                    <a href="{objUrl}" ><xsl:value-of select="objTitle" /></a>
                                    <span class="itemType">(gen<xsl:text disable-output-escaping="yes">&amp;eacute;</xsl:text>rico)</span>
                                </h3>
                                <img src="{/SEARCH/@path}images/i_data.png" width="29" height="24" alt=""/>
                            </xsl:otherwise>
                        </xsl:choose>
                        <xsl:if test="navPath">
                            <p class="itemPath"><xsl:value-of select="navPath" /></p>
                        </xsl:if>
                        <xsl:if test="objSummary">
                            <p class="itemLastUpdate">
                                <xsl:value-of select="objSummary" />
                            </p>
                        </xsl:if>
                        <p class="itemScore">Puntuaci<xsl:text disable-output-escaping="yes">&amp;oacute;</xsl:text>n: <xsl:value-of select="objScore" /></p>
                        <div class="scoreWrapper">
                            <div class="scoreBar" style="width:{objScore}%">
                                <xsl:text  disable-output-escaping="yes">
                                    &amp;nbsp;
                                </xsl:text>
                            </div>
                        </div>
                    </div>
                </xsl:for-each>
            </xsl:if>
            <xsl:if test="SEARCH/@bPagination">
                <p class="searchResultsPag">
                    P<xsl:text disable-output-escaping="yes">&amp;aacute;</xsl:text>ginas de Resultados:
                    <xsl:if test="SEARCH/@backPage">
                        <xsl:text  disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                        <a href="?q={/SEARCH/@wordsEnc}{/SEARCH/@params}&amp;wbSpage={/SEARCH/@backPage}" >Anterior</a>
                    </xsl:if>
                    <xsl:for-each select="SEARCH/pagination/page">
                        <xsl:choose>
                            <xsl:when test="actualPage='1'">
                                <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                                <xsl:value-of select="spageview"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                                <a href="?q={/SEARCH/@wordsEnc}&amp;wbSpage={ipage}"><xsl:value-of select="spageview"/></a>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:for-each>
                    <xsl:if test="SEARCH/@nextPage">
                        <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                        <a href="?q={/SEARCH/@wordsEnc}{/SEARCH/@params}&amp;wbSpage={/SEARCH/@nextPage}" >Siguiente</a>
                </xsl:if>
                </p>
            </xsl:if>
            
            <form method="get" action="{SEARCH/@url}" >
                <p>Escriba las palabras con las que desea realizar una b<xsl:text disable-output-escaping="yes">&amp;uacute;</xsl:text>squeda</p>
                <p id="nuevaBusqueda">
                    <label for="busquedaText">Buscar</label>
                    <input type="text" id="busquedaText" value="Nueva búsqueda" size="50" name="q" /> 
                    <label for="buscar">Buscar</label>
                    <input type="submit" id="buscar" value="Buscar" />
                </p>
            </form>
        </div>
    </xsl:template>
</xsl:stylesheet>