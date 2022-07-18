package controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.DynamicComboBox;
import static models.EmployeesDao.rol_user;
import models.Products;
import models.ProductsDao;

import views.SystemView;

public class ProductsController implements ActionListener, KeyListener, MouseListener {

    private Products product;
    private ProductsDao product_dao;
    private SystemView views;
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public ProductsController(Products product, ProductsDao product_dao, SystemView views) {
        this.product = product;
        this.product_dao = product_dao;
        this.views = views;

        //label
        this.views.jLabelProducts.addMouseListener(this);
        //Boton registrar
        this.views.btn_register_product.addActionListener(this);
        //Boton de modificar producto
        this.views.btn_update_product.addActionListener(this);
        //Boton de eliminar producto
        this.views.btn_delete_product.addActionListener(this);
        //Boton de cancelar
        this.views.btn_cancel_product.addActionListener(this);

        this.views.products_table.addMouseListener(this);
        //Buscador
        this.views.txt_search_product.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_product) {  //REGISTRAR
            if (views.txt_product_code.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_unit_price.getText().equals("")
                    || views.txt_product_description.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                markNullFields();
            } else {
                product.setCode(Integer.parseInt(views.txt_product_code.getText().trim()));
                product.setName(views.txt_product_name.getText().trim());
                product.setDescription(views.txt_product_description.getText().trim());
                product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText()));
                DynamicComboBox category_id = (DynamicComboBox) views.cmb_product_category.getSelectedItem();
                product.setCategory_id(category_id.getId());
                //    System.out.println("Se envía a registrar   ->"+product.getId());

                if (product_dao.registerProductQuery(product)) {
                    JOptionPane.showMessageDialog(null, "Producto registrado con éxito");
                    cleanTable();
                    ListAllProducts();

                    cleanFields();
                    initFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error ");
                }

            }

        } else if (e.getSource() == (views.btn_update_product)) {       // ACTUALIZAR
            //   System.out.println("Action   ->"+product.getId());
            if (views.txt_product_code.getText().equals("")
                    || views.txt_product_id.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_unit_price.getText().equals("")
                    || views.txt_product_description.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Primero seleccione un producto");
                markNullFields();
            } else {
                product.setCode(Integer.parseInt(views.txt_product_code.getText().trim()));
                product.setName(views.txt_product_name.getText().trim());
                product.setDescription(views.txt_product_description.getText().trim());
                product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText()));
                DynamicComboBox category_id = (DynamicComboBox) views.cmb_product_category.getSelectedItem();
                product.setCategory_id(category_id.getId());
                product.setId(Integer.parseInt(views.txt_product_id.getText().trim()));

                //   System.out.println("Se envía   ->"+product.getId());
                if (product_dao.updateProductQuery(product)) {
                    JOptionPane.showMessageDialog(null, "Producto actualizado con éxito");
                    cleanTable();
                    ListAllProducts();

                    cleanFields();
                    initFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error ");
                }
            }
        } else if (e.getSource() == views.btn_delete_product) {     // Eliminar
            if (views.txt_product_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Primero seleccione un producto");
                markNullFields();
            } else {
                product_dao.deleteProductQuery(Integer.parseInt(views.txt_product_id.getText().trim()));
                cleanTable();
                ListAllProducts();

                cleanFields();
                initFields();
            }
        } else if (e.getSource() == views.btn_cancel_product) {
            views.txt_search_product.setText("");
            cleanTable();
            ListAllProducts();
            cleanFields();
            initFields();
        }
    }
    // Listar los productos

    public void ListAllProducts() {
        if (rol.equals("Administrador") || rol.equals("Auxiliar")) {
            List<Products> list = product_dao.listProductQuery(views.txt_search_product.getText());
            model = (DefaultTableModel) views.products_table.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getCode();
                row[2] = list.get(i).getName();
                row[3] = list.get(i).getDescription();
                row[4] = list.get(i).getUnit_price();
                row[5] = list.get(i).getProduct_quantity();
                row[6] = list.get(i).getCategory_name();
                model.addRow(row);
                //  System.out.println("Se lista->"+product.getId());
            }
            views.products_table.setModel(model);

            if (rol.equals("Auxiliar")) {
                views.btn_register_product.setEnabled(false);
                views.btn_update_product.setEnabled(false);
                views.btn_delete_product.setEnabled(false);
                views.btn_cancel_product.setEnabled(false);
                views.txt_product_code.setEditable(false);
                views.txt_product_name.setEditable(false);
                views.txt_product_unit_price.setEditable(false);
                views.txt_product_description.setEditable(false);
                views.txt_product_id.setEditable(false);
            }
        }

    }

    private void markNullFields() {
        if (views.txt_product_name.getText().equals("")) {
            views.txt_product_name.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_product_name.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_product_code.getText().equals("")) {
            views.txt_product_code.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_product_code.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_product_unit_price.getText().equals("")) {
            views.txt_product_unit_price.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_product_unit_price.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_product_description.getText().equals("")) {
            views.txt_product_description.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_product_description.setBackground(new Color(255, 255, 255));
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("e tiene "+ e);
        if (e.getSource() == views.jLabelProducts) {
            //System.out.println("Entrooooo");

            views.jTabbedPane1.setSelectedIndex(0);
            cleanTable();
            ListAllProducts();

            cleanFields();
            initFields();

        } else if (e.getSource() == views.products_table) {
            initFields();
            cleanFields();
            int row = views.products_table.rowAtPoint(e.getPoint());
            views.txt_product_id.setText(views.products_table.getValueAt(row, 0).toString());
            //System.out.println("Antes de llamar al modulo search product (product dao  ->"+product.getId());
            product = product_dao.searchProduct(Integer.parseInt(views.txt_product_id.getText()));
            // System.out.println("despues de llamar al modulo search product (product dao  ->"+product.getId());
            views.txt_product_code.setText("" + product.getCode());
            views.txt_product_name.setText(product.getName());
            views.txt_product_description.setText(product.getDescription());
            views.txt_product_unit_price.setText("" + product.getUnit_price());
            views.cmb_product_category.setSelectedItem(new DynamicComboBox(product.getCategory_id(), product.getCategory_name()));
            //deshabilitar
            views.btn_register_product.setEnabled(false);

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

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_product) {
            cleanTable();
            ListAllProducts();
        }

    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void initFields() {
        views.txt_product_id.setBackground(new Color(255, 255, 255));
        views.txt_product_name.setBackground(new Color(255, 255, 255));
        views.txt_product_code.setBackground(new Color(255, 255, 255));
        views.txt_product_description.setBackground(new Color(255, 255, 255));
        views.cmb_product_category.setBackground(new Color(255, 255, 255));
        views.txt_product_unit_price.setBackground(new Color(255, 255, 255));

    }

    public void cleanFields() {

        views.btn_register_product.setEnabled(true);
        views.txt_product_name.setText("");
        views.txt_product_id.setText("");
        views.txt_product_code.setText("");
        views.txt_product_description.setText("");
        views.txt_product_unit_price.setText("");
        views.cmb_product_category.setSelectedIndex(0);

    }
}
