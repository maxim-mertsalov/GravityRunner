package me.xmertsalov.exceptions;

/**
 * This exception is thrown when there is an issue creating an animator.
 * It provides detailed error messages based on the specific field error encountered.
 */
public class AnimatorCreatingException extends RuntimeException {

    /**
     * Enum representing the possible field errors that can occur during animator creation.
     */
    public enum FieldError {
        thereAreNoImages, // Indicates that no images are available in the atlas.
        incorrectURL, // Indicates that the provided URL is incorrect.
        thereAreNotEnoughImageSettings, // Indicates insufficient image settings.
        noStates, // Indicates that no states are defined for the animator.
        noAnimations, // Indicates that no animations are defined for the animator.
        other // Represents any other unspecified error.
    }

    private String detailedMessage;

    /**
     * Constructs a new AnimatorCreatingException with a specific message and field error.
     *
     * @param message    The error message describing the issue.
     * @param fieldError The specific field error that occurred.
     */
    public AnimatorCreatingException(String message, FieldError fieldError) {
        super(message);
        switch (fieldError) {
            case thereAreNoImages:
                detailedMessage = ("There are no images in the atlas: " + message);
                break;
            case incorrectURL:
                detailedMessage = ("Incorrect URL: " + message);
                break;
            case thereAreNotEnoughImageSettings:
                detailedMessage = ("There are not enough image settings: " + message);
                break;
            default:
                detailedMessage = message;
                break;
        }
    }

    /**
     * Returns the detailed error message for this exception.
     *
     * @return The detailed error message.
     */
    @Override
    public String getMessage() {
        return detailedMessage;
    }
}
