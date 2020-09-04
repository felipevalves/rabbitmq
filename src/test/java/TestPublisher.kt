import br.com.felipe.rabbitmq.RabbitPublisher
import org.junit.Test

class TestPublisher {

    @Test
    fun test_send_message() {
        val send = RabbitPublisher()

        org.junit.Assert.assertEquals("OK", send.send(0))
    }


    @Test
    fun test_send_100_messages() {
        val send = RabbitPublisher()

        for (i in 0..1000)
            send.send(i)

        org.junit.Assert.assertTrue(true)
    }
}