package crud.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Pavel on 07.07.2017.
 */
@XmlRootElement(name = "Users")
public class XmlUser {

    List<User> users;

    @XmlElement
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
