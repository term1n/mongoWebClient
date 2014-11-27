package ru.spb.iac.controller;

import com.google.common.base.*;
import com.google.gson.*;
import lombok.extern.log4j.*;
import ru.spb.iac.ui.*;
import ru.spb.iac.ui.models.*;

import javax.servlet.http.*;
import java.io.*;

/**
 * Created by manaev on 10.10.14.
 */
@Log4j
public abstract class CommonController {

    public Gson gson = new Gson();

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
