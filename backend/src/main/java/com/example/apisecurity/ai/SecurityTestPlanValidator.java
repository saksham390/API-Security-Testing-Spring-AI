package com.example.apisecurity.ai;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.example.apisecurity.service.TestRunService;

@Component
public class SecurityTestPlanValidator {
    public SecurityTestPlan validate(SecurityTestPlan plan){
        if(plan==null||plan.endpointId()==null||plan.tests()==null||plan.tests().isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"AI plan is empty");
        List<SecurityTestItem> items=plan.tests().stream().filter(item->item!=null&&item.testType()!=null&&TestRunService.allowed(item.testType().name())).toList();
        if(items.size()!=plan.tests().size()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"AI plan contains an unknown test type");
        return new SecurityTestPlan(plan.endpointId(),items);
    }
}
