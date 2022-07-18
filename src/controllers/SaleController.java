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
import models.Customers;
import models.CustomersDao;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.rol_user;
import models.Products;
import models.ProductsDao;
import models.Sales;
import models.SalesDAO;
import views.Print;
import views.SystemView;
//import models.Employees.rol_user;

public class SaleController implements MouseListener, ActionListener, KeyListener {

    private Sales sale;
    private SalesDAO sale_dao;
    private SystemView views;
    private String rol = rol_user;
    private int id = id_user;
    private int invoice;
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel temp = new DefaultTableModel();
    double total;

    //private String id_customer = 
    //voy a instanciar el modelo producto y customer
    Products product = new Products();
    ProductsDao product_dao = new ProductsDao();
    Customers customer = new Customers();
    CustomersDao customer_dao = new CustomersDao();

    public SaleController(Sales sale, SalesDAO sale_dao, SystemView views) {
        this.sale = sale;
        this.sale_dao = sale_dao;
        this.views = views;

        //Botones de menú
        this.views.btn_new_sale.addActionListener(this);
        this.views.btn_remove_sale.addActionListener(this);
        this.views.btn_add_product_sale.addActionListener(this);
        this.views.btn_confirm_sale.addActionListener(this);
        this.views.jLabelSales.addMouseListener(this);
        this.views.jLabelReports.addMouseListener(this);
        this.views.txt_sale_product_code.addKeyListener(this);
        this.views.txt_sale_quantity.addKeyListener(this);
        this.views.txt_sale_customer_id.addKeyListener(this);
        //this.views.jLabelReports.addMouseListener(this);

    }
    
