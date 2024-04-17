package br.com.amaral.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.amaral.gestao_vagas.exceptions.UserFoundException;
import br.com.amaral.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.amaral.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity company) {
        this.companyRepository
                .findByUsernameOrEmail(company.getUsername(), company.getEmail())
                .ifPresent((data) -> {
                    throw new UserFoundException();
                });

        var password = this.passwordEncoder.encode(company.getPassword());
        company.setPassword(password);

        return this.companyRepository.save(company);
    }
}
