package bookshelf.renewal.exception;

public class MemberBookNotExistException extends RuntimeException {
    public static String msg_prefix = "[ERROR]/memberbooks/";
    public static String msg_suffix = "  The memberBook Doesn't Exist";

    public MemberBookNotExistException(String message) {
        super(message);
    }

    public MemberBookNotExistException(String username, Long bookId) {
        super();
        throw new IllegalArgumentException(msg_prefix + username + "& bookId: " + bookId + msg_suffix);
    }

    public MemberBookNotExistException(Long id) {
        super();
        throw new IllegalArgumentException(msg_prefix + id + msg_suffix);
    }

    public MemberBookNotExistException(String username, String isbn) {
        super();
        throw new IllegalArgumentException(msg_prefix + username + "& bookIsbn: " + isbn + msg_suffix);
    }
}
