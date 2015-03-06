package ru.spb.iac.controller;

import com.google.common.base.*;
import com.google.gson.*;
import lombok.extern.log4j.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import ru.spb.iac.exceptions.*;
import ru.spb.iac.security.*;
import ru.spb.iac.security.enc.*;
import ru.spb.iac.security.oo.*;
import ru.spb.iac.ui.*;
import ru.spb.iac.ui.models.*;

import javax.servlet.http.*;
import java.io.*;

/**
 * {"applicationId":{"$regex":".*151.*"}}
 * Created by manaev on 10.10.14.
 */
@Log4j
public abstract class CommonController {

    public Gson gson = new Gson();

    protected void addUser(MWCUser user, BCryptHash hash, MWCUserDetailsService mwcUDS) throws MongoException {
        user.setPassword(hash.getEncPassword(user.getPassword()));
        GrantedAuthority[] authorities = new GrantedAuthority[1];
        authorities[0] = new SimpleGrantedAuthority("ROLE_USER");
        user.setAuthorities(authorities);

        mwcUDS.getMongoTemplateFactory().getOperationsTemplate(mwcUDS.getMongoHere()).save(user);

    }

    public void enableAjaxSupport(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
    }

    public void closeConnection(HttpServletResponse response) {
        try {
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String serialiseDTO(Object dto) {
        return gson.toJson(dto);
    }

    public void writeSuccessAjaxResponse(HttpServletResponse response, Object data) {
        AjaxResponse res = new AjaxResponse(data);
        String temp = serialiseDTO(res);
        try {
            enableAjaxSupport(response);
            response.setContentLength(temp.getBytes().length);
            response.getOutputStream().write(temp.getBytes(Charsets.UTF_8));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            closeConnection(response);
        }
    }

    public void writeErrorAjaxResponse(HttpServletResponse response, Object error) {
        AjaxResponse res = new AjaxResponse(error, null);
        String temp = serialiseDTO(res);
        try {
            enableAjaxSupport(response);
            response.setContentLength(temp.getBytes().length);
            response.getOutputStream().write(temp.getBytes(Charsets.UTF_8));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            closeConnection(response);
        }
    }

    public boolean hasHostPort(MongoAddress address) {
        if (Strings.isNullOrEmpty(address.getHost()) || (address.getPort() == null)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean hasHostPortDbNColl(MongoAddress address) {
        if (Strings.isNullOrEmpty(address.getHost()) || (address.getPort() == null) || Strings.isNullOrEmpty(address.getDbName()) || Strings.isNullOrEmpty(address.getCollName())) {
            return false;
        } else {
            return true;
        }
    }
}
