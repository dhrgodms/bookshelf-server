package bookshelf.renewal.exception;

public class ShelfBookNotExistException extends RuntimeException {
    public static String msg_prefix = "[ERROR]/shelfBooks/";
    public static String msg_suffix = "  The shelfBooks Doesn't Exist";

    public ShelfBookNotExistException(String message) {
        super(message);
    }

    public ShelfBookNotExistException(Long shelfId, Long bookId) {
        super();
        throw new IllegalArgumentException(msg_prefix + shelfId + "&" + bookId + msg_suffix);
    }

    public ShelfBookNotExistException(Long id) {
        super();
        throw new IllegalArgumentException(msg_prefix + id + msg_suffix);
    }
}
