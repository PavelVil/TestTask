package crud.model;

import java.util.List;

/**
 * Created by Pavel on 07.07.2017.
 */
public class JsonUser {

    private List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
