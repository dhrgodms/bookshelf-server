package bookshelf.renewal.exception;


public class BookNotExistException extends RuntimeException {
    public static String msg_prefix = "[ERROR]/books/";
    public static String msg_suffix = "  The book Doesn't Exist";
    public BookNotExistException(String message) {
        super(message);
    }

    public BookNotExistException(Long bookId) {
        super();
        throw new IllegalArgumentException(msg_prefix + bookId + msg_suffix);
    }
}
