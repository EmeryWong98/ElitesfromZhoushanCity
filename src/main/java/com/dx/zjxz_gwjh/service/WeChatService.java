package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.zjxz_gwjh.dto.WeChatNetDetailsDto;
import com.dx.zjxz_gwjh.dto.WeChatStudentsDetailsDto;
import com.dx.zjxz_gwjh.entity.AreaNetEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.entity.UnionNetEntity;
import com.dx.zjxz_gwjh.enums.NetType;
import com.dx.zjxz_gwjh.repository.AreaNetRepository;
import com.dx.zjxz_gwjh.repository.HighSchoolNetRepository;
import com.dx.zjxz_gwjh.repository.StudentsRepository;
import com.dx.zjxz_gwjh.repository.UnionNetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeChatService {
    @Autowired
    private HighSchoolNetRepository highSchoolNetRepository;

    @Autowired
    private UnionNetRepository unionNetRepository;

    @Autowired
    private AreaNetRepository areaNetRepository;

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private DegreeBindingService degreeBindingService;

//    public WeChatNetDetailsDto getNetDetailsByUserId(String userId) {
//        WeChatNetDetailsDto netDetailsDto = new WeChatNetDetailsDto();
//
//        // Find in High School Net
//        List<HighSchoolNetEntity> highSchoolNet = highSchoolNetRepository.findByUserId(userId);
//        if (highSchoolNet != null) {
//            netDetailsDto.setUserId(userId);
//            netDetailsDto.setUserName(highSchoolNet.getUserName());
//            netDetailsDto.setPhone(highSchoolNet.getPhoneNumber());
//            netDetailsDto.setTotal(String.valueOf(studentsRepository.countByHighSchoolNetId(highSchoolNet.getId())));
//            return netDetailsDto;
//        }
//
//        // Find in Union Net
//        UnionNetEntity unionNet = unionNetRepository.findByUserId(userId);
//        if (unionNet != null) {
//            netDetailsDto.setUserId(userId);
//            netDetailsDto.setUserName(unionNet.getUserName());
//            netDetailsDto.setPhone(unionNet.getPhoneNumber());
//            netDetailsDto.setTotal(String.valueOf(studentsRepository.countByUnionNetId(unionNet.getId())));
//            return netDetailsDto;
//        }
//
//        // Find in Area Net
//        AreaNetEntity areaNet = areaNetRepository.findByUserId(userId);
//        if (areaNet != null) {
//            netDetailsDto.setUserId(userId);
//            netDetailsDto.setUserName(areaNet.getUserName());
//            netDetailsDto.setPhone(areaNet.getPhoneNumber());
//            netDetailsDto.setTotal(String.valueOf(studentsRepository.countByAreaNetId(areaNet.getId())));
//            return netDetailsDto;
//        }
//
//        // If no net found, return null or throw an exception
//        return null;
//    }
//
//    public String determineNetTypeByUserId(String userId) throws ServiceException {
//        if (highSchoolNetRepository.existsByUserId(userId)) {
//            return "HighSchoolNet";
//        } else if (unionNetRepository.existsByUserId(userId)) {
//            return "UnionNet";
//        } else if (areaNetRepository.existsByUserId(userId)) {
//            return "AreaNet";
//        } else {
//            throw new ServiceException("无效的网格类型");
//        }
//    }
//
//
//        public List<WeChatStudentsDetailsDto> getStudentsByUserId (String userId) throws ServiceException{
//            String netType = determineNetTypeByUserId(userId);
//            List<StudentsEntity> studentEntities;
//
//            switch (netType) {
//                case "HighSchoolNet":
//                    studentEntities = studentsRepository.findByHighSchoolNetId(userId);
//                    break;
//                case "UnionNet":
//                    studentEntities = studentsRepository.findByUnionNetId(userId);
//                    break;
//                case "AreaNet":
//                    studentEntities = studentsRepository.findByAreaNetId(userId);
//                    break;
//
//                default:
//                    throw new ServiceException("无效的网格类型");
//            }
//
//            return studentEntities.stream()
//                    .map(this::convertToDto)
//                    .collect(Collectors.toList());
//        }
//
//
//        private WeChatStudentsDetailsDto convertToDto (StudentsEntity entity){
//            WeChatStudentsDetailsDto dto = new WeChatStudentsDetailsDto();
//            dto.setStudentId(entity.getId());
//            dto.setStudentName(entity.getName());
//            dto.setAcademicYear(entity.getAcademicYear());
//            dto.setIsBack(entity.getIsBack());
//            dto.setUniversityName(degreeBindingService.findHighestDegreeUniversityNameByStudentId(entity.getId()));
//            return dto;
//        }

    }
