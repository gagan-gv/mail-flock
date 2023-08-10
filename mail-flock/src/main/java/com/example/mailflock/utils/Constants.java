package com.example.mailflock.utils;

import org.springframework.beans.factory.annotation.Value;

public record Constants() {
    @Value("${spring.mail.username}")
    public static String FROM_MAIL;
    public static final String FROM_NAME = "Team Mail Flock";

    public static final String SUBSCRIBE_MESSAGE = "Hey There,\n" +
            "We hope this email finds you well. " +
            "On behalf of the entire Mail Flock team, " +
            "we want to extend a warm thank you for subscribing to our mailing list. " +
            "We're excited to have you as part of our community!\n" +
            "\n" +
            "By subscribing to our mailing list, you've taken the first step " +
            "towards staying updated on the latest news, updates, and exciting " +
            "developments in the world of Cold Mailing. We're committed to " +
            "providing you with valuable insights, informative content, and exclusive " +
            "offers that we believe you'll find both engaging and beneficial.\n\n" +
            "Regards,\n" + FROM_NAME;

    public static final String UNSUBSCRIBE_MESSAGE = "Hey There,\n" +
            "We hope this email finds you well. We wanted to reach " +
            "out and express our sincere regret for seeing you unsubscribe " +
            "from our mailing list. Your presence has been valued, and we're " +
            "genuinely sorry that we couldn't provide you with content or " +
            "experiences that met your expectations.\n\n" +
            "While you won't be receiving our emails anymore, please know that " +
            "you're always welcome back to our community whenever you feel ready. " +
            "We're committed to learning from this experience and making the " +
            "necessary adjustments to create a more valuable and engaging " +
            "experience for all of our subscribers.\n\n" +
            "If you have any concerns, questions, or feedback, please don't hesitate " +
            "to reach out to our customer support team at support@mailflock.com. We're " +
            "here to listen and assist you in any way we can.\n\n" +
            "Regards,\n" + FROM_NAME;
}
