package br.com.felipe.rabbitmq.workqueues

import com.rabbitmq.client.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.Charset
import kotlin.random.Random

class RabbitConsumer {

    private val logger: Logger = LoggerFactory.getLogger(RabbitConsumer::class.java)
    private val QUEUE_NAME = "durable-queue"

    fun consume() {

        val factory = ConnectionFactory()
        factory.host = "172.10.1.15"
        factory.port = 5672
        factory.username = "guest"
        factory.password = "guest01"

        val conn = factory.newConnection()

        val channel = conn.createChannel()
        val durable = true
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null)

        val countMessage = 5
        channel.basicQos(countMessage)

        logger.info("Waiting for messages... ")


        val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
            val message = String(delivery.body, Charset.defaultCharset())
            println(" -> Received '$message'")

            fakeBusy()
        }

        val autoAck = false

        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, CancelCallback{ })
    }

    private fun fakeBusy() {

        val time = Random.nextLong(1, 10)
        println("\t\tfakeBusy - working for $time seconds")
        Thread.sleep(time * 1000)

    }

}

fun main(args: Array<String>) {
    val receive = RabbitConsumer()
    receive.consume()
}

