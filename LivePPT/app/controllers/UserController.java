package controllers;

import com.fever.liveppt.exception.common.CommonException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.user.UserException;
import com.fever.liveppt.exception.user.UserExistedException;
import com.fever.liveppt.service.UserService;
import com.fever.liveppt.utils.ControllerUtils;
import com.fever.liveppt.utils.ResultJson;
import com.fever.liveppt.utils.StatusCode;
import com.google.inject.Inject;
import play.Logger;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Map;

public class UserController extends Controller {
    @Inject
    UserService userService;

    /**
     * 检验用户Email是否被占用
     *
     * @return
     */
    public Result checkEmail() {
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        //返回的JSON，初始化为null
        ResultJson resultJson;
        try {
            if (null == params) {
                throw new InvalidParamsException();
            }
            //uemail
            if (!ControllerUtils.isFieldNotNull(params, "uemail")) {
                throw new InvalidParamsException();
            }

            // 获取参数
            String email = params.get("uemail")[0];

            //验证用户Email是否被占用
            boolean isExisted = userService.isEmailExisted(email);
            if (!isExisted) {
                resultJson = ResultJson.simpleSuccess();
            } else {
                throw new UserExistedException();
            }

        } catch (UserException e) {
            resultJson = new ResultJson(e);
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (CommonException e) {
            resultJson = new ResultJson(e);
        }

        //返回JSON
        return ok(resultJson);
    }

    /**
     * 用户登录接口
     *
     * @return
     */
    public Result login() {
        Logger.debug(Crypto.sign("simonlbw", "1234567890123456".getBytes()));

        //获取POST参数
        Map<String, String[]> params = request().body().asFormUrlEncoded();

        //返回的JSON，初始化为null
        ResultJson resultJson;
        try {
            if (null == params) {
                throw new InvalidParamsException();
            }

            //检查必须的参数是否存在
            //uemail
            if (!ControllerUtils.isFieldNotNull(params, "uemail")) {
                throw new InvalidParamsException();
            }

            //password
            if (!ControllerUtils.isFieldNotNull(params, "password")) {
                throw new InvalidParamsException();
            }

            //seed
            if (!ControllerUtils.isFieldNotNull(params, "seed")) {
                throw new InvalidParamsException();
            }


            // 获取参数
            String email = params.get("uemail")[0];
            String hashedPassword = params.get("password")[0];
            String seed = params.get("seed")[0];


            //验证帐号密码是否匹配
            resultJson = userService.isPassworrdCorrect(email, hashedPassword, seed);

        } catch (UserException e) {
            resultJson = new ResultJson(e);
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (CommonException e) {
            resultJson = new ResultJson(e);
        }

        //返回JSON
        return ok(resultJson);
    }

    /**
     * 用户注册接口
     *
     * @return
     */
    public Result register() {
        //获取POST参数
        Map<String, String[]> params = request().body().asFormUrlEncoded();

        //返回的JSON，初始化为null
        ResultJson resultJson;
        try {
            if (null == params) {
                throw new InvalidParamsException();
            }

            //检查必须的参数是否存在
            //uemail
            if (!ControllerUtils.isFieldNotNull(params, "uemail")) {
                throw new InvalidParamsException();
            }

            /*
            //displayname

            if (!ControllerUtils.isFieldNotNull(params, "displayname")) {
                throw new InvalidParamsException();
            }
            */

            //password
            if (!ControllerUtils.isFieldNotNull(params, "password")) {
                throw new InvalidParamsException();
            }

            //seed
            if (!ControllerUtils.isFieldNotNull(params, "seed") || params.get("seed")[0].length() != 16) {
                throw new InvalidParamsException();
            }


            // 获取参数
            String email = params.get("uemail")[0];
            String encryptedPassword = params.get("password")[0];
            String displayName = (ControllerUtils.isFieldNotNull(params, "displayname")) ? params.get("displayname")[0] : "";
            String seed = params.get("seed")[0];


            //注册用户,写入新用户信息
            resultJson = userService.register(email, encryptedPassword, displayName, seed);

        } catch (InvalidParamsException e) {
            //e.printStackTrace();
            resultJson = new ResultJson(e);
        } catch (UserException e) {
            //e.printStackTrace();
            resultJson = new ResultJson(e);
        } catch (CommonException e) {
            //e.printStackTrace();
            resultJson = new ResultJson(e);
        }

        //若返回JSON为空，设为位置错误
        resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;


        //返回JSON
        return ok(resultJson);

    }

}
