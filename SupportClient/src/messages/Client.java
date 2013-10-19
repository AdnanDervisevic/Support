/**
 * Client.java
 *
 * Created on 2013-apr-29, 12:22:42
 */
package messages;

import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 *
 * @author Tobias Oskarsson & Adnan Dervisevic
 */
public class Client extends JFrame {
    /**
     * Ett rekursivt objekt som innehåller en instans av vår klient
     */
    private static Client Instance;
    
    /**
     * Username string
     */
    private String username;
    
    /**
     * Input stream 
     */
    private ObjectInputStream sInput;
    
    /**
     * Output stream
     */
    private ObjectOutputStream sOutput;
    
    /**
     * En socket som används för att koppla upp sig mot en server
     */
    private Socket socket;
            
    /**
     * En boolean som avslutar uppkopplingen om den sätts till true
     */
    private boolean disconnected = false;
    
    /**
     * Skapar en uppkoppling mot inputtade parameterar om en instans har skapats
     * @param ip Ip till servern.
     * @param port Porten till servern.
     */
    public static void Connect(String ip, int port) 
    {
        // Om instancen inte är null så anslut till servern.
        if (Instance != null)
            Instance.Conn(ip, port);
    }
    
    /**
     * Skapar en uppkoppling mot inputtade parameterar har skapats
     * @param ip IP't till servern.
     * @param port Porten till servern.
     */
    public void Conn(String ip, int port) 
    {
        // En do-while loop som ber användaren mata in ett användarnamn.
        // loopen körs tills användarnamnet inte är tomt.
        do
        {
            this.username = JOptionPane.showInputDialog(null, "Användarnamn?");
        } while (this.username.trim().isEmpty());
        
        // En try-catch sats som försöker skapa en ny socket.
        try {
            // skapar en ny socket på ip't och porten.
            this.socket = new Socket(ip, port);
        }
        catch (Exception ex) {
            // Om något gick fel skrivs ett felmeddelande ut.
            this.errorMsg.setText("Kunde inte ansluta till servern.");
            this.errorMsg.setVisible(true);
            return;
        }
        
        // Om det lyckades så skrivs ett meddelande ut.
        String msg = "Lyckad anslutning.";
        this.errorMsg.setText(msg);
        
        // Try-catch sats som försöker skapa en input och output stream.
        try {
            this.sInput = new ObjectInputStream(socket.getInputStream());
            this.sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
            // Om något går fel skapas ett felmeddelande och skrivs ut.
            this.errorMsg.setText("Kunde inte skapa en input och output stream.");
            return;
        }
        
        // Skapar en ny ListenFromServer objekt och kör start funktionen.
        new ListenFromServer().start();
        
        // Try-catch sats som försöker skriva användarnamnet till servern.
        try
        {
            this.sOutput.writeObject(username);
        } catch (IOException IOEx) {
            // Går det inte att skicka användarnamnet får man ett felmeddelande.
            this.errorMsg.setText("Kunde inte logga in.");
            
            // Och kopplas från.
            this.disconnect();
            return;
        }
        
        // Man har nu anslutit så vi enablar koppla från knappen och disablar anslutningsknappen.
        this.disconnectMenuItem.setEnabled(true);
        this.connectMenuItem.setEnabled(false);
    }
    
    /**
     * Innehåller meddelande och typ av meddelande som blir mottagen av server.  
     * @param msg Ett meddelande av typ SupportMessage 
     */
    void sendMessage(SupportMessage msg) 
    {
        // Try-catch sats som försöker skicka ett SupportMessage meddelande till servern.
        try {
            this.sOutput.writeObject(msg);
            
            // Om inte meddelandet är av typen DISCONNECT så ska meddelandet skrivas ut
            // på clientens chatfönster.
            if (msg.getType() != SupportMessage.DISCONNECT)
                this.messageArea.append(" " + username + ": " + msg.getMessage() + "\n");
            
        } catch (IOException e) {
            // Om meddelandet inte kunde skickas till servern så visas ett felmeddelande.
            this.errorMsg.setText("Kunde inte skicka meddelandet till servern.");
        }
    }
    
    /**
     * Stänger uppkopplingen när diverse delmål möts
     */
    private void disconnect() 
    {
        // Skickar ett tomt disconnect meddelande till servern.
        this.sendMessage(new SupportMessage(SupportMessage.DISCONNECT, ""));
        
        // Enablar connect knappen och disablar disconnect knappen.
        this.connectMenuItem.setEnabled(true);
        this.disconnectMenuItem.setEnabled(false);
        // vi har disconnectat så sätt disconnected till true.
        this.disconnected = true;
        
        // Try-catch sats som försöker stänga input strömmen.
        try {
            if (this.sInput != null)
                this.sInput.close();
        } catch(Exception e) {}
        
        // Try-catch sats som försöker stänga output strömmen.
        try {
            if(this.sOutput != null) 
                this.sOutput.close();
        } catch(Exception e) {}
        
        // Try-catch sats som försöker stänga socketen.
        try{
            if(this.socket != null) 
                this.socket.close();
        } catch(Exception e) {}
        
        // Visar meddelande om att man kopplats ifrån.
        this.errorMsg.setText("Kopplades ifrån");
    }
    
    /**
     * En class som extendar Thread. Används till att lyssna på meddelanden från servern.
     * @author Tobias Oskarsson & Adnan Dervisevic
     */
    class ListenFromServer extends Thread 
    {
        /**
         * En metod som körs när tråden körs.
         */
        @Override
        public void run()
        {
            // En while loop som körs till man har disconnectat.
            while(true)
            {
                // Try-catch sats som försöker läsa ett meddelande från servern.
                try
                {
                    String msg = (String) sInput.readObject();
                    
                    // Skriver ut meddelandet i textarean.
                    messageArea.append(" " + msg + "\n");
                } 
                catch(IOException e)
                {
                    // Om det inte går att ta emot något meddelande så kollar den så vi inte har disconnectat.
                    if (!disconnected)
                    {
                        errorMsg.setText("Servern har stängt anslutningen");
                        disconnected = false;
                    }

                    break;
                } catch(ClassNotFoundException e){}
            }
        }
    }

