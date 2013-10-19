/*
 * OmFrame.java
 *
 * Created on 2013-apr-29, 12:39:48
 */
package messages;

/**
 *
 * @author Tobias Oskarsson & Adnan Dervisevic
 */
public class OmFrame extends javax.swing.JFrame {

    /**
     * Skapar en ny omFrame.
     */
    public OmFrame() {
        initComponents();
    }

    /**
     * Initializerar formuläret.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextArea1.setBackground(new java.awt.Color(240, 240, 240));
        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("Hej och välkommna till vårt program. \n\n\"Vi\" är Adnan Dervisevic och Tobias Oskarsson som går \ndatateknisk systemutveckling på Högskolan Väst.\n\nVi har skapat det här programmet under Christer Selvefors\nsom en övning i nätverksprogrammering med Java.\n\nVi hoppas Ni har haft en trevlig upplevelse med vårat \nprogram!\n");
        jTextArea1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 1, 1));
        jTextArea1.setCaretColor(new java.awt.Color(240, 240, 240));
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
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
                new OmFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
