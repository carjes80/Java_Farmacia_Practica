/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import models.Purchases;
import models.PurchasesDao;

/**
 *
 * @author carje
 */
public class Print extends javax.swing.JFrame {
    Purchases purchase = new Purchases();
    PurchasesDao purchase_dao = new PurchasesDao();
    DefaultTableModel model = new DefaultTableModel();
    
    
    public Print(int id) {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Factura de compra");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        txt_invoice.setText("-00000"+id);
        ListAllPurchasesDetail(id);
        calculatePurchase();
    }
    
    public void ListAllPurchasesDetail(int id){
        List<Purchases> list = purchase_dao.listPurchaseDetailQuery(id);
        model = (DefaultTableModel) purchase_detail_table.getModel();
        Object[] Row = new Object[7];
        for (int i=0; i<list.size();i++){
            Row[0] = list.get(i).getProduct_name();
            Row[1] = list.get(i).getPurchase_amount();
            Row[2] = list.get(i).getPurchase_price();
            Row[3] = list.get(i).getPurchase_subtotal();
            Row[4] = list.get(i).getSupplier_name_product();
            Row[5] = list.get(i).getPurchaser();
            Row[6] = list.get(i).getCreated();
            model.addRow(Row);
  
        }
        purchase_detail_table.setModel(model);
        
    }
    
    
    //calcular el total
    public void calculatePurchase() {
        double total = 0.0;
        int numRow = purchase_detail_table.getRowCount();

        for (int i = 0; i < numRow; i++) {
            //pasar el índice de la columna que se sumará
            total = total + Double.parseDouble(String.valueOf(purchase_detail_table.getValueAt(i, 3)));

        }
        txt_total.setText(" " + total+" $");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        form_print = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_invoice = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        purchase_detail_table = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        btn_print_purchase = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(607, 776));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        form_print.setBackground(new java.awt.Color(152, 202, 63));
        form_print.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/farmacia.png"))); // NOI18N
        jLabel1.setToolTipText("");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 70));

        form_print.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 100, 70));

        jPanel1.setBackground(new java.awt.Color(18, 45, 61));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("VIDA NATURAL");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        txt_invoice.setEditable(false);
        txt_invoice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(txt_invoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, 70, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Factura:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, -1, 40));

        form_print.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 490, 70));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("DETALLES DE LA COMPRA:");
        jLabel3.setToolTipText("");
        form_print.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        purchase_detail_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Qty", "Precio", "Subtotal", "Proveedor", "Comprador por:", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(purchase_detail_table);
        if (purchase_detail_table.getColumnModel().getColumnCount() > 0) {
            purchase_detail_table.getColumnModel().getColumn(0).setMinWidth(100);
            purchase_detail_table.getColumnModel().getColumn(5).setMinWidth(110);
            purchase_detail_table.getColumnModel().getColumn(6).setMinWidth(80);
        }

        form_print.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 550, 240));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Total:");
        form_print.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 450, -1, -1));

        txt_total.setEditable(false);
        txt_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        form_print.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 450, 110, 20));

        getContentPane().add(form_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 620, 710));

        btn_print_purchase.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_print_purchase.setText("IMPRIMIR");
        btn_print_purchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print_purchaseActionPerformed(evt);
            }
        });
        getContentPane().add(btn_print_purchase, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 720, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_print_purchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print_purchaseActionPerformed
       Toolkit tk = form_print.getToolkit();
       PrintJob pj = tk.getPrintJob(this,null,null);
       Graphics graphics = pj.getGraphics(); // estará simulando la impresora
       form_print.print(graphics);
       graphics.dispose();
       pj.end();
    }//GEN-LAST:event_btn_print_purchaseActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
 
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print_purchase;
    private javax.swing.JPanel form_print;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable purchase_detail_table;
    private javax.swing.JTextField txt_invoice;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
