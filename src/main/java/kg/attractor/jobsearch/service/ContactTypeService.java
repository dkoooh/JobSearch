package kg.attractor.jobsearch.service;

public interface ContactTypeService {
    String getById(Integer typeId);

    Boolean isExists(Integer typeId);
}
