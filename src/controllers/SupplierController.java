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
import static models.EmployeesDao.rol_user;
import models.Suppliers;
import models.SuppliesDAO;
import views.SystemView;

public class SupplierController implements ActionListener, MouseListener, KeyListener {

    private Suppliers supplier;
    private SuppliesDAO supplier_dao;
    private SystemView views;
    String rol = rol_user;
    String search = "name";
    int row;

    DefaultTableModel model = new DefaultTableModel();

    public SupplierController(Suppliers supplier, SuppliesDAO supplier_dao, SystemView views) {
        this.supplier = supplier;
        this.supplier_dao = supplier_dao;
        this.views = views;

        //boton de registrar
        this.views.btn_register_supplier.addActionListener(this);
        //tabla en escucha
        this.views.supplier_table.addMouseListener(this);
        this.views.supplier_table.addKeyListener(this);
        // escucha al buscador
        this.views.txt_supplier_search.addKeyListener(this);
        this.views.cmb_supplier_search_by.addActionListener(this);
        // boton de modificar
        this.views.btn_update_supplier.addActionListener(this);
        //boton de eliminar
        this.views.btn_delete_supplier.addActionListener(this);
        this.views.btn_cancel_supplier.addActionListener(this);
        //label de proveedores
        this.views.jLabelSuppliers.addMouseListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e
    ) {
        if (e.getSource() == views.btn_register_supplier) {
            if (views.txt_supplier_name.getText().equals("")
                    || views.txt_supplier_address.getText().equals("")
                    || views.txt_supplier_telephone.getText().equals("")
                    || views.txt_supplier_email.getText().equals("")
                    || views.txt_supplier_description.getText().equals("")
                    || views.cmb_supplier_city.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos");
                markNullFields();

            } else {
                //realizar inserción
                supplier.setName(views.txt_supplier_name.getText().trim());
                supplier.setAddress(views.txt_supplier_address.getText().trim());
                supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                supplier.setEmail(views.txt_supplier_email.getText().trim());
                supplier.setDescription(views.txt_supplier_description.getText().trim());
                supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString());

                if (supplier_dao.registerSupplierQuery(supplier)) {
                    JOptionPane.showMessageDialog(null, "Registrado");
                    //limpiar la tabla
                    cleanTable();
                    cleanFields();
                    initFields();
                    // listar clientes
                    ListAllSuppliers();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar");
                }
            }
        } else if (e.getSource() == views.cmb_supplier_search_by) {
            //  System.out.println("entró2");
            cleanTable();
            cleanFields();
            initFields();

            switch (views.cmb_supplier_search_by.getSelectedIndex()) {
                case 0 ->
                    search = "name";
                case 1 ->
                    search = "description";
                case 2 ->
                    search = "city";

            }

            ListAllSuppliers();
        } else if (e.getSource() == views.btn_update_supplier) {
            if (views.txt_supplier_name.getText().equals("")
                    || views.txt_supplier_address.getText().equals("")
                    || views.txt_supplier_telephone.getText().equals("")
                    || views.txt_supplier_email.getText().equals("")
                    || views.txt_supplier_description.getText().equals("")
                    || views.cmb_supplier_city.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Seleccione un proveedor en la tabla "
                        + "\nTodos los campos deben estar llenos");
                markNullFields();

            } else {
                //realizar inserción
                supplier.setId(Integer.parseInt(views.supplier_table.getValueAt(row, 0).toString()));
                supplier.setName(views.txt_supplier_name.getText().trim());
                supplier.setAddress(views.txt_supplier_address.getText().trim());
                supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                supplier.setEmail(views.txt_supplier_email.getText().trim());
                supplier.setDescription(views.txt_supplier_description.getText().trim());
                supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString());

