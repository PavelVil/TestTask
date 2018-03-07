package crud.model;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 08.07.2017.
 */
public class PdfDocument extends AbstractPdfView {

    @Override
    @SuppressWarnings("unchecked")
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        PdfPTable table = new PdfPTable(6);
        PdfPCell header1 = new PdfPCell(new Phrase("Имя"));
        PdfPCell header2 = new PdfPCell(new Phrase("Администратор"));
        PdfPCell header3 = new PdfPCell(new Phrase("Возраст"));
        PdfPCell header4 = new PdfPCell(new Phrase("Дата создания"));
        PdfPCell header5 = new PdfPCell(new Phrase("Город"));
        PdfPCell header6 = new PdfPCell(new Phrase("Email"));
        header1.setHorizontalAlignment(Element.ALIGN_LEFT);
        header2.setHorizontalAlignment(Element.ALIGN_LEFT);
        header3.setHorizontalAlignment(Element.ALIGN_LEFT);
        header4.setHorizontalAlignment(Element.ALIGN_LEFT);
        header5.setHorizontalAlignment(Element.ALIGN_LEFT);
        header6.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        table.addCell(header4);
        table.addCell(header5);
        table.addCell(header6);

        List<User> users = (List<User>) map.get("modelObject");
        for (User user : users) {
            table.addCell(user.getName());
            table.addCell(String.valueOf(user.isAdmin()));
            table.addCell(String.valueOf(user.getAge()));
            table.addCell(String.valueOf(user.getCreateDate()));
            table.addCell(user.getCity());
            table.addCell(user.getEmail());
        }
        String FONT_LOCATION = "/font/tahoma.ttf";
        BaseFont baseFont = BaseFont.createFont(FONT_LOCATION, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        document.add(new Paragraph("Русский", new Font(baseFont)));
        document.add(table);
    }
}
