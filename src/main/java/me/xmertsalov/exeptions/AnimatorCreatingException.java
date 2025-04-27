package me.xmertsalov.exeptions;

import me.xmertsalov.Game;

public class AnimatorCreatingException extends RuntimeException {


    public enum FieldError {
        thereAreNoImages,
        incorrectURL,
        thereAreNotEnoughImageSettings,
        noStates,
        noAnimations,
        other
    }

    private String detailedMessage;


    public AnimatorCreatingException(String message, FieldError fieldError) {
        super(message);
        switch (fieldError) {
            case thereAreNoImages -> detailedMessage = ("There are no images in the atlas: " + message);
            case incorrectURL -> detailedMessage = ("Incorrect URL: " + message);
            case thereAreNotEnoughImageSettings -> detailedMessage = ("There are not enough image settings: " + message);
            case other -> detailedMessage = message;
        }
    }

    @Override
    public String getMessage() {
        return detailedMessage;
    }
}
