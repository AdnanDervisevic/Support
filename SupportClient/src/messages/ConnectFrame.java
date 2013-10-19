/*
 * ConnectFrame.java
 *
 * Created on 2013-apr-29, 12:45:32
 */
package messages;

import java.util.regex.Pattern;
import javax.swing.*;
import java.util.regex.Matcher;


/**
 *
 * @author Tobias Oskarsson & Adnan Dervisevic
 */
public class ConnectFrame extends javax.swing.JFrame {

    /**
     * Skapar en ny ConnectFrame.
     */
    public ConnectFrame() {
        initComponents();
    }

    /** 
     * Initializerar formuläret.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        IPField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        PortNr = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        ConnectBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        IPField.setText("localhost");
        IPField.setToolTipText("Skriv in IP nummer här");

        jLabel1.setText("IP Nummer");

        PortNr.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1337", "3015", "4114", "3016" }));

        jLabel2.setText("Port");

        ConnectBtn.setText("Anslut");
        ConnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(IPField, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(PortNr, 0, 61, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addComponent(ConnectBtn, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IPField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PortNr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ConnectBtn)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * När man trycker på Anslut så valideras ip't och sedan kallar på
     * klientens Connect funktion med det valda ip't och porten.
     * @param evt Action eventet som skickas med.
     */
    private void ConnectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectBtnActionPerformed
       /**
        * Regex mönster för att matcha en IP address
        */
        String str = 
		"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
       /**
        * Pattern som skapas för att användas ihop med regex
        */
       Pattern pattern;
       /**
        * Matcher som skapas för att användas ihop med regex
        */
       Matcher matcher;
       /**
        * IP Sträng till servern
        */
       String ip = IPField.getText();
       /**
        * Port för servern
        */
       String port = this.PortNr.getSelectedItem().toString();
       pattern = Pattern.compile(str);
       matcher = pattern.matcher(ip);
       
       /**
        * Kopplar upp till servern om ip matchar eller är localhost, visar en error ruta om det är en ogiltig input
        */
       if (matcher.matches() || ip.equalsIgnoreCase("localhost")) {
            Client.Connect(ip, Integer.parseInt(port));
            this.dispose();
        }
       else {
           JOptionPane.showMessageDialog(null, "Error! Must be valid IP number");
       }
    }//GEN-LAST:event_ConnectBtnActionPerformed

    /**
     * Main funktionen som startar programmet.
     * @param args Argumenten som skickas med när programmet startar.
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ConnectFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ConnectBtn;
    private javax.swing.JTextField IPField;
    private javax.swing.JComboBox PortNr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
