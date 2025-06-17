package bookshelf.renewal.exception;

public class MemberShelfNotExistException extends RuntimeException {
    public static String msg_prefix = "[ERROR]/memberShelves/";
    public static String msg_suffix = "  The memberShelf Doesn't Exist";

    public MemberShelfNotExistException(String message) {
        super(message);
    }

    public MemberShelfNotExistException(String username, Long shelfId) {
        super();
        throw new IllegalArgumentException(msg_prefix + username + "&" + shelfId + msg_suffix);
    }

    public MemberShelfNotExistException(Long id) {
        super();
        throw new IllegalArgumentException(msg_prefix + id + msg_suffix);
    }
}
