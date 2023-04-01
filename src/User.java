/**
 *
 * @author Trevor Hartman
 * @author Geng Cha
 *
 * @since Version 1.0
 *
 */
public class User {

    private String username;
    private String passHash;

    /**
     *
     * @param username Class variables
     * @param passHash Class variables
     */
    public User(String username, String passHash) {

        this.username = username;
        this.passHash = passHash;

    }

    public String getUsername() {
        return this.username;
    }

    public String getPassHash() {
        return this.passHash;
    }

}
