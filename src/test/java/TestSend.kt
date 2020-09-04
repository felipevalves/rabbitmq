import br.com.felipe.rabbitmq.RabbitSend
import org.junit.Test

class TestSend {

    @Test
    fun test_send_message() {
        val send = RabbitSend()
        org.junit.Assert.assertEquals("OK", send.send())
    }
}