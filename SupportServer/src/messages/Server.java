/*
 * Server.java
 *
 * Created on 2013-apr-29, 14:17:53
 */
package messages;

import java.net.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

/**
 *
 * @author Tobias Oskarsson & Adnan Dervisevic
 */
public class Server extends JFrame {

    /**
     * Unikt id för varje tråd
     */
    private static int uniqueId;
    
    /**
     * Rekursivt objekt som är en instans av servern
     */
    private static Server Instance;
    
    /**
     * Håller koll på hur många chattflikar servern har uppe
     */
    private static int tabCount = 0;
    
    /**
     * Lista med alla olika trådar som servern har kopplad mot klienter
     */
    private ArrayList<ClientThread> clientThreads = new ArrayList<ClientThread>();
    
    /**
     * Lista med alla tabbar som är kopplade mot klienter
     */
    private ArrayList<ClientTab> clientTabs = new ArrayList<ClientTab>();
    
    /**
     * Sätts till false efter man öppnat sin första tab, håller koll på om det ska visas en startsida eller inte
     */
    private boolean firstTab = true;
    
    /**
     * Kollar om servern ska fortsätta köra
     */
    private boolean serverStop = false;
    
    /**
     * Håller koll på serverns port
     */
    private int port;
    
    /**
     * Namn på supportpersonal som sätts i programmet varje gång
     */
    private String supportName = "";
    
    /**
     * Startar servern. 
     * @param ip Ip't som servern lyssnar på.
     * @param port Porten som servern använder sig utav.
     */
    public static void Start(String ip, int port) {
        Instance.InternalStart(ip, port);
    }
    
    /**
     * Startar servern.
     * @param ip Ip't som servern lyssnar på.
     * @param port Porten som servern använder sig utav.
     */
    private void InternalStart(String ip, int port) {
        // Sätter porten och serverStop till false.
        this.port = port;
        this.serverStop = false;
        
        // En do-while loop som frågar efter supportens användarnamn.
        do
        {
            this.supportName = JOptionPane.showInputDialog(null, "Användarnamn?");
        } while (this.supportName.trim().isEmpty());
        
        // Skapar ett nytt object av classen "LookForClients".
        LookForClients t = new LookForClients();
        
        // Kör funktionen start i LookForClients klassen.
        t.start();
        
        // Ändrar status till "väntar på klienter".
        this.startLabel.setText("Väntar på klienter");
        
        // Enablar disconnect knappen och disablar connect knappen.
        this.disconnectMenuItem.setEnabled(true);
        this.connectMenuItem.setEnabled(false);
    }
    
    /**
     * Stannar servern om man trycker på disconect. 
     * Ändrar knapparna så att endast knappen för att koppla upp sig är aktiv
     */
    private void Stop() {
        // Sätter serverStop till true
        this.serverStop = true;
        
        // Enablar connect knappen och disablar disconnect knappen.
        this.disconnectMenuItem.setEnabled(false);
        this.connectMenuItem.setEnabled(true);
        
        // En try-catch sats som skapar en ny socket på localhost och porten.
        try {
            new Socket("localhost", port);
        } catch(Exception e) { }
        
        // Status ändras till "Väntar på klienter".
        this.startLabel.setText("Väntar på serverstart");
    }
    
    /**
     * Tar bort klienter. 
     * Söker igenom varje klienttråd och avslutar uppkopplingen mot klienten, och tar bort tabben. 
     * Om man är på noll klienter uppkopplade skall det visas en startskärm.
     * @param id Id't på den klient som ska tas bort.
     */
    @Override
    public synchronized void remove(int id) {        
        // Loopar igenom att klienttrådar.
        for (int i = 0; i < clientThreads.size(); i++) {
            // Hämtar den nuvarnade klienttråden.
            ClientThread ct = clientThreads.get(i);
            
            // Om klienttråden matchar ID't
            if (ct.id == id) {
                // ta bort klienttråden.
                this.clientThreads.remove(i);
                // Ta bort tabben som tillhör den tråden.
                this.Tabs.remove(ct.tabIndex);
                
                // Minska tabCount med ett.
                tabCount--;
                
                // Om tabcount är 0 så ska status ändras till "Väntar på klienter".
                if (tabCount == 0)
                {
                    this.startLabel.setText("Väntar på klienter");
                    // Lägg till status tabben.
                    this.Tabs.addTab("Startsida", startLabel);
                    // Det är första tabben så sätt till true.
                    this.firstTab = true;
                }
                
                return;
            }
        }
    }
    
