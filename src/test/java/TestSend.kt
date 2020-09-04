import br.com.felipe.rabbitmq.RabbitPublisher
import org.junit.Test

class TestSend {

    @Test
    fun test_send_message() {
        val send = RabbitPublisher()
        org.junit.Assert.assertEquals("OK", send.send())
    }
}