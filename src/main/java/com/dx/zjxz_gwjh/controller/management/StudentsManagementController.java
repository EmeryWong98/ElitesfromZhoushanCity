package com.dx.zjxz_gwjh.controller.management;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.annotation.Session;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.zjxz_gwjh.dto.StudentsCreateDto;
import com.dx.zjxz_gwjh.dto.StudentsDto;
import com.dx.zjxz_gwjh.dto.StudentsImportDto;
import com.dx.zjxz_gwjh.entity.DegreeBindingEntity;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import com.dx.zjxz_gwjh.filter.StudentsFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.repository.DegreeBindingRepository;
import com.dx.zjxz_gwjh.repository.UniversityRepository;
import com.dx.zjxz_gwjh.service.AreaCodeService;
import com.dx.zjxz_gwjh.service.DegreeBindingService;
import com.dx.zjxz_gwjh.service.StudentsService;
import com.dx.zjxz_gwjh.vo.StudentsVO;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApiModule("Students")
@Api(name = "StudentsManagement", description = "学子管理")
@RestController
@RequestMapping("/management/students")
@BindResource(value = "students:management")
public class StudentsManagementController {
    @Autowired
    private StudentsService studentsService;

    @Autowired
    private AreaCodeService areaCodeService;

    @Autowired
    private DegreeBindingService degreeBindingService;

    @Autowired
    private DegreeBindingRepository degreeBindingRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @BindResource(value = "students:management:list")
    @Action(value = "查询学生列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<StudentsVO> list(@Session RDUserSession user, @RequestBody QueryRequest<StudentsFilter> query)
            throws ServiceException {

        if (user.isStaff()) {
            StudentsFilter filter = query.getFilter();
            if (filter == null) {
                filter = new StudentsFilter();
            }
            filter.setArea(user.getTownship());
            query.setFilter(filter);
        }

        PagingData<StudentsEntity> result = studentsService.queryList(query);

        return result.map((entity) -> {
            StudentsVO vo = ObjectUtils.copyEntity(entity, StudentsVO.class);
            vo.setUniversityName(degreeBindingService.findHighestDegreeUniversityNameByStudentId(entity.getId()));
            vo.setUniversityProvince(degreeBindingService.findHighestDegreeUniversityProvinceByStudentId(entity.getId()));
            vo.setDegree(degreeBindingService.findHighestDegreeByStudentId(entity.getId()));
            vo.setMajor(degreeBindingService.findHighestDegreeMajorByStudentId(entity.getId()));
            return vo;
        });
    }


    @BindResource(value = "students:management:create")
    @Action(value = "创建学生信息", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public StudentsEntity create(@Session RDUserSession user, @Valid @RequestBody StudentsCreateDto dto)
            throws ServiceException {

        // 使用新的createStudent方法来创建或更新学生实体
        StudentsEntity entity = studentsService.createStudent(dto);

        // 其他逻辑（如果有的话）...

        return entity;
    }

    @BindResource("students:management:update")
    @Action(value = "更新学生信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody StudentsDto dto) throws ServiceException {
        // 获取现有的学生实体
        StudentsEntity entity = studentsService.getById(dto.getId());

        // 将dto中的字段复制到现有的学生实体中
        ObjectUtils.copyEntity(dto, entity);

        // 更新学生实体
        studentsService.updateStudents(dto);
    }


    @BindResource("students:management:delete")
    @Action(value = "删除学生", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) throws ServiceException {
        studentsService.deleteById(id);
    }

    @BindResource("students:management:details")
    @Action(value = "查询学生详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public StudentsDto details(@RequestParam("id") String id)
            throws ServiceException {
        StudentsEntity studentEntity = studentsService.getById(id);
        if (studentEntity == null) {
            throw new ServiceException("学生不存在");
        }

        StudentsDto dto = smartConvertToDto(studentEntity);
        return dto;
    }

        private StudentsDto convertToDto(StudentsEntity studentEntity)
            throws ServiceException {
            StudentsDto dto = new StudentsDto();
            // 将 studentEntity 的属性复制到 dto
            ObjectUtils.copyEntity(studentEntity, dto);

            List<DegreeBindingEntity> degrees = degreeBindingRepository.findByStudentId(studentEntity.getId());
            for (int i = 0; i < degrees.size(); i++) {
                DegreeBindingEntity degree = degrees.get(i);
                UniversityEntity university = universityRepository.findById(degree.getUniversityId()).orElse(null);

                if (university != null) {
                    if (i == 0) {
                        dto.setUniversity1Name(university.getName());
                        dto.setUniversity1Province(university.getProvince());
                        dto.setMajor1(degree.getMajor());
                        dto.setDegree1(degree.getDegree().getDescription());
                    } else if (i == 1) {
                        dto.setUniversity2Name(university.getName());
                        dto.setUniversity2Province(university.getProvince());
                        dto.setMajor2(degree.getMajor());
                        dto.setDegree2(degree.getDegree().getDescription());
                    } else if (i == 2) {
                        dto.setUniversity3Name(university.getName());
                        dto.setUniversity3Province(university.getProvince());
                        dto.setMajor3(degree.getMajor());
                        dto.setDegree3(degree.getDegree().getDescription());
                    }
                }
            }

            return dto;
        }

    private StudentsDto smartConvertToDto(StudentsEntity studentEntity)
            throws ServiceException {
        StudentsDto dto = new StudentsDto();
        // 将 studentEntity 的属性复制到 dto
        ObjectUtils.copyEntity(studentEntity, dto);

        List<DegreeBindingEntity> degrees = degreeBindingRepository.findByStudentId(studentEntity.getId());
        for (DegreeBindingEntity degree : degrees) {
            UniversityEntity university = universityRepository.findById(degree.getUniversityId()).orElse(null);

            if (university != null) {
                switch(degree.getDegree()) {
                    case Undergraduate:
                        dto.setUniversity1Name(university.getName());
                        dto.setUniversity1Province(university.getProvince());
                        dto.setMajor1(degree.getMajor());
                        dto.setDegree1(degree.getDegree().getDescription());
                        break;
                    case Graduate:
                        dto.setUniversity2Name(university.getName());
                        dto.setUniversity2Province(university.getProvince());
                        dto.setMajor2(degree.getMajor());
                        dto.setDegree2(degree.getDegree().getDescription());
                        break;
                    case PHD:
                        dto.setUniversity3Name(university.getName());
                        dto.setUniversity3Province(university.getProvince());
                        dto.setMajor3(degree.getMajor());
                        dto.setDegree3(degree.getDegree().getDescription());
                        break;
                }
            }
        }

        return dto;
    }



    @BindResource("students:management:import")
    @Action(value = "导入学生信息", type = Action.ActionType.CREATE)
    @PostMapping("/import")
    public String importStudents(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return "请选择文件";
        }
        try {
            List<StudentsImportDto> studentsList = parseExcelFile(file);
            for (StudentsImportDto student : studentsList) {
                studentsService.MassiveCreateStudent(student);
            }
            return "导入成功";
        } catch (Exception e) {
            return "导入失败：" + e.getMessage();
        }
    }

    private List<StudentsImportDto> parseExcelFile(MultipartFile file) throws IOException {
        List<StudentsImportDto> studentsList = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                StudentsImportDto dto = convertRowToDto(row);
                studentsList.add(dto);
            }
        }
        return studentsList;
    }

