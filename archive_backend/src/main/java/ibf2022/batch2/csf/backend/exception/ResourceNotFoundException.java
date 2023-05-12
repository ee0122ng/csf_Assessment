package ibf2022.batch2.csf.backend.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {}

    public ResourceNotFoundException(String message) {
        super(message);
    }
    
}
