package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResponseDto;
import kg.attractor.jobsearch.exception.CustomException;

import java.util.List;
import java.util.Map;

public interface ResponseService {
    ResponseDto getResponseByVacancy(Integer vacancyId, String email);

    ResponseDto getById(Integer id, String userEmail);

    ResponseDto getById(Integer id);

    List<Map<String, Object>> fetchAllGroups ();

    void create(Integer vacancyId, Integer resumeId);
}
