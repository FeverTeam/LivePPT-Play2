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

<<<<<<< HEAD
    public static String KEY_CTX_ARG_USER = "user";
    /**
     * 获取User
     *
     * @param ctx 传入Http.Context
     * @return
     */
    public static User getUser(Http.Context ctx) {
        // 获取session
        Session sess = ctx.session();
        User user = getUser(ctx);

        // 从Session中提取email字段
        String email = sess.get("email");

        // 若字段不存在则判定为未登录，否则为已登录
        if (email == null || email.equals("")) {
            Logger.info("Not logined!");
            user = null;
        } else {
            Logger.info("Logined " + email);
            user = User.find.where().eq("email", email).findUnique();
        }
        ctx.args.put(KEY_CTX_ARG_USER, user);
        return user;
    }

    public static Result login() {
        ObjectNode result = Json.newObject();

        // 获取POST参数
        Map<String, String[]> postData = request().body().asFormUrlEncoded();
        String email = postData.get("email")[0];
        String password = postData.get("password")[0];

        User user = User.isPasswordCorrect(email, password);
        if (user != null) {
            //用户验证成功
            result.put("isSuccess", true);

            Logger.info(user.email);
            Logger.info(user.displayname);

            // 更新session信息
            session("email", user.email);
            session("displayname", user.displayname);
        } else {
            //用户验证失败
            result.put("isSuccess", false);
            result.put("message", "用户名/密码不正确，或未注册。");
=======
    /**
     * 检验用户Email是否被占用
     *
     * @return
     */
    public Result checkEmail() {
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        //返回的JSON，初始化为null
        ResultJson resultJson = null;
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
>>>>>>> 37aaac0dc47379d6ab701ebfec39995ba15d6a00
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
        ResultJson resultJson = null;
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

        //若返回JSON为空，设为位置错误
        resultJson = (this == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, StatusCode.UNKONWN_ERROR_MESSAGE)) : resultJson;

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
        ResultJson resultJson = null;
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
