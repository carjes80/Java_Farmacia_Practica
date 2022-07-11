
package models;


public class Employees {
    private int id;
    private String full_name;
    private String username;
    private String address;
    private String telephone;
    private String email;
    private String password;
    private String rol;
    private String created;
    private String updated;

    public Employees() {
    }

    public Employees(int id, String full_name, String username, String address, String telephone, String email, String password, String rol, String created, String updated) {
        this.id = id;
        this.full_name = full_name;
        this.username = username;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.created = created;
        this.updated = updated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
    
    
}
