/*
 * HelpFrame.java
 *
 * Created on 2013-apr-29, 12:31:51
 */
package messages;

/**
 *
 * @author Tobias Oskarsson & Adnan Dervisevic
 */
public class HelpFrame extends javax.swing.JFrame {

    /**
     * Skapar en ny helpFrame.
     */
    public HelpFrame() {
        initComponents();
    }

    /**
     * Initializerar formuläret.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextArea1.setBackground(new java.awt.Color(240, 240, 240));
        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("Hej och välkommen till ditt serverchatprogram!\n\nVi på Veritas Agent Byrå (VAB) är väldigt glada över att du har valt\noss för att hjälpa dig med dina bekymmer idag.\n\nFör att starta en uppkoppling till en klient, gå in på Anslut (Ctrl+L) \nEfter att du  har kopplat dig till din agent kan ni börja chatta.\n\nTryck på submit för att skicka ditt meddelande.\n\nDu kan koppla från med Ctrl+K eller Menyalternativet.\n\nHa en fortsatt trevlig dag!\n\nMed Vänliga Hälsningar,\nVAB");
        jTextArea1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 1, 1));
        jTextArea1.setCaretColor(new java.awt.Color(240, 240, 240));
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(424, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Main funktionen som startar programmet.
     * @param args Argumenten som skickas med när programmet startar.
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new HelpFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
