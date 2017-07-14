package crud.controller;

import crud.model.JsonUser;
import crud.model.User;
import crud.model.XmlUser;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Pavel on 07.07.2017.
 */
@RestController
public class XmlAndJsonController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/json/getAll", produces = "application/json")
    public JsonUser getAllUsersJson(){
        List<User> users = null;
        try {
            users = userService.getAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        JsonUser jsonUser = new JsonUser();
        jsonUser.setUsers(users);
        return jsonUser;
    }

    @RequestMapping(value = "/xml/getAll", produces = "application/xml")
    public XmlUser getAllUserXml(){
        List<User> users = null;
        try {
            users = userService.getAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        XmlUser xmlUser = new XmlUser();
        xmlUser.setUsers(users);
        return xmlUser;
    }

}
