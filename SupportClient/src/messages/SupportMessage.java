/*
 * SupportMessage.java
 *
 * Created on 2013-apr-29, 12:55:57
 */
package messages;

import java.io.*;

/**
 *
 * @author Tobias Oskarsson & Adnan Dervisevic
 */
public class SupportMessage implements Serializable {
    
    /**
     * Två statiska ints som används som en typ av enumeration.
     * MESSAGE är 0 och DISCONNECT är 1.
     */
    public static final int MESSAGE = 0, DISCONNECT = 1;
    
    /**
     * Vilken typ är meddelandet av.
     */
    private int type;
    
    /**
     * Hur lyder meddelandet.
     */
    private String message;
    
    /**
     * Konstruktor som skapar ett meddelande.
     * @param type Typen av meddelande, 0 för MESSAGE och 1 för DISCONNECT.
     * @param message Meddelandet som ska skapas.
     */
    SupportMessage(int type, String message){
        this.type = type;
        this.message = message;
    }
    
    /**
     * Getter för typen.
     * @return Returnerar en int beroende på vilken typ meddelandet är av.
     */
    public int getType() {
        return type;
    }
    
    /**
     * Getter för meddelandet.
     * @return Returnerar meddelandet.
     */
    public String getMessage() {
        return message;
    }
}
