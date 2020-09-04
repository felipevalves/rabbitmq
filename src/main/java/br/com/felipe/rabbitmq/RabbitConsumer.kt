package br.com.felipe.rabbitmq

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.Charset
import kotlin.contracts.contract

class RabbitConsumer {

    private val logger: Logger = LoggerFactory.getLogger(RabbitConsumer::class.java)
    private val QUEUE_NAME = "hello"

    fun consume() {

        try {
            val factory = ConnectionFactory()
            factory.host = "172.10.1.15"
            factory.port = 5672
            factory.username = "guest"
            factory.password = "qwmi138**"

            val conn = factory.newConnection()

            val channel = conn.createChannel()
            channel.queueDeclare(QUEUE_NAME, false, false, false, null)

            logger.info("Waiting for messages... ")


            val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
                val message = String(delivery.body, Charset.defaultCharset())
                println(" -> Received '$message'")
            }

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, CancelCallback{})

        }
        catch (e: Exception ) {
            logger.error("Error to send message ", e)
        }

    }
}