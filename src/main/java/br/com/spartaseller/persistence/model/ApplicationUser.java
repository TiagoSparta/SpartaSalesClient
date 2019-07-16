package br.com.spartaseller.persistence.model;

public class ApplicationUser extends AbstractEntity{
    private String username;
    private String password;
    private Administrador administrador;

    public ApplicationUser() {
    }

    public ApplicationUser(long id, String username, String password, Administrador administrador) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.administrador = administrador;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
}
