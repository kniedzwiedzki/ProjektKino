package pl.projekt.alekino.domain.validation.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String className, Long id) {
        super(className + " with id " + id + " not found");
    }

    public NotFoundException(String classname, String name){
        super(classname + " with name " + name + " not found");
    }

}
