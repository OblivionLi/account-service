package account.service;

import account.model.security.Security;
import account.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService {
    @Autowired
    SecurityRepository securityRepository;

    public void addLogging(String action, String subject, String object, String path) {
        Security security = new Security(
                action,
                subject,
                object,
                path
        );
        securityRepository.save(security);
    }

    public ResponseEntity<List<Security>> getAllEvents() {
        List<Security> securities = securityRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return ResponseEntity.status(HttpStatus.OK).body(securities);
    }
}
