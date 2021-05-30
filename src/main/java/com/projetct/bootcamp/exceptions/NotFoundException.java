package com.projetct.bootcamp.exceptions;

import com.projetct.bootcamp.util.MessageUtils;

public class NotFoundException extends RuntimeException{
    public NotFoundException (){
        super(MessageUtils.NO_RECORDS_FOUND);
    }
}
