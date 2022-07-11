package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriesDAO {
    //Instanciar la conexión.

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn; //libreria connection, para qué sirve?
    PreparedStatement pst;//igual que arriba
    ResultSet rs;//igual que arriba

    //Registrar categorias
    public boolean registerCategoryQuery(Categories category) {
        String query = "INSERT INTO categories (name,created,updated) VALUES (?,?,?)";

        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setTimestamp(3, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la categoría");
            return false;
        }
    }

    //listar categorias
    public List listCategoryQuery(String value) {
        List<Categories> list_categories = new ArrayList();
        String query = "SELECT * FROM categories";
        String query_search_category = "SELECT * FROM categories WHERE name LIKE '%" + value + "%'";

        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_category);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                Categories category = new Categories(); // instanciando y creando el objeto category
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                list_categories.add(category);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
        return list_categories;

    }

    //modificar categorias
    public boolean updateCategoryQuery(Categories category) {
        String query = "UPDATE categories SET name = ?,updated = ? WHERE id = ?";

        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setInt(3, category.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar la categoría");
            return false;
        }
    }

    //Eliminar categorias
    public boolean deleteCategoryQuery(int id, String name) {

        String query = "DELETE FROM categories WHERE id = " + id;
        //String query_confirm = "SELECT * FROM categories WHERE id =" + id;
        try {
            conn = cn.getConnection();

            if (JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar la cateogría " + name, " PRECAUCIÓN ",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                pst = conn.prepareStatement(query);
                pst.execute();

                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar categoria relacionada con otra tabla" + e);
            return false;
        }
    }

}
