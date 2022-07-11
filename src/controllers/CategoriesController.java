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
import models.Categories;
import models.CategoriesDAO;
import models.DynamicComboBox;
import static models.EmployeesDao.rol_user;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import views.SystemView;

public class CategoriesController implements ActionListener, MouseListener, KeyListener {

    private Categories category;
    private CategoriesDAO category_dao;
    private SystemView views;
    String rol = rol_user;

    DefaultTableModel model = new DefaultTableModel();

    public CategoriesController(Categories category, CategoriesDAO category_dao, SystemView views) {
        this.category = category;
        this.category_dao = category_dao;
        this.views = views;

        //Boton de registrar
        this.views.btn_register_categories.addActionListener(this);
        // Tabla
        this.views.categories_table.addMouseListener(this);
        //search boton
        this.views.txt_search_categories.addKeyListener(this);
        //modificar cateogria 
        this.views.btn_update_categories.addActionListener(this);
        //boton de eliminar categoría
        this.views.btn_delete_categories.addActionListener(this);
        //boton de cancelar
        this.views.btn_cancel_categories.addActionListener(this);
        //Label
        this.views.jLabelCategories.addMouseListener(this);
        //enviar categorías a combo box de productos
        getCategoryName();
        AutoCompleteDecorator.decorate(views.cmb_product_category);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_categories) {
            if (views.txt_categories_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                markNullFields();
            } else {
                category.setName(views.txt_categories_name.getText().trim());

                if (category_dao.registerCategoryQuery(category)) {
                    JOptionPane.showMessageDialog(null, "Registrado con éxito");
                    ListAllCategories();
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
                }
            }

        } else if (e.getSource() == views.btn_update_categories) {
            if (views.txt_categories_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                if (views.txt_categories_id.getText().equals("")
                        || views.txt_categories_name.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "El campo Nombre no puede estar vacío");
                    markNullFields();
                } else {
                    category.setId(Integer.parseInt(views.txt_categories_id.getText()));
                    category.setName(views.txt_categories_name.getText());
                    if (category_dao.updateCategoryQuery(category)) {
                        ListAllCategories();
                        JOptionPane.showMessageDialog(null, "Categoría modificada");
                    } else {
                        // JOptionPane.showMessageDialog(null, "Error al modificar");
                    }

                }
            }
        } else if (e.getSource() == views.btn_delete_categories) {
            int row = views.categories_table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showConfirmDialog(null, "Debe seleccionar una categoría");

            } else {
                int id = Integer.parseInt(views.categories_table.getValueAt(row, 0).toString());
                String name_to_delete = views.categories_table.getValueAt(row, 1).toString();

                if (category_dao.deleteCategoryQuery(id, name_to_delete)) {
                    JOptionPane.showMessageDialog(null, "La categoría fue eliminada");
                    //limpiar la tabla
                    cleanTable();
                    cleanFields();
                    initFields();
                    // listar clientes
                    ListAllCategories();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar");
                }

            }
        } else if (e.getSource() == views.btn_cancel_categories) {
            cleanFields();
            cleanTable();
            initFields();
            ListAllCategories();
        }
    }

    private void markNullFields() {
        if (views.txt_categories_name.getText().equals("")) {
            views.txt_categories_name.setBackground(new Color(255, 204, 204));
        } else {
            views.txt_categories_name.setBackground(new Color(255, 255, 255));
        }

    }

    //listar caterorias
    public void ListAllCategories() {
        if (rol.equals("Administrador")) {
            cleanTable();
            cleanFields();
            List<Categories> list = category_dao.listCategoryQuery(views.txt_search_categories.getText());
            model = (DefaultTableModel) views.categories_table.getModel();

            Object[] row = new Object[2];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();

                model.addRow(row);

            }
            views.categories_table.setModel(model);//model contiene los datos que se van a mostrar en la tabla

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.categories_table) {
            int row = views.categories_table.rowAtPoint(e.getPoint());
            views.txt_categories_id.setText(views.categories_table.getValueAt(row, 0).toString());
            views.txt_categories_name.setText(views.categories_table.getValueAt(row, 1).toString());
            views.btn_register_categories.setEnabled(false);
            initFields();

        } else if (e.getSource() == views.jLabelCategories) {
            if (rol.equals("Administrador")) {
                views.jTabbedPane1.setSelectedIndex(6);
                cleanTable();
                cleanFields();
                initFields();
                ListAllCategories();

            } else {
                views.jTabbedPane1.setEnabledAt(6, false);
                views.jLabelCategories.setEnabled(false);
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
        if (e.getSource() == views.txt_search_categories) {
            cleanTable();
            initFields();
            cleanFields();
            ListAllCategories();
        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void initFields() {
        views.txt_categories_id.setBackground(new Color(255, 255, 255));
        views.txt_categories_name.setBackground(new Color(255, 255, 255));

    }

    public void cleanFields() {
        views.txt_categories_id.setText("");
        views.txt_categories_name.setText("");
        views.btn_register_categories.setEnabled(true);

    }
    
    //Método para mostrar el nombre de las categorías.
    public void getCategoryName(){
        List<Categories> list = category_dao.listCategoryQuery(views.txt_search_categories.getText());
        for(int i = 0; i< list.size(); i++){
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            views.cmb_product_category.addItem(new DynamicComboBox(id,name));
        }
    }
}
