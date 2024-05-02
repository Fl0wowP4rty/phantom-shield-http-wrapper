package tech.skidonion.api.wrapper.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String s){
        super(s);
    }
}
