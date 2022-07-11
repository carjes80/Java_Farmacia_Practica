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

public class SuppliesDAO {
    //Instanciar la conexión.

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn; //libreria connection, para qué sirve?
    PreparedStatement pst;//igual que arriba
    ResultSet rs;//igual que arriba

    //Registrar proveedor;
    public boolean registerSupplierQuery(Suppliers supplier) {
        String query = "INSERT INTO suppliers (name, description, address, telephone, email, city,created,updated)"
                + "VALUES (?,?,?,?,?,?,?,?)";

        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getTelephone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setTimestamp(8, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al registrar al proveedor" + e);
            return false;
        }
    }

    //Listar proveedor
    public List listSuppliersQuery(String value, String search) {
        //System.out.println("El valor en la búsqueda es: "+value);
        List<Suppliers> list_suppliers = new ArrayList();
        String query = "SELECT * FROM suppliers ";
        String query_search_supplier = "SELECT * FROM suppliers WHERE " + search + " LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_supplier);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                Suppliers supplier = new Suppliers();
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                supplier.setDescription(rs.getString("description"));
                supplier.setAddress(rs.getString("address"));
                supplier.setTelephone(rs.getString("telephone"));
                supplier.setEmail(rs.getString("email"));
                supplier.setCity(rs.getString("city"));
                list_suppliers.add(supplier);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar proveedores" + e);

        }
        return list_suppliers;
    }

    //Modificar proveedor.
    public boolean updateSupplierQuery(Suppliers supplier) {
        String query = "UPDATE suppliers SET name = ?, description = ?, address = ?, telephone = ?,"
                + "email = ?, city = ?, updated = ? WHERE id = ?";

        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getTelephone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, supplier.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al modificar los datos del proveedor" + e);
            return false;
        }
    }

    //Eliminar proveedor.
    public boolean deleteSupplierQuery(int id, String name) {
        String query = "DELETE FROM suppliers WHERE id = " + id;
        // String query_confirm = "SELECT * FROM customers WHERE id =" + id;
        try {

            if (JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar el Cliente " + name, " PRECAUCIÓN ",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                conn = cn.getConnection();
                pst = conn.prepareStatement(query);
                pst.execute();
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar al cliente" + e);
            return false;
        }
    }
    /*String query = "DELETE FROM suppliers WHERE id = "+id;
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"No se puede eliminar proveedor que tenga relación con otra tabla");
            return false;
        }
    }*/
}
