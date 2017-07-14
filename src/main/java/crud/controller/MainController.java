package crud.controller;


import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Pavel on 30.06.2017.
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String redirectToMain() {
        return "redirect:/users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String main(@RequestParam(required = false)Integer page,Model model){

        PagedListHolder<User> pagedListHolder = new PagedListHolder<>(userService.getAll());
        pagedListHolder.setPageSize(3);
        model.addAttribute("maxPages",pagedListHolder.getPageCount());
        if(page==null || page < 1 || page > pagedListHolder.getPageCount())
            page=1;
        model.addAttribute("page",page);
        if(page == null || page < 1 || page > pagedListHolder.getPageCount()) {
            pagedListHolder.setPage(0);
            model.addAttribute("listUsers",pagedListHolder.getPageList());
        }
        else if(page <= pagedListHolder.getPageCount()) {
            pagedListHolder.setPage(page-1);
            model.addAttribute("listUsers", pagedListHolder.getPageList());
        }
        model.addAttribute("user",new User());
        return "home";
    }

    @RequestMapping(value = "/users/add", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public String addUser(@RequestParam(required = false)Integer page,@RequestParam("photoFile")MultipartFile photo,@Valid @ModelAttribute("user")User user, BindingResult bindingResult, Model model) throws IOException {
        if(bindingResult.hasErrors()){
            PagedListHolder<User> pagedListHolder = new PagedListHolder<>(userService.getAll());
            pagedListHolder.setPageSize(3);
            model.addAttribute("maxPages",pagedListHolder.getPageCount());
            if(page==null || page < 1 || page > pagedListHolder.getPageCount())
                page=1;
            model.addAttribute("page",page);
            if(page == null || page < 1 || page > pagedListHolder.getPageCount()) {
                pagedListHolder.setPage(0);
                model.addAttribute("listUsers",pagedListHolder.getPageList());
            }
            else if(page <= pagedListHolder.getPageCount()) {
                pagedListHolder.setPage(page-1);
                model.addAttribute("listUsers", pagedListHolder.getPageList());
            }
            return "home";
        }
        if (!photo.isEmpty()){
            user.setPhoto(photo.getBytes());
        }
        if (user.getId()==0){
            userService.addUser(user);
        } else {
            userService.updateUser(user);
        }
        return "redirect:/users";
    }

    @RequestMapping(value = "/uploadPhoto", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public String uploadPhoto(@RequestParam("id") int id,@RequestParam("photo")MultipartFile photo) {
        if (!photo.isEmpty()) {
            try {
                byte[] fileBytes = photo.getBytes();
                if (id!=0){
                    User user = userService.getById(id);
                    user.setPhoto(fileBytes);
                    userService.updateUser(user);
                }
                return "redirect:/users";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    return "redirect:/users";
    }

    @RequestMapping(value = "/edit/{id}")
    public String updateUser(@PathVariable("id")int id, Model model){
        model.addAttribute("user",userService.getById(id));
        model.addAttribute("userList",userService.getAll());
        return "home";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "/search/", method = RequestMethod.POST)
    public String searchUser(@RequestParam("userName")String name, Model model){
        model.addAttribute("searchingUsers",userService.getByName(name));
        return "searchings";
    }
}