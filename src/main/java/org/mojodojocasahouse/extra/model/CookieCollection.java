package org.mojodojocasahouse.extra.model;

import jakarta.servlet.http.Cookie;
import lombok.Getter;

import java.util.List;

@Getter
public class CookieCollection {
    private final List<Cookie> cookies;

    public CookieCollection(List<Cookie> cookies){
        this.cookies = cookies;
    }

    public CookieCollection(Cookie cookie){
        this.cookies = List.of(cookie);
    }

    public void add(Cookie cookie){
        this.cookies.add(cookie);
    }
}