                if (supplier_dao.updateSupplierQuery(supplier)) {
                    //  System.out.println("se manda: "+supplier.getId()+" , "+supplier.getName());
                    JOptionPane.showMessageDialog(null, "El proveedor fue modificado");
                    //limpiar la tabla
                    cleanTable();
                    cleanFields();
                    initFields();
                    // listar clientes
                    ListAllSuppliers();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Registrar");
                }
            }
        } else if (e.getSource() == views.btn_delete_supplier) {

            row = views.supplier_table.getSelectedRow();
            System.out.println("ROW " + row);
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar el proveedor de la tabla");
            } else {

                String name_to_delete = views.supplier_table.getValueAt(row, 1).toString();
                int id = Integer.parseInt(views.supplier_table.getValueAt(row, 0).toString());
                //System.out.println("se va a mandar: row " + row + ", id " + id + " , " + name_to_delete);
                if (supplier_dao.deleteSupplierQuery(id, name_to_delete)) {
                    JOptionPane.showMessageDialog(null, "El proveedor fue eliminado");
                    //limpiar la tabla
                    cleanTable();
                    cleanFields();
                    initFields();
                    // listar clientes
                    ListAllSuppliers();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar");
                }

            }
        } else if (e.getSource() == views.btn_cancel_supplier) {
            //limpiar la tabla
            cleanTable();
            cleanFields();
            initFields();
            // listar clientes
            ListAllSuppliers();
        }
    }

    private void markNullFields() {

        if (views.txt_supplier_name.getText().equals("")) {
            views.txt_supplier_name.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_supplier_name.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_supplier_address.getText().equals("")) {
            views.txt_supplier_address.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_supplier_address.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_supplier_telephone.getText().equals("")) {
            views.txt_supplier_telephone.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_supplier_telephone.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_supplier_email.getText().equals("")) {
            views.txt_supplier_email.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_supplier_email.setBackground(new Color(255, 255, 255));
        }
        if (views.txt_supplier_description.getText().equals("")) {
            views.txt_supplier_description.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_supplier_description.setBackground(new Color(255, 255, 255));
        }
        if (views.cmb_supplier_city.getSelectedIndex() == 0) {
            views.cmb_supplier_city.setBackground(new Color(255, 204, 204));
        } else {
            views.cmb_supplier_city.setBackground(new Color(255, 255, 255));
        }
    }

    public void ListAllSuppliers() {
        if (rol.equals("Administrador")) {
            cleanTable();
            cleanFields();
            initFields();
            List<Suppliers> list = supplier_dao.listSuppliersQuery(views.txt_supplier_search.getText(), search);
            model = (DefaultTableModel) views.supplier_table.getModel();

            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getDescription();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getCity();

                model.addRow(row);

            }
            views.supplier_table.setModel(model);

        }
    }

    public void initFields() {
        views.txt_supplier_id.setBackground(new Color(255, 255, 255));
        views.txt_supplier_name.setBackground(new Color(255, 255, 255));
        views.txt_supplier_address.setBackground(new Color(255, 255, 255));
        views.txt_supplier_telephone.setBackground(new Color(255, 255, 255));
        views.txt_supplier_email.setBackground(new Color(255, 255, 255));
        views.txt_supplier_description.setBackground(new Color(255, 255, 255));
        views.cmb_supplier_city.setBackground(new Color(255, 255, 255));

    }

    public void cleanFields() {
        views.btn_register_supplier.setEnabled(true);
        views.txt_supplier_name.setText("");
        views.txt_supplier_id.setText("");
        views.txt_supplier_address.setText("");
        views.txt_supplier_telephone.setText("");
        views.txt_supplier_email.setText("");
        views.txt_supplier_description.setText("");
        views.cmb_supplier_city.setSelectedIndex(0);

    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.supplier_table) {
            cleanFields();
            initFields();
            row = views.supplier_table.rowAtPoint(e.getPoint());
            views.txt_supplier_id.setText(views.supplier_table.getValueAt(row, 0).toString());
            views.txt_supplier_name.setText(views.supplier_table.getValueAt(row, 1).toString());
            views.txt_supplier_description.setText(views.supplier_table.getValueAt(row, 2).toString());
            views.txt_supplier_address.setText(views.supplier_table.getValueAt(row, 3).toString());
            views.txt_supplier_telephone.setText(views.supplier_table.getValueAt(row, 4).toString());
            views.txt_supplier_email.setText(views.supplier_table.getValueAt(row, 5).toString());
            views.cmb_supplier_city.setSelectedItem(views.supplier_table.getValueAt(row, 6).toString());

            //deshabilitar botones
            views.btn_register_supplier.setEnabled(false);

        } else if (e.getSource() == views.jLabelSuppliers) {
            if (rol.equals("Administrador")) {
                views.jTabbedPane1.setSelectedIndex(5);
                cleanTable();
                cleanFields();
                initFields();
                ListAllSuppliers();
                
            } else{
                views.jTabbedPane1.setEnabledAt(5, false);
                views.jLabelSuppliers.setEnabled(false);
                JOptionPane.showMessageDialog(null,"No tienes privilegios");
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
        if (e.getSource() == views.txt_supplier_search) {
            cleanTable();
            cleanFields();
            initFields();
            ListAllSuppliers();

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //limpiar la tabla
            cleanTable();
            cleanFields();
            initFields();
            // listar clientes
            ListAllSuppliers();
        }

    }
}
