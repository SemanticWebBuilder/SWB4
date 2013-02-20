/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.inmujeres.swb.resources.auth;

/**
 *
 * @author gabriela.rosales
 */
public class UserSubordinado {
    private String login;
    private String nombre;

    public UserSubordinado() {
    }

    public UserSubordinado(String login, String nombre) {
        this.login = login;
        this.nombre = nombre;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Usuario Subordinado:"+" " + "Login:" +" "+ login +", "+ "Nombre:" +" "+ nombre +"";
    }


}
