package com.project_app.project_management.issue;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/issue")
public class IssueController {
    private final IssueService issueService;
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }
    @PostMapping ("/add-issue/{projectId}")
    public ResponseEntity<IssueResponse> addIssue(@RequestBody IssueDto issueDto, @PathVariable int projectId) {
        return ResponseEntity.ok(issueService.addIssue(issueDto ,projectId)) ;

    }
    @GetMapping("/get-all/{projectId}")
    public ResponseEntity<List<IssueResponse>> getAllIssues(@PathVariable int projectId) {
         return ResponseEntity.ok(issueService.getAllIssues(projectId));
    }
    @PatchMapping("/mark-as-solved/{issueId}")
    public ResponseEntity<IssueResponse> markAsSolved(@PathVariable int issueId) {
        return  ResponseEntity.ok(issueService.markAsReSolved(issueId)) ;
    }


}
