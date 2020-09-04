package br.com.felipe.rabbitmq.workqueues

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.MessageProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.Charset
import java.time.LocalDateTime


class RabbitPublisher {

    private val logger: Logger = LoggerFactory.getLogger(RabbitPublisher::class.java)
    private val QUEUE_NAME = "durable-queue"
    private val DEFAULT_EXCHANGE = ""


    fun send(index: Int): String {

        val message = "{Hello World from kotlin!! ${LocalDateTime.now()} --> $index"

        try {
            val factory = ConnectionFactory()
            factory.host = "172.10.1.15"
            factory.port = 5672
            factory.username = "guest"
            factory.password = "guest01"

            val conn = factory.newConnection()
            conn.use {
                val channel = it.createChannel()

                val durable = true

                channel.queueDeclare(QUEUE_NAME, durable, false, false, null)
                channel.basicPublish(DEFAULT_EXCHANGE, QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.toByteArray(Charset.defaultCharset()))
                logger.info("Message Sent! $message ")
            }
        }
        catch (e: Exception ) {
            logger.error("Error to send message ", e)
            return "NOT_OK"
        }

        return "OK"

    }
}