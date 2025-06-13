package bookshelf.renewal.exception;


public class ShelfNotExistException extends RuntimeException {
    public static String msg_prefix = "[ERROR]/shelves/";
    public static String msg_suffix = "  The shelf Doesn't Exist";
    public ShelfNotExistException(String message) {
        super(message);
    }

    public ShelfNotExistException(Long shelfId) {
        super();
        throw new IllegalArgumentException(msg_prefix + shelfId + msg_suffix);
    }
}
