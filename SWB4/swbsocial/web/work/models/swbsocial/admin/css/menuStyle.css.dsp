<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %>
<%@ taglib uri="http://www.zkoss.org/dsp/zk/core" prefix="z" %>
<c:include page="~./zul/css/ext.css.dsp"/>
<%--Para agregar a .zul
<?link rel="stylesheet" type="text/css" href="/estilos/menu.css.dsp?v=${desktop.webApp.build}"?>
--%>

.menuStyle .z-menu-inner-m .z-menu-btn,
.menuStyle .z-menubar-hor .z-menuitem-btn {
    min-height:88px;
    min-width:900px;
    text-align:center;
    background:transparent no-repeat center 0;
    font-size: 18pt;
    color: blue;
}
.menuStyle .z-menu-body-text-img .z-menu-inner-m .z-menu-btn,
.menuStyle .z-menu-body-img .z-menu-inner-m .z-menu-btn,
.menuStyle .z-menuitem-body-text-img .z-menuitem-inner-m .z-menuitem-btn {
    padding-right:0;
    padding-left:0;
    padding-bottom:0;
    text-align:center;
}
.menuStyle .z-menu-body-over .z-menu-inner-l,
.menuStyle .z-menu-body-over .z-menu-inner-r,
.menuStyle .z-menuitem-body-over .z-menuitem-inner-l,
.menuStyle .z-menuitem-body-over .z-menuitem-inner-r {
    background-image:none;
}
.menuStyle .z-menu-body-over .z-menu-inner-m,
.menuStyle .z-menuitem-body-over .z-menuitem-inner-m{
    background-image: none;
}
.menuStyle .z-menu-body-seld .z-menu-inner-l,
.menuStyle .z-menu-body-seld .z-menu-inner-r,
.menuStyle .z-menuitem-body-seld .z-menuitem-inner-l,
.menuStyle .z-menuitem-body-seld .z-menuitem-inner-r{
    background:none;
}
.menuStyle .z-menu-body-seld .z-menu-inner-m,
.menuStyle .z-menu-bodyitem-seld .z-menuitem-inner-m{
    background-image: none;
}
.menuStyle .z-menu-inner-m .z-menu-btn,
.menuStyle .z-menuitem-inner-m .z-menuitem-btn{
    padding-top:58px;
    padding-bottom:8px;
}
.menuStyle .z-menubar-hor .z-menu,
.menuStyle .z-menubar-ver .z-menu {
    vertical-align:top;
}
.menuStyle .z-menubar-hor .z-menu-body-clk-over .z-menu-inner-m div,
.menuStyle .z-menubar-hor .z-menuitem-body-clk-over .z-menuitem-inner-m div {
    background-image: none;
}
.menuStyle .z-menubar-hor .z-menu-body-over .z-menu-inner-m div,
.menuStyle .z-menubar-hor .z-menuitem-body-over .z-menuitem-inner-m div{
    background-image: none;
}