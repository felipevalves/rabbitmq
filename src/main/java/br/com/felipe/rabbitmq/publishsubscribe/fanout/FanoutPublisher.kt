package br.com.felipe.rabbitmq.publishsubscribe.fanout

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.MessageProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.Charset
import java.time.LocalDateTime


class FanoutPublisher {

    private val logger: Logger = LoggerFactory.getLogger(FanoutPublisher::class.java)
    private val EXCHANGE_NAME = "logs"
    private val EXCHANGE_TYPE = "fanout"


    fun send(index: Int): String {

        val message = "Hello World Fanout !! ${LocalDateTime.now()} --> $index"

        try {
            val factory = ConnectionFactory()
            factory.host = "172.10.1.15"
            factory.port = 5672
            factory.username = "guest"
            factory.password = "guest01"

            val conn = factory.newConnection()
            conn.use {
                val channel = it.createChannel()
                channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE)

                channel.basicPublish(EXCHANGE_NAME, "", null, message.toByteArray(Charset.defaultCharset()))
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