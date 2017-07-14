package crud.model;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 08.07.2017.
 */
public class ExcelDocument extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        HSSFSheet excelSheet = workbook.createSheet("Информация о пользователях");

        response.setHeader("Content-Disposition","attachment; filename=excelDocument.xls");

        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);

        CellStyle styleHeader = workbook.createCellStyle();
        styleHeader.setFillForegroundColor(HSSFColor.BLUE.index);
        styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleHeader.setFont(font);

        setExcelHeader(excelSheet,styleHeader);

        List<User> users = (List<User>) map.get("modelObject");
        int rowCount = 1;
        for(User user:users){
            HSSFRow row = excelSheet.createRow(rowCount++);
            row.createCell(0).setCellValue(user.getName());
            row.createCell(1).setCellValue(user.isAdmin());
            row.createCell(2).setCellValue(user.getAge());
            row.createCell(3).setCellValue(user.getCreateDate().toString());
            row.createCell(4).setCellValue(user.getCity());
            row.createCell(5).setCellValue(user.getEmail());
        }
    }

    private void setExcelHeader(HSSFSheet excelSheet, CellStyle styleHeader) {
        HSSFRow header = excelSheet.createRow(0);
        header.createCell(0).setCellValue("Имя");
        header.getCell(0).setCellStyle(styleHeader);
        header.createCell(1).setCellValue("Администратор");
        header.getCell(1).setCellStyle(styleHeader);
        header.createCell(2).setCellValue("Возраст");
        header.getCell(2).setCellStyle(styleHeader);
        header.createCell(3).setCellValue("Дата создания");
        header.getCell(3).setCellStyle(styleHeader);
        header.createCell(4).setCellValue("Город");
        header.getCell(4).setCellStyle(styleHeader);
        header.createCell(5).setCellValue("Email");
        header.getCell(5).setCellStyle(styleHeader);
    }
}
