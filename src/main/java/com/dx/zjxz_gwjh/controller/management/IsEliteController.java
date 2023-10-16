package com.dx.zjxz_gwjh.controller.management;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.zjxz_gwjh.util.UniversityMajorDictionary;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static com.dx.zjxz_gwjh.util.UniversityMajorDictionary.MAJOR_DICTIONARY;

@ApiModule("IsElite")
@Api(name = "IsEliteManagement", description = "是否重点学子批量处理")
@RestController
@RequestMapping("/management/IsElite")
@BindResource(value = "IsElite:management")
public class IsEliteController {

    private static final String UPLOADED_EXCEL_PATH = "C:\\Users\\emryw\\Downloads\\是否重点学子导入模板.xlsx";
    private static final String ORIGINAL_EXCEL_PATH = "C:\\Users\\emryw\\Downloads\\是否重点学子导入.xlsx";
    private static final String MODIFIED_EXCEL_PATH = "C:\\Users\\emryw\\Downloads\\是否重点学子导出.xlsx";
    private static final List<String> TOP_SCHOOLS = Arrays.asList("北京大学", "清华大学", "中国人民大学", "北京航空航天大学", "北京理工大学",
            "中国农业大学", "北京师范大学", "中央民族大学", "南开大学", "天津大学",
            "大连理工大学", "吉林大学", "哈尔滨工业大学", "复旦大学", "同济大学",
            "上海交通大学", "华东师范大学", "南京大学", "东南大学", "浙江大学",
            "中国科学技术大学", "厦门大学", "山东大学", "中国海洋大学", "武汉大学",
            "华中科技大学", "中南大学", "中山大学", "华南理工大学", "四川大学",
            "重庆大学", "电子科技大学", "西安交通大学", "西北工业大学", "兰州大学",
            "国防科技大学", "东北大学", "郑州大学", "湖南大学", "云南大学",
            "西北农林科技大学", "新疆大学", "北京交通大学", "北京工业大学", "北京科技大学",
            "北京化工大学", "北京邮电大学", "北京林业大学", "北京中医药大学", "北京外国语大学",
            "中国传媒大学", "中央财经大学", "对外经济贸易大学", "北京体育大学", "中央音乐学院",
            "中国政法大学", "天津医科大学", "河北工业大学", "太原理工大学", "内蒙古大学",
            "辽宁大学", "大连海事大学", "延边大学", "东北师范大学", "哈尔滨工程大学",
            "东北农业大学", "东北林业大学", "华东理工大学", "东华大学", "上海外国语大学",
            "上海财经大学", "上海大学", "苏州大学", "南京航空航天大学", "南京理工大学",
            "河海大学", "江南大学", "南京农业大学", "中国药科大学", "南京师范大学",
            "安徽大学", "合肥工业大学", "福州大学", "南昌大学", "武汉理工大学",
            "华中农业大学", "华中师范大学", "中南财经政法大学", "湖南师范大学", "暨南大学",
            "华南师范大学", "海南大学", "广西大学", "西南交通大学", "四川农业大学",
            "西南大学", "西南财经大学", "贵州大学", "西藏大学", "西北大学",
            "西安电子科技大学", "长安大学", "陕西师范大学", "青海大学", "宁夏大学",
            "石河子大学", "海军军医大学", "空军军医大学", "中国地质大学（北京）", "中国地质大学（武汉）",
            "中国石油大学（北京）", "中国石油大学（华东）", "中国矿业大学", "中国矿业大学（北京）", "华北电力大学",
            "华北电力大学（保定）", "中国科学院大学", "中国社会科学院大学", "中国地质大学", "中国石油大学");  // 省略了其他学校

    private String isTalentUpdated(Row row) {
        Cell schoolCell = row.getCell(4);
        Cell majorCell = row.getCell(5);

        String school = schoolCell != null ? schoolCell.getStringCellValue() : "";
        String major = majorCell != null ? majorCell.getStringCellValue() : "";

        if (TOP_SCHOOLS.contains(school)) {
            return "是（双一流）";
        }

        for (String topSchool : TOP_SCHOOLS) {
            if (school.contains(topSchool)) {
                return "待定";
            }
        }

        for (Map.Entry<String, List<String>> entry : MAJOR_DICTIONARY.entrySet()) {
            if (school.contains(entry.getKey())) {
                if (major.isEmpty()) {
                    return "待定";
                }
                for (String m : entry.getValue()) {
                    if (major.contains(m)) {
                        return "是";
                    }
                }
                return "待定";
            }
        }

        return "否";
    }

    public String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return Double.toString(cell.getNumericCellValue());
            // ... 其他类型的处理逻辑
            default:
                return "";
        }
    }

    @PostMapping("/import")
    public String importExcel(@RequestParam MultipartFile file) throws IOException {
        // 保存上传的文件
        file.transferTo(new File(ORIGINAL_EXCEL_PATH));

        FileInputStream fis = new FileInputStream(ORIGINAL_EXCEL_PATH);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // 遍历所有行并处理数据
        for (Row row : sheet) {
            Cell universityCell = row.getCell(4);
            Cell majorCell = row.getCell(5);

            if (universityCell == null || majorCell == null) {
                continue;  // 跳过没有数据的行
            }

            String university = getCellValue(universityCell);
            String major = getCellValue(majorCell);

            List<String> validMajors = UniversityMajorDictionary.MAJOR_DICTIONARY.get(university);

            // 如果大学存在且专业无效，则更新专业为 "其他"
            if (validMajors != null && !validMajors.contains(major)) {
                majorCell.setCellValue("其他");
            }

            // 更新或设置 "是否为重点联络学子" 的值
            Cell talentCell = row.getCell(10);
            if (talentCell == null) {
                talentCell = row.createCell(10);
            }
            talentCell.setCellValue(isTalentUpdated(row));
        }

        workbook.write(new FileOutputStream(MODIFIED_EXCEL_PATH));
        fis.close();
        workbook.close();

        return "Data processed and saved successfully!";
    }

    @PostMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        FileInputStream fis = new FileInputStream(MODIFIED_EXCEL_PATH);
        byte[] bytes = fis.readAllBytes();
        fis.close();

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=是否重点学子导出.xlsx");
        response.getOutputStream().write(bytes);
        response.flushBuffer();
    }

    @PostMapping("/download-template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        FileInputStream fis = new FileInputStream(UPLOADED_EXCEL_PATH);
        byte[] bytes = fis.readAllBytes();
        fis.close();

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=是否重点学子导入模板.xlsx");
        response.getOutputStream().write(bytes);
        response.flushBuffer();
    }
}
