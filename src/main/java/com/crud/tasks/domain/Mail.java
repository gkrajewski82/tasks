package com.crud.tasks.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Mail {

    private final String mailTo;
    private final String subject;
    private final String message;
    private final String toCc;

    /*public static class MailBuilder {
        private String mailTo;
        private String subject;
        private String message;
        private String toCc;

        public MailBuilder setMailTo(String mailTo) {
            this.mailTo = mailTo;
            return this;
        }

        public MailBuilder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public MailBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public MailBuilder setToCc(String toCc) {
            this.toCc = toCc;
            return this;
        }

        public Mail build() {
            return new Mail(mailTo, subject, message, toCc);
        }
    }*/
}