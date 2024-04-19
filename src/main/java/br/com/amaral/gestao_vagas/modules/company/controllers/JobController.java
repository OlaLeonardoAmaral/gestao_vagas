package br.com.amaral.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.amaral.gestao_vagas.modules.company.entities.JobEntity;
import br.com.amaral.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/job")
public class JobController {
    
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping
    public ResponseEntity<Object> creaEntity(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        try {
            // getAtribut setado no SecurityFilter -> request.setAttribute("company_id", subjectToken);
            var companyId = request.getAttribute("company_id");
            JobEntity jobEntity = JobEntity.builder()
                                .companyId(UUID.fromString(companyId.toString()))
                                .description(createJobDTO.getDescription())
                                .benefits(createJobDTO.getBenefits())
                                .level(createJobDTO.getLevel())
                                .build();

            var result = this.createJobUseCase.execute(jobEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
