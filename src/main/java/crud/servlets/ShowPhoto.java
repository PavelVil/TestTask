package crud.servlets;

import crud.utils.DaoUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by Pavel on 05.07.2017.
 */
@WebServlet(name = "showPhoto",urlPatterns = "/ShowPhoto")
public class ShowPhoto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");
        try (OutputStream outputStream = resp.getOutputStream()) {
            int id = Integer.parseInt(req.getParameter("id"));
            byte[] photo = DaoUtils.getPhoto(id);
            if (photo==null){
                URL photoUrl = ShowPhoto.class.getResource("/images/no_avatar.png");
                BufferedImage imageIO = ImageIO.read(photoUrl);
                ImageIO.setUseCache(false);
                ImageIO.write(imageIO, "png", outputStream);
                outputStream.flush();
            } else {
                resp.setContentLength(photo.length);
                outputStream.write(photo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
