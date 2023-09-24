package com.dx.zjxz_gwjh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexService {
    @Autowired
    private NetService netService;

    @Autowired
    private HighSchoolService highSchoolService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private UniversityService universityService;

    public int getNetCount() {
        return netService.count();
    }

    public int getHighSchoolCount() {
        return highSchoolService.count();
    }

    public int getStudentsCount() {
        return studentsService.count();
    }

    public int getUniversityCount() {
        return universityService.count();
    }

}
