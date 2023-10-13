package com.dx.zjxz_gwjh.controller.management;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.annotation.Session;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.zjxz_gwjh.dto.UniversityDto;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import com.dx.zjxz_gwjh.filter.UniversityFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.DegreeBindingService;
import com.dx.zjxz_gwjh.service.UniversityService;
import com.dx.zjxz_gwjh.vo.UniversityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dx.easyspringweb.core.model.PagingData;

import javax.validation.Valid;
import java.util.List;

@ApiModule("University")
@Api(name = "UniversityManagement", description = "大学管理")
@RestController
@RequestMapping("/management/university")
@BindResource(value = "university:management")
public class UniversityManagementController {
    @Autowired
    private UniversityService universityService;

    @Autowired
    private DegreeBindingService degreeBindingService;

    @BindResource(value = "university:management:list")
    @Action(value = "查询大学列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<UniversityVO> list(@Session RDUserSession user, @RequestBody QueryRequest<UniversityFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<UniversityEntity> result = universityService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, UniversityVO.class));
    }

    @BindResource(value = "university:management:create")
    @Action(value = "创建大学信息", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public UniversityEntity create(@Session RDUserSession user, @Valid @RequestBody UniversityDto dto)
            throws ServiceException {

        UniversityEntity entity = universityService.newEntity(dto);

        entity = universityService.create(entity);

        return entity;
    }

    @BindResource("university:management:delete")
    @Action(value = "删除大学", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id)
            throws ServiceException {

        universityService.deleteById(id);
    }

    @BindResource(value = "university:management:details")
    @Action(value = "查询大学详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public UniversityEntity details(@RequestParam("id") String id)
            throws ServiceException {

        return universityService.getById(id);
    }

    @BindResource(value = "university:management:update")
    @Action(value = "更新大学信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody UniversityDto dto)
            throws ServiceException {

        UniversityEntity entity = universityService.getById(dto.getId());

        ObjectUtils.copyEntity(dto, entity);

        universityService.update(entity);
    }

    @BindResource("university:management:lists")
    @Action(value = "查询大学列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/lists")
    public List<UniversityEntity> lists(){
        	return universityService.lists();
    }


//    @BindResource("universities:management:import")
//    @Action(value = "导入大学信息", type = Action.ActionType.CREATE)
//    @PostMapping("/import")
//    public String importUniversities(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return "请选择文件";
//        }
//        try {
//            List<UniversitiesImportDto> universitiesList = parseExcelFile(file);
//            for (UniversitiesImportDto university : universitiesList) {
//                universityService.massiveCreateUniversity(university);
//            }
//            return "导入成功";
//        } catch (Exception e) {
//            return "导入失败：" + e.getMessage();
//        }
//    }
//
//    private List<UniversitiesImportDto> parseExcelFile(MultipartFile file) throws IOException {
//        List<UniversitiesImportDto> universitiesList = new ArrayList<>();
//        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
//            Sheet sheet = workbook.getSheetAt(0);
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                Row row = sheet.getRow(i);
//                UniversitiesImportDto dto = convertRowToDto(row);
//                universitiesList.add(dto);
//            }
//        }
//        return universitiesList;
//    }
//
//    private UniversitiesImportDto convertRowToDto(Row row) {
//        UniversitiesImportDto dto = new UniversitiesImportDto();
//        dto.setName(row.getCell(0).getStringCellValue());
//        dto.setProvince(row.getCell(1).getStringCellValue());
//        dto.setIsSupreme("是".equals(row.getCell(2).getStringCellValue()));
//        dto.setIsKeyMajor("是".equals(row.getCell(3).getStringCellValue()));
//        return dto;
//    }
//
////    @BindResource("universities:management:once")
////    @Action(value = "导入信息", type = Action.ActionType.CREATE)
////    @PostMapping("/once")
////    public String populateDegreeBinding() {
////        degreeBindingService.populateDegreeBinding();
////        return "DegreeBinding has been populated";
////    }



}
