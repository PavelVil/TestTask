package crud.dao;

import crud.model.User;

import java.util.List;

/**
 * Created by Pavel on 30.06.2017.
 */
public interface UserDao {

    List<User> getAll();

    void addUser(User user);

    void updateUser(User user);

    User getById(int id);

    void delete(int id);

    List<User> getByName(String name);

}
