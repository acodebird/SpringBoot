package com.booking.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.booking.domain.Order;
import com.booking.domain.OrderDTO;
import com.booking.service.OrderService;
import com.booking.utils.ResponseEntity;
import com.booking.utils.STablePageRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/orderManage")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 获取一页订单
     *
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<Order>> findAll(STablePageRequest pageable) {

        Page<Order> page = Page.empty(pageable.getPageable());
        page = orderService.findAll(pageable.getPageable());

        return ResponseEntity.ofSuccess().status(HttpStatus.OK).data(page);
    }

    /**
     * 删除一条订单
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteById(id);
        return ResponseEntity.ofSuccess().status(HttpStatus.OK);
    }

    /**
     * 批量删除订单
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public ResponseEntity deleteByIds(@RequestBody List<Long> ids) {
        List<Order> orders = orderService.findAllById(ids);
        if (null != orders)
            orderService.deleteAll(orders);
        return ResponseEntity.ofSuccess().status(HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody OrderDTO orderDTO) {
        Order target = orderService.findById(id);
        if (target != null) {
            target.setStartTime(orderDTO.getStartTime());
            target.setEndTime(orderDTO.getEndTime());
            target.setPrice(orderDTO.getPrice());
            target.setCount(orderDTO.getCount());
            target.setTotalPrice(orderDTO.getTotalPrice());
            target.setStatus(orderDTO.getStatus());
            target.setCheckInPerson(orderDTO.getCheckInPerson());
            target.setRemark(orderDTO.getRemark());
            target.getHotel().setHname(orderDTO.getHotel().getHname());
            target.getRoom().setRname(orderDTO.getRoom().getRname());
            orderService.save(target);
        }

        return ResponseEntity.ofSuccess().status(HttpStatus.OK).data("更新成功");
    }
}
