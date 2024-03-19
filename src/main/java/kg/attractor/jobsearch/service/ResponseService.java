package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResponseDto;
import kg.attractor.jobsearch.exception.CustomException;

public interface ResponseService {
    ResponseDto getResponseByVacancy (Integer vacancyId, String email) throws CustomException;
}
