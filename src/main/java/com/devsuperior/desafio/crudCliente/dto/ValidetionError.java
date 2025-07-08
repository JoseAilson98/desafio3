package com.devsuperior.desafio.crudCliente.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidetionError extends CustomError{

    private List<FieldMessage> errors = new ArrayList<>();


    public ValidetionError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldMessage> getErrors(){
        return errors;
    }

    public void addErrors(String fieldName,String message){
        errors.add(new FieldMessage(fieldName,message));
    }
}
