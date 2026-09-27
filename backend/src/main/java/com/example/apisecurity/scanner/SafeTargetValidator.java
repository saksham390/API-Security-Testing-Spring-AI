package com.example.apisecurity.scanner;

import java.net.URI;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Component
public class SafeTargetValidator {
    public URI resolve(String baseUrl,String path){
        URI base=URI.create(baseUrl);String host=base.getHost();
        if(!"localhost".equalsIgnoreCase(host)&&!"127.0.0.1".equals(host)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Only localhost targets are allowed");
        if(path==null||!path.startsWith("/")||path.contains("..")||path.contains("\\")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unsafe endpoint path");
        return URI.create(baseUrl.replaceAll("/$","")+path);
    }
}