    /**
     * Skapar en ny tabb för en chatsession med klient
     * @param username Användarnamn på klienten som kopplar upp sig, skrivs ut i tabbens titel
     * @param id Id't för tabben.
     */
    private void newTab(String username, int id) {
        // Om det är första tabben så ska alla tabbs tas bort.
        if (this.firstTab){
            this.Tabs.removeAll();
            this.firstTab = false;
        }
        
        // Skapa en ny klienttabb.
        ClientTab tab = new ClientTab(id);
        // Lägg till tabben.
        this.clientTabs.add(tab);
        
        // Lägg till tabben.
        this.Tabs.add(username, tab);
        // Öka tabCount med en.
        tabCount++;
    }
    
    /**
     * Skriver ut meddelande
     * @param tabIndex Vilken tab meddelandet kommer till
     * @param username Vilket användarnamn klienten har
     * @param msg Meddelandet från klienten
     */
    private void incomingMessage(int tabIndex, String username, SupportMessage msg) {
        ((ClientTab)Tabs.getComponentAt(tabIndex)).messageArea.append(" " + username + ": " + msg.getMessage() + "\n");
    }
    
    /**
     * En klass som innehåller all information en en klient.
     * @author Tobias Oskarsson & Adnan Dervisevic
     */
    class ClientThread extends Thread 
    {
        
        /**
         * Socketen till klienten.
         */
        Socket socket;
        
        /**
         * Input streamen till klienten.
         */
        ObjectInputStream sInput;
        
        /**
         * Output streamen till klienten.
         */
        ObjectOutputStream sOutput;
        
        /**
         * Id't på klienten.
         */
        int id;
        
        /**
         * Tabb index till klienten.
         */
        int tabIndex;
        
        /**
         * Klientens användarnamn.
         */
        String username;
        
        /**
         * Meddelandet som kom från klienten.
         */
        SupportMessage msg;
        
        /**
         * Konstruktor som skapar en ny klient.
         * @param socket Klientens socket.
         * @param tabIndex Klientens tabIndex.
         */
        ClientThread(Socket socket, int tabIndex) 
        {
            // Sätter id, socket och tabIndex.
            this.id = ++uniqueId;
            this.socket = socket;
            this.tabIndex = tabIndex;
            
            // Try-catch sats som försöker skapa en output och input stream samt läser klientens användarnamn.
            try {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                username = (String)sInput.readObject();
            } catch (IOException e) { } catch (ClassNotFoundException e) { }
            
            // Skapar en ny tab för klienten.
            newTab(username, id);
        }
        
        /**
         * Metod som körs när trådens start funktion körs.
         */
        @Override
        public void run() 
        {
            // Sätter disconnect till false då vi inte är disconnectad.
            boolean disconnect = false;
            
            // Medan man är ansluten
            while (!disconnect) 
            {
                // Try-catch sats som försöker läsa in ett support meddelande från klienten.
                // om något går fel så avbryter den.
                try {
                    msg = (SupportMessage)sInput.readObject();
                } catch (IOException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    break;
                }
                
                // Switch sats som kollar vilken typ meddelandet är av.
                switch (msg.getType()) {
                    // Är meddelandet av typen MESSAGE så läses meddelandet.
                    case SupportMessage.MESSAGE:
                        incomingMessage(tabIndex, username, msg);
                        break;
                        
                    // Är meddelandet av typen DISCONNET så sätts disconnect till true.
                    case SupportMessage.DISCONNECT:
                        disconnect = true;
                        break;
                }
            }
            
            // Tar bort klienten och stänger den.
            remove(id);
            close();
        }
        
        /**
         * Skickar ett meddelande till klienten
         * @param msg Meddelandet som ska skickas till klienten.
         * @return Returnerar true om meddelandet har skickats, annars false.
         */
        private boolean writeMsg(String msg) {
            if (!socket.isConnected()) 
            {
                close();
                return false;
            }
            
            try {
                sOutput.writeObject(msg);
            } 
            catch (IOException e) 
            { }
            
            return true;
        }
        
        /**
         * Stänger kopplingen
         */
        private void close() {
            // Try-catch sats som stänger output streamen.
            try {
                if (this.sOutput != null) 
                    this.sOutput.close();
            } catch (Exception e) { }

            // Try-catch sats som stänger input streamen.
            try {
                if (this.sInput != null) 
                    this.sInput.close();
            } catch (Exception e) { }
            
            // Try-catch sats som stänger socketen.
            try {
                if (this.socket != null) 
                    this.socket.close();
            } catch (Exception e) { }
        }
    }
    
