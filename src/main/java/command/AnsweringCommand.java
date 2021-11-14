package command;

public class AnsweringCommand implements Command{
    private String message;

    public AnsweringCommand(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
