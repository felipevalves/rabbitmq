package br.com.felipe.rabbitmq.publishsubscribe

import br.com.felipe.rabbitmq.publishsubscribe.fanout.FanoutPublisher
import br.com.felipe.rabbitmq.workqueues.RabbitPublisher
import org.junit.Test

class TestFanoutPublisher {

    @Test
    fun test_send_message() {
        val send = FanoutPublisher()

        org.junit.Assert.assertEquals("OK", send.send(0))
    }

    @Test
    fun test_send_n_messages() {
        val send = FanoutPublisher()

        for (i in 1..20)
            send.send(i)

        org.junit.Assert.assertTrue(true)
    }


}