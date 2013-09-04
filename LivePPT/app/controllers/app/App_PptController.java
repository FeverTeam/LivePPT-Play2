package controllers.app;

import com.fever.liveppt.exception.common.CommonException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.common.TokenInvalidException;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.service.PptService;
import com.fever.liveppt.utils.JsonResult;
import com.fever.liveppt.utils.ResultJson;
import com.fever.liveppt.utils.StatusCode;
import com.fever.liveppt.utils.TokenAgent;
import com.google.inject.Inject;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class App_PptController extends Controller {

    @Inject
    PptService pptService;
    //数字匹配器
    Pattern patternNumbers = Pattern.compile("^[-\\+]?[\\d]*$");

    /**
     * 获取用户所有PPT的列表
     *
     * @return
     */
    public Result infoAll() {
        ResultJson resultJson = null;
        try {
            //验证Token
            String userEmail = TokenAgent.validateTokenFromHeader(request());
            if (userEmail == null) {
                //Token验证失败
            }

            //获取ppt
            List<Ppt> pptList = pptService.getPptList(userEmail);

            //组装PPT信息JSON数组
            ArrayNode pptInfoArraryNode = new ArrayNode(JsonNodeFactory.instance);
            for (Ppt ppt : pptList) {
                pptInfoArraryNode.add(ppt.toJsonNode());
            }

            resultJson = new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, pptInfoArraryNode);

            //Logger.info(resultJson.toString());
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        }
        resultJson = (this == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, StatusCode.UNKONWN_ERROR_MESSAGE)) : resultJson;

        return ok(resultJson);
    }

    /**
     * 获取用户所有PPT的列表
     *
     * @return
     */
    public Result getPptList() {
        ResultJson resultJson = null;
        try {
            //验证Token并获取userEmail
            String userEmail = TokenAgent.validateTokenFromHeader(request());

            //获取ppt
            List<Ppt> pptList = pptService.getPptList(userEmail);

            //组装PPT信息JSON数组
            ArrayNode pptInfoArraryNode = new ArrayNode(JsonNodeFactory.instance);
            for (Ppt ppt : pptList) {
                pptInfoArraryNode.add(ppt.toJsonNode());
            }

            resultJson = new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, pptInfoArraryNode);

            //Logger.info(resultJson.toString());
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        }
        resultJson = (this == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, StatusCode.UNKONWN_ERROR_MESSAGE)) : resultJson;

        return ok(resultJson);
    }

    /**
     * 获取指定PPT的信息
     *
     * @return
     */
    public Result getPptInfo() {
        Map<String, String[]> params = request().queryString();
        JsonResult resultJson;
        resultJson = checkPptId(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);
        Long pptId = Long.valueOf(params.get("pptId")[0]);
        resultJson = pptService.getPptInfo(pptId);
        Logger.info(resultJson.toString());
        return ok(resultJson);
    }

    /**
     * 获取指定PPT和页码的图片
     *
     * @return
     */
    public Result getPptPage() {


        String[] ifModifiedSince = request().headers().get(
                Controller.IF_MODIFIED_SINCE);
        if (ifModifiedSince != null && ifModifiedSince.length > 0) {
            return status(NOT_MODIFIED);
        }

        Map<String, String[]> params = request().queryString();
        Long pptId = Long.valueOf(params.get("pptId")[0]);
        Long pageIndex = Long.valueOf(params.get("pageIndex")[0]);
        JsonResult resultJson;
        //检查pptId
        resultJson = checkPptId(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);
        //检查pageIndex
        resultJson = checkPageIndex(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);

        // getPptPage修改返回类型，添加对pageIndex的容错性

        // 设置ContentType为image/jpeg
        response().setContentType("image/jpeg");
        // 设置返回头LastModified
        response().setHeader(Controller.LAST_MODIFIED,
                "" + new Date().getTime());

        return ok(pptService.getPptPageAsBig(pptId, pageIndex));
    }

    /**
     * 检查userId字段
     *
     * @param params
     * @return
     */
    JsonResult checkUserId(Map<String, String[]> params) {
        if (!params.containsKey("userId")) {
            return new JsonResult(false, StatusCode.USER_ID_ERROR, "userId字段错误");
        }

        if (!patternNumbers.matcher(params.get("userId")[0]).matches())
            return new JsonResult(false, StatusCode.USER_ID_ERROR, "userId字段错误");
        return new JsonResult(true);
    }

    /**
     * 检查pptId字段
     *
     * @param params
     * @return
     */
    JsonResult checkPptId(Map<String, String[]> params) {
        if (!params.containsKey("pptId")) {
            return new JsonResult(false, StatusCode.PPT_ID_ERROR, "pptId错误");
        }

        if (!patternNumbers.matcher(params.get("pptId")[0]).matches())
            return new JsonResult(false, StatusCode.PPT_ID_ERROR, "pptId错误");
        return new JsonResult(true);
    }

    /**
     * 检查pageIndex字段
     *
     * @param params
     * @return
     */
    JsonResult checkPageIndex(Map<String, String[]> params) {
        if (!params.containsKey("pageIndex")) {
            return new JsonResult(false, StatusCode.PPT_PAGEINDEX_ERROR, "pageIndex字段错误");
        }
        if (!patternNumbers.matcher(params.get("pageIndex")[0]).matches())
            return new JsonResult(false, StatusCode.PPT_PAGEINDEX_ERROR, "pageIndex字段错误");
        return new JsonResult(true);
    }
}
