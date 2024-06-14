package Client.Static;

public enum ConstsMessages {
    PRIVACY_POLICY("This is a generic privacy policy text.\n"),

    TERMS_OF_SERVICE("This is a generic terms of service text.\n" );

    private final String message;

    ConstsMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
