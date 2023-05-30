package ru.burtsev.yandexlavka2023.exception;

public class NotFound extends RuntimeException{
    public NotFound(String message){
        super(message);
    }
}
