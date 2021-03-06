
package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fever.liveppt.exception.common.CommonException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.common.TokenInvalidException;
import com.fever.liveppt.exception.common.UnknownErrorException;
import com.fever.liveppt.exception.ppt.PptException;
import com.fever.liveppt.exception.ppt.PptFileInvalidTypeException;
import com.fever.liveppt.exception.ppt.PptNotExistedException;
import com.fever.liveppt.exception.user.UserException;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.PptService;
import com.fever.liveppt.service.UserService;
import com.fever.liveppt.utils.ControllerUtils;
import com.fever.liveppt.utils.ResultJson;
import com.fever.liveppt.utils.StatusCode;
import com.fever.liveppt.utils.TokenAgent;
import com.google.inject.Inject;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author
 * @version : v1.00
 * @Description : PPT controller 提供给前端以及手机端PPT操作的接口
 */
public class PptController extends Controller {

    //PPT和PPTX文件的ContentType
    public static final String PPT_CONTENTTYPE = "application/vnd.ms-powerpoint";
    public static final String PPTX_CONTENTTYPE = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    //.ppt或.pptx文件后缀
    static Pattern pptTailPattern = Pattern.compile(".+\\.(ppt|pptx)");
    //通过guice注入
    @Inject
    UserService userService;
    @Inject
    PptService pptService;

    /**
     * 获取用户所有PPT的列表
     *
     * @return
     * @throws InvalidParamsException
     * @throws TokenInvalidException
     */
    public Result infoAll() {
        ResultJson resultJson;
        try {
            //验证Token并提取userEmail
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

        return ok(resultJson.o);
    }

    /**
     * 获取指定PPT的信息
     *
     * @return
     * @throws PptNotExistedException
     * @throws InvalidParamsException
     */
    public Result getPptInfo() {
        ResultJson resultJson;
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

            Ppt ppt = pptService.getPpt(pptId);
            if (ppt == null) {
                //未找到指定pptId的PPT
                throw new PptNotExistedException();
            }
            //组装成功返回信息
            ObjectNode data = ppt.toJsonNode();
            resultJson = new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, data);

        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (PptNotExistedException e) {
            resultJson = new ResultJson(e);
        }

        return ok(resultJson.o);
    }

    /**
     * 获取指定PPT和页码的图片
     *
     * @return
     * @throws InvalidParamsException
     * @throws PptNotExistedException
     * @throws NumberFormatException
     */
    public Result getPptPageImage() {


        //如果含有IF_MODIFIED_SINCE报头则返回NOT_MODIFIED
        String ifModifiedSince = request().getHeader(Controller.IF_MODIFIED_SINCE);
        if (ifModifiedSince != null && ifModifiedSince.length() > 0) {
            return status(NOT_MODIFIED);
        }

        ResultJson resultJson;
        try {
            //获取GET参数
            Map<String, String[]> params = request().queryString();
            if (params == null || params.size() == 0) {
                throw new InvalidParamsException();
            }

            //验证Token并提取userEmail
            String userEmail = request().getQueryString("uemail");
            String token = request().getQueryString("token");
            if (userEmail == null || token == null) {
                throw new InvalidParamsException();
            } else if (!TokenAgent.isTokenValid(token, userEmail)) {
                throw new TokenInvalidException();
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
            byte[] imageByte = pptService.getPptPage(userEmail, pptId, page);
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

        return ok(resultJson.o);
    }

    /**
     * 上传PPT
     *
     * @return
     * @throws InvalidParamsException
     * @throws PptFileInvalidTypeException
     * @throws UserException
     */
    public Result pptUpload() {
        ResultJson resultJson;
        try {
            //验证Token并提取userEmail
            User user = TokenAgent.validateTokenAndGetUser(userService, request());

            Http.MultipartFormData bodyData = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart pptFilePart = bodyData.getFile("pptFile");
            if (pptFilePart == null) {
                //提取文件失败
                throw new InvalidParamsException();
            } else {
                //成功提取FilePart

                //获取ContentType和文件名
                String contentType = pptFilePart.getContentType();
                String pptFileName = pptFilePart.getFilename();
                if (contentType == null || pptFileName == null) {
                    throw new PptFileInvalidTypeException();
                }

                //验证文件类型
                //验证contentType
                if (!contentType.equals(PPT_CONTENTTYPE) && !contentType.equals(PPTX_CONTENTTYPE)) {
                    throw new PptFileInvalidTypeException();
                }
                //验证文件名是否以".ppt"或".pptx"结尾
                if (!pptTailPattern.matcher(pptFileName).matches()) {
                    throw new PptFileInvalidTypeException();
                }

                //获取文件主体
                File pptFile = pptFilePart.getFile();
                long pptFileSize = pptFile.length();

                pptService.uploadPptToS3(user, pptFile, pptFileName, pptFileSize);

                return ok(ResultJson.simpleSuccess().o);
            }
        } catch (CommonException e) {
            resultJson = new ResultJson(e);
        } catch (PptException e) {
            resultJson = new ResultJson(e);
        } catch (UserException e) {
            resultJson = new ResultJson(e);
        }

        return ok(resultJson.o);
    }

    /**
     * 删除PPT
     *
     * @return
     * @throws InvalidParamsException
     * @throws PptNotExistedException
     * @throws UserException
     */
    public Result pptDelete() {
        ResultJson resultJson;
        try {
            //验证Token并提取userEmail
            User user = TokenAgent.validateTokenAndGetUser(userService, request());

            //获取GET参数
            Map<String, String[]> params = request().body().asFormUrlEncoded();
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

            pptService.deletePpt(user, pptId);
            resultJson = ResultJson.simpleSuccess();


        } catch (CommonException e) {
            resultJson = new ResultJson(e);
        } catch (PptException e) {
            resultJson = new ResultJson(e);
        } catch (UserException e) {
            resultJson = new ResultJson(e);
        }
        //若获取不成功返回JSON
        resultJson = (resultJson == null) ? (new ResultJson(new UnknownErrorException())) : resultJson;
        return ok(resultJson.o);
    }

    /**
     * 更新PPT转换的状态
     *
     * @return
     */
    public Result convertstatus() {
        String bodyText = request().body().asText();

        JsonNode json = Json.parse(bodyText);
        if (json == null) {
            Logger.info("convertstatus failed to parse bodyText:" + bodyText);
        } else {
            String messageText = json.findPath("Message").textValue();
            if (messageText == null) {
                Logger.info("messageText fetch failed");
            } else {
                messageText = messageText.replace("\\\"", "\"");

                JsonNode messageJson = Json.parse(messageText);
                if (messageJson == null) {
                    Logger.info("mj parse failed");
                } else {
                    pptService.updatePptConvertedStatus(messageJson);
                }
            }

        }
        return ok();
    }
}
