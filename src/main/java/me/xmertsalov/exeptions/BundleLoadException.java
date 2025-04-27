package me.xmertsalov.exeptions;

public class BundleLoadException extends Exception {
    private final String fileName;

    public BundleLoadException(String message, String fileName) {
        super(message);
        this.fileName = fileName;
    }

    public BundleLoadException(Exception exception, String fileName) {
        super(exception.getMessage());
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " in file: " + fileName;
    }
}
