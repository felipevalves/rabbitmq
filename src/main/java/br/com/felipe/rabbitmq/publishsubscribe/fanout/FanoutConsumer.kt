package br.com.felipe.rabbitmq.publishsubscribe.fanout

import com.rabbitmq.client.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.Charset
import kotlin.random.Random

class FanoutConsumer {

    private val logger: Logger = LoggerFactory.getLogger(FanoutConsumer::class.java)
    private val EXCHANGE_NAME = "logs"
    private val EXCHANGE_TYPE = "fanout"

    fun consume() {

        val factory = ConnectionFactory()
        factory.host = "172.10.1.15"
        factory.port = 5672
        factory.username = "guest"
        factory.password = "guest01"

        val conn = factory.newConnection()

        val channel = conn.createChannel()
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE)

        //when we supply no parameters to queueDeclare() we create a non-durable, exclusive, autodelete queue with a generated name
        val queueName = channel.queueDeclare().queue

        //binding - relationship between exchange and a queue
        channel.queueBind(queueName, EXCHANGE_NAME, "")

        logger.info("Waiting for messages... ")


        val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
            val message = String(delivery.body, Charset.defaultCharset())
            println(" -> Received '$message'")

            fakeBusy()
        }

        val autoAck = true

        channel.basicConsume(queueName, autoAck, deliverCallback, CancelCallback{ })
    }

    private fun fakeBusy() {

        val time = Random.nextLong(1, 10)
        println("\t\tfakeBusy - working for $time seconds")
        Thread.sleep(time * 1000)

    }

}

fun main(args: Array<String>) {
    val receive = FanoutConsumer()
    receive.consume()
}

