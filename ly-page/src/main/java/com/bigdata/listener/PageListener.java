package com.bigdata.listener;




import com.bigdata.service.FileService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PageListener {

    @Autowired
    private FileService fileService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ly.item.queue222", durable = "true"),
            exchange = @Exchange(
                    value = "ly.item.exchange",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"item.insert","item.update"}))
    public void listenCreate(Long id){
       //创建静态页面

        fileService.syncCreateHtml(id);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ly.item.queue1111", durable = "true"),
            exchange = @Exchange(
                    value = "ly.item.exchange",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"item.delete"}))
    public void listenDeletePage(Long id) {
        //删除静态页面
        fileService.deleteHtml(id);
    }
}
