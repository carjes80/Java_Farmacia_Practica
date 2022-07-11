package controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
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

    public ProductsController(Products product, ProductsDao product_dao, SystemView views) {
        this.product = product;
        this.product_dao = product_dao;
        this.views = views;

        //label
        this.views.jLabelProducts.addMouseListener(this);
        //Boton registrar
        this.views.btn_register_product.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_product) {
            if (views.txt_product_code.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_unit_price.getText().equals("")
                    || views.txt_product_description.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                markNullFields();
            } else {
                product.setCode(Integer.parseInt(views.txt_product_code.getText()));
                product.setName(views.txt_product_name.getText().trim());
                product.setDescription(views.txt_product_description.getText().trim());
                product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText()));
                DynamicComboBox category_id = (DynamicComboBox) views.cmb_product_category.getSelectedItem();
                product.setCategory_id(category_id.getId());

                if (product_dao.registerProductQuery(product)) {
                    JOptionPane.showMessageDialog(null, "Producto registrado con éxoto");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error ");
                }

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
            if (rol.equals("Administrador")) {
                //System.out.println("Entrooooo");
                views.jTabbedPane1.setSelectedIndex(0);
                //cleanTable();
                //cleanFields();
                //initFields();
                //ListAllCategories();

            } else {
                views.jTabbedPane1.setEnabledAt(0, false);
                views.jLabelProducts.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes privilegios");
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

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
