package controllers;

import com.fever.liveppt.exception.common.CommonException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.user.UserException;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.UserService;
import com.fever.liveppt.utils.ResultJson;
import com.fever.liveppt.utils.TokenAgent;
import com.google.inject.Inject;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 用于处理额外事务的Controller
 */
public class AdditionController extends Controller {

    //通过guice注入
    @Inject
    UserService userService;

    /**
     * 用于接收反馈文本并写入相应的logger
     * @return
     */
    public Result feedback() {
        try {
            //验证Token并提取userEmail
            User user = TokenAgent.validateTokenAndGetUser(userService, request());

            Map<String, String[]> params = request().body().asFormUrlEncoded();
            if (params == null || params.size() == 0 || !params.containsKey("text")) {
                throw new InvalidParamsException();
            }

            //取出text字段的反馈文本内容
            String text = params.get("text")[0];
            if (text == null || text.length() == 0) {
                throw new InvalidParamsException();
            }

            //写入feedback专用的logger
            Logger.of("feedback").info(user.displayname + "(" + user.email + "):\n" + text);

            return ok(ResultJson.simpleSuccess().objectNode);

        } catch (CommonException e) {
            return ok(new ResultJson(e).objectNode);
        } catch (UserException e) {
            return ok(new ResultJson(e).objectNode);
        }

    }
}
