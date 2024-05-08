package kg.attractor.jobsearch.dto.response;

import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private Integer id;
    private ResumeDto resume;
    private VacancyDto vacancy;
    private Boolean isConfirmed;
}
