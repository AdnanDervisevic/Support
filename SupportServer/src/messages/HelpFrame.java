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
        HelpArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        HelpArea.setBackground(new java.awt.Color(240, 240, 240));
        HelpArea.setColumns(20);
        HelpArea.setEditable(false);
        HelpArea.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        HelpArea.setLineWrap(true);
        HelpArea.setRows(5);
        HelpArea.setText("Hej och välkommen till din chattserver!\n\nDu är en värdefull supportagent för  Veritas AgentByrå (VAB) \noch vi vill att du skall kunna effektivt använda din serverapplikation \nför att hjälpa de mindre lyckligt lottad som har  lite problem.\n\nFör att starta server gå in på Anslutning (Ctrl+L). Efter att du har \nstartat en server kan du börja acceptera anslutningar från \nalla dina trevliga supportkunder. CTRL+K För frånkoppling eller i menyn.\n\nHa en fortsatt trevlig dag!\n\nMed Vänliga Hälsningar,\nVAB");
        HelpArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 1, 1));
        HelpArea.setCaretColor(new java.awt.Color(240, 240, 240));
        jScrollPane2.setViewportView(HelpArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(460, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
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
    private javax.swing.JTextArea HelpArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
