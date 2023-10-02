package com.dx.zjxz_gwjh.service;

import com.dx.zjxz_gwjh.entity.DegreeBindingEntity;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.enums.DegreeType;
import com.dx.zjxz_gwjh.repository.DegreeBindingRepository;
import com.dx.zjxz_gwjh.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DegreeBindingService {

    @Autowired
    private StudentsRepository studentsRepository; // Assume this is your Repository for StudentsEntity

    @Autowired
    private DegreeBindingRepository degreeBindingRepository; // Assume this is your Repository for DegreeBinding

    @PersistenceContext
    private EntityManager entityManager;


    public String findHighestDegreeUniversityNameByStudentId(String studentId) {
        String queryString = "SELECT db.universityId, db.degree " +
                "FROM DegreeBindingEntity db " +
                "WHERE db.studentId = :studentId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("studentId", studentId);

        List<Object[]> results = query.getResultList();

        return results.stream()
                .collect(Collectors.groupingBy(arr -> (String) arr[0],
                        Collectors.mapping(arr -> (DegreeType) arr[1], Collectors.toList())))
                .entrySet()
                .stream()
                .max((e1, e2) -> e1.getValue().stream().max(DegreeType::compareTo).orElse(DegreeType.Undergraduate)
                        .compareTo(e2.getValue().stream().max(DegreeType::compareTo).orElse(DegreeType.Undergraduate)))
                .map(e -> findUniversityNameById(e.getKey()))
                .orElse(null);
    }

    private String findUniversityNameById(String universityId) {
        String queryString = "SELECT u.name FROM UniversityEntity u WHERE u.id = :universityId";
        try {
            return (String) entityManager.createQuery(queryString)
                    .setParameter("universityId", universityId)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Handle the case where no result is found, if needed
            return null;
        }
    }

    public String findHighestDegreeUniversityProvinceByStudentId(String id) {
        String queryString = "SELECT db.universityId, db.degree " +
                "FROM DegreeBindingEntity db " +
                "WHERE db.studentId = :studentId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("studentId", id);

        List<Object[]> results = query.getResultList();

        return results.stream()
                .collect(Collectors.groupingBy(arr -> (String) arr[0],
                        Collectors.mapping(arr -> (DegreeType) arr[1], Collectors.toList())))
                .entrySet()
                .stream()
                .max((e1, e2) -> e1.getValue().stream().max(DegreeType::compareTo).orElse(DegreeType.Undergraduate)
                        .compareTo(e2.getValue().stream().max(DegreeType::compareTo).orElse(DegreeType.Undergraduate)))
                .map(e -> findUniversityProvinceById(e.getKey()))
                .orElse(null);
    }

    private String findUniversityProvinceById(String universityId) {
        String queryString = "SELECT u.province FROM UniversityEntity u WHERE u.id = :universityId";
        try {
            return (String) entityManager.createQuery(queryString)
                    .setParameter("universityId", universityId)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Handle the case where no result is found, if needed
            return null;
        }
    }

    public String findHighestDegreeByStudentId(String id) {
        String queryString = "SELECT db.degree " +
                "FROM DegreeBindingEntity db " +
                "WHERE db.studentId = :studentId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("studentId", id);

        List<DegreeType> results = query.getResultList();

        return results.stream()
                .max(DegreeType::compareTo)
                .map(DegreeType::getDescription)
                .orElse(null);
    }

    public String findHighestDegreeMajorByStudentId(String id) {
        String queryString = "SELECT db.major " +
                "FROM DegreeBindingEntity db " +
                "WHERE db.studentId = :studentId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("studentId", id);

        List<String> results = query.getResultList();

        return results.stream()
                .max(String::compareTo)
                .orElse(null);
    }



//    @Transactional
//    public void populateDegreeBinding() {
//        try (Stream<StudentsEntity> studentsStream = studentsRepository.streamAllStudents()) {
//            studentsStream.forEach(student -> {
//                DegreeBindingEntity degreeBinding = new DegreeBindingEntity();
//                degreeBinding.setStudentId(student.getId());
//                degreeBinding.setUniversityId(student.getUniversityId());
//                try {
//                    DegreeType degreeType = DegreeType.fromDescription(student.getDegree());
//                    degreeBinding.setDegree(degreeType);
//                } catch (IllegalArgumentException e) {
//                    System.err.println("Invalid degree value: " + student.getDegree());
//                    // You can continue to the next student as the current one has an invalid degree value
//                    return;
//                }
//                degreeBindingRepository.save(degreeBinding);
//            });
//        }
//    }
}

