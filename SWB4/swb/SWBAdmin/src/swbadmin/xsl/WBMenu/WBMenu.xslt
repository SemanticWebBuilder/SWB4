<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>
    
    <xsl:template match="/menu">
        <div class="swb-menu">
            <table>
                <xsl:for-each select="parent">
                    <tr>
                        <td>
                            <a href="{@path}" class="swb-menu-parent"><xsl:value-of select="@name"/></a>
                        </td>
                    </tr>
                </xsl:for-each>
                <xsl:for-each select="brothers">
                    <xsl:for-each select="brother">
                        <xsl:choose>
                            <xsl:when test="@current = '1'">
                                <tr>
                                    <td>
                                        <a href="{@path}" class="swb-menu-current" target="{@target}" title="{@name}">
                                            <xsl:value-of select="@name"/>
                                        </a>
                                    </td>
                                </tr>
                                <xsl:for-each select="child">
                                    <tr>
                                        <td class="wmn_data">
                                            ::<a href="{@path}" class="swb-menu-child" target="{@target}" title="{@name}"><xsl:value-of select="@name"/></a>
                                        </td>
                                    </tr>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:otherwise>
                                <tr>
                                    <td>
                                        <a href="{@path}" class="swb-menu-active" target="{@target}" title="{@name}">
                                            <xsl:value-of select="@name"/>
                                        </a>
                                    </td>
                                </tr>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:for-each>
                </xsl:for-each>
            </table>
        </div>
    </xsl:template>
</xsl:stylesheet>