    /**
     * Skapar en ny klient (instanserad klient)
     */
    public Client() {
        initComponents();
        Instance = this;
    }

    /** 
     * Initializerar formuläret.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageArea = new javax.swing.JTextArea();
        inputField = new javax.swing.JTextField();
        submitButton = new javax.swing.JButton();
        errorMsg = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        archiveMenu = new javax.swing.JMenu();
        quitMenuItem = new javax.swing.JMenuItem();
        connectMenu = new javax.swing.JMenu();
        connectMenuItem = new javax.swing.JMenuItem();
        disconnectMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpMenuItem = new javax.swing.JMenuItem();
        aboutMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        jMenu5.setText("File");
        jMenuBar2.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar2.add(jMenu6);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(168, 208, 214));

        messageArea.setColumns(20);
        messageArea.setEditable(false);
        messageArea.setRows(5);
        jScrollPane1.setViewportView(messageArea);

        inputField.setText("Skriv meddelande...");
        inputField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                clearInput(evt);
            }
        });

        submitButton.setText("Skicka");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        errorMsg.setFont(new java.awt.Font("Segoe UI", 0, 14));

        archiveMenu.setText("Arkiv");

        quitMenuItem.setText("Avsluta");
        quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Avsluta(evt);
            }
        });
        archiveMenu.add(quitMenuItem);

        jMenuBar1.add(archiveMenu);

        connectMenu.setText("Anslutning");

        connectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        connectMenuItem.setText("Anslut");
        connectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectMenuItemActionPerformed(evt);
            }
        });
        connectMenu.add(connectMenuItem);

        disconnectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_MASK));
        disconnectMenuItem.setText("Koppla ifrån");
        disconnectMenuItem.setEnabled(false);
        disconnectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onDisconnect(evt);
            }
        });
        connectMenu.add(disconnectMenuItem);

        jMenuBar1.add(connectMenu);

        helpMenu.setText("Hjälp");

        helpMenuItem.setText("Här finns hjälp!");
        helpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpBox(evt);
            }
        });
        helpMenu.add(helpMenuItem);

        jMenuBar1.add(helpMenu);

        aboutMenu.setText("Om Oss");

        aboutMenuItem.setText("Vilka är vi?");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickUs(evt);
            }
        });
        aboutMenu.add(aboutMenuItem);

        jMenuBar1.add(aboutMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(errorMsg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(submitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(errorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Metod som körs när man trycker på Avsluta knappen.
     * Metoden skickar ett disconnect meddelande och avslutar klienten.
     * @param evt Action eventet som skickas med.
     */
    private void Avsluta(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Avsluta
        // TODO add your handling code here:
        if (this.sOutput != null)
            sendMessage(new SupportMessage(SupportMessage.DISCONNECT, ""));
        System.exit(0);
    }//GEN-LAST:event_Avsluta

    /**
     * Metod som körs när man trycker på Help knappen.
     * Metoden startar en ny helpFrame.
     * @param evt Action eventet som skickas med.
     */
    private void HelpBox(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HelpBox
        // TODO add your handling code here
        new HelpFrame().setVisible(true);
    }//GEN-LAST:event_HelpBox

    /**
     * Metod som körs när man trycker på Om oss knappen.
     * Metoden startar en ny omFrame.
     * @param evt Action eventet som skickas med.
     */
    private void clickUs(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clickUs
        // TODO add your handling code here:
        new OmFrame().setVisible(true);
    }//GEN-LAST:event_clickUs

    /**
     * Metod som körs när man trycker på Anslut knappen.
     * Metoden startar en ny connectFrame.
     * @param evt Action eventet som skickas med.
     */
    private void connectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectMenuItemActionPerformed
        new ConnectFrame().setVisible(true);
    }//GEN-LAST:event_connectMenuItemActionPerformed

    /**
     * Metod som körs när man trycker på skicka knappen.
     * Metoden skickar meddelandet till servern.
     * @param evt Action eventet som skickas med.
     */
    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        // TODO add your handling code here:
        sendMessage(new SupportMessage(SupportMessage.MESSAGE, inputField.getText()));
        inputField.setText("");
    }//GEN-LAST:event_submitButtonActionPerformed

    /**
     * Metod som körs när text fältet får focus.
     * Metoden som tar bort allt i input fältet.
     * @param evt Action eventet som skickas med.
     */
    private void clearInput(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clearInput
        // TODO add your handling code here:
        inputField.setText("");
    }//GEN-LAST:event_clearInput

    /**
     * Metoden som körs när man trycker på Disconnect knappen.
     * Metod som disconnectar från servern.
     * @param evt Action eventet som skickas med.
     */
    private void onDisconnect(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onDisconnect
        // TODO add your handling code here:
        this.disconnect();
    }//GEN-LAST:event_onDisconnect

    /**
     * Main funktionen som startar programmet.
     * @param args Argumenten som skickas med när programmet startar.
     */
    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Client().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu aboutMenu;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenu archiveMenu;
    private javax.swing.JMenu connectMenu;
    private javax.swing.JMenuItem connectMenuItem;
    private javax.swing.JMenuItem disconnectMenuItem;
    private javax.swing.JLabel errorMsg;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JTextField inputField;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea messageArea;
    private javax.swing.JMenuItem quitMenuItem;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables
}