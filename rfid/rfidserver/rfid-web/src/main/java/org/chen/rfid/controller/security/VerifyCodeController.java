package org.chen.rfid.controller.security;

import io.swagger.annotations.Api;

import org.chen.rfid.util.VerifyCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author 程强
 * @date 2020年01月17日 15:22
 * @Description:
 */
@RestController
@Api(tags = "验证码接口")
public class VerifyCodeController {
    @GetMapping("/vercode")
    @ApiIgnore
    public void code(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        String text = vc.getText();
        HttpSession session = req.getSession();
        session.setAttribute("index_code", text);
        VerifyCode.output(image, resp.getOutputStream());
    }
}
