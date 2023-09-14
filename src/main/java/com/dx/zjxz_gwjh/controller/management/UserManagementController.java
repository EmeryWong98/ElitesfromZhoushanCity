package com.dx.zjxz_gwjh.controller.management;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dx.zjxz_gwjh.dto.UserDto;
import com.dx.zjxz_gwjh.entity.UserEntity;
import com.dx.zjxz_gwjh.filter.UserFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.UserService;
import com.dx.zjxz_gwjh.vo.UserVO;
import com.dx.easyspringweb.biz.controller.management.BaseUserManagementController;
import com.dx.easyspringweb.biz.model.Role;
import com.dx.easyspringweb.biz.service.RoleService;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.PermitAll;
import com.dx.easyspringweb.core.exception.ArgumentException;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.rpc.account.enums.AccountStatus;
import com.dx.easyspringweb.rpc.account.enums.Gender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserManagementController
        extends
        BaseUserManagementController<RDUserSession, UserService, UserEntity, UserDto, UserVO, UserFilter, String> {

    private UserService service;

    @Autowired
    private RoleService<String> roleService;

    public UserManagementController(UserService service) {
        super(service);
        this.service = service;
    }

    @PermitAll
    @Action("加管理员")
    @PostMapping("/test/add")
    public void testAdd() throws ServiceException {
        Role<String> role = roleService.getByName("超级管理员");

        UserDto dto = new UserDto();
        dto.setUserName("chen6855");
        dto.setRealName("超级管理员");
        dto.setPassword("dx123456");
        dto.setPhoneNumber("13429196855");
        dto.setStatus(AccountStatus.NORMAL);
        dto.setRemarks("技术维护");
        dto.setRoleIds(Arrays.asList(role.getId()));

        this.create(dto);
        // log.info(dto.toString());
    }

    @PermitAll
    @Action("导入excel")
    @PostMapping("/test/import/file")
    public void testImportFile(
            @RequestParam("file") MultipartFile file)
            throws IOException, ServiceException, InterruptedException {
        // ZipSecureFile.setMinInflateRatio(0);

        try (
                Workbook workbook = new HSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                log.info("-------> 正在处理:" + i);
                try {
                    Row row = sheet.getRow(i);
                    String phoneNumber = this.getCell(row, 4);

                    this.create(
                            "RD_" + phoneNumber,
                            this.getGender(this.getCell(row, 1)),
                            this.getCell(row, 0),
                            phoneNumber);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    log.info("ERROR: 行" + i + "创建失败:" + ex.getMessage());
                }

                // break;
            }
        }

    }

    private Gender getGender(String sex) {
        if ("女".equals(sex)) {
            return Gender.FEMALE;
        }
        return Gender.MALE;
    }

    private String getCell(Row row, int index) {
        try {
            return row.getCell(index).getStringCellValue();
        } catch (Exception ex) {
            return null;
        }
    }

    private void create(String userName, Gender gender, String realName, String phoneNumber)
            throws ServiceException {
        log.info("-->" + userName + "," + realName + "," + phoneNumber);

        if (!StringUtils.hasText(userName) || !StringUtils.hasText(realName) || !StringUtils.hasText(phoneNumber)) {
            throw new ArgumentException();
        }

        Role<String> role = roleService.getByName("人大代表");

        UserEntity user = service.getByUserName(userName);
        if (user != null) {
            log.info("已存在用户：" + userName + "," + phoneNumber);
            return;
        }

        UserDto dto = new UserDto();
        dto.setUserName(userName);
        dto.setRealName(realName);
        dto.setPassword(RandomStringUtils.randomNumeric(7));
        dto.setPhoneNumber(phoneNumber);
        dto.setStatus(AccountStatus.NORMAL);
        dto.setGender(gender);
        dto.setRoleIds(Arrays.asList(role.getId()));
        log.info(dto.toString());
        this.create(dto);

        log.info("创建完成：" + userName + "," + phoneNumber);
    }

}