    /**
     * Tråd som hela tiden körs och väntar på klienter som ska koppla upp sig
     * @author Tobias Oskarsson & Adnan Dervisevic
     */
    class LookForClients extends Thread 
    {
        /**
         * Metod som körs när trådens start metod körs.
         */
        @Override
        public void run() 
        {
            // Try-catch sats.
            try 
            {
                // Skapar en ServerSocket på den valda porten.
                ServerSocket serverSocket = new ServerSocket(port);

                // While loop som körs tills serverStop är satt till true.
                while (!serverStop) {
                    // Skapar en ny socket till klienten.
                    Socket socket = serverSocket.accept();

                    // Om servern ska stängas så breakar vi.
                    if (serverStop)
                        break;

                    // Skapar en ny klientTråd till klienten
                    ClientThread t = new ClientThread(socket, tabCount);
                    // Lägger till klienttråden i listan.
                    clientThreads.add(t);
                    // kör trådens start metod.
                    t.start();
                }
                
                // Try-catch sats som försöker stänga ner servern.
                try 
                {                
                    // Stänger server socketen.
                    serverSocket.close();
                    // Loopar igenom alla klienter och stänger dom.
                    for (int i = 0; i < clientThreads.size(); i++) {
                        ClientThread ct = clientThreads.get(i);
                        try {
                            ct.sInput.close();
                            ct.sOutput.close();
                            ct.socket.close();
                        } catch (IOException e) { JOptionPane.showMessageDialog(null, "Kunde inte stänga anslutningen till klienten med id: " + ct.id); }
                    }
                } catch (Exception e) { JOptionPane.showMessageDialog(null, "Kunde inte stänga av servern."); }
            } catch (IOException e) { JOptionPane.showMessageDialog(null, "Kunde inte starta servern."); }
        }
    }
    
    /**
     * En klass som extendar från JLayeredPane som är en klienttab.
     * @author Tobias Oskarsson & Adnan Dervisevic
     */
    class ClientTab extends JLayeredPane 
    {
        /**
         * Input fältet där supporten skriver meddelandet till klienten.
         */
        private JTextField inputField = new JTextField();
        
        /**
         * Submit knapp som supporten trycker på för att skicka meddelandet.
         */
        private JButton submitButton = new JButton();
        
        /**
         * Scroll pane för att scrolla chatten.
         */
        private JScrollPane scrollPane = new JScrollPane();
        
        /**
         * Id't på tabben.
         */
        private int id = 0;
        
        /**
         * Text arean för tabben.
         */
        public JTextArea messageArea = new JTextArea();