    private StudentsImportDto convertRowToDto(Row row) {
        DataFormatter dataFormatter = new DataFormatter();
        StudentsImportDto dto = new StudentsImportDto();

        dto.setAcademicYear(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(1)))); // 从第二列开始
        dto.setName(dataFormatter.formatCellValue(row.getCell(2)));
        dto.setProvince(dataFormatter.formatCellValue(row.getCell(3)));
        dto.setUniversityName(dataFormatter.formatCellValue(row.getCell(4)));
        dto.setUniversityProvince(dataFormatter.formatCellValue(row.getCell(5)));
        dto.setMajor(dataFormatter.formatCellValue(row.getCell(6)));
        dto.setDegree(dataFormatter.formatCellValue(row.getCell(7)));
        dto.setIdCard(dataFormatter.formatCellValue(row.getCell(8)));
        dto.setHighSchoolName(dataFormatter.formatCellValue(row.getCell(9)));

        String isKeyContact = dataFormatter.formatCellValue(row.getCell(10));
        dto.setIsKeyContact(!"否".equals(isKeyContact));
        dto.setIsSupreme("是（双一流）".equals(isKeyContact));

        dto.setPhone(dataFormatter.formatCellValue(row.getCell(11)));
        dto.setArea(dataFormatter.formatCellValue(row.getCell(12)));
        dto.setAddress(dataFormatter.formatCellValue(row.getCell(13)));
        dto.setFamilyContactor(dataFormatter.formatCellValue(row.getCell(14)));
        dto.setFamilyContactorMobile(dataFormatter.formatCellValue(row.getCell(15)));
        dto.setUndergraduateClass(dataFormatter.formatCellValue(row.getCell(16)));
        dto.setHeadTeacher(dataFormatter.formatCellValue(row.getCell(17)));
        dto.setHeadTeacherMobile(dataFormatter.formatCellValue(row.getCell(18)));

        if (StringUtils.isNotBlank(dataFormatter.formatCellValue(row.getCell(19)))) {
            dto.setHighSchoolNetName(dataFormatter.formatCellValue(row.getCell(19)));
            dto.setHighSchoolNetContactor(dataFormatter.formatCellValue(row.getCell(20)));
            dto.setHighSchoolNetContactorMobile(dataFormatter.formatCellValue(row.getCell(21)));
            dto.setHighSchoolNetAreaCode(areaCodeService.getAreaCodeByName(dataFormatter.formatCellValue(row.getCell(12))));
            dto.setHighSchoolNetLocation(dataFormatter.formatCellValue(row.getCell(9)));
        }

        if (StringUtils.isNotBlank(dataFormatter.formatCellValue(row.getCell(22)))) {
            dto.setAreaNetName(dataFormatter.formatCellValue(row.getCell(22)));
            dto.setAreaNetContactor(dataFormatter.formatCellValue(row.getCell(23)));
            dto.setAreaNetContactorMobile(dataFormatter.formatCellValue(row.getCell(24)));
            dto.setAreaNetAreaCode(areaCodeService.getAreaCodeByName(dataFormatter.formatCellValue(row.getCell(12))));
            dto.setAreaNetLocation(dataFormatter.formatCellValue(row.getCell(12)));
        }

        if (StringUtils.isNotBlank(dataFormatter.formatCellValue(row.getCell(25)))) {
            dto.setOfficerNetName(dataFormatter.formatCellValue(row.getCell(25)));
            dto.setOfficerNetPosition(dataFormatter.formatCellValue(row.getCell(26)));
        }

        if (StringUtils.isNotBlank(dataFormatter.formatCellValue(row.getCell(27)))) {
            dto.setUnionNetName(dataFormatter.formatCellValue(row.getCell(27)));
            dto.setUnionNetContactor(dataFormatter.formatCellValue(row.getCell(28)));
            dto.setUnionNetContactorMobile(dataFormatter.formatCellValue(row.getCell(29)));
            dto.setUnionNetLocation(dataFormatter.formatCellValue(row.getCell(5)));
        }

        return dto;
    }

}
