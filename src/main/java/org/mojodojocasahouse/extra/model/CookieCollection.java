package org.mojodojocasahouse.extra.model;

import jakarta.servlet.http.Cookie;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class CookieCollection {
    private Set<Cookie> cookies;

    public CookieCollection(Set<Cookie> cookies){
        this.cookies = cookies;
    }

    public CookieCollection(Cookie cookie){
        this.cookies = new HashSet<>();
        this.cookies.add(cookie);
    }

    public void add(Cookie cookie){
        this.cookies.add(cookie);
    }
}
