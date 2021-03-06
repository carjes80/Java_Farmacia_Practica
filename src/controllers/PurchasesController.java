package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.DynamicComboBox;
import static models.EmployeesDao.rol_user;
import static models.EmployeesDao.id_user;
import models.Products;
import models.ProductsDao;
import models.Purchases;
import models.PurchasesDao;
import views.Print;
import views.SystemView;

public class PurchasesController implements KeyListener, ActionListener, MouseListener {

    private Purchases purchase;
    private PurchasesDao purchase_dao;
    private SystemView views;
    private String rol = rol_user;
    private int getIdSupplier = 0;
    private int item = 0;
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel temp = new DefaultTableModel();

    //instanciamos el modelo productos
    Products product = new Products();
    ProductsDao product_dao = new ProductsDao();

    public PurchasesController(Purchases purchase, PurchasesDao purchase_dao, SystemView views) {
        this.purchase = purchase;
        this.purchase_dao = purchase_dao;
        this.views = views;

        //Botón de agregar
        this.views.btn_add_product_to_buy.addActionListener(this);
        //boton de comprar
        this.views.btn_confirm_purchase.addActionListener(this);
        //boton de eliminar compra
        this.views.btn_remove_purchase.addActionListener(this);

        this.views.txt_purchase_product_code.addKeyListener(this);
        this.views.txt_purchase_price.addKeyListener(this);
        this.views.txt_purchase_product_amount.addKeyListener(this);
        this.views.jTabbedPane1.addKeyListener(this);
        this.views.btn_new_purchase.addActionListener(this);

        this.views.jLabelPurchases.addMouseListener(this);
        this.views.jLabelReports.addMouseListener(this);
    }

    private void insertPurchase() {
        double total = Double.parseDouble(views.txt_purchase_total_to_pay.getText());
        int employee_id = id_user;
        if (purchase_dao.registerPurchaseQuery(getIdSupplier, employee_id, total)) {
            int purchase_id = purchase_dao.purchaseId();
            for (int i = 0; i < views.purchase_table.getRowCount(); i++) {
                int product_id = Integer.parseInt(views.purchase_table.getValueAt(i, 0).toString());
                int purchase_amount = Integer.parseInt(views.purchase_table.getValueAt(i, 2).toString());
                double purchase_price = Double.parseDouble(views.purchase_table.getValueAt(i, 3).toString());
                double purchase_subtotal = purchase_price * purchase_amount;

                //Registrar detalles de la compra
                purchase_dao.registerPurchaseDetailQuery(purchase_id, purchase_price, purchase_amount, purchase_subtotal, product_id);
                //traer la cantidad de productos
                product = product_dao.searchProductById(product_id);
                int amount = product.getProduct_quantity() + purchase_amount;

                product_dao.updateStockQuery(amount, product_id);

            }
            cleanTableTemp();
            JOptionPane.showMessageDialog(null, "Compra generada con éxito");
            cleanFieldsPurchases();
            Print print = new Print(purchase_id);
            print.setVisible(true);

        }

    }

    //metodo para listar las compras realizadas
    public void ListAllPurchases() {
        if (rol.equals("Administrador") || rol.equals("Auxiliar")) {
            List<Purchases> list = purchase_dao.listAllPurchasesQuery();
            model = (DefaultTableModel) views.table_all_purchases.getModel();
            Object[] row = new Object[4];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getSupplier_name_product();
                row[2] = list.get(i).getTotal();
                row[3] = list.get(i).getCreated();

                model.addRow(row);
            }
            views.table_all_purchases.setModel(model);
        }
    }

