package bookshelf.renewal.exception;

public class MemberBookNotExistException extends RuntimeException {
        public static String msg_prefix = "[ERROR]/memberbooks/";
        public static String msg_suffix = "  The memberBook Doesn't Exist";
        public MemberBookNotExistException(String message) {
            super(message);
        }

        public MemberBookNotExistException(String username, Long bookId) {
            super();
            throw new IllegalArgumentException(msg_prefix + username + "&" + bookId + msg_suffix);
        }
}
