package pl.maropce.etutor.domain.question;

public enum QuestionType {
    SINGLE("single"),
    MULTI("multi"),
    FILL("fill"),
    ORDER("order");

    private final String value;
    QuestionType(String value) {
        this.value = value;
    }
}
