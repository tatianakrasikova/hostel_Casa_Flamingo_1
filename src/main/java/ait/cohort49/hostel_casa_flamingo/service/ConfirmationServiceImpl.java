package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.entity.ConfirmationCode;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.ConfirmationCodeRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.ConfirmationService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationCodeRepository repository;

    public ConfirmationServiceImpl(ConfirmationCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generateConfirmationCode(User user) {

        String code = UUID.randomUUID().toString();

        ConfirmationCode confirmationCode = new ConfirmationCode(
                code,
                LocalDateTime.now().plusDays(3),
                user
        );

        repository.save(confirmationCode);
        return code;
    }

    @Transactional
    @Override
    public ConfirmationCode validateToken(String confirmationTokenCode) {
        ConfirmationCode confirmationCode = findOrThrow(confirmationTokenCode);
        LocalDateTime tokenExpiration = confirmationCode.getExpired();

        if (tokenExpiration.isBefore(LocalDateTime.now())) {
            throw new RestException(HttpStatus.UNAUTHORIZED, "Token expired");
        }
        repository.delete(confirmationCode);
        return confirmationCode;
    }

    @Transactional
    @Override
    public ConfirmationCode findOrThrow(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Confirmation code not found"));
    }
}
