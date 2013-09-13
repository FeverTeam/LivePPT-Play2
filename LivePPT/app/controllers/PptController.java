package controllers;

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
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
     */
    public Result infoAll() {
        ResultJson resultJson = null;
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

            Ppt ppt = pptService.getPpt(pptId);
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

    public Result pptUpload() {
        ResultJson resultJson = null;
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

                return ok(new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, null));
            }
        } catch (CommonException e) {
            resultJson = new ResultJson(e);
        } catch (PptException e) {
            resultJson = new ResultJson(e);
        } catch (UserException e) {
            resultJson = new ResultJson(e);
        }
        //若获取不成功返回JSON
        resultJson = (this == null) ? (new ResultJson(new UnknownErrorException())) : resultJson;
        return ok(resultJson);
    }

    public Result pptDelete() {
        ResultJson resultJson = null;
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
            resultJson = new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, null);


        } catch (CommonException e) {
            resultJson = new ResultJson(e);
        } catch (PptException e) {
            resultJson = new ResultJson(e);
        } catch (UserException e) {
            resultJson = new ResultJson(e);
        }
        //若获取不成功返回JSON
        resultJson = (resultJson == null) ? (new ResultJson(new UnknownErrorException())) : resultJson;
        return ok(resultJson);
    }

    /**
     * 更新PPT转换的状态
     *
     * @return
     */
    public Result convertstatus() {
        JsonNode json = Json.parse(request().body().asText());
        JsonNode messageJson = Json.parse(json.findPath("Message")
                .getTextValue());
        pptService.updatePptConvertedStatus(messageJson);
        return ok();
    }
}
