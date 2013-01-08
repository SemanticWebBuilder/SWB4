/*
 * Clase para identificar una acción y elemento a partir de las variaciones 
 * textuales de un comando de voz.
 */
package org.semanticwb.nlp.CommandTranslator;

import org.semanticwb.nlp.Utils.string;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author vieyra
 * samuel.vieyra@infotec.com.mx
 */
public class CommandParser {

    private HashMap<String,String> Actions = new HashMap();
    private ElementSelector elementSelector = null;
    private String[] speechSentences;
    private String[] selectedSentences;
    private String[] elements;
    private Command command;

    public CommandParser() {
        setActions();
        elementSelector = new ElementSelector();
    }

    public void addAction(String key, String value) {
        Actions.put(key, value);
    }

    private String[] getSelectedSentences() {
        if (speechSentences != null && speechSentences.length > 0) {
            if (elementSelector == null) {
                elementSelector = new ElementSelector();
            }
            if (elementSelector.getSpeechSentences() == null || elementSelector.getSpeechSentences().length == 0) {
                elementSelector.setSpeechSentences(speechSentences);
            }
            if (elementSelector.getElements() == null || elementSelector.getElements().length == 0) {
                elementSelector.setElements(elements);
            }
            return (selectedSentences = elementSelector.selectSpeechSentences().toArray(new String[]{}));
        }
        return null;
    }

    public void setSpeechSentences(String[] speechSentences) {
        this.speechSentences = speechSentences;
    }

    public void setElements(String[] elements) {
        this.elements = elements;
    }

    private void setActions() {
        Actions = new HashMap();
        Actions.put("encender", "Encender");
        Actions.put("conectar", "Encender");
        Actions.put("activar", "Encender");
        Actions.put("prender", "Encender");

        Actions.put("apagar", "Apagar");
        Actions.put("desactivar", "Apagar");
        Actions.put("cortar", "Apagar");
        Actions.put("desconectar", "Apagar");
    }
    
    public void setActions(HashMap Actions) {
        this.Actions = Actions;
    }

    public Command parseCommand() {
        getSelectedSentences();
        List<SentenceParser> parsedSentences = parseSentences();
        command = getCommand(parsedSentences);
        return command;
    }
    
    public Command parseCommand(String[] sentences){
        setSpeechSentences(sentences);
        selectedSentences = null;
        elementSelector = null;
        
        return parseCommand();
    }

    private Command getCommand(List<SentenceParser> parsedSentences) {
        Command c = new Command();
        if(elementSelector.getSelectedElement()!=null)
        {
            c.setElement(elementSelector.getSelectedElement().getElementSimilar());
        }
        if (parsedSentences.size() > 1) {
            c.setAction(selectAction(parsedSentences));
            c.setIntensity(string.getIntensity(selectIntensity(parsedSentences)));
            c.setTime(string.getCalendar(selectTime(parsedSentences)));
        } else if (parsedSentences.size() == 1) {
            c.setAction(parsedSentences.get(0).getAction() != null ? parsedSentences.get(0).getAction().getLemma() : null);
            c.setIntensity(parsedSentences.get(0).getIntensityModifier() != null ? string.getIntensity(parsedSentences.get(0).getIntensityModifier().getLemma()) : -1);
            c.setTime(parsedSentences.get(0).getTemporalModifier()!= null ? 
                    string.getCalendar(string.printDate(parsedSentences.get(0).getTemporalModifier().getLemma())) : null);
        }
        return c;
    }

