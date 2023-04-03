package com.example.kotlin.fitness.demo.vic.mail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailSender {
    @Autowired
    private val mailSender: JavaMailSender? = null

    // emailTo - recipient , subject -topipOfmail ,message - msg
    @Value("\${spring.mail.username}")
    private val username: String? = null
    fun send(emailTo: String?, subject: String?, message: String?) {
        val mailMessage = SimpleMailMessage()
        mailMessage.from = username
        mailMessage.setTo(emailTo)
        mailMessage.subject = subject
        mailMessage.text = message
        mailSender!!.send(mailMessage)
    }
}