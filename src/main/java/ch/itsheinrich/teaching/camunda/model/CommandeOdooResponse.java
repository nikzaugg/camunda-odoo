package ch.itsheinrich.teaching.camunda.model;


public class CommandeOdooResponse {
    String message;
    String result;

    public CommandeOdooResponse(String message, String result) {
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
