package com.booking.service;

import com.booking.common.service.IMailService;
import com.booking.domain.Order;
import com.booking.domain.User;
import com.booking.dto.UserQueryDTO;
import com.booking.utils.MailInfo;
import com.booking.utils.SHA2;
import com.booking.utils.STablePageRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserService {
    @Autowired
    UserService userService;
    @Autowired
    IMailService mailService;

    @Test
    public void saveUsers() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String p="0000000000";
        SHA2 sha2=new SHA2();
        for(int i=0;i<100;i++){
            String salt=sha2.getSalt(128);
            String password="password_"+i;
            password=sha2.sha2(password+salt);
            User user=new User();
            user.setSalt(salt);
            user.setUpassword(password);
            user.setEmail(i+"_@example.com");
            user.setEnable((1==(i%2)?false:true));
            user.setIcon("/upload/user/avatar/default_avatar.jpeg");
            user.setTelephone((String.valueOf(i)+p).substring(0,11));
            user.setType(i%2);
            user.setUname("name_"+i);
            userService.save(user);
        }
    }
    @Test
    public void findUsers(){
        STablePageRequest pageable=new STablePageRequest();
        UserQueryDTO queryDTO=new UserQueryDTO();
        Page<User> page = userService.findAll(queryDTO.getWhereClause(queryDTO),pageable.getUserPageable());
    }
    @Test
    public void findOrderByUser(){
        UserQueryDTO queryDTO=new UserQueryDTO();
        List<Long> uids=queryDTO.getUids();
        uids.add(1L);
        uids.add(2L);
        queryDTO.setUids(uids);
        List<Order> orders= userService.findAllOrder(queryDTO.getOrderSepcByUser(queryDTO));
        for(Order o : orders){
            System.out.println(o);
        }
    }

    @Test
<<<<<<< HEAD
    public void testSaveOrder() {
        for (int i = 1; i <= 5; i++) {
            Order order = new Order();
            order.setCount(2);
            order.setPrice(129.9);
            order.setTotalPrice(order.getCount() * order.getPrice());
            order.setCreateTime(new Date());
            order.setStartTime(new Date());
            order.setEndTime(new Date());
            if (i % 4 == 0) order.setStatus(OrderStatusEnum.UNPAY);
            else if (i % 3 == 0) order.setStatus(OrderStatusEnum.UNUSE);
            else if (i % 2 == 0) order.setStatus(OrderStatusEnum.SUCCESS);
            else order.setStatus(OrderStatusEnum.CANCEL);
            order.setHotel(hotelService.findById(2L));
            order.setRoom(roomService.findById(10L));
            order.setUser(userService.findById(3L));
            order.setRemark("无烟房,外窗");
            order.setCheckInPerson(order.getUser().getUname());
            orderService.save(order);
        }
=======
    public void sendTemplateMail() throws MessagingException {
        MailInfo mailInfo=new MailInfo();
        mailInfo.setTo("imyxiong@163.com");
        mailInfo.setSubject("SpringBoot 发送模板邮件");
        mailService.sendTemplateMail(mailInfo, "thymeleaf/verification.html", "code","1234");
>>>>>>> aafd12c5ee2f0445450955a5aaf6f26e0633a081
    }
}
