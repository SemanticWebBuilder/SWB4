package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.scene.effect.DropShadow;
import javafx.animation.*;
import java.lang.Math;

public class MenuPopup extends CustomNode {

    // espacio hacia el borde del menu
    public var padding=4.0;
    // espacio vertical entre opciones
    public var verticalSpacing=2.0;

    // ancho del menu
    var menuWidth=0.0;
    // alto del menu
    var menuHeight=0.0;
    // escala del menu
    var scale=1.0;

    // font del menu
    public var font:Font = Font{ size: 14};
    // color de fondo del menu
    public var fill:Color = Color.WHITE;
    // color del borde del menu
    public var borderColor:Color = Color.BLACK;
    // color del borde del menu
    public var stroke:Color = Color.BLACK;
    // ancho del borde del menu
    public var borderWidth:Number = 1;
    // color para destacar fondo de opcion
    public var highlight:Color = Color.LIGHTBLUE;
    // color para destacar texto de opcion
    public var highlightStroke:Color = Color.BLUE;
    // indica si tiene sombra
    public var shadow:Boolean=true;
    // sombra horizontal
    public var shadowX=5.0;
    // sombra horizontal
    public var shadowY=5.0;
    // esquinas redondeadas
    public var corner=0.0;
    // gradiente opcional
    public var gradient:LinearGradient=null;
    // animacion in/out
    public var animate=false;

    // opacidad
    override public var opacity=1.0;
    // giro del menu
    override public var rotate=0.0;


    // opciones del menu popup
    public var content:MenuItem[]=null on replace {
        // si cambia, obtiene dimensiones del menu y preprocesa opciones
        parseOptions(content) };
    public var event:MouseEvent=null on replace {
        // si oprimio el boton derecho, hace visible el menu
        var isVisible = event.button==MouseButton.SECONDARY;
        this.visible = isVisible;
        if (animate==true) then {
            appear.playFromStart();
        }
    };

    // opciones graficas
    var optionsObjects:Node[];

    // procesa opciones del menu
    function parseOptions(options:MenuItem[]):Void
    {
        // si hay opciones y font definido
        if (options!=null and font!=null) then {

            // calcula ancho basico
            var maxWidth = 0.0;
            // calcula alto basico
            var maxHeight = padding;

            // recorre opciones
            for (option in options) {
                // obtiene texto de la opcion
                var text = Text {
                    font:bind font
                    content: option.text
                };
                // obtiene dimension del texto de la opcion
                var rec = text.boundsInLocal;
                // posicion vertical
                option.pos = maxHeight;
                // acumula alto del menu
                maxHeight += rec.height + verticalSpacing;
                // busca el ancho maximo entre las opciones
                maxWidth = Math.max(rec.width, maxWidth);
            };

            // tamano calculado para el menu
            menuWidth = maxWidth;
            menuHeight = maxHeight - verticalSpacing;

            // crea botones de opciones y espacios
            for (option in options) {
                // obtiene texto de la opcion
                var text = Text {
                    font: bind font
                    content: option.text
                };
                // obtiene dimension del texto de la opcion
                var rec=text.boundsInLocal;
                
                // crea opcion
                var optionButton = Group {

                    // color para el fondo de la opcion
                    var color:Color = fill;
                    // color para el texto de la opcion
                    var txtcolor:Color = stroke;

                    // timeline para destacar la opcion con el mouse
                    var show=Timeline {
                        keyFrames: [
                            at (0s) {color => fill; txtcolor => stroke }
                            at (0.2s) {color => highlight; txtcolor => highlightStroke }
                        ]
                    };

                    // timeline para normalizar opcion luego de ser destacada
                    var hide=Timeline {
                        keyFrames: [
                            at (0s) {color => highlight; txtcolor => highlightStroke }
                            at (0.2s) {color => fill; txtcolor => stroke }
                        ]
                    };

                    // bloquea el click del mouse si hay elementos bajo del menu
                    //blocksMouse:true

                    // declara la opcion de menu
                    content: [
                        // rectangulo de fondo para la opcion
                        Rectangle {
                            fill:bind color
                            x: bind event.x + padding - 10
                            y: bind event.y + option.pos - 10
                            width: bind maxWidth
                            height: bind rec.height
                        },
                        // texto de la opcion
                        Text {
                            fill: bind txtcolor
                            x: bind event.x + padding - 10
                            y: bind event.y + option.pos - 10
                            font: bind font
                            content: option.text
                            textOrigin: TextOrigin.TOP
                        },
                        // rectangulo transparente sobre el texto y fondo
                        Rectangle {
                            // bloquea el click para los elementos que estan debajo
                            //blocksMouse:true
                            fill:Color.TRANSPARENT
                            x: bind event.x + padding - 10
                            y: bind event.y + option.pos - 10
                            width: bind maxWidth
                            height: bind rec.height
                            // si el usuario hace click sobre la opcion
                            onMousePressed: function(e) {
                                // con el boton principal del mouse
                                if (e.button==MouseButton.PRIMARY) {
                                    // ejecuta la funcion asociada al boton
                                    // traspasando el evento del mouse (posicion del mouse)
                                    option.call(e);
                                    // oculta el menu flotante
                                    this.visible=false;
                                }
                            };
                            // si entra con el mouse a la opcion
                            onMouseEntered: function(e) {
                                // destaca la opcion (cambiando colores)
                                show.playFromStart();
                            };
                            // si sale de la opcion con el mouse
                            onMouseExited: function(e) {
                                // restaura opcion (repone colores)
                                hide.playFromStart();
                            };
                        },

                    ]
                };
                // agrega la opcion a una secuencia (array o arreglo)
                insert optionButton into optionsObjects;
            };
        };
    };

    // animacion para mostrar menu
    var appear=Timeline {
        repeatCount:1
        keyFrames: [
            at (0s) { scale => 0.0 }
            at (0.2s) { scale => 1.0 tween Interpolator.EASEIN }
        ]
    };

    // crea el componente del menu flotante
    override function create():Node
    {
        blocksMouse=true;
        // no es visible al crearlo
        this.visible=false;

        Group {
//            cache:true
            rotate: bind rotate
            scaleX: bind scale
            scaleY: bind scale
            opacity: bind opacity
            content: [
                // rectangulo de fondo del menu flotante
                Rectangle {
                    // posiciona en la ubicacion del mouse
                    x: bind event.x - 10
                    y: bind event.y - 10
                    arcHeight: bind corner
                    arcWidth: bind corner
                    // asigna tamano dependiendo de las opciones
                    width: bind menuWidth + padding * 2
                    height: bind menuHeight + padding
                    // parametros para pintar el fondo
                    fill: bind if (gradient != null) then gradient else fill
                    stroke: bind borderColor
                    strokeWidth: bind borderWidth
                    // si la sobra esta activa, la agrega, sino la omite
                    effect: bind if (shadow) {
                        DropShadow {
                            offsetX:bind shadowX
                            offsetY:bind shadowY
                        }
                    }
                    else null;
                },
                // agrega las opciones pregeneradas
                optionsObjects
            ]
            onMouseExited: function (e: MouseEvent): Void
            {
                visible=false;
            }
        }
    }

}
