package controllers.app;

import com.fever.liveppt.exception.common.CommonException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.common.TokenInvalidException;
import com.fever.liveppt.exception.common.UnknownErrorException;
import com.fever.liveppt.exception.ppt.PptException;
import com.fever.liveppt.exception.ppt.PptNotExistedException;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.service.PptService;
import com.fever.liveppt.utils.*;
import com.google.inject.Inject;
import org.codehaus.jackson.JsonNode;
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
        ResultJson resultJson = null;
        try {
            //获取GET参数
            Map<String, String[]> params = request().queryString();
            if (params == null) {
                throw new InvalidParamsException();
            }

            //检查字段参数
            if (!ControllerUtils.isFieldNotNull(params, "pptId")) {
                throw new InvalidParamsException();
            }

            //获取参数
            Long pptId = Long.valueOf(params.get("pptId")[0]);
            if (pptId == null) {
                //长整型转换失败
                throw new InvalidParamsException();
            }

            Ppt ppt = pptService.getSinglePptInfo(pptId);
            if (ppt == null) {
                //未找到指定pptId的PPT
                throw new PptNotExistedException();
            }
            //组装成功返回信息
            JsonNode data = ppt.toJsonNode();
            resultJson = new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, data);

            return ok(resultJson);
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (PptNotExistedException e) {
            resultJson = new ResultJson(e);
        }

        resultJson = (this == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, StatusCode.UNKONWN_ERROR_MESSAGE)) : resultJson;

        return ok(resultJson);
    }

    /**
     * 获取指定PPT和页码的图片
     *
     * @return
     */
    public Result getPptPageImage() {
        //如果含有IF_MODIFIED_SINCE报头则返回NOT_MODIFIED
        String ifModifiedSince = request().getHeader(Controller.IF_MODIFIED_SINCE);
        if (ifModifiedSince != null && ifModifiedSince.length() > 0) {
            return status(NOT_MODIFIED);
        }

        ResultJson resultJson = null;
        try {
            //获取GET参数
            Map<String, String[]> params = request().queryString();
            if (params == null || params.size() == 0) {
                throw new InvalidParamsException();
            }

            //检查参数
            //pptId
            if (!ControllerUtils.isFieldNotNull(params, "pptId")) {
                throw new InvalidParamsException();
            }
            //pageIndex
            if (!ControllerUtils.isFieldNotNull(params, "page")) {
                throw new InvalidParamsException();
            }

            //获取参数
            Long pptId = Long.valueOf(params.get("pptId")[0]);
            Long page = Long.valueOf(params.get("page")[0]);


            //尝试获取指定页码图像数据
            byte[] imageByte = pptService.getPptPage(pptId, page);
            if (imageByte.length > 0) {
                //成功获取图像数据

                // 设置ContentType为image/jpeg
                response().setContentType("image/jpeg");
                // 设置返回头LastModified
                response().setHeader(Controller.LAST_MODIFIED,
                        "" + new Date().getTime());
                return ok(imageByte);

            } else {
                //没有获得数据
                return ok();
            }


        } catch (CommonException e) {
            resultJson = new ResultJson(e);
        } catch (NumberFormatException e) {
            //整数转换失败
            resultJson = new ResultJson(new InvalidParamsException());
        } catch (PptException e) {
            resultJson = new ResultJson(e);
        }

        //若获取不成功返回JSON
        resultJson = (this == null) ? (new ResultJson(new UnknownErrorException())) : resultJson;
        return ok(resultJson);

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
