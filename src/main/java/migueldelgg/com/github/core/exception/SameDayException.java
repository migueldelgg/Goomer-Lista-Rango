package migueldelgg.com.github.core.exception;

public class SameDayException extends RuntimeException{
    
    // Construtor padrão
    public SameDayException() {
        super();
    }

    // Construtor com mensagem de erro
    public SameDayException(String message) {
        super(message);  // Passa a mensagem para a superclasse Exception
    }
}
