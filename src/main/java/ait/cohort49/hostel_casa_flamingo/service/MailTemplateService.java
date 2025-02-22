package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.dto.BookingDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailTemplateService {

    private final Configuration mailConfig;

    public MailTemplateService(Configuration mailConfig) {
        this.mailConfig = mailConfig;
        this.mailConfig.setDefaultEncoding("UTF-8");
        this.mailConfig.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/mail"));
    }

    public String generateConfirmationEmail(final User user, final String code, final String baseUrl) {
        try {
            Template template = mailConfig.getTemplate("confirm_reg_mail.ftlh");

            String confirmationLink = baseUrl + "/auth/confirm?code=" + code;

            Map<String, Object> modelPattern = new HashMap<>();
            modelPattern.put("name", user.getFirstName());
            modelPattern.put("confirmationLink", confirmationLink);

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, modelPattern);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateBookingConfirmationEmail(User user, List<BookingDto> bookings) {
        try {
            Template template = mailConfig.getTemplate("booking_confirmation.ftlh");

            Map<String, Object> model = new HashMap<>();
            model.put("user", user);
            model.put("bookings", bookings);

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Error while generating an email template", e);
        }
    }

}
