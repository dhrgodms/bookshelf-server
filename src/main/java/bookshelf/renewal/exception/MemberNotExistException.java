package bookshelf.renewal.exception;


public class MemberNotExistException extends RuntimeException {
    public static String msg_prefix = "[ERROR]/members/";
    public static String msg_suffix = "  The member Doesn't Exist";
    public MemberNotExistException(String message) {
        super(message);
    }

    public MemberNotExistException(Long memberId) {
        super();
        throw new IllegalArgumentException(msg_prefix + memberId + msg_suffix);
    }
}