        /**
         * Konstrurerar en ny tabb.
         */
        public ClientTab(int id) 
        {
            // Sätter inputField värden.
            this.inputField.setText("Skriv meddelande här !");
            this.inputField.setBounds(10, 340, 500, 30);
            this.inputField.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent evt) {
                    clearInput(evt);
                }
            });
            
            // Lägger till inputField i JLAyeredPanen.
            this.add(inputField, javax.swing.JLayeredPane.DEFAULT_LAYER);

            // Sätter submitButton värden.
            this.submitButton.setText("Skicka");
            this.submitButton.setBounds(517, 340, 73, 30);
            this.submitButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    Submit(evt);
                }
            });
            
            // Lägger till submitButton i JLAyeredPanen.
            this.add(submitButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

            // Sätter messageArea värden.
            this.messageArea.setColumns(20);
            this.messageArea.setEditable(false);
            this.messageArea.setRows(5);
            
            // Sätter scrollPane värden.
            this.scrollPane.setViewportView(messageArea);
            this.scrollPane.setBounds(10, 10, 580, 320);
            
            // Lägger till scrollPane i JLAyeredPanen.
            this.add(scrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

            // Sätter tabb id't
            this.id = id;
        }

        /**
         * Metoden som körs när supporten trycker på Skicka knappen.
         * @param evt 
         */
        private void Submit(java.awt.event.ActionEvent evt) 
        {
            // Loopar igenom alla klienter och letar efter rätt id.
            for (int i = 0; i < clientThreads.size(); i++)
            {
                ClientThread ct = clientThreads.get(i);
                
                // Kollar om det är rätt id.
                if (ct.id == this.id)
                {
                    // Skickar ett meddelande till den klienten.
                    ct.writeMsg(supportName + ": " + this.inputField.getText());
                    // Skriver meddelandet i text arean.
                    this.messageArea.append(" " + supportName + ": " + this.inputField.getText() + "\n");
                    this.inputField.setText("");
                }
            }
        }
        
        /**
         * Metod som körs när input fältet får focus.
         * Metoden tar bort allt i input fältet.
         * @param evt Focus eventet som skickas med.
         */
        private void clearInput(java.awt.event.FocusEvent evt) {
            this.inputField.setText("");
        }
    }

    /**
     * Konstrurerar en ny server.
     */
    public Server() {
        initComponents();
        Instance = this;
    }

    /**
     * Initializerar formuläret.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Tabs = new javax.swing.JTabbedPane();
        startLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        archiveMenu = new javax.swing.JMenu();
        quitMenuItem = new javax.swing.JMenuItem();
        connectMenu = new javax.swing.JMenu();
        connectMenuItem = new javax.swing.JMenuItem();
        disconnectMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpMenuItem = new javax.swing.JMenuItem();
        aboutMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        startLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startLabel.setText("Väntar på serverstart");
        Tabs.addTab("Startsida", startLabel);

        archiveMenu.setText("Arkiv");

        quitMenuItem.setText("Avsluta");
        quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quit(evt);
            }
        });
        archiveMenu.add(quitMenuItem);

        menuBar.add(archiveMenu);

        connectMenu.setText("Anslutning");

        connectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        connectMenuItem.setText("Anslut");
        connectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onConnect(evt);
            }
        });
        connectMenu.add(connectMenuItem);

        disconnectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_MASK));
        disconnectMenuItem.setText("Koppla ifrån");
        disconnectMenuItem.setEnabled(false);
        disconnectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnect(evt);
            }
        });
        connectMenu.add(disconnectMenuItem);

        menuBar.add(connectMenu);

        helpMenu.setText("Hjälp");

        helpMenuItem.setText("Här finns hjälp!");
        helpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                help(evt);
            }
        });
        helpMenu.add(helpMenuItem);

        menuBar.add(helpMenu);

        aboutMenu.setText("Om oss");

        aboutMenuItem.setText("Vilka är vi?");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickOmOss(evt);
            }
        });
        aboutMenu.add(aboutMenuItem);

        menuBar.add(aboutMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Metod som körs när man trycker på Om oss knappen.
     * Metoden startar en ny omFrame.
     * @param evt Action eventet som skickas med.
     */
    private void clickOmOss(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clickOmOss
        // TODO add your handling code here:
        new OmFrame().setVisible(true);
    }//GEN-LAST:event_clickOmOss

    /**
     * Metod som körs när man trycker på Avsluta knappen.
     * Metoden skickar ett disconnect meddelande och avslutar klienten.
     * @param evt Action eventet som skickas med.
     */
    private void quit(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quit
        // TODO add your handling code here:
        this.Stop();
        System.exit(0);
    }//GEN-LAST:event_quit

    /**
     * Metod som körs när man trycker på Anslut knappen.
     * Metoden startar en ny connectFrame.
     * @param evt Action eventet som skickas med.
     */
    private void onConnect(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onConnect
        // TODO add your handling code here:
        new ConnectFrame().setVisible(true);
    }//GEN-LAST:event_onConnect

    /**
     * Metoden som körs när man trycker på Disconnect knappen.
     * Metod som disconnectar från servern.
     * @param evt Action eventet som skickas med.
     */
    private void disconnect(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnect
        // TODO add your handling code here:
        this.Stop();
    }//GEN-LAST:event_disconnect

    /**
     * Metod som körs när man trycker på Help knappen.
     * Metoden startar en ny helpFrame.
     * @param evt Action eventet som skickas med.
     */
    private void help(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_help
        // TODO add your handling code here:
        new HelpFrame().setVisible(true);
    }//GEN-LAST:event_help

    /**
     * Main funktionen som startar programmet.
     * @param args Argumenten som skickas med när programmet startar.
     */
    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Server().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Tabs;
    private javax.swing.JMenu aboutMenu;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenu archiveMenu;
    private javax.swing.JMenu connectMenu;
    private javax.swing.JMenuItem connectMenuItem;
    private javax.swing.JMenuItem disconnectMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem quitMenuItem;
    private javax.swing.JLabel startLabel;
    // End of variables declaration//GEN-END:variables
}