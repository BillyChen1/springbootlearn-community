package cn.billychen.community.advice;

import cn.billychen.community.dto.ResultDTO;
import cn.billychen.community.exception.CustomizeErrorCode;
import cn.billychen.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ResponseBody
@ControllerAdvice
public class CustmomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handleControllerException(HttpServletRequest request, Throwable e, Model model) {

        String contentType = request.getContentType();

        if ("application/json".equals(contentType)) {
            //返回json
            if (e instanceof CustomizeException) {
                return ResultDTO.errorOf((CustomizeException)e);
            } else {
                return ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }

        } else {
            //错误页面详情
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");

        }

         }


}
