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
import models.Customers;
import models.CustomersDao;
import views.SystemView;

/*
Primero se crea las variables encapsuladas, luego se importan las clases. Se implementa el accion listener
y sus modelos.
 */
public class CustomerController implements ActionListener, MouseListener, KeyListener {

    private Customers customer;
    private CustomersDao customer_dao;
    private SystemView views;

    DefaultTableModel model = new DefaultTableModel();

    public CustomerController(Customers customer, CustomersDao customer_dao, SystemView views) {
        this.customer = customer;
        this.customer_dao = customer_dao;
        this.views = views;

        //boton de registrar cliente
        this.views.btn_register_customer.addActionListener(this);

        // boton de 
        this.views.customer_table.addMouseListener(this); // para la tabla se coloca Mouse Listener y no action como a los botones
        // teclado para busqueda
        this.views.txt_search_customer.addKeyListener(this);
        //modificar
        this.views.btn_update_customer.addActionListener(this);
        //eliminar
        this.views.btn_delete_customer.addActionListener(this);
        //cancelar
        this.views.btn_cancel_customer.addActionListener(this);
        this.views.customer_table.addKeyListener(this);
        //label de customer
        this.views.jLabelCustomers.addMouseListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Boton de Registrar
        if (e.getSource() == views.btn_register_customer) {
            //verificar si los campos están vacios
            if (views.txt_customer_id.getText().equals("")
                    || views.txt_customer_fullname.getText().equals("")
                    || views.txt_customer_address.getText().equals("")
                    || views.txt_customer_telephone.getText().equals("")
                    || views.txt_customer_email.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                markNullFields();

            } else {
                customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                customer.setFull_name(views.txt_customer_fullname.getText().trim());
                customer.setAddress(views.txt_customer_address.getText().trim());
                customer.setTelephone(views.txt_customer_telephone.getText().trim());
                customer.setEmail(views.txt_customer_email.getText().trim());

                if (customer_dao.registrerCustomerQuery(customer)) {
                    JOptionPane.showMessageDialog(null, "Cliente registrado con éxito");
                    //limpiar la tabla
                    cleanTable();
                    cleanFields();
                    initFields();
                    // listar clientes
                    ListAllCustomers();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar al cliente");
                }
            }
        } else if (e.getSource() == views.btn_update_customer) {
            // System.out.println("va a entrar");
            if (views.txt_customer_fullname.getText().equals("")
                    || views.txt_customer_address.getText().equals("")
                    || views.txt_customer_telephone.getText().equals("")
                    || views.txt_customer_email.getText().equals("")) {
                //    System.out.println("Entró");
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                markNullFields();

            } else {
                customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                customer.setFull_name(views.txt_customer_fullname.getText().trim());
                customer.setAddress(views.txt_customer_address.getText().trim());
                customer.setTelephone(views.txt_customer_telephone.getText().trim());
                customer.setEmail(views.txt_customer_email.getText().trim());

                if (customer_dao.updateCustomerQuery(customer)) {
                    JOptionPane.showMessageDialog(null, "Cliente actualizado con éxito");
                    //limpiar la tabla
                    cleanTable();
                    cleanFields();
                    initFields();
                    // listar clientes
                    ListAllCustomers();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar al cliente");
                }
            }
        } else if (e.getSource() == views.btn_delete_customer) {
            int row = views.customer_table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Primero seleccione un cliente de la lista");
            } else {

                int id = Integer.parseInt(views.customer_table.getValueAt(row, 0).toString());
                //System.out.println("El id a eliminar es " +Id_user);
                if (customer_dao.deleteCustomerQuery(id)) {

                    //limpiar la tabla
                    cleanTable();
                    cleanFields();
                    initFields();
                    // listar clientes
                    ListAllCustomers();
                    JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente");
                }

            }
        } else if (e.getSource() == views.btn_cancel_customer) {
            //limpiar la tabla
            cleanTable();
            cleanFields();
            initFields();
            // listar clientes
            ListAllCustomers();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.customer_table) {
            int row = views.customer_table.rowAtPoint(e.getPoint());
            views.txt_customer_id.setText(views.customer_table.getValueAt(row, 0).toString());
            views.txt_customer_fullname.setText(views.customer_table.getValueAt(row, 1).toString());
            views.txt_customer_telephone.setText(views.customer_table.getValueAt(row, 2).toString());
            views.txt_customer_address.setText(views.customer_table.getValueAt(row, 3).toString());
            views.txt_customer_email.setText(views.customer_table.getValueAt(row, 4).toString());

            //Deshabilitar botones
            views.btn_register_customer.setEnabled(false);
            views.txt_customer_id.setEditable(false);
            initFields();

        } else if (e.getSource() == views.jLabelCustomers) {
            views.jTabbedPane1.setSelectedIndex(3);
            //limpiar la tabla
            cleanTable();
            cleanFields();
            initFields();
            // listar clientes
            ListAllCustomers();
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
        if (e.getSource() == views.txt_search_customer) {
            //limpiar la tabla
            cleanTable();
            cleanFields();

            // listar clientes
            ListAllCustomers();
            initFields();

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            //limpiar la tabla
            cleanTable();
            cleanFields();

            // listar clientes
            ListAllCustomers();
            initFields();

        }

    }

    private void markNullFields() {
        if (views.txt_customer_id.getText().equals("")) {
            views.txt_customer_id.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_customer_id.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_customer_fullname.getText().equals("")) {
            views.txt_customer_fullname.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_customer_fullname.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_customer_address.getText().equals("")) {
            views.txt_customer_address.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_customer_address.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_customer_telephone.getText().equals("")) {
            views.txt_customer_telephone.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_customer_telephone.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_customer_email.getText().equals("")) {
            views.txt_customer_email.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_customer_email.setBackground(new Color(255, 255, 255));
        }

    }

    public void initFields() {
        views.txt_customer_id.setBackground(new Color(255, 255, 255));
        views.txt_customer_fullname.setBackground(new Color(255, 255, 255));
        views.txt_customer_address.setBackground(new Color(255, 255, 255));
        views.txt_customer_telephone.setBackground(new Color(255, 255, 255));
        views.txt_customer_email.setBackground(new Color(255, 255, 255));

    }

    public void cleanFields() {
        views.txt_customer_id.setText("");
        views.txt_customer_id.setEditable(true);
        views.btn_register_customer.setEnabled(true);
        views.txt_customer_fullname.setText("");
        views.txt_customer_address.setText("");
        views.txt_customer_telephone.setText("");
        views.txt_customer_email.setText("");

    }

    public void ListAllCustomers() {
        List<Customers> list = customer_dao.listCustomerQuery(views.txt_search_customer.getText());
        model = (DefaultTableModel) views.customer_table.getModel();

        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getFull_name();
            row[2] = list.get(i).getTelephone();
            row[3] = list.get(i).getAddress();
            row[4] = list.get(i).getEmail();

            model.addRow(row);

        }
        views.customer_table.setModel(model);

    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

}