     public void ListAllSales() {
        if (rol.equals("Administrador")) {
            System.out.println("Entró");
            List<Sales> list = sale_dao.listAllSalesQuery();
            model = (DefaultTableModel) views.table_all_sales.getModel();
            Object[] row = new Object[5];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getCustomer_name();
                row[2] = list.get(i).getEmployee_name();
                row[3] = list.get(i).getTotal_to_pay();
                row[4] = list.get(i).getSale_date();
                

                model.addRow(row);
            }
            views.table_all_sales.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.jLabelSales) {
            views.jTabbedPane1.setSelectedIndex(2);
            
            
        }else if (e.getSource() == views.jLabelReports) {
            if(rol.equals("Administrador")){
            views.jTabbedPane1.setSelectedIndex(7);
            cleanTable();
            
            ListAllSales();
            }else {
                JOptionPane.showMessageDialog(null,"No tiene privilegios");
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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_add_product_sale) {
            if (views.txt_sale_product_code.getText().equals("")
                    || views.txt_sale_product_id.getText().equals("")
                    || views.txt_sale_price.getText().equals("")
                    || views.txt_sale_product_name.getText().equals("")
                    || views.txt_sale_subtotal.getText().equals("")
                    || views.txt_sale_quantity.getText().equals("")
                    || views.txt_sale_stock.getText().equals("")
                    || views.txt_sale_customer_id.getText().equals("")
                    || views.txt_sale_customer_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Verifique que todos los datos sean correctos");
            } else {
                //System.out.println("Entró");
                temp = (DefaultTableModel) views.sales_table.getModel();
                

                int product_id = Integer.parseInt(views.txt_sale_product_id.getText().trim());
                double subtotal = Double.parseDouble(views.txt_sale_subtotal.getText().trim());
                String product_name = views.txt_sale_product_name.getText();
                int amount = Integer.parseInt(views.txt_sale_quantity.getText().trim());
                double price = Double.parseDouble(views.txt_sale_price.getText().trim());
                String customer_name = views.txt_sale_customer_name.getText();

                ArrayList list = new ArrayList();
                int item = 1;
                list.add(item);
                list.add(product_id);
                list.add(product_name);
                list.add(amount);
                list.add(price);
                list.add(subtotal);
                list.add(customer_name);

                Object[] obj = new Object[6];
                obj[0] = list.get(1);
                obj[1] = list.get(2);
                obj[2] = list.get(3);
                obj[3] = list.get(4);
                obj[4] = list.get(5);
                obj[5] = list.get(6);

                temp.addRow(obj);
                views.sales_table.setModel(temp);
                //cleanFieldsPurchases();
                //views.cmb_purchase_supplier.setEditable(false);
                views.txt_sale_customer_id.setEditable(false);
                views.btn_add_product_sale.setEnabled(false);
                views.txt_sale_product_code.requestFocus();
                calculateSale();
                //calculatePurchase();
            }

        } else if (e.getSource() == views.btn_confirm_sale) {
            //double total = Double.parseDouble(views.txt_sale_total_to_pay.getText());
            calculateSale();
            int employee_id = id_user;
            int customer_id = Integer.parseInt(views.txt_sale_customer_id.getText().trim());
            if (sale_dao.registerSalesQuery(customer_id, employee_id, total)) {
                invoice = sale_dao.saleID();
                for (int i = 0; i < views.sales_table.getRowCount(); i++) {
                    int product_id = Integer.parseInt(views.sales_table.getValueAt(i, 0).toString());
                    
                    int sale_quantity = Integer.parseInt(views.sales_table.getValueAt(i, 2).toString());
                    double sale_price = Double.parseDouble(views.sales_table.getValueAt(i, 3).toString());
                    double sale_subtotal = sale_price * sale_quantity;

                    //Registrar detalles de la venta
                    sale_dao.registerSaleDetailQuery(product_id, invoice, sale_quantity, sale_price, sale_subtotal);
                    //traer la cantidad de productos
                    product = product_dao.searchProductById(product_id);
                    int amount = product.getProduct_quantity() - sale_quantity;

                    product_dao.updateStockQuery(amount, product_id);

                }
                cleanTableTemp();
                JOptionPane.showMessageDialog(null, "Venta extiosa");
                cleanFieldsSales();
                //Print print = new Print(purchase_id);
                //print.setVisible(true);

            }
        }else if (e.getSource() == views.btn_remove_sale) {
            if (views.sales_table.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null, "Seleccione el producto de la tabla que desea eliminar");
            } else {
                model = (DefaultTableModel) views.sales_table.getModel();
                model.removeRow(views.sales_table.getSelectedRow());
                calculateSale();
                cleanFieldsSales();
                views.txt_sale_product_code.requestFocus();
            }
        } else if (e.getSource() == views.btn_new_sale) {
            cleanTableTemp();
            cleanFieldsSales();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == views.txt_sale_product_code) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (views.txt_sale_product_code.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingresa el código del producto");
                } else {
                    int product_code = Integer.parseInt(views.txt_sale_product_code.getText());
                    product = product_dao.searchProductByCode(product_code);
                    views.txt_sale_product_name.setText(product.getName());
                    views.txt_sale_product_id.setText("" + product.getId());
                    //product = product_dao.searchProductById(product.getId());
                    views.txt_sale_price.setText("" + product.getUnit_price());
                    views.txt_sale_stock.setText("" + product.getProduct_quantity());
                    views.txt_sale_subtotal.setText("");
                    views.btn_add_product_sale.setEnabled(true);
                    views.txt_sale_quantity.requestFocus();
                }
            }
        } else if (e.getSource() == views.txt_sale_quantity) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (views.txt_sale_quantity.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingresa la cantidad");
                } else {
                    //System.out.println("entró");
                    int qty = Integer.parseInt(views.txt_sale_quantity.getText());
                    // System.out.println("QTY "+qty);
                    double subtotal = qty * Double.parseDouble(views.txt_sale_price.getText());
                    views.txt_sale_subtotal.setText("" + subtotal);
                    // System.out.println("ST "+subtotal);
                    views.txt_sale_customer_id.requestFocus();
                }
            }
        } else if (e.getSource() == views.txt_sale_customer_id) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (views.txt_sale_customer_id.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingresa el id de la persona");
                } else {
                    int customer_id = Integer.parseInt(views.txt_sale_customer_id.getText());
                    customer = customer_dao.searchCustomerById(customer_id);
                    views.txt_sale_customer_name.setText(customer.getFull_name());
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanTableTemp() {
        for (int i = 0; i < temp.getRowCount(); i++) {
            temp.removeRow(i);
            i = i - 1;
        }
    }

    public void calculateSale() {
        total = 0.0;
        int numRow = views.sales_table.getRowCount();

        for (int i = 0; i < numRow; i++) {
            //pasar el índice de la columna que se sumará
            total = total + Double.parseDouble(String.valueOf(views.sales_table.getValueAt(i, 4)));

        }
        views.txt_sale_total_to_pay.setText("" + total);
    }

    public void cleanFieldsSales() {
        views.txt_sale_product_name.setText("");
        views.txt_sale_price.setText("");
        views.txt_sale_quantity.setText("");
        views.txt_sale_product_code.setText("");
        views.txt_sale_product_id.setText("");
        views.txt_sale_subtotal.setText("");
        views.txt_sale_customer_id.setText("");
        views.txt_sale_stock.setText("");
        views.txt_sale_customer_name.setText("");
        views.txt_sale_total_to_pay.setText("");
        views.txt_sale_customer_id.setEditable(true);

    }

}
