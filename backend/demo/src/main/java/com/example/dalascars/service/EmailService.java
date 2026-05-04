package com.example.dalascars.service;

import com.example.dalascars.entity.EstimationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendConfirmationEmail(EstimationRequest request) {
        try {
            String to = request.getUser() != null
                    ? request.getUser().getEmail()
                    : request.getContactEmail();

            String firstName = request.getUser() != null
                    ? request.getUser().getFirstName()
                    : request.getContactFirstName();

            String brandName = request.getBrand() != null
                    ? request.getBrand().getName()
                    : request.getCustomBrand();

            String modelName = request.getCarModel() != null
                    ? request.getCarModel().getName()
                    : request.getCustomModel();

            String trackingUrl = "http://localhost:4200/suivi/" + request.getTrackingToken();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("✅ DalasCars — Votre demande d'estimation a été reçue");
            helper.setFrom("noreply@dalascars.be");

            String html = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; background: #080808; color: white; border-radius: 16px; overflow: hidden;">
                    <div style="background: linear-gradient(135deg, #1a1505, #2a2008); padding: 40px; text-align: center;">
                        <h1 style="font-size: 28px; font-weight: 900; margin: 0;">
                            Dalas<span style="color: #D4AF37;">Cars</span>
                        </h1>
                    </div>
                    <div style="padding: 40px;">
                        <h2 style="color: #D4AF37; font-size: 20px; margin-bottom: 16px;">
                            Bonjour %s,
                        </h2>
                        <p style="color: rgba(255,255,255,0.7); line-height: 1.6; margin-bottom: 24px;">
                            Nous avons bien reçu votre demande d'estimation pour votre <strong style="color: white;">%s %s</strong>.
                            Notre expert va analyser votre dossier et vous répondre en moins de <strong style="color: #D4AF37;">2 heures</strong>.
                        </p>
                        <div style="background: rgba(212,175,55,0.08); border: 1px solid rgba(212,175,55,0.2); border-radius: 12px; padding: 24px; margin-bottom: 32px;">
                            <p style="color: rgba(255,255,255,0.5); font-size: 13px; margin: 0 0 8px 0;">Suivez votre demande en temps réel :</p>
                            <a href="%s"
                               style="display: inline-block; background: linear-gradient(135deg, #D4AF37, #B8860B); color: #080808; font-weight: 900; padding: 14px 32px; border-radius: 10px; text-decoration: none; font-size: 15px;">
                                Voir mon estimation →
                            </a>
                        </div>
                        <p style="color: rgba(255,255,255,0.3); font-size: 12px; line-height: 1.6;">
                            Si vous n'avez pas soumis cette demande, ignorez cet email.<br/>
                            © 2026 DalasCars — Bruxelles, Belgique
                        </p>
                    </div>
                </div>
                """.formatted(firstName, brandName, modelName, trackingUrl);

            helper.setText(html, true);
            mailSender.send(message);

        } catch (Exception e) {
            System.err.println("Erreur envoi email : " + e.getMessage());
        }
    }

    public void sendEstimationEmail(EstimationRequest request) {
        try {
            String to = request.getUser() != null
                    ? request.getUser().getEmail()
                    : request.getContactEmail();

            String firstName = request.getUser() != null
                    ? request.getUser().getFirstName()
                    : request.getContactFirstName();

            String trackingUrl = "http://localhost:4200/suivi/" + request.getTrackingToken();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("💰 DalasCars — Votre estimation est prête !");
            helper.setFrom("noreply@dalascars.be");

            String html = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; background: #080808; color: white; border-radius: 16px; overflow: hidden;">
                    <div style="background: linear-gradient(135deg, #1a1505, #2a2008); padding: 40px; text-align: center;">
                        <h1 style="font-size: 28px; font-weight: 900; margin: 0;">
                            Dalas<span style="color: #D4AF37;">Cars</span>
                        </h1>
                    </div>
                    <div style="padding: 40px;">
                        <h2 style="color: #D4AF37; font-size: 20px; margin-bottom: 16px;">
                            Bonjour %s, votre estimation est prête !
                        </h2>
                        <p style="color: rgba(255,255,255,0.7); line-height: 1.6; margin-bottom: 24px;">
                            Notre expert a analysé votre véhicule. Cliquez sur le bouton ci-dessous pour voir votre estimation complète.
                        </p>
                        <div style="background: rgba(212,175,55,0.08); border: 1px solid rgba(212,175,55,0.2); border-radius: 12px; padding: 24px; margin-bottom: 32px; text-align: center;">
                            <a href="%s"
                               style="display: inline-block; background: linear-gradient(135deg, #D4AF37, #B8860B); color: #080808; font-weight: 900; padding: 14px 32px; border-radius: 10px; text-decoration: none; font-size: 15px;">
                                Voir mon estimation →
                            </a>
                        </div>
                        <p style="color: rgba(255,255,255,0.3); font-size: 12px; line-height: 1.6;">
                            © 2026 DalasCars — Bruxelles, Belgique
                        </p>
                    </div>
                </div>
                """.formatted(firstName, trackingUrl);

            helper.setText(html, true);
            mailSender.send(message);

        } catch (Exception e) {
            System.err.println("Erreur envoi email estimation : " + e.getMessage());
        }
    }
}