package ru.family.notification.kafka;


import static ru.family.notification.config.KafkaConfigConstants.TOPIC_PREFIX;

public interface MailTopics {
    String MAIL_2FA_TOPIC = TOPIC_PREFIX + "mail-auth2fa-account-created";
    String PASSWORD_RESET = TOPIC_PREFIX + "password-reset";
    String REWARD_CONFIRMATION = TOPIC_PREFIX + "sold-reward";

}
