package br.com.felipe.rabbitmq

import com.rabbitmq.client.ConnectionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.Charset
import java.time.LocalDateTime


class RabbitSend {

    private val logger: Logger = LoggerFactory.getLogger(RabbitSend::class.java)
    private val QUEUE_NAME = "hello"


    fun send(): String {

        val message = "My Message ${LocalDateTime.now()}"

        try {
            val factory = ConnectionFactory()
            factory.host = "172.10.1.15"
            factory.port = 5672
            factory.username = "guest"
            factory.password = "qwmi138**"

            val conn = factory.newConnection()
            conn.use {
                val channel = it.createChannel()
                channel.queueDeclare(QUEUE_NAME, false, false, false, null)
                channel.basicPublish("", QUEUE_NAME, null, message.toByteArray(Charset.defaultCharset()))
                logger.info("Message Sent! %message ")
            }
        }
        catch (e: Exception ) {
            logger.error("Error to send message ", e)
            return "NOT_OK"
        }

        return "OK"

    }

}