package org.pesmypetcare.mypetcare;

public class User {
    private String username;
    private String mail;
    private String passwd;

    public User(String username, String mail, String passwd) {
        this.username = username;
        this.mail = mail;
        this.passwd = passwd;
        comunicateDatabase(this.username, this.mail, this.passwd);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
        comunicateDatabase(this.username, this.mail, this.passwd);
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
        comunicateDatabase(this.username, this.mail, this.passwd);
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
        comunicateDatabase(this.username, this.mail, this.passwd);
    }

    private void comunicateDatabase(String username, String mail, String passwd) {
        //Comunicació amb la base de dades;
    }
}