//Limpiar campos
    public void cleanFieldsPurchases() {
        views.txt_purchase_product_name.setText("");
        views.txt_purchase_price.setText("");
        views.txt_purchase_product_amount.setText("");
        views.txt_purchase_product_code.setText("");
        views.txt_purchase_subtotal.setText("");
        views.txt_purchase_id.setText("");
        views.txt_purchase_total_to_pay.setText("");

    }

    //calcular el total a pagar
    public void calculatePurchase() {
        double total = 0.0;
        int numRow = views.purchase_table.getRowCount();

        for (int i = 0; i < numRow; i++) {
            //pasar el índice de la columna que se sumará
            total = total + Double.parseDouble(String.valueOf(views.purchase_table.getValueAt(i, 4)));

        }
        views.txt_purchase_total_to_pay.setText("" + total);
    }

    //limpiar tabla temporal
    public void cleanTableTemp() {
        for (int i = 0; i < temp.getRowCount(); i++) {
            temp.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_add_product_to_buy) {
            
            DynamicComboBox supplier_cmb = (DynamicComboBox) views.cmb_purchase_supplier.getSelectedItem();
            int supplier_id = supplier_cmb.getId();
            //System.out.println("Entró al boton agregar");

            if (getIdSupplier == 0 || getIdSupplier == supplier_id || views.purchase_table.getRowCount()==0) {
                getIdSupplier = supplier_id;
                int amount;
                if (views.txt_purchase_product_amount.getText().equals("")) {
                    amount = 1;
                } else {
                    amount = Integer.parseInt(views.txt_purchase_product_amount.getText());
                }
                String product_name = views.txt_purchase_product_name.getText();
                double price = Double.parseDouble(views.txt_purchase_price.getText());
                int purchase_id = Integer.parseInt(views.txt_purchase_id.getText());
                String supplier_name = views.cmb_purchase_supplier.getSelectedItem().toString();

                if (amount > 0) {
                    temp = (DefaultTableModel) views.purchase_table.getModel();
                    for (int i = 0; i < views.purchase_table.getRowCount(); i++) {
                        if (views.purchase_table.getValueAt(i, 1).equals(views.txt_purchase_product_name.getText())) {
                            if (JOptionPane.showConfirmDialog(null, "El producto ya se encuentra en la tabla ¿desea sumarlo a lo actual?", "Producto agregado anteriormente",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                System.out.println("Se va a agregar pronto lcdtm");
                            }

                            return;
                        }
                    }
                    ArrayList list = new ArrayList();
                    item = 1;
                    list.add(item);
                    list.add(purchase_id);
                    list.add(product_name);
                    list.add(amount);
                    list.add(price);
                    list.add(amount * price);
                    list.add(supplier_name);

                    Object[] obj = new Object[6];
                    obj[0] = list.get(1);
                    obj[1] = list.get(2);
                    obj[2] = list.get(3);
                    obj[3] = list.get(4);
                    obj[4] = list.get(5);
                    obj[5] = list.get(6);

                    temp.addRow(obj);
                    views.purchase_table.setModel(temp);
                    cleanFieldsPurchases();
                    views.cmb_purchase_supplier.setEditable(false);
                    views.txt_purchase_product_code.requestFocus();
                    calculatePurchase();
                    System.out.println("Supplier es " + getIdSupplier);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No puede seleccionar a varios proveedores");
                System.out.println("varios proveedores");

            }
        } else if (e.getSource() == views.btn_confirm_purchase) {
            insertPurchase();
        } else if (e.getSource() == views.btn_remove_purchase) {
            if (views.purchase_table.getSelectedRow() < 0 && views.purchase_table.getRowCount() > 0) {
                JOptionPane.showMessageDialog(null, "Seleccione el producto de la tabla que desea eliminar");
            } else if (views.purchase_table.getRowCount() == 0){
                cleanTableTemp();
            cleanFieldsPurchases();
            initFields();
            getIdSupplier = 0;
            }else{
                model = (DefaultTableModel) views.purchase_table.getModel();
                model.removeRow(views.purchase_table.getSelectedRow());
                calculatePurchase();
                views.txt_purchase_product_code.requestFocus();
            }
        } else if (e.getSource() == views.btn_new_purchase) {
            cleanTableTemp();
            cleanFieldsPurchases();
            initFields();
            getIdSupplier = 0;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.jLabelPurchases) {
            //System.out.println("Entrooooo");
            if (rol.equals("Administrador")) {
                //System.out.println("Entrooooo");
                views.jTabbedPane1.setSelectedIndex(1);
                cleanTable();
                //ListAllPurchases();

            } else {
                views.jTabbedPane1.setEnabledAt(1, false);
                views.jLabelPurchases.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tiene privilegios");

            }

        } else if (e.getSource() == views.jLabelReports) {
            if (rol.equals("Administrador")) {
                //System.out.println("Entrooooo");
                views.jTabbedPane1.setSelectedIndex(7);
                cleanTable();
                ListAllPurchases();

            } else {
                views.jLabelReports.setEnabled(false);
                
                

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == views.txt_purchase_product_code) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (views.txt_purchase_product_code.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingresa el código del producto");
                } else {
                    int id = Integer.parseInt(views.txt_purchase_product_code.getText());
                    product = product_dao.searchProductByCode(id);
                    views.txt_purchase_product_name.setText(product.getName());
                    views.txt_purchase_id.setText("" + product.getId());
                    views.txt_purchase_product_amount.requestFocus();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_purchase_price) {
            int quantity;
            double price;
            if (views.txt_purchase_price.getText().equals("")) {
                views.txt_purchase_subtotal.setText("0.0");
            } else if (views.txt_purchase_product_amount.getText().equals("")) {
                price = Double.parseDouble(views.txt_purchase_price.getText());
                views.txt_purchase_subtotal.setText("" + price);

            } else {
                quantity = Integer.parseInt(views.txt_purchase_product_amount.getText());
                price = Double.parseDouble(views.txt_purchase_price.getText());
                views.txt_purchase_subtotal.setText("" + quantity * price);
            }
        } else if (e.getSource() == views.txt_purchase_product_amount) {
            int quantity;
            double price;

            if (views.txt_purchase_price.getText().equals("")) {
                views.txt_purchase_subtotal.setText("0.0");

            } else if (views.txt_purchase_product_amount.getText().equals("")) {
                price = Double.parseDouble(views.txt_purchase_price.getText());
                views.txt_purchase_subtotal.setText("" + price);

            } else {
                quantity = Integer.parseInt(views.txt_purchase_product_amount.getText());
                price = Double.parseDouble(views.txt_purchase_price.getText());
                views.txt_purchase_subtotal.setText("" + quantity * price);
            }

        }

    }
    public void initFields(){
        getIdSupplier = 0;
    }
}
