package ru.spb.iac.security.oo;

import org.springframework.security.core.*;

/**
 * Created by manaev on 05.03.15.
 */
public class MWCUserUi {

    private String username;
    private String eMail;
    private GrantedAuthority[] authorities;
    private String id;

    public MWCUserUi(String id, String username, String eMail, GrantedAuthority[] authorities) {
        this.id = id;
        this.username = username;
        this.eMail = eMail;
        this.authorities = authorities;
    }

    public MWCUserUi(String username, String eMail, GrantedAuthority[] authorities) {
        this.username = username;
        this.eMail = eMail;
        this.authorities = authorities;
    }

    public MWCUserUi(String username) {
        this.username = username;
    }

    public MWCUserUi() {
        authorities = new GrantedAuthority[5];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public GrantedAuthority[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(GrantedAuthority[] authorities) {
        this.authorities = authorities;
    }
}
