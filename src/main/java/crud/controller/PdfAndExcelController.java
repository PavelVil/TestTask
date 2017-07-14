package crud.controller;

import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Pavel on 08.07.2017.
 */
@Controller
public class PdfAndExcelController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/pdf",method = RequestMethod.GET)
    public ModelAndView getPdf(){
        List<User> users = userService.getAll();
        return new ModelAndView("pdfDocument","modelObject",users);
    }

    @RequestMapping(value = "/excel",method = RequestMethod.GET)
    public ModelAndView getExcel(){
        List<User> users = userService.getAll();
        return new ModelAndView("excelDocument","modelObject",users);
    }

}
