package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.model.Authority;

public interface AuthorityService {
    Authority getById (Integer id);

    Authority getByName (String name);
}
