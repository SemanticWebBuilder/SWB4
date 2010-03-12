/*
 * Styles.fx
 *
 * Created on 13/02/2010, 10:32:55 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
 * @author javier.solis
 */

public-read var stylesheets = "{__DIR__}style.css";
//public-read var color="#6060FF";
public-read var color="#03689a";
public-read var color_over="#FF6060";
public-read var color_focused="#00FF00";
public-read var color_fill="#f5f5ff";
public-read var color_fill_pool="#d5d5ff";

public-read var color_endEvent="#660000";
public-read var color_iniEvent="#3c8e3c";
public-read var color_interEvent="#5e7a9e";

public-read var color_gateway="#cc9900";

public-read var style_task="fill: {color_fill}; stroke: {color}; strokeWidth: 2; arcWidth: 15; arcHeight: 15;";
public-read var style_task_text="font-size: 14px; font-family: \"'Verdana'\"; fill: #000000;font-weight: bold;";
//var style_task_textbox="font-size: 14px; font-family: \"Helvetica, Arial\"; font-weight: bold; border-fill:transparent; background-fill:transparent; focus-fill:transparent; shadow-fill:transparent";
public-read var style_task_textbox="font-size: 14px; font-family: \"Helvetica, Arial\"; font-weight: bold; focus-fill:transparent; shadow-fill:transparent";
public-read var style_gateway="fill: {color_fill}; stroke: {color_gateway}; strokeWidth: 2;";
public-read var style_message="fill: {color_fill}; stroke: {color}; strokeWidth: 2;";
public-read var style_simbol="fill: {color_fill}; stroke: {color_gateway}; strokeWidth: 4;";
public-read var style_event="fill: {color_fill}; stroke: {color}; strokeWidth: 2;";
public-read var style_connection="stroke: {color}; strokeWidth: 2;";
public-read var style_connection_arrow="stroke: {color}; strokeWidth: 2;";
public-read var style_toolbar="fill: #f0f0f0; stroke: #909090; strokeWidth: 2;";
public-read var style_pool="fill: {color_fill_pool}; stroke: {color}; strokeWidth: 1;";

public-read var ICO_SUBPROCESS_ADHOC=Image{url: "{__DIR__}images/n_adhoc2.png"}
public-read var ICO_SUBPROCESS_CICLE=Image{url: "{__DIR__}images/n_ciclo2.png"}
public-read var ICO_SUBPROCESS_COMPENSATION=Image{url: "{__DIR__}images/n_compensa2-38.png"}
public-read var ICO_SUBPROCESS_COMPENSATION2=Image{url: "{__DIR__}images/n_compensa.png"}
public-read var ICO_TASK_ADHOC=Image{url: "{__DIR__}images/n_adhoc.png"}
public-read var ICO_TASK_LOOP=Image{url: "{__DIR__}images/n_ciclo.png"}
public-read var ICO_TASK_MANUAL=Image{url: "{__DIR__}images/n_manual.png"}
public-read var ICO_TASK_MULTIPLE=Image{url: "{__DIR__}images/n_objeto.png"}
public-read var ICO_TASK_SCRIPT=Image{url: "{__DIR__}images/n_script.png"}
public-read var ICO_TASK_SERVICE=Image{url: "{__DIR__}images/n_servicio.png"}
public-read var ICO_TASK_USER=Image{url: "{__DIR__}images/n_usr.png"}
public-read var ICO_GATEWAY_ORMULTIPLE=Image{url: "{__DIR__}images/n_excl_eventos_str.png"}
public-read var ICO_GATEWAY_ORMULTIPLE_INT=Image{url: "{__DIR__}images/n_excl_eventos_int.png"}
public-read var ICO_GATEWAY_COMPLEX=Image{url: "{__DIR__}images/n_compleja.png"}

public-read var ICO_EVENT_W_TIMER=Image{url: "{__DIR__}images/n_tmp.png"}
public-read var ICO_EVENT_W_MESSAGE=Image{url: "{__DIR__}images/n_msj_b.png"}
public-read var ICO_EVENT_W_CONDITINAL=Image{url: "{__DIR__}images/n_cond.png"}
public-read var ICO_EVENT_W_LINK=Image{url: "{__DIR__}images/n_enlace_b.png"}
public-read var ICO_EVENT_W_MULTIPLE=Image{url: "{__DIR__}images/n_multi_b.png"}
public-read var ICO_EVENT_W_SIGNAL=Image{url: "{__DIR__}images/n_senal_b.png"}
public-read var ICO_EVENT_W_PARALLEL=Image{url: "{__DIR__}images/n_paralelo_b.png"}
public-read var ICO_EVENT_W_SCALATION=Image{url: "{__DIR__}images/n_escala_b.png"}
public-read var ICO_EVENT_W_ERROR=Image{url: "{__DIR__}images/n_error_b.png"}
public-read var ICO_EVENT_W_COMPENSATION=Image{url: "{__DIR__}images/n_compensa_b.png"}
public-read var ICO_EVENT_W_CANCELATION=Image{url: "{__DIR__}images/n_cancela_b.png"}

public-read var ICO_EVENT_B_MESSAGE=Image{url: "{__DIR__}images/n_msj_n.png"}
public-read var ICO_EVENT_B_ERROR=Image{url: "{__DIR__}images/n_error_n.png"}
public-read var ICO_EVENT_B_CANCELATION=Image{url: "{__DIR__}images/n_cancela_n.png"}
public-read var ICO_EVENT_B_COMPENSATION=Image{url: "{__DIR__}images/n_compensa_n.png"}
public-read var ICO_EVENT_B_SIGNAL=Image{url: "{__DIR__}images/n_senal_n.png"}
public-read var ICO_EVENT_B_MULTIPLE=Image{url: "{__DIR__}images/n_multi_n.png"}
public-read var ICO_EVENT_B_SCALATION=Image{url: "{__DIR__}images/n_escala_n.png"}
public-read var ICO_EVENT_B_TERMINATION=Image{url: "{__DIR__}images/n_termina.png"}
public-read var ICO_EVENT_B_LINK=Image{url: "{__DIR__}images/n_enlace_n.png"}


public-read var dropShadow = DropShadow {
    offsetX: 3
    offsetY: 3
    radius: 10.0
    color: Color.web("#707070")
}

public-read var style_tooltip="fill: #FFFF66; stroke: #000000; strokeWidth: 0; arcWidth: 0; arcHeight: 0;";