    private String selectAction(List<SentenceParser> parsedSentences) {
        List<Object[]> Candidates = new ArrayList<Object[]>();
        double noNullsActions = 0.0;
        for (SentenceParser parsedSentence : parsedSentences) {
            if (parsedSentence.getAction() != null) {
                noNullsActions++;
            }
        }
        for (SentenceParser parsedSentence : parsedSentences) {
            if (parsedSentence.getAction() != null) {
                Object[] candidate = new Object[]{parsedSentence.getAction().getLemma(), 1.0 / noNullsActions};
                boolean isDeclared = false;
                for (Object[] c : Candidates) {
                    if (c[0].equals(candidate[0])) {
                        c[1] = Double.parseDouble(c[1].toString()) + 1.0 / noNullsActions;
                        isDeclared = true;
                        break;
                    }
                }
                if (!isDeclared) {
                    Candidates.add(candidate);
                }
            }
        }
        if (noNullsActions == 0) {
            return null;
        }
        String key=getBestCandidate(Candidates);
        return Actions.get(key);
    }

    private String getBestCandidate(List<Object[]> candidates) {
        double maxProb = 0.0;
        List<String> BestCandidate = new ArrayList<String>();
        for (Object[] candidate : candidates) {
            if (Double.parseDouble(candidate[1].toString()) > maxProb) {
                BestCandidate = new ArrayList<String>();
                BestCandidate.add(candidate[0].toString());
                maxProb = Double.parseDouble(candidate[1].toString());
            } else if (Double.parseDouble(candidate[1].toString()) == maxProb) {
                BestCandidate.add(candidate[0].toString());
            }
        }
        if (BestCandidate.size() != 1) {
            for (String bcandidate : BestCandidate) 
            {
                if (Actions.containsKey(bcandidate)) {
                    return bcandidate;
                }
            }
            return null;
        }
        return BestCandidate.get(0);
    }

    private String selectIntensity(List<SentenceParser> parsedSentences) {
        for (SentenceParser parsedSentence : parsedSentences) {
            if (parsedSentence.getIntensityModifier() != null) {
                return parsedSentence.getIntensityModifier().getLemma();
            }
        }
        return null;
    }

    private String selectTime(List<SentenceParser> parsedSentences) {
        String[] parsedDate = new String[6];
        boolean identifiedDate = false;
        for(SentenceParser parsedSentence : parsedSentences){
            if (parsedSentence.getTemporalModifier() != null) {
                String date[] = string.parseDate(parsedSentence.getTemporalModifier().getLemma());
                for(int i = 0 ; i < 6 ; i++){
                    if(date[i] != null){
                        identifiedDate = true;
                        parsedDate[i] = date[i];
                    }
                }
            }
        }
        if(identifiedDate){
            return "["+parsedDate[0]+":"+parsedDate[1]+"/"+parsedDate[2]+"/"+parsedDate[3]+":"+parsedDate[4]+":"+parsedDate[5]+"]";
        }
        return null;
    }

    private List<SentenceParser> parseSentences() {
        List<SentenceParser> parsedSentences = new ArrayList<SentenceParser>();
        for (String sentence : selectedSentences) {
            parsedSentences.add(parseSentence(sentence));
        }
        return parsedSentences;
    }

    private SentenceParser parseSentence(String sentence) {
        return new SentenceParser(sentence, elementSelector.getSelectedElement().getElementSimilar());
    }

