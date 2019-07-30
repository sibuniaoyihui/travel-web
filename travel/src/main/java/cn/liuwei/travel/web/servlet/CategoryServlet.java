package cn.liuwei.travel.web.servlet;

import cn.liuwei.travel.domain.Category;
import cn.liuwei.travel.service.CategoryService;
import cn.liuwei.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/categoryServlet/*")
public class CategoryServlet extends BaseServlet {
    CategoryService categoryService = new CategoryServiceImpl();
    /*
    查询所有
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> list = categoryService.findAll();
//        ObjectMapper objectMapper = new ObjectMapper();
//        response.setContentType("application/json;charset=utf-8");
//        objectMapper.writeValue(response.getWriter(),list);
        writeValue(list,response);
    }

}
