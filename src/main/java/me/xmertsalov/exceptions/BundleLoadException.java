package me.xmertsalov.exceptions;

/**
 * This exception is thrown when there is an issue loading a resource bundle.
 * It provides additional context by including the name of the file that caused the error.
 */
public class BundleLoadException extends Exception {
    private final String fileName;

    /**
     * Constructs a new BundleLoadException with a specific message and file name.
     *
     * @param message  The error message describing the issue.
     * @param fileName The name of the file that caused the error.
     */
    public BundleLoadException(String message, String fileName) {
        super(message);
        this.fileName = fileName;
    }

    /**
     * Constructs a new BundleLoadException with an existing exception and file name.
     *
     * @param exception The original exception that caused this error.
     * @param fileName  The name of the file that caused the error.
     */
    public BundleLoadException(Exception exception, String fileName) {
        super(exception.getMessage());
        this.fileName = fileName;
    }

    /**
     * Returns the name of the file that caused the error.
     *
     * @return The file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns the detailed error message, including the file name.
     *
     * @return The detailed error message.
     */
    @Override
    public String getMessage() {
        return super.getMessage() + " in file: " + fileName;
    }
}
