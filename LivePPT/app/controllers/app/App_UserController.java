package controllers.app;

import java.util.Map;

import com.fever.liveppt.utils.ControllerUtils;
import com.fever.liveppt.utils.ResultJson;
import com.fever.liveppt.utils.exception.CommonException;
import com.fever.liveppt.utils.exception.UserException;
import com.fever.liveppt.utils.exception.utils.common.InvalidParamsException;
import play.Logger;
import play.api.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;

import com.fever.liveppt.service.UserService;
import com.fever.liveppt.utils.StatusCode;
import com.google.inject.Inject;

public class App_UserController extends Controller {
    @Inject
    UserService userService;

    /** 检验用户Email是否被占用
     *
     * @return
     */
    public Result checkEmail()
    {
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        //返回的JSON，初始化为null
        ResultJson resultJson = null;
        try {
            if (null == params) {
                throw new InvalidParamsException();
            }
            //uemail
            if (!ControllerUtils.isFieldExisted(params, "uemail")) {
                throw new InvalidParamsException();
            }

            // 获取参数
            String email = params.get("uemail")[0];

            //验证用户Email是否被占用
            resultJson = userService.isEmailExisted(email);
    } catch (UserException e) {
            resultJson = new ResultJson(e);
        } catch (InvalidParamsException e) {
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
        //获取get类型的参数
      //  Map<String, String[]> params = request().queryString();
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        //返回的JSON，初始化为null
        ResultJson resultJson = null;
        try {
            if (null == params) {
                throw new InvalidParamsException();
            }
        //检查必须的参数是否存在
            //检查必须的参数是否存在
            //uemail
            if (!ControllerUtils.isFieldExisted(params, "uemail")) {
                throw new InvalidParamsException();
            }

            //password
            if (!ControllerUtils.isFieldExisted(params, "password")) {
                throw new InvalidParamsException();
            }

            //seed
            if (!ControllerUtils.isFieldExisted(params, "seed") || params.get("seed")[0].length() != 16) {
                throw new InvalidParamsException();
            }


            // 获取参数
        String email = params.get("uemail")[0];
        String encryptedPassword = params.get("password")[0];
        String seed = params.get("seed")[0] ;

            //for test
           // encryptedPassword =  Crypto.encryptAES(encryptedPassword,seed);

        //验证帐号密码是否匹配
        resultJson = userService.isPassworrdCorrect(email, encryptedPassword,seed);

        Logger.info(resultJson.toString());
    } catch (UserException e) {
            resultJson = new ResultJson(e);
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        }

        //若返回JSON为空，设为位置错误
        resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

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
            if (!ControllerUtils.isFieldExisted(params, "uemail")) {
                throw new InvalidParamsException();
            }

            /*
            //displayname

            if (!ControllerUtils.isFieldExisted(params, "displayname")) {
                throw new InvalidParamsException();
            }
            */

            //password
            if (!ControllerUtils.isFieldExisted(params, "password")) {
                throw new InvalidParamsException();
            }

            //seed
            if (!ControllerUtils.isFieldExisted(params, "seed") || params.get("seed")[0].length() != 16) {
                throw new InvalidParamsException();
            }


            // 获取参数
            String email = params.get("uemail")[0];
            String encryptedPassword =params.get("password")[0];
            String displayName = (ControllerUtils.isFieldExisted(params, "displayname")) ? params.get("displayname")[0] : "";
            String seed = params.get("seed")[0];
            //for test
            encryptedPassword =  Crypto.encryptAES(encryptedPassword,seed);


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

    //以下代码准备舍弃

    /**
     * 检查email字段
     *
     * @param params
     * @return
     */
    ResultJson checkEmail(Map<String, String[]> params) {
        if (!params.containsKey("uemail")) {
            return new ResultJson(StatusCode.INVALID_PARAMS, null, "email字段缺失");
        }
        return new ResultJson(StatusCode.SUCCESS, null, null);
    }
    /*JsonResult checkEmail(Map<String, String[]> params)
    {
		if (!params.containsKey("email")){
			return new JsonResult(false, StatusCode.USER_EMAIL_ERROR, "email字段错误");
		}
		//TODO
		//添加邮件格式检查
	    /*if (! patternNumbers.matcher(params.get("email")[0]).matches())
			return new JsonResult(false, StatusCode.USER_EMAIL_ERROR, "email字段错误");*/
    /*	return new JsonResult(true);
    }  */

    /**
     * 检查password字段
     *
     * @param params
     * @return
     */
    ResultJson checkPassword(Map<String, String[]> params) {
        if (!params.containsKey("password")) {
            return new ResultJson(StatusCode.INVALID_PARAMS, null, "password字段缺失");
        }
        return new ResultJson(StatusCode.SUCCESS, null, null);
    }

    /**
     * 检查displayName字段
     *
     * @param params
     * @return
     */
    ResultJson checkDisplayName(Map<String, String[]> params) {
        if (!params.containsKey("displayname")) {
            return new ResultJson(StatusCode.INVALID_PARAMS, null, "displayName字段缺失");
        }
        return new ResultJson(StatusCode.SUCCESS, null, null);
    }

    /**
     * @param params
     * @return
     */
    ResultJson checkSeed(Map<String, String[]> params) {
        if (!params.containsKey("seed")) {
            return new ResultJson(StatusCode.INVALID_PARAMS, null, "seed字段缺失");
        }
        return new ResultJson(StatusCode.SUCCESS, null, null);
    }
}
