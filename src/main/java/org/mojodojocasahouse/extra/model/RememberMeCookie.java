package org.mojodojocasahouse.extra.model;

import lombok.Data;

@Data
public class RememberMeCookie {
    private String selector;

    private String passwordHashHex;

    public RememberMeCookie(String selector, String passwordHashHex){
        this.passwordHashHex = passwordHashHex;
        this.selector = selector;
    }

    public static RememberMeCookie from(String cookie){
        String[] params = cookie.split(":", 2);

        return new RememberMeCookie(params[0], params[1]);
    }
}
