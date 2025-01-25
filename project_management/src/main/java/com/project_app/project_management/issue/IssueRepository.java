package com.project_app.project_management.issue;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends CrudRepository<Issue, Integer> {
  public List<Issue> findByProject_Id(int id);


}
