package Principal;

import java.util.HashSet;
import javax.swing.DefaultListModel;

public class panelEstados extends javax.swing.JPanel {

    public panelEstados() {
        initComponents();
    }
    
    //Rellena la lista de estados mostrada gráficamente
    public void setEstados(HashSet<String> estados)
    {
        DefaultListModel modeloLista = new DefaultListModel();
        modeloLista.addAll(estados);
        lista.setModel(modeloLista);
    }

    //Obtiene los estados seleccionados de la lista mostrada al usuario
    public HashSet<String> getEstados()
    {
        HashSet<String> estadosSel = new HashSet();
        estadosSel.addAll(lista.getSelectedValuesList()); 
        return estadosSel;
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lista = new javax.swing.JList<>();

        lista.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        jScrollPane1.setViewportView(lista);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lista;
    // End of variables declaration//GEN-END:variables
}