    public static void main(String[] args) {
        
        //***Crear objecto de la clase command parser
        CommandParser cp = new CommandParser();
        
        //***Opcional. Asignar las acciones disponibles con un hash map <sinonimo,acción>
        //Estas son las acciones por default si no se especifica alguna
        //En el futuro se contempla el uso de un diccionario de verbos
        HashMap Actions = new HashMap();

        Actions.put("encender", "Encender");
        Actions.put("conectar", "Encender");
        Actions.put("activar", "Encender");
        Actions.put("prender", "Encender");

        Actions.put("apagar", "Apagar");
        Actions.put("desactivar", "Apagar");
        Actions.put("cortar", "Apagar");
        Actions.put("desconectar", "Apagar");

        cp.setActions(Actions);

        //***Asignar un arreglo de los elementos disponibles (Catálogo de elementos)
        String[] elements = {
            "foco del comedor",
            "foco de la sala 1",
            "foco de la sala 2",
            "focos de la sala",
            "foco de la habitación de samuel 1",
            "foco de la habitación de samuel 2",
            "foco del estudio",
            "foco de la cocina",
            "foco del baño 2",
            "foco exterior 1",
            "foco exterior 2",
            "foco exterior 3",
            "foco exterior 4",
            "foco del jardin de enfrente",
            "foco del jardin trasero",
            "foco del porton",
            "foco del baño 1"
        };
        cp.setElements(elements);
        
        //***Parsear un conjunto de oraciones que serán resultado de las variaciones de la api 
        //de voz de google dada una oración
        String[] sentences = {
            "enciende el foco de comedor bebés a las 8 de la noche al 50%",
            "enciende el foco de comedor el jueves a las 8 de la noche al 50%",
            "enciende foco del comedor despensa las 8 de la noche al 50%",
            "el cine . el comedor el jueves a las 8 de la noche al 50%",
            "dirección de fútbol comedor el jueves a las 8 de la noche al 50%",
            "enciende el foco del comedor el jueves a las 8 de la noche al 50%",
            "enciende el foco del comedor el bebé a las 8 de la noche al 50%"
        };
        
        //El parseo devuelve un objeto de la clase Command
        // {String Element(perteneciente a los elementos agregados), 
        //  String Action(perteneciente al catálogo de acciones),
        //  int Intensidad( 0 <= Intensidad <= 16, -1 no especificado),
        //  Calendar Time
        //}
        System.out.println("Caso 1. enciende el foco de comedor el jueves a las 8 de la noche al 50%");
        Command command = cp.parseCommand(sentences);
        
        
        System.out.println("Selected element: " + command.getElement());
        System.out.println("Selected action: " + command.getAction());
        System.out.println("Selected intensity: " + command.getIntensity());
        System.out.println("Selected time: " + command.getTime());
        
        sentences = new String[] {
            "enciende el foco del tren trasero a las 9 de la noche",
            "enciende tu carrito 0 a las 9 de la noche",
            "enciende el foco de tren trasero a las nueve noches",
            "enciende el foco de jardín trasero a las 9 de la noche"
        };
        
        //***Ejemplo de uso 2
        System.out.println("\n\nCaso 2. enciende el foco de jardín trasero a las 9 de la noche");
        command = cp.parseCommand(sentences);
        
        
        System.out.println("Selected element: " + command.getElement());
        System.out.println("Selected action: " + command.getAction());
        System.out.println("Selected intensity: " + command.getIntensity());
        System.out.println("Selected time: " + command.getTime());
        
        //Ejemplo de uso 3
        
        sentences = new String[]{
            "fútbol jardín trasero encendido",
            "foco del jardín trasero encendido",
            "foto de perfil de acero encendido"
        };
        
        System.out.println("\n\nCaso 3. foco del jardín trasero encendido");
        command = cp.parseCommand(sentences);
        
        System.out.println("Selected element: " + command.getElement());
        System.out.println("Selected action: " + command.getAction());
        System.out.println("Selected intensity: " + command.getIntensity());
        System.out.println("Selected time: " + command.getTime());
        
        //Ejemplo de uso 4
        System.out.println("\n\nCaso 4. foco de la habitación de samuel 1 encendido el viernes a las 8");
        sentences = new String[]{
            "foco de la intención samuel 1 encendido el viernes a las 8",
            "foco de la constitución de samuel 1 encendido el viernes a las 8",
            "foco de la ubicación de su muerte 1 encendido el viernes a las 8",
            "foco de la habitación de samuel 1 encendido el viernes a las 8",
            "foco del habitaciones 1 al 1 encendido el viernes a las 8"
        };
        
        command = cp.parseCommand(sentences);
        
        System.out.println("Selected element: " + command.getElement());
        System.out.println("Selected action: " + command.getAction());
        System.out.println("Selected intensity: " + command.getIntensity());
        System.out.println("Selected time: " + command.getTime());
        
         //Ejemplo de uso 5
        System.out.println("\n\nCaso 5. apaga el foco de la sala 2 el sábado 6 de octubre");
        
        sentences = new String[]{
            "apagar foco de los helados el sábado 6 de octubre",
            "apaga el foco de la sala 2 el sábado 6 de octubre",
            "apaga el foco de los salados estado civil tube",
            "mapa del foco de la sala 2 el sofrito tube",
            "apagar poco de la sala 2 el sábado 6 de octubre"
        };
        
        command = cp.parseCommand(sentences);
        
        System.out.println("Selected element: " + command.getElement());
        System.out.println("Selected action: " + command.getAction());
        System.out.println("Selected intensity: " + command.getIntensity());
        System.out.println("Selected time: " + command.getTime());
        
        //Ejemplo de uso 6
        System.out.println("\n\nCaso 6. apaga el foco de la sala 1");
        sentences = new String[]{
            "apaga el fútbol sala 1",
            "tapas del foco de la zona 1",
            "apaga el foco de la sala 1",
            "doctor el foco de la zona 1",
            "apagar el fútbol sala 1"
        };
        
        command = cp.parseCommand(sentences);
        
        System.out.println("Selected element: " + command.getElement());
        System.out.println("Selected action: " + command.getAction());
        System.out.println("Selected intensity: " + command.getIntensity());
        System.out.println("Selected time: " + command.getTime());
        
        //Ejemplo de uso 7
        System.out.println("\n\nCaso 7. foco de la sala 1");
        sentences = new String[]{
            "foco de la sala 1",
            "foto de la sala 1",
            "fotos de la sala 1",
            "sólo uno",
            "sube la sala 1",
            "foco de la salud",
            "foco de la zona 1"
        };
        command = cp.parseCommand(sentences);
        
        System.out.println("Selected element: " + command.getElement());
        System.out.println("Selected action: " + command.getAction());
        System.out.println("Selected intensity: " + command.getIntensity());
        System.out.println("Selected time: " + command.getTime());
        
        
        //Ejemplo de uso 8
        System.out.println("\n\nCaso 8. enciende el foco del comedor al 50%");
        sentences = new String[]{
            "enciende un poco el comedor 50%",
            "enciende poco el comedor 50%",
            "enciende el foco de comedor un 50%",
            "enciende el foco del comedor al 50%",
            "enciende el foco de comedor al 50%"
        };
        
                
        command = cp.parseCommand(sentences);
        
        System.out.println("Selected element: " + command.getElement());
        System.out.println("Selected action: " + command.getAction());
        System.out.println("Selected intensity: " + command.getIntensity());
        System.out.println("Selected time: " + command.getTime());

        //Ejemplo de uso 9
        System.out.println("\n\nCaso 9. enciende el foco del comedor a las 8 de la noche");
        sentences = new String[]{
            "enciende el foco del comedor a las 8 de la noche",
            "enciende el foco del comedor ana",
            "enciende uco el comedor ana wow"
        };

                
        command = cp.parseCommand(sentences);
        
        System.out.println("Selected element: " + command.getElement());
        System.out.println("Selected action: " + command.getAction());
        System.out.println("Selected intensity: " + command.getIntensity());
        System.out.println("Selected time: " + command.getTime());

        //Ejemplo de uso 10
        System.out.println("\n\nCaso 10. enciende el foco del comedor");
        sentences = new String[]{
            "enciende el foco del co",
            "100 de acuerdo",
            "enciende poco del comedor"
        };

                
        command = cp.parseCommand(sentences);
        
        System.out.println("Selected element: " + command.getElement());
        System.out.println("Selected action: " + command.getAction());
        System.out.println("Selected intensity: " + command.getIntensity());
        System.out.println("Selected time: " + command.getTime());
        
    }
}
