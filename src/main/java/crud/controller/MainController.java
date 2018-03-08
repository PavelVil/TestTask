package crud.controller;


import crud.model.User;
import crud.service.UserService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 30.06.2017.
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    @ResponseBody
    public void getReport(HttpServletResponse response) throws JRException, IOException {
        InputStream jasperStream = new FileInputStream(MainController.class.getResource("/reports/JREmp1.jrxml").getFile());

        Map<String, Object> params = new HashMap<>();
        List<User> userList = userService.getAll();
        JRDataSource dataSource = new JRBeanCollectionDataSource(userList);
        params.put("datasource", dataSource);
        JasperReport jasperReport
                = JasperCompileManager.compileReport(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=helloWorldReport.pdf");

        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String redirectToMain() {
        return "redirect:/users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String main(@RequestParam(required = false) Integer page, Model model) {
        setPagination(model, page);
        model.addAttribute("user", new User());
        return "home";
    }

    @RequestMapping(value = "/users/add", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public String addUser(@RequestParam(required = false) Integer page, @RequestParam("photoFile") MultipartFile photo, @Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            setPagination(model, page);
            return "home";
        }
        if (!photo.isEmpty()) {
            user.setPhoto(photo.getBytes());
        }
        if (user.getId() == 0) {
            userService.addUser(user);
        } else {
            userService.updateUser(user);
        }
        return "redirect:/users";
    }

    @RequestMapping(value = "/uploadPhoto", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public String uploadPhoto(@RequestParam("id") int id, @RequestParam("photo") MultipartFile photo) {
        if (!photo.isEmpty()) {
            try {
                byte[] fileBytes = photo.getBytes();
                if (id != 0) {
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
    public String updateUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("userList", userService.getAll());
        return "home";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "/search/", method = RequestMethod.POST)
    public String searchUser(@RequestParam("userName") String name, Model model) {
        model.addAttribute("searchingUsers", userService.getByName(name));
        return "searchings";
    }

    @RequestMapping(value = "/user/{id}/photo", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPhoto(@PathVariable("id") Integer id) {
        return userService.getById(id).getPhoto();
    }

    private void setPagination(Model model, Integer page) {
        PagedListHolder<User> pagedListHolder = new PagedListHolder<>(userService.getAll());
        pagedListHolder.setPageSize(3);
        model.addAttribute("maxPages", pagedListHolder.getPageCount());
        if (page == null || page < 1 || page > pagedListHolder.getPageCount())
            page = 1;
        model.addAttribute("page", page);
        if (page < 1 || page > pagedListHolder.getPageCount()) {
            pagedListHolder.setPage(0);
            model.addAttribute("listUsers", pagedListHolder.getPageList());
        } else if (page <= pagedListHolder.getPageCount()) {
            pagedListHolder.setPage(page - 1);
            model.addAttribute("listUsers", pagedListHolder.getPageList());
        }
    }
}
