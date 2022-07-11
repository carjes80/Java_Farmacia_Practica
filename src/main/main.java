
package main;

import views.LoginView;


public class main {
    public static void main(String[] args) {
        //Instancia del login
        LoginView login = new LoginView();
        login.setVisible(true);// acá inicio la ejecución de la pantalla de login. Si se pone false, no aparece.
        System.out.println("Programa en ejecución");
    }
}
