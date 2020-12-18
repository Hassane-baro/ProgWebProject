package com.progweb.Progweb.Models;

import java.nio.charset.Charset;
import java.util.Random;

public class TokenGen {

    public TokenGen(){

    }
    public String CreateToken (){
        byte[] array = new byte[20];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }
}
