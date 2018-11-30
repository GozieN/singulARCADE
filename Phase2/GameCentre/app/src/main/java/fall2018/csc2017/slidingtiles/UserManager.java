package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A manager for all the users that play the games.
 */
class UserManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private static ArrayList<User> listOfUsers = new ArrayList<>();


    /**
     * @param username The name of the user.
     * @param password The password of the user.
     * @return boolean whether the sign in was successful
     */
    boolean signIn(String username, String password) {
        if (!listOfUsers.isEmpty()) {
            int i = 0;
            while (!listOfUsers.get(i).getUsername().equals(username)) {
                i += 1;
                if (i == listOfUsers.size()){
                    return false;
                }
            }
            return listOfUsers.get(i).getPassword().equals(password);
        }
        else {
            return false;
        }
    }


    /**
     * @param newusername The new user to be added to the list of Users.
     * @param newpassword The password of the user.
     * @return boolean whether the sign up was successful.
     */

    boolean signUp(String newusername, String newpassword) {
        int i = 0;
        if (!listOfUsers.isEmpty()) {
            while (!listOfUsers.get(i).getUsername().equals(newusername)) {
                i += 1;
                if (listOfUsers.size() == i) {
                    listOfUsers.add(new User(newusername, newpassword));
                    return true;
                }
            }
            return false;
        } else {
            listOfUsers.add(new User(newusername, newpassword));
            return true;
        }
    }

    /**
     * Return the user corresponding the the username input.
     *
     * @param username the username that is to be found
     * @return the username or null
     */

    User findUser(String username) {
        if (!listOfUsers.isEmpty()) {
            for (int i = 0; i < listOfUsers.size(); i++) {
                if (listOfUsers.get(i).getUsername().equals(username)) {
                    return listOfUsers.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Return the list of Users.
     *
     * @return ArrayList the list of Users
     */
    ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    /**
     * Return the most recently added user in the list of users.
     * @return the most recently added user in the list of users.
     */
    User lastAddedUser() {
        return getListOfUsers().get(getListOfUsers().size()-1);
    }

    /**
     * Empty the list of users
     */
    void emptyListOfUsers() {
        listOfUsers = new ArrayList<>();
    }
}
