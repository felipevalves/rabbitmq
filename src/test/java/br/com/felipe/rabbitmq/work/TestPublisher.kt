package br.com.felipe.rabbitmq.work

import br.com.felipe.rabbitmq.workqueues.RabbitPublisher
import org.junit.Test

class TestPublisher {

    @Test
    fun test_send_message() {
        val send = RabbitPublisher()

        org.junit.Assert.assertEquals("OK", send.send(0))
    }


    @Test
    fun test_send_n_messages() {
        val send = RabbitPublisher()

        for (i in 1..20)
            send.send(i)

        org.junit.Assert.assertTrue(true)
    }
}